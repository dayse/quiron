package service;

import java.io.Serializable;
import java.util.List;

import service.anotacao.Transacional;
import service.exception.AplicacaoException;
import modelo.Atendimento;
import modelo.Paciente;
import DAO.AtendimentoDAO;
import DAO.Impl.AtendimentoDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;
import actions.BaseActions;

public class AtendimentoAppService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static AtendimentoDAO atendimentoDAO;
	
	public AtendimentoAppService() throws Exception{
		try{
			atendimentoDAO = FabricaDeDao.getDao(AtendimentoDAOImpl.class);
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Transacional
	public void altera (Atendimento atendimento) throws AplicacaoException{
		atendimentoDAO.altera(atendimento);
	}
	
	@Transacional
	public void exclui (Atendimento atendimento) throws AplicacaoException{
		Atendimento atendimentoBD = null;
		try{
			atendimentoBD = atendimentoDAO.getPorIdComLock(atendimento.getId());
		}catch(ObjetoNaoEncontradoException e){
			throw new AplicacaoException("atendimento.NAO_ENCONTRADO");
		}
		atendimentoDAO.exclui(atendimentoBD);
	}
	
	@Transacional
	public void inclui (Atendimento atendimento) throws AplicacaoException{
		try{
			atendimentoDAO.recuperaAtendimentoPorCodigo(atendimento.getCodAtendimento());
			throw new AplicacaoException("atendimento.CODIGO_EXISTENTE");
		}catch(ObjetoNaoEncontradoException ob){
		}
		atendimentoDAO.inclui(atendimento);
	}


	public Atendimento recuperaAtendimentoPorCodigo(String codAtendimento) throws AplicacaoException{
		Atendimento atendimentoBD = null;
		try{
			atendimentoBD = atendimentoDAO.recuperaAtendimentoPorCodigo(codAtendimento);
		}catch(ObjetoNaoEncontradoException exc){
			throw new AplicacaoException("atendimento.NAO_ENCONTRADO");
		}
		return atendimentoBD;		
	}
	
	public Atendimento recuperaAtendimentoPorCodigoComPaciente(String codAtendimento) throws AplicacaoException{
		Atendimento atendimentoBD = null;
		try{
			atendimentoBD = atendimentoDAO.recuperaAtendimentoPorCodigoComPaciente(codAtendimento);
		}catch(ObjetoNaoEncontradoException exc){
			throw new AplicacaoException("atendimento.NAO_ENCONTRADO");
		}
		return atendimentoBD;		
	}

	public List<Atendimento> recuperaListaDeAtendimentosPaginada(){
		return atendimentoDAO.recuperaListaDeAtendimentosPaginada();
	}

	public List<Atendimento> recuperaListaDeAtendimentosComPacientePaginada(){
		return atendimentoDAO.recuperaListaDeAtendimentosComPacientePaginada();
	}
	
	public List<Atendimento> recuperaListaPaginadaDeAtendimentosPorPacientePorCodigoLike(String codPaciente){
		return atendimentoDAO.recuperaListaPaginadaDeAtendimentosPorPacientePorCodigoLike(codPaciente);
	}
	
	public List<Atendimento> recuperaListaPaginadaDeAtendimentosPorPacientePorNome(String nome){
		return atendimentoDAO.recuperaListaPaginadaDeAtendimentosPorPacientePorNome(nome);
	}
	
	public Atendimento recuperaUltimoAtendimento(){
		return atendimentoDAO.recuperaUltimoAtendimento();
	}
	public List<Atendimento> recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLike(String nomeMedico){
		return atendimentoDAO.recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLike(nomeMedico);
	}

	public List<Atendimento> recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLike(String nomePaciente){
		return atendimentoDAO.recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLike(nomePaciente);
	}
	
	
}
