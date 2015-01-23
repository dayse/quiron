package relatorio.atendimento;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import exception.RelatorioException;
import relatorio.DeclaracaoAbstrata;
import relatorio.Relatorio;
import relatorio.RelatorioPdf;
import util.Constantes;

/**
 * 
 * Classe que implementa os métodos
 * necessários para gerar o(s) relatório(s)
 * de atendimento
 * 
 * @author bruno.oliveira
 *
 */
public class RelatorioAtendimento extends DeclaracaoAbstrata {

	@Override
	public void gerarRelatorio(List dados, Map<String, Object> parametros)
			throws RelatorioException {

		// Define nome do arquivo pdf que vai ser gerado.
		String nomeArquivo = "relatorioAtendimentoEspecificoDoPaciente.pdf";

		// Recupera do stream o arquivo jasper compilado usando o caminho
		// completo como parametro.
		InputStream jasper = super
				.recuperaJasper(Relatorio.JASPER_RELATORIO_ATENDIMENTO_ESPECIFICO_PACIENTE);

		parametros.put("LOGO_INT", Relatorio.LOGO_INT);

		RelatorioPdf pdf = new RelatorioPdf(
				Constantes.CAMINHO_RELATORIOS_GERADOS);

		// Preenchimento do relatorio com os dados
		pdf.download(nomeArquivo, jasper, parametros, dados);
	}

}
