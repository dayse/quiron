package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exception.RelatorioException;
import modelo.Anamnese;
import modelo.Atendimento;
import modelo.AvalIndicacaoEspec;
import modelo.Algoritmo;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.Paciente;
import modelo.Parametro;
import modelo.TipoUsuario;
import modelo.Usuario;
import relatorio.Relatorio;
import relatorio.RelatorioFactory;
import service.anotacao.Transacional;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import DAO.AnamneseDAO;
import DAO.AtendimentoDAO;
import DAO.AvalIndicacaoEspecDAO;
import DAO.AlgoritmoDAO;
import DAO.EspecialistaDAO;
import DAO.IndicacaoDAO;
import DAO.ParametroDAO;
import DAO.Impl.AnamneseDAOImpl;
import DAO.Impl.AtendimentoDAOImpl;
import DAO.Impl.AvalIndicacaoEspecDAOImpl;
import DAO.Impl.AlgoritmoDAOImpl;
import DAO.Impl.EspecialistaDAOImpl;
import DAO.Impl.IndicacaoDAOImpl;
import DAO.Impl.ParametroDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.InfraestruturaException;
import DAO.exception.ObjetoNaoEncontradoException;

public class AlgoritmoAppService {
	
	private static AlgoritmoDAO algoritmoDAO;
	
	public AlgoritmoAppService() throws Exception{
		try{
			algoritmoDAO = FabricaDeDao.getDao(AlgoritmoDAOImpl.class);
		
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);	
		}
	}
	
	@Transacional
	public void altera (Algoritmo algoritmo) throws AplicacaoException{
		Algoritmo algoritmoAtivo = algoritmoDAO.recuperaAlgoritmoAtivo();
		if(algoritmo.equals(algoritmoAtivo)){
			throw new AplicacaoException("algoritmo.JA_ESTA_ATIVO");
		}else{
			algoritmo.setStatus("Ativo");
			algoritmoAtivo.setStatus("Inativo");
			algoritmoDAO.altera(algoritmo);
			algoritmoDAO.altera(algoritmoAtivo);
		}
	}
	
	@Transacional
	public void inclui(Algoritmo algoritmo) throws AplicacaoException{
			
			try{
				algoritmoDAO.recuperaAlgoritmoPorNome(algoritmo.getNome());
				throw new AplicacaoException("algoritmo.NOME_EXISTENTE");
			}catch(ObjetoNaoEncontradoException ob){
			}
			algoritmoDAO.inclui(algoritmo);
	}

	public List<Algoritmo> recuperaListaDeAlgoritmo(){
		return algoritmoDAO.recuperaListaDeAlgoritmo();
	}
	
	public Algoritmo recuperaAlgoritmoAtivo(){
		return algoritmoDAO.recuperaAlgoritmoAtivo();
	}
	
}
