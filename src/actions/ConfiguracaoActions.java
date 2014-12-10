package actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import modelo.Paciente;
import modelo.Parametro;
import service.ConfiguracaoAppService;
import service.ParametroAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.SelectOneDataModel;

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

public class ConfiguracaoActions extends BaseActions implements Serializable {

	// Service
	private static ConfiguracaoAppService configuracaoService;
	
	// Componentes de Controle
	private static final long serialVersionUID = 1L;
	private DataModel listaConfiguracao;

	// Páginas
	public final String PAGINA_LIST = "listConfiguracao";

	
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
	public ConfiguracaoActions() throws Exception{
		try{
			configuracaoService = FabricaDeAppService.getAppService(ConfiguracaoAppService.class);
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
		listaConfiguracao = null;
		return PAGINA_LIST;
	}

	
	/* ************* Get & Set ************ */
	

	
	public DataModel getListaConfiguracao(){
		if(listaConfiguracao == null){
			listaConfiguracao = new ListDataModel(configuracaoService.recuperaListaDeConfiguracaoPaginada());
		}
		return listaConfiguracao;
	}
	
	public void setListaConfiguracao(DataModel listaConfiguracao){
		this.listaConfiguracao = listaConfiguracao;
	}
	

}
