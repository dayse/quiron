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
 * Interface criada no padrão DAO para intermediar os pedidos de requisição de
 * banco de dados ao modelo Parametro.
 * 
 * @author bruno.oliveira (Atualização)
 * 
 */
public interface ParametroDAO extends DaoGenerico<Parametro, Long> {

	/**
	 * 
	 * Consulta que retorna a lista de parametros atualizada.
	 * 
	 * @return lista atualizada de parametros (não paginada)
	 * 
	 * @author bruno.oliveira (Atualização)
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
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina=10)
	public List<Parametro> recuperaListaDeParametrosPaginada();
	
	/**
	 * Recupera um parametros específico através de uma busca por um código.
	 * 
	 * @param codParametro - Parâmetro que será utilizado para se atingir o parametro
	 *            que se deseja recuperar.
	 * @return parametro - Retorna um objeto do tipo parametro com os dados
	 *         preenchidos conforme as informações encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o parâmetro passado.
	 *             
	 * @author bruno.oliveira (Atualização)
	 *            
	 */
	@RecuperaObjeto
	public Parametro recuperaParametroPorCodigo(String codParametro) throws ObjetoNaoEncontradoException;
	
	/**
	 * Recupera um parametros específico através de uma busca por um nome.
	 * 
	 * @param nome - Parâmetro que será utilizado para se atingir o parametro
	 *            que se deseja recuperar.
	 * @return parametro - Retorna um objeto do tipo parametro com os dados
	 *         preenchidos conforme as informações encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o parâmetro passado.
	 *             
	 * @author bruno.oliveira (Atualização)
	 *            
	 */
	@RecuperaObjeto
	public Parametro recuperaParametroPorNome(String nome) throws ObjetoNaoEncontradoException;
	
	/**
	 * 
	 * Consulta que retorna lista de parametros que batem ou tem semelhança
	 * com o código do parametro buscado.
	 * 
	 * @param codParametro - código do parametro o qual deseja-se procurar
	 * @return lista não-pagina de parametros
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaLista
	public List<Parametro> recuperaParametroPorCodigoLike(String codParametro);
	
	/**
	 * 
	 * Consulta que retorna lista de parametros que batem ou tem semelhança
	 * com o nome do parametro buscado.
	 * 
	 * @param nomeParametro - nome do parametro o qual deseja-se procurar
	 * @return lista não-pagina de parametros
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaLista
	public List<Parametro> recuperaParametroPorNomeLike(String nomeParametro);
}
