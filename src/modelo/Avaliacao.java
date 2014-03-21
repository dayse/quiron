package modelo;

public class Avaliacao {

	public Avaliacao() {
		this.indicacao = new Indicacao();
		this.febre = 0;
		this.disuria = 0;
		this.diabetes = 0;
		this.enterococos = 0;
		this.escherichia = 0;
		this.candida = 0;
		this.efeitosColaterais = 0;
		this.somatorio = 0;
		this.grauSemelhanca = 0;
	}

	public Avaliacao(Indicacao indicacao, Double febre,
			Double disuria, 
			Double diabetes,
			Double enterococos, 
			Double escherichia, 
			Double candida, 
			Double efeitosColaterais,  Double somatorio,
			Double grauSemelhanca) {
		this.indicacao = indicacao;
		this.febre = febre;
		this.disuria = disuria;
		this.diabetes = diabetes;
		this.enterococos = enterococos;
		this.escherichia = escherichia;
		this.candida = candida;
		this.efeitosColaterais = efeitosColaterais;
		this.somatorio = somatorio;
		this.grauSemelhanca = grauSemelhanca;		
	}

	private Indicacao indicacao;

	private double febre;

	private double disuria;

	private double diabetes;

	private double enterococos;

	private double escherichia;

	private double candida;

	private double efeitosColaterais;

	private double somatorio;

	private double grauSemelhanca;
	
	public double somaParametros(){
		return febre + disuria + diabetes + enterococos + escherichia
				+ candida + efeitosColaterais;		
	}
	
	// ================================== Métodos get() e set()
	// ================================== //

	public double getFebre() {
		return febre;
	}

	public void setFebre(double febre) {
		this.febre = febre;
	}

	public double getDisuria() {
		return disuria;
	}

	public void setDisuria(double disuria) {
		this.disuria = disuria;
	}

	public double getDiabetes() {
		return diabetes;
	}

	public void setDiabetes(double diabetes) {
		this.diabetes = diabetes;
	}

	public double getEnterococos() {
		return enterococos;
	}

	public void setEnterococos(double enterococos) {
		this.enterococos = enterococos;
	}

	public double getEscherichia() {
		return escherichia;
	}

	public void setEscherichia(double escherichia) {
		this.escherichia = escherichia;
	}

	public double getCandida() {
		return candida;
	}

	public void setCandida(double candida) {
		this.candida = candida;
	}

	public double getEfeitosColaterais() {
		return efeitosColaterais;
	}

	public void setEfeitosColaterais(
			double efeitosColaterais) {
		this.efeitosColaterais = efeitosColaterais;
	}

	public Indicacao getIndicacao() {
		return indicacao;
	}

	public void setIndicacao(Indicacao indicacao) {
		this.indicacao = indicacao;
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
