package DAO;

import java.util.List;

import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;
import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaListaPaginada;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

/**
 * 
 * Interface criada no padr�o DAO para intermediar os pedidos de requisi��o de
 * banco de dados ao modelo AvalIndicacaoEspec.
 * 
 * @author bruno.oliveira
 * 
 */
public interface AvalIndicacaoEspecDAO extends
		DaoGenerico<AvalIndicacaoEspec, Long> {

	/**
	 * 
	 * Consulta que recupera uma lista paginada de avalia��o de indica��es
	 * realizadas por um especialista utilizando um objeto do tipo especialista
	 * como par�metro.
	 * 
	 * @param especialista
	 *            - Par�metro que ser� utilizado para se atingir o atendimento
	 *            que se deseja recuperar. Neste caso o par�metro � o objeto que
	 *            representa um especialista cadastrado.
	 * @return list de avalIndicacaoEspec - Retorna uma lista paginada de
	 *         avalia��es de indica��es do especialista passado por par�metro.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna uma exception caso nenhum registro for encontrado
	 *             com o par�metro passado.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina = 10)
	public List<AvalIndicacaoEspec> recuperaListaDeAvaliacaoEspecPaginada(
			Especialista especialista);

	/**
	 * 
	 * Consulta que recupera uma lista n�o paginada de avalia��es de indica��es
	 * de um especialista espec�fico utilizando um objeto do tipo especialista
	 * como identificador.
	 * 
	 * @param especialista
	 *            - Objeto do tipo Especialista que representa um registro de
	 *            especialista no banco, caso exista.
	 * @return list de avalIndicacaoEspec - Retorna uma lista n�o paginada de
	 *         avalia��es de indica��es do especialista passado por par�metro.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaLista
	public List<AvalIndicacaoEspec> recuperaListaDeAvaliacaoEspecComIndicacaoDeUmEspec(
			Especialista especialista);

	/**
	 * 
	 * Consulta que recupera uma lista n�o paginada de todas as avalia��es junto
	 * com os registros de indica��es e especialistas relacionadas registrados
	 * no banco.
	 * 
	 * @return list de avalIndicacaoEspec - Retorna uma lista n�o paginada de
	 *         avalia��es de indica��es do especialista passado por par�metro.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaLista
	public List<AvalIndicacaoEspec> recuperaListaDeAvaliacaoEspecComIndicacaoComEspec();

	/**
	 * 
	 * Consulta que recupera uma lista n�o paginada de avalia��es de
	 * especialistas para uma indica��o espec�fica.
	 * 
	 * @param indicacao
	 *            - Objeto do tipo Indica��o que representa um registro de
	 *            indica��o no banco, caso exista.
	 * @return list de avalIndicacaoEspec - Retorna uma lista n�o paginada de
	 *         avalia��es para a indica��o passada por par�metro, caso existam.
	 *         ao paciente, caso existam.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaLista
	public List<AvalIndicacaoEspec> recuperaListaDeAvaliacaoEspecPorIndicacao(
			Indicacao indicacao);

	/**
	 * 
	 * Consulta que recupera uma avalia��o atrav�s do c�digo identificador (ID).
	 * 
	 * @param indicacao
	 *            - Objeto do tipo Indica��o que representa um registro de
	 *            indica��o no banco, caso exista.
	 * @param especialista
	 *            - Objeto do tipo Especialista que representa um registro de
	 *            especialista no banco, caso exista.
	 * @return avalia��o de indica��o do especialista - Retorna um objeto do
	 *         tipo avalIndicacaoEspec com os dados preenchidos conforme as
	 *         informa��es encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o par�metro passado.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaObjeto
	public AvalIndicacaoEspec recuperaAvalIndicacaoEspecPorID(Long ID)
			throws ObjetoNaoEncontradoException;

	/**
	 * 
	 * Consulta que recupera a m�dia de peso de todos os especialistas
	 * cadastrados no banco.
	 * 
	 * @return double - Retorna um valor double que representa a m�dia do peso
	 *         dos especialistas cadastrados.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaObjeto
	public Double recuperaMediaDoPesoAvaliadorDosEspecialistas();

}
