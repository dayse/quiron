package actions;

import java.io.Serializable;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import modelo.Paciente;
import modelo.Parametro;
import service.ParametroAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;

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
		parametroCorrente = new Parametro();
		return PAGINA_NEW;
	}
	/**
	 * 
	 * M�todo para inclus�o de parametro no banco de dados.
	 * 
	 * @author felipe.pontes
	 * 
	 */
	public String inclui() {
		try {
			parametroService.incluiComVerificacaoUsuario(parametroCorrente, sessaoUsuarioCorrente.getUsuarioLogado());
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_NEW;
		}
		info("parametro.SUCESSO_INCLUSAO");
		listaParametros = null;
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
		return PAGINA_EDIT;
	}

	/**
	 * Altera o parametroCorrente para os valores passados pelo usuario em tela.
	 * @author felipe.pontes
	 */
	public String altera() {
		try {
			parametroService.alteraComVerificacaoUsuario(parametroCorrente,sessaoUsuarioCorrente.getUsuarioLogado());
		} catch (AplicacaoException e) {
			error(e.getMessage());
			return PAGINA_EDIT;
		}
		info("parametro.SUCESSO_ALTERACAO");
		listaParametros = null;
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
		return PAGINA_LIST;
	}
	/* ************* Get & Set ************ */
	
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
}
