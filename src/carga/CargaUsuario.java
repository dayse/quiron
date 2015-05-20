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
 * � uma Carga do sistema, portanto deve herdar de CargaBase e
 * implementar o m�todo executar().
 * Nesse m�todo "executar" � que � chamado pelos outros m�todos que s�o 
 * as etapas dessa carga.
 * Portanto se � necessario rodar um m�todo depois do outro, eles devem ser chamados
 * na ordem correta. Ex: incluiPaciente() vem antes de incluiAtendimento(), portanto no m�todo executar()
 * 
 * Terminado de executar todas as etapas � preciso retornar true.
 * Se houver algum problema(exce��o) na execu��o de uma das etapas, essa exce��o deve ser lancada.
 * 
 * Essa Carga:
 * Classe respons�vel pela inclus�o de Tipos de Usu�rio e de Usu�rio.
 * � usada na carga do sistema e deve ser a primeira a ser executada.
 * Est� criando um usu�rio para cada tipo.
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
	 * Construdor da carga, respons�vel por instanciar os services.
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
	 * M�todo herdado de CargaBase e que retona uma lista de cargas que esta
	 * carga depende para ser executada de maneira completa.
	 * 
	 * @return lista de dependencias.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */	
	@Override
	public List<CargaBase> getCargasDependentes(){
		List<CargaBase> dependencias = new ArrayList<CargaBase>();
		return dependencias;
	}
	
	/**
	 * 
	 * M�todo herdado de CargaBase e utilizado para definir as etapas
	 * de execu��o desta carga.
	 * 
	 * @return Boolean - True se n�o ocorrer nenhum problema (exce��o).
	 * @throws AplicacaoException
	 *             Retorna uma AplicacaoException caso ocorra uma exce��o deste
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
	 * M�todo respons�vel por preparar e inserir os valores padr�es dos
	 * usu�rios no banco.Inclui tipo usu�rio e usu�rio
	 * 
	 * @throws AplicacaoException
	 *             Retorna uma AplicacaoException caso ocorra uma exce��o deste
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
			tipoUsuarioAdmin.setDescricao("O usu�rio ADMINISTRADOR pode realizar qualquer opera��o no Sistema.");
			
			tipoUsuarioAluno.setTipoUsuario(TipoUsuario.ALUNO);
			tipoUsuarioAluno.setDescricao("O usu�rio ALUNO pode realizar apenas consultas e impress�o de relat�rios nas telas " +
					                        "relativas ao Horizonte de Planejamento (HP,Periodo PMP, Periodo PAP) e n�o acessa " +
					                        "Administra��o e Eng. Conhecimento");
			
			tipoUsuarioClinico.setTipoUsuario(TipoUsuario.CLINICO);
			tipoUsuarioClinico.setDescricao("O usu�rio Cl�nico pode realizar qualquer opera��o no Sistema, por�m n�o possui acesso" +
					"as �reas de Administra��o e Engenharia de Conhecimento.");
			
			tipoUsuarioTecnico.setTipoUsuario(TipoUsuario.TECNICO);
			tipoUsuarioTecnico.setDescricao("O us�rio T�cnico pode realizar qualquer opera��o no Sistema, por�m n�o possui acesso" + 
					"as �reas de Administra��o e Engenharia de Conhecimento.");
			
			tipoUsuarioEngenheiro.setTipoUsuario(TipoUsuario.ENGENHEIRO_DE_CONHECIMENTO);
			tipoUsuarioEngenheiro.setDescricao("O usu�rio ENGENHEIRO pode realizar a parte de Logica Fuzzy (Engenharia de Conhecimento)" +
					"no Sistema. Por�m, n�o possui acesso a �rea Administrativa.");
			
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
