package modelo;

import java.util.Date;

public class PacienteRelatorio implements Comparable<PacienteRelatorio> {

	/**
	 * Código do paciente.
	 */
	private String codPaciente;
	
	/**
	 * Nome do paciente.
	 */
	private String nome;
	
	/**
	 * Endereço do paciente.
	 */
	private String endereco;
	
	/**
	 * Sexo do paciente, convertido de Boolean para String.
	 */
	private String sexo;
	
	/**
	 * Data de nascimento do paciente, convertido de Calendar para Date.
	 */
	private Date dataNascimento;
	
	/**
	 * Documento do paciente ou do responsável dele.
	 */
	private String documento;
	
	/**
	 * Nome do responsavel pelo paciente, caso haja.
	 */
	private String nomeResponsavel;
	
	// ********* Construtor *********
	
	public PacienteRelatorio(Paciente paciente){
		this.codPaciente = paciente.getCodPaciente();
		this.nome = paciente.getNome();
		this.endereco = paciente.getEndereco();
		
		if(paciente.getSexo()){
			this.sexo = "Masculino";
		}else{
			this.sexo = "Feminino";
		}
		
		this.dataNascimento = (paciente.getDataNascimento()).getTime();
		this.documento = paciente.getDocumento();
		this.nomeResponsavel = paciente.getNomeResponsavel();
	}
	
	// ********* Métodos get/set *********

	public String getCodPaciente() {
		return codPaciente;
	}

	public void setCodPaciente(String codPaciente) {
		this.codPaciente = codPaciente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	/**
	 * Esse método consiste em definir o critério de orientação entre 2 objetos Paciente, que deve estar
	 * associado a um de seus atributos. Neste caso, o atributo em questão é "codPaciente", que é do tipo
	 * nativo 'String', que sabe internamente se auto-ordenar, graças a implementação da API Java que o realiza.
	 * 
	 * @param - O objeto paciente que será comparado.
	 * @return - Se as duas Strings forem iguais o método retorna 1, senão retorna o resultado da comparação que representa a diferença entre as Strings.
	 *
	 * @author bruno.oliveira
	 */	
	@Override
	public int compareTo(PacienteRelatorio pr) {
		int valor = codPaciente.compareTo(pr.codPaciente);
		return (valor != 0 ? valor : 1);
	}
	
}
