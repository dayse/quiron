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
 * Interface criada no padrão DAO para intermediar os pedidos de requisição de
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
	 * @return lista de especialista atualizada e não paginada
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
	 * Consulta que retorna um especialista a partir de um código
	 * 
	 * @param codEspecialista do especialista que deseja-se buscar
	 * @return especialista que corresponde ao código buscado
	 * @throws ObjetoNaoEncontradoException caso o código não represente nenhum especialista no banco.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaObjeto
	public Especialista recuperaEspecialistaPorCodigo(String codEspecialista) throws ObjetoNaoEncontradoException;
	
	/**
	 * 
	 * Consulta que retorna a média dos pesos de avaliação dos especialistas cadastrados.
	 * 
	 * @return média dos pesos dos especialistas
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
	 * @return lista não paginada de especialistas que tem semelhança com a string buscada.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaLista
	public List<Especialista> recuperaEspecialistaPorNomeLike(String nome);
	
	/**
	 * 
	 * Consulta que retorna uma lista de especialistas que tenham o código semelhante
	 * ao termo buscado.
	 * 
	 * @param codigo do especialista que deseja-se encontrar
	 * @return lista não paginada de especialistas que tem semelhança com a string buscada.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaLista
	public List<Especialista> recuperaEspecialistaPorCodigoLike(String codigo);
	
}
