package actions;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.TipoUsuario;
import modelo.Usuario;
import service.TipoUsuarioAppService;
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.SelectOneDataModel;

/**
 * 
 * UsuarioActions é uma classe relacionada à manipulação de tela, ou seja, a
 * interação do ususário de fato dar-se-á através de objetos do tipo
 * UsuarioActions quando na tela de Usuario. Objetos do tipo "actions" nome aqui
 * adotado também são popularmente conhecidos como managebeans em outras
 * palavras beans gerenciáveis.
 * 
 * @author marques.araujo
 * 
 */

public class UsuarioActions extends BaseActions {

	// Componentes de Controle
	private DataModel listaUsuarios;
	private SelectOneDataModel<TipoUsuario> comboTiposUsuario;

	// Variaveis de Tela
	private Usuario usuarioCorrente;
	private String confirmacaoSenha;
	private String opcaoRelatorioEscolhido;
	@SuppressWarnings("unused")
	private boolean exclusaoUsuarioLogado;
	private boolean permitirAlterarStatus;

	// Services
	private UsuarioAppService usuarioService;
	private TipoUsuarioAppService tipoUsuarioService;

	// Paginas
	public final String PAGINA_LIST = "listUsuario";
	public final String PAGINA_NEW = "newUsuario";
	public final String PAGINA_SHOW = "showUsuario";
	public final String PAGINA_EDIT = "editUsuario";

	/**
	 * 
	 * Construtor responsável por instanciar os services que serão usados no
	 * decorrer da classe.
	 * 
	 * @throws Exception
	 *             - Retorna uma exception caso ocorra alguma problema no
	 *             carregamento dos services.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public UsuarioActions() {
		try {
			usuarioService = FabricaDeAppService
					.getAppService(UsuarioAppService.class);
			tipoUsuarioService = FabricaDeAppService
					.getAppService(TipoUsuarioAppService.class);
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * Método para alteração de um determinado registro de usuário já
	 * cadastrado.
	 * 
	 * @return Caso ocorra erro, mantém na página de edição. Caso contrário
	 *         retorna para página de listagem de usuários e renderiza a
	 *         mensagem de sucesso.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public String altera() {
		try {
			usuarioCorrente.setTipoUsuario(comboTiposUsuario
					.getObjetoSelecionado());
			usuarioService.altera(usuarioCorrente, sessaoUsuarioCorrente
					.getUsuarioLogado(), confirmacaoSenha);
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			listaUsuarios = null;
			return PAGINA_EDIT;
		}
		logUsuarioAutenticadoMsg("Admin - Altera Usuario:" + usuarioCorrente.getLogin());
		info("usuario.SUCESSO_ALTERACAO");
		listaUsuarios = null;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * Método usado em diversos momentos para zerar as principais variáveis
	 * usadas em situações de manipulação de entidades, como por exemplo, edição
	 * ou inclusão e renderizar para a tela de listagem de usuários.
	 * 
	 * @return Retorna uma String que corresponde ao no mapeamento da tela de
	 *         listagem de usuários.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public String cancelar() {
		usuarioCorrente = new Usuario();
		return PAGINA_LIST;
	}

	/**
	 * 
	 * Método usado para exclusão de determinado registro do banco de dados.
	 * 
	 * @return Atualiza a listagem de usuários na tela, ou se necessário
	 *         renderiza uma mensagem de erro.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public String exclui() {
		try {
			usuarioService.exclui(usuarioCorrente);
		} catch (AplicacaoException e) {
			error(e.getMessage());
			return PAGINA_LIST;
		}
		logUsuarioAutenticadoMsg("Admin - Exclui Usuario: " + usuarioCorrente.getLogin());
		info("usuario.SUCESSO_EXCLUSAO");
		listaUsuarios = null;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * Método padrão que gera um relatório de usuários, tendo como saída o
	 * formato PDF.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public void imprimir() {
		try {
			List<Usuario> listaDeUsuarios = usuarioService
					.recuperaListaDeUsuarios();
			usuarioService.gerarRelatorio(listaDeUsuarios);
		} catch (AplicacaoException re) {
			error("usuario.USUARIOS_INEXISTENTES");
		}
	}

	/**
	 * 
	 * Método usado para gerar um relatório de usuários, tendo como opções os
	 * formatos PDF ou HTML.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public void imprimirEmPdfOuHthml() {
		String url = "/GeraRelatorioDeUsuarios";
		FacesContext context = FacesContext.getCurrentInstance();
		List<Usuario> listaDeUsuarios = null;
		try {
			listaDeUsuarios = usuarioService.recuperaListaDeUsuarios();
			if (listaDeUsuarios.isEmpty()) {
				throw new AplicacaoException();
			}
			ServletContext sc = (ServletContext) context.getExternalContext()
					.getContext();
			RequestDispatcher rd = sc.getRequestDispatcher(url);
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();
			request.setAttribute("opcaoRelatorioEscolhido",
					opcaoRelatorioEscolhido);
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}

	/**
	 * 
	 * Método usado para fazer a inclusão de um novo registro no banco de dados.
	 * 
	 * @return Renderiza uma mensagem de erro, caso ocorra um problema na
	 *         inclusão. Ou redireciona para a tela de listagem atualizada de
	 *         usuários com uma mensagem de sucesso.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public String inclui() {
		usuarioCorrente
				.setTipoUsuario(comboTiposUsuario.getObjetoSelecionado());
		try {
			usuarioService.inclui(usuarioCorrente, confirmacaoSenha);
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_NEW;
		}
		logUsuarioAutenticadoMsg("Admin - Inclui Usuario:" + usuarioCorrente.getLogin());
		info("usuario.SUCESSO_INCLUSAO");
		listaUsuarios = null;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * Método usado para carregar as informações especifícas de um determinado
	 * usuário na tela de detalhamento.
	 * 
	 * @return Retorna uma String que redireciona o usuário para a tela de
	 *         detalhamento das informações do registro.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public String mostra() {
		usuarioCorrente = (Usuario) listaUsuarios.getRowData();
		comboTiposUsuario = SelectOneDataModel
				.criaComObjetoSelecionadoSemTextoInicial(tipoUsuarioService
						.recuperaListaDeTipoUsuario(), usuarioCorrente
						.getTipoUsuario());
		
		logUsuarioAutenticadoMsg("Admin - Mostra Usuario:" + usuarioCorrente.getLogin());
		return PAGINA_SHOW;
	}

	/**
	 * 
	 * Método acionado antes da tela de edição ser renderizada. Ele é
	 * responsável por capturar qual foi o usuário que o usuário logado
	 * escolheu, de forma que seja possível recuperar as informações necessárias
	 * do banco.
	 * 
	 * @return Caso a busca ao banco não retorne nada exibe uma mensagem de erro
	 *         sem redirecionar a tela. Caso se obtenha sucesso, o usuário é
	 *         redirecionado para a tela de edição.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public String preparaAlteracao() {
		usuarioCorrente = (Usuario) listaUsuarios.getRowData();
		comboTiposUsuario = SelectOneDataModel
				.criaComObjetoSelecionadoSemTextoInicial(tipoUsuarioService
						.recuperaListaDeTipoUsuario(), usuarioCorrente
						.getTipoUsuario());
		permitirAlterarStatus = true;
		return PAGINA_EDIT;
	}

	/**
	 * 
	 * Método acionado antes do modal panel de exclusão ser renderizado. Ele é
	 * responsável por capturar qual foi o registro deusuário que o usuário
	 * logado escolheu, de forma que a referência não se perca.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public void preparaExclusao() {
		usuarioCorrente = (Usuario) listaUsuarios.getRowData();
	}

	/**
	 * 
	 * Método acionado antes da tela de inclusão ser renderizada. Ele é
	 * responsável por preparar as instâncias de todas as entidades necessárias
	 * para a inclusão de um novo usuário. De forma a garantir que resíduos de
	 * procedimentos antigos não diminuam a garantia de confiabilidade da
	 * inclusão.
	 * 
	 * @return Retorna uma String que corresponde ao nome do mapeamento da tela
	 *         de inclusão, redirecionado o usuário para a mesma.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public String preparaInclusao() {
		usuarioCorrente = new Usuario();
		return PAGINA_NEW;
	}

	/*   ************* Get & Set ************ */

	/**
	 * 
	 * Este método retorna uma lista de usuários.
	 * 
	 * @return DataModel contendo a lista de completa de usuários cadastrados e
	 *         seus respectivos tipos de permissão.
	 * 
	 */
	public DataModel getListaUsuarios() {

		if (listaUsuarios == null) {
			listaUsuarios = new ListDataModel(usuarioService
					.recuperaUsuariosComTipo());
		}

		return listaUsuarios;
	}

	public Usuario getUsuarioCorrente() {
		return usuarioCorrente;
	}

	/**
	 * 
	 * Este método retorna os tipos de usuários disponíveis.
	 * 
	 * @return SelectOneDataModel<TipoUsuario> - Uma lista do tipo
	 *         SelectOneDataModel contendo todos os tipos de usuários
	 *         cadastrados no banco.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public SelectOneDataModel<TipoUsuario> getComboTiposUsuario() {

		if (comboTiposUsuario == null) {
			comboTiposUsuario = SelectOneDataModel
					.criaSemTextoInicial(tipoUsuarioService
							.recuperaListaDeTipoUsuario());
		}

		return comboTiposUsuario;
	}

	public void setListaUsuarios(DataModel listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public void setUsuarioCorrente(Usuario usuarioCorrente) {
		this.usuarioCorrente = usuarioCorrente;
	}

	public void setComboTiposUsuario(
			SelectOneDataModel<TipoUsuario> comboTiposUsuario) {
		this.comboTiposUsuario = comboTiposUsuario;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setExclusaoUsuarioLogado(boolean exclusaoUsuarioLogado) {
		this.exclusaoUsuarioLogado = exclusaoUsuarioLogado;
	}

	/**
	 * 
	 * Este método identifica se o usuário está logado a partir do método
	 * equals.
	 * 
	 * @return boolean - True se o usuário está logado, do contrário retorna
	 *         false.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public boolean isExclusaoUsuarioLogado() {
		return sessaoUsuarioCorrente.getUsuarioLogado().equals(
				(Usuario) listaUsuarios.getRowData());
	}

	public boolean isPermitirAlterarStatus() {
		return permitirAlterarStatus;
	}

	public void setPermitirAlterarStatus(boolean permitirAlterarStatus) {
		this.permitirAlterarStatus = permitirAlterarStatus;
	}

	public String getOpcaoRelatorioEscolhido() {
		return opcaoRelatorioEscolhido;
	}

	public void setOpcaoRelatorioEscolhido(String opcaoRelatorioEscolhido) {
		this.opcaoRelatorioEscolhido = opcaoRelatorioEscolhido;
	}

}