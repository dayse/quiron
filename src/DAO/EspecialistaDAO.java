package DAO;

import java.util.List;

import modelo.Especialista;
import modelo.Usuario;
import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaListaPaginada;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

/**
 * 
 * Interface criada no padr�o DAO para intermediar os pedidos de requisi��o de
 * banco de dados ao modelo Especialista.
 * 
 * @author bruno.oliveira
 * 
 */
public interface EspecialistaDAO extends DaoGenerico<Especialista, Long> {
	
	/**
	 * 
	 * Consulta que retorna uma lista de especialistas
	 * 
	 * @return lista de especialista atualizada e n�o paginada
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaLista
	public List<Especialista> recuperaListaEspecialista();

	/**
	 * 
	 * Consulta que retorna uma lista de especialistas paginada
	 * 
	 * @return lista de especialista atualizada e paginada
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina=10)
	public List<Especialista> recuperaListaDeEspecialistasPaginada();
	
	/**
	 * 
	 * Consulta que retorna um especialista a partir de um c�digo
	 * 
	 * @param codEspecialista do especialista que deseja-se buscar
	 * @return especialista que corresponde ao c�digo buscado
	 * @throws ObjetoNaoEncontradoException caso o c�digo n�o represente nenhum especialista no banco.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaObjeto
	public Especialista recuperaEspecialistaPorCodigo(String codEspecialista) throws ObjetoNaoEncontradoException;
	
	/**
	 * 
	 * Consulta que retorna a m�dia dos pesos de avalia��o dos especialistas cadastrados.
	 * 
	 * @return m�dia dos pesos dos especialistas
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaObjeto
	public Double recuperaMediaDoPesoAvaliadorDosEspecialistas();
	
	/**
	 * 
	 * Consulta que retorna uma lista de especialistas que tenham nome semelhantes
	 * ao termo buscado.
	 * 
	 * @param nome do especialista que deseja-se encontrar
	 * @return lista n�o paginada de especialistas que tem semelhan�a com a string buscada.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaLista
	public List<Especialista> recuperaEspecialistaPorNomeLike(String nome);
	
	/**
	 * 
	 * Consulta que retorna uma lista de especialistas que tenham o c�digo semelhante
	 * ao termo buscado.
	 * 
	 * @param codigo do especialista que deseja-se encontrar
	 * @return lista n�o paginada de especialistas que tem semelhan�a com a string buscada.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaLista
	public List<Especialista> recuperaEspecialistaPorCodigoLike(String codigo);
	
}
