package Anamnese;

import java.util.ArrayList;
import java.util.List;

import modelo.Atendimento;
import modelo.ConjuntoAvaliacao;
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
//		cargaAtendimento.executar();
//		Atendimento atendimentoPaciente1 = atendimentoService.recuperaAtendimentoPorCodigoComPaciente("atp1");
//		
//		List<ConjuntoAvaliacao> conjuntoDeAvaliacoes = anamneseService.recuperaAvaliacaoCalculadaPorIndicacao(atendimentoPaciente1);
//
//		int num_conjuntos = 5;
//		
//		
//		AssertJUnit.assertEquals(num_conjuntos, conjuntoDeAvaliacoes.size());
		
		//teste avaliação amoxilina 500:
//		ConjuntoAvaliacao conjuntoAvaliacaoAmox500 = conjuntoDeAvaliacoes.get(0);
//		
//		AssertJUnit.assertEquals(conjuntoDeAvaliacoes., actual);
		
	}

//	@Test
//	public void testeCalculaAvaliacaoPorAtendimentoPorIndicacaoPorParametro() throws AplicacaoException {
//		Atendimento atendimentoPaciente1 = atendimentoService.recuperaAtendimentoPorCodigoComPaciente("atp1");
//		
//		List<ConjuntoAvaliacao> conjuntoDeAvaliacoes = anamneseService.recuperaAvaliacaoCalculadaPorIndicacao(atendimentoPaciente1);
//
//		int num_conjuntos = 5;
//		
//		
//		AssertJUnit.assertEquals(num_conjuntos, conjuntoDeAvaliacoes.size());
//		
//		//teste avaliação amoxilina 500:
////		ConjuntoAvaliacao conjuntoAvaliacaoAmox500 = conjuntoDeAvaliacoes.get(0);
////		
////		AssertJUnit.assertEquals(conjuntoDeAvaliacoes., actual);
//		
//	}
	

	public void cargaDependencias() {
		List<CargaBase> cargas;
		cargas = new ArrayList<CargaBase>();
		cargas.add(new CargaUsuario());
		cargas.add(new CargaParametros());
		cargas.add(new CargaIndicacao());
		//Inclui os especialistas
		cargas.add(new CargaEspecialista());
		//Inclui as avaliações dos especialistas para as indicações cadastradas
//		cargas.add(new CargaAvalIndicacaoEspec());
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
