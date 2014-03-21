package modelo;

public class RegraModelagemView {
	
	private Double valor;
	
	private int indice;
	
	
	public RegraModelagemView(){
		
	}


	// ================================== Métodos get() e set() ================================== //

	public Double getValor() {
		return valor;
	}


	public void setValor(Double valor) {
		this.valor = valor;
	}



	public int getIndice() {
		return indice;
	}


	public void setIndice(int indice) {
		this.indice = indice;
	}


	@Override
	public String toString() {
		return "Regra: " + indice;
	}
	
	
}