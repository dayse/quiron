package DAO;

import java.util.List;

import modelo.Atendimento;
import modelo.Usuario;
import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaListaPaginada;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

/**
 * 
 * Interface criada no padrão DAO para intermediar os pedidos de requisição de
 * banco de dados ao modelo Atendimento.
 * 
 * @author bruno.oliveira
 * 
 */
public interface AtendimentoDAO extends DaoGenerico<Atendimento, Long> {

	/**
	 * 
	 * Consulta que recupera um atendimento utilizando um código identificador
	 * de atendimento como parâmetro.
	 * 
	 * @param codAtendimento
	 *            - Parâmetro que será utilizado para se atingir o atendimento
	 *            que se deseja recuperar.
	 * @return atendimento - Retorna um objeto do tipo atendimento com os dados
	 *         preenchidos conforme as informações encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o parâmetro passado.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaObjeto
	public Atendimento recuperaAtendimentoPorCodigo(String codAtendimento)
			throws ObjetoNaoEncontradoException;

	/**
	 * Faz o mesmo que o recuperaAtendimentoPorCodigo, porém 
	 * retorna o atendimento trazendo junto seu paciente (que é lazy).
	 * 
	 * @param codAtendimento - Parâmetro que será utilizado para se atingir o atendimento
	 *            que se deseja recuperar.
	 * @return atendimento - Retorna um objeto do tipo atendimento com os dados
	 *         preenchidos conforme as informações encontradas no banco.
	 * @throws ObjetoNaoEncontradoException
	 *             - Retorna um exception caso nenhum registro for encontrado
	 *             com o parâmetro passado.
	 *             
	 * @author bruno.oliveira (Atualização)
	 *            
	 */
	@RecuperaObjeto
	public Atendimento recuperaAtendimentoPorCodigoComPaciente(String codAtendimento)
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
	public List<Atendimento> recuperaListaDeAtendimentosComPacientePaginada();

	/**
	 * 
	 * Consulta que recupera a lista paginada de todos os atendimentos
	 * registrados no banco e traz junto os pacientes.
	 * 
	 * @return List de atendimento - Retorna uma lista paginada de atendimentos com pacientes.
	 * 
	 * @author felipe.arruda
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina = 10)
	public List<Atendimento> recuperaListaDeAtendimentosPaginada();

	/**
	 * 
	 * Consulta que recupera uma lista paginada de atendimentos de um paciente
	 * específico utilizando o código do paciente como identificador.
	 * 
	 * @param codPaciente
	 *            - Código do paciente que terá os atendimentos recuperados do
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
	 * específico utilizando o nome do paciente como identificador.
	 * 
	 * @param nome
	 *            - Nome do paciente que terá os atendimentos recuperados do
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
	 * Retorna a lista paginada de atendimentos com pacientes pelo 
	 * nome do médico (não é case sensitive).
	 * 
	 * @param nomeMedico - Nome do médico/clínico que terá seus atendimentos recuperados
	 * @return lista de atendimentos do médico/clínico passado por parâmetro e informações de paciente
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina = 10)
	public List<Atendimento> recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLike(
			String nomeMedico);
	
	/**
	 * 
	 * Retorna a lista paginada de atendimentos com pacientes pelo 
	 * nome do paciente (não é case sensitive).
	 * 
	 * @param nomePaciente - nome do paciente cujo atendimentos deseja-se procurar
	 * @return lista paginada de atendimentos e paciente
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina = 10)
	public List<Atendimento> recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLike(
			String nomePaciente);
	

	/**
	 * 
	 * Retorna a lista paginada de atendimentos com pacientes pelo 
	 * codigo do paciente
	 * 
	 * @param codPaciente - código do paciente cujo atendimentos deseja-se procurar
	 * @return lista de atendimento 
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina = 10)
	public List<Atendimento> recuperaListaPaginadaDeAtendimentosComPacientePorCodigoPaciente(
			String codPaciente);

	/**
	 * 
	 * Retorna a lista paginada de atendimentos com pacientes e com anamnese pelo 
	 * codigo do paciente
	 * 
	 * @param codPaciente - código do paciente cujo atendimentos deseja-se procurar
	 * @return lista paginada de atendimentos, anamneses e paciente
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */	
	@RecuperaListaPaginada(tamanhoPagina = 10)
	public List<Atendimento> recuperaListaPaginadaDeAtendimentosComPacienteComAnamnesePorCodigoPaciente(
			String codPaciente);

	/**
	 * 
	 * Consulta que retorna lista de atendimentos com pacientes e anamneses através de
	 * uma busca pelo código do paciente.
	 * 
	 * @param codPaciente - código do paciente cujo atendimentos deseja-se procurar
	 * @return lista paginada de atendimentos, anamneses e paciente
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaLista
	public List<Atendimento> recuperaListaDeAtendimentosComPacienteComAnamnesePorCodigoPaciente(
			String codPaciente);
	
	/**
	 * 
	 * Consulta que retorna a lista de atendimentos atualizada.
	 * 
	 * @return lista atualizada de atendimentos (não paginada)
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaLista
	public List<Atendimento> recuperaListaAtendimento();
	
	/**
	 * 
	 * Consulta que recupera o último atendimento cadastrado no banco. Ou seja,
	 * o mais recente.
	 * 
	 * @return atendimento - Retorna um objeto do tipo atendimento com os dados
	 *         preenchidos conforme as informações encontradas no banco.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@RecuperaObjeto
	public Atendimento recuperaUltimoAtendimento();
	
	/**
	 * 
	 * Consulta que retorna uma lista de atendimentos que tem um clínico/médico em comum
	 * 
	 * @param usuario - clinico/medico cujo atendimentos deseja-se procurar
	 * @return lista de atendimentos de um determinado clinico/médico (lista não paginada)
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaLista
	public List<Atendimento> recuperaListaDeAntendimentosParaUmClinico(Usuario usuario);
	
	/**
	 * 
	 * Consulta que retorna uma lista de atendimentos que tem um técnico em comum
	 * 
	 * @param usuario - técnico cujo atendimentos deseja-se procurar
	 * @return lista de atendimentos de um determinado técnico (lista não paginada)
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaLista
	public List<Atendimento> recuperaListaDeAntendimentosParaUmTecnico(Usuario usuario);
	
	/**
	 * 
	 * Consulta que retorna uma lista de atendimentos marcados com um determinado status.
	 * 
	 * @param status com o qual se deseja filtrar os atendimentos
	 * @return lista de atendimentos paginadas e com dados de paciente que possuem o status buscado.
	 * Lista paginada com limite de 10 por página.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina = 10)
	public List<Atendimento> recuperaListaPaginadaDeAtendimentosComPacientePorStatus(String status);
	
	/**
	 * 
	 * Consulta que retorna uma lista de atendimentos de um determinado médico e marcado com um determinado status
	 * 
	 * @param nomeMedico - médico/clínico cujo atendimento deseja-se encontrar
	 * @param status com o qual se deseja filtrar os atendimentos
	 * @return lista paginada de atendimentos com dados de paciente que possuem o status e o médico/clinico buscado.
	 * Lista paginada com limite de 10 por página.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina = 10)
	public List<Atendimento> recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLikePorStatus(String nomeMedico, String status);
	
	/**
	 * 
	 * Consulta que retorna uma lista de atendimentos de um determinado paciente e marcado com um determinado status
	 * 
	 * @param nomePaciente - paciente cujo atendimento deseja-se encontrar
	 * @param status com o qual se deseja filtrar os atendimentos
	 * @return lista paginada de atendimentos com os dados de paciente que pertencem a determinado paciente e com determinado status buscado.
	 * Lista paginada com limite de 10 por página.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaListaPaginada(tamanhoPagina = 10)
	public List<Atendimento> recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLikePorStatus(String nomePaciente, String status);
}
