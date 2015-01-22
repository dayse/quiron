package DAO;

import java.util.List;

import modelo.TipoUsuario;

import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

/**
 * 
 * Interface criada no padrão DAO para intermediar os pedidos de requisição de
 * banco de dados ao modelo TipoUsuario.
 * 
 * @author bruno.oliveira (Atualização)
 * 
 */
public interface TipoUsuarioDAO extends DaoGenerico<TipoUsuario, Long>
{

	/**
	 * 
	 * Retorna um objeto do tipo TipoUsuario que representa
	 * um dos níveis de usuário no sistema. O nome do tipo de usuário
	 * buscado é passado como parâmetro.
	 * 
	 * 
	 * @param tipoUsuario - String - nome do tipo de usuário buscado
	 * @return Tipo do usuario buscado, se for encontrado
	 * @throws ObjetoNaoEncontradoException - Caso não encontre o tipo procurado retorna essa exception.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaObjeto
	public TipoUsuario recuperaTipoUsuarioPorTipo(String tipoUsuario) throws ObjetoNaoEncontradoException;
	
	/**
	 * 
	 * Retorna uma lista não paginada de tipos de
	 * usuários existentes no sistema.
	 * 
	 * @return lista não paginada com todo os tipos de usuarios do sistema.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaLista
	public List<TipoUsuario> recuperaListaDeTipoUsuario();
}
