package relatorio.paciente;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import relatorio.DeclaracaoAbstrata;
import relatorio.Relatorio;
import relatorio.RelatorioPdf;
import util.Constantes;
import exception.RelatorioException;

public class RelatorioListagemPacientes extends DeclaracaoAbstrata {

	@Override
	public void gerarRelatorio(List dados, Map<String, Object> parametros) throws RelatorioException {

		String nomeArquivo = "RelatorioListagemPacientes.pdf";

		InputStream jasper = super.recuperaJasper(Relatorio.JASPER_LISTAGEM_DE_PACIENTES);

		parametros.put("LOGO_COPPE", Relatorio.LOGO_COPPE);
		parametros.put("LOGO_INT", Relatorio.LOGO_INT);
		
		RelatorioPdf pdf = new RelatorioPdf(Constantes.CAMINHO_RELATORIOS_GERADOS);
		pdf.download(nomeArquivo, jasper, parametros, dados);

	}
}
