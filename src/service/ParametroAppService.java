package service;

import java.util.List;

import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.Parametro;
import service.anotacao.Transacional;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import DAO.EspecialistaDAO;
import DAO.IndicacaoDAO;
import DAO.ParametroDAO;
import DAO.Impl.EspecialistaDAOImpl;
import DAO.Impl.IndicacaoDAOImpl;
import DAO.Impl.ParametroDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;

public class ParametroAppService {
	
	private static ParametroDAO parametroDAO;
	private static EspecialistaDAO especialistaDAO;
	private static IndicacaoDAO indicacaoDAO;
	
	private static AvalIndicacaoEspecAppService avalIndicacaoEspecService;
	
	public ParametroAppService() throws Exception{
		try{
			parametroDAO = FabricaDeDao.getDao(ParametroDAOImpl.class);
			especialistaDAO = FabricaDeDao.getDao(EspecialistaDAOImpl.class);
			indicacaoDAO = FabricaDeDao.getDao(IndicacaoDAOImpl.class);
			
			avalIndicacaoEspecService = FabricaDeAppService.getAppService(AvalIndicacaoEspecAppService.class);
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);	
		}
	}
	
	@Transacional
	public void inclui(Parametro parametro) throws AplicacaoException{
		try{
			parametroDAO.recuperaParametroPorCodigo(parametro.getCodParametro());
			throw new AplicacaoException("parametro.CODIGO_EXISTENTE");
		}catch(ObjetoNaoEncontradoException ob){
		}
		try{
			parametroDAO.recuperaParametroPorNome(parametro.getNome());
			throw new AplicacaoException("parametro.NOME_EXISTENTE");
		}catch(ObjetoNaoEncontradoException ob){
		}
		parametroDAO.inclui(parametro);
		
		List<Especialista> especialistaBD = especialistaDAO.recuperaListaEspecialista();
		List<Indicacao> indicacaoBD = indicacaoDAO.recuperaListaIndicacao();
		
		if(!especialistaBD.isEmpty() && !indicacaoBD.isEmpty()){
			for(Especialista especialista : especialistaBD){
				for(Indicacao indicacao : indicacaoBD){
				
				AvalIndicacaoEspec avalIndicacaoEspec = new AvalIndicacaoEspec();
				
				avalIndicacaoEspec.setValor(0);
				
				avalIndicacaoEspec.setEspecialista(especialista);
				avalIndicacaoEspec.setParametro(parametro);
				avalIndicacaoEspec.setIndicacao(indicacao);
				
				avalIndicacaoEspecService.inclui(avalIndicacaoEspec);
				}
			}
		} 
	}

	@Transacional
	public void altera(Parametro parametro) throws AplicacaoException {
		parametroDAO.altera(parametro);
	}

	public List<Parametro> recuperaListaDeParametrosPaginada(){
		return parametroDAO.recuperaListaDeParametrosPaginada();
	}
	
	public Parametro recuperaParametroPorCodigo(String codigo) throws AplicacaoException{
		Parametro parametro = null;
		try {
			parametro = parametroDAO.recuperaParametroPorCodigo(codigo);
		} catch (ObjetoNaoEncontradoException ob) {
			throw new AplicacaoException("parametro.CODIGO_NAO_ENCONTRADO");
		}
		return parametro;
	}
	
	public List<Parametro> recuperaListaDeParametros(){
		return parametroDAO.recuperaListaDeParametros();
	}
}
