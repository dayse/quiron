

package DAO;

import java.util.List;

import modelo.Indicacao;
import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaListaPaginada;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

/**
 * 
 * Interface criada no padr�o DAO para intermediar os pedidos de requisi��o de
 * banco de dados ao modelo Indica��o.
 * 
 * @author bruno.oliveira
 * 
 */
public interface IndicacaoDAO extends DaoGenerico<Indicacao, Long> {
	
	/**
	 * 
	 * Consulta que recupera a lista paginada de todos os atendimentos
	 * registrados no banco e traz junto os pacientes.
	 * 
	 * @return List de atendimento - Retorna uma lista paginada de atendimentos com pacientes.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina=10)
	public List<Indicacao> recuperaListaDeIndicacoesPaginada();
	
	/**
	 * 
	 * Consulta que recupera uma indica��o utilizando um c�digo identificador
	 * de indica��o como par�metro.
	 * 
	 * @param codIndicacao
	 *            - Par�metro que ser� utilizado para se atingir o atendimento
	 *            que se deseja recuperar.
	 * @return indicacao - Retorna um objeto do tipo indicacao com os dados
	 *         preenchidos conforme as informa��es encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o par�metro passado.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaObjeto
	public Indicacao recuperaIndicacaoPorCodigo(String codIndicacao) throws ObjetoNaoEncontradoException;
	
	/**
	 * 
	 * Consulta que retorna lista de indica��es que batem ou tem em si semelhan�a
	 * com c�digo da indica��o passado por par�metro.
	 * 
	 * @param codIndicacao - c�digo de indica��o que deseja-se encontrar.
	 * @return lista n�o-pagina com os resultados da busca por c�digo.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */	
	@RecuperaLista
	public List<Indicacao> recuperaIndicacaoPorCodigoLike(String codIndicacao);
	
	/**
	 * 
	 * Consulta que retorna lista de indica��es que batem
	 * com o nome de indica��o buscado.
	 * 
	 * @param nomeIndicacao - nome de indica��o que deseja-se encontrar.
	 * @return lista n�o-pagina com os resultados da busca por nome.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */	
	@RecuperaLista
	public List<Indicacao> recuperaIndicacaoPorNome(String nomeIndicacao);

	/**
	 * 
	 * Consulta que retorna lista de indica��es n�o-paginada.
	 * 
	 * @return lista n�o-pagina de indica��es.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */	
	@RecuperaLista
	public List<Indicacao> recuperaListaIndicacao();
}
