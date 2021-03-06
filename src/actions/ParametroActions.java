package actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import modelo.Paciente;
import modelo.Parametro;
import modelo.TipoUsuario;
import modelo.Usuario;
import service.ParametroAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.SelectOneDataModel;

/**
 * 
 * ParametroActions � uma classe relacionada a manipula��o de tela, ou seja, a
 * intera��o do usu�rio de fato dar-se-� atrav�s de objetos do tipo
 * ParametroActions quando estiver na tela de Parametros.
 * 
 * Objetos do "tipo actions" s�o managed beans.
 *
 * @author bruno.oliveira
 *
 */

public class ParametroActions extends BaseActions implements Serializable {

	// Service
	private static ParametroAppService parametroService;
	
	// Componentes de Controle
	private static final long serialVersionUID = 1L;
	private DataModel listaParametros;
	private Parametro parametroCorrente;
	private String campoDeBusca;
	private SelectOneDataModel<String> comboTiposDeBusca;
	private SelectOneDataModel<String> comboTiposParametro;
	private List<String> list = new ArrayList<String> (2); 
	private boolean buscaEfetuada = false;
	private final String BUSCA_POR_CODIGO = "C�digo";
	private final String BUSCA_POR_NOME = "Nome";
	private int paginaParametro = 1;

	// P�ginas
	public final String PAGINA_LIST = "listParametro";
	public final String PAGINA_NEW = "newParametro";
	public final String PAGINA_SHOW = "showParametro";
	public final String PAGINA_EDIT = "editParametro";
	
	/**
	 * 
	 * Construtor respons�vel por instanciar os 
	 * services que ser�o usados no decorrer da classe;
	 * 
	 * @throws Exception - Retorna uma exception caso ocorra
	 * alguma problema no carregamento dos services.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public ParametroActions() throws Exception{
		try{
			parametroService = FabricaDeAppService.getAppService(ParametroAppService.class);
		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * 
	 * M�todo utilizado para fazer a busca
	 * de um determinado parametro no
	 * banco atrav�s de dados passados via
	 * formul�rio pelo usu�rio.
	 * 
	 * @return A lista de parametros atualizada
	 * com os resultados da pesquisa no banco ou
	 * uma mensagem de erro, caso algum ocorra.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String buscaParametro(){
		List<Parametro> parametrosEncontrados = null;
		if(campoDeBusca.trim().isEmpty()){
			error("parametro.FORNECER_CAMPO_DE_BUSCA");
			return PAGINA_LIST;
		} else {
			listaParametros = null;
			if(comboTiposDeBusca.getObjetoSelecionado().equals(BUSCA_POR_CODIGO)){
				parametrosEncontrados = new ArrayList<Parametro>(parametroService.recuperaParametroPorCodigoLike(campoDeBusca));
			} else {
				parametrosEncontrados = new ArrayList<Parametro>(parametroService.recuperaParametroPorNomeLike(campoDeBusca));
			}
			if(parametrosEncontrados.isEmpty()){
				error("parametro.NAO_ENCONTRADO");
				listaParametros = null;
				buscaEfetuada = false;
				return PAGINA_LIST;
			} else {
				info("parametro.ENCONTRADOS");
			}
		}
		listaParametros = new ListDataModel(parametrosEncontrados);
		buscaEfetuada = true;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * M�todo utilizado para sair de um tela interna do menu Parametro e
	 * voltar para a tela de listagem de parametro. Al�m, zera vari�veis
	 * importantes para que n�o as mesmas n�o fiquem com dados residuais das
	 * �ltimas a��es feitas pelo usu�rio.
	 * 
	 * 
	 * @author felipe.pontes
	 * 
	 */
	public String cancelar() {
		listaParametros = null;
		parametroCorrente = new Parametro();
		comboTiposDeBusca = null;
		buscaEfetuada = false;
		return PAGINA_LIST;
	}


	/**
	 * 
	 * M�todo acionado antes que a tela de inclus�o seja renderizada. Zera a
	 * inst�ncia de parametro para garantir que n�o haja res�duos.
	 * 
	 * 
	 * @author felipe.pontes
	 * 
	 */
	public String preparaInclusao() {
		//comboTiposParametro = SelectOneDataModel.criaSemTextoInicial(list);
		try {
			parametroService.verificaUsuarioAutenticadoTemPermissao( sessaoUsuarioCorrente.getUsuarioLogado());
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		
		parametroCorrente = new Parametro();
		comboTiposDeBusca = null;
		buscaEfetuada = false;
		listaParametros =  null;
		return PAGINA_NEW;
	}
	/**
	 * 
	 * M�todo para inclus�o de parametro no banco de dados.
	 * 
	 * @author felipe.pontes
	 * 
	 * Foi colocado o campo tipo de parametros
	 * 
	 * @author patricia.lima
	 */
	public String inclui() {
		parametroCorrente.setTipo(comboTiposParametro.getObjetoSelecionado());
		try {
			parametroService.incluiComVerificacaoUsuario(parametroCorrente, sessaoUsuarioCorrente.getUsuarioLogado());
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_NEW;
		}
		info("parametro.SUCESSO_INCLUSAO");
		listaParametros = null;
		buscaEfetuada = false;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * M�todo acionado antes que a tela de edi��o seja renderizada. Salva o
	 * parametro selecionado pelo usu�rio para que a refer�ncia n�o se perca.
	 * 
	 * @author felipe.pontes
	 * 
	 */
	public String preparaAlteracao() {
		parametroCorrente = (Parametro) listaParametros.getRowData();
		//comboTiposParametro = SelectOneDataModel.criaSemTextoInicial(list);
		try {
			parametroService.verificaUsuarioAutenticadoTemPermissao( sessaoUsuarioCorrente.getUsuarioLogado());
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_LIST;
		}
	
		return PAGINA_EDIT;
	}

	/**
	 * Altera o parametroCorrente para os valores passados pelo usuario em tela.
	 * @author felipe.pontes
	 */
	public String altera() {
		parametroCorrente.setTipo(comboTiposParametro.getObjetoSelecionado());
		try {
			parametroService.alteraComVerificacaoUsuario(parametroCorrente,sessaoUsuarioCorrente.getUsuarioLogado());
		} catch (AplicacaoException e) {
			error(e.getMessage());
			return PAGINA_EDIT;
		}
		info("parametro.SUCESSO_ALTERACAO");
		listaParametros = null;
		comboTiposDeBusca = null;
		buscaEfetuada = false;
		return PAGINA_LIST;
		
	}
	
	/**
	 * 
	 * M�todo acionado antes do
	 * modal panel de exclus�o ser
	 * renderizado.
	 * Ele � respons�vel por capturar
	 * qual foi o parametro que o usu�rio
	 * escolheu, de forma que a refer�ncia
	 * n�o se perca.
	 * 
	 * @author felipe.pontes
	 * 
	 */
	public void preparaExclusao(){

		parametroCorrente = (Parametro) listaParametros.getRowData();
	System.out.println("Passei aqui");
	}

	/**
	 * 
	 * M�todo usado para exclus�o de 
	 * determinado registro de parametro do
	 * banco de dados.
	 * 
	 * @author felipe.pontes
	 * 
	 */
	public String exclui(){
		try{
			parametroService.excluiComSegurancaComVerificacaoUsuario(parametroCorrente,sessaoUsuarioCorrente.getUsuarioLogado());
			
		}catch(AplicacaoException ex){
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		logUsuarioAutenticadoMsg("Parametro - Exclui parametro:" + parametroCorrente.getCodParametro());
		info("parametro.SUCESSO_EXCLUSAO");
		listaParametros = null;
		comboTiposDeBusca = null;
		comboTiposParametro = null;
		buscaEfetuada = false;
		return PAGINA_LIST;
	}
	
	/**
	 * Imprime um relat�rio contendo a lista dos parametros
	 * cadastrados no sistema.
	 * 
	 * @author bruno.oliveira
	 * 
	 */		
	public void imprimir() throws AplicacaoException{
	
			List<Parametro> listaDeParametros = parametroService.recuperaListaDeParametros();

			if(listaDeParametros.isEmpty()){
				error ("parametro.PARAMETRO_INEXISTENTES");

			}
			else{
				parametroService.gerarRelatorio(listaDeParametros);
			}


		}

	
	/* ************* Get & Set ************ */
	
	public boolean isBuscaEfetuada(){
		return buscaEfetuada;
	}
	
	public void setBuscaEfetuada(boolean buscaEfetuada){
		this.buscaEfetuada = buscaEfetuada;
	}
	
	public String getCampoDeBusca(){
		return campoDeBusca;
	}
	
	public void setCampoDeBusca(String campoDeBusca){
		this.campoDeBusca = campoDeBusca;
	}

	public SelectOneDataModel<String> getComboTiposDeBusca(){
		if(comboTiposDeBusca == null){
			List<String> tiposDeBusca = new ArrayList<String>(2);
			tiposDeBusca.add(BUSCA_POR_CODIGO);
			tiposDeBusca.add(BUSCA_POR_NOME);
			comboTiposDeBusca =  SelectOneDataModel.criaComObjetoSelecionadoSemTextoInicial(tiposDeBusca, BUSCA_POR_CODIGO);
		}
		return comboTiposDeBusca;
	}
	/**
	 * Inserindo no cadastro de Parametros a lista dos tipos de parametros
	 * cadastrados no sistema.
	 * 
	 * @author patricia.lima
	 * 
	 */	
	public SelectOneDataModel<String> getComboTiposParametro() {
		if (comboTiposParametro == null) {
		 
        list.add ("N�o pode exceder");  
        list.add ("Pode exceder");  
        
		
			comboTiposParametro = SelectOneDataModel.criaSemTextoInicial(list);
		}

		return comboTiposParametro;
	}
	public void setComboTiposDeBusca(SelectOneDataModel<String> comboTiposDeBusca){
		this.comboTiposDeBusca = comboTiposDeBusca;
	}
	
	public DataModel getListaParametros(){
		if(listaParametros == null){
			listaParametros = new ListDataModel(parametroService.recuperaListaDeParametrosPaginada());
		}
		return listaParametros;
	}
	
	public void setListaParametros(DataModel listaParametros){
		this.listaParametros = listaParametros;
	}

	public Parametro getParametroCorrente() {
		return parametroCorrente;
	}

	public void setParametroCorrente(Parametro parametroCorrente) {
		this.parametroCorrente = parametroCorrente;
	}
	
	public int getPaginaParametro(){
		return paginaParametro;
	}
	
	public void setPaginaParametro(int paginaParametro){
		this.paginaParametro = paginaParametro;
	}

	public void setComboTiposParametro(
			SelectOneDataModel<String> comboTiposParametro) {
		this.comboTiposParametro = comboTiposParametro;
	}
}
