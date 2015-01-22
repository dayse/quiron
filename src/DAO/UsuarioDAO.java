package DAO;

import java.util.List;

import modelo.TipoUsuario;
import modelo.Usuario;

import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaListaPaginada;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

/**
 * 
 * Interface criada no padrão DAO para intermediar os pedidos de requisição de
 * banco de dados ao modelo Usuário.
 * 
 * @author bruno.oliveira (Atualização)
 * 
 */
public interface UsuarioDAO extends DaoGenerico<Usuario, Long>
{

	/**
	 * 
	 * Faz uma busca por um usuário através do seu login e senha,
	 * caso algum dos dados não seja localizado retorna uma exception
	 * 
	 * @param login do usuário buscado
	 * @param senha do usuário buscado
	 * @return objeto que representa o usuário
	 * @throws ObjetoNaoEncontradoException caso não encontre nenhuma das informações
	 * passadas por parâmetro.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaObjeto
	public Usuario recuperaPorLoginESenha(String login, String senha) throws ObjetoNaoEncontradoException;

	/**
	 * 
	 * Faz uma busca por um usuário através apenas do login do mesmo
	 * 
	 * @param login do usuário buscado
	 * @return objeto que representa o usuário encontrado
	 * @throws ObjetoNaoEncontradoException caso não encontre um usuário que bata com o login passado.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaObjeto
	public Usuario recuperaPorLogin(String login) throws ObjetoNaoEncontradoException;

	/**
	 * 
	 * Recupera uma lista nao paginada de usuários
	 * 
	 * @return lista atualzida, mas nao paginada, de usuários
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaLista
	public List<Usuario> recuperaListaDeUsuarios();
	
	/**
	 * 
	 * Recupera uma lista nao paginadade usuários e junto
	 * também recupera os TiposUsuario de cada usuário da lista
	 * 
	 * @return lista nao paginada de usuarios e seus respectivos tiposUsuarios
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaLista
	public List<Usuario> recuperaListaDeUsuariosComTipo();

	/**
	 * 
	 * Recupera uma lista paginada de usuarios através de uma busca por nome.
	 * 
	 * @param nome que deseja-se encontrar
	 * @return lista paginada de todos os usuários que possuam nome igual ao
	 * termo passado como parâmetro.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaListaPaginada
	public List<Usuario> recuperaListaPaginadaPorNome(String nome);

	/**
	 * 
	 * Recupera uma lista nao paginada de usuarios através de uma busca
	 * por um tipode usuário específico.
	 * 
	 * @param tipoUsuario que representa o nível de acesso ao sistema cujo
	 * usuários deseja-se encontrar.
	 * @return lista não paginada de usuários que possuem o nível de acesso buscado.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaLista
	public List<Usuario> recuperaListaDeUsuarioPorTipo(TipoUsuario tipoUsuario);
	
	/**
	 * 
	 * Recupera uma lista de usuários através de uma busca por nome.
	 * 
	 * @param nome que deseja-se encontrar
	 * @return lista não paginada de todos os usuários que possuam ao menos
	 * uma parte do nome igual ao termo passado como parâmetro.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaLista
	public List<Usuario> recuperaUsuarioPorNomeLike(String nome);
	
	/**
	 * 
	 * Recupera uma lista de usuários através de uma busca por login.
	 * 
	 * @param login que deseja-se encontrar
	 * @return lista não paginada de todos os usuários cujo login sejam iguais
	 * ou ao menos possuam uma parte igual ao termo passado como parâmetro.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaLista
	public List<Usuario> recuperaUsuarioPorLoginLike(String login);

}
