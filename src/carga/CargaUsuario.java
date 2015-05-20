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
 * Classe responsável pela inclusão de Tipos de Usuário e de Usuário.
 * É usada na carga do sistema e deve ser a primeira a ser executada.
 * Está criando um usuário para cada tipo.
 * 
 * @author felipe.arruda
 *
 */
public class CargaUsuario extends CargaBase{
  
	// Services
	public TipoUsuarioAppService tipoUsuarioService;
	public UsuarioAppService usuarioService;
	
	/**
	 * 
	 * Construdor da carga, responsável por instanciar os services.
	 * 
	 * @author felipe.arruda
	 * 
	 */
	public CargaUsuario(){
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
	 * @author bruno.oliveira (Atualização)
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
	 * @author felipe.arruda
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
	 * usuários no banco.Inclui tipo usuário e usuário
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
			TipoUsuario tipoUsuarioAluno = new TipoUsuario();
			TipoUsuario tipoUsuarioClinico = new TipoUsuario();
			TipoUsuario tipoUsuarioTecnico = new TipoUsuario();
			TipoUsuario tipoUsuarioEngenheiro = new TipoUsuario();
			
			tipoUsuarioAdmin.setTipoUsuario(TipoUsuario.ADMINISTRADOR);
			tipoUsuarioAdmin.setDescricao("O usuário ADMINISTRADOR pode realizar qualquer operação no Sistema.");
			
			tipoUsuarioAluno.setTipoUsuario(TipoUsuario.ALUNO);
			tipoUsuarioAluno.setDescricao("O usuário ALUNO pode realizar apenas consultas e impressão de relatórios nas telas " +
					                        "relativas ao Horizonte de Planejamento (HP,Periodo PMP, Periodo PAP) e não acessa " +
					                        "Administração e Eng. Conhecimento");
			
			tipoUsuarioClinico.setTipoUsuario(TipoUsuario.CLINICO);
			tipoUsuarioClinico.setDescricao("O usuário Clínico pode realizar qualquer operação no Sistema, porém não possui acesso" +
					"as áreas de Administração e Engenharia de Conhecimento.");
			
			tipoUsuarioTecnico.setTipoUsuario(TipoUsuario.TECNICO);
			tipoUsuarioTecnico.setDescricao("O usário Técnico pode realizar qualquer operação no Sistema, porém não possui acesso" + 
					"as áreas de Administração e Engenharia de Conhecimento.");
			
			tipoUsuarioEngenheiro.setTipoUsuario(TipoUsuario.ENGENHEIRO_DE_CONHECIMENTO);
			tipoUsuarioEngenheiro.setDescricao("O usuário ENGENHEIRO pode realizar a parte de Logica Fuzzy (Engenharia de Conhecimento)" +
					"no Sistema. Porém, não possui acesso a área Administrativa.");
			
			tipoUsuarioService.inclui(tipoUsuarioAdmin);
			tipoUsuarioService.inclui(tipoUsuarioAluno);
			tipoUsuarioService.inclui(tipoUsuarioClinico);
			tipoUsuarioService.inclui(tipoUsuarioTecnico);
			tipoUsuarioService.inclui(tipoUsuarioEngenheiro);
			
	
			List<Usuario> usuarios = recuperaUsuariosDeArquivoConfigJson();
			
			Usuario usuarioAdmin = usuarios.get(0);
			usuarioAdmin.setTipoUsuario(tipoUsuarioAdmin);
			
			Usuario usuarioAluno = usuarios.get(1);
			usuarioAluno.setTipoUsuario(tipoUsuarioAluno);
			
			Usuario usuarioClinico = usuarios.get(2);
			usuarioClinico.setTipoUsuario(tipoUsuarioClinico);
	
			Usuario usuarioEngenheiro = usuarios.get(3);
			usuarioEngenheiro.setTipoUsuario(tipoUsuarioEngenheiro);
			
	
			Usuario usuarioTecnico = usuarios.get(4);
			usuarioTecnico.setTipoUsuario(tipoUsuarioTecnico);
			
				usuarioService.inclui(usuarioAdmin, usuarioAdmin.getSenha());
				usuarioService.inclui(usuarioEngenheiro, usuarioEngenheiro.getSenha());
				usuarioService.inclui(usuarioClinico, usuarioClinico.getSenha());
				usuarioService.inclui(usuarioTecnico, usuarioTecnico.getSenha());
				usuarioService.inclui(usuarioAluno, usuarioAluno.getSenha());

	}

}
