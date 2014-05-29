package service;

import java.util.List;

import service.anotacao.Transacional;
import service.exception.AplicacaoException;
import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.Parametro;
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

/**
 * 
 * @author bruno.oliveira
 * 
 */
public class IndicacaoAppService {

	private static IndicacaoDAO indicacaoDAO;
	private static EspecialistaDAO especialistaDAO;
	private static ParametroDAO parametroDAO;
	private static AvalIndicacaoEspecDAO avalIndicacaoEspecDAO;

	public IndicacaoAppService() throws Exception {
		try {
			indicacaoDAO = FabricaDeDao.getDao(IndicacaoDAOImpl.class);
			especialistaDAO = FabricaDeDao.getDao(EspecialistaDAOImpl.class);
			parametroDAO = FabricaDeDao.getDao(ParametroDAOImpl.class);
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
		Indicacao indicacaoBD = null;
		try {
			indicacaoBD = indicacaoDAO.getPorIdComLock(indicacao.getId());
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("indicacao.NAO_ENCONTRADO");
		}
		indicacaoDAO.exclui(indicacaoBD);
	}

	@Transacional
	public void inclui (Indicacao indicacao) throws AplicacaoException{
		try{
			indicacaoDAO.recuperaIndicacaoPorCodigo(indicacao.getCodIndicacao());
			throw new AplicacaoException("indicacao.CODIGO_EXISTENTE");
		}catch(ObjetoNaoEncontradoException ob){
			
		}
		indicacaoDAO.inclui(indicacao);
		
		List<Especialista> especialistaBD = especialistaDAO.recuperaListaEspecialista();
		List<Parametro> parametroBD = parametroDAO.recuperaListaDeParametros();
		
		if(!especialistaBD.isEmpty() && !parametroBD.isEmpty()){
			for(Especialista especialista : especialistaBD){
				
				AvalIndicacaoEspec avalIndicacaoEspec = new AvalIndicacaoEspec();
				avalIndicacaoEspec.setIndicacao(indicacao);
				avalIndicacaoEspec.setEspecialista(especialista);
				
				for(Parametro parametro : parametroBD){
					avalIndicacaoEspec.setParametro(parametro);
					avalIndicacaoEspecDAO.inclui(avalIndicacaoEspec);
				}
			}
		}
	}

	public List<Indicacao> recuperaListaDeIndicacoesPaginada() {
		return indicacaoDAO.recuperaListaDeIndicacoesPaginada();
	}

	public List<Indicacao> recuperaIndicacaoPorCodigoLike(
			String codIndicacao) {
		return indicacaoDAO.recuperaIndicacaoPorCodigoLike(codIndicacao);
	}

	public List<Indicacao> recuperaIndicacaoPorNome(String nomeIndicacao) {
		return indicacaoDAO.recuperaIndicacaoPorNome(nomeIndicacao);
	}

	public Indicacao recuperaIndicacaoPorCodigo(String codIndicacao) throws ObjetoNaoEncontradoException {
		return indicacaoDAO.recuperaIndicacaoPorCodigo(codIndicacao);
	}

}
