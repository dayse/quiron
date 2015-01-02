package actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import service.AvalIndicacaoEspecAppService;
import service.EspecialistaAppService;
import service.ParametroAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.SelectOneDataModel;
import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.Parametro;
import modelo.Usuario;

/**
 * 
 * EspecialistaActions � uma classe relacionada a manipula��o de tela, ou seja,
 * a intera��o do usu�rio de fato dar-se-� atrav�s de objetos do tipo
 * EspecialistaActions quando estiver na tela de Especialistas.
 * 
 * Objetos do "tipo actions" s�o managed beans.
 * 
 * @author bruno.oliveira
 * 
 */
public class EspecialistaActions extends BaseActions implements Serializable {

	// Services
	public static EspecialistaAppService especialistaService;
	public static AvalIndicacaoEspecAppService avalIndicacaoEspecService;
	public static ParametroAppService parametroService;

	// P�ginas
	public final String PAGINA_LIST = "listEspecialista";
	public final String PAGINA_NEW = "newEspecialista";
	public final String PAGINA_SHOW = "showEspecialista";
	public final String PAGINA_EDIT = "editEspecialista";
	public final String PAGINA_AVALIACAO = "avalEspecialista";
	public final String PAGINA_EDIT_AVAL = "editAvalEspecialista";

	// Componentes de Controle & Vari�veis de Tela
	private static final long serialVersionUID = 1L;
	private int numParametros;
	private DataModel listaDeEspecialistas;
	private DataModel listaDeAvaliacao;
	private List<Parametro> listaDeParametros;
	private List<AvalIndicacaoEspec> avaliacaoAlterada;
	private Especialista especialistaCorrente;
	private Indicacao indicacaoCorrente;
	private boolean buscaEfetuada = false;
	private String campoDeBusca;
	public final String BUSCA_POR_CODIGO = "C�digo";
	public final String BUSCA_POR_NOME = "Nome";
	private SelectOneDataModel<String> comboTiposDeBusca;

	/**
	 * 
	 * Construtor respons�vel por instanciar os services que ser�o usados no
	 * decorrer da classe;
	 * 
	 * @throws Exception
	 *             - Retorna uma exception caso ocorra alguma problema no
	 *             carregamento dos services.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public EspecialistaActions() throws Exception {
		try {
			especialistaService = FabricaDeAppService
					.getAppService(EspecialistaAppService.class);
			avalIndicacaoEspecService = FabricaDeAppService
					.getAppService(AvalIndicacaoEspecAppService.class);
			parametroService = FabricaDeAppService
					.getAppService(ParametroAppService.class);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 
	 * M�todo usado para alterar um refisto de especialista no banco de dados.
	 * 
	 * @return Caso ocorra erro envia uma mensagem de erro a tela de edi��o. Em
	 *         caso de sucesso, redireciona o usu�rio para a tela de listagem de
	 *         especialistas e mostra uma mensagem de sucesso.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String altera() {
		try {
			especialistaService.alteraComSeguranca(especialistaCorrente, sessaoUsuarioCorrente.getUsuarioLogado());
		} catch (AplicacaoException e) {
			error(e.getMessage());
			return PAGINA_EDIT;
		}
		info("especialista.SUCESSO_ALTERACAO");
		listaDeEspecialistas = null;
		avaliacaoAlterada = null;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * M�todo que realiza a busca de um especialista dentre os
	 * que est�o cadastrados no banco.
	 * 
	 * @return lista de especialistas que combinam com o termo buscado, se existirem.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String buscaEspecialista(){
		List<Especialista> especialistasEncontrados = null;
		if(campoDeBusca.trim().isEmpty()){
			error("especialista.FORNECER_CAMPO_DE_BUSCA");
			return PAGINA_LIST;
		}else{
			listaDeEspecialistas = null;
			if(comboTiposDeBusca.getObjetoSelecionado().equals(BUSCA_POR_CODIGO)){
				especialistasEncontrados = new ArrayList<Especialista>(especialistaService.recuperaEspecialistaPorCodigoLike(campoDeBusca));
			}else{
				especialistasEncontrados = new ArrayList<Especialista>(especialistaService.recuperaEspecialistaPorNomeLike(campoDeBusca));
			}
			if(especialistasEncontrados.isEmpty()){
				error("especialista.NAO_ENCONTRADO");
				listaDeEspecialistas = null;
				buscaEfetuada = false;
				return PAGINA_LIST;
			}else{
				info("especialista.ENCONTRADOS");
			}
		}
		listaDeEspecialistas = new ListDataModel(especialistasEncontrados);
		buscaEfetuada = true;
		return PAGINA_LIST;
	}	

	/**
	 * 
	 * M�todo usado para alterar a avalia��o para determinado especialista.
	 * 
	 * @return Caso ocorra erro envia uma mensagem para a tela de edi��o. Em
	 *         caso de sucesso, redireciona o usu�rio para a tela de listagem de
	 *         avalia��es e exibe uma mensagem de sucesso.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String alteraAvaliacao() {
		for(AvalIndicacaoEspec avaliacao : avaliacaoAlterada){
			try {
				avalIndicacaoEspecService.altera(avaliacao);
			} catch (AplicacaoException e) {
				error(e.getMessage());
				return PAGINA_EDIT_AVAL;
			}
		}
		info("parametro.SUCESSO_ALTERACAO");
		avaliacaoAlterada = null;
		indicacaoCorrente = null;
		listaDeParametros = parametroService.recuperaListaDeParametros();
		listaDeAvaliacao = null;
		return PAGINA_AVALIACAO;
	}

	/**
	 * 
	 * M�todo para inclus�o de especialista no banco de dados.
	 * 
	 * @return Retorna uma mensagem de erro a tela de inclus�o, caso ocorra, ou
	 *         uma mensagem de sucesso para a tela atualizada de listagem de
	 *         especialistas.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String inclui() {
		try {
			especialistaService.incluiComSeguranca(especialistaCorrente, sessaoUsuarioCorrente.getUsuarioLogado());
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_NEW;
		}
		info("especialista.SUCESSO_INCLUSAO");
		listaDeEspecialistas = null;
		avaliacaoAlterada = null;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * M�todo utilizado para sair de um tela interna do menu Especialista e
	 * voltar para a tela de listagem de especialistas. Al�m disso, zera vari�veis
	 * importantes para que n�o as mesmas n�o fiquem com dados residuais das
	 * �ltimas a��es feitas pelo usu�rio.
	 * 
	 * @return Texto que corresponde ao mapeamento da tela de listagem de
	 *         especialistas no facesconfig.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String cancelar() {
		listaDeEspecialistas = null;
		avaliacaoAlterada = null;
		listaDeAvaliacao = null;
		indicacaoCorrente = new Indicacao();
		especialistaCorrente = new Especialista();
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * M�todo utilizado para sair de uma tela interna de avalia��o de indica��es
	 * do especialista.
	 * Este m�todo � respons�vel por zerar as vari�veis quando o usu�rio
	 * optar cancelar a edi��o de uma avalia��o de um especialista para uma indica��o
	 * (por exemplo).
	 * 
	 * @return para tela de listagem de avalia��es de um especialista para uma indica��o.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String cancelarAvaliacao(){
		listaDeAvaliacao = null;
		avaliacaoAlterada = null;
		indicacaoCorrente = new Indicacao();
		return PAGINA_AVALIACAO;
	}

	/**
	 * 
	 * M�todo utilizado para excluir um registro de especialista do banco de
	 * dados.
	 * 
	 * @return Retorna uma mensagem de erro a tela caso ocorra, ou uma mensagem
	 *         de sucesso para a tela atualizada de listagem de especialistas.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String exclui() {
		try {
			especialistaService.excluiComSeguranca(especialistaCorrente, sessaoUsuarioCorrente.getUsuarioLogado());
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		info("especialista.SUCESSO_EXCLUSAO");
		listaDeEspecialistas = null;
		avaliacaoAlterada = null;
		return PAGINA_LIST;
	}
	
	/**
	 * Imprimi um relat�rio contendo a lista dos especialistas
	 * cadastrados no sistema e o seu n�vel de peso avaliador.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public void imprimir(){
		try{
			List<Especialista> listaDeEspecialistas = especialistaService.recuperaListaEspecialista();
			especialistaService.gerarRelatorio(listaDeEspecialistas);
		} catch (AplicacaoException e){
			e.printStackTrace();
		} 
	}

	/**
	 * Imprimi o relat�rio de avalia��es de um especialista
	 * para todas as indica��es cadastradas no sistema.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public void imprimirAvaliacaoDeUmEspecialistaParaIndicacoes(){
		try {
			List<AvalIndicacaoEspec> listaDeAvaliacao = 
					avalIndicacaoEspecService.recuperaListaDeAvaliacaoEspecComIndicacaoDeUmEspec(especialistaCorrente);
			especialistaService.gerarRelatorioDeAvaliacaoDeUmEspecialistaParaIndicacoes(listaDeAvaliacao);
		} catch (AplicacaoException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * 
	 * M�todo usado para mostrar as informa��es detalhadas do especialista.
	 * 
	 * @return Texto que corresponde ao mapeamento da tela de exibi��o dos dados
	 *         detalhados de um especialistas.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String mostra() {
		especialistaCorrente = (Especialista) listaDeEspecialistas.getRowData();
		return PAGINA_SHOW;
	}

	/**
	 * 
	 * M�todo acionado antes que a tela de edi��o seja renderizada. Salva o
	 * especialista selecionado pelo usu�rio para que a refer�ncia n�o se perca.
	 * 
	 * @return Texto que corresponde ao mapeamento da tela de edi��o de
	 *         especialistas.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String preparaAlteracao() {
		especialistaCorrente = (Especialista) listaDeEspecialistas.getRowData();
		return PAGINA_EDIT;
	}

	/**
	 * 
	 * M�todo acionado antes que a tela de edi��o de avalia��o seja renderizada.
	 * Salva a avalia��o selecionada pelo usu�rio para que a refer�ncia n�o se
	 * perca.
	 * 
	 * @return Texto que corresponde ao mapeamento da tela de edi��o de
	 *         avalia��o de um determinado especialista.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String preparaAlteracaoAval() {
		return PAGINA_EDIT_AVAL;
	}

	/**
	 * 
	 * M�todo acionado antes que a tela de avalia��o seja renderizada. Salva o
	 * especialista selecionado e zera a inst�ncia de avalia��o para garantir
	 * que n�o haja res�duos.
	 * 
	 * @return Texto que corresponde ao mapeamento da tela de avalia��o de um
	 *         determinado especialista.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String preparaAvaliacao() {
		especialistaCorrente = (Especialista) listaDeEspecialistas.getRowData();
		listaDeAvaliacao = null;
		avaliacaoAlterada = null;
		listaDeParametros = parametroService.recuperaListaDeParametros();
		return PAGINA_AVALIACAO;
	}

	/**
	 * 
	 * M�todo acionado antes que o modal panel de exclus�o seja renderizado.
	 * Salva o especialista selecionado para que a refer�ncia n�o se perca.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public void preparaExclusao() {
		especialistaCorrente = (Especialista) listaDeEspecialistas.getRowData();
	}

	/**
	 * 
	 * M�todo acionado antes que a tela de inclus�o seja renderizada. Zera a
	 * inst�ncia de especialista para garantir que n�o haja res�duos.
	 * 
	 * @return Texto que corresponde ao mapeamento da tela de inclus�o de um
	 *         determinado especialista.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String preparaInclusao() {
		especialistaCorrente = new Especialista();
		return PAGINA_NEW;
	}

	/*     ************* Get & Set ************ */

	public DataModel getListaDeEspecialistas() {
		if (listaDeEspecialistas == null) {
			listaDeEspecialistas = new ListDataModel(especialistaService
					.recuperaListaDeEspecialistasPaginada());
		}
		return listaDeEspecialistas;
	}

	public void setListaDeEspecialistas(DataModel listaDeEspecialistas) {
		this.listaDeEspecialistas = listaDeEspecialistas;
	}

	public Especialista getEspecialistaCorrente() {
		return especialistaCorrente;
	}

	public void setEspecialistaCorrente(Especialista especialista) {
		this.especialistaCorrente = especialista;
	}

	public List<AvalIndicacaoEspec> getAvaliacaoAlterada() {
		if (avaliacaoAlterada == null) {
			avaliacaoAlterada = avalIndicacaoEspecService
				.recuperaListaDeAvaliacaoPorEspecialistaPorIndicacao(especialistaCorrente, indicacaoCorrente);
		}
		return avaliacaoAlterada;
	}

	public void setAvaliacaoAlterada(List<AvalIndicacaoEspec> avaliacaoAlterada) {
		this.avaliacaoAlterada = avaliacaoAlterada;
	}

	public Indicacao getIndicacaoCorrente() {
		return indicacaoCorrente;
	}

	public void setIndicacaoCorrente(Indicacao indicacaoCorrente) {
		this.indicacaoCorrente = indicacaoCorrente;
	}
	
	public int getNumParametros(){
		return this.listaDeParametros.size();
	}
	
	public List<Parametro> getListaDeParametros(){
		return this.listaDeParametros;
	}
	
	public void setListaDeParametros(List<Parametro> listaDeParametros){
		this.listaDeParametros = listaDeParametros;
	}

	public DataModel getListaDeAvaliacao() {
		if(listaDeAvaliacao == null){
			listaDeAvaliacao = new ListDataModel(especialistaService.recuperaConjuntoAvaliacaoDeUmEspecialista(especialistaCorrente));
		}
		return listaDeAvaliacao;
	}

	public void setListaDeAvaliacao(DataModel listaDeAvaliacao) {
		this.listaDeAvaliacao = listaDeAvaliacao;
	}

	public boolean isBuscaEfetuada() {
		return buscaEfetuada;
	}

	public void setBuscaEfetuada(boolean buscaEfetuada) {
		this.buscaEfetuada = buscaEfetuada;
	}

	public String getCampoDeBusca() {
		return campoDeBusca;
	}

	public void setCampoDeBusca(String campoDeBusca) {
		if(comboTiposDeBusca == null){
			List<String> tiposDeBusca = new ArrayList<String>(2);
			tiposDeBusca.add(BUSCA_POR_CODIGO);
			tiposDeBusca.add(BUSCA_POR_NOME);
			comboTiposDeBusca = SelectOneDataModel.criaComObjetoSelecionadoSemTextoInicial(tiposDeBusca, BUSCA_POR_CODIGO);
		}				
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
		
}
