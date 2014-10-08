package Anamnese;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import modelo.Atendimento;
import modelo.Avaliacao;
import modelo.ConjuntoAvaliacao;
import modelo.Parametro;
import modelo.Usuario;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import carga.CargaAtendimento;
import carga.CargaAvalIndicacaoEspec;
import carga.CargaBase;
import carga.CargaEspecialista;
import carga.CargaIndicacao;
import carga.CargaPaciente;
import carga.CargaParametros;
import carga.CargaUsuario;
import service.AnamneseAppService;
import service.AtendimentoAppService;
import service.ParametroAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.Constantes;
import util.JPAUtil;
import utils.TesteCaseBase;

/**
 * Testes referentes a Anamnese
 * @author felipe.pontes
 *
 */
public class TesteAnamnese {

	public AnamneseAppService anamneseService;
	private static ParametroAppService parametroService;
	private static AtendimentoAppService atendimentoService;

	private CargaAtendimento cargaAtendimento;

	@BeforeClass
	public void setupClass(){
		try {
			System.out.println("-----------------------------> Startando a JPA...");
			JPAUtil.JPAstartUp();
			System.out.println("-----------------------------> JPA startada com sucesso!");

			anamneseService = FabricaDeAppService.getAppService(AnamneseAppService.class);
			atendimentoService = FabricaDeAppService.getAppService(AtendimentoAppService.class);
			parametroService = FabricaDeAppService.getAppService(ParametroAppService.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		cargaAtendimento = new CargaAtendimento();
		setConstantes();
		cargaDependencias();
	}
	
	@AfterClass
	public void tearDown(){
		try {
			System.out.println("-----------------------------> desligando a JPA...");
			JPAUtil.closeEntityManager();
			System.out.println("-----------------------------> JPA desligada com sucesso!");


		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void setConstantes(){
		String sep = java.io.File.separator;
		String base_path = System.getProperty("user.dir") + sep + "WebContent";
		
        Constantes.CAMINHO_ABSOLUTO_ARQUIVOS_CARGA = base_path + sep + Constantes.CAMINHO_ARQUIVOS_CARGA + sep;
        Constantes.CAMINHO_ABSOLUTO_ARQUIVO_USUARIOS_CARGA = base_path + sep + Constantes.CAMINHO_ARQUIVO_USUARIOS_CARGA + sep;
		
	}
	
	@Test
	public void testeRecuperaAvaliacaoCalculadaPorIndicacao() throws AplicacaoException {
		cargaAtendimento.executar();

		DecimalFormat df = new DecimalFormat("0.00"); 
		
		Parametro febre = parametroService.recuperaParametroPorCodigo("P001");
		Parametro disuria = parametroService.recuperaParametroPorCodigo("P002");
		Parametro diabetes = parametroService.recuperaParametroPorCodigo("P003");
		Parametro enterococos = parametroService.recuperaParametroPorCodigo("P004");
		Parametro escherichia = parametroService.recuperaParametroPorCodigo("P005");
		Parametro candida = parametroService.recuperaParametroPorCodigo("P006");
		Parametro efeitosColaterais = parametroService.recuperaParametroPorCodigo("P007");
		Parametro alergia = parametroService.recuperaParametroPorCodigo("P008");
		
		Atendimento atendimentoPaciente1 = atendimentoService.recuperaAtendimentoPorCodigoComPaciente("atp1");
		
		List<ConjuntoAvaliacao> conjuntoDeAvaliacoes = anamneseService.recuperaAvaliacaoCalculadaPorIndicacao(atendimentoPaciente1);

		int num_conjuntos = 5;
		
		
		AssertJUnit.assertEquals(num_conjuntos, conjuntoDeAvaliacoes.size());
		
//		teste avaliação amoxilina 1:
		ConjuntoAvaliacao conjuntoAvaliacaoAmox1 = conjuntoDeAvaliacoes.get(4);
		AssertJUnit.assertEquals("amox1", conjuntoAvaliacaoAmox1.getIndicacao().getCodIndicacao());

		Avaliacao amox1Febre = conjuntoAvaliacaoAmox1.getAvaliacoes().get(0);

		AssertJUnit.assertEquals(febre.getCodParametro(), amox1Febre.getParametro().getCodParametro());
		AssertJUnit.assertEquals(df.format(0.6), df.format(amox1Febre.getIntersecao()));
		AssertJUnit.assertEquals(df.format(0.7), df.format(amox1Febre.getUniao()));
		
		AssertJUnit.assertEquals(df.format(2.4), df.format(conjuntoAvaliacaoAmox1.getSomatorioIntersecao()));
		AssertJUnit.assertEquals(df.format(5.5), df.format(conjuntoAvaliacaoAmox1.getSomatorioUniao()));
		AssertJUnit.assertEquals(df.format(0.436), df.format(conjuntoAvaliacaoAmox1.getGrauSemelhanca()));

//		teste avaliação amoxilina 500:
		ConjuntoAvaliacao conjuntoAvaliacaoAmox500 = conjuntoDeAvaliacoes.get(3);
		AssertJUnit.assertEquals("amox500", conjuntoAvaliacaoAmox500.getIndicacao().getCodIndicacao());

		Avaliacao amox500Disuria = conjuntoAvaliacaoAmox500.getAvaliacoes().get(1);

		AssertJUnit.assertEquals(disuria.getCodParametro(), amox500Disuria.getParametro().getCodParametro());
		AssertJUnit.assertEquals(df.format(0.8), df.format(amox500Disuria.getIntersecao()));
		AssertJUnit.assertEquals(df.format(1.0), df.format(amox500Disuria.getUniao()));
		
		AssertJUnit.assertEquals(df.format(3.3), df.format(conjuntoAvaliacaoAmox500.getSomatorioIntersecao()));
		AssertJUnit.assertEquals(df.format(6.4), df.format(conjuntoAvaliacaoAmox500.getSomatorioUniao()));
		AssertJUnit.assertEquals(df.format(0.516), df.format(conjuntoAvaliacaoAmox500.getGrauSemelhanca()));

//		teste avaliação bactrim:
		ConjuntoAvaliacao conjuntoAvaliacaoBactrim = conjuntoDeAvaliacoes.get(2);
		AssertJUnit.assertEquals("bactrim", conjuntoAvaliacaoBactrim.getIndicacao().getCodIndicacao());

		Avaliacao bactrimDiabetes = conjuntoAvaliacaoBactrim.getAvaliacoes().get(2);

		AssertJUnit.assertEquals(diabetes.getCodParametro(), bactrimDiabetes.getParametro().getCodParametro());
		AssertJUnit.assertEquals(df.format(0.7), df.format(bactrimDiabetes.getIntersecao()));
		AssertJUnit.assertEquals(df.format(0.7), df.format(bactrimDiabetes.getUniao()));
		
		AssertJUnit.assertEquals(df.format(3.1), df.format(conjuntoAvaliacaoBactrim.getSomatorioIntersecao()));
		AssertJUnit.assertEquals(df.format(5.8), df.format(conjuntoAvaliacaoBactrim.getSomatorioUniao()));
		AssertJUnit.assertEquals(df.format(0.534), df.format(conjuntoAvaliacaoBactrim.getGrauSemelhanca()));
		
		double max_grau_semalhanca = 0.0;
		for (ConjuntoAvaliacao conjuntoAvaliacao : conjuntoDeAvaliacoes) {
			if (conjuntoAvaliacao.getGrauSemelhanca() >= max_grau_semalhanca){
				max_grau_semalhanca = conjuntoAvaliacao.getGrauSemelhanca();
			}
			
		}
		
	}

	public void cargaDependencias() {
		List<CargaBase> cargas;
		cargas = new ArrayList<CargaBase>();
		cargas.add(new CargaUsuario());
		cargas.add(new CargaParametros());
		cargas.add(new CargaIndicacao());
		//Inclui os especialistas
		cargas.add(new CargaEspecialista());
		//Inclui as avaliações dos especialistas para as indicações cadastradas
		cargas.add(new CargaAvalIndicacaoEspec());
		//Inclui os pacientes
		cargas.add(new CargaPaciente());
		
		
		try {
			for (CargaBase carga : cargas) {
				carga.executar();
			}
		} catch (AplicacaoException e) {
			AssertJUnit.fail("Erro na carga:" + e.getMessage());
		}
	}
	
	
}
