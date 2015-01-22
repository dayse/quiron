package DAO;

import java.util.List;

import modelo.TipoUsuario;

import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

/**
 * 
 * Interface criada no padr�o DAO para intermediar os pedidos de requisi��o de
 * banco de dados ao modelo TipoUsuario.
 * 
 * @author bruno.oliveira (Atualiza��o)
 * 
 */
public interface TipoUsuarioDAO extends DaoGenerico<TipoUsuario, Long>
{

	/**
	 * 
	 * Retorna um objeto do tipo TipoUsuario que representa
	 * um dos n�veis de usu�rio no sistema. O nome do tipo de usu�rio
	 * buscado � passado como par�metro.
	 * 
	 * 
	 * @param tipoUsuario - String - nome do tipo de usu�rio buscado
	 * @return Tipo do usuario buscado, se for encontrado
	 * @throws ObjetoNaoEncontradoException - Caso n�o encontre o tipo procurado retorna essa exception.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaObjeto
	public TipoUsuario recuperaTipoUsuarioPorTipo(String tipoUsuario) throws ObjetoNaoEncontradoException;
	
	/**
	 * 
	 * Retorna uma lista n�o paginada de tipos de
	 * usu�rios existentes no sistema.
	 * 
	 * @return lista n�o paginada com todo os tipos de usuarios do sistema.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaLista
	public List<TipoUsuario> recuperaListaDeTipoUsuario();
}
