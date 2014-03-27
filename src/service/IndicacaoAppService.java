package service;

import java.util.List;

import service.anotacao.Transacional;
import service.exception.AplicacaoException;

import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;

import DAO.AvalIndicacaoEspecDAO;
import DAO.EspecialistaDAO;
import DAO.IndicacaoDAO;
import DAO.Impl.AvalIndicacaoEspecDAOImpl;
import DAO.Impl.EspecialistaDAOImpl;
import DAO.Impl.IndicacaoDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;

/**
 * 
 * @author bruno.oliveira
 * 
 */
public class IndicacaoAppService {

	private static IndicacaoDAO indicacaoDAO;
	private static EspecialistaDAO especialistaDAO;
	private static AvalIndicacaoEspecDAO avalIndicacaoEspecDAO;

	public IndicacaoAppService() throws Exception {
		try {
			indicacaoDAO = FabricaDeDao.getDao(IndicacaoDAOImpl.class);
			especialistaDAO = FabricaDeDao.getDao(EspecialistaDAOImpl.class);
			avalIndicacaoEspecDAO = FabricaDeDao.getDao(AvalIndicacaoEspecDAOImpl.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Transacional
	public void altera(Indicacao indicacao) throws AplicacaoException {
		indicacaoDAO.altera(indicacao);
	}

	@Transacional
	public void exclui(Indicacao indicacao) throws AplicacaoException {
		Indicacao medicamentoBD = null;
		try {
			medicamentoBD = indicacaoDAO.getPorIdComLock(indicacao.getId());
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("medicamento.NAO_ENCONTRADO");
		}
		indicacaoDAO.exclui(medicamentoBD);
	}

	@Transacional
	public void inclui (Indicacao indicacao) throws AplicacaoException{
		try{
			indicacaoDAO.recuperaMedicamentoPorCodigo(indicacao.getCodIndicacao());
			throw new AplicacaoException("medicamento.CODIGO_EXISTENTE");
		}catch(ObjetoNaoEncontradoException ob){
			
		}
		indicacaoDAO.inclui(indicacao);
		
		List<Especialista> especialistaBD = especialistaDAO.recuperaListaEspecialista();
		if(!especialistaBD.isEmpty()){
			for(Especialista especialista : especialistaBD){
				AvalIndicacaoEspec avalIndicacaoEspec = new AvalIndicacaoEspec();
				avalIndicacaoEspec.setIndicacao(indicacao);
				avalIndicacaoEspec.setEspecialista(especialista);
				avalIndicacaoEspecDAO.inclui(avalIndicacaoEspec);
			}
		}
	}

	public List<Indicacao> recuperaListaDeMedicamentosPaginada() {
		return indicacaoDAO.recuperaListaDeMedicamentosPaginada();
	}

	public List<Indicacao> recuperaMedicamentoPorCodigoLike(
			String codMedicamento) {
		return indicacaoDAO.recuperaMedicamentoPorCodigoLike(codMedicamento);
	}

	public List<Indicacao> recuperaMedicamentoPorNome(String nomeMedicamento) {
		return indicacaoDAO.recuperaMedicamentoPorNome(nomeMedicamento);
	}

}
