package DAO;

import java.util.List;

import modelo.Atendimento;
import DAO.anotacao.RecuperaListaPaginada;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

/**
 * 
 * Interface criada no padr�o DAO para intermediar os pedidos de requisi��o de
 * banco de dados ao modelo Atendimento.
 * 
 * @author bruno.oliveira
 * 
 */
public interface AtendimentoDAO extends DaoGenerico<Atendimento, Long> {

	/**
	 * 
	 * Consulta que recupera uma atendimento utilizando um c�digo identificador
	 * de atendimento como par�metro.
	 * 
	 * @param codAtendimento
	 *            - Par�metro que ser� utilizado para se atingir o atendimento
	 *            que se deseja recuperar.
	 * @return atendimento - Retorna um objeto do tipo atendimento com os dados
	 *         preenchidos conforme as informa��es encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o par�metro passado.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaObjeto
	public Atendimento recuperaAtendimentoPorCodigo(String codAtendimento)
			throws ObjetoNaoEncontradoException;

	/**
	 * 
	 * Consulta que recupera a lista paginada de todos os atendimentos
	 * registrados no banco.
	 * 
	 * @return List de atendimento - Retorna uma lista paginada de atendimentos.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina = 10)
	public List<Atendimento> recuperaListaDeAtendimentosPaginada();

	/**
	 * 
	 * Consulta que recupera uma lista paginada de atendimentos de um paciente
	 * espec�fico utilizando o c�digo do paciente como identificador.
	 * 
	 * @param codPaciente
	 *            - C�digo do paciente que ter� os atendimentos recuperados do
	 *            banco, caso existam.
	 * @return list de atendimento - Retorna uma lista de atendimentos referente
	 *         ao paciente, caso existam.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina = 10)
	public List<Atendimento> recuperaListaPaginadaDeAtendimentosPorPacientePorCodigoLike(
			String codPaciente);

	/**
	 * 
	 * Consulta que recupera uma lista paginada de atendimentos de um paciente
	 * espec�fico utilizando o nome do paciente como identificador.
	 * 
	 * @param nome
	 *            - Nome do paciente que ter� os atendimentos recuperados do
	 *            banco, caso existam.
	 * @return list de atendimento - Retorna uma lista de atendimentos referente
	 *         ao paciente, caso existam.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina = 10)
	public List<Atendimento> recuperaListaPaginadaDeAtendimentosPorPacientePorNome(
			String nome);

	/**
	 * 
	 * Consulta que recupera o �ltimo atendimento cadastrado no banco. Ou seja,
	 * o mais recente.
	 * 
	 * @return atendimento - Retorna um objeto do tipo atendimento com os dados
	 *         preenchidos conforme as informa��es encontradas no banco.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaObjeto
	public Atendimento recuperaUltimoAtendimento();
}
