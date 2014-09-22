package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exception.RelatorioException;
import modelo.AvalIndicacaoEspec;
import modelo.ConjuntoIndicacaoParaAvaliacao;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.Parametro;
import modelo.TipoUsuario;
import modelo.Usuario;
import relatorio.Relatorio;
import relatorio.RelatorioFactory;
import service.anotacao.Transacional;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import DAO.EspecialistaDAO;
import DAO.IndicacaoDAO;
import DAO.ParametroDAO;
import DAO.Impl.EspecialistaDAOImpl;
import DAO.Impl.IndicacaoDAOImpl;
import DAO.Impl.ParametroDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;

public class EspecialistaAppService {

	private static EspecialistaDAO especialistaDAO;
	private static IndicacaoDAO indicacaoDAO;
	private static ParametroDAO parametroDAO;
	
	private static AvalIndicacaoEspecAppService avalIndicacaoEspecService;

	public EspecialistaAppService() throws Exception {
		try {
			especialistaDAO = FabricaDeDao.getDao(EspecialistaDAOImpl.class);
			indicacaoDAO = FabricaDeDao.getDao(IndicacaoDAOImpl.class);
			parametroDAO = FabricaDeDao.getDao(ParametroDAOImpl.class);
			
			avalIndicacaoEspecService = FabricaDeAppService.getAppService(AvalIndicacaoEspecAppService.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Transacional
	public void altera(Especialista especialista) throws AplicacaoException {
			especialistaDAO.altera(especialista);
	}
	
	@Transacional
	public void alteraComSeguranca(Especialista especialista, Usuario usuarioAutenticado) throws AplicacaoException{
		if(verificaUsuarioAutenticadoTemPermissao(usuarioAutenticado)){
			this.altera(especialista);
		}
	}

	@Transacional
	public void exclui(Especialista especialista) throws AplicacaoException {
			Especialista especialistaBD = null;
			try {
				especialistaBD = especialistaDAO.getPorIdComLock(especialista
						.getId());
			} catch (ObjetoNaoEncontradoException e) {
				throw new AplicacaoException("especialista.NAO_ENCONTRADO");
			}
			especialistaDAO.exclui(especialistaBD);
	}
	
	@Transacional
	public void excluiComSeguranca(Especialista especialista, Usuario usuarioAutenticado) throws AplicacaoException{
		if(verificaUsuarioAutenticadoTemPermissao(usuarioAutenticado)){
			this.exclui(especialista);
		}
	}

	@Transacional
	public void inclui(Especialista especialista) throws AplicacaoException {
			try {
				especialistaDAO.recuperaEspecialistaPorCodigo(especialista
						.getCodEspecialista());
				throw new AplicacaoException("especialista.CODIGO_EXISTENTE");
			} catch (ObjetoNaoEncontradoException ob) {
			}
			especialistaDAO.inclui(especialista);
	
			List<Indicacao> indicacaoBD = indicacaoDAO.recuperaListaIndicacao();
			List<Parametro> parametroBD = parametroDAO.recuperaListaDeParametros();
			
			if(!indicacaoBD.isEmpty() && !parametroBD.isEmpty()){
				for(Indicacao indicacao : indicacaoBD){
					for(Parametro parametro : parametroBD){
					
					AvalIndicacaoEspec avalIndicacaoEspec = new AvalIndicacaoEspec();
					
					avalIndicacaoEspec.setValor(0);
					
					avalIndicacaoEspec.setEspecialista(especialista);
					avalIndicacaoEspec.setIndicacao(indicacao);
					avalIndicacaoEspec.setParametro(parametro);
					
					avalIndicacaoEspecService.inclui(avalIndicacaoEspec);
					}
				}
			}
	}
	
	@Transacional
	public void incluiComSeguranca(Especialista especialista, Usuario usuarioAutenticado) throws AplicacaoException{
		if(verificaUsuarioAutenticadoTemPermissao(usuarioAutenticado)){
			this.inclui(especialista);
		}
	}

	public List<Especialista> recuperaListaDeEspecialistasPaginada() {
		return especialistaDAO.recuperaListaDeEspecialistasPaginada();
	}
	
	public Double recuperaMediaDoPesoAvaliadorDosEspecialistas(){
		return especialistaDAO.recuperaMediaDoPesoAvaliadorDosEspecialistas();
	}


	public Especialista recuperaEspecialistaPorCodigo(String codEspecialista) throws ObjetoNaoEncontradoException {
		return especialistaDAO.recuperaEspecialistaPorCodigo(codEspecialista);
	}

	/**
	 * 
	 * Método necessário para preencher a tela Avaliação do Especilista para cada Indicação. Tela esta que tem uma tabela
	 * onde tanto as colunas como as linhas são criadas de maneira dinâmica.
	 * 
	 * @param especialista - Recebe como parametro o especialista que irá avaliar as indicações.
	 * @return ConjuntoIndicacaoParaAvaliacao - lista pronta para preencher uma tabela dinâmica.
	 * 
	 * @author bruno.oliveira
	 */
	public List<ConjuntoIndicacaoParaAvaliacao> recuperaConjuntoAvaliacaoDeUmEspecialista(
			Especialista especialista) {
		
		List<Indicacao> indicacoes = indicacaoDAO.recuperaListaIndicacao();
		List<ConjuntoIndicacaoParaAvaliacao> conjuntos = new ArrayList<ConjuntoIndicacaoParaAvaliacao>();
		
		for(Indicacao indicacao : indicacoes){
			ConjuntoIndicacaoParaAvaliacao conjunto = new ConjuntoIndicacaoParaAvaliacao();
			conjunto.setIndicacao(indicacao);
			conjunto.setAvaliacao(avalIndicacaoEspecService
					.recuperaListaDeAvaliacaoPorEspecialistaPorIndicacao(especialista, indicacao));
			conjuntos.add(conjunto);			
		}
		
		return conjuntos;
	}
	
	/**
	 * Retorna true se o usuario autenticado for administrador ou engenheiro do conhecimento,
	 * caso contrário levanta uma AplicacaoException de permissão
	 * @param usuarioAutenticado
	 * @return
	 * @throws AplicacaoException
	 * @author felipe.pontes
	 */
	@Transacional
	public Boolean verificaUsuarioAutenticadoTemPermissao(Usuario usuarioAutenticado) throws AplicacaoException{
		
		if (!usuarioAutenticado.getTipoUsuario().getTipoUsuario().equals(TipoUsuario.ADMINISTRADOR) &&
			!usuarioAutenticado.getTipoUsuario().getTipoUsuario().equals(TipoUsuario.ENGENHEIRO_DE_CONHECIMENTO)) {
			throw new AplicacaoException(2,"usuario.NAO_POSSUI_PERMISSAO");
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param listaDeAvaliacao
	 * @throws AplicacaoException
	 */
	public void gerarRelatorio(List<AvalIndicacaoEspec> listaDeAvaliacao) 
			throws AplicacaoException {
		System.out.println("Antes do metodo getRelatorio dentro de gerarRelatorio de EspecialistaAppService");
		
		Relatorio relatorio = RelatorioFactory.getRelatorio(Relatorio.RELATORIO_AVALIACAO_DO_ESPECIALISTA);
		
		if(relatorio != null)
			System.out.println("A variavel do tipo Relatorio e difente de null em EspecialsitaAppService");
		
		System.out.println("Depois do metodo getRelatorio dentro de gerarRelatorio de EspecialsitaAppService");
		
		try{
			relatorio.gerarRelatorio(listaDeAvaliacao, new HashMap());
		}catch(RelatorioException re){
			throw new AplicacaoException("especialista.RELATORIO_NAO_GERADO");
		}
	}
	
}
