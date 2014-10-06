package util;

/**
 * Classe com definicao das constantes usadas nas telas que informam
 * o comprimento do campo na tela.
 * 
 * @author equipe
 *
 */
public class Constantes {
	
	public static final String PAGINA_LOGIN = "login";
	public static final String PAGINA_HOME = "home";
	
	//-------------------- CONSTANTES DE TAMANHO EM TELAS ------------------------
	
	// --------------------- Atendimento ---------------------
	public static final int L_ATENDIMENTO_CODIGO = 15;
	public static final int L_ATENDIMENTO_OBSERVACOES = 100;
	public static final int L_ATENDIMENTO_PRESCRICAO = 100;
	public static final int L_ATENDIMENTO_DIAGNOSTICO = 100;
	
	// --------------------- Avaliacao ---------------------
	public static final int L_AVALIACAO_RESULTADO = 3;

	// --------------------- Especialista ---------------------
	public static final int L_ESPECIALISTA_CODIGO = 15;
	public static final int L_ESPECIALISTA_NOME = 30;
	public static final int L_ESPECIALISTA_PESO_AVALIADOR = 3;
	
	// --------------------- Paciente ---------------------
	public static final int L_PACIENTE_CODIGO = 15;
	public static final int L_PACIENTE_DOCUMENTO = 15;
	public static final int L_PACIENTE_ENDERECO = 100;
	public static final int L_PACIENTE_INFORMACOES_GERAIS = 1000;
	public static final int L_PACIENTE_NOME = 30;
	public static final int L_PACIENTE_NOME_RESPONSAVEL = 30;
	
	// --------------------- Parametro ---------------------
	public static final int L_PARAMETRO_CANDIDA = 3;
	public static final int L_PARAMETRO_DIABETES = 3;
	public static final int L_PARAMETRO_DISURIA = 3;
	public static final int L_PARAMETRO_EFEITOS_COLATERAIS = 3;
	public static final int L_PARAMETRO_ENTEROCOCOS = 3;
	public static final int L_PARAMETRO_ESCHERICHIA = 3;
	public static final int L_PARAMETRO_FEBRE = 2;
	
	public static final int L_PARAMETRO_CODIGO = 15;
	public static final int L_PARAMETRO_NOME = 30;
	public static final int L_PARAMETRO_DESCRICAO = 30;
	
	
	// --------------------- Indicacao ---------------------
	public static final int L_INDICACAO_CODIGO = 15;
	public static final int L_INDICACAO_DOSAGEM = 100;
	public static final int L_INDICACAO_NOME = 30;
	
	// --------------------- Usuario ---------------------
	public static final int L_USUARIO_NOME = 25;
	public static final int L_USUARIO_LOGIN = 20; 
	public static final int L_USUARIO_SENHA = 20;
	
	//----------------------- CONSTANTES DE RELATÓRIOS ---------------------------
	public final static String CAMINHO_JASPERS = "/WEB-INF/relatorios/";
	public static String CAMINHO_LOGOTIPOS = "/WEB-INF/imagensRelatorio/";
	public static String CAMINHO_RELATORIOS_GERADOS;
    public static String CAMINHO_ABSOLUTO_RELATORIOS;
    public static String CAMINHO_JASPERS_SUBRELATORIOS;
    public static String CAMINHO_SERVLET_LOGO_INT = CAMINHO_LOGOTIPOS + "logoINT2.jpg";
    public static String CAMINHO_SERVLET_LOGO_COPPE = CAMINHO_LOGOTIPOS + "logoCoppe.png";
	//----------------------------------------------------------------------------

	//----------------------- CONSTANTES DE CARGA ---------------------------
  	public final static String CAMINHO_ARQUIVOS_CARGA = "/WEB-INF/carga/";
	public static String CAMINHO_ABSOLUTO_ARQUIVOS_CARGA;
	public final static String CAMINHO_ARQUIVO_USUARIOS_CARGA = CAMINHO_ARQUIVOS_CARGA + "usuarios.json";
	public static String CAMINHO_ABSOLUTO_ARQUIVO_USUARIOS_CARGA;
	//----------------------------------------------------------------------------
	
	public static String SENHA_CARGABD;
	
  	 /* ------- ATENDIMENTO ------- */

  	public int getlAtendimentoCodigo(){
  		return L_ATENDIMENTO_CODIGO;
  	}
  	public int getlAtendimentoObservacoes(){
  		return L_ATENDIMENTO_OBSERVACOES;
  	}
  	public int getlAtendimentoPrescricao(){
  		return L_ATENDIMENTO_PRESCRICAO;
  	}
  	public int getlAtendimentoDiagnostico(){
  		return L_ATENDIMENTO_DIAGNOSTICO;
  	}
  	
 	 /* ------- AVALIACAO ------- */
  	
 	public int getlAvaliacaoResultado(){
 		return L_AVALIACAO_RESULTADO;
 	}  	
 
  	/* ------- ESPECIALISTA ------- */
 	
 	public int getlEspecialistaCodigo(){
 		return L_ESPECIALISTA_CODIGO;
 	}
 	
 	public int getlEspecialistaNome(){
 		return L_ESPECIALISTA_NOME;
 	}	
 	
 	public int getlEspecialistaPesoAvaliador(){
 		return L_ESPECIALISTA_PESO_AVALIADOR;
 	}
 	
	/* ------- INDICACAO ------- */
	
  	public int getlIndicacaoCodigo(){
  		return L_INDICACAO_CODIGO;
  	}
	
	public int getlIndicacaoDosagem() {
		return L_INDICACAO_DOSAGEM;
	}

	public int getlIndicacaoNome() {
		return L_INDICACAO_NOME;
	}	
 	
  	/* ------- PACIENTE ------- */

	public int getlPacienteCodigo() {
		return L_PACIENTE_CODIGO;
	}
	
	public int getlPacienteDocumento(){
		return L_PACIENTE_DOCUMENTO;
	}
	
	public int getlPacienteEndereco(){
		return L_PACIENTE_ENDERECO;
	}

	public int getlPacienteNome() {
		return L_PACIENTE_NOME;
	}
	
	public int getlPacienteNomeResponsavel(){
		return L_PACIENTE_NOME_RESPONSAVEL;
	}
	
	public int getlPacienteInformacoesGerais(){
		return L_PACIENTE_INFORMACOES_GERAIS;
	}
	
	/* ------- PARAMETRO ------- */

	public int getlParametroCodigo() {
		return L_PARAMETRO_CODIGO;
	}

	public int getlParametroNome() {
		return L_PARAMETRO_NOME;
	}

	public int getlParametroDescricao() {
		return L_PARAMETRO_DESCRICAO;
	}
	
	public int getlParametroCandida(){
		return L_PARAMETRO_CANDIDA;
	}
	
	public int getlParametroDiabetes(){
		return L_PARAMETRO_DIABETES;
	}
	
	public int getlParametroDisuria(){
		return L_PARAMETRO_DISURIA;
	}
	
	public int getlParametroEfeitosColaterais(){
		return L_PARAMETRO_EFEITOS_COLATERAIS;
	}
	
	public int getlParametroEnterococos(){
		return L_PARAMETRO_ENTEROCOCOS;
	}
	
	public int getlParametroEscherichia(){
		return L_PARAMETRO_ESCHERICHIA;
	}
	
	public int getlParametroFebre(){
		return L_PARAMETRO_FEBRE;
	}

	/* ------- USUARIO ------- */
	
	public int getlUsuarioNome() {
		return L_USUARIO_NOME;
	}

	public int getlUsuarioLogin() {
		return L_USUARIO_LOGIN;
	}

	public int getlUsuarioSenha() {
		return L_USUARIO_SENHA;
	}

	
}