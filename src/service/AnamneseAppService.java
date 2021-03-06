package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Algoritmo;
import modelo.Anamnese;
import modelo.Atendimento;
import modelo.AvalIndicacaoEspec;
import modelo.Avaliacao;
import modelo.ConjuntoAvaliacao;
import modelo.Indicacao;
import modelo.Parametro;
import service.anotacao.Transacional;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import DAO.AnamneseDAO;
import DAO.AvalIndicacaoEspecDAO;
import DAO.EspecialistaDAO;
import DAO.IndicacaoDAO;
import DAO.ParametroDAO;
import DAO.Impl.AnamneseDAOImpl;
import DAO.Impl.AvalIndicacaoEspecDAOImpl;
import DAO.Impl.EspecialistaDAOImpl;
import DAO.Impl.IndicacaoDAOImpl;
import DAO.Impl.ParametroDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;

public class AnamneseAppService {

	private static AnamneseDAO anamneseDAO;
	private static AvalIndicacaoEspecDAO avalIndicacaoEspecDAO;
	private static IndicacaoDAO indicacaoDAO;
	private static ParametroDAO parametroDAO;
	
	public AvalIndicacaoEspecAppService avalIndicacaoEspecService;
	public AlgoritmoAppService algoritmoService;
	public AnamneseAppService() throws Exception {
		try {
			anamneseDAO = FabricaDeDao.getDao(AnamneseDAOImpl.class);
			avalIndicacaoEspecDAO = FabricaDeDao
					.getDao(AvalIndicacaoEspecDAOImpl.class);
			indicacaoDAO = FabricaDeDao.getDao(IndicacaoDAOImpl.class);
			parametroDAO = FabricaDeDao.getDao(ParametroDAOImpl.class);
			
			avalIndicacaoEspecService = FabricaDeAppService.getAppService(AvalIndicacaoEspecAppService.class);
			algoritmoService= FabricaDeAppService.getAppService(AlgoritmoAppService.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Transacional
	public void altera(Anamnese anamnese) throws AplicacaoException {
		anamneseDAO.altera(anamnese);
	}

	/**
	 * 
	 * Dependendo do algoritmo ativo ele chama o m�todo adequado que retornar� 
	 * um conjunto das avalia��es calculadas com o algoritmo selecionado.
	 * 
	 * @param atendimento
	 * @return
	 * 
	 * @author bruno.oliveira dayse.arruda
	 */
	public List<ConjuntoAvaliacao> recuperaAvaliacaoCalculadaPorIndicacao(Atendimento atendimento){
		Algoritmo algoritmoCorrente = algoritmoService.recuperaAlgoritmoAtivo();
		
		List<ConjuntoAvaliacao> conjuntosDeAvaliacoes = new ArrayList<ConjuntoAvaliacao>();
		if(algoritmoCorrente.getNome().equals("Grau de Semelhan�a")){
			conjuntosDeAvaliacoes = recuperaAvaliacaoCalculadaPorIndicacaoPeloGrauSemelhanca(atendimento);
		}
		else if(algoritmoCorrente.getNome().equals("�ndice de Descartes por Supera��o-Dist�ncia")){
			conjuntosDeAvaliacoes =	recuperaAvaliacaoCalculadaPorIndicacaoPelaDistanciaDescartes(atendimento);
		}
		else if(algoritmoCorrente.getNome().equals("Grau de Inclus�o")){
			conjuntosDeAvaliacoes =	recuperaAvaliacaoCalculadaPorIndicacaoPeloGrauDeInclusao(atendimento);
		}
		return conjuntosDeAvaliacoes;
	}
	
	/**
	 * Calculo da avalia��o por atendimento, por indica��o e por parametro, utilizando
	 * um algoritmo de Grau de Semelhan�a.
	 *  
	 * @param atendimento
	 * @param indicacao
	 * @param parametro
	 * @return
	 */
	public Avaliacao calculaAvaliacaoPorAtendimentoPorIndicacaoPorParametroPeloGrauSemelhanca(
			Atendimento atendimento, Indicacao indicacao, Parametro parametro) {
		
		Anamnese anamneseCorrente = null;
		try {
			/**
			 * Retorna a necessidade do paciente que pode ser encontrada atraves do atendimento passado por
			 * parametro mais o parametro que estamos calculando neste momento.
			 * 
			 * Ex.: Poder�amos dizer que num momento estamos passando como parametro o "Atendimento1" e o
			 * parametro "Febre", o resultado seria a anamnese deste parametro (febre) para este atendimento
			 * (atendimento1).
			 * 
			 * @author bruno.oliveira dayse.arruda (Coment�rios)
			 */
			anamneseCorrente = anamneseDAO.recuperaAnamnesePorAtendimentoPorParametro(atendimento, parametro);
		} catch (ObjetoNaoEncontradoException e) {
			e.printStackTrace();
		}
		
		/**
		 * Retorna a media do especialista para uma �nica indica��o e um �nico parametro.
		 */
		Double mediaValorEspecialistas = 
				avalIndicacaoEspecService.calculaMediaAvaliacaoEspecialistasPorIndicacaoPorParametro(indicacao, parametro);
		

		Avaliacao avaliacaoCorrente = new Avaliacao();
		avaliacaoCorrente.setParametro(parametro);
		avaliacaoCorrente.setIndicacao(indicacao);
		avaliacaoCorrente.setIntersecao(Math.min(mediaValorEspecialistas, anamneseCorrente.getValor()));
		avaliacaoCorrente.setUniao(Math.max(mediaValorEspecialistas, anamneseCorrente.getValor()));
		avaliacaoCorrente.setMediaEspecialistas(mediaValorEspecialistas);
		return avaliacaoCorrente;
	}
	
	
	/**
	 * Recupera avalia��o calcula por uma indica��o utilizando o algoritmo de grau de 
	 * semelhan�a.
	 * @param atendimento
	 * @return
	 */
	public List<ConjuntoAvaliacao> recuperaAvaliacaoCalculadaPorIndicacaoPeloGrauSemelhanca(
			Atendimento atendimento) {


		List<Indicacao> listIndicacao = indicacaoDAO.recuperaListaIndicacao();
		List<Parametro> listParametro = parametroDAO.recuperaListaDeParametros();


		List<ConjuntoAvaliacao> conjuntosDeAvaliacoes = new ArrayList<ConjuntoAvaliacao>();
		
		for (Indicacao indicacao : listIndicacao) {
			ConjuntoAvaliacao conjuntoAvaliacao = new ConjuntoAvaliacao();
			conjuntoAvaliacao.setIndicacao(indicacao);
			

			List<Avaliacao> listAvaliacao = new ArrayList<Avaliacao>();
			for (Parametro parametro : listParametro) {
				Avaliacao avaliacaoCorrente = calculaAvaliacaoPorAtendimentoPorIndicacaoPorParametroPeloGrauSemelhanca(atendimento, indicacao, parametro);
				listAvaliacao.add(avaliacaoCorrente);
			}
			
			conjuntoAvaliacao.setAvaliacoes(listAvaliacao);
			conjuntoAvaliacao.setSomatorioIntersecao(conjuntoAvaliacao.somaParametrosIntersecao());
			conjuntoAvaliacao.setSomatorioUniao(conjuntoAvaliacao.somaParametrosUniao());
			conjuntoAvaliacao.setGrauSemelhanca(conjuntoAvaliacao.getSomatorioIntersecao() / conjuntoAvaliacao.getSomatorioUniao());
			conjuntosDeAvaliacoes.add(conjuntoAvaliacao);
		}
		//Ordena e poe em ranking
		Collections.sort(conjuntosDeAvaliacoes);
		for (int i = 0; i < conjuntosDeAvaliacoes.size(); i++) {
			ConjuntoAvaliacao conjuntoAvaliacao = conjuntosDeAvaliacoes.get(i);
			conjuntoAvaliacao.setRanking(i+1);
		}
		return conjuntosDeAvaliacoes;
	}

	/**
	 * Recupera a media de avalia��o de indica��o dos especialistas.
	 * @return
	 */
	public List<ConjuntoAvaliacao> recuperaMediaDeAvaliacaoDeIndicacaoDeEspecialistas() {


		List<Indicacao> listIndicacao = indicacaoDAO.recuperaListaIndicacao();
		List<Parametro> listParametro = parametroDAO.recuperaListaDeParametros();


		List<ConjuntoAvaliacao> conjuntosDeAvaliacoes = new ArrayList<ConjuntoAvaliacao>();
		
		for (Indicacao indicacao : listIndicacao) {
			ConjuntoAvaliacao conjuntoAvaliacao = new ConjuntoAvaliacao();
			conjuntoAvaliacao.setIndicacao(indicacao);
			

			List<Avaliacao> listAvaliacao = new ArrayList<Avaliacao>();
//			for (Parametro parametro : listParametro) {
//				Avaliacao avaliacaoCorrente = calculaAvaliacaoPorAtendimentoPorIndicacaoPorParametro(atendimento, indicacao, parametro);
//				listAvaliacao.add(avaliacaoCorrente);
//			}
			
			conjuntoAvaliacao.setAvaliacoes(listAvaliacao);
			//usando apenas a uni�o que � a media dos especialistas para um determinado parametro para uma indicacao
			conjuntoAvaliacao.setSomatorioUniao(conjuntoAvaliacao.somaParametrosUniao());
			conjuntosDeAvaliacoes.add(conjuntoAvaliacao);
		}
		return conjuntosDeAvaliacoes;
	}
	
	/**
	 * Recupera a avalia��o calculada por indica��o dos especialistas para um determinado
	 * atendimento, utilizando o algoritmo da �ndice de Descartes por Supera��o de Dist�ncia
	 * (Combina a Dist�ncia de Hamming mais a Dist�ncia de Descartes).
	 * 
	 * @param atendimento
	 * @return
	 */
	public List<ConjuntoAvaliacao> recuperaAvaliacaoCalculadaPorIndicacaoPelaDistanciaDescartes(Atendimento atendimento){

		List<Indicacao> listIndicacao = indicacaoDAO.recuperaListaIndicacao();
		List<Parametro> listParametro = parametroDAO.recuperaListaDeParametros();

		List<ConjuntoAvaliacao> conjuntosDeAvaliacoes = new ArrayList<ConjuntoAvaliacao>();
		
		/* 
		 * Para cada indica��o � criado um objeto do tipo ConjuntoAvaliacao,
		 * que � um objeto preparado para ser manipulado no contexto do algoritmo.
		 * Sendo assim, a indica��o � setada em seu respectivo objeto ConjuntoAvaliacao.
		 */
		for (Indicacao indicacao : listIndicacao) {
			ConjuntoAvaliacao conjuntoAvaliacao = new ConjuntoAvaliacao();
			conjuntoAvaliacao.setIndicacao(indicacao);
			
			/*
			 *  Em seguida, calcula a avalia��o de cada Indica��o por parametro,
			 *  tendo em vista os dados de anamnese no atendimento.
			 *  
			 *  Este loop ir� gerar a avalia��o de todas os parametros em compara��o
			 *  a indica��o atual.
			 */
			List<Avaliacao> listAvaliacao = new ArrayList<Avaliacao>();
			for (Parametro parametro : listParametro) {
				Avaliacao avaliacaoCorrente = calculaAvaliacaoPorAtendimentoPorIndicacaoPorParametroPelaDistanciaDescartes(atendimento, indicacao, parametro);
				listAvaliacao.add(avaliacaoCorrente);
			}
			/*
			 * Lembrando que ainda n�o concluimos o loop de indica��o,
			 * o conjunAvaliacao da indicacao atual ir� receber
			 * a lista de avalia��es de todos os parametros nessa indica��o. 
			 */
			conjuntoAvaliacao.setAvaliacoes(listAvaliacao);
			
			/*
			 * Setamos o atributo SomatorioDistancia fazendo uma chamada do m�todo
			 * somaParametrosDistancia do mesmo objeto.
			 * 
			 * O somaParametrosDistancia � o m�todo que percorre a lista de avalia��es
			 * previamente setada e para cada avalia��o pega a dist�ncia e multiplica
			 * pelo peso do parametro.
			 */
			conjuntoAvaliacao.setSomatorioDistancia(conjuntoAvaliacao.somaParametrosDistancia());
			
			/*
			 * Setamos o atributo SometorioPesosParametos fazendo uma chamada do m�todo
			 * somaPesosParametros do mesmo objeto.
			 * 
			 * O somaPesosParametros � um m�todo que percorre a lista de avalia��es 
			 * previamente setada e para cada avalia��o pega o peso do parametro e
			 * soma ao total de pesos de parametros.
			 */
			conjuntoAvaliacao.setSomatorioPesosParametros(conjuntoAvaliacao.somaPesosParametros());
			
			/*
			 * Setamos o atributo DistanciaDescartes com o resultado da divis�o do Somat�rio
			 * das dist�ncias previamente calculadas divido pelo somat�rio dos pesos dos parametros
			 * previamente setados.
			 */
			conjuntoAvaliacao.setDistanciaDescartes(conjuntoAvaliacao.getSomatorioDistancia() / conjuntoAvaliacao.getSomatorioPesosParametros());
			
			/*
			 * No final, o processo estar� terminado para a Indica��o atual no loop e
			 * os dados estar�o salvos na inst�ncia atual do objeto conjuntoAvaliacao.
			 * 
			 * Ent�o, esse objeto ser� salvo numa lista para que as demais indica��es
			 * sejam tratadas.
			 */
			conjuntosDeAvaliacoes.add(conjuntoAvaliacao);
		}
		/*
		 * Reordena a lista de forma que as indica��es fiquem rankeadas corretamente.
		 */
		Collections.sort(conjuntosDeAvaliacoes);
		/*
		 * Ap�s reordenar a lista na ordem correta do ranking, este percorre cada avalia��o
		 * de cada indica��o e seta o seu Ranking.
		 * 
		 * Acontece que o collections.sort apenas coloca as avalia��es na ordem correta, o que o
		 * for faz � setar explicitamente um valor inteiro no atributo Ranking que representa a posi��o
		 * desta avalia��o no ranking.
		 * 
		 * O atributo � setado com i+1, j� que o loop inicia em zero.
		 */
		for(int i = 0; i < conjuntosDeAvaliacoes.size(); i++){
			ConjuntoAvaliacao conjuntoAvaliacao = conjuntosDeAvaliacoes.get(i);
			conjuntoAvaliacao.setRanking(i+1);
		}
		return conjuntosDeAvaliacoes;
	}
	
	/**
	 * Calcula uma avalia��o dado o atendimento, indicacao e o parametro, 
	 * utilizando o algoritimo da distancia de descartes
	 * @param atendimento
	 * @param indicacao
	 * @param parametro
	 * @return
	 */
	public Avaliacao calculaAvaliacaoPorAtendimentoPorIndicacaoPorParametroPelaDistanciaDescartes
									(Atendimento atendimento, Indicacao indicacao, Parametro parametro){

		Anamnese anamneseCorrente = null;
		/*
		 * Recupera a anamnese de um parametro em um atendimento espec�fico.
		 */
		try {
			anamneseCorrente = anamneseDAO.recuperaAnamnesePorAtendimentoPorParametro(atendimento, parametro);
		} catch (ObjetoNaoEncontradoException e) {
			e.printStackTrace();
		}
		
		/*
		 *  Retorna a m�dia de todos os especialistas.
		 */
		Double mediaValorEspecialistas = 
				avalIndicacaoEspecService.calculaMediaAvaliacaoEspecialistasPorIndicacaoPorParametro(indicacao, parametro);
		

		Avaliacao avaliacaoCorrente = new Avaliacao();
		avaliacaoCorrente.setParametro(parametro);
		avaliacaoCorrente.setIndicacao(indicacao);
		avaliacaoCorrente.setMediaEspecialistas(mediaValorEspecialistas);
		
		Double valorDistancia = 0.0;
		/* Valor de dist�ncia � medido atrav�s do valor dado a anamnese atual descrecido da m�dia dos especialistas*/
		valorDistancia = anamneseCorrente.getValor() - mediaValorEspecialistas;
		/* Do resultado da conta anterior gera-se o valor absoluto. */
		valorDistancia = Math.abs(valorDistancia);
		/* Caso o parametro for do tipo em que exceder n�o seja um problema ent�o
			calculamos o m�ximo entre 0 e o resultado do valor da distancia calculada
			nas duas linhas anteriores. Dessa forma o algoritmo n�o penaliza se ultrapassar
			 a necessidade do paciente */
		if(parametro.getTipo().equals(Parametro.TIPO_PODE_EXCEDER)){
			valorDistancia = Math.max(0, valorDistancia);		
		}
		avaliacaoCorrente.setDistancia(valorDistancia);

		return avaliacaoCorrente;
	}
	
	/**
	 * Recupera a avalia��o calculada por indica��o dos especialistas para um determinado
	 * atendimento, utilizando o algoritmo de Grau de Inclus�o.
	 * 
	 * @author bruno.oliveira
	 * @param atendimento
	 * @return lista de conjunto avalia��o
	 * 
	 */
	public List<ConjuntoAvaliacao> recuperaAvaliacaoCalculadaPorIndicacaoPeloGrauDeInclusao(Atendimento atendimento){

		List<Indicacao> listIndicacao = indicacaoDAO.recuperaListaIndicacao();
		List<Parametro> listParametro = parametroDAO.recuperaListaDeParametros();

		List<ConjuntoAvaliacao> conjuntosDeAvaliacoes = new ArrayList<ConjuntoAvaliacao>();
		
		/* 
		 * Para cada indica��o � criado um objeto do tipo ConjuntoAvaliacao,
		 * que � um objeto preparado para ser manipulado no contexto do algoritmo.
		 * Sendo assim, a indica��o � setada em seu respectivo objeto ConjuntoAvaliacao.
		 */
		for (Indicacao indicacao : listIndicacao) {
			ConjuntoAvaliacao conjuntoAvaliacao = new ConjuntoAvaliacao();
			conjuntoAvaliacao.setIndicacao(indicacao);
			
			/*
			 *  Em seguida, calcula a avalia��o de cada Indica��o por parametro,
			 *  tendo em vista os dados de anamnese no atendimento.
			 *  
			 *  Este loop ir� gerar a avalia��o de todas os parametros em compara��o
			 *  a indica��o atual.
			 */
			List<Avaliacao> listAvaliacao = new ArrayList<Avaliacao>();
			for (Parametro parametro : listParametro) {
				Avaliacao avaliacaoCorrente = calculaAvaliacaoPorAtendimentoPorIndicacaoPorParametroPeloGrauDeInclusao(atendimento, indicacao, parametro);
				listAvaliacao.add(avaliacaoCorrente);
			}
			/*
			 * Lembrando que ainda n�o concluimos o loop de indica��o,
			 * o conjunAvaliacao da indicacao atual ir� receber
			 * a lista de avalia��es de todos os parametros nessa indica��o. 
			 */
			conjuntoAvaliacao.setAvaliacoes(listAvaliacao);
			
			/*
			 * Setamos o atributo SomatorioDistancia fazendo uma chamada do m�todo
			 * somaParametrosDistancia do mesmo objeto.
			 * 
			 * O somaParamnetrosDistancia � um m�todo que percorre a lista de avalia��es
			 * previamente setada e para cada avalia��o pega a dist�ncia e multiplica
			 * pelo peso do parametro.
			 */
			conjuntoAvaliacao.setSomatorioDistancia(conjuntoAvaliacao.somaDistanciaDoGrauDeInclusao());
			
			/*
			 * Seta a necessidade do paciente atual, para que mais na frente possa somar todas as necessidades.
			 */
		//	conjuntoAvaliacao.setSomatorioNecessidadeDoPaciente(anamneseDAO.recuperaSomaAnamneseParaUmAtendimento(atendimento));
			List<Anamnese> necessidadesDoPaciente = new ArrayList<Anamnese>(anamneseDAO.recuperaListaDeAnamnesePorAtendimento(atendimento));
			for(Anamnese anamnese : necessidadesDoPaciente){
				conjuntoAvaliacao.setSomatorioNecessidadeDoPaciente(
						conjuntoAvaliacao.getSomatorioNecessidadeDoPaciente() + (anamnese.getValor() * anamnese.getParametro().getPeso())
						);
			}
			
			/*
			 * Setamos o atributo GrauInclusao com o resultado da subtra��o do Somat�rio
			 * das Necessidades deste Paciente e Distancia total calculada.
			 * O resultado da subtra��o do elementos � divido pelo somat�rio da necessidade do paciente
			 */
			/*
			 * G.I. = 1 - [SOMA(PESO i * MAX(0 ; NecPaciente i - MediaEspec )) / SOMA(NecPaciente i * Peso i) ]
			 * 
			 * essa f�rmula equivale a:
			 * 
			 * G.I. = [SOMA(NecPaciente i * Peso i) - SOMA(PESO i * {MAX(0 ; NecPaciente i - MediaEspec))) / SOMA(NecPaciente i * Peso i)]
			 * 
			 */
			conjuntoAvaliacao.setGrauInclusao((conjuntoAvaliacao.getSomatorioNecessidadeDoPaciente() - conjuntoAvaliacao.getSomatorioDistancia()) / conjuntoAvaliacao.getSomatorioNecessidadeDoPaciente());
			
			/*
			 * No final, o processo estar� terminado para a Indica��o atual no loop e
			 * os dados estar�o salvos na inst�ncia atual do objeto conjuntoAvaliacao.
			 * 
			 * Ent�o, esse objeto ser� salvo numa lista para que as demais indica��es
			 * sejam tratadas.
			 */
			conjuntosDeAvaliacoes.add(conjuntoAvaliacao);
		}
		/*
		 * Reordena a lista de forma que as indica��es fiquem rankeadas corretamente.
		 */
		Collections.sort(conjuntosDeAvaliacoes);
		/*
		 * Ap�s reordenar a lista na ordem correta do ranking, este percorre cada avalia��o
		 * de cada indica��o e seta o seu Ranking.
		 * 
		 * Acontece que o collections.sort apenas coloca as avalia��es na ordem correta, o que o
		 * for faz � setar explicitamente um valor inteiro no atributo Ranking que representa a posi��o
		 * desta avalia��o no ranking.
		 * 
		 * O atributo � setado com i+1, j� que o loop inicia em zero.
		 */
		for(int i = 0; i < conjuntosDeAvaliacoes.size(); i++){
			ConjuntoAvaliacao conjuntoAvaliacao = conjuntosDeAvaliacoes.get(i);
			conjuntoAvaliacao.setRanking(i+1);
		}		
		return conjuntosDeAvaliacoes;
	}
	
	/**
	 * Calcula uma avalia��o dado o atendimento, indicacao e o parametro, 
	 * utilizando o algoritimo do grau de inclus�o
	 * @param atendimento
	 * @param indicacao
	 * @param parametro
	 * @return
	 */
	public Avaliacao calculaAvaliacaoPorAtendimentoPorIndicacaoPorParametroPeloGrauDeInclusao
									(Atendimento atendimento, Indicacao indicacao, Parametro parametro){

		Anamnese anamneseCorrente = null;
		/*
		 * Recupera a anamnese de um paramentro em um atendimento espec�fico.
		 */
		try {
			anamneseCorrente = anamneseDAO.recuperaAnamnesePorAtendimentoPorParametro(atendimento, parametro);
		} catch (ObjetoNaoEncontradoException e) {
			e.printStackTrace();
		}
		
		/*
		 *  Calcula a media de todos os especialistas
		 */
		Double mediaValorEspecialistas = 
				avalIndicacaoEspecService.calculaMediaAvaliacaoEspecialistasPorIndicacaoPorParametro(indicacao, parametro);
		

		Avaliacao avaliacaoCorrente = new Avaliacao();
		avaliacaoCorrente.setParametro(parametro);
		avaliacaoCorrente.setIndicacao(indicacao);
		avaliacaoCorrente.setMediaEspecialistas(mediaValorEspecialistas);
		
		Double valorDistancia = 0.0;
		// Necessidade do paciente - m�dia da avalia��o dos especialistas
		valorDistancia = anamneseCorrente.getValor() - mediaValorEspecialistas;
		// Realiza a verifica��o do m�ximo entre Zero e o resultado da dist�ncia.
		valorDistancia = Math.max(0, valorDistancia);
		avaliacaoCorrente.setDistancia(valorDistancia);

		return avaliacaoCorrente;
	}	

	@Transacional
	public void exclui(Anamnese anamnese) throws AplicacaoException {
		Anamnese anamneseBD = null;
		try {
			anamneseBD = anamneseDAO.getPorIdComLock(anamnese.getId());
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("anamnese.NAO_ENCONTRADA");
		}
		anamneseDAO.exclui(anamneseBD);
	}

	@Transacional
	public void inclui(Anamnese anamnese) throws AplicacaoException {
		try {
			anamneseDAO.recuperaAnamnesePorAtendimentoPorParametro(anamnese
					.getAtendimento(), anamnese.getParametro());
			throw new AplicacaoException("anamnese.ATENDIMENTO_POSSUI_ANAMNESE");
		} catch (ObjetoNaoEncontradoException ob) {
		}
		anamneseDAO.inclui(anamnese);
	}
	
	/**
	 * Utilizando todos os parametros disponiveis gera uma lista de anamneses
	 * para um dado atendimento.
	 * com os dados de parametros j� preenchidos (para cada parametro no banco).
	 * Esses objetos n�o foram persistidos ainda!
	 * A ideia � que possam ser usados em conjunto com um atendimento n�o persistido
	 * em banco tamb�m.
	 * 
	 * @return
	 */
	public List<Anamnese> geraListaDeAnamnesesPadroesParaAtendimento(Atendimento atendimento){
		List<Parametro> parametros = parametroDAO.recuperaListaDeParametros();
		
		List<Anamnese> anamneses = new ArrayList<Anamnese>();
		for (Parametro parametro : parametros) {
			Anamnese anamnese = new Anamnese(atendimento, parametro, 0.0);
			anamneses.add(anamnese);
		}
		return anamneses;
	}

	public List<Anamnese> recuperaListaDeAnamnesePorAtendimento(Atendimento atendimento) {
		return anamneseDAO.recuperaListaDeAnamnesePorAtendimento(atendimento);
	}
	
	public List<Anamnese> recuperaListaDeAnamnesePorParametro(Parametro parametro) {
		return anamneseDAO.recuperaListaDeAnamnesePorParametro(parametro);
	}
	
	public Anamnese recuperaAnamnesePorAtendimentoPorParametro(Atendimento atendimento, Parametro parametro) throws AplicacaoException{
		try {
			return anamneseDAO.recuperaAnamnesePorAtendimentoPorParametro(atendimento, parametro);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("anamnese.NAO_ENCONTRADA");
		}
	}
}
