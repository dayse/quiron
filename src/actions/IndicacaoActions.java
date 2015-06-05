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
 * IndicacaoActions � uma classe relacionada a manipula��o de tela, ou seja, a
 * intera��o do usu�rio de fato dar-se-� atrav�s de objetos do tipo
 * IndicacaoActions quando estiver na tela de Indica��es.
 * 
 * Objetos do "tipo actions" s�o managed beans.
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
	// P�ginas
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
	private final String BUSCA_POR_CODIGO = "C�digo";
	private final String BUSCA_POR_NOME = "Nome";

	private DataModel listaDeMediasDeEspecialistasdeIndicacoes;
	private int paginaMediaEspecialistas = 1;
	private List<Parametro> listaDeParametros; 
	private int numParametros;
	
	/**
	 * 
	 * Construtor respons�vel por instanciar os 
	 * services que ser�o usados no decorrer da classe.
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
	 * M�todo para altera��o de um determinado 
	 * registro de Indica��o j� cadastrado.
	 * 
	 * @return Caso ocorra erro, mant�m na p�gina de edi��o. 
	 * Caso contr�rio retorna para p�gina de listagem de indica��es
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
	 * M�todo utilizado para fazer a busca
	 * de um determinado indicacao no
	 * banco atrav�s de dados passados via
	 * formul�rio pelo usu�rio.
	 * 
	 * @return A lista de indica��es atualizada
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
	 * M�todo usado em diversos momentos
	 * para zerar as principais vari�veis
	 * usadas em situa��es de manipula��o
	 * de entidades, como por exemplo,
	 * edi��o ou inclus�o e renderizar
	 * para a tela de listagem de indica��es.
	 * 
	 * @return Retorna uma String que corresponde
	 * ao no mapeamento da tela de listagem de
	 * indica��es.
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
	 * M�todo usado para exclus�o de 
	 * determinado registro do
	 * banco de dados.
	 * 
	 * @return Atualiza a listagem de indica��es
	 * na tela, ou se necess�rio renderiza uma
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
	 * Imprime um relat�rio contendo a lista dos especialistas
	 * cadastrados no sistema e o seu n�vel de peso avaliador.
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
	 * M�todo usado para fazer a
	 * inclus�o de um novo
	 * registro no banco de dados.
	 * 
	 * @return Renderiza uma mensagem de erro,
	 * caso ocorra um problema na inclus�o.
	 * Ou redireciona para a tela de listagem
	 * atualizada de indica��es com uma
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
	 * M�todo usado para carregar as
	 * informa��es especif�cas de um
	 * determinado indica��o
	 * na tela de detalhamento.
	 * 
	 * @return Retorna uma String
	 * que redireciona o usu�rio
	 * para a tela de detalhamento
	 * das informa��es do indica��o.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String mostra(){
		indicacaoCorrente = (Indicacao) listaDeIndicacoes.getRowData();
		return PAGINA_SHOW;
	}
	
	/**
	 * Calcula a m�dia das notas dadas pelos especialistas
	 * para cada indica��o cadastrada.
	 * 
	 * @return String - redireciona para a p�gina que exibe a m�dia
	 *  dos especialistas para cada indica��o.
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
	 * M�todo acionado antes da tela 
	 * de edi��o ser renderizada.
	 * Ele � respons�vel por capturar
	 * qual foi o indica��o que o usu�rio
	 * escolheu, de forma que seja poss�vel
	 * recuperar as informa��es necess�rias
	 * do banco.
	 * 
	 * @return Caso a busca ao banco n�o retorne
	 * nada exibe uma mensagem de erro sem redirecionar
	 * a tela. Caso se obtenha sucesso, o usu�rio �
	 * redirecionado para a tela de edi��o.
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
	 * M�todo acionado antes do modal panel de exclus�o ser renderizado. Ele �
	 * respons�vel por capturar qual foi o indica��o que o usu�rio escolheu,
	 * de forma que a refer�ncia n�o se perca.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public void preparaExclusao(){
		indicacaoCorrente = (Indicacao) listaDeIndicacoes.getRowData();
	}
	
	/**
	 * 
	 * M�todo acionado antes da tela
	 * de inclus�o ser renderizada. 
	 * Ele � respons�vel por preparar
	 * as inst�ncias de todas as entidades
	 * necess�rias para a inclus�o de um novo
	 * indica��o. De forma a garantir que res�duos
	 * de procedimentos antigos n�o diminuam
	 * a garantia de confiabilidade da inclus�o.
	 * 
	 * @return Retorna uma String que corresponde
	 * ao nome do mapeamento da tela de inclus�o
	 * redirecionado o usu�rio para a mesma.
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
	 * M�todo que zera as vari�veis
	 * relacionadas a lista de 
	 * indica��es. De forma que
	 * quando for chamado, a lista
	 * ser� atualizada.
	 * 
	 * @return Retorna uma String que corresponde
	 * ao nome do mapeamento da tela de listagem
	 * de indica��es no faces config.
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
