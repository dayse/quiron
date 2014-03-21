package service;

import java.util.List;

import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;
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
		avalIndicacaoEspecDAO.altera(avalIndicacaoEspec);
	}
	
	@Transacional
	public void exclui(AvalIndicacaoEspec avalIndicacaoEspec) throws AplicacaoException{
		AvalIndicacaoEspec avalIndicacaoEspecBD = null;
		try{
			avalIndicacaoEspecBD = avalIndicacaoEspecDAO.getPorIdComLock(avalIndicacaoEspec.getId());
		}catch(ObjetoNaoEncontradoException e){
			throw new AplicacaoException("avalIndicacaoEspec.AVALIACAO_NAO_ENCONTRADA");
		}
		avalIndicacaoEspecDAO.exclui(avalIndicacaoEspecBD);
	}
	
	@Transacional
	public void inclui(AvalIndicacaoEspec avalIndicacaoEspec) throws AplicacaoException{
		try{
			avalIndicacaoEspecDAO.recuperaAvalIndicacaoEspecPorID(avalIndicacaoEspec.getIndicacao(), avalIndicacaoEspec.getEspecialista());
			throw new AplicacaoException("avalIndicacaoEspec.AVALIACAO_EXISTENTE");
		}catch(ObjetoNaoEncontradoException ob){
		}
		avalIndicacaoEspecDAO.inclui(avalIndicacaoEspec);
	}
	
	public List<AvalIndicacaoEspec> recuperaListaDeAvaliacaoEspecPaginada(Especialista especialista){
		return avalIndicacaoEspecDAO.recuperaListaDeAvaliacaoEspecPaginada(especialista);
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
	
	public Double recuperaMediaDoPesoAvaliadorDosEspecialistas(){
		return recuperaMediaDoPesoAvaliadorDosEspecialistas();
	}
}
