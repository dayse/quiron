package AvalIndicacaoEspec;

import java.util.ArrayList;
import java.util.List;

import modelo.AvalIndicacaoEspec;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import carga.CargaAvalIndicacaoEspec;
import carga.CargaBase;
import carga.CargaEspecialista;
import carga.CargaIndicacao;
import carga.CargaParametros;
import service.AvalIndicacaoEspecAppService;
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

	@BeforeClass
	public void setupClass(){
		try {
			System.out.println("-----------------------------> Startando a JPA...");
			JPAUtil.JPAstartUp();
			System.out.println("-----------------------------> JPA startada com sucesso!");

			avalIndicacaoEspecService = FabricaDeAppService.getAppService(AvalIndicacaoEspecAppService.class);

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
			//	System.out.println(carga.toString());
				carga.executar();
			}
		}catch(AplicacaoException e ){
			AssertJUnit.fail("Erro na carga: " + e.getMessage());
		}
	}
	
}
