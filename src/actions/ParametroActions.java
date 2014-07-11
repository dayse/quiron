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
 * ParametroActions é uma classe relacionada a manipulação de tela, ou seja, a
 * interação do usuário de fato dar-se-á através de objetos do tipo
 * ParametroActions quando estiver na tela de Parametros.
 * 
 * Objetos do "tipo actions" são managed beans.
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
	

	// Páginas
	public final String PAGINA_LIST = "listParametro";
	public final String PAGINA_NEW = "newParametro";
	public final String PAGINA_SHOW = "showParametro";
	public final String PAGINA_EDIT = "editParametro";
	
	/**
	 * 
	 * Construtor responsável por instanciar os 
	 * services que serão usados no decorrer da classe;
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
	 * Método utilizado para sair de um tela interna do menu Parametro e
	 * voltar para a tela de listagem de parametro. Além, zera variáveis
	 * importantes para que não as mesmas não fiquem com dados residuais das
	 * últimas ações feitas pelo usuário.
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
	 * Método acionado antes que a tela de inclusão seja renderizada. Zera a
	 * instância de parametro para garantir que não haja resíduos.
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
	 * Método para inclusão de parametro no banco de dados.
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
	 * Método acionado antes que a tela de edição seja renderizada. Salva o
	 * parametro selecionado pelo usuário para que a referência não se perca.
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
	 * Método acionado antes do
	 * modal panel de exclusão ser
	 * renderizado.
	 * Ele é responsável por capturar
	 * qual foi o parametro que o usuário
	 * escolheu, de forma que a referência
	 * não se perca.
	 * 
	 * @author felipe.pontes
	 * 
	 */
	public void preparaExclusao(){
		parametroCorrente = (Parametro) listaParametros.getRowData();
	}

	/**
	 * 
	 * Método usado para exclusão de 
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
