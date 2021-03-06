package modelo;

/**
 * 
 * Essa � uma classe POJO com defini��o dos campos calculados usados nos algoritmos
 * de avalia��o fuzzy.
 * 
 * Ela representa o relacionamento entre uma �nica indica��o e um �nico parametro. E � utilizada na tela
 * em que temos colunas din�micas para recuperar os valores dessa rela��o.
 * 
 * A maior parte dos c�lculos � efetuada dentro de anamneseService.
 * 
 * @author bruno.oliveira dayse.arruda (coment�rios)
 *
 */
public class Avaliacao {

	/**
	 * Indica��o que est� sendo avaliada em rela��o a determinado
	 * par�metro.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	private Indicacao indicacao;
	
	/**
	 * Par�metro que est� sendo avaliado em rela��o a determinada
	 * indica��o.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	private Parametro parametro;

	/**
	 * Uni�o entre a necessidade do paciente e a m�dia dos especialista
	 * para um parametro em particular de uma indica��o.
	 * 
	 * @author bruno.oliveira dayse.arruda (coment�rios)
	 */
	private double uniao;

	/**
	 * Interse��o entre a necessidade do paciente e a m�dia dos especialista
	 * para um parametro em particular de uma indica��o.
	 * 
	 * @author bruno.oliveira dayse.arruda (coment�rios)
	 */
	private double intersecao;
	
	/**
	 * M�dia dos especialistas para um parametro em particular de uma indica��o.
	 * 
	 * @author bruno.oliveira dayse.arruda (coment�rios)
	 */
	private double mediaEspecialistas;
	
	/**
	 * M�dulo da diferen�a entre a necessidade do paciente e a m�dia dos especialista
	 * para um parametro em particular de uma indica��o.
	 * 
	 * @author bruno.oliveira dayse.arruda (coment�rios)
	 */
	private double distancia;
	
	/**
	 * 
	 * Construtor sem parametros que inicializa
	 * os atributos n�mericos como zerados.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
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
	 * atributo com par�metros passados
	 * 
	 * @param indicacao - envolvida nesta avaliacao
	 * @param parametro - envolvido nesta avaliacao
	 * @param uniao - resultado da uni�o entre a Necessidade do Paciente e
	 *  a M�dia dos Especialistas para esse cen�rio de Indicacao X Parametro
	 * @param intersecao - resultado da interse��o entre a Necessidade do Paciente e
	 *  a M�dia dos Especialistas para esse cen�rio de Indicacao X Parametro
	 * @param mediaEspecialistas - M�dia das notas datas pelos especialistas para esse cen�rio de Indica��o
	 * X Par�metro
	 * 
	 * @author bruno.oliveira (Atualiza��o)
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

	// ================================== M�todos get() e set()
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
