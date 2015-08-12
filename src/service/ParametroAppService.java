package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exception.RelatorioException;
import modelo.Anamnese;
import modelo.Atendimento;
import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.Paciente;
import modelo.Parametro;
import modelo.TipoUsuario;
import modelo.Usuario;
import relatorio.Relatorio;
import relatorio.RelatorioFactory;
import service.anotacao.Transacional;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import DAO.AnamneseDAO;
import DAO.AtendimentoDAO;
import DAO.AvalIndicacaoEspecDAO;
import DAO.EspecialistaDAO;
import DAO.IndicacaoDAO;
import DAO.ParametroDAO;
import DAO.Impl.AnamneseDAOImpl;
import DAO.Impl.AtendimentoDAOImpl;
import DAO.Impl.AvalIndicacaoEspecDAOImpl;
import DAO.Impl.EspecialistaDAOImpl;
import DAO.Impl.IndicacaoDAOImpl;
import DAO.Impl.ParametroDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.InfraestruturaException;
import DAO.exception.ObjetoNaoEncontradoException;

public class ParametroAppService {
	
	private static AtendimentoDAO atendimentoDAO;
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
			atendimentoDAO = FabricaDeDao.getDao(AtendimentoDAOImpl.class);

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
	public void incluiComVerificacaoUsuario(Parametro parametro, Usuario usuarioAutenticado) throws AplicacaoException {
		if(verificaUsuarioAutenticadoTemPermissao(usuarioAutenticado)){
			if(verificaSeParametrosEstaoEmUso()){
				this.inclui(parametro);			
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
	public void exclui(Parametro parametro) throws AplicacaoException {
			Parametro parametroBD = null;
			try {
				parametroBD = parametroDAO.getPorIdComLock(parametro
						.getId());
			} catch (ObjetoNaoEncontradoException e) {
				throw new AplicacaoException("especialista.NAO_ENCONTRADO");
			}
			parametroDAO.exclui(parametroBD);
	}
	

	@Transacional
	public void excluiComSegurancaComVerificacaoUsuario(Parametro parametro, Usuario usuarioAutenticado) throws AplicacaoException {
		if(verificaUsuarioAutenticadoTemPermissao(usuarioAutenticado)){
			if(verificaSeParametrosEstaoEmUso()){
				this.exclui(parametro);			
			}
		}
	}
	
	/**
	 * 
	 * Verifica se já existe algum
	 * especialista ou atendimento
	 * cadastrados no banco.
	 * 
	 * @return true - Retorna se o banco estiver sem nenhum cadastro de especialista/atendimento
	 * @throws AplicacaoException - Retorna uma exception se as tabelas especialista ou atendimento tiverem registros.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@Transacional
	public boolean verificaSeParametrosEstaoEmUso() throws AplicacaoException{
		List<Especialista> especialistas = new ArrayList<Especialista>(especialistaDAO.recuperaListaEspecialista());
		List<Atendimento> atendimentos = new ArrayList<Atendimento>(atendimentoDAO.recuperaListaAtendimento());
		if(especialistas.size() > 0 || atendimentos.size() > 0){
			throw new AplicacaoException("parametro.EM_USO");
		}else{
			return true;
		}
	}

	@Transacional
	public void altera(Parametro parametro) throws AplicacaoException {
		parametroDAO.altera(parametro);
	}
	
	@Transacional
	public void alteraComVerificacaoUsuario(Parametro parametro, Usuario usuarioAutenticado) throws AplicacaoException {
		if(verificaUsuarioAutenticadoTemPermissao(usuarioAutenticado)){
			this.altera(parametro);			
		}
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
	@Transacional
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
	
	public List<Parametro> recuperaParametroPorCodigoLike(String codParametro){
		return parametroDAO.recuperaParametroPorCodigoLike(codParametro);
	}
	
	public List<Parametro> recuperaParametroPorNomeLike(String nomeParametro){
		return parametroDAO.recuperaParametroPorNomeLike(nomeParametro);
	}
	
	public void gerarRelatorio(List<Parametro> listaDeParametros)
		throws AplicacaoException{
		System.out.println("Antes do metodo getRelatorio dentro de gerarRelatorio de ParametroAppService");
		
		Relatorio relatorio = RelatorioFactory.getRelatorio(Relatorio.RELATORIO_LISTAGEM_DE_PARAMETROS);
		
		if(relatorio != null)
			System.out.println("A variavel do tipo Relatorio é diferente de null em ParametroAppService");
		
		System.out.println("Depois do metodo getRelatorio dentro de gerarRelatorio de ParametroAppService");
		
		try{
			relatorio.gerarRelatorio(listaDeParametros, new HashMap());
		} catch (RelatorioException re){
			throw new AplicacaoException("parametro.Relatorio_NAO_GERADO");
		}		
	}
}
