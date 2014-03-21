package DAO;

import java.util.List;

import modelo.Paciente;
import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaListaPaginada;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

/**
 * @author bruno.oliveira
 *
 */
public interface PacienteDAO extends DaoGenerico<Paciente, Long> {

	@RecuperaLista
	public List<Paciente> recuperaListaDePacientes();
	
	@RecuperaObjeto
	public Paciente recuperaPacientePorCodigo(String codPaciente) throws ObjetoNaoEncontradoException;
	
	@RecuperaLista
	public List<Paciente> recuperaPacientePorCodigoLike(String codPaciente);
	
	@RecuperaLista
	public List<Paciente> recuperaPacientePorNome(String nomePaciente);
	
	@RecuperaListaPaginada(tamanhoPagina=10)
	public List<Paciente> recuperaListaDePacientesPaginada();
	
	@RecuperaListaPaginada(tamanhoPagina=10)
	public List<Paciente> recuperaListaDePacientesPaginadaComListaDeAtendimentos();
	
	@RecuperaObjeto
	public Paciente recuperaUmPacienteComAtendimento(Paciente paciente) throws ObjetoNaoEncontradoException;		
}
