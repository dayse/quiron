package service;

import java.util.HashMap;
import java.util.List;

import exception.RelatorioException;
import relatorio.Relatorio;
import relatorio.RelatorioFactory;
import service.anotacao.Transacional;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.Parametro;
import DAO.AvalIndicacaoEspecDAO;
import DAO.EspecialistaDAO;
import DAO.IndicacaoDAO;
import DAO.ParametroDAO;
import DAO.Impl.AvalIndicacaoEspecDAOImpl;
import DAO.Impl.EspecialistaDAOImpl;
import DAO.Impl.IndicacaoDAOImpl;
import DAO.Impl.ParametroDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;

/**
 * 
 * @author bruno.oliveira
 * 
 */
public class IndicacaoAppService {

	private static IndicacaoDAO indicacaoDAO;
	private static EspecialistaDAO especialistaDAO;
	private static ParametroDAO parametroDAO;
	
	private static AvalIndicacaoEspecAppService avalIndicacaoEspecService;

	public IndicacaoAppService() throws Exception {
		try {
			indicacaoDAO = FabricaDeDao.getDao(IndicacaoDAOImpl.class);
			especialistaDAO = FabricaDeDao.getDao(EspecialistaDAOImpl.class);
			parametroDAO = FabricaDeDao.getDao(ParametroDAOImpl.class);

			avalIndicacaoEspecService = FabricaDeAppService.getAppService(AvalIndicacaoEspecAppService.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Transacional
	public void altera(Indicacao indicacao) throws AplicacaoException {
		indicacaoDAO.altera(indicacao);
	}

	@Transacional
	public void exclui(Indicacao indicacao) throws AplicacaoException {
		Indicacao indicacaoBD = null;
		try {
			indicacaoBD = indicacaoDAO.getPorIdComLock(indicacao.getId());
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("indicacao.NAO_ENCONTRADO");
		}
		indicacaoDAO.exclui(indicacaoBD);
	}

	@Transacional
	public void inclui (Indicacao indicacao) throws AplicacaoException{
		try{
			indicacaoDAO.recuperaIndicacaoPorCodigo(indicacao.getCodIndicacao());
			throw new AplicacaoException("indicacao.CODIGO_EXISTENTE");
		}catch(ObjetoNaoEncontradoException ob){
			
		}
		indicacaoDAO.inclui(indicacao);
		
		List<Especialista> especialistaBD = especialistaDAO.recuperaListaEspecialista();
		List<Parametro> parametroBD = parametroDAO.recuperaListaDeParametros();
		
		if(!especialistaBD.isEmpty() && !parametroBD.isEmpty()){
			for(Especialista especialista : especialistaBD){
				for(Parametro parametro : parametroBD){
				
				AvalIndicacaoEspec avalIndicacaoEspec = new AvalIndicacaoEspec();
				
				avalIndicacaoEspec.setValor(0);
				
				avalIndicacaoEspec.setIndicacao(indicacao);
				avalIndicacaoEspec.setEspecialista(especialista);
				avalIndicacaoEspec.setParametro(parametro);
				
				avalIndicacaoEspecService.inclui(avalIndicacaoEspec);
				}
			}
		} 
	}
	
	public List<Indicacao> recuperaListaIndicacao(){
		return indicacaoDAO.recuperaListaIndicacao();
	}

	public List<Indicacao> recuperaListaDeIndicacoesPaginada() {
		return indicacaoDAO.recuperaListaDeIndicacoesPaginada();
	}

	public List<Indicacao> recuperaIndicacaoPorCodigoLike(
			String codIndicacao) {
		return indicacaoDAO.recuperaIndicacaoPorCodigoLike(codIndicacao);
	}

	public List<Indicacao> recuperaIndicacaoPorNome(String nomeIndicacao) {
		return indicacaoDAO.recuperaIndicacaoPorNome(nomeIndicacao);
	}

	public Indicacao recuperaIndicacaoPorCodigo(String codIndicacao) throws ObjetoNaoEncontradoException {
		return indicacaoDAO.recuperaIndicacaoPorCodigo(codIndicacao);
	}
	
	 /**
	 *  
	 * Método utilizado para gerar o relatório para chamar
	 * a fábrica de relatórios responsável por criar o
	 * relatório de indicações.
	 * 
	 * @param listaDeIndicacao - Uma lista com as indicações
	 * cadastrados no banco.
	 * @throws AplicacaoException - Retornar uma exception de aplicacao
	 * quando algo impedir que o relatório for gerado.
	 * 
	 */	
	public void gerarRelatorio(List<Indicacao> listaDeIndicacao)
		throws AplicacaoException{
		System.out.println("Antes do metodo getRelatorio dentro de gerarRelatorio de EspecialistaAppService");
		
		Relatorio relatorio = RelatorioFactory.getRelatorio(Relatorio.RELATORIO_LISTAGEM_DE_INDICACAO);
		
		if(relatorio != null)
			System.out.println("A variavel do tipo Relatorio é diferente de null em EspecialistaAppService");
		
		System.out.println("Depois do metodo getRelatorio dentro de gerarRelatorio de EspecialistaAppService");
		
		try{
			relatorio.gerarRelatorio(listaDeIndicacao, new HashMap());
		} catch (RelatorioException re){
			throw new AplicacaoException("indicacao.Relatorio_NAO_GERADO");
		}
	}

}
