package modelo;

import java.util.Date;
import java.util.List;

/**
 * 
 * Essa � uma classe POJO que n�o � persistida no banco de dados.
 * Ela existe para preparar entidades do tipo HistoricoAvaliacao em
 * um formato acess�vel para preenchimento de um relatorio PDF.
 * 
 * @author bruno.oliveira
 *
 */
public class HistoricoAtendimentoRelatorio implements
		Comparable<HistoricoAtendimentoRelatorio> {

	/**
	 * C�digo do Paciente relacionado
	 * a esse hist�rico de atendimento.
	 */
	private String codPaciente;

	/**
	 * Nome do paciente
	 */
	private String nome;

	/**
	 * Data de nascimento do paciente
	 */
	private Date dataNascimento;

	/**
	 * Sexo do paciente
	 */
	private String sexo;

	/** 
	 * Endere�o do paciente
	 */
	private String endereco;

	/**
	 * Nome do respons�vel do paciente,
	 * caso este seja menor de idade.
	 */
	private String nomeResponsavel;

	/**
	 * Documento de identifica��o do paciente
	 * ou do seu respons�vel.
	 */
	private String documento;

	/**
	 * Informa��es gerais a respeito do paciente
	 * que podem ser de interesse do m�dico.
	 */
	private String informacoesGerais;
	
	/**
	 * Id do atendimento
	 */
	private Long idAtendimento;

	/**
	 * C�digo do atendimento
	 */
	private String codAtendimento;

	/**
	 * Data e hora do atendimento
	 */
	private Date dataAtendimento;

	/**
	 * Nome do m�dico
	 */
	private String medico;

	/**
	 * Nome do t�cnico/enfermeiro
	 */
	private String tecnico;

	/**
	 * Status do atendimento
	 */
	private String status;

	/**
	 * Precri��o determinada pelo m�dico,
	 * caso exista.
	 */
	private String prescricao;

	/**
	 * Diagn�stico dado pelo m�dico.
	 */
	private String diagnostico;

	/**
	 * Observa��es gerais que podem ser
	 * interesse do m�dico guardar.
	 */
	private String observacoes;
	
	/**
	 * Nome de um Par�metro
	 */
	private String nomeParametro;
	
	/**
	 * Valor do Par�metro
	 */
	private Double valorParametro;

	/** 
	 * Lista com o resultado da anamnese
	 * realizada pelo m�dico.
	 */
	private List<Anamnese> anamneses;
	
	/** 
	 * 
	 * Construtor que recebe os objetos que cont�m as informa��es
	 * necess�rias para o modelo de relat�rio de Hist�rico de
	 * Atendimento.
	 * @param atendimento - objeto com os dados do atendimento e paciente
	 * @param anamnese - realizada pelo m�dico durante o atendimento
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public HistoricoAtendimentoRelatorio(Atendimento atendimento, Anamnese anamnese) {
		this.codPaciente = atendimento.getPaciente().getCodPaciente();
		this.nome = atendimento.getPaciente().getNome();
		this.endereco = atendimento.getPaciente().getEndereco();

		if (atendimento.getPaciente().getSexo()) {
			this.sexo = "Masculino";
		} else {
			this.sexo = "Feminino";
		}

		this.dataNascimento = (atendimento.getPaciente().getDataNascimento())
				.getTime();
		this.documento = atendimento.getPaciente().getDocumento();
		this.nomeResponsavel = atendimento.getPaciente().getNomeResponsavel();
		this.informacoesGerais = atendimento.getPaciente()
				.getInformacoesGerais();

		this.idAtendimento = atendimento.getId();
		this.codAtendimento = atendimento.getCodAtendimento();
		this.dataAtendimento = atendimento.getDataAtendimento().getTime();
		this.medico = atendimento.getMedico().getNome();
		if(atendimento.getTecnico() == null){
			this.tecnico = "Sem T�cnico";
		} else {
			this.tecnico = atendimento.getTecnico().getNome();
		}
		this.status = atendimento.getStatus();
		this.prescricao = atendimento.getPrescricao();
		this.diagnostico = atendimento.getDiagnostico();
		this.observacoes = atendimento.getObservacoes();
		this.setNomeParametro(anamnese.getParametro().getNome());
		this.setValorParametro(anamnese.getValor());
	}

	// ************************************ GETTERS e SETTERS

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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getInformacoesGerais() {
		return informacoesGerais;
	}

	public void setInformacoesGerais(String informacoesGerais) {
		this.informacoesGerais = informacoesGerais;
	}

	public Long getIdAtendimento() {
		return idAtendimento;
	}
	
	public void setIdAtendimento(Long idAtendimento) {
		this.idAtendimento = idAtendimento;
	}
	
	public String getCodAtendimento() {
		return codAtendimento;
	}

	public void setCodAtendimento(String codAtendimento) {
		this.codAtendimento = codAtendimento;
	}

	public Date getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public String getMedico() {
		return medico;
	}

	public void setMedico(String medico) {
		this.medico = medico;
	}

	public String getTecnico() {
		return tecnico;
	}

	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrescricao() {
		return prescricao;
	}

	public void setPrescricao(String prescricao) {
		this.prescricao = prescricao;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public List<Anamnese> getAnamneses() {
		return anamneses;
	}

	public void setAvaliacoes(List<Anamnese> anamneses) {
		this.anamneses = anamneses;
	}

	@Override
	public int compareTo(HistoricoAtendimentoRelatorio arg0) {
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((anamneses == null) ? 0 : anamneses.hashCode());
		result = prime * result
				+ ((codAtendimento == null) ? 0 : codAtendimento.hashCode());
		result = prime * result
				+ ((codPaciente == null) ? 0 : codPaciente.hashCode());
		result = prime * result
				+ ((dataAtendimento == null) ? 0 : dataAtendimento.hashCode());
		result = prime * result
				+ ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result
				+ ((diagnostico == null) ? 0 : diagnostico.hashCode());
		result = prime * result
				+ ((documento == null) ? 0 : documento.hashCode());
		result = prime * result
				+ ((endereco == null) ? 0 : endereco.hashCode());
		result = prime
				* result
				+ ((informacoesGerais == null) ? 0 : informacoesGerais
						.hashCode());
		result = prime * result + ((medico == null) ? 0 : medico.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((nomeResponsavel == null) ? 0 : nomeResponsavel.hashCode());
		result = prime * result
				+ ((observacoes == null) ? 0 : observacoes.hashCode());
		result = prime * result
				+ ((prescricao == null) ? 0 : prescricao.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tecnico == null) ? 0 : tecnico.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoricoAtendimentoRelatorio other = (HistoricoAtendimentoRelatorio) obj;
		if (anamneses == null) {
			if (other.anamneses != null)
				return false;
		} else if (!anamneses.equals(other.anamneses))
			return false;
		if (codAtendimento == null) {
			if (other.codAtendimento != null)
				return false;
		} else if (!codAtendimento.equals(other.codAtendimento))
			return false;
		if (codPaciente == null) {
			if (other.codPaciente != null)
				return false;
		} else if (!codPaciente.equals(other.codPaciente))
			return false;
		if (dataAtendimento == null) {
			if (other.dataAtendimento != null)
				return false;
		} else if (!dataAtendimento.equals(other.dataAtendimento))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (diagnostico == null) {
			if (other.diagnostico != null)
				return false;
		} else if (!diagnostico.equals(other.diagnostico))
			return false;
		if (documento == null) {
			if (other.documento != null)
				return false;
		} else if (!documento.equals(other.documento))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (informacoesGerais == null) {
			if (other.informacoesGerais != null)
				return false;
		} else if (!informacoesGerais.equals(other.informacoesGerais))
			return false;
		if (medico == null) {
			if (other.medico != null)
				return false;
		} else if (!medico.equals(other.medico))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (nomeResponsavel == null) {
			if (other.nomeResponsavel != null)
				return false;
		} else if (!nomeResponsavel.equals(other.nomeResponsavel))
			return false;
		if (observacoes == null) {
			if (other.observacoes != null)
				return false;
		} else if (!observacoes.equals(other.observacoes))
			return false;
		if (prescricao == null) {
			if (other.prescricao != null)
				return false;
		} else if (!prescricao.equals(other.prescricao))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tecnico == null) {
			if (other.tecnico != null)
				return false;
		} else if (!tecnico.equals(other.tecnico))
			return false;
		return true;
	}

	public String getNomeParametro() {
		return nomeParametro;
	}

	public void setNomeParametro(String nomeParametro) {
		this.nomeParametro = nomeParametro;
	}

	public Double getValorParametro() {
		return valorParametro;
	}

	public void setValorParametro(Double valorParametro) {
		this.valorParametro = valorParametro;
	}

}
