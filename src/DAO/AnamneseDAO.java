package DAO;

import modelo.Anamnese;
import modelo.Atendimento;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

/**
 * 
 * Interface criada no padrão DAO para intermediar os pedidos de requisição de
 * banco de dados ao modelo Anamnese.
 * 
 * @author bruno.oliveira
 * 
 */
public interface AnamneseDAO extends DaoGenerico<Anamnese, Long> {

	/**
	 * 
	 * Consulta que recupera uma anmnese utilizando um atendimento como
	 * parâmetro.
	 * 
	 * @param atendimento
	 *            - Para consultar uma anmnese é necessário de qual atendimento
	 *            ela pertence, já que ambas entidades relacionam-se.
	 * @return anamnese - Retorna um objeto do tipo anmnese com os dados
	 *         preenchidos conforme as informações encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o parâmetro passado.
	 *             
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaObjeto
	public Anamnese recuperaAnamnesePorAtendimento(Atendimento atendimento)
			throws ObjetoNaoEncontradoException;
}
