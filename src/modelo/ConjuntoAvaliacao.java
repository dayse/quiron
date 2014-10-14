package modelo;

import java.io.Serializable;
import java.util.List;

public class ConjuntoAvaliacao implements Serializable, Comparable<ConjuntoAvaliacao>{


	private static final long serialVersionUID = 1L;

	private Indicacao indicacao;
	
	private List<Avaliacao> avaliacoes;

	private double somatorioIntersecao;

	private double somatorioUniao;

	private double grauSemelhanca;
	
	private int ranking;
	
	
	public ConjuntoAvaliacao() {
	}


	public double somaParametrosIntersecao(){
		double total = 0.0;
		for (Avaliacao avaliacao : this.avaliacoes) {
			total += avaliacao.getIntersecao() * avaliacao.getParametro().getPeso();
		}
		return total;		
	}
	public double somaParametrosUniao(){
		double total = 0.0;
		for (Avaliacao avaliacao : this.avaliacoes) {
			total += avaliacao.getUniao() * avaliacao.getParametro().getPeso();
		}
		return total;		
	}
	
	// ================================== Métodos get() e set()
	// ================================== //


	public Indicacao getIndicacao() {
		return indicacao;
	}

	public void setIndicacao(Indicacao indicacao) {
		this.indicacao = indicacao;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public double getSomatorioIntersecao() {
		return somatorioIntersecao;
	}

	public void setSomatorioIntersecao(double somatorioIntersecao) {
		this.somatorioIntersecao = somatorioIntersecao;
	}


	public double getGrauSemelhanca() {
		return grauSemelhanca;
	}


	public void setGrauSemelhanca(double grauSemelhanca) {
		this.grauSemelhanca = grauSemelhanca;
	}


	public double getSomatorioUniao() {
		return somatorioUniao;
	}


	public void setSomatorioUniao(double somatorioUniao) {
		this.somatorioUniao = somatorioUniao;
	}


	public int getRanking() {
		return ranking;
	}


	public void setRanking(int ranking) {
		this.ranking = ranking;
	}


	@Override
	public int compareTo(ConjuntoAvaliacao o) {
		return this.getGrauSemelhanca() > o.getGrauSemelhanca() ? -1 : this.getGrauSemelhanca() < o.getGrauSemelhanca() ? 1 : 0;
	}


	

}
