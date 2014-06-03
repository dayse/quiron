package service;

import java.util.List;

import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.Parametro;
import service.anotacao.Transacional;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import DAO.AvalIndicacaoEspecDAO;
import DAO.EspecialistaDAO;
import DAO.IndicacaoDAO;
import DAO.ParametroDAO;
import DAO.Impl.AvalIndicacaoEspecDAOImpl;
import DAO.Impl.EspecialistaDAOImpl;
import DAO.Impl.IndicacaoDAOImpl;
import DAO.Impl.ParametroDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;

public class EspecialistaAppService {

	private static EspecialistaDAO especialistaDAO;
	private static IndicacaoDAO indicacaoDAO;
	private static ParametroDAO parametroDAO;
	
	private static AvalIndicacaoEspecAppService avalIndicacaoEspecService;

	public EspecialistaAppService() throws Exception {
		try {
			especialistaDAO = FabricaDeDao.getDao(EspecialistaDAOImpl.class);
			indicacaoDAO = FabricaDeDao.getDao(IndicacaoDAOImpl.class);
			parametroDAO = FabricaDeDao.getDao(ParametroDAOImpl.class);
			
			avalIndicacaoEspecService = FabricaDeAppService.getAppService(AvalIndicacaoEspecAppService.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Transacional
	public void altera(Especialista especialista) throws AplicacaoException {
		especialistaDAO.altera(especialista);
	}

	@Transacional
	public void exclui(Especialista especialista) throws AplicacaoException {
		Especialista especialistaBD = null;
		try {
			especialistaBD = especialistaDAO.getPorIdComLock(especialista
					.getId());
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("especialista.NAO_ENCONTRADO");
		}
		especialistaDAO.exclui(especialistaBD);
	}

	@Transacional
	public void inclui(Especialista especialista) throws AplicacaoException {
		try {
			especialistaDAO.recuperaEspecialistaPorCodigo(especialista
					.getCodEspecialista());
			throw new AplicacaoException("especialista.CODIGO_EXISTENTE");
		} catch (ObjetoNaoEncontradoException ob) {
		}
		especialistaDAO.inclui(especialista);

		List<Indicacao> indicacaoBD = indicacaoDAO.recuperaListaIndicacao();
		List<Parametro> parametroBD = parametroDAO.recuperaListaDeParametros();
		
		if(!indicacaoBD.isEmpty() && !parametroBD.isEmpty()){
			for(Indicacao indicacao : indicacaoBD){
				for(Parametro parametro : parametroBD){
				
				AvalIndicacaoEspec avalIndicacaoEspec = new AvalIndicacaoEspec();
				
				avalIndicacaoEspec.setValor(0);
				
				avalIndicacaoEspec.setEspecialista(especialista);
				avalIndicacaoEspec.setIndicacao(indicacao);
				avalIndicacaoEspec.setParametro(parametro);
				
				avalIndicacaoEspecService.inclui(avalIndicacaoEspec);
				}
			}
		} 
	}

	public List<Especialista> recuperaListaDeEspecialistasPaginada() {
		return especialistaDAO.recuperaListaDeEspecialistasPaginada();
	}
	
	public Double recuperaMediaDoPesoAvaliadorDosEspecialistas(){
		return especialistaDAO.recuperaMediaDoPesoAvaliadorDosEspecialistas();
	}


	public Especialista recuperaEspecialistaPorCodigo(String codEspecialista) throws ObjetoNaoEncontradoException {
		return especialistaDAO.recuperaEspecialistaPorCodigo(codEspecialista);
	}
}
