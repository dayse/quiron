package service;

import java.util.List;

import service.anotacao.Transacional;
import service.exception.AplicacaoException;
import DAO.AvalIndicacaoEspecDAO;
import DAO.EspecialistaDAO;
import DAO.IndicacaoDAO;
import DAO.Impl.AvalIndicacaoEspecDAOImpl;
import DAO.Impl.EspecialistaDAOImpl;
import DAO.Impl.IndicacaoDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;
import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;

public class EspecialistaAppService {

	private static EspecialistaDAO especialistaDAO;
	private static IndicacaoDAO indicacaoDAO;
	private static AvalIndicacaoEspecDAO avalIndicacaoEspecDAO;

	public EspecialistaAppService() throws Exception {
		try {
			especialistaDAO = FabricaDeDao.getDao(EspecialistaDAOImpl.class);
			indicacaoDAO = FabricaDeDao.getDao(IndicacaoDAOImpl.class);
			avalIndicacaoEspecDAO = FabricaDeDao
					.getDao(AvalIndicacaoEspecDAOImpl.class);
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
		if(!indicacaoBD.isEmpty()){
			for(Indicacao indicacao : indicacaoBD){
				AvalIndicacaoEspec avalIndicacaoEspec = new AvalIndicacaoEspec();
				avalIndicacaoEspec.setEspecialista(especialista);
				avalIndicacaoEspec.setIndicacao(indicacao);
				avalIndicacaoEspecDAO.inclui(avalIndicacaoEspec);
			}
		}
	}

	public List<Especialista> recuperaListaDeEspecialistasPaginada() {
		return especialistaDAO.recuperaListaDeEspecialistasPaginada();
	}
	
	public Double recuperaMediaDoPesoAvaliadorDosEspecialistas(){
		return especialistaDAO.recuperaMediaDoPesoAvaliadorDosEspecialistas();
	}
}
