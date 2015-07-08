/**
 * 
 */
package actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import modelo.Algoritmo;
import modelo.Anamnese;
import modelo.Atendimento;
import modelo.Paciente;
import modelo.Parametro;
import modelo.Usuario;
import service.AlgoritmoAppService;
import service.AnamneseAppService;
import service.AtendimentoAppService;
import service.HistoricoAvaliacaoAppService;
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
 * PacienteActions é uma classe relacionada a manipulação de tela, ou seja, a
 * interação do usuário de fato dar-se-á através de objetos do tipo
 * PacienteActions quando estiver na tela de Pacientes.
 * 
 * Objetos do "tipo actions" são managed beans.
 * 
 * @author bruno.oliveira
 *
 */
public class PacienteActions extends BaseActions implements Serializable {

	// Componentes de Controle
	private static final long serialVersionUID = 1L;
	private SelectOneDataModel<String> comboTiposDeBusca;
	private SelectOneDataModel<String> radioFlags;
	private SelectOneDataModel<Usuario> comboMedicos;
	private SelectOneDataModel<Usuario> comboTecnicos;
	private SelectOneDataModel<String> comboStatus;
	private DataModel listaDePacientes;
	private DataModel listaDePacientesComAtendimentos;
	private DataModel listaDeAtendimentos;
	private DataModel listaAvaliacao;
	private DataModel listaDeAnamneses;
	private DataModel listaConjuntoAvaliacao;
	private DataModel listaHistorico;
	private List<Parametro> listaDeParametros;
	private List<String> status = new ArrayList<String>();
	
	// Paginas
	public final String PAGINA_EDIT = "editPaciente";
	public final String PAGINA_LIST = "listPaciente";
	public final String PAGINA_NEW = "newPaciente";
	public final String PAGINA_SHOW = "showPaciente";
	public final String PAGINA_LIST_ATENDIMENTO = "listAtendimento";
	public final String PAGINA_VISUALIZACAO_HISTORICO_PACIENTE = "showHistoricoPaciente";

	// Services
	private static AtendimentoAppService atendimentoService;
	private static AnamneseAppService anamneseService;
	private static PacienteAppService pacienteService;
	private static ParametroAppService parametroService;
	private static TipoUsuarioAppService tipoUsuarioService;
	private static UsuarioAppService usuarioService;
	private static HistoricoAvaliacaoAppService historicoService;
	
	// Variáveis de Tela
	private Anamnese anamnesesCorrente;
	private Atendimento atendimentoCorrente;
	private Paciente pacienteCorrente;
	private Algoritmo algoritmoCorrente;
	private Date dataNascimento;
	private Date dataAtendimento;
	private String campoDeBusca;
	private boolean buscaEfetuada = false;
	public List<String> tiposDeFlag = new ArrayList<String>(2);
	private int pagina;
	private int numParametros;
	public final String BUSCA_POR_CODIGO = "Código";
	public final String BUSCA_POR_NOME = "Nome";
	
	//infos do filtro
		private SelectOneDataModel<String> comboFiltroStatus;
		
	/**
	 * 
	 * Construtor responsável por instanciar os 
	 * services que serão usados no decorrer da classe
	 * e também por carregar os tipos de pesquisas
	 * disponíveis para a busca.
	 * 
	 * @throws Exception - Retorna uma exception caso ocorra
	 * alguma problema no carregamento dos services.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public PacienteActions() throws Exception{
		try{
			atendimentoService = FabricaDeAppService
					.getAppService(AtendimentoAppService.class);
			anamneseService = FabricaDeAppService
					.getAppService(AnamneseAppService.class);
			historicoService = FabricaDeAppService
					.getAppService(HistoricoAvaliacaoAppService.class);
			pacienteService = FabricaDeAppService
					.getAppService(PacienteAppService.class);
			tipoUsuarioService = FabricaDeAppService
					.getAppService(TipoUsuarioAppService.class);
			usuarioService = FabricaDeAppService
					.getAppService(UsuarioAppService.class);
			parametroService = FabricaDeAppService
					.getAppService(ParametroAppService.class);
		} catch (Exception e){
			throw e;
		}
		tiposDeFlag.add(Paciente.FLAG_MASCULINO);
		tiposDeFlag.add(Paciente.FLAG_FEMININO);
	}
	
	/**
	 * 
	 * Método para alteração de um determinado 
	 * registro de Paciente já cadastrado.
	 * 
	 * @return Caso ocorra erro, mantém na página de edição. 
	 * Caso contrário retorna para página de listagem de pacientes
	 * e renderiza a mensagem de sucesso.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String altera(){
		if(radioFlags.getObjetoSelecionado().equals(Paciente.FLAG_MASCULINO)){
			pacienteCorrente.setSexo(true);
		}else{
			pacienteCorrente.setSexo(false);
		}
		
		try{
			pacienteService.altera(pacienteCorrente);
		}catch(AplicacaoException e){
			error(e.getMessage());
			return PAGINA_LIST;
		}
		logUsuarioAutenticadoMsg("Paciente - Altera paciente:" + pacienteCorrente.getCodPaciente());
		info("paciente.SUCESSO_ALTERACAO");
		listaDePacientes = null;
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * Método utilizado para fazer a busca
	 * de um determinado paciente no
	 * banco através de dados passados via
	 * formulário pelo usuário.
	 * 
	 * @return A lista de pacientes atualizada
	 * com os resultados da pesquisa no banco ou
	 * uma mensagem de erro, caso algum ocorra.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String buscaPaciente(){
		List<Paciente> pacientesEncontrados = null;
		if(campoDeBusca.trim().isEmpty()){
			error("paciente.FORNECER_CAMPO_DE_BUSCA");
			return PAGINA_LIST;
		}else{
			listaDePacientes = null;
			if(comboTiposDeBusca.getObjetoSelecionado().equals(BUSCA_POR_CODIGO)){
				pacientesEncontrados = new ArrayList<Paciente>(pacienteService.recuperaPacientePorCodigoLike(campoDeBusca));
			}else{
				pacientesEncontrados = new ArrayList<Paciente>(pacienteService.recuperaPacientePorNome(campoDeBusca));
			}
			if(pacientesEncontrados.isEmpty()){
				error("paciente.NAO_ENCONTRADO");
				listaDePacientes = null;
				buscaEfetuada = false;
				return PAGINA_LIST;
			}else{
				info("paciente.ENCONTRADOS");
			}
		}
		listaDePacientes = new ListDataModel(pacientesEncontrados);
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
	 * para a tela de listagem de pacientes.
	 * 
	 * @return Retorna uma String que corresponde
	 * ao no mapeamento da tela de 
	 * listagem de paciente.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String cancelar(){
		listaDePacientes = null;
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * Método usado para exclusão de 
	 * determinado registro do
	 * banco de dados.
	 * 
	 * @return Atualiza a listagem de pacientes
	 * na tela, ou se necessário renderiza uma
	 * mensagem de erro.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String exclui(){
		try{
			pacienteService.exclui(pacienteCorrente);
		}catch(AplicacaoException ex){
			error(ex.getMessage());
			return PAGINA_LIST;
		}
		logUsuarioAutenticadoMsg("Paciente - Exclui paciente:" + pacienteCorrente.getCodPaciente());
		info("paciente.SUCESSO_EXCLUSAO");
		listaDePacientes = null;
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		return PAGINA_LIST;
	}
	
	/**
	 * Imprime um relatório contendo a lista dos pacientes
	 * cadastrados no sistema.
	 * 
	 * @author bruno.oliveira
	 * 
	 */	
	public void imprimir(){
		try{
			List<Paciente> listaDePacientes = pacienteService.recuperaListaDePacientes();
			pacienteService.gerarRelatorio(listaDePacientes);
		} catch (AplicacaoException e){
			e.printStackTrace();
		}
	}	
	
	/**
	 * Imprime o histórico contendo todos
	 * os atendimentos de um determinado
	 * paciente.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	/*public void imprimirHistoricoAtendimento(){
		
		pacienteCorrente = (Paciente) listaDePacientes.getRowData();
		List<Atendimento> atendimentos = atendimentoService
				.recuperaListaDeAtendimentosComPacienteComAnamnesePorCodigoPaciente(pacienteCorrente.getCodPaciente());
		try {
			atendimentoService.gerarRelatorioHistorico(atendimentos);
		} catch (AplicacaoException e) {
			e.printStackTrace();
		} 		
	}*/

	public String visualizarHistorico() {

		
		pacienteCorrente = 	(Paciente) listaDePacientes.getRowData();
		
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
		
							
		return PAGINA_VISUALIZACAO_HISTORICO_PACIENTE;
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
	 * atualizada de pacientes com uma
	 * mensagem de sucesso.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String inclui(){
		if(radioFlags.getObjetoSelecionado().equals(Paciente.FLAG_MASCULINO)){
			pacienteCorrente.setSexo(true);
		}else{
			pacienteCorrente.setSexo(false);
		}
		pacienteCorrente.setDataNascimento(DataUtil.dateToCalendar(dataNascimento));
		try{
			pacienteService.inclui(pacienteCorrente);
		}catch(AplicacaoException ex){
			error(ex.getMessage());
			return PAGINA_NEW;
		}
		logUsuarioAutenticadoMsg("Paciente - Inclui paciente:" + pacienteCorrente.getCodPaciente());
		info("paciente.SUCESSO_INCLUSAO");
		listaDePacientes = null;
		buscaEfetuada = false;
		return PAGINA_LIST;
	}
	
	/**
	 * 
	 * Método usado para carregar as
	 * informações especifícas de um
	 * determinado paciente
	 * na tela de detalhamento.
	 * 
	 * @return Retorna uma String
	 * que redireciona o usuário
	 * para a tela de detalhamento
	 * das informações do paciente.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String mostra(){
		pacienteCorrente = (Paciente) listaDePacientes.getRowData();
		radioFlags = SelectOneDataModel.criaComObjetoSelecionadoSemTextoInicial(tiposDeFlag, 
						pacienteCorrente.getSexo() ? Paciente.FLAG_MASCULINO : Paciente.FLAG_FEMININO);
		return PAGINA_SHOW;
	}
	
	/**
	 * 
	 * Método usado para carregar
	 * a listagem de atendimentos
	 * de um determinado paciente.
	 * 
	 * @return Retorna uma String
	 * que redireciona o usuário para
	 * a tela de relação dos atendimentos
	 * deste paciente em específico.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String mostraAtendimentos(){
		pacienteCorrente = (Paciente) listaDePacientes.getRowData();
		int pag = ((List<Paciente>) listaDePacientes.getWrappedData()).indexOf(pacienteCorrente);
		pagina = pag + 1;
		listaDePacientes = new ListDataModel(pacienteService.recuperaListaDePacientesPaginada());
		return PAGINA_LIST_ATENDIMENTO;
	}

	/**
	 * 
	 * Método acionado antes da tela 
	 * de edição ser renderizada.
	 * Ele é responsável por capturar
	 * qual foi o paciente que o usuário
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
		pacienteCorrente = (Paciente) listaDePacientes.getRowData();
		comboTiposDeBusca = null;
		radioFlags = SelectOneDataModel.criaComObjetoSelecionadoSemTextoInicial(tiposDeFlag, 
						pacienteCorrente.getSexo() ? Paciente.FLAG_MASCULINO : Paciente.FLAG_FEMININO);
		return PAGINA_EDIT;
	}
	
	/**
	 * 
	 * Método acionado antes do
	 * modal panel de exclusão ser
	 * renderizado.
	 * Ele é responsável por capturar
	 * qual foi o paciente que o usuário
	 * escolheu, de forma que a referência
	 * não se perca.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public void preparaExclusao(){
		pacienteCorrente = (Paciente) listaDePacientes.getRowData();
	}
	
	/**
	 * 
	 * Método acionado antes da tela
	 * de inclusão ser renderizada. 
	 * Ele é responsável por preparar
	 * as instâncias de todas as entidades
	 * necessárias para a inclusão de um novo
	 * paciente. De forma a garantir que resíduos
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
		dataNascimento = null;
		pacienteCorrente = new Paciente();
		comboTiposDeBusca = null;
		return PAGINA_NEW;
	}
	
	/**
	 * 
	 * Método que zera as variáveis
	 * relacionadas a lista de 
	 * pacientes. De forma que
	 * quando for chamado, a lista
	 * será atualizada.
	 * 
	 * @return Retorna uma String que corresponde
	 * ao nome do mapeamento da tela de listagem
	 * de pacientes no faces config.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String preparaListagem(){
		listaDePacientes = null;
		buscaEfetuada = false;
		comboTiposDeBusca = null;
		return PAGINA_LIST;
	}

	/* ************* Get & Set ************ */
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
	
	public DataModel getListaHistorico() {

		if (listaHistorico == null) {
				listaHistorico = new 
						ListDataModel(historicoService.recuperaListaHistoricoPorAtendimento(atendimentoCorrente));
		}
		
		return listaHistorico;
	}

	public void setListaHistorico(DataModel listaHistorico) {
		this.listaHistorico = listaHistorico;
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
	public SelectOneDataModel<String> getComboStatus() {
		if (comboStatus == null) {
			comboStatus = SelectOneDataModel.criaSemTextoInicial(status);
		}
		return comboStatus;
	}

	public void setComboStatus(SelectOneDataModel<String> comboStatus) {
		this.comboStatus = comboStatus;
	}

	public Anamnese getAnamneseCorrente() {
		return anamnesesCorrente;
	}

	public void setAnamneseCorrente(Anamnese anamneseCorrente) {
		this.anamnesesCorrente = anamneseCorrente;
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
	public SelectOneDataModel<String> getComboFiltroStatus() {
		if (comboFiltroStatus == null) {
			comboFiltroStatus = SelectOneDataModel.criaComTextoInicialDefault(status);
		}
		return comboFiltroStatus;
	}

	public void setComboFiltroStatus(SelectOneDataModel<String> comboFiltroStatus) {
		this.comboFiltroStatus = comboFiltroStatus;
	}
//-------------------------------------------------------------------------------
	
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

	public DataModel getListaDePacientes(){
		if(listaDePacientes == null){
			listaDePacientes =  new ListDataModel(pacienteService.recuperaListaDePacientesPaginada());
		}
		return listaDePacientes;
	}

	public void setListaDePacientes(DataModel listaDePacientes) {
		this.listaDePacientes = listaDePacientes;
	}
	
	public DataModel getListaDePacientesComAtendimentos(){
		if(listaDePacientesComAtendimentos == null){
			listaDePacientesComAtendimentos = new ListDataModel(pacienteService.recuperaListaDePacientesPaginadaComListaDeAtendimentos());
		}
		return listaDePacientesComAtendimentos;
	}
	
	public void setListaDePacientesComAtendimentos(DataModel listaDePacientesComAtendimentos){
		this.listaDePacientesComAtendimentos = listaDePacientesComAtendimentos;
	}

	public Paciente getPacienteCorrente() {
		return pacienteCorrente;
	}

	public void setPacienteCorrente(Paciente pacienteCorrente) {
		this.pacienteCorrente = pacienteCorrente;
	}
	
	public SelectOneDataModel<String> getRadioFlags(){
		if(radioFlags == null){
			radioFlags = SelectOneDataModel.criaComObjetoSelecionadoSemTextoInicial(tiposDeFlag, Paciente.FLAG_MASCULINO);
		}
		return radioFlags;
	}
	
	public void setRadioFlags(SelectOneDataModel<String> radioFlags){
		this.radioFlags = radioFlags;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	
}
