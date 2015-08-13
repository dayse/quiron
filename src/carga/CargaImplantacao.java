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
 * na ordem correta. Ex: incluiPaciente() vem antes de incluiAtendimento(), portanto no método executar()
 * 
 * Terminado de executar todas as etapas é preciso retornar true.
 * Se houver algum problema(exceção) na execução de uma das etapas, essa exceção deve ser lancada.
 * 
 * Essa Carga:
 * Classe responsável pela inclusão de Tipos de Usuário e de um Usuário do tipo Administrador.
 * É usada na carga do sistema e deve ser a primeira a ser executada. Será usada na implantação
 * do sistema dentro de uma empresa
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

	/**
	 * 
	 * Método herdado de CargaBase e que retona uma lista de cargas que esta
	 * carga depende para ser executada de maneira completa.
	 * 
	 * @return lista de dependencias.
	 * 
	 * @author bruno.oliveira
	 * 
	 */	
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

	/**
	 * 
	 * Método que acessa o nosso arquivo de configuração de usuários padrões e recupera-os
	 * 
	 * @return Lista de usuários recuperados do arquivo JSON
	 * 
	 * @author bruno.oliveira 
	 * 
	 */
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
	 * Método responsável por preparar e inserir os valores padrões do
	 * usuário administrador no banco.
	 * 
	 * @throws AplicacaoException
	 *             Retorna uma AplicacaoException caso ocorra uma exceção deste
	 *             tipo.
	 *             
	 * @author bruno.oliveira
	 * 
	 */
	public void incluirTiposDeUsuario() throws AplicacaoException {
		TipoUsuario tipoUsuarioAdmin = new TipoUsuario();
		TipoUsuario tipoUsuarioAluno = new TipoUsuario();
		TipoUsuario tipoUsuarioClinico = new TipoUsuario();
		TipoUsuario tipoUsuarioTecnico = new TipoUsuario();
		TipoUsuario tipoUsuarioEspecialista = new TipoUsuario();
		TipoUsuario tipoUsuarioEngenheiro = new TipoUsuario();
		
		tipoUsuarioAdmin.setTipoUsuario(TipoUsuario.ADMINISTRADOR);
		tipoUsuarioAdmin.setDescricao("O usuário ADMINISTRADOR pode realizar qualquer operação no Sistema.");
		
		tipoUsuarioAluno.setTipoUsuario(TipoUsuario.ALUNO);
		tipoUsuarioAluno.setDescricao("O usuário ALUNO pode realizar as mesmas funções que os clínicos");
		
		tipoUsuarioClinico.setTipoUsuario(TipoUsuario.CLINICO);
		tipoUsuarioClinico.setDescricao("O usuário CLINICO não possui acesso aos módulos" +
				"de Administração, Configuração e Especialistas.");
		
		tipoUsuarioTecnico.setTipoUsuario(TipoUsuario.TECNICO);
		tipoUsuarioTecnico.setDescricao("O usuário TECNICO apenas pode fazer um pré atendimento ou cadastro de pacientes");
		
		tipoUsuarioEspecialista.setTipoUsuario(TipoUsuario.ESPECIALISTA);
		tipoUsuarioEspecialista.setDescricao("O usuário ESPECIALISTA possui acesso somente ao cadastro de especialistas");	
		
		tipoUsuarioEngenheiro.setTipoUsuario(TipoUsuario.ENGENHEIRO_DE_CONHECIMENTO);
		tipoUsuarioEngenheiro.setDescricao("O usuário ENGENHEIRO DE CONHECIMENTO não possui acesso a área Administrativa.");
		
		tipoUsuarioService.inclui(tipoUsuarioAdmin);
		tipoUsuarioService.inclui(tipoUsuarioAluno);
		tipoUsuarioService.inclui(tipoUsuarioClinico);
		tipoUsuarioService.inclui(tipoUsuarioTecnico);
		tipoUsuarioService.inclui(tipoUsuarioEspecialista);
		tipoUsuarioService.inclui(tipoUsuarioEngenheiro);
	
			List<Usuario> usuarios = recuperaUsuariosDeArquivoConfigJson();
			
			Usuario usuarioAdmin = usuarios.get(0);
			usuarioAdmin.setTipoUsuario(tipoUsuarioAdmin);
			
			usuarioService.inclui(usuarioAdmin, usuarioAdmin.getSenha());

	}

}
