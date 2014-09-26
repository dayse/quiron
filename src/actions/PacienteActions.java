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

import modelo.Paciente;

import service.PacienteAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;

import util.DataUtil;
import util.SelectOneDataModel;

/**
 * 
 * PacienteActions � uma classe relacionada a manipula��o de tela, ou seja, a
 * intera��o do usu�rio de fato dar-se-� atrav�s de objetos do tipo
 * PacienteActions quando estiver na tela de Pacientes.
 * 
 * Objetos do "tipo actions" s�o managed beans.
 * 
 * @author bruno.oliveira
 *
 */
public class PacienteActions extends BaseActions implements Serializable {


	// Services
	private static PacienteAppService pacienteService;
	
	// Paginas
	public final String PAGINA_EDIT = "editPaciente";
	public final String PAGINA_LIST = "listPaciente";
	public final String PAGINA_NEW = "newPaciente";
	public final String PAGINA_SHOW = "showPaciente";
	public final String PAGINA_LIST_ATENDIMENTO_PACIENTE = "listAtendimentoPaciente";
	
	// Componentes de Controle
	private static final long serialVersionUID = 1L;
	private DataModel listaDePacientes;
	private DataModel listaDePacientesComAtendimentos;
	private SelectOneDataModel<String> comboTiposDeBusca;
	private SelectOneDataModel<String> radioFlags;
	
	// Vari�veis de Tela
	private Date dataNascimento;
	private String campoDeBusca;
	private boolean buscaEfetuada = false;
	private Paciente pacienteCorrente;
	public List<String> tiposDeFlag = new ArrayList<String>(2);
	private int pagina;
	public final String BUSCA_POR_CODIGO = "C�digo";
	public final String BUSCA_POR_NOME = "Nome";
	
	/**
	 * 
	 * Construtor respons�vel por instanciar os 
	 * services que ser�o usados no decorrer da classe
	 * e tamb�m por carregar os tipos de pesquisas
	 * dispon�veis para a busca.
	 * 
	 * @throws Exception - Retorna uma exception caso ocorra
	 * alguma problema no carregamento dos services.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public PacienteActions() throws Exception{
		try{
			pacienteService = FabricaDeAppService.getAppService(PacienteAppService.class);
		} catch (Exception e){
			throw e;
		}
		tiposDeFlag.add(Paciente.FLAG_MASCULINO);
		tiposDeFlag.add(Paciente.FLAG_FEMININO);
	}
	
	/**
	 * 
	 * M�todo para altera��o de um determinado 
	 * registro de Paciente j� cadastrado.
	 * 
	 * @return Caso ocorra erro, mant�m na p�gina de edi��o. 
	 * Caso contr�rio retorna para p�gina de listagem de pacientes
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
	 * M�todo utilizado para fazer a busca
	 * de um determinado paciente no
	 * banco atrav�s de dados passados via
	 * formul�rio pelo usu�rio.
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
	 * M�todo usado em diversos momentos
	 * para zerar as principais vari�veis
	 * usadas em situa��es de manipula��o
	 * de entidades, como por exemplo,
	 * edi��o ou inclus�o e renderizar
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
	 * M�todo usado para exclus�o de 
	 * determinado registro do
	 * banco de dados.
	 * 
	 * @return Atualiza a listagem de pacientes
	 * na tela, ou se necess�rio renderiza uma
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
	 * 
	 * M�todo usado para fazer a
	 * inclus�o de um novo
	 * registro no banco de dados.
	 * 
	 * @return Renderiza uma mensagem de erro,
	 * caso ocorra um problema na inclus�o.
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
	 * M�todo usado para carregar as
	 * informa��es especif�cas de um
	 * determinado paciente
	 * na tela de detalhamento.
	 * 
	 * @return Retorna uma String
	 * que redireciona o usu�rio
	 * para a tela de detalhamento
	 * das informa��es do paciente.
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
	 * M�todo usado para carregar
	 * a listagem de atendimentos
	 * de um determinado paciente.
	 * 
	 * @return Retorna uma String
	 * que redireciona o usu�rio para
	 * a tela de rela��o dos atendimentos
	 * deste paciente em espec�fico.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public String mostraAtendimentos(){
		pacienteCorrente = (Paciente) listaDePacientes.getRowData();
		return PAGINA_LIST_ATENDIMENTO_PACIENTE;
	}

	/**
	 * 
	 * M�todo acionado antes da tela 
	 * de edi��o ser renderizada.
	 * Ele � respons�vel por capturar
	 * qual foi o paciente que o usu�rio
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
		pacienteCorrente = (Paciente) listaDePacientes.getRowData();
		comboTiposDeBusca = null;
		radioFlags = SelectOneDataModel.criaComObjetoSelecionadoSemTextoInicial(tiposDeFlag, 
						pacienteCorrente.getSexo() ? Paciente.FLAG_MASCULINO : Paciente.FLAG_FEMININO);
		return PAGINA_EDIT;
	}
	
	/**
	 * 
	 * M�todo acionado antes do
	 * modal panel de exclus�o ser
	 * renderizado.
	 * Ele � respons�vel por capturar
	 * qual foi o paciente que o usu�rio
	 * escolheu, de forma que a refer�ncia
	 * n�o se perca.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public void preparaExclusao(){
		pacienteCorrente = (Paciente) listaDePacientes.getRowData();
	}
	
	/**
	 * 
	 * M�todo acionado antes da tela
	 * de inclus�o ser renderizada. 
	 * Ele � respons�vel por preparar
	 * as inst�ncias de todas as entidades
	 * necess�rias para a inclus�o de um novo
	 * paciente. De forma a garantir que res�duos
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
		dataNascimento = null;
		pacienteCorrente = new Paciente();
		comboTiposDeBusca = null;
		return PAGINA_NEW;
	}
	
	/**
	 * 
	 * M�todo que zera as vari�veis
	 * relacionadas a lista de 
	 * pacientes. De forma que
	 * quando for chamado, a lista
	 * ser� atualizada.
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
	
	/**
	 *  ???????????????????????
	 * @return
	 */
	public String voltar(){
		pacienteCorrente = null;
		listaDePacientes = null;
		buscaEfetuada = false;
		comboTiposDeBusca = null;
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
