package DAO;

import java.util.List;

import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaListaPaginada;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;
import modelo.Parametro;

/**
 * 
 * Interface criada no padr�o DAO para intermediar os pedidos de requisi��o de
 * banco de dados ao modelo Parametro.
 * 
 * @author bruno.oliveira (Atualiza��o)
 * 
 */
public interface ParametroDAO extends DaoGenerico<Parametro, Long> {

	/**
	 * 
	 * Consulta que retorna a lista de parametros atualizada.
	 * 
	 * @return lista atualizada de parametros (n�o paginada)
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaLista
	public List<Parametro> recuperaListaDeParametros();
	
	/**
	 * 
	 * Consulta que recupera a lista paginada de todos os parametros
	 * registrados no banco.
	 * 
	 * @return List de parametros - Retorna uma lista paginada de parametros.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina=10)
	public List<Parametro> recuperaListaDeParametrosPaginada();
	
	/**
	 * Recupera um parametros espec�fico atrav�s de uma busca por um c�digo.
	 * 
	 * @param codParametro - Par�metro que ser� utilizado para se atingir o parametro
	 *            que se deseja recuperar.
	 * @return parametro - Retorna um objeto do tipo parametro com os dados
	 *         preenchidos conforme as informa��es encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o par�metro passado.
	 *             
	 * @author bruno.oliveira (Atualiza��o)
	 *            
	 */
	@RecuperaObjeto
	public Parametro recuperaParametroPorCodigo(String codParametro) throws ObjetoNaoEncontradoException;
	
	/**
	 * Recupera um parametros espec�fico atrav�s de uma busca por um nome.
	 * 
	 * @param nome - Par�metro que ser� utilizado para se atingir o parametro
	 *            que se deseja recuperar.
	 * @return parametro - Retorna um objeto do tipo parametro com os dados
	 *         preenchidos conforme as informa��es encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o par�metro passado.
	 *             
	 * @author bruno.oliveira (Atualiza��o)
	 *            
	 */
	@RecuperaObjeto
	public Parametro recuperaParametroPorNome(String nome) throws ObjetoNaoEncontradoException;
	
	/**
	 * 
	 * Consulta que retorna lista de parametros que batem ou tem semelhan�a
	 * com o c�digo do parametro buscado.
	 * 
	 * @param codParametro - c�digo do parametro o qual deseja-se procurar
	 * @return lista n�o-pagina de parametros
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaLista
	public List<Parametro> recuperaParametroPorCodigoLike(String codParametro);
	
	/**
	 * 
	 * Consulta que retorna lista de parametros que batem ou tem semelhan�a
	 * com o nome do parametro buscado.
	 * 
	 * @param nomeParametro - nome do parametro o qual deseja-se procurar
	 * @return lista n�o-pagina de parametros
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaLista
	public List<Parametro> recuperaParametroPorNomeLike(String nomeParametro);
}
