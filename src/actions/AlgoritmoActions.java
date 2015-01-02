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
 * AlgoritmoActions é uma classe relacionada a manipulação de tela, ou seja, a
 * interação do usuário de fato dar-se-á através de objetos do tipo
 * AlgoritmoActions quando estiver na tela de Configuração.
 * 
 * Objetos do "tipo actions" são managed beans.
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

	// Páginas
	public final String PAGINA_LIST = "listAlgoritmo";

	
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
	public AlgoritmoActions() throws Exception{
		try{
			algoritmoService = FabricaDeAppService.getAppService(AlgoritmoAppService.class);
		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * 
	 * Método utilizado para sair de um tela interna do menu Configuração e
	 * voltar para a tela de listagem de algoritmos. Além disso esse método zera as variáveis
	 * importantes para que não fiquem com dados residuais das
	 * últimas ações feitas pelo usuário.
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
	 * Método reponsável por mudar o status de um algoritmo para ativo.
	 * Antes de realizar uma alteração, o método verifica se o usuário
	 * realmente selecionou uma opção. A partir daí o método altera do
	 * algoritmoService é chamado, e este verifica se o algoritmo selecionado
	 * é o quê está atualmente ativo.
	 * 
	 * @return String - que irá atualizar a página.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String ativar(){
		// Recupera o algoritmo selecionado no combo box da página.
		algoritmoCorrente = comboTiposDeAlgoritmos.getObjetoSelecionado();
		// Verifica se o usuário esqueceu de selecionar um algoritmo
		if(algoritmoCorrente == null){
			// Retona um erro para a tela caso o usuário tenha esquecido de selecionar um algoritmo
			error("algoritmo.NAO_SELECIONADO");
			return PAGINA_LIST;
		}	
		// Tenta alterar o algortimo selecionado, e se por algum motivo
		// ocorrer erro na operação imprime a mensagem de erro na tela.
		try{
			algoritmoService.altera(algoritmoCorrente);
		}catch(AplicacaoException ex){
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		// Se chegar até este ponto, significa que a alteração ocorreu com
		// sucesso e que o algoritmo foi ativado.
		info("algoritmo.ATIVADO_COM_SUCESSO");
		// Setamos aqui o combo box de algoritmos como null,
		// assim quando a tela for atualizada o combo será repreenchido com 
		// as informações atualizadas.
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
	 * como null. Se tiver sido, será feito uma consulta
	 * ao banco através do algoritmoService. O service então
	 * retornar uma lista atualizada para a variável que estará
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
	 * o mesmo será preenchido novamente com uma lista atualizada de
	 * opções.
	 * 
	 * @return lista atualizada de opções para o combo box
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
