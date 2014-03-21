package modelo;

public class VariavelModelagemView {
	
	private Double valor;
	
	private String nome;
	
	
	public VariavelModelagemView(){
		
	}


	// ================================== Métodos get() e set() ================================== //

	public Double getValor() {
		return valor;
	}


	public void setValor(Double valor) {
		this.valor = valor;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	@Override
	public String toString() {
		return "Variavel: " + nome;
	}
	
	
}