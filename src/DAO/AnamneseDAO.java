package DAO;

import java.util.List;

import modelo.Anamnese;
import modelo.Atendimento;
import modelo.Parametro;
import DAO.anotacao.RecuperaLista;
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
	 * Consulta que recupera uma lista de anmneses utilizando um atendimento como
	 * par�metro.
	 * 
	 * @param atendimento
	 *            - Para consultar uma lista de anmneses � necess�rio de qual atendimento
	 *            ela pertence, j� que ambas entidades relacionam-se.
	 *             
	 * @author bruno.oliveira, felipe.pontes
	 * 
	 */
	@RecuperaLista
	public List<Anamnese>  recuperaListaDeAnamnesePorAtendimento(Atendimento atendimento);

	/**
	 * 
	 * Consulta que recupera uma lista de anmneses utilizando um parametro.
	 * 
	 * @param parametro
	 *            - Para consultar uma lista de anmneses � necess�rio de qual parametro
	 *            ela pertence, j� que ambas entidades relacionam-se.
	 *             
	 * @author felipe.pontes
	 * 
	 */
	@RecuperaLista
	public List<Anamnese>  recuperaListaDeAnamnesePorParametro(Parametro parametro);
	
	/**
	 * 
	 * Consulta que recupera uma anamnese a partir de um atendimento e um parametro.
	 * 
	 * @param atendimento ao qual essa anamnese pertence
	 * @param parametro ao qual essa anamnese pertence
	 * @return anamnese desse atendimento X parametro
	 * @throws ObjetoNaoEncontradoException - caso a anamnese n�o exista
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaObjeto
	public Anamnese  recuperaAnamnesePorAtendimentoPorParametro(Atendimento atendimento, Parametro parametro)
			throws ObjetoNaoEncontradoException;

}
