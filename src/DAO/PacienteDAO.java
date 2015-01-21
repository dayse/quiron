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
 * Interface criada no padrão DAO para intermediar os pedidos de requisição de
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
	 * @return lista atualizada de pacientes (não paginada)
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaLista
	public List<Paciente> recuperaListaDePacientes();
	
	/**
	 * Recupera um paciente específico através de uma busca por um código.
	 * 
	 * @param codPaciente - Parâmetro que será utilizado para se atingir o paciente
	 *            que se deseja recuperar.
	 * @return paciente - Retorna um objeto do tipo paciente com os dados
	 *         preenchidos conforme as informações encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o parâmetro passado.
	 *             
	 * @author bruno.oliveira (Atualização)
	 *            
	 */
	@RecuperaObjeto
	public Paciente recuperaPacientePorCodigo(String codPaciente) throws ObjetoNaoEncontradoException;
	
	/**
	 * 
	 * Consulta que retorna lista de pacientes que batem ou tem semelhança
	 * com o código de paciente buscado.
	 * 
	 * @param codPaciente - código do paciente cujo pacientes deseja-se procurar
	 * @return lista não-pagina de pacientes
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaLista
	public List<Paciente> recuperaPacientePorCodigoLike(String codPaciente);
	
	/**
	 * 
	 * Consulta que retorna lista de pacientes por um nome passado como parametro.
	 * 
	 * @param nomePaciente - nome do paciente cujo cadrastos deseja-se procurar
	 * @return lista não-paginada de paciente
	 * 
	 * @author bruno.oliveira (Atualização)
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
	 * Recupera um paciente específico com seu atendimentos
	 * através de uma busca por um objeto paciente.
	 * 
	 * @param paciente - Parâmetro que será utilizado para se atingir o paciente
	 *            que se deseja recuperar.
	 * @return paciente - Retorna um objeto do tipo paciente com os dados
	 *         preenchidos conforme as informações encontradas no banco. Incluindo as infomações
	 *         de atendimentos que esse paciente tenha feito.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o parâmetro passado.
	 *             
	 * @author bruno.oliveira (Atualização)
	 *            
	 */	
	@RecuperaObjeto
	public Paciente recuperaUmPacienteComAtendimento(Paciente paciente) throws ObjetoNaoEncontradoException;		
}
