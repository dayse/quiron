package carga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modelo.TipoUsuario;
import modelo.Usuario;
import service.TipoUsuarioAppService;
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.Constantes;
import util.JsonConfigLoader;

import com.google.gson.Gson;

/**
 * 
 * Sobre a Carga:
 * É uma Carga do sistema, portanto deve herdar de CargaBase e
 * implementar o método executar().
 * Nesse método "executar" é que é chamado pelos outros métodos que são 
 * as etapas dessa carga.
 * Portanto se é necessario rodar um método depois do outro, eles devem ser chamados
 * na ordem correta. Ex:
 * incluiHP() vem antes de inicializaHP(), portanto no método executar() eles devem ser chamados nessa ordem.
 * 
 * Terminado de executar todas as etapas é preciso retornar true.
 * Se houver algum problema(exceção) na execução de uma das etapas, essa exceção deve ser lancada.
 * 
 * Essa Carga:
 * Classe responsável pela inclusão de Tipos de Usuário e de Usuário.
 * É usada na carga do sistema e deve ser a primeira a ser executada.
 * Está criando um usuário para cada tipo.
 * 
 * @author bruno.oliveira
 *
 */
public class CargaImplantacao extends CargaBase{
  
	// Services
	public TipoUsuarioAppService tipoUsuarioService;
	public UsuarioAppService usuarioService;
	
	/**
	 * 
	 * Construdor da carga, responsável por instanciar os services.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public CargaImplantacao(){
		try {
			tipoUsuarioService = FabricaDeAppService.getAppService(TipoUsuarioAppService.class);
			usuarioService = FabricaDeAppService.getAppService(UsuarioAppService.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<CargaBase> getCargasDependentes(){
		List<CargaBase> dependencias = new ArrayList<CargaBase>();
		return dependencias;
	}
	
	/**
	 * 
	 * Método herdado de CargaBase e utilizado para definir as etapas
	 * de execução desta carga.
	 * 
	 * @return Boolean - True se não ocorrer nenhum problema (exceção).
	 * @throws AplicacaoException
	 *             Retorna uma AplicacaoException caso ocorra uma exceção deste
	 *             tipo.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@Override
	public boolean executar() throws AplicacaoException {
		this.incluirTiposDeUsuario();
		return true;
	}

	public List<Usuario> recuperaUsuariosDeArquivoConfigJson(){

		System.out.println(
				JsonConfigLoader.getJson(Constantes.CAMINHO_ABSOLUTO_ARQUIVO_USUARIOS_CARGA)
				);
		String json = JsonConfigLoader.getJson(Constantes.CAMINHO_ABSOLUTO_ARQUIVO_USUARIOS_CARGA);
		Gson gson = new Gson();
		Usuario[] arrayUsuarios = gson.fromJson(json, Usuario[].class);
		
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>(Arrays.asList(arrayUsuarios));
		return usuarios;
	}
	
	/**
	 * 
	 * Método responsável por preparar e inserir os valores padrões dos
	 * usuários no banco.
	 * 
	 * @throws AplicacaoException
	 *             Retorna uma AplicacaoException caso ocorra uma exceção deste
	 *             tipo.
	 *             
	 * @author felipe.arruda
	 * 
	 */
	public void incluirTiposDeUsuario() throws AplicacaoException {
			TipoUsuario tipoUsuarioAdmin = new TipoUsuario();
		
			tipoUsuarioAdmin.setTipoUsuario(TipoUsuario.ADMINISTRADOR);
			tipoUsuarioAdmin.setDescricao("O usuário ADMINISTRADOR pode realizar qualquer operação no Sistema.");
			
			tipoUsuarioService.inclui(tipoUsuarioAdmin);
	
			List<Usuario> usuarios = recuperaUsuariosDeArquivoConfigJson();
			
			Usuario usuarioAdmin = usuarios.get(0);
			usuarioAdmin.setTipoUsuario(tipoUsuarioAdmin);
			
			usuarioService.inclui(usuarioAdmin, usuarioAdmin.getSenha());

	}

}
