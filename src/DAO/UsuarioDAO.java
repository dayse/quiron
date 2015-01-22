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
 * Interface criada no padr�o DAO para intermediar os pedidos de requisi��o de
 * banco de dados ao modelo Usu�rio.
 * 
 * @author bruno.oliveira (Atualiza��o)
 * 
 */
public interface UsuarioDAO extends DaoGenerico<Usuario, Long>
{

	/**
	 * 
	 * Faz uma busca por um usu�rio atrav�s do seu login e senha,
	 * caso algum dos dados n�o seja localizado retorna uma exception
	 * 
	 * @param login do usu�rio buscado
	 * @param senha do usu�rio buscado
	 * @return objeto que representa o usu�rio
	 * @throws ObjetoNaoEncontradoException caso n�o encontre nenhuma das informa��es
	 * passadas por par�metro.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaObjeto
	public Usuario recuperaPorLoginESenha(String login, String senha) throws ObjetoNaoEncontradoException;

	/**
	 * 
	 * Faz uma busca por um usu�rio atrav�s apenas do login do mesmo
	 * 
	 * @param login do usu�rio buscado
	 * @return objeto que representa o usu�rio encontrado
	 * @throws ObjetoNaoEncontradoException caso n�o encontre um usu�rio que bata com o login passado.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaObjeto
	public Usuario recuperaPorLogin(String login) throws ObjetoNaoEncontradoException;

	/**
	 * 
	 * Recupera uma lista nao paginada de usu�rios
	 * 
	 * @return lista atualzida, mas nao paginada, de usu�rios
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaLista
	public List<Usuario> recuperaListaDeUsuarios();
	
	/**
	 * 
	 * Recupera uma lista nao paginadade usu�rios e junto
	 * tamb�m recupera os TiposUsuario de cada usu�rio da lista
	 * 
	 * @return lista nao paginada de usuarios e seus respectivos tiposUsuarios
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaLista
	public List<Usuario> recuperaListaDeUsuariosComTipo();

	/**
	 * 
	 * Recupera uma lista paginada de usuarios atrav�s de uma busca por nome.
	 * 
	 * @param nome que deseja-se encontrar
	 * @return lista paginada de todos os usu�rios que possuam nome igual ao
	 * termo passado como par�metro.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaListaPaginada
	public List<Usuario> recuperaListaPaginadaPorNome(String nome);

	/**
	 * 
	 * Recupera uma lista nao paginada de usuarios atrav�s de uma busca
	 * por um tipode usu�rio espec�fico.
	 * 
	 * @param tipoUsuario que representa o n�vel de acesso ao sistema cujo
	 * usu�rios deseja-se encontrar.
	 * @return lista n�o paginada de usu�rios que possuem o n�vel de acesso buscado.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaLista
	public List<Usuario> recuperaListaDeUsuarioPorTipo(TipoUsuario tipoUsuario);
	
	/**
	 * 
	 * Recupera uma lista de usu�rios atrav�s de uma busca por nome.
	 * 
	 * @param nome que deseja-se encontrar
	 * @return lista n�o paginada de todos os usu�rios que possuam ao menos
	 * uma parte do nome igual ao termo passado como par�metro.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaLista
	public List<Usuario> recuperaUsuarioPorNomeLike(String nome);
	
	/**
	 * 
	 * Recupera uma lista de usu�rios atrav�s de uma busca por login.
	 * 
	 * @param login que deseja-se encontrar
	 * @return lista n�o paginada de todos os usu�rios cujo login sejam iguais
	 * ou ao menos possuam uma parte igual ao termo passado como par�metro.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaLista
	public List<Usuario> recuperaUsuarioPorLoginLike(String login);

}
