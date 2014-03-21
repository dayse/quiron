package actions;

import java.io.Serializable;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import service.ParametroAppService;
import service.controleTransacao.FabricaDeAppService;

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
