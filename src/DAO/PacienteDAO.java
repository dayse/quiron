package DAO;

import java.util.List;

import modelo.Paciente;
import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaListaPaginada;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

/**
 * 
 * Interface criada no padr�o DAO para intermediar os pedidos de requisi��o de
 * banco de dados ao modelo Paciente.
 * 
 * @author bruno.oliveira
 * 
 */
public interface PacienteDAO extends DaoGenerico<Paciente, Long> {

	/**
	 * 
	 * Consulta que retorna a lista de pacientes atualizada.
	 * 
	 * @return lista atualizada de pacientes (n�o paginada)
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaLista
	public List<Paciente> recuperaListaDePacientes();
	
	/**
	 * Recupera um paciente espec�fico atrav�s de uma busca por um c�digo.
	 * 
	 * @param codPaciente - Par�metro que ser� utilizado para se atingir o paciente
	 *            que se deseja recuperar.
	 * @return paciente - Retorna um objeto do tipo paciente com os dados
	 *         preenchidos conforme as informa��es encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o par�metro passado.
	 *             
	 * @author bruno.oliveira (Atualiza��o)
	 *            
	 */
	@RecuperaObjeto
	public Paciente recuperaPacientePorCodigo(String codPaciente) throws ObjetoNaoEncontradoException;
	
	/**
	 * 
	 * Consulta que retorna lista de pacientes que batem ou tem semelhan�a
	 * com o c�digo de paciente buscado.
	 * 
	 * @param codPaciente - c�digo do paciente cujo pacientes deseja-se procurar
	 * @return lista n�o-pagina de pacientes
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaLista
	public List<Paciente> recuperaPacientePorCodigoLike(String codPaciente);
	
	/**
	 * 
	 * Consulta que retorna lista de pacientes por um nome passado como parametro.
	 * 
	 * @param nomePaciente - nome do paciente cujo cadrastos deseja-se procurar
	 * @return lista n�o-paginada de paciente
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaLista
	public List<Paciente> recuperaPacientePorNome(String nomePaciente);

	/**
	 * 
	 * Consulta que recupera a lista paginada de todos os pacientes
	 * registrados no banco.
	 * 
	 * @return List de pacientes - Retorna uma lista paginada de pacientes.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina=10)
	public List<Paciente> recuperaListaDePacientesPaginada();
	
	/**
	 * 
	 * Consulta que recupera a lista paginada de todos os pacientes
	 * registrados no banco junto com a lista de atendimentos dos pacientes.
	 * 
	 * @return List de pacientes - Retorna uma lista paginada de pacientes.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina=10)
	public List<Paciente> recuperaListaDePacientesPaginadaComListaDeAtendimentos();
	
	/**
	 * Recupera um paciente espec�fico com seu atendimentos
	 * atrav�s de uma busca por um objeto paciente.
	 * 
	 * @param paciente - Par�metro que ser� utilizado para se atingir o paciente
	 *            que se deseja recuperar.
	 * @return paciente - Retorna um objeto do tipo paciente com os dados
	 *         preenchidos conforme as informa��es encontradas no banco. Incluindo as infoma��es
	 *         de atendimentos que esse paciente tenha feito.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o par�metro passado.
	 *             
	 * @author bruno.oliveira (Atualiza��o)
	 *            
	 */	
	@RecuperaObjeto
	public Paciente recuperaUmPacienteComAtendimento(Paciente paciente) throws ObjetoNaoEncontradoException;		
}
