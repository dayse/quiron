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
 * AlgoritmoActions � uma classe relacionada a manipula��o de tela, ou seja, a
 * intera��o do usu�rio de fato dar-se-� atrav�s de objetos do tipo
 * AlgoritmoActions quando estiver na tela de Configura��o.
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
	 * M�todo utilizado para sair de um tela interna do menu Configura��o e
	 * voltar para a tela de listagem de algoritmos. Al�m disso esse m�todo zera as vari�veis
	 * importantes para que n�o fiquem com dados residuais das
	 * �ltimas a��es feitas pelo usu�rio.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String cancelar() {
		listaAlgoritmo = null;
		return PAGINA_LIST;
	}
	
	
	/**
	 * 
	 * M�todo repons�vel por mudar o status de um algoritmo para ativo.
	 * Antes de realizar uma altera��o, o m�todo verifica se o usu�rio
	 * realmente selecionou uma op��o. A partir da� o m�todo altera do
	 * algoritmoService � chamado, e este verifica se o algoritmo selecionado
	 * � o qu� est� atualmente ativo.
	 * 
	 * @return String - que ir� atualizar a p�gina.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String ativar(){
		// Recupera o algoritmo selecionado no combo box da p�gina.
		algoritmoCorrente = comboTiposDeAlgoritmos.getObjetoSelecionado();
		// Verifica se o usu�rio esqueceu de selecionar um algoritmo
		if(algoritmoCorrente == null){
			// Retona um erro para a tela caso o usu�rio tenha esquecido de selecionar um algoritmo
			error("algoritmo.NAO_SELECIONADO");
			return PAGINA_LIST;
		}	
		// Tenta alterar o algortimo selecionado, e se por algum motivo
		// ocorrer erro na opera��o imprime a mensagem de erro na tela.
		try{
			algoritmoService.altera(algoritmoCorrente);
		}catch(AplicacaoException ex){
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		// Se chegar at� este ponto, significa que a altera��o ocorreu com
		// sucesso e que o algoritmo foi ativado.
		info("algoritmo.ATIVADO_COM_SUCESSO");
		// Setamos aqui o combo box de algoritmos como null,
		// assim quando a tela for atualizada o combo ser� repreenchido com 
		// as informa��es atualizadas.
		comboTiposDeAlgoritmos = null;
		// Zeramos a lista de algoritmos. Na tela essa lista representa
		// a tabela de dados com os algoritmos inativos e ativo.
		listaAlgoritmo = null;
		return PAGINA_LIST;
	}

	
	/* ************* Get & Set ************ */
	
	/**
	 * 
	 * Verifica se a lista de algoritmos foi marcada
	 * como null. Se tiver sido, ser� feito uma consulta
	 * ao banco atrav�s do algoritmoService. O service ent�o
	 * retornar uma lista atualizada para a vari�vel que estar�
	 * pronta para preencher os dados em tela.
	 * 
	 * @return lista atualizada de algoritmos
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public DataModel getListaAlgoritmo(){
		if(listaAlgoritmo == null){
			listaAlgoritmo = new ListDataModel(algoritmoService.recuperaListaDeAlgoritmo());
		}
		return listaAlgoritmo;
	}
	
	public void setListaAlgoritmo(DataModel listaAlgoritmo){
		this.listaAlgoritmo = listaAlgoritmo;
	}
	
	/**
	 * Verifica se o combo box foi marcado como null. Se tiver sido,
	 * o mesmo ser� preenchido novamente com uma lista atualizada de
	 * op��es.
	 * 
	 * @return lista atualizada de op��es para o combo box
	 * 
	 * @author bruno.oliveira
	 * 
	 */
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
