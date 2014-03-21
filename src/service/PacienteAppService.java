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

import modelo.Paciente;

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
}