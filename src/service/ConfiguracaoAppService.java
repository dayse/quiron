package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exception.RelatorioException;
import modelo.Anamnese;
import modelo.Atendimento;
import modelo.AvalIndicacaoEspec;
import modelo.Configuracao;
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
import DAO.ConfiguracaoDAO;
import DAO.EspecialistaDAO;
import DAO.IndicacaoDAO;
import DAO.ParametroDAO;
import DAO.Impl.AnamneseDAOImpl;
import DAO.Impl.AtendimentoDAOImpl;
import DAO.Impl.AvalIndicacaoEspecDAOImpl;
import DAO.Impl.ConfiguracaoDAOImpl;
import DAO.Impl.EspecialistaDAOImpl;
import DAO.Impl.IndicacaoDAOImpl;
import DAO.Impl.ParametroDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.InfraestruturaException;
import DAO.exception.ObjetoNaoEncontradoException;

public class ConfiguracaoAppService {
	
	private static ConfiguracaoDAO configuracaoDAO;
	
	public ConfiguracaoAppService() throws Exception{
		try{
			configuracaoDAO = FabricaDeDao.getDao(ConfiguracaoDAOImpl.class);
		
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);	
		}
	}
	
	@Transacional
	public void inclui(Configuracao configuracao) throws AplicacaoException{
			
			try{
				configuracaoDAO.recuperaConfiguracaoPorNome(configuracao.getNome());
				throw new AplicacaoException("configuracao.NOME_EXISTENTE");
			}catch(ObjetoNaoEncontradoException ob){
			}
			configuracaoDAO.inclui(configuracao);
			
			
			}
	



	public List<Configuracao> recuperaListaDeConfiguracaoPaginada(){
		return configuracaoDAO.recuperaListaDeConfiguracaoPaginada();
	}
	
	
	
}
