package actions.controle;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import modelo.Usuario;

import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;

import util.Constantes;

import actions.BaseActions;

/**
 * 
 * Classe respons�vel por fornecer subs�dios para auntentica��o e inicializa��o
 * de uma sess�o para o usu�rio.
 * 
 * @author bruno.oliveira (Atualiza��o)
 * 
 */
public class AutenticadorActions extends BaseActions {

	// Services
	private UsuarioAppService usuarioAppService;

	// Variaveis de tela
	private String login;
	private String senha;

	/**
	 * 
	 * Construtor respons�vel por instanciar os services que ser�o usados no
	 * decorrer da classe.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	public AutenticadorActions() {
		try {
			usuarioAppService = FabricaDeAppService
					.getAppService(UsuarioAppService.class);
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * M�todo utilizado para autenticar um tentativa de login no sistema.
	 * 
	 * @return Em caso de sucesso redireciona o usu�rio para a tela inicial do
	 *         sistema, do contr�rio exibe uma mensagem de erro na tela de
	 *         login.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	public String autenticar() {
		Usuario usuario;
		try {
			usuario = usuarioAppService.recuperaPorLoginESenha(login, senha);
			System.out.println(" >>>> USUARIO CADASTRADO = "
					+ usuario.getNome());
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return Constantes.PAGINA_LOGIN;
		}
		sessaoUsuarioCorrente.setUsuarioLogado(usuario);
		return Constantes.PAGINA_HOME;
	}

	/**
	 * 
	 * M�todo executado no momento do logout, respons�vel por destruir a sess�o
	 * do usu�rio.
	 * 
	 * @return Retorna ao usu�rio para a p�gina de login.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	public String logout() {
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		sessao.removeAttribute("sessaoDoUsuario");
		if (sessao != null) { // Ou seja, se a sess�o ainda existe...
			sessao.invalidate(); // ... � invalidada!
		}
		return Constantes.PAGINA_LOGIN;
	}

	/*  ************* Get & Set ************ */

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}