package modelo;

/**
 * 
 * Essa é uma classe POJO com definição dos campos calculados usados nos algoritmos
 * de avaliação fuzzy.
 * 
 * Ela representa o relacionamento entre uma única indicação e um único parametro. E é utilizada na tela
 * em que temos colunas dinâmicas para recuperar os valores dessa relação.
 * 
 * A maior parte dos cálculos é efetuada dentro de anamneseService.
 * 
 * @author bruno.oliveira dayse.arruda (comentários)
 *
 */
public class Avaliacao {

	private Indicacao indicacao;
	private Parametro parametro;

	/**
	 * União entre a necessidade do paciente e a média dos especialista
	 * para um parametro em particular de uma indicação.
	 * 
	 * @author bruno.oliveira dayse.arruda (comentários)
	 */
	private double uniao;

	/**
	 * Interseção entre a necessidade do paciente e a média dos especialista
	 * para um parametro em particular de uma indicação.
	 * 
	 * @author bruno.oliveira dayse.arruda (comentários)
	 */
	private double intersecao;
	
	/**
	 * Média dos especialistas para um parametro em particular de uma indicação.
	 * 
	 * @author bruno.oliveira dayse.arruda (comentários)
	 */
	private double mediaEspecialistas;
	
	private String nomeIndicacaoNaTabela;
	
	/**
	 * Módulo da diferença entre a necessidade do paciente e a média dos especialista
	 * para um parametro em particular de uma indicação.
	 * 
	 * @author bruno.oliveira dayse.arruda (comentários)
	 */
	private double distancia;
	
	public Avaliacao() {
		this.uniao = 0;
		this.intersecao = 0;
		this.mediaEspecialistas = 0;
	}

	public Avaliacao(Indicacao indicacao, Parametro parametro,
			double uniao,
			double intersecao, 
			double somatorio,
			double grauSemelhanca,
			double mediaEspecialistas) {

		this.indicacao = indicacao;
		this.parametro = parametro;
		this.uniao = uniao;
		this.intersecao = intersecao;
		this.mediaEspecialistas = mediaEspecialistas;
	}

	public double somaParametros(){
		return 0;		
	}
	
	// ================================== Métodos get() e set()
	// ================================== //

	public Indicacao getIndicacao() {
		return indicacao;
	}

	public void setIndicacao(Indicacao indicacao) {
		this.indicacao = indicacao;
	}

	public double getUniao() {
		return uniao;
	}

	public void setUniao(double uniao) {
		this.uniao = uniao;
	}

	public double getIntersecao() {
		return intersecao;
	}

	public void setIntersecao(double intersecao) {
		this.intersecao = intersecao;
	}

	
	public String getNomeIndicacaoNaTabela() {
		return nomeIndicacaoNaTabela;
	}

	public void setNomeIndicacaoNaTabela(String nomeIndicacaoNaTabela) {
		this.nomeIndicacaoNaTabela = nomeIndicacaoNaTabela;
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public double getMediaEspecialistas() {
		return mediaEspecialistas;
	}

	public void setMediaEspecialistas(double mediaEspecialistas) {
		this.mediaEspecialistas = mediaEspecialistas;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	
}
