package AvalIndicacaoEspec;

import java.util.ArrayList;
import java.util.List;

import modelo.AvalIndicacaoEspec;
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
import service.AvalIndicacaoEspecAppService;
import service.EspecialistaAppService;
import service.IndicacaoAppService;
import service.ParametroAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.JPAUtil;

/**
 * Classe para testes dos metodos necessários ao funcionamento da biblioteca XFuzzy
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
	
	public void cargaDependencias(){
		List<CargaBase> cargas;
		cargas = new ArrayList<CargaBase>();
		
		cargas.add(new CargaIndicacao());
		cargas.add(new CargaEspecialista());
		cargas.add(new CargaParametros());
		
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
		/** falta um try catch em parametroService **/
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
	
}
