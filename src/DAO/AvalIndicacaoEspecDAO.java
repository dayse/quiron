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
 * Interface criada no padrão DAO para intermediar os pedidos de requisição de
 * banco de dados ao modelo AvalIndicacaoEspec.
 * 
 * @author bruno.oliveira
 * 
 */
public interface AvalIndicacaoEspecDAO extends
		DaoGenerico<AvalIndicacaoEspec, Long> {

	/**
	 * 
	 * Consulta que recupera uma lista paginada de avaliação de indicações
	 * realizadas por um especialista utilizando um objeto do tipo especialista
	 * como parâmetro.
	 * 
	 * @param especialista
	 *            - Parâmetro que será utilizado para se atingir o atendimento
	 *            que se deseja recuperar. Neste caso o parâmetro é o objeto que
	 *            representa um especialista cadastrado.
	 * @return list de avalIndicacaoEspec - Retorna uma lista paginada de
	 *         avaliações de indicações do especialista passado por parâmetro.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna uma exception caso nenhum registro for encontrado
	 *             com o parâmetro passado.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina = 10)
	public List<AvalIndicacaoEspec> recuperaListaDeAvaliacaoEspecPaginada(
			Especialista especialista);

	/**
	 * 
	 * Consulta que recupera uma lista não paginada de avaliações de indicações
	 * de um especialista específico utilizando um objeto do tipo especialista
	 * como identificador.
	 * 
	 * @param especialista
	 *            - Objeto do tipo Especialista que representa um registro de
	 *            especialista no banco, caso exista.
	 * @return list de avalIndicacaoEspec - Retorna uma lista não paginada de
	 *         avaliações de indicações do especialista passado por parâmetro.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaLista
	public List<AvalIndicacaoEspec> recuperaListaDeAvaliacaoEspecComIndicacaoDeUmEspec(
			Especialista especialista);

	/**
	 * 
	 * Consulta que recupera uma lista não paginada de todas as avaliações junto
	 * com os registros de indicações e especialistas relacionadas registrados
	 * no banco.
	 * 
	 * @return list de avalIndicacaoEspec - Retorna uma lista não paginada de
	 *         avaliações de indicações do especialista passado por parâmetro.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaLista
	public List<AvalIndicacaoEspec> recuperaListaDeAvaliacaoEspecComIndicacaoComEspec();

	/**
	 * 
	 * Consulta que recupera uma lista não paginada de avaliações de
	 * especialistas para uma indicação específica.
	 * 
	 * @param indicacao
	 *            - Objeto do tipo Indicação que representa um registro de
	 *            indicação no banco, caso exista.
	 * @return list de avalIndicacaoEspec - Retorna uma lista não paginada de
	 *         avaliações para a indicação passada por parâmetro, caso existam.
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
	 * Consulta que recupera uma avaliação através do código identificador (ID).
	 * 
	 * @param indicacao
	 *            - Objeto do tipo Indicação que representa um registro de
	 *            indicação no banco, caso exista.
	 * @param especialista
	 *            - Objeto do tipo Especialista que representa um registro de
	 *            especialista no banco, caso exista.
	 * @return avaliação de indicação do especialista - Retorna um objeto do
	 *         tipo avalIndicacaoEspec com os dados preenchidos conforme as
	 *         informações encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o parâmetro passado.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaObjeto
	public AvalIndicacaoEspec recuperaAvalIndicacaoEspecPorID(Long ID)
			throws ObjetoNaoEncontradoException;

	/**
	 * 
	 * Consulta que recupera a média de peso de todos os especialistas
	 * cadastrados no banco.
	 * 
	 * @return double - Retorna um valor double que representa a média do peso
	 *         dos especialistas cadastrados.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaObjeto
	public Double recuperaMediaDoPesoAvaliadorDosEspecialistas();

}
