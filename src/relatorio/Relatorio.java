package relatorio;

import java.util.List;
import java.util.Map;

import exception.RelatorioException;

import util.Constantes;

/**
 * 
 * Interface do relatorio implemantada
 * pelas classes que cuidam dos relatorios
 * do sistema.
 * 
 * @author bruno.oliveira
 *
 */
public interface Relatorio {
	
	    //Tipos de Relatorios do sistema Quiron:
		//Cada relatorio deve receber um n�mero diferente, 
		//isto � colocado no hashmap da fabrica de relatorios (RelatorioFactory).
		public final static int RELATORIO_AVALIACAO_DO_ESPECIALISTA = 0;
		public final static int RELATORIO_LISTAGEM_DE_PACIENTES = 1;
		public final static int RELATORIO_LISTAGEM_DE_ESPECIALISTAS = 2;
		public final static int RELATORIO_LISTAGEM_DE_INDICACAO = 3;
		public final static int RELATORIO_LISTAGEM_DE_PARAMETROS = 4;
		public final static int RELATORIO_HISTORICO_ATENDIMENTOS_PACIENTE = 5;
		public final static int RELATORIO_ATENDIMENTO_ESPECIFICO_PACIENTE = 6;
	    public final static int RELATORIO_LISTAGEM_DE_USUARIOS = 11;
	    //Define os nomes dos arquivos  relativos as Imagens do Relatorio,
	    // concatenando com os caminhos definidos em constantes.java
	    public static final String LOGO_INT = Constantes.CAMINHO_LOGOTIPOS + "logoINT2.jpg";
	    
	    //Nome dos Arquivos de Relatorio (compilados Jaspers) que ser�o criados
	    // no momento da compila��o (quando der preview no arquivo 
	    //*.jrxml que corresponde ao projeto) 
	    public static final String JASPER_RELATORIO_AVALIACAO_DO_ESPECIALISTA = Constantes.CAMINHO_JASPERS + "relatorioListagemAvaliacaoDoEspecialista.jasper";
	    public static final String JASPER_RELATORIO_LISTAGEM_DE_ESPECIALISTAS = Constantes.CAMINHO_JASPERS + "relatorioListagemEspecialistas.jasper";
	    public static final String JASPER_RELATORIO_LISTAGEM_DE_INDICACAO = Constantes.CAMINHO_JASPERS + "relatorioListagemIndicacao.jasper";
	    public static final String JASPER_RELATORIO_LISTAGEM_DE_PACIENTES = Constantes.CAMINHO_JASPERS + "relatorioListagemPacientes.jasper";
	    public static final String JASPER_RELATORIO_LISTAGEM_DE_PARAMETROS = Constantes.CAMINHO_JASPERS + "relatorioListagemParametro.jasper";
	    public static final String JASPER_RELATORIO_HISTORICO_ATENDIMENTOS_PACIENTE = Constantes.CAMINHO_JASPERS + "relatorioHistoricoAtendimentoPaciente.jasper";
	    public static final String JASPER_RELATORIO_ATENDIMENTO_ESPECIFICO_PACIENTE = Constantes.CAMINHO_JASPERS + "relatorioAtendimentoEspecificoDoPaciente.jasper";
	    public static final String JASPER_LISTAGEM_DE_USUARIOS = Constantes.CAMINHO_JASPERS + "relatorioListagemUsuarios.jasper";
	   
	    //M�todo que esta interface OBRIGA as subclasses a implementar, de acordo com seus requisitos.
	    public void gerarRelatorio(List dados, Map<String, Object> parametros) throws RelatorioException;
	    
	}