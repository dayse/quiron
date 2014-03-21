package actions;

import java.io.Serializable;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import service.ParametroAppService;
import service.controleTransacao.FabricaDeAppService;

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
}
