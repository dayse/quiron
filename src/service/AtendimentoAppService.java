package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import exception.RelatorioException;
import relatorio.Relatorio;
import relatorio.RelatorioFactory;
import service.anotacao.Transacional;
import service.exception.AplicacaoException;
import util.jayflot.spider.SpiderMainPlot;
import util.jayflot.spider.data.SpiderPlotData;
import util.jayflot.spider.tipo.PlotSpider;
import modelo.Anamnese;
import modelo.Atendimento;
import modelo.Avaliacao;
import modelo.ConjuntoAvaliacao;
import modelo.HistoricoAtendimentoRelatorio;
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

	public AtendimentoAppService() throws Exception {
		try {
			atendimentoDAO = FabricaDeDao.getDao(AtendimentoDAOImpl.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Transacional
	public void altera(Atendimento atendimento) throws AplicacaoException {
		atendimentoDAO.altera(atendimento);
	}
	
	/**
	 * Gera Valores de PlotData para o grafico de Grau de 
	 * Semelhança, no caso esse metodo é para gerar a Necessidade do Paciente.
	 * @param atendimento
	 * @return
	 */
	public ArrayList<Double[]> gerarValoresDeDataDeNecessidadeDoPacienteParaGraficoGrauDeSemelhanca(Atendimento atendimento){

		int num_parametros = atendimento.getAnamneses().size();
		
		ArrayList<Double[]> valoresData = new ArrayList<Double[]>();
		for (int i = 0; i < num_parametros; i++) {
			Anamnese anamnese = atendimento.getAnamneses().get(i);
			ArrayList<Double> valor_temp = new ArrayList<Double>();
			valor_temp.add((double)(i));
			valor_temp.add(anamnese.getValor());
			valoresData.add((Double[])valor_temp.toArray());
		}
		return valoresData;
	}

	/**
	 * Gera Valores de PlotData para o grafico de Grau de 
	 * Semelhança, no caso esse metodo é para alguma media de avaliação de indicação que será
	 * comparada com a necessidade do paciente.
	 * @param atendimento
	 * @return
	 */
	public ArrayList<Double[]> gerarValoresDeDataDeAvaliacaoDeIndicacaoParaGraficoGrauDeSemelhanca(ConjuntoAvaliacao conjuntoAvaliacao){

		int num_parametros = conjuntoAvaliacao.getAvaliacoes().size();
		
		ArrayList<Double[]> valoresData = new ArrayList<Double[]>();
		for (int i = 0; i < num_parametros; i++) {
			
			Avaliacao avaliacao = conjuntoAvaliacao.getAvaliacoes().get(i);
			ArrayList<Double> valor_temp = new ArrayList<Double>();
			valor_temp.add((double)(i));
			valor_temp.add(avaliacao.getMediaEspecialistas());
			valoresData.add((Double[])valor_temp.toArray());
		}
		return valoresData;
	}
	
	
	/**
	 * Método que vai gerar o grafico (SpiderMainPlot) para o algoritmo
	 * de Grau de Semelhança de uma determinada Avaliação de Indicação de um Atendimento.
	 */
	public SpiderMainPlot geraGraficoGrauDeSemelhancaParaAvaliacaoDeIndicacaoDeAtendimento
							(List<ConjuntoAvaliacao> conjuntosDeAvaliacoes, Atendimento atendimento){
		
		int num_parametros = atendimento.getAnamneses().size();
		
		
		// PLOT DATA //
		//inicializa o conjunto de datas
		ArrayList<SpiderPlotData> plotDatas = new ArrayList<SpiderPlotData>();
		
		//Necessidade do Paciente
		SpiderPlotData necessidadeDoPacienteData = new SpiderPlotData();
		
		necessidadeDoPacienteData.setData(
						gerarValoresDeDataDeNecessidadeDoPacienteParaGraficoGrauDeSemelhanca(atendimento)
						);
		necessidadeDoPacienteData.setLabel("Necessidade do Paciente");
		necessidadeDoPacienteData.setSpider(new PlotSpider(true, true));
		plotDatas.add(necessidadeDoPacienteData);
		
		for (ConjuntoAvaliacao conjuntoAvaliacao : conjuntosDeAvaliacoes) {
			SpiderPlotData mediaIndicacaoData = new SpiderPlotData();
			mediaIndicacaoData.setData(
							gerarValoresDeDataDeAvaliacaoDeIndicacaoParaGraficoGrauDeSemelhanca(conjuntoAvaliacao)
							);
			mediaIndicacaoData.setLabel(conjuntoAvaliacao.getIndicacao().toString());
			mediaIndicacaoData.setSpider(new PlotSpider(true, null));	
			plotDatas.add(mediaIndicacaoData);		
		}
		
		SpiderMainPlot spiderPlot = new SpiderMainPlot();
		return spiderPlot;
	}
	
	public List<HistoricoAtendimentoRelatorio> converterParaHistoricoAtendimentoRelatorio(List<Atendimento> listaAtendimentos){
		List<HistoricoAtendimentoRelatorio> historicoAtendimentos = new LinkedList<HistoricoAtendimentoRelatorio>();
		Collections.sort(listaAtendimentos);
		for(Atendimento atendimento : listaAtendimentos){
			List<Anamnese> anamneses = atendimento.getAnamneses();
			for(Anamnese anamnese : anamneses){
				HistoricoAtendimentoRelatorio historico =  new HistoricoAtendimentoRelatorio(atendimento, anamnese);
				historicoAtendimentos.add(historico);
			}
		}
		return historicoAtendimentos;
	}

	@Transacional
	public void exclui(Atendimento atendimento) throws AplicacaoException {
		Atendimento atendimentoBD = null;
		try {
			atendimentoBD = atendimentoDAO.getPorIdComLock(atendimento.getId());
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("atendimento.NAO_ENCONTRADO");
		}
		atendimentoDAO.exclui(atendimentoBD);
	}

	public void gerarRelatorio(Atendimento atendimento)
			throws AplicacaoException {
		System.out
				.println("Antes do metodo getRelatorio dentro de gerarRelatorio de ParametroAppService");

		Relatorio relatorio = RelatorioFactory
				.getRelatorio(Relatorio.RELATORIO_ATENDIMENTO_ESPECIFICO_PACIENTE);

		if (relatorio != null)

			System.out
					.println("A variavel do tipo Relatorio é diferente de null em AtendimentoAppService");

		System.out
				.println("Depois do metodo getRelatorio dentro de gerarRelatorio de AtendimentoAppService");

		List<Atendimento> atendimentoRelatorio = new ArrayList<Atendimento>();
		atendimentoRelatorio.add(atendimento);

		try {
			relatorio.gerarRelatorio(this.converterParaHistoricoAtendimentoRelatorio(atendimentoRelatorio), new HashMap());
		} catch (RelatorioException re) {
			throw new AplicacaoException("atendimento.Relatorio_NAO_GERADO");
		}
	}
	
	public void gerarRelatorioHistorico(List<Atendimento> listaAtendimentos)
			throws AplicacaoException {
		System.out
				.println("Antes do metodo getRelatorio dentro de gerarRelatorio de AtendimentoAppService");

		Relatorio relatorio = RelatorioFactory
				.getRelatorio(Relatorio.RELATORIO_HISTORICO_ATENDIMENTOS_PACIENTE);

		if (relatorio != null)

			System.out
					.println("A variavel do tipo Relatorio é diferente de null em AtendimentoAppService");

		System.out
				.println("Depois do metodo getRelatorio dentro de gerarRelatorio de AtendimentoAppService");

		try {
			relatorio.gerarRelatorio(this.converterParaHistoricoAtendimentoRelatorio(listaAtendimentos), new HashMap());
		} catch (RelatorioException re) {
			throw new AplicacaoException("atendimento.Relatorio_NAO_GERADO");
		} 
	}	

	@Transacional
	public void inclui(Atendimento atendimento) throws AplicacaoException {
		try {
			atendimentoDAO.recuperaAtendimentoPorCodigo(atendimento
					.getCodAtendimento());
			throw new AplicacaoException("atendimento.CODIGO_EXISTENTE");
		} catch (ObjetoNaoEncontradoException ob) {
		}
		atendimentoDAO.inclui(atendimento);
	}

	public Atendimento recuperaAtendimentoPorCodigo(String codAtendimento)
			throws AplicacaoException {
		Atendimento atendimentoBD = null;
		try {
			atendimentoBD = atendimentoDAO
					.recuperaAtendimentoPorCodigo(codAtendimento);
		} catch (ObjetoNaoEncontradoException exc) {
			throw new AplicacaoException("atendimento.NAO_ENCONTRADO");
		}
		return atendimentoBD;
	}

	public Atendimento recuperaAtendimentoPorCodigoComPaciente(
			String codAtendimento) throws AplicacaoException {
		Atendimento atendimentoBD = null;
		try {
			atendimentoBD = atendimentoDAO
					.recuperaAtendimentoPorCodigoComPaciente(codAtendimento);
		} catch (ObjetoNaoEncontradoException exc) {
			throw new AplicacaoException("atendimento.NAO_ENCONTRADO");
		}
		return atendimentoBD;
	}

	public List<Atendimento> recuperaListaDeAtendimentosPaginada() {
		return atendimentoDAO.recuperaListaDeAtendimentosPaginada();
	}

	public List<Atendimento> recuperaListaDeAtendimentosComPacientePaginada() {
		return atendimentoDAO.recuperaListaDeAtendimentosComPacientePaginada();
	}

	public List<Atendimento> recuperaListaPaginadaDeAtendimentosPorPacientePorCodigoLike(
			String codPaciente) {
		return atendimentoDAO
				.recuperaListaPaginadaDeAtendimentosPorPacientePorCodigoLike(codPaciente);
	}

	public List<Atendimento> recuperaListaPaginadaDeAtendimentosPorPacientePorNome(
			String nome) {
		return atendimentoDAO
				.recuperaListaPaginadaDeAtendimentosPorPacientePorNome(nome);
	}

	public Atendimento recuperaUltimoAtendimento() {
		return atendimentoDAO.recuperaUltimoAtendimento();
	}

	public List<Atendimento> recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLike(
			String nomeMedico) {
		return atendimentoDAO
				.recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLike(nomeMedico);
	}

	public List<Atendimento> recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLike(
			String nomePaciente) {
		return atendimentoDAO
				.recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLike(nomePaciente);
	}

	public List<Atendimento> recuperaListaPaginadaDeAtendimentosComPacientePorCodigoPaciente(
			String codPaciente) {
		return atendimentoDAO
				.recuperaListaPaginadaDeAtendimentosComPacientePorCodigoPaciente(codPaciente);
	}

	public List<Atendimento> recuperaListaPaginadaDeAtendimentosComPacienteComAnamnesePorCodigoPaciente(
			String codPaciente) {
		return atendimentoDAO
				.recuperaListaPaginadaDeAtendimentosComPacienteComAnamnesePorCodigoPaciente(codPaciente);
	}

	public List<Atendimento> recuperaListaDeAtendimentosComPacienteComAnamnesePorCodigoPaciente(
			String codPaciente) {
		return atendimentoDAO
				.recuperaListaDeAtendimentosComPacienteComAnamnesePorCodigoPaciente(codPaciente);
	}	
	
}
