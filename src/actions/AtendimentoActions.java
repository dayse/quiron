	package actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import DAO.exception.ObjetoNaoEncontradoException;

import modelo.Atendimento;
import modelo.Paciente;
import modelo.Anamnese;
import modelo.Usuario;

import service.AnamneseAppService;
import service.AtendimentoAppService;
import service.PacienteAppService;
import service.TipoUsuarioAppService;
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;

import util.SelectOneDataModel;

/**
 * 
 * AtendimentoActions é uma classe relacionada a manipulação de tela, ou seja, a
 * interação do usuário de fato dar-se-á através de objetos do tipo
 * AtendimentoActions quando estiver na tela de Atendimentos.
 * 
 * Objetos do "tipo actions" são managed beans.
 * 
 * @author bruno.oliveira
 * 
 */
public class AtendimentoActions extends BaseActions implements Serializable {

	// Componentes de Controle
	private static final long serialVersionUID = 1L;
	private SelectOneDataModel<Usuario> comboMedicos;
	private SelectOneDataModel<Usuario> comboTecnicos;
	private SelectOneDataModel<String> comboStatus;
	private SelectOneDataModel<String> comboTiposDeBusca;
	private DataModel listaDePacientes;
	private DataModel listaDeAtendimentos;
	private DataModel listaAvaliacao;
	private List<String> status = new ArrayList<String>();

	// Páginas
	public final String PAGINA_EDIT = "editAtendimento";
	public final String PAGINA_LIST = "listAtendimento";
	public final String PAGINA_NEW = "newAtendimento";
	public final String PAGINA_SHOW = "showAtendimento";
	public final String PAGINA_STATUS = "listStatusAtendimento";
	public final String PAGINA_AVALIACAO = "listAvaliacao";
	public final String BUSCA_POR_COD_PACIENTE = "Cód. do Paciente";
	public final String BUSCA_POR_NOME = "Nome";

	// Services
	private static AtendimentoAppService atendimentoService;
	private static AnamneseAppService anamneseService;
	private static PacienteAppService pacienteService;
	private static TipoUsuarioAppService tipoUsuarioService;
	private static UsuarioAppService usuarioService;

	// Variáveis de tela
	private Anamnese anamneseCorrente;
	private Atendimento atendimentoCorrente;
	private Paciente pacienteCorrente;
	private Date dataAtendimento;
	private int paginaAtendimento = 1;
	private int pagina;
	private boolean tecnicoEditavel;
	private boolean clinicoEditavel;
	private boolean maioridade;
	private String campoDeBusca;

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
	public AtendimentoActions() throws Exception {
		try {
			atendimentoService = FabricaDeAppService
					.getAppService(AtendimentoAppService.class);
			anamneseService = FabricaDeAppService
					.getAppService(AnamneseAppService.class);
			pacienteService = FabricaDeAppService
					.getAppService(PacienteAppService.class);
			tipoUsuarioService = FabricaDeAppService
					.getAppService(TipoUsuarioAppService.class);
			usuarioService = FabricaDeAppService
					.getAppService(UsuarioAppService.class);
		} catch (Exception e) {
			throw e;
		}

		pagina = ((PacienteActions) getManagedBean("pacienteActions"))
				.getPagina();

		status.add("Aberto");
		status.add("Em atendimento");
		status.add("Encerrado");
	}

	/**
	 * 
	 * Método para alteração de um determinado registro de Atendimento já
	 * cadastrado.
	 * 
	 * @return Caso ocorra erro, mantém na página de edição. Caso contrário
	 *         retorna para página de listagem de atendimentos e renderiza a
	 *         mensagem de sucesso.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String altera() {
		atendimentoCorrente.setMedico(comboMedicos.getObjetoSelecionado());
		atendimentoCorrente.setTecnico(comboTecnicos.getObjetoSelecionado());
		atendimentoCorrente.setStatus(comboStatus.getObjetoSelecionado());
		try {
			atendimentoService.altera(atendimentoCorrente);
		} catch (AplicacaoException e) {
			error(e.getMessage());
			return PAGINA_EDIT;
		}
		try {
			anamneseService.altera(anamneseCorrente);
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_EDIT;
		}
		logUsuarioAutenticadoMsg("Atendimento - Altera atendimento:" + atendimentoCorrente.getCodAtendimento());
		info("atendimento.SUCESSO_ALTERACAO");
		return PAGINA_LIST;
	}

	/**
	 * 
	 * Método que calcula a avaliação para um determinado atendimento.
	 * 
	 * @return Redireciona para a página de avaliação, exibindo os dados da
	 *         avaliação, por parâmetro e por indicação.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String calculaAvaliacao() {
		listaAvaliacao = new ListDataModel(anamneseService
				.recuperaAvaliacaoCalculadaPorIndicacao(atendimentoCorrente));
		try {
			anamneseCorrente = anamneseService
					.recuperaAnamnesePorAtendimento(atendimentoCorrente);
		} catch (ObjetoNaoEncontradoException ex) {
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		try {
			comboMedicos = SelectOneDataModel.criaComObjetoSelecionadoSemTextoInicial(usuarioService
					.recuperaListaDeUsuarioPorTipo(tipoUsuarioService
							.recuperaTipoUsuarioClinico()), atendimentoCorrente.getMedico());
		} catch (AplicacaoException e) {
			e.printStackTrace();
		}
		try {
			comboTecnicos = SelectOneDataModel
					.criaComObjetoSelecionadoSemTextoInicial(
							usuarioService
									.recuperaListaDeUsuarioPorTipo(tipoUsuarioService
											.recuperaTipoUsuarioTecnico()),
							atendimentoCorrente.getTecnico());
		} catch (AplicacaoException e) {
			e.printStackTrace();
		}
		comboStatus = SelectOneDataModel.criaComObjetoSelecionado(status, atendimentoCorrente.getStatus());
		return PAGINA_AVALIACAO;
	}

	/**
	 * 
	 * Método disponível na tela de avaliação que redireciona o usuário para a
	 * tela de listagem de antedimentos por paciente.
	 * 
	 * @return Retorna uma String que corresponde ao no mapeamento da tela de
	 *         listagem de atendimento por paciente.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String cancelaAvaliacao() {
		listaDeAtendimentos = null;
		listaDePacientes = null;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * Método usado em diversos momentos para zerar as principais variáveis
	 * usadas em situações de manipulação de entidades, como por exemplo, edição
	 * ou inclusão e renderizar para a tela de listagem de atendimentos.
	 * 
	 * @return Retorna uma String que corresponde ao no mapeamento da tela de
	 *         listagem de atendimento por paciente.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String cancelar() {
		listaDePacientes = null;
		listaDeAtendimentos = null;
		pacienteCorrente = null;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * Método usado para exclusão de determinado registro do banco de dados.
	 * 
	 * @return Atualiza a listagem de atendimentos na tela, ou se necessário
	 *         renderiza uma mensagem de erro.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String exclui() {
		/**
		 * Implementar exclusão em cascata. Economia de código!
		 */
		try {
			anamneseCorrente = anamneseService
					.recuperaAnamnesePorAtendimento(atendimentoCorrente);
		} catch (ObjetoNaoEncontradoException ex) {
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		try {
			anamneseService.exclui(anamneseCorrente);
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		try {
			atendimentoService.exclui(atendimentoCorrente);
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		logUsuarioAutenticadoMsg("Atendimento - Exclui atendimento:" + atendimentoCorrente.getCodAtendimento());
		info("atendimento.SUCESSO_EXCLUSAO");
		listaDeAtendimentos = null;
		listaDePacientes = null;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * Método usado para fazer a inclusão de um novo registro no banco de dados.
	 * 
	 * @return Renderiza uma mensagem de erro, caso ocorra um problema na
	 *         inclusão. Ou redireciona para a tela de listagem atualizada de
	 *         atendimentos com uma mensagem de sucesso.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String inclui() {
		atendimentoCorrente.setDataAtendimento(dataAtendimento);
		atendimentoCorrente.setMedico(comboMedicos.getObjetoSelecionado());
		atendimentoCorrente.setTecnico(comboTecnicos.getObjetoSelecionado());
		atendimentoCorrente.setStatus(comboStatus.getObjetoSelecionado());
		try {
			atendimentoService.inclui(atendimentoCorrente);
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_NEW;
		}
		anamneseCorrente.setAtendimento(atendimentoCorrente);
		try {
			anamneseService.inclui(anamneseCorrente);
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_NEW;
		}
		logUsuarioAutenticadoMsg("Atendimento - Inclui atendimento:" + atendimentoCorrente.getCodAtendimento());
		info("atendimento.SUCESSO_INCLUSAO");
		listaDeAtendimentos = null;
		listaDePacientes = null;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * Método usado para carregar as informações especifícas de um determinado
	 * atendimento e anamnese na tela de detalhamento.
	 * 
	 * @return Retorna uma String que redireciona o usuário para a tela de
	 *         detalhamento das informações do atendimento e de sua respectiva
	 *         anamnese.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String mostrar() {
		try {
			anamneseCorrente = anamneseService
					.recuperaAnamnesePorAtendimento(atendimentoCorrente);
		} catch (ObjetoNaoEncontradoException ex) {
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		try {
			comboMedicos = SelectOneDataModel.criaComObjetoSelecionadoSemTextoInicial(usuarioService
					.recuperaListaDeUsuarioPorTipo(tipoUsuarioService
							.recuperaTipoUsuarioClinico()), atendimentoCorrente.getMedico());
		} catch (AplicacaoException e) {
			e.printStackTrace();
		}
		
		if(atendimentoCorrente.getTecnico() == null){
			comboTecnicos = null;
		}else{
			try {
				comboTecnicos = SelectOneDataModel
						.criaComObjetoSelecionadoSemTextoInicial(
								usuarioService
										.recuperaListaDeUsuarioPorTipo(tipoUsuarioService
												.recuperaTipoUsuarioTecnico()),
								atendimentoCorrente.getTecnico());
			} catch (AplicacaoException e) {
				e.printStackTrace();
			}
		}
		
		comboStatus = SelectOneDataModel.criaComObjetoSelecionado(status, atendimentoCorrente.getStatus());
		return PAGINA_SHOW;
	}

	/**
	 * 
	 * Método acionado antes da tela de edição ser renderizada. Ele é
	 * responsável por capturar qual foi o paciente que o usuário escolheu, de
	 * forma que seja possível recuperar as informações necessárias do banco.
	 * 
	 * @return Caso a busca ao banco não retorne nada exibe uma mensagem de erro
	 *         sem redirecionar a tela. Caso se obtenha sucesso, o usuário é
	 *         redirecionado para a tela de edição.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String preparaAlteracao() {
		pacienteCorrente = atendimentoCorrente.getPaciente();
		try {
			anamneseCorrente = anamneseService
					.recuperaAnamnesePorAtendimento(atendimentoCorrente);
		} catch (ObjetoNaoEncontradoException ex) {
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		try {
			comboMedicos = SelectOneDataModel.criaComObjetoSelecionadoSemTextoInicial(usuarioService
					.recuperaListaDeUsuarioPorTipo(tipoUsuarioService
							.recuperaTipoUsuarioClinico()), atendimentoCorrente.getMedico());
		} catch (AplicacaoException e) {
			e.printStackTrace();
		}

		if(atendimentoCorrente.getTecnico() == null){
			comboTecnicos = null;
		}else{
			try {
				comboTecnicos = SelectOneDataModel
						.criaComObjetoSelecionadoSemTextoInicial(
								usuarioService
										.recuperaListaDeUsuarioPorTipo(tipoUsuarioService
												.recuperaTipoUsuarioTecnico()),
								atendimentoCorrente.getTecnico());
			} catch (AplicacaoException e) {
				e.printStackTrace();
			}
		}
		comboStatus = SelectOneDataModel.criaComObjetoSelecionado(status, atendimentoCorrente.getStatus());
		return PAGINA_EDIT;
	}

	/**
	 * 
	 * Método acionado antes da tela de inclusão ser renderizada. Ele é
	 * responsável por preparar as instâncias de todas as entidades necessárias
	 * para a inclusão de um novo atendimento. De forma a garantir que resíduos
	 * de procedimentos antigos não diminuam a garantia de confiabilidade da
	 * inclusão.
	 * 
	 * @return Retorna uma String que corresponde ao nome do mapeamento da tela
	 *         de inclusão redirecionado o usuário para a mesma.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String preparaInclusao() {
		comboMedicos = null;
		comboTecnicos = null;
		comboStatus = null;
		dataAtendimento = null;
		atendimentoCorrente = new Atendimento();
		anamneseCorrente = new Anamnese();
		pacienteCorrente = (Paciente) listaDePacientes.getRowData();
		atendimentoCorrente.setPaciente(pacienteCorrente);
		return PAGINA_NEW;
	}

	/*      ************* Get & Set ************ */

	public Atendimento getAtendimentoCorrente() {
		return atendimentoCorrente;
	}

	public void setAtendimentoCorrente(Atendimento atendimentoCorrente) {
		this.atendimentoCorrente = atendimentoCorrente;
	}

	public Date getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public DataModel getListaDePacientes() {
		if (listaDePacientes == null) {
			listaDePacientes = new ListDataModel(pacienteService
					.recuperaListaDePacientesPaginadaComListaDeAtendimentos());
		}
		return listaDePacientes;
	}

	public void setListaDePacientes(DataModel listaDePacientes) {
		this.listaDePacientes = listaDePacientes;
	}

	@SuppressWarnings("unchecked")
	public DataModel getListaDeAtendimentos() {
		if (listaDeAtendimentos == null) {

			List<Atendimento> atendimentos = new ArrayList(atendimentoService
					.recuperaListaDeAtendimentosPaginada());
			for (Atendimento atendimento : atendimentos) {
				try {
					atendimento.setPaciente(pacienteService
							.recuperaUmPacienteComAtendimento(atendimento
									.getPaciente()));
				} catch (AplicacaoException e) {
				}
			}

			listaDeAtendimentos = new ListDataModel(atendimentos);
		}
		return listaDeAtendimentos;
	}

	public void setListaDeAtendimentos(DataModel listaDeAtendimentos) {
		this.listaDeAtendimentos = listaDeAtendimentos;
	}

	public SelectOneDataModel<Usuario> getComboMedicos()
			throws AplicacaoException {
		if (comboMedicos == null) {
			if (sessaoUsuarioCorrente.isClinico()) {
				comboMedicos = SelectOneDataModel
						.criaComObjetoSelecionadoSemTextoInicial(
								usuarioService
										.recuperaListaDeUsuarioPorTipo(tipoUsuarioService
												.recuperaTipoUsuarioClinico()),
								sessaoUsuarioCorrente.getUsuarioLogado());
			} else {
				comboMedicos = SelectOneDataModel
						.criaSemTextoInicial(usuarioService
								.recuperaListaDeUsuarioPorTipo(tipoUsuarioService
										.recuperaTipoUsuarioClinico()));
			}
		}
		return comboMedicos;
	}

	public void setComboMedicos(SelectOneDataModel<Usuario> comboMedicos) {
		this.comboMedicos = comboMedicos;
	}

	public SelectOneDataModel<Usuario> getComboTecnicos()
			throws AplicacaoException {
		if (comboTecnicos == null) {
			if (sessaoUsuarioCorrente.isTecnico()) {
				comboTecnicos = SelectOneDataModel
						.criaComObjetoSelecionadoSemTextoInicial(
								usuarioService
										.recuperaListaDeUsuarioPorTipo(tipoUsuarioService
												.recuperaTipoUsuarioTecnico()),
								sessaoUsuarioCorrente.getUsuarioLogado());
			} else {
				comboTecnicos = SelectOneDataModel
						.criaComTextoInicialDefault(usuarioService
								.recuperaListaDeUsuarioPorTipo(tipoUsuarioService
										.recuperaTipoUsuarioTecnico()));
			}
		}
		return comboTecnicos;
	}

	public void setComboTecnicos(SelectOneDataModel<Usuario> comboTecnicos) {
		this.comboTecnicos = comboTecnicos;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public int getPaginaAtendimento() {
		return paginaAtendimento;
	}

	public void setPaginaAtendimento(int paginaAtendimento) {
		this.paginaAtendimento = paginaAtendimento;
	}

	public boolean isTecnicoEditavel() {
		if (sessaoUsuarioCorrente.isTecnico()) {
			tecnicoEditavel = true;
		} else {
			tecnicoEditavel = false;
		}
		return tecnicoEditavel;
	}

	public void setTecnicoEditavel(boolean tecnicoEditavel) {
		this.tecnicoEditavel = tecnicoEditavel;
	}

	public boolean isClinicoEditavel() {
		if (sessaoUsuarioCorrente.isClinico()) {
			clinicoEditavel = true;
		} else {
			clinicoEditavel = false;
		}
		return clinicoEditavel;
	}

	public void setClinicoEditavel(boolean clinicoEditavel) {
		this.clinicoEditavel = clinicoEditavel;
	}

	public SelectOneDataModel<String> getComboStatus() {
		if (comboStatus == null) {
			comboStatus = SelectOneDataModel.criaSemTextoInicial(status);
		}
		return comboStatus;
	}

	public void setComboStatus(SelectOneDataModel<String> comboStatus) {
		this.comboStatus = comboStatus;
	}

	public String getCampoDeBusca() {
		return campoDeBusca;
	}

	public void setCampoDeBusca(String campoDeBusca) {
		this.campoDeBusca = campoDeBusca;
	}

	public SelectOneDataModel<String> getComboTiposDeBusca() {
		if (comboTiposDeBusca == null) {
			List<String> tiposDeBusca = new ArrayList<String>(2);
			tiposDeBusca.add(BUSCA_POR_COD_PACIENTE);
			tiposDeBusca.add(BUSCA_POR_NOME);
			comboTiposDeBusca = SelectOneDataModel
					.criaComObjetoSelecionadoSemTextoInicial(tiposDeBusca,
							BUSCA_POR_COD_PACIENTE);
		}
		return comboTiposDeBusca;
	}

	public void setComboTiposDeBusca(
			SelectOneDataModel<String> comboTiposDeBusca) {
		this.comboTiposDeBusca = comboTiposDeBusca;
	}

	public Anamnese getAnamneseCorrente() {
		return anamneseCorrente;
	}

	public void setAnamneseCorrente(Anamnese anamneseCorrente) {
		this.anamneseCorrente = anamneseCorrente;
	}

	/**
	 * 
	 * Método usado para calcular se um paciente já é legalmente independente ou
	 * se precisa estar acompanhado de um responsável.
	 * 
	 * @return Boolean - True se o paciente for legamente independente, false se
	 *         precisar de acompanhamento de responsável por ser tratar de uma
	 *         criança/adolescente.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public boolean isMaioridade() {
		maioridade = pacienteCorrente.calculaMaioridade();
		System.out.println(maioridade);
		return maioridade;
	}

	public void setMaioridade(boolean maioridade) {
		this.maioridade = maioridade;
	}

	public DataModel getListaAvaliacao() {
		return listaAvaliacao;
	}

	public void setListaAvaliacao(DataModel listaAvaliacao) {
		this.listaAvaliacao = listaAvaliacao;
	}

}
