package modelo;

import java.util.List;

public class ConjuntoAvaliacao {


	
	private Indicacao indicacao;
	
	private List<Avaliacao> avaliacoes;
	
	private double somatorio;

	private double grauSemelhanca;
	
	
	public ConjuntoAvaliacao() {
		this.somatorio = 0;
		this.grauSemelhanca = 0;
	}

	public ConjuntoAvaliacao(Indicacao indicacao,
			List<Avaliacao> avaliacoes,
			double somatorio,
			double grauSemelhanca) {
		
		this.indicacao = indicacao;
		this.avaliacoes = avaliacoes;
		this.somatorio = somatorio;
		this.grauSemelhanca = grauSemelhanca;		
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

	public double getSomatorio() {
		return somatorio;
	}

	public void setSomatorio(double somatorio) {
		this.somatorio = somatorio;
	}

	public double getGrauSemelhanca() {
		return grauSemelhanca;
	}

	public void setGrauSemelhanca(double grauSemelhanca) {
		this.grauSemelhanca = grauSemelhanca;
	}

	

}
