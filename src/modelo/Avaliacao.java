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

	/**
	 * Indicação que está sendo avaliada em relação a determinado
	 * parâmetro.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	private Indicacao indicacao;
	
	/**
	 * Parâmetro que está sendo avaliado em relação a determinada
	 * indicação.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
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
	
	/**
	 * Módulo da diferença entre a necessidade do paciente e a média dos especialista
	 * para um parametro em particular de uma indicação.
	 * 
	 * @author bruno.oliveira dayse.arruda (comentários)
	 */
	private double distancia;
	
	/**
	 * 
	 * Construtor sem parametros que inicializa
	 * os atributos númericos como zerados.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public Avaliacao() {
		this.uniao = 0;
		this.intersecao = 0;
		this.mediaEspecialistas = 0;
	}

	/**
	 * 
	 * Construtor que inicializa cada
	 * atributo com parâmetros passados
	 * 
	 * @param indicacao - envolvida nesta avaliacao
	 * @param parametro - envolvido nesta avaliacao
	 * @param uniao - resultado da união entre a Necessidade do Paciente e
	 *  a Média dos Especialistas para esse cenário de Indicacao X Parametro
	 * @param intersecao - resultado da interseção entre a Necessidade do Paciente e
	 *  a Média dos Especialistas para esse cenário de Indicacao X Parametro
	 * @param mediaEspecialistas - Média das notas datas pelos especialistas para esse cenário de Indicação
	 * X Parâmetro
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	public Avaliacao(Indicacao indicacao, Parametro parametro,
			double uniao,
			double intersecao, 
			double mediaEspecialistas) {

		this.indicacao = indicacao;
		this.parametro = parametro;
		this.uniao = uniao;
		this.intersecao = intersecao;
		this.mediaEspecialistas = mediaEspecialistas;
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
