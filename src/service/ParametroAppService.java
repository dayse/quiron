package service;

import java.util.List;

import modelo.Anamnese;
import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.Paciente;
import modelo.Parametro;
import modelo.TipoUsuario;
import modelo.Usuario;
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
import DAO.exception.InfraestruturaException;
import DAO.exception.ObjetoNaoEncontradoException;

public class ParametroAppService {
	
	private static ParametroDAO parametroDAO;
	private static EspecialistaDAO especialistaDAO;
	private static IndicacaoDAO indicacaoDAO;
	private static AnamneseDAO anamneseDAO;
	private static AvalIndicacaoEspecDAO avalIndicacaoEspecDAO;

	private static AvalIndicacaoEspecAppService avalIndicacaoEspecService;
	
	public ParametroAppService() throws Exception{
		try{
			parametroDAO = FabricaDeDao.getDao(ParametroDAOImpl.class);
			especialistaDAO = FabricaDeDao.getDao(EspecialistaDAOImpl.class);
			indicacaoDAO = FabricaDeDao.getDao(IndicacaoDAOImpl.class);
			anamneseDAO = FabricaDeDao.getDao(AnamneseDAOImpl.class);
			avalIndicacaoEspecDAO = FabricaDeDao.getDao(AvalIndicacaoEspecDAOImpl.class);

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

	/**
	 * Exclui um parametro, não deve ser utilizado fora deste service,
	 * pois um parametro só deve ser excluido utilizando meotodo:
	 * excluiComSeguranca.
	 * 
	 * @param parametro
	 * @throws AplicacaoException
	 */
	@Transacional
	private void exclui(Parametro parametro) throws AplicacaoException{
		Parametro parametroBD = null;
		try{
			parametroBD = parametroDAO.getPorIdComLock(parametro.getId());
		}catch(ObjetoNaoEncontradoException e){
			throw new AplicacaoException("parametro.NAO_ENCONTRADO");
		}
		parametroDAO.exclui(parametroBD);
	}

	/**
	 * Método que pode ser utilizado para excluir um parametro
	 * apenas exclui o mesmo se este não estiver em uso no sistema
	 * @param parametro
	 * @throws AplicacaoException
	 */
	@Transacional
	public void excluiComSeguranca(Parametro parametro) throws AplicacaoException{
		List<AvalIndicacaoEspec> avalIndicacaoEspecList = avalIndicacaoEspecDAO.
															recuperaListaDeAvaliacaoEspecPorParametro(parametro);
		List<Anamnese> anameseList = anamneseDAO.recuperaListaDeAnamnesePorParametro(parametro);
		
		if(anameseList.size() != 0 || avalIndicacaoEspecList.size() != 0){
			throw new AplicacaoException("parametro.EM_USO");
		}
		else{
			exclui(parametro);
		}
	}
	
	@Transacional
	public void altera(Parametro parametro) throws AplicacaoException {
		parametroDAO.altera(parametro);
	}

	public List<Parametro> recuperaListaDeParametrosPaginada(){
		return parametroDAO.recuperaListaDeParametrosPaginada();
	}
	
	/**
	 * Retorna true se o usuario autenticado for administrador ou engenheiro do conhecimento,
	 * caso contrário levanta uma AplicacaoException de permissão
	 * @param usuarioAutenticado
	 * @return
	 * @throws AplicacaoException
	 * @author felipe.pontes
	 */
	public Boolean verificaUsuarioAutenticadoTemPermissao(Usuario usuarioAutenticado) throws AplicacaoException{
		
		if (!usuarioAutenticado.getTipoUsuario().getTipoUsuario().equals(TipoUsuario.ADMINISTRADOR) &&
			!usuarioAutenticado.getTipoUsuario().getTipoUsuario().equals(TipoUsuario.ENGENHEIRO_DE_CONHECIMENTO)) {
			throw new AplicacaoException(2,"usuario.NAO_POSSUI_PERMISSAO");
		}
		
		return true;
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
