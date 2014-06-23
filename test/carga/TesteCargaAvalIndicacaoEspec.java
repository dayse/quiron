package carga;

import java.util.ArrayList;
import java.util.List;

import modelo.AvalIndicacaoEspec;
import modelo.Especialista;

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
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.JPAUtil;

/**
 * Classe para testes dos metodos necessários ao funcionamento da biblioteca XFuzzy
 * @author bruno.oliveira
 *
 */
public class TesteCargaAvalIndicacaoEspec {

	public AvalIndicacaoEspecAppService avalIndicacaoEspecService;
	public EspecialistaAppService especialistaService;
	
	private CargaAvalIndicacaoEspec cargaAvalIndicacaoEspec;

	@BeforeClass
	public void setupClass(){
		try {
			System.out.println("-----------------------------> Startando a JPA...");
			JPAUtil.JPAstartUp();
			System.out.println("-----------------------------> JPA startada com sucesso!");

			avalIndicacaoEspecService = FabricaDeAppService.getAppService(AvalIndicacaoEspecAppService.class);
			especialistaService = FabricaDeAppService.getAppService(EspecialistaAppService.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		cargaAvalIndicacaoEspec = new CargaAvalIndicacaoEspec();
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
		int num = 1;
		try{
			for(CargaBase cargaDependente : cargaAvalIndicacaoEspec.getCargasDependentes()){
				cargaDependente.executar();
				System.out.println(num);
				num++;
			}
		}catch (AplicacaoException e){
			AssertJUnit.fail("Erro na carga: " + e.getMessage());
		}
	}
	
	@Test
	public void testeCargaAvalIndicacaoEspec() throws AplicacaoException{
		cargaAvalIndicacaoEspec.executar();
		
		Especialista especialista1 = new Especialista();
		try {
			especialista1 = especialistaService.recuperaEspecialistaPorCodigo("espec1");
		} catch (ObjetoNaoEncontradoException e) {
			e.printStackTrace();
		}
		Especialista especialista2 = new Especialista();
		try {
			especialista2 = especialistaService.recuperaEspecialistaPorCodigo("espec2");
		} catch (ObjetoNaoEncontradoException e) {
			e.printStackTrace();
		}
		int num_avaliacao = 35;
		
		List<AvalIndicacaoEspec> listaDeAvaliacaoDoEspec1 =
				avalIndicacaoEspecService.recuperaListaDeAvaliacaoEspecPaginada(especialista1);
		List<AvalIndicacaoEspec> listaDeAvaliacaoDoEspec2 =
				avalIndicacaoEspecService.recuperaListaDeAvaliacaoEspecPaginada(especialista2);
		
		AssertJUnit.assertEquals(num_avaliacao, listaDeAvaliacaoDoEspec1.size());
		AssertJUnit.assertEquals(num_avaliacao, listaDeAvaliacaoDoEspec2.size());
	}
}
