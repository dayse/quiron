package modelo;

public class Avaliacao {

	private Indicacao indicacao;
	private Parametro parametro;

	private double uniao;

	private double intersecao;

	private double somatorio;

	private double grauSemelhanca;
	
	private double mediaEspecialistas;
	
	private String nomeIndicacaoNaTabela;
	
	public Avaliacao() {
		this.uniao = 0;
		this.intersecao = 0;
		this.somatorio = 0;
		this.grauSemelhanca = 0;
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
		this.somatorio = somatorio;
		this.grauSemelhanca = grauSemelhanca;		
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
}
