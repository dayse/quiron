package service;

import java.util.List;

import modelo.AvalIndicacaoEspec;
import modelo.Avaliacao;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.Parametro;
import service.anotacao.Transacional;
import service.exception.AplicacaoException;
import DAO.AvalIndicacaoEspecDAO;
import DAO.Impl.AvalIndicacaoEspecDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;

public class AvalIndicacaoEspecAppService {

	private static AvalIndicacaoEspecDAO avalIndicacaoEspecDAO;
	
	public AvalIndicacaoEspecAppService() throws Exception{
		try{
			avalIndicacaoEspecDAO = FabricaDeDao.getDao(AvalIndicacaoEspecDAOImpl.class);
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
	
	public Avaliacao calculaAvaliacaoPorIndicacaoPorParametro(Indicacao indicacao, Parametro parametro) {
		Double mediaValorEspecialistas = calculaMediaAvaliacaoEspecialistasPorIndicacaoPorParametro(indicacao, parametro);
		

		Avaliacao avaliacaoCorrente = new Avaliacao();
		avaliacaoCorrente.setParametro(parametro);
		avaliacaoCorrente.setIndicacao(indicacao);
		avaliacaoCorrente.setIntersecao(mediaValorEspecialistas);
		avaliacaoCorrente.setUniao(mediaValorEspecialistas);
		return avaliacaoCorrente;
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
