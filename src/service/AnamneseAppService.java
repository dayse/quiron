package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public AnamneseAppService() throws Exception {
		try {
			anamneseDAO = FabricaDeDao.getDao(AnamneseDAOImpl.class);
			avalIndicacaoEspecDAO = FabricaDeDao
					.getDao(AvalIndicacaoEspecDAOImpl.class);
			indicacaoDAO = FabricaDeDao.getDao(IndicacaoDAOImpl.class);
			parametroDAO = FabricaDeDao.getDao(ParametroDAOImpl.class);
			
			avalIndicacaoEspecService = FabricaDeAppService.getAppService(AvalIndicacaoEspecAppService.class);
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
	 * Calculo da avaliação por atendimento, por indicação e por parametro, utilizando
	 * um algoritmo de Grau de Semelhança.
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
			anamneseCorrente = anamneseDAO.recuperaAnamnesePorAtendimentoPorParametro(atendimento, parametro);
		} catch (ObjetoNaoEncontradoException e) {
			e.printStackTrace();
		}
		
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
	 * Recupera avaliação calcula por uma indicação utilizando o algoritmo de grau de 
	 * semelhança.
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
		//ordena e poem ranking
		Collections.sort(conjuntosDeAvaliacoes);
		for (int i = 0; i < conjuntosDeAvaliacoes.size(); i++) {
			ConjuntoAvaliacao conjuntoAvaliacao = conjuntosDeAvaliacoes.get(i);
			conjuntoAvaliacao.setRanking(i+1);
		}
		return conjuntosDeAvaliacoes;
	}

	/**
	 * Recupera a media de avaliação de indicação dos especialistas.
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
			//usando apenas a união que é a media dos especialistas para um determinado parametro para uma indicacao
			conjuntoAvaliacao.setSomatorioUniao(conjuntoAvaliacao.somaParametrosUniao());
			conjuntosDeAvaliacoes.add(conjuntoAvaliacao);
		}
		return conjuntosDeAvaliacoes;
	}
	
	/**
	 * Recupera a avaliação calculada por indicação dos especialistas para um determinado
	 * atendimento, utilizando o algoritmo da Distancia de Descartes
	 * @param atendimento
	 * @return
	 */
	public List<ConjuntoAvaliacao> recuperaAvaliacaoCalculadaPorIndicacaoPelaDistanciaDescartes(Atendimento atendimento){

		List<Indicacao> listIndicacao = indicacaoDAO.recuperaListaIndicacao();
		List<Parametro> listParametro = parametroDAO.recuperaListaDeParametros();

		List<ConjuntoAvaliacao> conjuntosDeAvaliacoes = new ArrayList<ConjuntoAvaliacao>();
		
		/* 
		 * Para cada indicação é criado um objeto do tipo ConjuntoAvaliacao,
		 * que é um objeto preparado para ser manipulado no contexto do algoritmo.
		 * Sendo assim, a indicação é setada em seu respectivo objeto ConjuntoAvaliacao.
		 */
		for (Indicacao indicacao : listIndicacao) {
			ConjuntoAvaliacao conjuntoAvaliacao = new ConjuntoAvaliacao();
			conjuntoAvaliacao.setIndicacao(indicacao);
			
			/*
			 *  Em seguida, calcula a avaliação de cada Indicacação por paremetro,
			 *  tendo em vista os dados de anamnese no atendimento.
			 *  
			 *  Este loop irá gerar a avaliação de todas os parametros em comparação
			 *  a indicação atual do loop anterior.
			 */
			List<Avaliacao> listAvaliacao = new ArrayList<Avaliacao>();
			for (Parametro parametro : listParametro) {
				Avaliacao avaliacaoCorrente = calculaAvaliacaoPorAtendimentoPorIndicacaoPorParametroPelaDistanciaDescartes(atendimento, indicacao, parametro);
				listAvaliacao.add(avaliacaoCorrente);
			}
			/*
			 * Lembrando que ainda não concluimos o loop de indicação,
			 * o conjunAvaliacao da indicacao atual dentro do loop irá receber
			 * a lista de avaliações de todos os parametros nessa indicação. 
			 */
			conjuntoAvaliacao.setAvaliacoes(listAvaliacao);
			
			/*
			 * Setamos o atributo SomatorioDistancia fazendo uma chamada do método
			 * somaParametrosDistancia do mesmo objeto.
			 * 
			 * O somaParamnetrosDistancia é um método que percorre a lista de avaliações
			 * previamente setada e para cada avaliação pega a distância e multiplica
			 * pelo peso do parametro.
			 */
			conjuntoAvaliacao.setSomatorioDistancia(conjuntoAvaliacao.somaParametrosDistancia());
			
			/*
			 * Setamos o atributo SometorioPesosParametos fazendo uma chama do método
			 * somaPesosParametros do mesmo objeto.
			 * 
			 * O somaPesosParametros é um método que percorre a lista de avaliações 
			 * previamente setada e para cada avaliação pega o peso do parametro e
			 * soma ao total de pesos de parametros.
			 */
			conjuntoAvaliacao.setSomatorioPesosParametros(conjuntoAvaliacao.somaPesosParametros());
			
			/*
			 * Setamos o atributo DistanciaDescartes com o resultado da divisão do Somatório
			 * das distâncias previamente calculadas divido pelo somatório dos pesos dos parametros
			 * previamente setados.
			 */
			conjuntoAvaliacao.setDistanciaDescartes(conjuntoAvaliacao.getSomatorioDistancia() / conjuntoAvaliacao.getSomatorioPesosParametros());
			
			/*
			 * No final, o processo estará terminado para a Indicação atual no loop e
			 * os dados estarão salvos na instância atual do objeto conjuntoAvaliacao.
			 * 
			 * Então, esse objeto será salvo numa lista para que as demais indicações
			 * sejam tratadas.
			 */
			conjuntosDeAvaliacoes.add(conjuntoAvaliacao);
		}
		/*
		 * Reordena a lista de forma que as indicações fiquem rankeadas corretamente.
		 */
		Collections.sort(conjuntosDeAvaliacoes);
		/*
		 * Após reordenar a lista na ordem correta do ranking, este percorre cada avaliação
		 * de cada indicação e seta o seu Ranking.
		 * 
		 * Acontece que o collections.sort apenas coloca as avaliações na ordem correta, o que o
		 * for faz é setar explicitamente um valor inteiro no atributo Ranking que representa a posição
		 * desta avaliação no ranking.
		 * 
		 * O atributo é setado com i+1, já que o loop inicia em zero.
		 */
		for(int i = 0; i < conjuntosDeAvaliacoes.size(); i++){
			ConjuntoAvaliacao conjuntoAvaliacao = conjuntosDeAvaliacoes.get(i);
			conjuntoAvaliacao.setRanking(i+1);
		}
		return conjuntosDeAvaliacoes;
	}
	
	/**
	 * Calcula uma avaliação dado o atendimento, indicacao e o parametro, 
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
		 * Recupera a anamnese de um paramentro em um atendimento específico.
		 */
		try {
			anamneseCorrente = anamneseDAO.recuperaAnamnesePorAtendimentoPorParametro(atendimento, parametro);
		} catch (ObjetoNaoEncontradoException e) {
			e.printStackTrace();
		}
		
		/*
		 *  
		 */
		Double mediaValorEspecialistas = 
				avalIndicacaoEspecService.calculaMediaAvaliacaoEspecialistasPorIndicacaoPorParametro(indicacao, parametro);
		

		Avaliacao avaliacaoCorrente = new Avaliacao();
		avaliacaoCorrente.setParametro(parametro);
		avaliacaoCorrente.setIndicacao(indicacao);
		avaliacaoCorrente.setMediaEspecialistas(mediaValorEspecialistas);
		
		Double valorDistancia = 0.0;
		valorDistancia = anamneseCorrente.getValor() - mediaValorEspecialistas;
		valorDistancia = Math.abs(valorDistancia);
		//o parametro não penaliza se ultrapassar a necessidade do paciente
		if(parametro.getTipo().equals(Parametro.TIPO_PODE_EXCEDER)){
			valorDistancia = Math.max(0, valorDistancia);		
		}
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
	 * com os dados de parametros já preenchidos (para cada parametro no banco).
	 * Esses objetos não foram persistidos ainda!
	 * A ideia é que possam ser usados em conjunto com um atendimento não persistido
	 * em banco também.
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
