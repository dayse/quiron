

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
 * Interface criada no padrão DAO para intermediar os pedidos de requisição de
 * banco de dados ao modelo Indicação.
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
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina=10)
	public List<Indicacao> recuperaListaDeIndicacoesPaginada();
	
	/**
	 * 
	 * Consulta que recupera uma indicação utilizando um código identificador
	 * de indicação como parâmetro.
	 * 
	 * @param codIndicacao
	 *            - Parâmetro que será utilizado para se atingir o atendimento
	 *            que se deseja recuperar.
	 * @return indicacao - Retorna um objeto do tipo indicacao com os dados
	 *         preenchidos conforme as informações encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o parâmetro passado.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaObjeto
	public Indicacao recuperaIndicacaoPorCodigo(String codIndicacao) throws ObjetoNaoEncontradoException;
	
	/**
	 * 
	 * Consulta que retorna lista de indicações que batem ou tem em si semelhança
	 * com código da indicação passado por parâmetro.
	 * 
	 * @param codIndicacao - código de indicação que deseja-se encontrar.
	 * @return lista não-pagina com os resultados da busca por código.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */	
	@RecuperaLista
	public List<Indicacao> recuperaIndicacaoPorCodigoLike(String codIndicacao);
	
	/**
	 * 
	 * Consulta que retorna lista de indicações que batem
	 * com o nome de indicação buscado.
	 * 
	 * @param nomeIndicacao - nome de indicação que deseja-se encontrar.
	 * @return lista não-pagina com os resultados da busca por nome.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */	
	@RecuperaLista
	public List<Indicacao> recuperaIndicacaoPorNome(String nomeIndicacao);

	/**
	 * 
	 * Consulta que retorna lista de indicações não-paginada.
	 * 
	 * @return lista não-pagina de indicações.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */	
	@RecuperaLista
	public List<Indicacao> recuperaListaIndicacao();
}
