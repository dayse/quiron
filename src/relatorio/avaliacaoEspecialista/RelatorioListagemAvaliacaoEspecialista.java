package relatorio.avaliacaoEspecialista;

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
 * de avaliação dos especialistas.
 * 
 * @author bruno.oliveira
 *
 */
public class RelatorioListagemAvaliacaoEspecialista extends DeclaracaoAbstrata {

	@Override
	public void gerarRelatorio(List dados, Map<String, Object> parametros) throws RelatorioException {

		String nomeArquivo = "RelatorioListagemAvaliacaoDoEspecialista.pdf";

		InputStream jasper = super.recuperaJasper(Relatorio.JASPER_RELATORIO_AVALIACAO_DO_ESPECIALISTA);

		parametros.put("LOGO_INT", Relatorio.LOGO_INT);
		
		RelatorioPdf pdf = new RelatorioPdf(Constantes.CAMINHO_RELATORIOS_GERADOS);
		pdf.download(nomeArquivo, jasper, parametros, dados);

	}
}
