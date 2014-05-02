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
	
	// --------------------- Avalicao ---------------------
	public static final int L_AVALIACAO_RESULTADO = 3;
	
	// --------------------- Paciente ---------------------
	public static final int L_PACIENTE_CODIGO = 15; 
	public static final int L_PACIENTE_NOME = 30;
	
	// --------------------- INDICACAO ---------------------
	public static final int L_INDICACAO_CODIGO = 15;
	public static final int L_INDICACAO_EFEITOSCOLATERAIS = 100;
	public static final int L_INDICACAO_INDICACAO = 100;
	public static final int L_INDICACAO_INTERACAOMEDICAMENTOSA = 100;
	public static final int L_INDICACAO_NOME = 30;
	public static final int L_INDICACAO_PERIODICIDADE = 100;
	public static final int L_INDICACAO_POSOLOGIA = 100;
	
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
  	
 	/* ------- AVALIACAO ------- */
  	
  	public int getlAvaliacaoResultado(){
  		return L_AVALIACAO_RESULTADO;
  	}
  	
  	/* ------- PACIENTE ------- */

	public int getlPacienteCodigo() {
		return L_PACIENTE_CODIGO;
	}

	public int getlPacienteNome() {
		return L_PACIENTE_NOME;
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

	/* ------- INDICACAO ------- */
	
	public static int getlINDICACAOCodigo() {
		return L_INDICACAO_CODIGO;
	}
	
	public static int getlINDICACAOEfeitosColaterais() {
		return L_INDICACAO_EFEITOSCOLATERAIS;
	}
	
	public static int getlINDICACAOIndicacao() {
		return L_INDICACAO_INDICACAO;
	}
	
	public static int getlINDICACAOInteracaoMedicamentosa() {
		return L_INDICACAO_INTERACAOMEDICAMENTOSA;
	}
	
	public static int getlINDICACAOPeriodicidade() {
		return L_INDICACAO_PERIODICIDADE;
	}
	
	public static int getlINDICACAOPosologia() {
		return L_INDICACAO_POSOLOGIA;
	}

	public static int getlINDICACAONome() {
		return L_INDICACAO_NOME;
	}
	
}