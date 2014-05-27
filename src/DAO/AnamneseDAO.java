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
 * Interface criada no padrão DAO para intermediar os pedidos de requisição de
 * banco de dados ao modelo Anamnese.
 * 
 * @author bruno.oliveira
 * 
 */
public interface AnamneseDAO extends DaoGenerico<Anamnese, Long> {

	/**
	 * 
	 * Consulta que recupera uma lista de anmneses utilizando um atendimento como
	 * parâmetro.
	 * 
	 * @param atendimento
	 *            - Para consultar uma lista de anmneses é necessário de qual atendimento
	 *            ela pertence, já que ambas entidades relacionam-se.
	 *             
	 * @author bruno.oliveira, felipe.pontes
	 * 
	 */
	@RecuperaLista
	public List<Anamnese>  recuperaListaDeAnamnesePorAtendimento(Atendimento atendimento);
	
	@RecuperaObjeto
	public Anamnese  recuperaAnamnesePorAtendimentoPorParametro(Atendimento atendimento, Parametro parametro)
			throws ObjetoNaoEncontradoException;
}
