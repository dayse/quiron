package actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import modelo.Indicacao;

import service.IndicacaoAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.SelectOneDataModel;

/**
 * 
 * MedicamentoActions é uma classe relacionada a manipulação de tela, ou seja, a
 * interação do usuário de fato dar-se-á através de objetos do tipo
 * MedicamentoActions quando estiver na tela de Medicamentos.
 * 
 * Objetos do "tipo actions" são managed beans.
 * 
 * @author bruno.oliveira
 *
 */
public class MedicamentoActions extends BaseActions implements Serializable {

	// Service
	private static IndicacaoAppService medicamentoService;

	// Páginas
	private final String PAGINA_EDIT = "editMedicamento";
	private final String PAGINA_LIST = "listMedicamento";
	private final String PAGINA_NEW = "newMedicamento";
	private final String PAGINA_SHOW = "showMedicamento";
	
	// Componentes de Controle
	private static final long serialVersionUID = 1L;
	private String campoDeBusca;
	private int paginaMedicamento = 1;
	private boolean buscaEfetuada = false;
	private SelectOneDataModel<String> comboTiposDeBusca;
	private Indicacao medicamentoCorrente;
	private DataModel listaDeMedicamentos;
	private final String BUSCA_POR_CODIGO = "Código";
	private final String BUSCA_POR_NOME = "Nome";
	
	/**
	 * 
	 * Construtor responsável por instanciar os 
	 * services que serão usados no decorrer da classe.
	 * 
	 * @throws Exception - Retorna uma exception caso ocorra
	 * alguma problema no carregamento dos services.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public MedicamentoActions() throws Exception{
		try{
			medicamentoService = FabricaDeAppService.getAppService(IndicacaoAppService.class);
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 
	 * Método para alteração de um determinado 
	 * registro de Medicamento já cadastrado.
	 * 
	 * @return Caso ocorra erro, mantém na página de edição. 
	 * Caso contrário retorna para página de listagem de medicamentos
	 * e renderiza a mensagem de sucesso.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String altera(){
		try{
			medicamentoService.altera(medicamentoCorrente);
		}catch(AplicacaoException e){
			error(e.getMessage());
			return PAGINA_LIST;
		}
		info("medicamento.SUCESSO_ALTERACAO");
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		listaDeMedicamentos = null;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * Método utilizado para fazer a busca
	 * de um determinado medicamento no
	 * banco através de dados passados via
	 * formulário pelo usuário.
	 * 
	 * @return A lista de medicamentos atualizada
	 * com os resultados da pesquisa no banco ou
	 * uma mensagem de erro, caso algum ocorra.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String buscaMedicamento(){
		List<Indicacao> medicamentosEncontrados = null;
		if(campoDeBusca.trim().isEmpty()){
			error("medicamento.FORNECER_CAMPO_DE_BUSCA");
			return PAGINA_LIST;
		}else{
			listaDeMedicamentos = null;
			if(comboTiposDeBusca.getObjetoSelecionado().equals(BUSCA_POR_CODIGO)){
				medicamentosEncontrados = new ArrayList<Indicacao>(medicamentoService.recuperaMedicamentoPorCodigoLike(campoDeBusca));
			}else{
				medicamentosEncontrados = new ArrayList<Indicacao>(medicamentoService.recuperaMedicamentoPorNome(campoDeBusca));
			}
			if(medicamentosEncontrados.isEmpty()){
				error("medicamento.NAO_ENCONTRADO");
				listaDeMedicamentos = null;
				return PAGINA_LIST;
			}else{
				info("medicamento.ENCONTRADOS");
			}
		}
		listaDeMedicamentos = new ListDataModel(medicamentosEncontrados);
		buscaEfetuada = true;
		return PAGINA_LIST;		
	}
	
	/**
	 * 
	 * Método usado em diversos momentos
	 * para zerar as principais variáveis
	 * usadas em situações de manipulação
	 * de entidades, como por exemplo,
	 * edição ou inclusão e renderizar
	 * para a tela de listagem de medicamentos.
	 * 
	 * @return Retorna uma String que corresponde
	 * ao no mapeamento da tela de listagem de
	 * medicamentos.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String cancelar(){
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		listaDeMedicamentos = null;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * Método usado para exclusão de 
	 * determinado registro do
	 * banco de dados.
	 * 
	 * @return Atualiza a listagem de medicamentos
	 * na tela, ou se necessário renderiza uma
	 * mensagem de erro.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String exclui(){
		try{
			medicamentoService.exclui(medicamentoCorrente);
		}catch(AplicacaoException ex){
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		info("medicamneto.SUCESSO_EXCLUSAO");
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		listaDeMedicamentos = null;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * Método usado para fazer a
	 * inclusão de um novo
	 * registro no banco de dados.
	 * 
	 * @return Renderiza uma mensagem de erro,
	 * caso ocorra um problema na inclusão.
	 * Ou redireciona para a tela de listagem
	 * atualizada de medicamentos com uma
	 * mensagem de sucesso.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String inclui(){
		try{
			medicamentoService.inclui(medicamentoCorrente);
		}catch(AplicacaoException ex){
			error(ex.getMessage());
			return PAGINA_NEW;
		}
		info("medicamento.SUCESSO_INCLUSAO");
		buscaEfetuada = false;
		listaDeMedicamentos = null;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * Método usado para carregar as
	 * informações especifícas de um
	 * determinado medicamento
	 * na tela de detalhamento.
	 * 
	 * @return Retorna uma String
	 * que redireciona o usuário
	 * para a tela de detalhamento
	 * das informações do medicamento.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String mostra(){
		medicamentoCorrente = (Indicacao) listaDeMedicamentos.getRowData();
		return PAGINA_SHOW;
	}
	
	/**
	 * 
	 * Método acionado antes da tela 
	 * de edição ser renderizada.
	 * Ele é responsável por capturar
	 * qual foi o medicamento que o usuário
	 * escolheu, de forma que seja possível
	 * recuperar as informações necessárias
	 * do banco.
	 * 
	 * @return Caso a busca ao banco não retorne
	 * nada exibe uma mensagem de erro sem redirecionar
	 * a tela. Caso se obtenha sucesso, o usuário é
	 * redirecionado para a tela de edição.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String preparaAlteracao(){
		medicamentoCorrente = (Indicacao) listaDeMedicamentos.getRowData();
		return PAGINA_EDIT;
	}
	
	/**
	 * 
	 * Método acionado antes do modal panel de exclusão ser renderizado. Ele é
	 * responsável por capturar qual foi o medicamento que o usuário escolheu,
	 * de forma que a referência não se perca.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public void preparaExclusao(){
		medicamentoCorrente = (Indicacao) listaDeMedicamentos.getRowData();
	}
	
	/**
	 * 
	 * Método acionado antes da tela
	 * de inclusão ser renderizada. 
	 * Ele é responsável por preparar
	 * as instâncias de todas as entidades
	 * necessárias para a inclusão de um novo
	 * medicamento. De forma a garantir que resíduos
	 * de procedimentos antigos não diminuam
	 * a garantia de confiabilidade da inclusão.
	 * 
	 * @return Retorna uma String que corresponde
	 * ao nome do mapeamento da tela de inclusão
	 * redirecionado o usuário para a mesma.
	 * 
	 * @author bruno.oliveira
	 * 
 	 */
	public String preparaInclusao(){
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		listaDeMedicamentos = null;
		medicamentoCorrente = new Indicacao();
		return PAGINA_NEW;
	}
	
	/**
	 * 
	 * Método que zera as variáveis
	 * relacionadas a lista de 
	 * medicamentos. De forma que
	 * quando for chamado, a lista
	 * será atualizada.
	 * 
	 * @return Retorna uma String que corresponde
	 * ao nome do mapeamento da tela de listagem
	 * de medicamentos no faces config.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String preparaListagem(){
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		listaDeMedicamentos = null;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * ??????????????????????
	 * 
	 * @return
	 */
	public String voltar(){
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		listaDeMedicamentos = null;
		return PAGINA_LIST;
	}
	
	/* ************* Get & Set ************ */
	
	public String getCampoDeBusca() {
		return campoDeBusca;
	}

	public void setCampoDeBusca(String campoDeBusca) {
		this.campoDeBusca = campoDeBusca;
	}
	
	public SelectOneDataModel<String> getComboTiposDeBusca() {
		if(comboTiposDeBusca == null){
			List<String> tiposDeBusca = new ArrayList<String>(2);
			tiposDeBusca.add(BUSCA_POR_CODIGO);
			tiposDeBusca.add(BUSCA_POR_NOME);
			comboTiposDeBusca = SelectOneDataModel.criaComObjetoSelecionadoSemTextoInicial(tiposDeBusca, BUSCA_POR_CODIGO);
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
	
	public DataModel getListaDeMedicamentos(){
		if(listaDeMedicamentos == null){
			listaDeMedicamentos =  new ListDataModel(medicamentoService.recuperaListaDeMedicamentosPaginada());
		}
		return listaDeMedicamentos;
	}

	public void setListaDeMedicamentos(DataModel listaDeMedicamentos) {
		this.listaDeMedicamentos = listaDeMedicamentos;
	}

	public int getPaginaMedicamento() {
		return paginaMedicamento;
	}

	public void setPaginaMedicamento(int paginaMedicamento) {
		this.paginaMedicamento = paginaMedicamento;
	}

	public Indicacao getMedicamentoCorrente() {
		return medicamentoCorrente;
	}

	public void setMedicamentoCorrente(Indicacao medicamentoCorrente) {
		this.medicamentoCorrente = medicamentoCorrente;
	}
	
	
}
