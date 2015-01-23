package relatorio.parametro;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import relatorio.DeclaracaoAbstrata;
import relatorio.Relatorio;
import relatorio.RelatorioPdf;
import util.Constantes;
import exception.RelatorioException;

/**
 * 
 * Classe que implementa os métodos
 * necessários para gerar o(s) relatório(s)
 * de parâmetros
 * 
 * @author bruno.oliveira
 *
 */
public class RelatorioListagemParametros extends DeclaracaoAbstrata {

	@Override
	public void gerarRelatorio(List dados, Map<String, Object> parametros) throws RelatorioException {
		// Define nome do arquivo pdf que vai ser gerado.
		String nomeArquivo = "RelatorioListagemParametros.pdf";
		
		// Recupera do stream o arquivo jasper compilado usando o caminho completo como parametro.	
		InputStream jasper = super.recuperaJasper(Relatorio.JASPER_RELATORIO_LISTAGEM_DE_PARAMETROS);

		parametros.put("LOGO_INT", Relatorio.LOGO_INT);
		
		RelatorioPdf pdf = new RelatorioPdf(Constantes.CAMINHO_RELATORIOS_GERADOS);
		
		// Preenchimento do relatorio com os dados	
		pdf.download(nomeArquivo, jasper, parametros, dados);
	}
	
}
