package service;

import java.util.ArrayList;
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

	public AnamneseAppService() throws Exception {
		try {
			anamneseDAO = FabricaDeDao.getDao(AnamneseDAOImpl.class);
			avalIndicacaoEspecDAO = FabricaDeDao
					.getDao(AvalIndicacaoEspecDAOImpl.class);
			indicacaoDAO = FabricaDeDao.getDao(IndicacaoDAOImpl.class);
			parametroDAO = FabricaDeDao.getDao(ParametroDAOImpl.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Transacional
	public void altera(Anamnese anamnese) throws AplicacaoException {
		anamneseDAO.altera(anamnese);
	}


	public Avaliacao calculaAvaliacaoPorAtendimentoPorIndicacaoPorParametro(
			Atendimento atendimento, Indicacao indicacao, Parametro parametro) {
		
		Anamnese anamneseCorrente = null;
		try {
			anamneseCorrente = anamneseDAO.recuperaAnamnesePorAtendimentoPorParametro(atendimento, parametro);
		} catch (ObjetoNaoEncontradoException e) {
			e.printStackTrace();
		}
		
		List<AvalIndicacaoEspec> listAvalIndicacaoEspec = avalIndicacaoEspecDAO
				.recuperaAvaliacaoPorIndicacaoParametro(indicacao, parametro);

		Double somatorioValorEspecialistas = 0.0;
		Double mediaValorEspecialistas = 0.0;
		Double mediaPesoAvaliador = 0.0;
		for (AvalIndicacaoEspec avalIndicacaoEspec : listAvalIndicacaoEspec) {
			mediaPesoAvaliador += avalIndicacaoEspec.getEspecialista().getPesoAvaliador();
			somatorioValorEspecialistas += avalIndicacaoEspec.getValor() * avalIndicacaoEspec.getEspecialista().getPesoAvaliador();
			
		}
		mediaValorEspecialistas = somatorioValorEspecialistas/mediaPesoAvaliador;
		

		Avaliacao avaliacaoCorrente = new Avaliacao();
		avaliacaoCorrente.setParametro(parametro);
		avaliacaoCorrente.setIndicacao(indicacao);
		avaliacaoCorrente.setIntersecao(Math.min(mediaValorEspecialistas, anamneseCorrente.getValor()));
		avaliacaoCorrente.setUniao(Math.max(mediaValorEspecialistas, anamneseCorrente.getValor()));
		return avaliacaoCorrente;
	}
	
	
	
	public List<ConjuntoAvaliacao> recuperaAvaliacaoCalculadaPorIndicacao(
			Atendimento atendimento) {


		List<Indicacao> listIndicacao = indicacaoDAO.recuperaListaIndicacao();
		List<Parametro> listParametro = parametroDAO.recuperaListaDeParametros();


		List<ConjuntoAvaliacao> conjuntosDeAvaliacoes = new ArrayList<ConjuntoAvaliacao>();
		
		for (Indicacao indicacao : listIndicacao) {
			ConjuntoAvaliacao conjuntoAvaliacao = new ConjuntoAvaliacao();
			conjuntoAvaliacao.setIndicacao(indicacao);
			

			List<Avaliacao> listAvaliacao = new ArrayList<Avaliacao>();
			for (Parametro parametro : listParametro) {
				Avaliacao avaliacaoCorrente = calculaAvaliacaoPorAtendimentoPorIndicacaoPorParametro(atendimento, indicacao, parametro);
				listAvaliacao.add(avaliacaoCorrente);
			}
			
			conjuntoAvaliacao.setAvaliacoes(listAvaliacao);
			conjuntoAvaliacao.setSomatorioIntersecao(conjuntoAvaliacao.somaParametrosIntersecao());
			conjuntoAvaliacao.setSomatorioUniao(conjuntoAvaliacao.somaParametrosUniao());
			conjuntoAvaliacao.setGrauSemelhanca(conjuntoAvaliacao.getSomatorioIntersecao() / conjuntoAvaliacao.getSomatorioUniao());
			conjuntosDeAvaliacoes.add(conjuntoAvaliacao);
		}
		return conjuntosDeAvaliacoes;
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
	
	public Anamnese recuperaAnamnesePorAtendimentoPorParametro(Atendimento atendimento, Parametro parametro) throws AplicacaoException{
		try {
			return anamneseDAO.recuperaAnamnesePorAtendimentoPorParametro(atendimento, parametro);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("anamnese.NAO_ENCONTRADA");
		}
	}
}
