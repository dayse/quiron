package relatorio;

import java.util.HashMap;

import relatorio.avaliacaoEspecialista.RelatorioListagemAvaliacaoEspecialista;
import relatorio.especialista.RelatorioListagemEspecialistas;
import relatorio.indicacao.RelatorioListagemIndicacao;
import relatorio.paciente.RelatorioListagemPacientes;
import relatorio.parametro.RelatorioListagemParametros;
import relatorio.usuario.RelatorioListagemUsuarios;

/**
 * Esta classe implementa o padr�o SINGLETON, cuja caracter�stica-chave de identifica��o � a presen�a de um
 * construtor privado, possuindo o intuito de retornar uma nova instancia de uma classe caso ela n�o tenha
 * sido instanciada ainda. Caso contr�rio, retorna uma referencia para a propria classe. 
 * 
 * @author Walanem
 */
public class RelatorioFactory {

    private final static HashMap<Integer, Relatorio> relatorios = new HashMap();
    
    private final static RelatorioFactory factory;
    
    //Este bloco � executado uma unica vez, que � quando a classe � instanciada pela primeira vez.
    static {
        factory = new RelatorioFactory();
    }
    
    //No construtor, o Map de Relat�rios � preenchido com todos os relat�rios existentes na aplica��o.
    private RelatorioFactory() {
    	relatorios.put(Relatorio.RELATORIO_AVALIACAO_DO_ESPECIALISTA, new RelatorioListagemAvaliacaoEspecialista());
    	relatorios.put(Relatorio.RELATORIO_LISTAGEM_DE_ESPECIALISTAS, new RelatorioListagemEspecialistas());
    	relatorios.put(Relatorio.RELATORIO_LISTAGEM_DE_INDICACAO, new RelatorioListagemIndicacao());
    	relatorios.put(Relatorio.RELATORIO_LISTAGEM_DE_PARAMETROS, new RelatorioListagemParametros());
    	relatorios.put(Relatorio.RELATORIO_LISTAGEM_DE_PACIENTES, new RelatorioListagemPacientes());
    	relatorios.put(Relatorio.RELATORIO_LISTAGEM_DE_USUARIOS, new RelatorioListagemUsuarios());
    }
    
    public static RelatorioFactory getInstance() {
        return factory;
    }

    public static Relatorio getRelatorio(int tipoRelatorio) {
        return relatorios.get(tipoRelatorio);
    }
}