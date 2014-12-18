package modelo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Essa � uma classe POJO com defini��o dos campos calculados usados nos algoritmos
 * de avalia��o fuzzy e � respons�vel por guardar a mem�ria de c�lculo que ser� exibida na
 * tela de detalhamento.
 * 
 * Ela representa o relacionamento entre uma �nica indica��o e o seu conjunto de parametros.
 * Esse conjunto de parametros � agrupado dentro de uma lista do tipo Avaliacao.
 * 
 * 
 * @author bruno.oliveira dayse.arruda (coment�rios)
 *
 */
public class ConjuntoAvaliacao implements Serializable, Comparable<ConjuntoAvaliacao>{


	private static final long serialVersionUID = 1L;

	/**
	 * O registro da indica��o correspondente a esta avalia��o.
	 */
	private Indicacao indicacao;
	
	/**
	 * Lista de avalia��es para cada parametro relacionado a esta indica��o.
	 */
	private List<Avaliacao> avaliacoes;

	private double somatorioIntersecao;

	private double somatorioUniao;

	private double grauSemelhanca;
	
	private double somatorioDistancia;
	
	private double distanciaDescartes;
	
	private double somatorioPesosParametros;
	
	private double somatorioNecessidadeDoPaciente;
	
	private int ranking;
	
	private double resultadoDoAlgoritmo;
	
	private double grauInclusao;
	
	public ConjuntoAvaliacao() {
	}

	/**
	 * 
	 * Nesse momento que cada interse��o � multiplicada
	 * pelo peso do respectivo parametro.
	 * 
	 * @author bruno.oliveira dayse.arruda (Coment�rios)
	 */
	public double somaParametrosIntersecao(){
		double total = 0.0;
		for (Avaliacao avaliacao : this.avaliacoes) {
			total += avaliacao.getIntersecao() * avaliacao.getParametro().getPeso();
		}
		return total;		
	}
	
	/**
	 * 
	 * Nesse momento que cada uni�o � multiplicada
	 * pelo peso do respectivo parametro.
	 * 
	 * @author bruno.oliveira dayse.arruda (Coment�rios)
	 */	
	public double somaParametrosUniao(){
		double total = 0.0;
		for (Avaliacao avaliacao : this.avaliacoes) {
			total += avaliacao.getUniao() * avaliacao.getParametro().getPeso();
		}
		return total;		
	}

	/**
	 * 
	 * Nesse momento que cada m�dulo da dist�ncia � multiplicada
	 * pelo peso do respectivo parametro.
	 * 
	 * @author bruno.oliveira dayse.arruda (Coment�rios)
	 */
	public double somaParametrosDistancia(){
		double total = 0.0;
		for (Avaliacao avaliacao : this.avaliacoes) {
			total += avaliacao.getDistancia() * avaliacao.getParametro().getPeso();
		}
		return total;		
	}
	
	public double somaDistanciaDoGrauDeInclusao(){
		double total = 0.0;
		for(Avaliacao avaliacao : this.avaliacoes){
			total += avaliacao.getDistancia() * avaliacao.getParametro().getPeso();
		}
		return total;
	}
	
	public double somaPesosParametros(){
		double total = 0.0;
		for (Avaliacao avaliacao : this.avaliacoes) {
			total += avaliacao.getParametro().getPeso();
		}
		return total;
	}
	
	public double getResultadoDoAlgoritmo(){
		if(!(this.grauSemelhanca == 0.0)){
			return this.grauSemelhanca;
		}else if(!(this.distanciaDescartes == 0.0)){
			return this.distanciaDescartes;
		}else if(!(this.grauInclusao == 0.0)){
			return this.grauInclusao;
		}else{
			return 0.0;
		}
	}
	
	// ================================== M�todos get() e set()
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

	/**
	 * Esse m�todo ser� chamado pelo m�todo sort() da classe Collections.
	 * Ele � o respons�vel por determinar a ordena��o entre dois parametros
	 * da lista de parametros.
	 * 
	 * A forma como ser� ordenado depende de qual � o algoritmo ativo.
	 * 
	 * @author bruno.oliveira @dayse.arruda (Coment�rios)
	 */
	@Override
	public int compareTo(ConjuntoAvaliacao o) {
		if(!(this.grauSemelhanca == 0.0)){
			return this.getGrauSemelhanca() > o.getGrauSemelhanca() ? -1 : this.getGrauSemelhanca() < o.getGrauSemelhanca() ? 1 : 0;
		}else if(!(this.distanciaDescartes == 0.0)){
			return this.getDistanciaDescartes() < o.getDistanciaDescartes() ? -1 : this.getDistanciaDescartes() > o.getDistanciaDescartes() ? 1 : 0;
		}else if(!(this.grauInclusao == 0.0)){
			return this.getGrauInclusao() > o.getGrauInclusao() ? -1 : this.getGrauInclusao() < o.getGrauInclusao() ? 1 : 0;
		}else{
			return 0;
		}
	}


	public double getDistanciaDescartes() {
		return distanciaDescartes;
	}

	public void setDistanciaDescartes(double distanciaDescartes) {
		this.distanciaDescartes = distanciaDescartes;
	}


	public double getSomatorioDistancia() {
		return somatorioDistancia;
	}

	public void setSomatorioDistancia(double somatorioDistancia) {
		this.somatorioDistancia = somatorioDistancia;
	}

	public double getSomatorioPesosParametros() {
		return somatorioPesosParametros;
	}

	public void setSomatorioPesosParametros(double somatorioPesosParametros) {
		this.somatorioPesosParametros = somatorioPesosParametros;
	}

	public double getSomatorioNecessidadeDoPaciente() {
		return somatorioNecessidadeDoPaciente;
	}


	public void setSomatorioNecessidadeDoPaciente(
			double somatorioNecessidadeDoPaciente) {
		this.somatorioNecessidadeDoPaciente = somatorioNecessidadeDoPaciente;
	}

	public double getGrauInclusao() {
		return grauInclusao;
	}


	public void setGrauInclusao(double grauInclusao) {
		this.grauInclusao = grauInclusao;
	}
	

}
