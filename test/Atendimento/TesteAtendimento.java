package Atendimento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import modelo.Anamnese;
import modelo.Atendimento;
import modelo.ConjuntoAvaliacao;
import modelo.Paciente;
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
import service.PacienteAppService;
import service.ParametroAppService;
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.Constantes;
import util.JPAUtil;
import util.jayflot.spider.SpiderMainPlot;

/**
 * Testes relativos a atendimento
 *
 */
public class TesteAtendimento {

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
			usuarioService = FabricaDeAppService
					.getAppService(UsuarioAppService.class);

			anamneseService = FabricaDeAppService.getAppService(AnamneseAppService.class);
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
			cargaAtendimento.executar();
		} catch (AplicacaoException e) {
			AssertJUnit.fail("Erro na carga:" + e.getMessage());
		}
		
	}
	
	@Test
	public void testeRecuperaListaDeAtendimentosComPacientePaginada() throws AplicacaoException {
		List<Atendimento> listaDeAtendimentos = atendimentoService.recuperaListaDeAtendimentosComPacientePaginada();
		
		int num_atendimentos = 2;
		AssertJUnit.assertEquals(num_atendimentos, listaDeAtendimentos.size());;

		Paciente paciente1 = pacienteService.recuperaPacientePorCodigo("paciente1");
		Paciente paciente2 = pacienteService.recuperaPacientePorCodigo("paciente2");

		Usuario clinico = usuarioService.recuperaPorLogin("clinico");
		Usuario tecnico = usuarioService.recuperaPorLogin("tecnico");

		Atendimento atendimentoPaciente1 = listaDeAtendimentos.get(0);
		Atendimento atendimentoPaciente2 = listaDeAtendimentos.get(1);

       	Calendar dataAtual_anterior = Calendar.getInstance();

		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getDataAtendimento().get(Calendar.DAY_OF_YEAR), 
				dataAtual_anterior.get(Calendar.DAY_OF_YEAR));
		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getPaciente(), 
				paciente1);
		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getMedico(), 
				clinico);

		AssertJUnit.assertEquals(
				atendimentoPaciente2.getPaciente(), 
				paciente2);
		
		AssertJUnit.assertEquals(
				atendimentoPaciente2.getTecnico(), 
				tecnico);
		
	}

	@Test
	public void testeRecuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLike() throws AplicacaoException {

		Usuario clinico = usuarioService.recuperaPorLogin("clinico");

		List<Atendimento> listaDeAtendimentos = atendimentoService.recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLike(clinico.getNome());
		
		int num_atendimentos = 2;
		AssertJUnit.assertEquals(num_atendimentos, listaDeAtendimentos.size());

		listaDeAtendimentos = atendimentoService.recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLike(clinico.getNome().toUpperCase());
		num_atendimentos = 2;
		AssertJUnit.assertEquals(num_atendimentos, listaDeAtendimentos.size());
		
		listaDeAtendimentos = atendimentoService.recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLike(clinico.getNome().toLowerCase());
		num_atendimentos = 2;
		AssertJUnit.assertEquals(num_atendimentos, listaDeAtendimentos.size());
		

		Paciente paciente1 = pacienteService.recuperaPacientePorCodigo("paciente1");
		Paciente paciente2 = pacienteService.recuperaPacientePorCodigo("paciente2");
		
		Atendimento atendimentoPaciente1 = listaDeAtendimentos.get(0);
		Atendimento atendimentoPaciente2 = listaDeAtendimentos.get(1);

       	Calendar dataAtual_anterior = Calendar.getInstance();

		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getDataAtendimento().get(Calendar.DAY_OF_YEAR), 
				dataAtual_anterior.get(Calendar.DAY_OF_YEAR));
		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getPaciente(), 
				paciente1);
		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getMedico(), 
				clinico);

		AssertJUnit.assertEquals(
				atendimentoPaciente2.getPaciente(), 
				paciente2);
		
	}
	@Test
	public void testeRecuperaListaDeAtendimentosPaginada() throws AplicacaoException {

		List<Atendimento> listaDeAtendimentos = atendimentoService.recuperaListaDeAtendimentosPaginada();
		
		int num_atendimentos = 2;
		AssertJUnit.assertEquals(num_atendimentos, listaDeAtendimentos.size());

		Atendimento atendimentoPaciente1 = listaDeAtendimentos.get(0);
       	Calendar dataAtual_anterior = Calendar.getInstance();
		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getDataAtendimento().get(Calendar.DAY_OF_YEAR), 
				dataAtual_anterior.get(Calendar.DAY_OF_YEAR));
	}
	@Test
	public void testeRecuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLike() throws AplicacaoException {

		Usuario clinico = usuarioService.recuperaPorLogin("clinico");
		Paciente paciente1 = pacienteService.recuperaPacientePorCodigo("paciente1");

		List<Atendimento> listaDeAtendimentos = atendimentoService.recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLike(paciente1.getNome());
		
		int num_atendimentos = 1;
		AssertJUnit.assertEquals(num_atendimentos, listaDeAtendimentos.size());

		listaDeAtendimentos = atendimentoService.recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLike(paciente1.getNome().toUpperCase());
		num_atendimentos = 1;
		AssertJUnit.assertEquals(num_atendimentos, listaDeAtendimentos.size());
		
		listaDeAtendimentos = atendimentoService.recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLike(paciente1.getNome().toLowerCase());
		num_atendimentos = 1;
		AssertJUnit.assertEquals(num_atendimentos, listaDeAtendimentos.size());
		
		
		Atendimento atendimentoPaciente1 = listaDeAtendimentos.get(0);
		

		AssertJUnit.assertEquals(
				atendimentoPaciente1.getPaciente(), 
				paciente1);
		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getMedico(), 
				clinico);
		
		Paciente paciente2 = pacienteService.recuperaPacientePorCodigo("paciente2");
		listaDeAtendimentos = atendimentoService.recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLike(paciente2.getNome());

		Atendimento atendimentoPaciente2 = listaDeAtendimentos.get(0);

		AssertJUnit.assertEquals(
				atendimentoPaciente2.getPaciente(), 
				paciente2);
		
		AssertJUnit.assertEquals(
				atendimentoPaciente2.getMedico(), 
				clinico);
		

		listaDeAtendimentos = atendimentoService.recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLike("p");

		atendimentoPaciente1 = listaDeAtendimentos.get(0);
		
       	Calendar dataAtual_anterior = Calendar.getInstance();

		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getDataAtendimento().get(Calendar.DAY_OF_YEAR), 
				dataAtual_anterior.get(Calendar.DAY_OF_YEAR));
		
	}

	@Test
	public void testeRecuperaListaPaginadaDeAtendimentosComPacientePorCodigoPaciente() throws AplicacaoException {

		Usuario clinico = usuarioService.recuperaPorLogin("clinico");
		Paciente paciente1 = pacienteService.recuperaPacientePorCodigo("paciente1");

		List<Atendimento> listaDeAtendimentos = atendimentoService.recuperaListaPaginadaDeAtendimentosComPacientePorCodigoPaciente(paciente1.getCodPaciente());
		
		int num_atendimentos = 1;
		AssertJUnit.assertEquals(num_atendimentos, listaDeAtendimentos.size());
		
		
		Atendimento atendimentoPaciente1 = listaDeAtendimentos.get(0);
		

       	Calendar dataAtual_anterior = Calendar.getInstance();

		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getDataAtendimento().get(Calendar.DAY_OF_YEAR), 
				dataAtual_anterior.get(Calendar.DAY_OF_YEAR));
		
		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getPaciente(), 
				paciente1);
		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getMedico(), 
				clinico);
		
		Paciente paciente2 = pacienteService.recuperaPacientePorCodigo("paciente2");
		listaDeAtendimentos = atendimentoService.recuperaListaPaginadaDeAtendimentosComPacientePorCodigoPaciente(paciente2.getCodPaciente());

		Atendimento atendimentoPaciente2 = listaDeAtendimentos.get(0);

		AssertJUnit.assertEquals(
				atendimentoPaciente2.getPaciente(), 
				paciente2);
		
		AssertJUnit.assertEquals(
				atendimentoPaciente2.getMedico(), 
				clinico);
		
	}

	@Test
	public void testeRecuperaListaPaginadaDeAtendimentosComPacienteComAnamnesePorCodigoPaciente() throws AplicacaoException {

		Usuario clinico = usuarioService.recuperaPorLogin("clinico");
		Paciente paciente1 = pacienteService.recuperaPacientePorCodigo("paciente1");


		List<Atendimento> listaDeAtendimentos = atendimentoService.
													recuperaListaPaginadaDeAtendimentosComPacienteComAnamnesePorCodigoPaciente(
															paciente1.getCodPaciente());
		
		int num_atendimentos = 1;
		AssertJUnit.assertEquals(num_atendimentos, listaDeAtendimentos.size());
		
		
		
		Atendimento atendimentoPaciente1 = listaDeAtendimentos.get(0);

       	Calendar dataAtual = Calendar.getInstance();

		AssertJUnit.assertEquals(
				atendimentoPaciente1.getDataAtendimento().get(Calendar.DAY_OF_YEAR), 
				dataAtual.get(Calendar.DAY_OF_YEAR));
		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getPaciente(), 
				paciente1);
		
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getMedico(), 
				clinico);

		List<Parametro> listaDeParametros = parametroService.recuperaListaDeParametrosPaginada();
		
		int num_anamneses = listaDeParametros.size();
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getAnamneses().size(), 
				num_anamneses);
		
		Parametro febre = listaDeParametros.get(0);
		Anamnese anamneseFebreAtendimentoPaciente1 = atendimentoPaciente1.getAnamneses().get(0);
		
		AssertJUnit.assertEquals(anamneseFebreAtendimentoPaciente1.getParametro().getCodParametro(), 
				febre.getCodParametro());
		
		
		
	}
	

	@Test
	public void testGeraGraficoGrauDeSemelhancaParaAvaliacaoDeIndicacaoDeAtendimento() throws AplicacaoException{

		Atendimento atendimentoPaciente1 = atendimentoService.recuperaAtendimentoPorCodigoComPaciente("atp1");
		
		List<ConjuntoAvaliacao> conjuntoDeAvaliacoes = anamneseService.recuperaAvaliacaoCalculadaPorIndicacaoPeloGrauSemelhanca(atendimentoPaciente1);
		
		SpiderMainPlot graficoGrauSemelhanca
					= atendimentoService.geraGraficoParaAvaliacaoDeIndicacaoDeAtendimento(
													conjuntoDeAvaliacoes, 
													atendimentoPaciente1
													);
		System.out.println("data:");
		System.out.println(graficoGrauSemelhanca.getPrintData());
		System.out.println("opt:");
		System.out.println(graficoGrauSemelhanca.getPrintOptions());
		
	}
}
