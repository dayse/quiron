package relatorio.indicacao;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import exception.RelatorioException;
import relatorio.DeclaracaoAbstrata;
import relatorio.Relatorio;
import relatorio.RelatorioPdf;
import util.Constantes;

public class RelatorioListagemIndicacao extends DeclaracaoAbstrata {

	@Override
	public void gerarRelatorio(List dados, Map<String, Object> parametros)
			throws RelatorioException {
		// Define o nome do arquivo pdf que vai ser gerado.
		String nomeArquivo = "RelatorioListagemIndicacao.pdf";
		
		// Recupera do stream o arquivo jasper compilado usando o caminho completo como parametro.
		InputStream jasper = super.recuperaJasper(Relatorio.JASPER_RELATORIO_LISTAGEM_DE_INDICACAO);
		
		parametros.put(LOGO_INT, Relatorio.LOGO_INT);
		
		RelatorioPdf pdf = new RelatorioPdf(Constantes.CAMINHO_RELATORIOS_GERADOS);
		
		// Preenchimento do relatório com os dados.
		pdf.download(nomeArquivo, jasper, parametros, dados);
	}

	
}
