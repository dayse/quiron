package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exception.RelatorioException;
import relatorio.Relatorio;
import relatorio.RelatorioFactory;
import service.anotacao.Transacional;
import service.exception.AplicacaoException;
import util.SelectOneDataModel;
import DAO.PacienteDAO;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.Impl.PacienteDAOImpl;
import modelo.Indicacao;
import modelo.Paciente;
import modelo.PacienteRelatorio;

public class PacienteAppService {
	
	// DAOs
	private static PacienteDAO pacienteDAO;
	
	public PacienteAppService() throws Exception{
		try{
			pacienteDAO = FabricaDeDao.getDao(PacienteDAOImpl.class);
		}catch (Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	@Transacional
	public void altera(Paciente paciente) throws AplicacaoException{
		pacienteDAO.altera(paciente);
	}
	
	@Transacional
	public void exclui(Paciente paciente) throws AplicacaoException{
		Paciente pacienteBD = null;
		try{
			pacienteBD = pacienteDAO.getPorIdComLock(paciente.getId());
		}catch(ObjetoNaoEncontradoException e){
			throw new AplicacaoException("paciente.NAO_ENCONTRADO");
		}
		pacienteDAO.exclui(pacienteBD);
	}
	
	@Transacional
	public void inclui (Paciente paciente) throws AplicacaoException{
		try{
			pacienteDAO.recuperaPacientePorCodigo(paciente.getCodPaciente());
			throw new AplicacaoException("paciente.CODIGO_EXISTENTE");
		}catch(ObjetoNaoEncontradoException ob){
		}
		pacienteDAO.inclui(paciente);
	}
	
	public List<Paciente> recuperaListaDePacientes() throws AplicacaoException{
		List<Paciente> pacientes = pacienteDAO.recuperaListaDePacientes();
		if(pacientes.isEmpty()){
			throw new AplicacaoException("paciente.PACIENTES_INEXISTENTES");
		}else{
			return pacientes;
		}
	}
	
	public List<Paciente> recuperaListaDePacientesPaginada(){
		return pacienteDAO.recuperaListaDePacientesPaginada();
	}
	
	public Paciente recuperaPacientePorCodigo(String codPaciente) throws AplicacaoException{
		Paciente pacienteBD = null;
		try{
			pacienteBD = pacienteDAO.recuperaPacientePorCodigo(codPaciente);
		}catch(ObjetoNaoEncontradoException exc){
			throw new AplicacaoException("paciente.NAO_ENCONTRADO");
		}
		return pacienteBD;
	}
	
	public List<Paciente> recuperaPacientePorCodigoLike(String codigoPaciente){
		return pacienteDAO.recuperaPacientePorCodigoLike(codigoPaciente);
	}
	
	public List<Paciente> recuperaPacientePorNome(String nomePaciente){
		return pacienteDAO.recuperaPacientePorNome(nomePaciente);
	}

	public List<Paciente> recuperaListaDePacientesPaginadaComListaDeAtendimentos(){
		return pacienteDAO.recuperaListaDePacientesPaginadaComListaDeAtendimentos();
	}
	
	public Paciente recuperaUmPacienteComAtendimento(Paciente paciente) throws AplicacaoException{
		try{
			return pacienteDAO.recuperaUmPacienteComAtendimento(paciente);
		}catch(ObjetoNaoEncontradoException e){
			throw new AplicacaoException("paciente.NAO_ENCONTRADO");
		}
	}

	 /**
	 *  
	 * Método utilizado para chamar
	 * a fábrica de relatórios responsável por criar o
	 * relatório de pacientes.
	 * 
	 * @param listaDePacientes - Uma lista com as pacientes
	 * cadastrados no banco.
	 * @throws AplicacaoException - Retornar uma exception de aplicacao
	 * quando algo impedir que o relatório for gerado.
	 * 
	 */	
	public void gerarRelatorio(List<Paciente> listaDePacientes)
		throws AplicacaoException{
		System.out.println("Antes do metodo getRelatorio dentro de gerarRelatorio de PacienteAppService");
		
		Relatorio relatorio = RelatorioFactory.getRelatorio(Relatorio.RELATORIO_LISTAGEM_DE_PACIENTES);
		
		if(relatorio != null)
			System.out.println("A variavel do tipo Relatorio é diferente de null em PacienteAppService");
		
		System.out.println("Depois do metodo getRelatorio dentro de gerarRelatorio de PacienteAppService");
		
		try{
			relatorio.gerarRelatorio(this.converterParaPacienteRelatorio(listaDePacientes), new HashMap());
		} catch (RelatorioException re){
			throw new AplicacaoException("paciente.Relatorio_NAO_GERADO");
		}
	}
	
	/**
	 * 
	 * Método utilizado para fazer a conversão de uma
	 * lista de pacientes para uma lista de pacientes formatada
	 * especialmente para o relatório.
	 * 
	 * @param listaDePacientes - Uma lista de objetos do tipo Paciente
	 * @return listaPacientesRelatorio - Uma lista de objetos do tipo PacienteRelatorio
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public List<PacienteRelatorio> converterParaPacienteRelatorio(List<Paciente> listaDePacientes){
		List<PacienteRelatorio> listaPacientesRelatorio = new ArrayList<PacienteRelatorio>();
		for(Paciente paciente : listaDePacientes){
			PacienteRelatorio pacienteRelatorio = new PacienteRelatorio(paciente);
			listaPacientesRelatorio.add(pacienteRelatorio);
		}		
		return listaPacientesRelatorio;
	}
}