package DAO;

import modelo.Anamnese;
import modelo.Atendimento;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

/**
 * 
 * Interface criada no padr�o DAO para intermediar os pedidos de requisi��o de
 * banco de dados ao modelo Anamnese.
 * 
 * @author bruno.oliveira
 * 
 */
public interface AnamneseDAO extends DaoGenerico<Anamnese, Long> {

	/**
	 * 
	 * Consulta que recupera uma anmnese utilizando um atendimento como
	 * par�metro.
	 * 
	 * @param atendimento
	 *            - Para consultar uma anmnese � necess�rio de qual atendimento
	 *            ela pertence, j� que ambas entidades relacionam-se.
	 * @return anamnese - Retorna um objeto do tipo anmnese com os dados
	 *         preenchidos conforme as informa��es encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o par�metro passado.
	 *             
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaObjeto
	public Anamnese recuperaAnamnesePorAtendimento(Atendimento atendimento)
			throws ObjetoNaoEncontradoException;
}
