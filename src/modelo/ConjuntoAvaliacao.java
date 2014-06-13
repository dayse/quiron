package modelo;

import java.util.List;

public class ConjuntoAvaliacao {


	
	private Indicacao indicacao;
	
	private List<Avaliacao> avaliacoes;

	private double somatorioIntersecao;

	private double somatorioUniao;

	private double grauSemelhanca;
	
	
	public ConjuntoAvaliacao() {
	}


	public double somaParametrosIntersecao(){
		double total = 0.0;
		for (Avaliacao avaliacao : this.avaliacoes) {
			total += avaliacao.getIntersecao();
		}
		return total;		
	}
	public double somaParametrosUniao(){
		double total = 0.0;
		for (Avaliacao avaliacao : this.avaliacoes) {
			total += avaliacao.getUniao();
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


	

}
