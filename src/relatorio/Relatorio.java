package relatorio;

import java.util.List;
import java.util.Map;

import exception.RelatorioException;

import util.Constantes;


public interface Relatorio {
	
	    //Tipos de Relatorios do sistema GESPLAN:
		//Cada relatorio deve receber um número diferente, 
		//isto é colocado no hashmap da fabrica de relatorios (RelatorioFactory).
		public final static int RELATORIO_AVALIACAO_DO_ESPECIALISTA = 0;
		public final static int RELATORIO_LISTAGEM_DE_ESPECIALISTAS=2;
	    public final static int RELATORIO_LISTAGEM_DE_USUARIOS = 11;
	    //Define os nomes dos arquivos  relativos as Imagens do Relatorio,
	    // concatenando com os caminhos definidos em constantes.java
	    public static final String LOGO_INT = Constantes.CAMINHO_LOGOTIPOS + "logoINT2.jpg";
	    
	    //Nome dos Arquivos de Relatorio (compilados Jaspers) que serão criados
	    // no momento da compilação (quando der preview no arquivo 
	    //*.jrxml que corresponde ao projeto) 
	    public static final String JASPER_RELATORIO_AVALIACAO_DO_ESPECIALISTA = Constantes.CAMINHO_JASPERS + "relatorioListagemAvaliacaoDoEspecialista.jasper";
	    public static final String JASPER_RELATORIO_LISTAGEM_DE_ESPECIALISTAS = Constantes.CAMINHO_JASPERS + "relatorioListagemEspecialistas.jasper";
	    public static final String JASPER_LISTAGEM_DE_USUARIOS = Constantes.CAMINHO_JASPERS + "relatorioListagemUsuarios.jasper";
	   
	    //Método que esta interface OBRIGA as subclasses a implementar, de acordo com seus requisitos.
	    public void gerarRelatorio(List dados, Map<String, Object> parametros) throws RelatorioException;
	    
	}