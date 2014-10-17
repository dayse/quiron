package carga;

import java.util.List;

import modelo.Anamnese;
import modelo.Atendimento;
import modelo.Paciente;
import modelo.Parametro;
import modelo.Usuario;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import service.AnamneseAppService;
import service.AtendimentoAppService;
import service.PacienteAppService;
import service.ParametroAppService;
import service.TipoUsuarioAppService;
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.Constantes;
import util.JPAUtil;

/**
 * Classe para testes dos metodos necessários ao funcionamento da biblioteca XFuzzy
 * @author dayse.arruda
 *
 */
public class TesteCargaAtendimento {

	private static PacienteAppService pacienteService;
	private static AtendimentoAppService atendimentoService;
	private static ParametroAppService parametroService;
	private static AnamneseAppService anamneseService;
	private static UsuarioAppService usuarioService;

	private CargaAtendimento cargaAtendimento;
	@BeforeClass
	public void setupClass(){
		try {
			System.out.println("-----------------------------> Startando a JPA...");
			JPAUtil.JPAstartUp();
			System.out.println("-----------------------------> JPA startada com sucesso!");

			pacienteService = FabricaDeAppService.getAppService(PacienteAppService.class);
			atendimentoService = FabricaDeAppService.getAppService(AtendimentoAppService.class);
			parametroService = FabricaDeAppService.getAppService(ParametroAppService.class);
			anamneseService = FabricaDeAppService
					.getAppService(AnamneseAppService.class);
			usuarioService = FabricaDeAppService
					.getAppService(UsuarioAppService.class);

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
			System.exit(1);
		}
		System.out.println("AfterMethod");
		
	}
	
	public void setConstantes(){
		String sep = java.io.File.separator;
		String base_path = System.getProperty("user.dir") + sep + "WebContent";
		
        Constantes.CAMINHO_ABSOLUTO_ARQUIVOS_CARGA = base_path + sep + Constantes.CAMINHO_ARQUIVOS_CARGA + sep;
        Constantes.CAMINHO_ABSOLUTO_ARQUIVO_USUARIOS_CARGA = base_path + sep + Constantes.CAMINHO_ARQUIVO_USUARIOS_CARGA + sep;
		
	}
	

	public void cargaDependencias(){
		try {
			for (CargaBase cargaDependente : cargaAtendimento.getCargasDependentes()) {
					cargaDependente.executar();
			}
		} catch (AplicacaoException e) {
			AssertJUnit.fail("Erro na carga:" + e.getMessage());
		}
		
	}
	
	@Test
	public void testeCargaIncluirAtendimentos() throws AplicacaoException {
		cargaAtendimento.executar();
		List<Atendimento> listaDeAtendimentos = atendimentoService.recuperaListaDeAtendimentosPaginada();
		
		int num_atendimentos = 2;
		AssertJUnit.assertEquals(num_atendimentos, listaDeAtendimentos.size());;

		Paciente paciente1 = pacienteService.recuperaPacientePorCodigo("paciente1");
		Paciente paciente2 = pacienteService.recuperaPacientePorCodigo("paciente2");

		Usuario clinico = usuarioService.recuperaPorLogin("clinico");
		Usuario tecnico = usuarioService.recuperaPorLogin("tecnico");

		Atendimento atendimentoPaciente1 = atendimentoService.recuperaAtendimentoPorCodigoComPaciente("atp1");

		AssertJUnit.assertEquals(
				atendimentoPaciente1.getPaciente(), 
				paciente1);
		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getMedico(), 
				clinico);
		
		Atendimento atendimentoPaciente2 = atendimentoService.recuperaAtendimentoPorCodigoComPaciente("atp2");

		AssertJUnit.assertEquals(
				atendimentoPaciente2.getPaciente(), 
				paciente2);

		AssertJUnit.assertEquals(
				atendimentoPaciente2.getMedico(), 
				clinico);
		
		AssertJUnit.assertEquals(
				atendimentoPaciente2.getTecnico(), 
				tecnico);
		
	}
	
	@Test(dependsOnMethods="testeCargaIncluirAtendimentos")
	public void testeCargaIncluirAnamneses() throws AplicacaoException {

		Parametro febre = parametroService.recuperaParametroPorCodigo("P001");
		Parametro disuria = parametroService.recuperaParametroPorCodigo("P002");
		Parametro diabetes = parametroService.recuperaParametroPorCodigo("P003");
		Parametro enterococos = parametroService.recuperaParametroPorCodigo("P004");
		Parametro escherichia = parametroService.recuperaParametroPorCodigo("P005");
		Parametro candida = parametroService.recuperaParametroPorCodigo("P006");
		Parametro efeitosColaterais = parametroService.recuperaParametroPorCodigo("P007");
		Parametro alergia = parametroService.recuperaParametroPorCodigo("P008");
		
		Atendimento atendimentoPaciente1 = atendimentoService.recuperaAtendimentoPorCodigoComPaciente("atp1");
		Atendimento atendimentoPaciente2 = atendimentoService.recuperaAtendimentoPorCodigoComPaciente("atp2");
		
		List<Anamnese> anamnesesAtp1 = anamneseService.recuperaListaDeAnamnesePorAtendimento(atendimentoPaciente1);
		

		int num_anamnesesAtp1 = 8;
		AssertJUnit.assertEquals(num_anamnesesAtp1, anamnesesAtp1.size());

		Anamnese atendimentoPc1_febre = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente1,febre);
		AssertJUnit.assertEquals(0.7, atendimentoPc1_febre.getValor());

		Anamnese atendimentoPc1_disuria = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente1,disuria);
		AssertJUnit.assertEquals(0.8, atendimentoPc1_disuria.getValor());

		Anamnese atendimentoPc1_diabetes = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente1,diabetes);
		AssertJUnit.assertEquals(0.7, atendimentoPc1_diabetes.getValor());

		Anamnese atendimentoPc1_enterococos = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente1,enterococos);
		AssertJUnit.assertEquals(0.0, atendimentoPc1_enterococos.getValor());

		Anamnese atendimentoPc1_escherichia = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente1,escherichia);
		AssertJUnit.assertEquals(0.0, atendimentoPc1_escherichia.getValor());

		Anamnese atendimentoPc1_candida = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente1,candida);
		AssertJUnit.assertEquals(1.0, atendimentoPc1_candida.getValor());

		Anamnese atendimentoPc1_efeitosColaterais = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente1,efeitosColaterais);
		AssertJUnit.assertEquals(1.0, atendimentoPc1_efeitosColaterais.getValor());

		Anamnese atendimentoPc1_alergia = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente1,alergia);
		AssertJUnit.assertEquals(1.0, atendimentoPc1_alergia.getValor());		

		List<Anamnese> anamnesesAtp2 = anamneseService.recuperaListaDeAnamnesePorAtendimento(atendimentoPaciente2);
		
		int num_anamnesesAtp2 = 8;
		AssertJUnit.assertEquals(num_anamnesesAtp2, anamnesesAtp2.size());

		Anamnese atendimentoPc2_febre = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente2,febre);
		AssertJUnit.assertEquals(0.2, atendimentoPc2_febre.getValor());

		Anamnese atendimentoPc2_disuria = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente2,disuria);
		AssertJUnit.assertEquals(0.4, atendimentoPc2_disuria.getValor());

		Anamnese atendimentoPc2_diabetes = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente2,diabetes);
		AssertJUnit.assertEquals(0.3, atendimentoPc2_diabetes.getValor());

		Anamnese atendimentoPc2_enterococos = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente2,enterococos);
		AssertJUnit.assertEquals(1.0, atendimentoPc2_enterococos.getValor());

		Anamnese atendimentoPc2_escherichia = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente2,escherichia);
		AssertJUnit.assertEquals(0.0, atendimentoPc2_escherichia.getValor());

		Anamnese atendimentoPc2_candida = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente2,candida);
		AssertJUnit.assertEquals(0.0, atendimentoPc2_candida.getValor());

		Anamnese atendimentoPc2_efeitosColaterais = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente2,efeitosColaterais);
		AssertJUnit.assertEquals(1.0, atendimentoPc2_efeitosColaterais.getValor());

		Anamnese atendimentoPc2_alergia = anamneseService.recuperaAnamnesePorAtendimentoPorParametro(atendimentoPaciente2,alergia);
		AssertJUnit.assertEquals(0.0, atendimentoPc2_alergia.getValor());		
	}
	
	
}
