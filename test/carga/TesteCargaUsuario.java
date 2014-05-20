package carga;

import java.util.List;

import modelo.Usuario;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import service.TipoUsuarioAppService;
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.Constantes;
import util.JPAUtil;

/**
 * Classe para testes dos metodos necessÃ¡rios ao funcionamento da biblioteca XFuzzy
 * @author dayse.arruda
 *
 */
public class TesteCargaUsuario {

	public TipoUsuarioAppService tipoUsuarioService;
	public UsuarioAppService usuarioService;

	@BeforeClass
	public void setupClass(){
		try {
			System.out.println("-----------------------------> Startando a JPA...");
			JPAUtil.JPAstartUp();
			System.out.println("-----------------------------> JPA startada com sucesso!");

			tipoUsuarioService = FabricaDeAppService.getAppService(TipoUsuarioAppService.class);
			usuarioService = FabricaDeAppService.getAppService(UsuarioAppService.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		setConstantes();
	}
	
	@AfterClass
	public void tearDown(){
		try {
			System.out.println("-----------------------------> desligando a JPA...");
			JPAUtil.closeEntityManager();
			System.out.println("-----------------------------> JPA desligada com sucesso!");

			tipoUsuarioService = FabricaDeAppService.getAppService(TipoUsuarioAppService.class);
			usuarioService = FabricaDeAppService.getAppService(UsuarioAppService.class);

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

	@Test
	public void testeCargaBasicaDeUsuarios() throws AplicacaoException {
		CargaUsuario cargaUsuario = new CargaUsuario();
		cargaUsuario.executar();
		List<Usuario> listaDeUsuarios = usuarioService.recuperaListaDeUsuarios();
		
		int num_usuarios = 5;
		AssertJUnit.assertEquals(num_usuarios, listaDeUsuarios.size());;
		
		//não precisa fazer assert aqui por que se não funcionar esse metodo ele dará uma exception
		//e vai acusar no teste
		usuarioService.recuperaPorLoginESenha("admin", "123");
		usuarioService.recuperaPorLoginESenha("aluno", "123");
		usuarioService.recuperaPorLoginESenha("clinico", "123");
		usuarioService.recuperaPorLoginESenha("engenheiro", "123");
		usuarioService.recuperaPorLoginESenha("tecnico", "123");
		
	}
	
	
}
