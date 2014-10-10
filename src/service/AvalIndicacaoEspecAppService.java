package service;

import java.util.ArrayList;
import java.util.List;

import modelo.AvalIndicacaoEspec;
import modelo.Avaliacao;
import modelo.ConjuntoAvaliacao;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.Parametro;
import service.anotacao.Transacional;
import service.exception.AplicacaoException;
import DAO.AvalIndicacaoEspecDAO;
import DAO.IndicacaoDAO;
import DAO.ParametroDAO;
import DAO.Impl.AvalIndicacaoEspecDAOImpl;
import DAO.Impl.IndicacaoDAOImpl;
import DAO.Impl.ParametroDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;

public class AvalIndicacaoEspecAppService {

	private static AvalIndicacaoEspecDAO avalIndicacaoEspecDAO;
	private static IndicacaoDAO indicacaoDAO;
	private static ParametroDAO parametroDAO;
	
	public AvalIndicacaoEspecAppService() throws Exception{
		try{
			avalIndicacaoEspecDAO = FabricaDeDao.getDao(AvalIndicacaoEspecDAOImpl.class);
			indicacaoDAO = FabricaDeDao.getDao(IndicacaoDAOImpl.class);
			parametroDAO = FabricaDeDao.getDao(ParametroDAOImpl.class);
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Transacional
	public void altera(AvalIndicacaoEspec avalIndicacaoEspec) throws AplicacaoException{
		AvalIndicacaoEspec avaliacao = null;
		try {
			avaliacao = avalIndicacaoEspecDAO.getPorIdComLock(avalIndicacaoEspec.getId());
			avaliacao.setValor(avalIndicacaoEspec.getValor());
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("avaliacaoDaIndicacao.NAO_ENCONTRADA");
		}
		avalIndicacaoEspecDAO.altera(avaliacao);
	}
	
	@Transacional
	public void exclui(AvalIndicacaoEspec avalIndicacaoEspec) throws AplicacaoException{
		AvalIndicacaoEspec avalIndicacaoEspecBD = null;
		try{
			avalIndicacaoEspecBD = avalIndicacaoEspecDAO.getPorIdComLock(avalIndicacaoEspec.getId());
		}catch(ObjetoNaoEncontradoException e){
			throw new AplicacaoException("avaliacaoDaIndicacao.NAO_ENCONTRADA");
		}
		avalIndicacaoEspecDAO.exclui(avalIndicacaoEspecBD);
	}
	
	@Transacional
	public void inclui(AvalIndicacaoEspec avalIndicacaoEspec) throws AplicacaoException{
		try{
			avalIndicacaoEspecDAO.recuperaAvaliacaoPorEspecialistaIndicacaoParametro
									(avalIndicacaoEspec.getEspecialista(), avalIndicacaoEspec.getIndicacao(), avalIndicacaoEspec.getParametro());
			throw new AplicacaoException("avaliacaoDaIndicacao.AVALIACAO_JA_EXISTENTE");
		}catch(ObjetoNaoEncontradoException ob){			
		}
		avalIndicacaoEspecDAO.inclui(avalIndicacaoEspec);
	}
	

	/**
	 * Metodo usado para calcular a media da avaliação dos especialistas para uma
	 * determinada combinação de indicação e parametro.
	 * 
	 * @param indicacao
	 * @param parametro
	 * @return
	 */
	public Double calculaMediaAvaliacaoEspecialistasPorIndicacaoPorParametro(Indicacao indicacao, Parametro parametro) {

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
		return mediaValorEspecialistas;
	}
	
	/**
	 * Calcula a uma avaliação para uma combinação de indicação e parametro.
	 * 
	 * @param indicacao
	 * @param parametro
	 * @return
	 */
	public Avaliacao calculaAvaliacaoPorIndicacaoPorParametro(Indicacao indicacao, Parametro parametro) {
		Double mediaValorEspecialistas = calculaMediaAvaliacaoEspecialistasPorIndicacaoPorParametro(indicacao, parametro);
		

		Avaliacao avaliacaoCorrente = new Avaliacao();
		avaliacaoCorrente.setParametro(parametro);
		avaliacaoCorrente.setIndicacao(indicacao);
		avaliacaoCorrente.setIntersecao(mediaValorEspecialistas);
		avaliacaoCorrente.setUniao(mediaValorEspecialistas);
		return avaliacaoCorrente;
	}

	/**
	 * Metodo utilizado para achar a media de Avaliação de Indicação de todos os especialistas.
	 * Esse metodo é utilizado na tela de media dos especialistas
	 * 
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
			for (Parametro parametro : listParametro) {
				Avaliacao avaliacaoCorrente = calculaAvaliacaoPorIndicacaoPorParametro(indicacao, parametro);
				listAvaliacao.add(avaliacaoCorrente);
			}
			
			conjuntoAvaliacao.setAvaliacoes(listAvaliacao);
			conjuntosDeAvaliacoes.add(conjuntoAvaliacao);
		}
		return conjuntosDeAvaliacoes;
	}

	
	public AvalIndicacaoEspec recuperaAvaliacaoPorEspecialistaIndicacaoParametro(Especialista especialista, Indicacao indicacao, Parametro parametro)throws ObjetoNaoEncontradoException{
		return avalIndicacaoEspecDAO.recuperaAvaliacaoPorEspecialistaIndicacaoParametro(especialista, indicacao, parametro);
	}
	
	public List<AvalIndicacaoEspec> recuperaListaDeAvaliacaoEspecPorEspecialistaPaginada(Especialista especialista){
		return avalIndicacaoEspecDAO.recuperaListaDeAvaliacaoEspecPorEspecialistaPaginada(especialista);
	}
	
	public List<AvalIndicacaoEspec> recuperaListaDeAvaliacaoEspecComIndicacaoDeUmEspec(Especialista especialista){
		return avalIndicacaoEspecDAO.recuperaListaDeAvaliacaoEspecComIndicacaoDeUmEspec(especialista);
	}
	
	public List<AvalIndicacaoEspec> recuperaListaDeAvaliacaoEspecComIndicacaoComEspec(){
		return avalIndicacaoEspecDAO.recuperaListaDeAvaliacaoEspecComIndicacaoComEspec();
	}
	
	public List<AvalIndicacaoEspec> recuperaListaDeAvaliacaoEspecPorIndicacao(Indicacao indicacao){
		return avalIndicacaoEspecDAO.recuperaListaDeAvaliacaoEspecPorIndicacao(indicacao);
	}

	public List<AvalIndicacaoEspec> recuperaListaDeAvaliacaoEspecPorParametro(Parametro parametro){
		return avalIndicacaoEspecDAO.recuperaListaDeAvaliacaoEspecPorParametro(parametro);
	}
	public Double recuperaMediaDoPesoAvaliadorDosEspecialistas(){
		return recuperaMediaDoPesoAvaliadorDosEspecialistas();
	}

	public List<AvalIndicacaoEspec> recuperaListaDeAvaliacaoPorEspecialistaPorIndicacao(
										Especialista especialista, Indicacao indicacao){
		return avalIndicacaoEspecDAO.recuperaListaDeAvaliacaoPorEspecialistaPorIndicacao(especialista, indicacao);
	}

	public List<AvalIndicacaoEspec> recuperaAvaliacaoPorIndicacaoParametro(
										Indicacao indicacao, Parametro parametro){
		return avalIndicacaoEspecDAO.recuperaAvaliacaoPorIndicacaoParametro(indicacao, parametro);
	}
	
}
