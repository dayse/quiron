package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Anamnese;
import modelo.Atendimento;
import modelo.AvalIndicacaoEspec;
import modelo.Avaliacao;
import modelo.Indicacao;
import service.anotacao.Transacional;
import service.exception.AplicacaoException;
import DAO.AnamneseDAO;
import DAO.AvalIndicacaoEspecDAO;
import DAO.EspecialistaDAO;
import DAO.IndicacaoDAO;
import DAO.Impl.AnamneseDAOImpl;
import DAO.Impl.AvalIndicacaoEspecDAOImpl;
import DAO.Impl.EspecialistaDAOImpl;
import DAO.Impl.IndicacaoDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;

public class AnamneseAppService {

	private static AnamneseDAO anamneseDAO;
	private static AvalIndicacaoEspecDAO avalIndicacaoEspecDAO;
	private static IndicacaoDAO indicacaoDAO;

	public AnamneseAppService() throws Exception {
		try {
			anamneseDAO = FabricaDeDao.getDao(AnamneseDAOImpl.class);
			avalIndicacaoEspecDAO = FabricaDeDao
					.getDao(AvalIndicacaoEspecDAOImpl.class);
			indicacaoDAO = FabricaDeDao.getDao(IndicacaoDAOImpl.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Transacional
	public void altera(Anamnese anamnese) throws AplicacaoException {
		anamneseDAO.altera(anamnese);
	}

	public List<Avaliacao> recuperaAvaliacaoCalculadaPorIndicacao(
			Atendimento atendimento) {

		Anamnese anamnese = new Anamnese();
		try {
			anamnese = anamneseDAO.recuperaAnamnesePorAtendimento(atendimento);
		} catch (ObjetoNaoEncontradoException e) {
		}
		
		List<Indicacao> listIndicacao = indicacaoDAO.recuperaListaIndicacao();

		List<Avaliacao> listAvaliacao = new ArrayList<Avaliacao>();

		for (Indicacao indicacao : listIndicacao) {
			

			List<AvalIndicacaoEspec> listAvalIndicacaoEspec = avalIndicacaoEspecDAO
					.recuperaListaDeAvaliacaoEspecPorIndicacao(indicacao);
			
			Double febre = 0.0;
			Double diabetes = 0.0;
			Double disuria = 0.0;
			Double enterococos = 0.0;
			Double escherichia = 0.0;
			Double candida = 0.0;
			Double efeitosColaterais = 0.0; 
			Avaliacao avaliacaoIntersecao = new Avaliacao();
			Avaliacao avaliacaoUniao = new Avaliacao();
			Double mediaPesoAvaliador = 0.0;

			for (AvalIndicacaoEspec avalIndicacaoEspec : listAvalIndicacaoEspec) {
				
				mediaPesoAvaliador += avalIndicacaoEspec.getEspecialista()
						.getPesoAvaliador();
				
				febre = febre + (avalIndicacaoEspec.getFebre() * avalIndicacaoEspec.getEspecialista().getPesoAvaliador());
				diabetes = diabetes + (avalIndicacaoEspec.getDiabetes() * avalIndicacaoEspec.getEspecialista().getPesoAvaliador());
				disuria = disuria + (avalIndicacaoEspec.getDisuria() * avalIndicacaoEspec.getEspecialista().getPesoAvaliador());
				enterococos = enterococos + (avalIndicacaoEspec.getEnterococos() * avalIndicacaoEspec.getEspecialista().getPesoAvaliador());
				escherichia = escherichia + (avalIndicacaoEspec.getEscherichia() * avalIndicacaoEspec.getEspecialista().getPesoAvaliador());
				candida = candida + (avalIndicacaoEspec.getCandida() * avalIndicacaoEspec.getEspecialista().getPesoAvaliador());
				efeitosColaterais = efeitosColaterais + (avalIndicacaoEspec.getEfeitosColaterais() * avalIndicacaoEspec.getEspecialista().getPesoAvaliador());
	
			}
			
			febre = febre / mediaPesoAvaliador;
			diabetes = diabetes / mediaPesoAvaliador;
			disuria = disuria / mediaPesoAvaliador;
			enterococos = enterococos / mediaPesoAvaliador;
			escherichia = escherichia / mediaPesoAvaliador;
			candida = candida / mediaPesoAvaliador;
			efeitosColaterais = efeitosColaterais / mediaPesoAvaliador;
			
			avaliacaoIntersecao.setIndicacao(indicacao);
			avaliacaoUniao.setIndicacao(indicacao);
			
			avaliacaoIntersecao.setFebre(Math.min(febre, anamnese.getFebre()));
			avaliacaoUniao.setFebre(Math.max(febre, anamnese.getFebre()));
			
			avaliacaoIntersecao.setDiabetes(Math.min(diabetes, anamnese.getDiabetes()));
			avaliacaoUniao.setDiabetes(Math.max(diabetes, anamnese.getDiabetes()));			

			avaliacaoIntersecao.setDisuria(Math.min(disuria, anamnese.getDisuria()));
			avaliacaoUniao.setDisuria(Math.max(disuria, anamnese.getDisuria()));
			
			avaliacaoIntersecao.setEnterococos(Math.min(enterococos, anamnese.getEnterococos()));
			avaliacaoUniao.setEnterococos(Math.max(enterococos, anamnese.getEnterococos()));
			
			avaliacaoIntersecao.setEscherichia(Math.min(escherichia, anamnese.getEscherichia()));
			avaliacaoUniao.setEscherichia(Math.max(escherichia, anamnese.getEscherichia()));
			
			avaliacaoIntersecao.setCandida(Math.min(candida, anamnese.getCandida()));
			avaliacaoUniao.setCandida(Math.max(candida, anamnese.getCandida()));		
			
			avaliacaoIntersecao.setEfeitosColaterais(Math.min(efeitosColaterais, anamnese.getEfeitosColaterais()));
			avaliacaoUniao.setEfeitosColaterais(Math.max(efeitosColaterais, anamnese.getEfeitosColaterais()));			
			
			avaliacaoIntersecao.setSomatorio(avaliacaoIntersecao.somaParametros());
			avaliacaoUniao.setSomatorio(avaliacaoUniao.somaParametros());
			
			avaliacaoIntersecao.setGrauSemelhanca(avaliacaoIntersecao.getSomatorio() / avaliacaoUniao.getSomatorio());
			avaliacaoUniao.setGrauSemelhanca(avaliacaoIntersecao.getSomatorio() / avaliacaoUniao.getSomatorio());
			
			listAvaliacao.add(avaliacaoIntersecao);
			listAvaliacao.add(avaliacaoUniao);
		}

		return listAvaliacao;
	}

	@Transacional
	public void exclui(Anamnese anamnese) throws AplicacaoException {
		Anamnese anamneseBD = null;
		try {
			anamneseBD = anamneseDAO.getPorIdComLock(anamnese.getId());
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("anamnese.NAO_ENCONTRADA");
		}
		anamneseDAO.exclui(anamneseBD);
	}

	@Transacional
	public void inclui(Anamnese anamnese) throws AplicacaoException {
		try {
			anamneseDAO.recuperaAnamnesePorAtendimento(anamnese
					.getAtendimento());
			throw new AplicacaoException("anamnese.ATENDIMENTO_POSSUI_ANAMNESE");
		} catch (ObjetoNaoEncontradoException ob) {
		}
		anamneseDAO.inclui(anamnese);
	}

	public Anamnese recuperaAnamnesePorAtendimento(Atendimento atendimento)
			throws ObjetoNaoEncontradoException {
		return anamneseDAO.recuperaAnamnesePorAtendimento(atendimento);
	}
}
