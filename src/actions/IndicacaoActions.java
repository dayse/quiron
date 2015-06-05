package actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import modelo.Especialista;
import modelo.Indicacao;
import modelo.Parametro;
import modelo.Usuario;
import service.AvalIndicacaoEspecAppService;
import service.EspecialistaAppService;
import service.IndicacaoAppService;
import service.ParametroAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.SelectOneDataModel;

/**
 * 
 * IndicacaoActions é uma classe relacionada a manipulação de tela, ou seja, a
 * interação do usuário de fato dar-se-á através de objetos do tipo
 * IndicacaoActions quando estiver na tela de Indicações.
 * 
 * Objetos do "tipo actions" são managed beans.
 * 
 * @author bruno.oliveira
 *
 */
public class IndicacaoActions extends BaseActions implements Serializable {

	// Service
	private static IndicacaoAppService indicacaoService;
	private static AvalIndicacaoEspecAppService avalIndicacaoEspecService;
	private static ParametroAppService parametroService;
	private static EspecialistaAppService especialistaService;
	// Páginas
	private final String PAGINA_EDIT = "editIndicacao";
	private final String PAGINA_LIST = "listIndicacao";
	private final String PAGINA_NEW = "newIndicacao";
	private final String PAGINA_SHOW = "showIndicacao";
	private final String PAGINA_MEDIA_ESPEC = "mediaEspecialistas";
	
	// Componentes de Controle
	private static final long serialVersionUID = 1L;
	private String campoDeBusca;
	private int paginaIndicacao = 1;
	private boolean buscaEfetuada = false;
	private SelectOneDataModel<String> comboTiposDeBusca;
	private Indicacao indicacaoCorrente;
	private DataModel listaDeIndicacoes;
	private final String BUSCA_POR_CODIGO = "Código";
	private final String BUSCA_POR_NOME = "Nome";

	private DataModel listaDeMediasDeEspecialistasdeIndicacoes;
	private int paginaMediaEspecialistas = 1;
	private List<Parametro> listaDeParametros; 
	private int numParametros;
	
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
	public IndicacaoActions() throws Exception{
		try{
			indicacaoService = FabricaDeAppService.getAppService(IndicacaoAppService.class);
			avalIndicacaoEspecService = FabricaDeAppService.getAppService(AvalIndicacaoEspecAppService.class);
			parametroService = FabricaDeAppService.getAppService(ParametroAppService.class);
			especialistaService = FabricaDeAppService.getAppService(EspecialistaAppService.class);
			
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 
	 * Método para alteração de um determinado 
	 * registro de Indicação já cadastrado.
	 * 
	 * @return Caso ocorra erro, mantém na página de edição. 
	 * Caso contrário retorna para página de listagem de indicações
	 * e renderiza a mensagem de sucesso.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String altera(){
		try{
			indicacaoService.altera(indicacaoCorrente);
		}catch(AplicacaoException e){
			error(e.getMessage());
			return PAGINA_LIST;
		}
		info("indicacao.SUCESSO_ALTERACAO");
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		listaDeIndicacoes = null;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * Método utilizado para fazer a busca
	 * de um determinado indicacao no
	 * banco através de dados passados via
	 * formulário pelo usuário.
	 * 
	 * @return A lista de indicações atualizada
	 * com os resultados da pesquisa no banco ou
	 * uma mensagem de erro, caso algum ocorra.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String buscaIndicacao(){
		List<Indicacao> indicacoesEncontrados = null;
		if(campoDeBusca.trim().isEmpty()){
			error("indicacao.FORNECER_CAMPO_DE_BUSCA");
			return PAGINA_LIST;
		}else{
			listaDeIndicacoes = null;
			if(comboTiposDeBusca.getObjetoSelecionado().equals(BUSCA_POR_CODIGO)){
				indicacoesEncontrados = new ArrayList<Indicacao>(indicacaoService.recuperaIndicacaoPorCodigoLike(campoDeBusca));
			}else{
				indicacoesEncontrados = new ArrayList<Indicacao>(indicacaoService.recuperaIndicacaoPorNome(campoDeBusca));
			}
			if(indicacoesEncontrados.isEmpty()){
				error("indicacao.NAO_ENCONTRADO");
				listaDeIndicacoes = null;
				buscaEfetuada = false;
				return PAGINA_LIST;
			}else{
				info("indicacao.ENCONTRADOS");
			}
		}
		listaDeIndicacoes = new ListDataModel(indicacoesEncontrados);
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
	 * para a tela de listagem de indicações.
	 * 
	 * @return Retorna uma String que corresponde
	 * ao no mapeamento da tela de listagem de
	 * indicações.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String cancelar(){
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		listaDeIndicacoes = null;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * Método usado para exclusão de 
	 * determinado registro do
	 * banco de dados.
	 * 
	 * @return Atualiza a listagem de indicações
	 * na tela, ou se necessário renderiza uma
	 * mensagem de erro.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String exclui(){
		try{
			indicacaoService.exclui(indicacaoCorrente);
		}catch(AplicacaoException ex){
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		info("indicacao.SUCESSO_EXCLUSAO");
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		listaDeIndicacoes = null;
		return PAGINA_LIST;
	}
	
	/**
	 * Imprime um relatório contendo a lista dos especialistas
	 * cadastrados no sistema e o seu nível de peso avaliador.
	 * 
	 * @author bruno.oliveira
	 * 
	 */	
	public void imprimir(){
		try{
			List<Indicacao> listaDeIndicacao = indicacaoService.recuperaListaIndicacao();
			indicacaoService.gerarRelatorio(listaDeIndicacao);
		} catch (AplicacaoException e){
			e.printStackTrace();
		}
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
	 * atualizada de indicações com uma
	 * mensagem de sucesso.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String inclui(){
		try{
			indicacaoService.inclui(indicacaoCorrente);
		}catch(AplicacaoException ex){
			error(ex.getMessage());
			return PAGINA_NEW;
		}
		info("indicacao.SUCESSO_INCLUSAO");
		buscaEfetuada = false;
		listaDeIndicacoes = null;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * Método usado para carregar as
	 * informações especifícas de um
	 * determinado indicação
	 * na tela de detalhamento.
	 * 
	 * @return Retorna uma String
	 * que redireciona o usuário
	 * para a tela de detalhamento
	 * das informações do indicação.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String mostra(){
		indicacaoCorrente = (Indicacao) listaDeIndicacoes.getRowData();
		return PAGINA_SHOW;
	}
	
	/**
	 * Calcula a média das notas dadas pelos especialistas
	 * para cada indicação cadastrada.
	 * 
	 * @return String - redireciona para a página que exibe a média
	 *  dos especialistas para cada indicação.
	 *  
	 *  @author bruno.oliveira
	 *  
	 */
	public String mostraMediaEspecialistas(){

				List<Especialista> listaEspecialista = especialistaService.recuperaListaEspecialista();
				List<Indicacao> listaIndicacao = indicacaoService.recuperaListaIndicacao();
		
		if (listaEspecialista.isEmpty() || listaIndicacao.isEmpty() ){
				error("indicacao.INDICACAO_INEXISTENTES");
				return PAGINA_LIST;

		}
		
		listaDeMediasDeEspecialistasdeIndicacoes= new ListDataModel(
								avalIndicacaoEspecService.recuperaMediaDeAvaliacaoDeIndicacaoDeEspecialistas());
		
		listaDeParametros = parametroService.recuperaListaDeParametrosPaginada();
		
		return PAGINA_MEDIA_ESPEC;
	}
	
	/**
	 * 
	 * Método acionado antes da tela 
	 * de edição ser renderizada.
	 * Ele é responsável por capturar
	 * qual foi o indicação que o usuário
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
		indicacaoCorrente = (Indicacao) listaDeIndicacoes.getRowData();
		return PAGINA_EDIT;
	}
	
	/**
	 * 
	 * Método acionado antes do modal panel de exclusão ser renderizado. Ele é
	 * responsável por capturar qual foi o indicação que o usuário escolheu,
	 * de forma que a referência não se perca.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public void preparaExclusao(){
		indicacaoCorrente = (Indicacao) listaDeIndicacoes.getRowData();
	}
	
	/**
	 * 
	 * Método acionado antes da tela
	 * de inclusão ser renderizada. 
	 * Ele é responsável por preparar
	 * as instâncias de todas as entidades
	 * necessárias para a inclusão de um novo
	 * indicação. De forma a garantir que resíduos
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
		listaDeIndicacoes = null;
		indicacaoCorrente = new Indicacao();
		return PAGINA_NEW;
	}
	
	/**
	 * 
	 * Método que zera as variáveis
	 * relacionadas a lista de 
	 * indicações. De forma que
	 * quando for chamado, a lista
	 * será atualizada.
	 * 
	 * @return Retorna uma String que corresponde
	 * ao nome do mapeamento da tela de listagem
	 * de indicações no faces config.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String preparaListagem(){
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		listaDeIndicacoes = null;
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
	
	public DataModel getListaDeIndicacoes(){
		if(listaDeIndicacoes == null){
			listaDeIndicacoes =  new ListDataModel(indicacaoService.recuperaListaDeIndicacoesPaginada());
		}
		return listaDeIndicacoes;
	}

	public void setListaDeIndicacoes(DataModel listaDeIndicacoes) {
		this.listaDeIndicacoes = listaDeIndicacoes;
	}

	public int getPaginaIndicacao() {
		return paginaIndicacao;
	}

	public void setPaginaIndicacao(int paginaIndicacao) {
		this.paginaIndicacao = paginaIndicacao;
	}

	public Indicacao getIndicacaoCorrente() {
		return indicacaoCorrente;
	}

	public void setIndicacaoCorrente(Indicacao indicacaoCorrente) {
		this.indicacaoCorrente = indicacaoCorrente;
	}

	public DataModel getListaDeMediasDeEspecialistasdeIndicacoes() {
		return listaDeMediasDeEspecialistasdeIndicacoes;
	}

	public void setListaDeMediasDeEspecialistasdeIndicacoes(
			DataModel listaDeMediasDeEspecialistasdeIndicacoes) {
		this.listaDeMediasDeEspecialistasdeIndicacoes = listaDeMediasDeEspecialistasdeIndicacoes;
	}

	public List<Parametro> getListaDeParametros() {
		return this.listaDeParametros;
	}

	public void setListaDeParametros(List<Parametro> listaDeParametros) {
		this.listaDeParametros = listaDeParametros;
	}

	public int getPaginaMediaEspecialistas() {
		return paginaMediaEspecialistas;
	}

	public void setPaginaMediaEspecialistas(int paginaMediaEspecialistas) {
		this.paginaMediaEspecialistas = paginaMediaEspecialistas;
	}
	public int getNumParametros() {
		return this.listaDeParametros.size();
	}

	public void setNumParametros(int numParametros) {
		this.numParametros = numParametros;
	}
	
}
