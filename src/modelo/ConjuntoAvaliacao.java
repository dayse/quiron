package modelo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Essa é uma classe POJO com definição dos campos calculados usados nos algoritmos
 * de avaliação fuzzy e é responsável por guardar a memória de cálculo que será exibida na
 * tela de detalhamento.
 * 
 * Ela representa o relacionamento entre uma única indicação e o seu conjunto de parametros.
 * Esse conjunto de parametros é agrupado dentro de uma lista do tipo Avaliacao.
 * 
 * 
 * @author bruno.oliveira dayse.arruda (comentários)
 *
 */
public class ConjuntoAvaliacao implements Serializable, Comparable<ConjuntoAvaliacao>{


	private static final long serialVersionUID = 1L;

	/**
	 * O registro da indicação correspondente a esta avaliação.
	 */
	private Indicacao indicacao;
	
	/**
	 * Lista de avaliações para cada parametro relacionado a esta indicação.
	 */
	private List<Avaliacao> avaliacoes;

	/**
	 * Somatório dos valores de interseção de cada avaliacao para um
	 * cenário Parâmetro X Indicação.
	 */
	private double somatorioIntersecao;

	/**
	 * Somatório dos valores de união de cada avaliacao para um
	 * cenário Parâmetro X Indicação.
	 */
	private double somatorioUniao;

	/**
	 * Atributo que guarda o valor resultante do cálculo
	 * de grau de semelhança.
	 */
	private double grauSemelhanca;
	
	/**
	 * Somatório dos valores de distância de cada avaliação para
	 * um cenário Parametro X Indicação
	 */
	private double somatorioDistancia;
	
	/**
	 * Atributo que guarda o valor resultante do cálculo
	 * da distância de Descartes e Hamming.
	 */
	private double distanciaDescartes;
	
	/**
	 * Somatório dos valores de peso dos parâmetros.
	 */
	private double somatorioPesosParametros;
	
	/**
	 * Somatório dos valores da Necessidade do Paciente
	 */
	private double somatorioNecessidadeDoPaciente;
	
	/**
	 * Índice que representa a posição em ranking
	 *  da indicação avaliada.
	 */
	private int ranking;
	
	/**
	 * Atributo que contém o resultado final do algoritmo,
	 * independente do algoritmo utilizado para calcular a
	 * avaliação, o valor final é salvo aqui. Para que a tabela
	 * de ranking possa ser renderizada independente do algoritmo.
	 */
	private double resultadoDoAlgoritmo;
	
	/**
	 * Atributo que guarda o valor resultante do cálculo
	 * de grau de semelhança.
	 */
	private double grauInclusao;
	
	/**
	 * Construtor padrão
	 */
	public ConjuntoAvaliacao() {
	}

	/**
	 * 
	 * Nesse momento que cada interseção é multiplicada
	 * pelo peso do respectivo parametro.
	 * 
	 * @author bruno.oliveira dayse.arruda (Comentários)
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
	 * Nesse momento que cada união é multiplicada
	 * pelo peso do respectivo parametro.
	 * 
	 * @author bruno.oliveira dayse.arruda (Comentários)
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
	 * Nesse momento que cada módulo da distância é multiplicada
	 * pelo peso do respectivo parametro.
	 * 
	 * @author bruno.oliveira dayse.arruda (Comentários)
	 */
	public double somaParametrosDistancia(){
		double total = 0.0;
		for (Avaliacao avaliacao : this.avaliacoes) {
			total += avaliacao.getDistancia() * avaliacao.getParametro().getPeso();
		}
		return total;		
	}
	
	/**
	 * 
	 * Nesse momento que cada distância do grau de inclusão é multiplicada
	 * pelo peso do respectivo parametro.
	 * 
	 * @author bruno.oliveira dayse.arruda (Comentários)
	 */	
	public double somaDistanciaDoGrauDeInclusao(){
		double total = 0.0;
		for(Avaliacao avaliacao : this.avaliacoes){
			total += avaliacao.getDistancia() * avaliacao.getParametro().getPeso();
		}
		return total;
	}
	/**
	 * Método que retorna a soma dos pesos
	 * de todos os parâmetros.
	 * 
	 * @return total do somatório dos pesos
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public double somaPesosParametros(){
		double total = 0.0;
		for (Avaliacao avaliacao : this.avaliacoes) {
			total += avaliacao.getParametro().getPeso();
		}
		return total;
	}
	
	// ================================== Métodos get() e set() ================================ //

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
	 * Esse método será chamado pelo método sort() da classe Collections.
	 * Ele é o responsável por determinar a ordenação entre dois parametros
	 * da lista de parametros.
	 * 
	 * A forma como será ordenado depende de qual é o algoritmo ativo.
	 * 
	 * @author bruno.oliveira @dayse.arruda (Comentários)
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
