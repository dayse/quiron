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
import modelo.Parametro;
import modelo.Usuario;
import service.AnamneseAppService;
import service.AtendimentoAppService;
import service.PacienteAppService;
import service.ParametroAppService;
import service.TipoUsuarioAppService;
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.DataUtil;
import util.SelectOneDataModel;

/**
 * 
 * AtendimentoActions � uma classe relacionada a manipula��o de tela, ou seja, a
 * intera��o do usu�rio de fato dar-se-� atrav�s de objetos do tipo
 * AtendimentoActions quando estiver na tela de Atendimentos.
 * 
 * Objetos do "tipo actions" s�o managed beans.
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
	private DataModel listaDePacientes;
	private DataModel listaDeAtendimentos;
	private DataModel listaAvaliacao;
	private DataModel listaDeAnamneses;
	private DataModel listaConjuntoAvaliacao;
	private List<Parametro> listaDeParametros; 
	private List<String> status = new ArrayList<String>();

	// P�ginas
	public final String PAGINA_EDIT = "editAtendimento";
	public final String PAGINA_LIST = "listAtendimento";
	public final String PAGINA_NEW = "newAtendimento";
	public final String PAGINA_SHOW = "showAtendimento";
	public final String PAGINA_STATUS = "listStatusAtendimento";
	public final String PAGINA_AVALIACAO = "listAvaliacao";
	public final String PAGINA_AVALIACAO_DETALHADA = "listAvaliacaoDetail";

	// Services
	private static AtendimentoAppService atendimentoService;
	private static AnamneseAppService anamneseService;
	private static PacienteAppService pacienteService;
	private static ParametroAppService parametroService;
	private static TipoUsuarioAppService tipoUsuarioService;
	private static UsuarioAppService usuarioService;

	// Vari�veis de tela
	private Anamnese anamnesesCorrente;
	private Atendimento atendimentoCorrente;
	private Paciente pacienteCorrente;
	private Date dataAtendimento;
	private int paginaAtendimento = 1;
	private int pagina;
	private int numParametros;
	private boolean tecnicoEditavel;
	private boolean clinicoEditavel;

	
	//infos de busca

	public final String BUSCA_POR_NOME_PACIENTE = "Nome do Paciente";
	public final String BUSCA_POR_NOME_MEDICO = "Nome do M�dico";
	private String campoDeBusca;
	private SelectOneDataModel<String> comboTiposDeBusca;
	private boolean buscaEfetuada = false;
	
	
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
			parametroService = FabricaDeAppService
					.getAppService(ParametroAppService.class);
			
			
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
	 * M�todo para altera��o de um determinado registro de Atendimento j�
	 * cadastrado.
	 * 
	 * @return Caso ocorra erro, mant�m na p�gina de edi��o. Caso contr�rio
	 *         retorna para p�gina de listagem de atendimentos e renderiza a
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

		List<Anamnese> anamneses = (List<Anamnese>) listaDeAnamneses.getWrappedData();
		for (Anamnese anamnese : anamneses) {

			try {
				anamneseService.altera(anamnese);
			} catch (AplicacaoException ex) {
				error(ex.getMessage());
				return PAGINA_EDIT;
			}
		}
		
		logUsuarioAutenticadoMsg("Atendimento - Altera atendimento:" + atendimentoCorrente.getCodAtendimento());
		info("atendimento.SUCESSO_ALTERACAO");
		return PAGINA_LIST;
	}

	/**
	 * 
	 * M�todo que calcula a avalia��o para um determinado atendimento.
	 * 
	 * @return Redireciona para a p�gina de avalia��o, exibindo os dados da
	 *         avalia��o, por par�metro e por indica��o.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String calculaAvaliacao() {
		listaConjuntoAvaliacao = new ListDataModel(
								anamneseService.recuperaAvaliacaoCalculadaPorIndicacao(atendimentoCorrente)
								);
		listaDeParametros = parametroService.recuperaListaDeParametrosPaginada();
		try {
			comboMedicos = SelectOneDataModel.criaComObjetoSelecionadoSemTextoInicial(usuarioService
					.recuperaListaDeUsuarioPorTipo(tipoUsuarioService
							.recuperaTipoUsuarioClinico()), atendimentoCorrente.getMedico());
		} catch (AplicacaoException e) {
			e.printStackTrace();
		}
		listaDeAnamneses = new ListDataModel(
					anamneseService.recuperaListaDeAnamnesePorAtendimento(atendimentoCorrente)
				);
		
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
		return PAGINA_AVALIACAO;
	}
	
	/**
	 * 
	 * M�todo que calcula a avalia��o para um determinado atendimento, mostrando os dados detalhados
	 * do algoritimo, como os valores de interce��o/uni�o de cada parametro.
	 * 
	 * @return Redireciona para a p�gina de avalia��o detalhada, exibindo os dados da
	 *         avalia��o, por par�metro e por indica��o.
	 * 
	 * @author felipe.pontes
	 * 
	 */
	public String calculaAvaliacaoDetalhado() {
		return PAGINA_AVALIACAO_DETALHADA;
	}

	/**
	 * 
	 * M�todo dispon�vel na tela de avalia��o que redireciona o usu�rio para a
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
	 * M�todo usado em diversos momentos para zerar as principais vari�veis
	 * usadas em situa��es de manipula��o de entidades, como por exemplo, edi��o
	 * ou inclus�o e renderizar para a tela de listagem de atendimentos.
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
	 * M�todo usado para exclus�o de determinado registro do banco de dados.
	 * 
	 * @return Atualiza a listagem de atendimentos na tela, ou se necess�rio
	 *         renderiza uma mensagem de erro.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String exclui() {
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
	 * M�todo usado para fazer a inclus�o de um novo registro no banco de dados.
	 * 
	 * @return Renderiza uma mensagem de erro, caso ocorra um problema na
	 *         inclus�o. Ou redireciona para a tela de listagem atualizada de
	 *         atendimentos com uma mensagem de sucesso.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String inclui() {
		atendimentoCorrente.setDataAtendimento(DataUtil.dateToCalendar(dataAtendimento));
		atendimentoCorrente.setMedico(comboMedicos.getObjetoSelecionado());
		atendimentoCorrente.setTecnico(comboTecnicos.getObjetoSelecionado());
		atendimentoCorrente.setStatus(comboStatus.getObjetoSelecionado());
		try {
			atendimentoService.inclui(atendimentoCorrente);
		} catch (AplicacaoException ex) {
			error(ex.getMessage());
			return PAGINA_NEW;
		}
		
		List<Anamnese> anamneses = (List<Anamnese>) listaDeAnamneses.getWrappedData();
		for (Anamnese anamnese : anamneses) {

			try {
				anamneseService.inclui(anamnese);
			} catch (AplicacaoException ex) {
				error(ex.getMessage());
				return PAGINA_NEW;
			}
		}
		logUsuarioAutenticadoMsg("Atendimento - Inclui atendimento:" + atendimentoCorrente.getCodAtendimento());
		info("atendimento.SUCESSO_INCLUSAO");
		listaDeAtendimentos = null;
		listaDePacientes = null;
		listaDeAnamneses = null;
		return PAGINA_LIST;
	}

	/**
	 * 
	 * M�todo usado para carregar as informa��es especif�cas de um determinado
	 * atendimento e anamnese na tela de detalhamento.
	 * 
	 * @return Retorna uma String que redireciona o usu�rio para a tela de
	 *         detalhamento das informa��es do atendimento e de sua respectiva
	 *         anamnese.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String mostrar() {
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
		listaDeAnamneses = null;
		comboStatus = SelectOneDataModel.criaComObjetoSelecionado(status, atendimentoCorrente.getStatus());
		listaDeAtendimentos = new ListDataModel(
				atendimentoService.
				recuperaListaPaginadaDeAtendimentosComPacienteComAnamnesePorCodigoPaciente(
							atendimentoCorrente.getPaciente().getCodPaciente())
							);
		return PAGINA_SHOW;
	}

	/**
	 * 
	 * M�todo acionado antes da tela de edi��o ser renderizada. Ele �
	 * respons�vel por capturar qual foi o paciente que o usu�rio escolheu, de
	 * forma que seja poss�vel recuperar as informa��es necess�rias do banco.
	 * 
	 * @return Caso a busca ao banco n�o retorne nada exibe uma mensagem de erro
	 *         sem redirecionar a tela. Caso se obtenha sucesso, o usu�rio �
	 *         redirecionado para a tela de edi��o.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String preparaAlteracao() {
		pacienteCorrente = atendimentoCorrente.getPaciente();
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
						.criaComObjetoSelecionado(
								usuarioService
										.recuperaListaDeUsuarioPorTipo(tipoUsuarioService
												.recuperaTipoUsuarioTecnico()),
								atendimentoCorrente.getTecnico());
			} catch (AplicacaoException e) {
				e.printStackTrace();
			}
		}
		comboStatus = SelectOneDataModel.criaComObjetoSelecionado(status, atendimentoCorrente.getStatus());

		listaDeAnamneses = null;
		listaDeAtendimentos = new ListDataModel(
				atendimentoService.
				recuperaListaPaginadaDeAtendimentosComPacienteComAnamnesePorCodigoPaciente(
							pacienteCorrente.getCodPaciente())
							);
		return PAGINA_EDIT;
	}

	/**
	 * 
	 * M�todo acionado antes da tela de inclus�o ser renderizada. Ele �
	 * respons�vel por preparar as inst�ncias de todas as entidades necess�rias
	 * para a inclus�o de um novo atendimento. De forma a garantir que res�duos
	 * de procedimentos antigos n�o diminuam a garantia de confiabilidade da
	 * inclus�o.
	 * 
	 * @return Retorna uma String que corresponde ao nome do mapeamento da tela
	 *         de inclus�o redirecionado o usu�rio para a mesma.
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
		anamnesesCorrente = new Anamnese();
		listaDeAnamneses = null;

		pacienteCorrente = 	(Paciente)
							((PacienteActions) getManagedBean("pacienteActions"))
							.getListaDePacientes().getRowData();
		
		atendimentoCorrente.setPaciente(pacienteCorrente);
		
		
		listaDeAtendimentos = new ListDataModel(
								atendimentoService.
								recuperaListaPaginadaDeAtendimentosComPacienteComAnamnesePorCodigoPaciente(
											pacienteCorrente.getCodPaciente())
											);
		return PAGINA_NEW;
	}

	
	/**
	 * 
	 * M�todo que zera as vari�veis
	 * relacionadas a lista de 
	 * atendimentos. De forma que
	 * quando for chamado, a lista
	 * ser� atualizada.
	 * 
	 * 
	 */
	public String preparaListagem(){
		listaDeAtendimentos = null;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * M�todo utilizado para fazer a busca
	 * de um determinado atendimento no
	 * banco atrav�s de dados passados via
	 * formul�rio pelo usu�rio.
	 * 
	 * @return A lista de atendimentos atualizada
	 * com os resultados da pesquisa no banco ou
	 * uma mensagem de erro, caso algum ocorra.
	 * 
	 * @author felipe.pontes
	 * 
	 */
	public String buscaAtendimento(){
		List<Atendimento> atendimentosEncontrados = null;
		if(campoDeBusca.trim().isEmpty()){
			error("atendimento.FORNECER_CAMPO_DE_BUSCA");
			return PAGINA_LIST;
		}else{
			listaDeAtendimentos = null;
			if(comboTiposDeBusca.getObjetoSelecionado().equals(BUSCA_POR_NOME_MEDICO)){
				atendimentosEncontrados = new ArrayList<Atendimento>(atendimentoService.recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLike(campoDeBusca));
			}else{
				atendimentosEncontrados = new ArrayList<Atendimento>(atendimentoService.recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLike(campoDeBusca));
			}
			if(atendimentosEncontrados.isEmpty()){
				error("atendimento.NAO_ENCONTRADO");
				listaDeAtendimentos = null;
				return PAGINA_LIST;
			}else{
				info("atendimento.ENCONTRADOS");
			}
		}
		listaDeAtendimentos = new ListDataModel(atendimentosEncontrados);
		buscaEfetuada = true;
		return PAGINA_LIST;
	}


	/**
	 *  Action usada na hora de sair da tela de inclus�o/edi��o e show
	 *  de atendimento, que faz voltar para a list.
	 * @return
	 */
	public String voltar(){
		comboMedicos = null;
		comboTecnicos = null;
		comboStatus = null;
		dataAtendimento = null;
		atendimentoCorrente = null;
		anamnesesCorrente = null;
		listaDeAnamneses = null;
		pacienteCorrente = null;
		if(!buscaEfetuada){
			listaDeAtendimentos = null;
		}
		return PAGINA_LIST;
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

			List<Atendimento> atendimentos = new ArrayList<Atendimento>(atendimentoService
					.recuperaListaDeAtendimentosComPacientePaginada());

			listaDeAtendimentos = new ListDataModel(atendimentos);
		}
		return listaDeAtendimentos;
	}

	public DataModel getListaDeAnamneses() {

		if (listaDeAnamneses == null) {
			if(atendimentoCorrente.getId() == null){
				listaDeAnamneses = new ListDataModel(anamneseService
						.geraListaDeAnamnesesPadroesParaAtendimento(atendimentoCorrente));
			}
			else{
				listaDeAnamneses = new ListDataModel(anamneseService
						.recuperaListaDeAnamnesePorAtendimento(atendimentoCorrente));
			}
		}
		
		return listaDeAnamneses;
	}

	public void setListaDeAnamneses(DataModel listaDeAnamneses) {
		this.listaDeAnamneses = listaDeAnamneses;
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
			tiposDeBusca.add(BUSCA_POR_NOME_PACIENTE);
			tiposDeBusca.add(BUSCA_POR_NOME_MEDICO);
			comboTiposDeBusca = SelectOneDataModel
					.criaComObjetoSelecionadoSemTextoInicial(tiposDeBusca,
							BUSCA_POR_NOME_PACIENTE);
		}
		return comboTiposDeBusca;
	}

	public void setComboTiposDeBusca(
			SelectOneDataModel<String> comboTiposDeBusca) {
		this.comboTiposDeBusca = comboTiposDeBusca;
	}

	public Anamnese getAnamneseCorrente() {
		return anamnesesCorrente;
	}

	public void setAnamneseCorrente(Anamnese anamneseCorrente) {
		this.anamnesesCorrente = anamneseCorrente;
	}

	public DataModel getListaAvaliacao() {
		return listaAvaliacao;
	}

	public void setListaAvaliacao(DataModel listaAvaliacao) {
		this.listaAvaliacao = listaAvaliacao;
	}
	public DataModel getListaConjuntoAvaliacao() {
		return listaConjuntoAvaliacao;
	}

	public void setListaConjuntoAvaliacao(DataModel listaConjuntoAvaliacao) {
		this.listaConjuntoAvaliacao = listaConjuntoAvaliacao;
	}



	public List<Parametro> getListaDeParametros() {
		return this.listaDeParametros;
	}

	public void setListaDeParametros(List<Parametro> listaDeParametros) {
		this.listaDeParametros = listaDeParametros;
	}

	public int getNumParametros() {
		return this.listaDeParametros.size();
	}

	public void setNumParametros(int numParametros) {
		this.numParametros = numParametros;
	}
	public boolean isBuscaEfetuada() {
		return buscaEfetuada;
	}

	public void setBuscaEfetuada(boolean buscaEfetuada) {
		this.buscaEfetuada = buscaEfetuada;
	}
}
