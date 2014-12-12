package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import br.blog.arruda.plot.opt.PlotOptions;
import exception.RelatorioException;
import relatorio.Relatorio;
import relatorio.RelatorioFactory;
import service.anotacao.Transacional;
import service.exception.AplicacaoException;
import util.jayflot.spider.SpiderMainPlot;
import util.jayflot.spider.data.SpiderPlotData;
import util.jayflot.spider.opt.SpiderPlotGrid;
import util.jayflot.spider.opt.SpiderPlotSeries;
import util.jayflot.spider.tipo.PlotSpider;
import util.jayflot.spider.tipo.SpiderConnection;
import util.jayflot.spider.tipo.SpiderHighlight;
import util.jayflot.spider.tipo.SpiderLeg;
import modelo.Anamnese;
import modelo.Atendimento;
import modelo.Avaliacao;
import modelo.ConjuntoAvaliacao;
import modelo.HistoricoAtendimentoRelatorio;
import modelo.HistoricoAvaliacao;
import modelo.Paciente;
import modelo.Usuario;
import DAO.AtendimentoDAO;
import DAO.HistoricoAvaliacaoDAO;
import DAO.Impl.AtendimentoDAOImpl;
import DAO.Impl.HistoricoAvaliacaoDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;
import actions.BaseActions;

public class HistoricoAvaliacaoAppService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static HistoricoAvaliacaoDAO historicoDAO;

	public HistoricoAvaliacaoAppService() throws Exception {
		try {
			historicoDAO = FabricaDeDao.getDao(HistoricoAvaliacaoDAOImpl.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Transacional
	public void inclui(Atendimento atendimento,List<ConjuntoAvaliacao> conjuntosAvaliacao) throws AplicacaoException {
		for(ConjuntoAvaliacao avaliacao : conjuntosAvaliacao){
			HistoricoAvaliacao	historico = new HistoricoAvaliacao();
			historico.setIndicacao(avaliacao.getIndicacao());
			historico.setAvaliacao(avaliacao.getResultadoDoAlgoritmo());
			historico.setRanking(avaliacao.getRanking());
			historico.setAtendimento(atendimento);
			historicoDAO.inclui(historico);
		}
		
	}
	
}
