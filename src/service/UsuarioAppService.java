package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exception.RelatorioException;
import modelo.Atendimento;
import modelo.TipoUsuario;
import modelo.Usuario;
import relatorio.Relatorio;
import relatorio.RelatorioFactory;
import service.anotacao.Transacional;
import service.exception.AplicacaoException;
import util.Digesto;
import DAO.AtendimentoDAO;
import DAO.UsuarioDAO;
import DAO.Impl.AtendimentoDAOImpl;
import DAO.Impl.UsuarioDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;

/**
 * 
 * UsuarioAppService � uma classe de servi�o que possui as regras de neg�cio para manipular inicalmente
 * entidade Usuario. Estas manipula��es incluem quando necess�rio chamadas as interfaces DAOs,
 * outras classes de servi�o e acessos a informa��es do banco.
 * 
 * A classe UsuarioAppService fora criada para atender ao Padr�o MVC, Model Vision Control, sendo a mesma uma
 * classe de servi�o que � capaz de efetuar: controle de transa��o, ou seja esta classe possui o recurso de
 * abrir transa�ao, commitar e fechar uma transa�ao atrav�s de um interceptador de servi�o.
 * Neste interceptador ser� definido se o m�todo � transacional ou n�o e em fun��o desta informa��o
 * o interceptador ir� usar ou n�o uma transa��o.
 * 
 * @author marques.araujo
 * 
 */

public class UsuarioAppService {
	// DAOs
	private UsuarioDAO usuarioDAO;
	private AtendimentoDAO atendimentoDAO;

	public UsuarioAppService() {
		try {
			// DAOs
			usuarioDAO = FabricaDeDao.getDao(UsuarioDAOImpl.class);
			atendimentoDAO = FabricaDeDao.getDao(AtendimentoDAOImpl.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Usuario recuperaPorLoginESenha(String login, String senha) throws AplicacaoException {
		Usuario usuario;
		try {
			
			senha = Digesto.gerarDigestoMD5Correto(senha);
			
			usuario = usuarioDAO.recuperaPorLoginESenha(login, senha);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("usuario.ERRO_LOGIN_SENHA_INEXISTENTES");
		}
		
		return usuario;
	}
	
	@Transacional
	public void inclui(Usuario usuario, String confirmacaoSenha) throws AplicacaoException {
		
		try {
			usuarioDAO.recuperaPorLogin(usuario.getLogin());
			throw new AplicacaoException("usuario.LOGIN_EXISTENTE");
		} catch (ObjetoNaoEncontradoException e) {
		}
		
		if (usuario.getSenha().equals(confirmacaoSenha)) {
			String senha = Digesto.gerarDigestoMD5Correto(usuario.getSenha());
			usuario.setSenha(senha);
			
			usuario.setNome(usuario.getNome().toUpperCase());
			
			usuarioDAO.inclui(usuario);
		} else {
			throw new AplicacaoException("usuario.SENHA_CONFIRMACAO_ERRADA");
		}
	}
	
	@Transacional
	public void altera(Usuario usuario, Usuario usuarioAlterador, String confirmacaoSenha) throws AplicacaoException {
		
		if (!usuarioAlterador.getTipoUsuario().getTipoUsuario().equals(TipoUsuario.ADMINISTRADOR)) {
			throw new AplicacaoException(2,"usuario.NAO_POSSUI_PERMISSAO");
		}
		
		try {
			// Faz uma busca utilizando o ID do usu�rio que est� sendo editado e o salva na vari�vel usuarioExistente.
			Usuario usuarioExistente = usuarioDAO.getPorIdComLock(usuario.getId());
			
			/* O if verifica se o usuario que foi encontrado no banco possui um login diferente
			 		 do usu�rio que est� sendo editado neste momento.
				Uma fez que o m�todo equals retorne false - indicando que os logins s�o diferentes - o operador
					de nega��o (!) o transforma em true fazendo que as linhas dentro do if sejam executadas.
				A primeira linha de c�digo dentro do if() faz uma busca por login, caso ele encontre algum resultado
					uma exce��o � gerada. Isso se deve ao fato de que, se existe um login igual no banco, ele pertence
					a um usu�rio diferente.
			*/
			if(!usuarioExistente.getLogin().equals(usuario.getLogin())){
				usuarioDAO.recuperaPorLogin(usuario.getLogin());
				throw new AplicacaoException("usuario.LOGIN_EXISTENTE");
			}			
		} catch (ObjetoNaoEncontradoException e) {
		}
		
		if (usuario.getSenha().equals(confirmacaoSenha)) {
			try {
				usuarioDAO.getPorIdComLock(usuario.getId());
				
				String senha = Digesto.gerarDigestoMD5Correto(usuario.getSenha());
				usuario.setSenha(senha);
				
				usuario.setNome(usuario.getNome().toUpperCase());
				usuarioDAO.altera(usuario);
				
			} catch (ObjetoNaoEncontradoException e) {
				throw new AplicacaoException(0, "usuario.NAO_ENCONTRADO");
			}
		}else{
			throw new AplicacaoException("usuario.SENHA_CONFIRMACAO_ERRADA");
		}
	}
	
	@Transacional
	public void exclui(Usuario usuario) throws AplicacaoException {
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();
		if(usuario.getTipoUsuario().getTipoUsuario().equals(TipoUsuario.CLINICO)){
			atendimentos = atendimentoDAO.recuperaListaDeAntendimentosParaUmClinico(usuario);
		}else if(usuario.getTipoUsuario().getTipoUsuario().equals(TipoUsuario.TECNICO)){
			atendimentos = atendimentoDAO.recuperaListaDeAntendimentosParaUmTecnico(usuario);
		}
		if(atendimentos.size() > 0){
			throw new AplicacaoException("usuario.VINCULADO_EM_ATENDIMENTO");
		}		
		Usuario usuarioBD = null;
		
		try {
			usuarioBD = usuarioDAO.getPorIdComLock(usuario.getId());
					
			usuarioDAO.exclui(usuarioBD);
			
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException();
		}
	}
	
	public List<Usuario> recuperaUsuarios() {
		return usuarioDAO.recuperaListaDeUsuarios();
	}
	
	public List<Usuario> recuperaUsuariosComTipo() {
		return usuarioDAO.recuperaListaDeUsuariosComTipo();
	}
	
	public List<Usuario> recuperaPorNome(String nome) {
		return usuarioDAO.recuperaListaPaginadaPorNome(nome);
	}
	
	public List<Usuario> recuperaListaDeUsuarioPorTipo(TipoUsuario tipoUsuario){
		return usuarioDAO.recuperaListaDeUsuarioPorTipo(tipoUsuario);
	}
	
	public List<Usuario> recuperaListaDeUsuarios() throws AplicacaoException 
	{	
		List<Usuario> usuarios = usuarioDAO.recuperaListaDeUsuarios();
		if (usuarios.size() == 0) 
		{	throw new AplicacaoException("Usu�rios n�o encontrados.");
		} 
		else 
		{	return usuarios;
		}
	}
	
	public List<Usuario> recuperaUsuarioPorNomeLike(String nome){
		return usuarioDAO.recuperaUsuarioPorNomeLike(nome);
	}
	
	public List<Usuario> recuperaUsuarioPorLoginLike(String login){
		return usuarioDAO.recuperaUsuarioPorLoginLike(login);
	}
	
	/**
	 * Este m�todo � respons�vel por gerar relat�rio simples. 
	 * @param List<Usuario> 
	 * @return void
	 * @throws AplicacaoException
	 */
	@SuppressWarnings("unchecked")
	public void gerarRelatorio(List<Usuario> listaDeUsuarios) throws AplicacaoException {
		
		System.out.println("Antes do metodo getRelatorio dentro de gerarRelatorio de UsuarioAppService");

		Relatorio relatorio = RelatorioFactory.getRelatorio(Relatorio.RELATORIO_LISTAGEM_DE_USUARIOS);
				
		if(relatorio != null)
			System.out.println("A variavel do tipo Relatorio e difente de null em UsuarioAppService");
		
		System.out.println("Depois do metodo getRelatorio dentro de gerarRelatorio  de UsuarioAppService");
		
		try{
			relatorio.gerarRelatorio(listaDeUsuarios, new HashMap());
		} catch (RelatorioException re){
			throw new AplicacaoException("Usuario.RELATORIO_NAO_GERADO");
		}
	}

	public Usuario recuperaPorLogin(String login) throws AplicacaoException {
		Usuario usuario;
		try {
			usuario = usuarioDAO.recuperaPorLogin(login);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("usuario.ERRO_LOGIN_SENHA_INEXISTENTES");
		}
		
		return usuario;
	}
		
}