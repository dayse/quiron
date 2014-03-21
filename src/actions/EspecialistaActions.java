package actions;

import java.io.Serializable;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import service.AvalIndicacaoEspecAppService;
import service.EspecialistaAppService;
import service.IndicacaoAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;

import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;

/**
 * 
 * EspecialistaActions é uma classe relacionada a manipulação de tela, ou seja,
 * a interação do usuário de fato dar-se-á através de objetos do tipo
 * EspecialistaActions quando estiver na tela de Especialistas.
 * 
 * Objetos do "tipo actions" são managed beans.
 * 
 * @author bruno.oliveira
 * 
 */
public class EspecialistaActions extends BaseActions implements Serializable {

	// Services
	public static EspecialistaAppService especialistaService;
	public static IndicacaoAppService indicacaoService;
	public static AvalIndicacaoEspecAppService avalIndicacaoEspecService;

	// Páginas
	public final String PAGINA_LIST = "listEspecialista";
	public final String PAGINA_NEW = "newEspecialista";
	public final String PAGINA_SHOW = "showEspecialista";
	public final String PAGINA_EDIT = "editEspecialista";
	public final String PAGINA_AVALIACAO = "avalEspecialista";
	public final String PAGINA_EDIT_AVAL = "editAvalEspecialista";

	// Componentes de Controle & Variáveis de Tela
	private int paginaEspecialista = 1;
	private int paginaIndicacao = 1;
	private static final long serialVersionUID = 1L;
	private DataModel listaDeEspecialistas;
	private DataModel listaDeIndicacao;
	private DataModel listaDeAvalIndicEspec;
	private Especialista especialistaCorrente;
	private Indicacao indicacaoCorrente;
	private AvalIndicacaoEspec avalIndicacaoEspecCorrente;

	/**
	 * 
	 * Construtor responsável por instanciar os services que serão usados no
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
			indicacaoService = FabricaDeAppService
					.getAppService(IndicacaoAppService.class);
			avalIndicacaoEspecService = FabricaDeAppService
					.getAppService(AvalIndicacaoEspecAppService.class);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 
	 * Método usado para alterar um refisto de especialista no banco de dados.
	 * 
	 * @return Caso ocorra erro envia uma mensagem de erro a tela de edição. Em
	 *         caso de sucesso, redireciona o usuário para a tela de listagem de
	 *         especialistas e mostra uma mensagem de sucesso.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String altera() {
		try {
			especialistaService.altera(especialistaCorrente);
		} catch (AplicacaoException e) {
			error(e.getMessage());
			return PAGINA_EDIT;
		}
		info("especialista.SUCESSO_ALTERACAO");
		listaDeEspecialistas = null;
		listaDeAvalIndicEspec = null;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * Método usado para alterar a avaliação para determinado especialista.
	 * 
	 * @return Caso ocorra erro envia uma mensagem para a tela de edição. Em
	 *         caso de sucesso, redireciona o usuário para a tela de listagem de
	 *         avaliações e exibe uma mensagem de sucesso.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String alteraAvaliacao() {
		try {
			avalIndicacaoEspecService.altera(avalIndicacaoEspecCorrente);
		} catch (AplicacaoException e) {
			error(e.getMessage());
			return PAGINA_EDIT_AVAL;
		}
		info("parametros.SUCESSO_ALTERACAO");
		listaDeAvalIndicEspec = null;
		return PAGINA_AVALIACAO;
	}

	/**
	 * 
	 * Método para inclusão de especialista no banco de dados.
	 * 
	 * @return Retorna uma mensagem de erro a tela de inclusão, caso ocorra, ou
	 *         uma mensagem de sucesso para a tela atualizada de listagem de
	 *         especialistas.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String inclui() {
		try {
			especialistaService.inclui(especialistaCorrente);
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_NEW;
		}
		info("especialista.SUCESSO_INCLUSAO");
		listaDeEspecialistas = null;
		listaDeAvalIndicEspec = null;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * Método utilizado para sair de um tela interna do menu Especialista e
	 * voltar para a tela de listagem de especialistas. Além, zera variáveis
	 * importantes para que não as mesmas não fiquem com dados residuais das
	 * últimas ações feitas pelo usuário.
	 * 
	 * @return Texto que corresponde ao mapeamento da tela de listagem de
	 *         especialistas no facesconfig.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String cancelar() {
		listaDeEspecialistas = null;
		listaDeAvalIndicEspec = null;
		especialistaCorrente = new Especialista();
		return PAGINA_LIST;
	}

	/**
	 * 
	 * Método utilizado para excluir um registro de especialista do banco de
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
			especialistaService.exclui(especialistaCorrente);
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		info("especialista.SUCESSO_EXCLUSAO");
		listaDeEspecialistas = null;
		listaDeAvalIndicEspec = null;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * Método usado para mostrar as informações detalhadas do especialista.
	 * 
	 * @return Texto que corresponde ao mapeamento da tela de exibição dos dados
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
	 * Método acionado antes que a tela de edição seja renderizada. Salva o
	 * especialista selecionado pelo usuário para que a referência não se perca.
	 * 
	 * @return Texto que corresponde ao mapeamento da tela de edição de
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
	 * Método acionado antes que a tela de edição de avaliação seja renderizada.
	 * Salva a avaliação selecionada pelo usuário para que a referência não se
	 * perca.
	 * 
	 * @return Texto que corresponde ao mapeamento da tela de edição de
	 *         avaliação de um determinado especialista.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String preparaAlteracaoAval() {
		avalIndicacaoEspecCorrente = (AvalIndicacaoEspec) listaDeAvalIndicEspec
				.getRowData();
		return PAGINA_EDIT_AVAL;
	}

	/**
	 * 
	 * Método acionado antes que a tela de avaliação seja renderizada. Salva o
	 * especialista selecionado e zera a instância de avaliação para garantir
	 * que não haja resíduos.
	 * 
	 * @return Texto que corresponde ao mapeamento da tela de avaliação de um
	 *         determinado especialista.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String preparaAvaliacao() {
		especialistaCorrente = (Especialista) listaDeEspecialistas.getRowData();
		listaDeAvalIndicEspec = null;
		avalIndicacaoEspecCorrente = new AvalIndicacaoEspec();
		return PAGINA_AVALIACAO;
	}

	/**
	 * 
	 * Método acionado antes que o modal panel de exclusão seja renderizado.
	 * Salva o especialista selecionado para que a referência não se perca.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public void preparaExclusao() {
		especialistaCorrente = (Especialista) listaDeEspecialistas.getRowData();
	}

	/**
	 * 
	 * Método acionado antes que a tela de inclusão seja renderizada. Zera a
	 * instância de especialista para garantir que não haja resíduos.
	 * 
	 * @return Texto que corresponde ao mapeamento da tela de inclusão de um
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

	public AvalIndicacaoEspec getAvalIndicacaoEspecCorrente() {
		return avalIndicacaoEspecCorrente;
	}

	public void setAvalIndicacaoEspecCorrente(
			AvalIndicacaoEspec avalIndicacaoEspec) {
		this.avalIndicacaoEspecCorrente = avalIndicacaoEspec;
	}

	public DataModel getListaDeIndicacao() {
		if (listaDeIndicacao == null) {
			listaDeIndicacao = new ListDataModel(indicacaoService
					.recuperaListaDeMedicamentosPaginada());
		}
		return listaDeIndicacao;
	}

	public void setListaDeIndicacao(DataModel listaDeIndicacao) {
		this.listaDeIndicacao = listaDeIndicacao;
	}

	public DataModel getListaDeAvalIndicEspec() {
		if (listaDeAvalIndicEspec == null) {
			listaDeAvalIndicEspec = new ListDataModel(
					avalIndicacaoEspecService
							.recuperaListaDeAvaliacaoEspecComIndicacaoDeUmEspec(especialistaCorrente));
		}
		return listaDeAvalIndicEspec;
	}

	public void setListaDeAvalIndicEspec(DataModel listaDeAvalIndicEspec) {
		this.listaDeAvalIndicEspec = listaDeAvalIndicEspec;
	}

	public Indicacao getIndicacaoCorrente() {
		return indicacaoCorrente;
	}

	public void setIndicacaoCorrente(Indicacao indicacaoCorrente) {
		this.indicacaoCorrente = indicacaoCorrente;
	}

	public int getPaginaEspecialista() {
		return paginaEspecialista;
	}

	public void setPaginaEspecialista(int pagina) {
		this.paginaEspecialista = pagina;
	}

	public int getPaginaIndicacao() {
		return paginaIndicacao;
	}

	public void setPaginaIndicacao(int pagina) {
		this.paginaIndicacao = pagina;
	}
}
