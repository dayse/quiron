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
 * Classe responsável por fornecer subsídios para auntenticação e inicialização
 * de uma sessão para o usuário.
 * 
 * @author bruno.oliveira (Atualização)
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
	 * Construtor responsável por instanciar os services que serão usados no
	 * decorrer da classe.
	 * 
	 * @author bruno.oliveira (Atualização)
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
	 * Método utilizado para autenticar um tentativa de login no sistema.
	 * 
	 * @return Em caso de sucesso redireciona o usuário para a tela inicial do
	 *         sistema, do contrário exibe uma mensagem de erro na tela de
	 *         login.
	 * 
	 * @author bruno.oliveira (Atualização)
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
	 * Método executado no momento do logout, responsável por destruir a sessão
	 * do usuário.
	 * 
	 * @return Retorna ao usuário para a página de login.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public String logout() {
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		sessao.removeAttribute("sessaoDoUsuario");
		if (sessao != null) { // Ou seja, se a sessão ainda existe...
			sessao.invalidate(); // ... é invalidada!
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