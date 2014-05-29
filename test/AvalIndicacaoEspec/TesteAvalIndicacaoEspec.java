package AvalIndicacaoEspec;

import modelo.AvalIndicacaoEspec;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.JPAUtil;

/**
 * Classe para testes dos metodos necessários ao funcionamento da biblioteca XFuzzy
 * @author dayse.arruda
 *
 */
public class TesteAvalIndicacaoEspec {

	public AvalIndicacaoEspec avalIndicacaoEspec;

	@BeforeClass
	public void setupClass(){
		try {
			System.out.println("-----------------------------> Startando a JPA...");
			JPAUtil.JPAstartUp();
			System.out.println("-----------------------------> JPA startada com sucesso!");

			avalIndicacaoEspec = FabricaDeAppService.getAppService(AvalIndicacaoEspec.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
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
	
	@Test
	public void testeRecuperaAvaliacaoCalculadaPorIndicacao() throws AplicacaoException {
	//	anamneseService.recuperaAvaliacaoCalculadaPorIndicacao(atendimento);
		
	}
	
	
}
