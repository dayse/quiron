package Atendimento;

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

import carga.CargaAtendimento;
import carga.CargaBase;
import service.AnamneseAppService;
import service.AtendimentoAppService;
import service.PacienteAppService;
import service.ParametroAppService;
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.Constantes;
import util.JPAUtil;

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
	public void testeRecuperaListaDeAtendimentosPaginada() throws AplicacaoException {
		cargaAtendimento.executar();
		List<Atendimento> listaDeAtendimentos = atendimentoService.recuperaListaDeAtendimentosPaginada();
		
		int num_atendimentos = 2;
		AssertJUnit.assertEquals(num_atendimentos, listaDeAtendimentos.size());;

		Paciente paciente1 = pacienteService.recuperaPacientePorCodigo("paciente1");
		Paciente paciente2 = pacienteService.recuperaPacientePorCodigo("paciente2");

		Usuario clinico = usuarioService.recuperaPorLogin("clinico");
		Usuario tecnico = usuarioService.recuperaPorLogin("tecnico");

		Atendimento atendimentoPaciente1 = listaDeAtendimentos.get(0);
		AssertJUnit.assertEquals(
				atendimentoPaciente1.getPaciente(), 
				paciente1);

		AssertJUnit.assertEquals(
				atendimentoPaciente1.getMedico(), 
				clinico);
		
	}
}
