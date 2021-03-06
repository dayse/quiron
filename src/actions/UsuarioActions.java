package actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Indicacao;
import modelo.TipoUsuario;
import modelo.Usuario;
import service.TipoUsuarioAppService;
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.SelectOneDataModel;

/**
 * 
 * UsuarioActions � uma classe relacionada � manipula��o de tela, ou seja, a
 * intera��o do usus�rio de fato dar-se-� atrav�s de objetos do tipo
 * UsuarioActions quando na tela de Usuario. Objetos do tipo "actions" nome aqui
 * adotado tamb�m s�o popularmente conhecidos como managebeans em outras
 * palavras beans gerenci�veis.
 * 
 * @author marques.araujo
 * 
 */

public class UsuarioActions extends BaseActions {

	// Componentes de Controle
	private DataModel listaUsuarios;
	private SelectOneDataModel<TipoUsuario> comboTiposUsuario;
	private SelectOneDataModel<String> comboTiposDeBusca;

	// Variaveis de Tela
	private String login;
	private String senha;
	private Usuario usuarioCorrente;
	private String confirmacaoSenha;
	@SuppressWarnings("unused")
	private boolean exclusaoUsuarioLogado;
	private boolean permitirAlterarStatus;
	private String campoDeBusca;
	private boolean buscaEfetuada = false;
	public final String BUSCA_POR_LOGIN = "Login";
	public final String BUSCA_POR_NOME = "Nome";	

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
	 * Construtor respons�vel por instanciar os services que ser�o usados no
	 * decorrer da classe.
	 * 
	 * @throws Exception
	 *             - Retorna uma exception caso ocorra alguma problema no
	 *             carregamento dos services.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
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
	 * M�todo para altera��o de um determinado registro de usu�rio j�
	 * cadastrado.
	 * 
	 * @return Caso ocorra erro, mant�m na p�gina de edi��o. Caso contr�rio
	 *         retorna para p�gina de listagem de usu�rios e renderiza a
	 *         mensagem de sucesso.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
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
		comboTiposDeBusca = null;
		buscaEfetuada = false;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * M�todo respons�vel por executar a busca no banco de dados.
	 * 
	 * @return lista de usu�rios que correspondem ao termo buscado.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	public String buscaUsuario(){
		List<Usuario> usuariosEncontrados = null;
		if(campoDeBusca.trim().isEmpty()){
			error("usuario.FORNECER_CAMPO_DE_BUSCA");
			return PAGINA_LIST;
		}else{
			listaUsuarios = null;
			if(comboTiposDeBusca.getObjetoSelecionado().equals(BUSCA_POR_LOGIN)){
				usuariosEncontrados = new ArrayList<Usuario>(usuarioService.recuperaUsuarioPorLoginLike(campoDeBusca));
			}else{
				usuariosEncontrados = new ArrayList<Usuario>(usuarioService.recuperaUsuarioPorNomeLike(campoDeBusca));
			}
			if(usuariosEncontrados.isEmpty()){
				error("usuario.NAO_ENCONTRADO");
				listaUsuarios = null;
				buscaEfetuada = false;
				return PAGINA_LIST;
			}else{
				info("usuario.ENCONTRADOS");
			}
		}
		listaUsuarios = new ListDataModel(usuariosEncontrados);
		buscaEfetuada = true;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * M�todo usado em diversos momentos para zerar as principais vari�veis
	 * usadas em situa��es de manipula��o de entidades, como por exemplo, edi��o
	 * ou inclus�o e renderizar para a tela de listagem de usu�rios.
	 * 
	 * @return Retorna uma String que corresponde ao no mapeamento da tela de
	 *         listagem de usu�rios.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	public String cancelar() {
		usuarioCorrente = new Usuario();
		comboTiposDeBusca = null;
		buscaEfetuada = false;
		listaUsuarios = null;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * M�todo usado para exclus�o de determinado registro do banco de dados.
	 * 
	 * @return Atualiza a listagem de usu�rios na tela, ou se necess�rio
	 *         renderiza uma mensagem de erro.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
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
		comboTiposDeBusca = null;
		buscaEfetuada = false;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * M�todo padr�o que gera um relat�rio de usu�rios, tendo como sa�da o
	 * formato PDF.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	public void imprimir() throws AplicacaoException{
	
			List<Usuario> listaDeUsuarios = usuarioService.recuperaListaDeUsuarios();
			
			if(listaDeUsuarios.isEmpty()){
				error ("usuario.USUARIO_INEXISTENTES");

			}
			else{
				usuarioService.gerarRelatorio(listaDeUsuarios);
			}


		}

	/**
	 * 
	 * M�todo usado para fazer a inclus�o de um novo registro no banco de dados.
	 * 
	 * @return Renderiza uma mensagem de erro, caso ocorra um problema na
	 *         inclus�o. Ou redireciona para a tela de listagem atualizada de
	 *         usu�rios com uma mensagem de sucesso.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
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
		buscaEfetuada = false;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * M�todo usado para carregar as informa��es especif�cas de um determinado
	 * usu�rio na tela de detalhamento.
	 * 
	 * @return Retorna uma String que redireciona o usu�rio para a tela de
	 *         detalhamento das informa��es do registro.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
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
	 * M�todo acionado antes da tela de edi��o ser renderizada. Ele �
	 * respons�vel por capturar qual foi o usu�rio que o usu�rio logado
	 * escolheu, de forma que seja poss�vel recuperar as informa��es necess�rias
	 * do banco.
	 * 
	 * @return Caso a busca ao banco n�o retorne nada exibe uma mensagem de erro
	 *         sem redirecionar a tela. Caso se obtenha sucesso, o usu�rio �
	 *         redirecionado para a tela de edi��o.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	public String preparaAlteracao() {
		usuarioCorrente = (Usuario) listaUsuarios.getRowData();
		comboTiposUsuario = SelectOneDataModel
				.criaComObjetoSelecionadoSemTextoInicial(tipoUsuarioService
						.recuperaListaDeTipoUsuario(), usuarioCorrente
						.getTipoUsuario());
		permitirAlterarStatus = true;
		comboTiposDeBusca = null;
		return PAGINA_EDIT;
	}

	/**
	 * 
	 * M�todo acionado antes do modal panel de exclus�o ser renderizado. Ele �
	 * respons�vel por capturar qual foi o registro deusu�rio que o usu�rio
	 * logado escolheu, de forma que a refer�ncia n�o se perca.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	public void preparaExclusao() {
		usuarioCorrente = (Usuario) listaUsuarios.getRowData();
	}

	/**
	 * 
	 * M�todo acionado antes da tela de inclus�o ser renderizada. Ele �
	 * respons�vel por preparar as inst�ncias de todas as entidades necess�rias
	 * para a inclus�o de um novo usu�rio. De forma a garantir que res�duos de
	 * procedimentos antigos n�o diminuam a garantia de confiabilidade da
	 * inclus�o.
	 * 
	 * @return Retorna uma String que corresponde ao nome do mapeamento da tela
	 *         de inclus�o, redirecionado o usu�rio para a mesma.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	
	public String preparaInclusao() {
		senha = null;
		login = null;
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		listaUsuarios = null;
		confirmacaoSenha = null;
		usuarioCorrente = new Usuario();
		
		return PAGINA_NEW;
	}

	/*   ************* Get & Set ************ */

	/**
	 * 
	 * Este m�todo retorna uma lista de usu�rios.
	 * 
	 * @return DataModel contendo a lista de completa de usu�rios cadastrados e
	 *         seus respectivos tipos de permiss�o.
	 * 
	 */
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
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
	 * Este m�todo retorna os tipos de usu�rios dispon�veis.
	 * 
	 * @return SelectOneDataModel<TipoUsuario> - Uma lista do tipo
	 *         SelectOneDataModel contendo todos os tipos de usu�rios
	 *         cadastrados no banco.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
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
	 * Este m�todo identifica se o usu�rio est� logado a partir do m�todo
	 * equals.
	 * 
	 * @return boolean - True se o usu�rio est� logado, do contr�rio retorna
	 *         false.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	public boolean isExclusaoUsuarioLogado() {
		try {
			List<Usuario> listaAdministrador = usuarioService.recuperaListaDeUsuarioPorTipo(tipoUsuarioService.recuperaTipoUsuarioAdministrador());
		} catch (AplicacaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*if (listaAdministrador == "Administrador")	{
			
		}*/
		
		return sessaoUsuarioCorrente.getUsuarioLogado().equals(
				(Usuario) listaUsuarios.getRowData());
	}

	public boolean isPermitirAlterarStatus() {
		return permitirAlterarStatus;
	}

	public void setPermitirAlterarStatus(boolean permitirAlterarStatus) {
		this.permitirAlterarStatus = permitirAlterarStatus;
	}

	public String getCampoDeBusca() {
		return campoDeBusca;
	}

	public void setCampoDeBusca(String campoDeBusca) {
		this.campoDeBusca = campoDeBusca;
	}

	public SelectOneDataModel<String> getComboTiposDeBusca() {
		if(comboTiposDeBusca == null){
			List<String> tiposDeBusca = new ArrayList<String>(2);
			tiposDeBusca.add(BUSCA_POR_LOGIN);
			tiposDeBusca.add(BUSCA_POR_NOME);
			comboTiposDeBusca = SelectOneDataModel.criaComObjetoSelecionadoSemTextoInicial(tiposDeBusca, BUSCA_POR_LOGIN);
		}		
		return comboTiposDeBusca;
	}

	public void setComboTiposDeBusca(SelectOneDataModel<String> comboTiposDeBusca) {
		this.comboTiposDeBusca = comboTiposDeBusca;
	}

	public boolean isBuscaEfetuada() {
		return buscaEfetuada;
	}

	public void setBuscaEfetuada(boolean buscaEfetuada) {
		this.buscaEfetuada = buscaEfetuada;
	}
	
}