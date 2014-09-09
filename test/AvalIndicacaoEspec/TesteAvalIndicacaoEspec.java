package AvalIndicacaoEspec;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import modelo.AvalIndicacaoEspec;
import modelo.Avaliacao;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.Parametro;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import DAO.exception.ObjetoNaoEncontradoException;
import carga.CargaAvalIndicacaoEspec;
import carga.CargaBase;
import carga.CargaEspecialista;
import carga.CargaIndicacao;
import carga.CargaParametros;
import carga.CargaUsuario;
import service.AvalIndicacaoEspecAppService;
import service.EspecialistaAppService;
import service.IndicacaoAppService;
import service.ParametroAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.Constantes;
import util.JPAUtil;

/**
 * Classe para testes dos metodos necessÃ¡rios ao funcionamento da biblioteca XFuzzy
 * @author bruno.oliveira
 *
 */
public class TesteAvalIndicacaoEspec {

	public AvalIndicacaoEspecAppService avalIndicacaoEspecService;
	private static ParametroAppService parametroService;
	private static IndicacaoAppService indicacaoService;
	private static EspecialistaAppService especialistaService;

	@BeforeClass
	public void setupClass(){
		try {
			System.out.println("-----------------------------> Startando a JPA...");
			JPAUtil.JPAstartUp();
			System.out.println("-----------------------------> JPA startada com sucesso!");

			avalIndicacaoEspecService = FabricaDeAppService.getAppService(AvalIndicacaoEspecAppService.class);
			parametroService = FabricaDeAppService.getAppService(ParametroAppService.class);
			indicacaoService = FabricaDeAppService.getAppService(IndicacaoAppService.class);
			especialistaService = FabricaDeAppService.getAppService(EspecialistaAppService.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
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
		List<CargaBase> cargas;
		cargas = new ArrayList<CargaBase>();

		cargas.add(new CargaUsuario());
		cargas.add(new CargaParametros());
		cargas.add(new CargaIndicacao());
		//Inclui os especialistas
		cargas.add(new CargaEspecialista());
		//Inclui as avaliações dos especialistas para as indicações cadastradas
		cargas.add(new CargaAvalIndicacaoEspec());
		
		try{
			for(CargaBase carga : cargas){
				carga.executar();
			}
		}catch(AplicacaoException e ){
			AssertJUnit.fail("Erro na carga: " + e.getMessage());
		}
	}
	
	@Test
	public void testeRecuperaAvalIndicacaoEspec() throws AplicacaoException{
		Especialista espec1 = new Especialista();
		Indicacao amox500 = new Indicacao();
		Parametro febre = new Parametro();
		AvalIndicacaoEspec avaliacaoRecuperada = new AvalIndicacaoEspec();
				
		febre = parametroService.recuperaParametroPorCodigo("P1");
		
		try {
			espec1 = especialistaService.recuperaEspecialistaPorCodigo("espec1");
		} catch (ObjetoNaoEncontradoException e) {
			e.printStackTrace();
		}
		try {
			amox500 = indicacaoService.recuperaIndicacaoPorCodigo("amox500");
		} catch (ObjetoNaoEncontradoException e) {
			e.printStackTrace();
		}

		try {
			avaliacaoRecuperada =
					avalIndicacaoEspecService.
					recuperaAvaliacaoPorEspecialistaIndicacaoParametro(espec1, amox500, febre);
		} catch (ObjetoNaoEncontradoException e) {
			e.printStackTrace();
		}
		
		AssertJUnit.assertEquals("amox500", avaliacaoRecuperada.getIndicacao().getCodIndicacao());
		AssertJUnit.assertEquals("espec1", avaliacaoRecuperada.getEspecialista().getCodEspecialista());
		AssertJUnit.assertEquals("P1", avaliacaoRecuperada.getParametro().getCodParametro());
	}

	@Test
	public void testCalculaAvaliacaoPorIndicacaoPorParametro()  throws AplicacaoException{
		Indicacao amox500 = new Indicacao();
		Parametro febre = new Parametro();
		Avaliacao avaliacaoRecuperada = new Avaliacao();

		DecimalFormat df = new DecimalFormat("0.00"); 
		febre = parametroService.recuperaParametroPorCodigo("P1");
		
		try {
			amox500 = indicacaoService.recuperaIndicacaoPorCodigo("amox500");
		} catch (ObjetoNaoEncontradoException e) {
			e.printStackTrace();
		}

		avaliacaoRecuperada =
				avalIndicacaoEspecService.
				calculaAvaliacaoPorIndicacaoPorParametro(amox500, febre);
		
		AssertJUnit.assertEquals("amox500", avaliacaoRecuperada.getIndicacao().getCodIndicacao());
		AssertJUnit.assertEquals("P1", avaliacaoRecuperada.getParametro().getCodParametro());

		AssertJUnit.assertEquals(df.format(0.8), df.format(avaliacaoRecuperada.getIntersecao()));
		AssertJUnit.assertEquals(df.format(0.8), df.format(avaliacaoRecuperada.getUniao()));
	}
	
	@Test
	public void testCalculaMediaAvaliacaoEspecialistasPorIndicacaoPorParametro()  throws AplicacaoException{
		Indicacao amox500 = new Indicacao();
		Parametro febre = new Parametro();
		DecimalFormat df = new DecimalFormat("0.00"); 
		Double mediaFebreAmox500 = 0.8;

		febre = parametroService.recuperaParametroPorCodigo("P1");
		
		try {
			amox500 = indicacaoService.recuperaIndicacaoPorCodigo("amox500");
		} catch (ObjetoNaoEncontradoException e) {
			e.printStackTrace();
		}

		Double mediaRecuperada =
				avalIndicacaoEspecService.
				calculaMediaAvaliacaoEspecialistasPorIndicacaoPorParametro(amox500, febre);
		
		AssertJUnit.assertEquals(df.format(0.8), df.format(mediaRecuperada));
	}
	
	@Test
	public void testRecuperaAvaliacaoPorIndicacaoParametro() throws AplicacaoException{
		Indicacao amox500 = new Indicacao();
		Parametro febre = new Parametro();
		DecimalFormat df = new DecimalFormat("0.00"); 
		Double valorFebreAmox500 = 0.6;

		febre = parametroService.recuperaParametroPorCodigo("P1");
		
		try {
			amox500 = indicacaoService.recuperaIndicacaoPorCodigo("amox500");
		} catch (ObjetoNaoEncontradoException e) {
			e.printStackTrace();
		}

		List<AvalIndicacaoEspec> listAvalIndicacaoEspec = avalIndicacaoEspecService
				.recuperaAvaliacaoPorIndicacaoParametro(amox500, febre);
		AvalIndicacaoEspec avEspec1Amox500Febre = listAvalIndicacaoEspec.get(0);
		AssertJUnit.assertEquals(df.format(valorFebreAmox500), df.format(avEspec1Amox500Febre.getValor()));
	}

	
}
