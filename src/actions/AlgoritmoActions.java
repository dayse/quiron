package actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import modelo.Algoritmo;
import modelo.Paciente;
import modelo.Parametro;
import service.AlgoritmoAppService;
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

public class AlgoritmoActions extends BaseActions implements Serializable {

	// Service
	private static AlgoritmoAppService algoritmoService;
	
	// Componentes de Controle
	private static final long serialVersionUID = 1L;
	private DataModel listaAlgoritmo;
	private SelectOneDataModel<Algoritmo> comboTiposDeAlgoritmos;
	private Algoritmo algoritmoCorrente = new Algoritmo();

	// P�ginas
	public final String PAGINA_LIST = "listAlgoritmo";

	
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
	public AlgoritmoActions() throws Exception{
		try{
			algoritmoService = FabricaDeAppService.getAppService(AlgoritmoAppService.class);
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
		listaAlgoritmo = null;
		return PAGINA_LIST;
	}
	
	public String ativar(){
		algoritmoCorrente = comboTiposDeAlgoritmos.getObjetoSelecionado();
		if(algoritmoCorrente == null){
			System.out.println("estou aqui");
			error("algoritmo.NAO_SELECIONADO");
			return PAGINA_LIST;
		}		
		try{
			algoritmoService.altera(algoritmoCorrente);
		}catch(AplicacaoException ex){
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		info("algoritmo.ATIVADO_COM_SUCESSO");
		comboTiposDeAlgoritmos = null;
		listaAlgoritmo = null;
		return PAGINA_LIST;
	}

	
	/* ************* Get & Set ************ */
	

	
	public DataModel getListaAlgoritmo(){
		if(listaAlgoritmo == null){
			listaAlgoritmo = new ListDataModel(algoritmoService.recuperaListaDeAlgoritmo());
		}
		return listaAlgoritmo;
	}
	
	public void setListaAlgoritmo(DataModel listaAlgoritmo){
		this.listaAlgoritmo = listaAlgoritmo;
	}
	
	public SelectOneDataModel<Algoritmo> getComboTiposDeAlgoritmos(){
		if(comboTiposDeAlgoritmos == null){
			comboTiposDeAlgoritmos = SelectOneDataModel.criaComTextoInicialPersonalizado
					(algoritmoService.recuperaListaDeAlgoritmo(), "Selecione um algoritmo para ativar");
		}
		return comboTiposDeAlgoritmos;
	}
	
	public void setComboTiposDeAlgoritmos(SelectOneDataModel<Algoritmo> comboTiposDeAlgoritmos){
		this.comboTiposDeAlgoritmos = comboTiposDeAlgoritmos;
	}	
	

}
