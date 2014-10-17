package parametro;

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
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.Constantes;
import util.JPAUtil;
import utils.TesteCaseBase;

/**
 * Testes referentes a Parametro
 * @author felipe.pontes
 *
 */
public class TesteParametro {

	private static ParametroAppService parametroService;
	public UsuarioAppService usuarioService;

	@BeforeClass
	public void setupClass(){
		try {
			System.out.println("-----------------------------> Startando a JPA...");
			JPAUtil.JPAstartUp();
			System.out.println("-----------------------------> JPA startada com sucesso!");
			parametroService = FabricaDeAppService.getAppService(ParametroAppService.class);
			usuarioService = FabricaDeAppService.getAppService(UsuarioAppService.class);
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
		}
		
	}

	public void setConstantes(){
		String sep = java.io.File.separator;
		String base_path = System.getProperty("user.dir") + sep + "WebContent";
		
        Constantes.CAMINHO_ABSOLUTO_ARQUIVOS_CARGA = base_path + sep + Constantes.CAMINHO_ARQUIVOS_CARGA + sep;
        Constantes.CAMINHO_ABSOLUTO_ARQUIVO_USUARIOS_CARGA = base_path + sep + Constantes.CAMINHO_ARQUIVO_USUARIOS_CARGA + sep;
		
	}
	
	@Test
	public void testeVerificaUsuarioAutenticadoTemPermissao() throws AplicacaoException {


		Usuario admin = usuarioService.recuperaPorLoginESenha("admin", "123");
		Usuario engenheiro = usuarioService.recuperaPorLoginESenha("engenheiro", "123");
		Usuario aluno = usuarioService.recuperaPorLoginESenha("aluno", "123");
		Usuario clinico = usuarioService.recuperaPorLoginESenha("clinico", "123");
		Usuario tecnico = usuarioService.recuperaPorLoginESenha("tecnico", "123");
		

		AssertJUnit.assertEquals(Boolean.valueOf(true), parametroService.verificaUsuarioAutenticadoTemPermissao(admin));
		AssertJUnit.assertEquals(Boolean.valueOf(true), parametroService.verificaUsuarioAutenticadoTemPermissao(engenheiro));
		
		try{
			AssertJUnit.assertEquals(Boolean.valueOf(false), parametroService.verificaUsuarioAutenticadoTemPermissao(aluno));
			AssertJUnit.assertEquals(Boolean.valueOf(false), parametroService.verificaUsuarioAutenticadoTemPermissao(clinico));
			AssertJUnit.assertEquals(Boolean.valueOf(false), parametroService.verificaUsuarioAutenticadoTemPermissao(tecnico));
			AssertJUnit.fail("Não jogou AplicacaoException por conta da verificacao de autenticacao.");
		}
		catch(AplicacaoException ex){
		}
		
	}

	@Test
	public void testeVerificaPesoDeParametro() throws AplicacaoException {
		
		Parametro febre = new Parametro();
		Parametro alergia = new Parametro();
		DecimalFormat df = new DecimalFormat("0.00");
		
		febre = parametroService.recuperaParametroPorCodigo("P001");
		alergia = parametroService.recuperaParametroPorCodigo("P008");
		
		AssertJUnit.assertEquals(df.format(1.0), df.format(febre.getPeso()));
		AssertJUnit.assertEquals(df.format(10.0), df.format(alergia.getPeso()));
		
	}

	public void cargaDependencias() {
		List<CargaBase> cargas;
		cargas = new ArrayList<CargaBase>();
		cargas.add(new CargaUsuario());
		cargas.add(new CargaParametros());
		
		try {
			for (CargaBase carga : cargas) {
				carga.executar();
			}
		} catch (AplicacaoException e) {
			AssertJUnit.fail("Erro na carga:" + e.getMessage());
		}
	}
	
	
}
