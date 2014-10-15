package relatorio;

import java.util.HashMap;

import relatorio.avaliacaoEspecialista.RelatorioListagemAvaliacaoEspecialista;
import relatorio.especialista.RelatorioListagemEspecialistas;
import relatorio.indicacao.RelatorioListagemIndicacao;
import relatorio.paciente.RelatorioListagemPacientes;
import relatorio.parametro.RelatorioListagemParametros;
import relatorio.usuario.RelatorioListagemUsuarios;

/**
 * Esta classe implementa o padrão SINGLETON, cuja característica-chave de identificação é a presença de um
 * construtor privado, possuindo o intuito de retornar uma nova instancia de uma classe caso ela não tenha
 * sido instanciada ainda. Caso contrário, retorna uma referencia para a propria classe. 
 * 
 * @author Walanem
 */
public class RelatorioFactory {

    private final static HashMap<Integer, Relatorio> relatorios = new HashMap();
    
    private final static RelatorioFactory factory;
    
    //Este bloco é executado uma unica vez, que é quando a classe é instanciada pela primeira vez.
    static {
        factory = new RelatorioFactory();
    }
    
    //No construtor, o Map de Relatórios é preenchido com todos os relatórios existentes na aplicação.
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