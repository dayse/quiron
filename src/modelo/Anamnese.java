package modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries(
		{
			@NamedQuery(name = "Anamnese.recuperaAnamnesePorAtendimento", 
						query = "select a from Anamnese a " +
								"where a.atendimento = ?"
			)
		}
)

@Entity
@Table(name = "ANAMNESE")
@SequenceGenerator(name = "SEQUENCIA", sequenceName = "SEQ_ANAMNESE", allocationSize = 1)
public class Anamnese implements Serializable, Comparable<Anamnese> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Atendimento atendimento;

	private double febre;

	private double disuria;

	private double diabetes;

	private double enterococos;

	private double escherichia;

	private double candida;

	private double efeitosColaterais;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCIA")
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "ATENDIMENTO_ID", nullable = false)
	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

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

	public void setEfeitosColaterais(double efeitosColaterais) {
		this.efeitosColaterais = efeitosColaterais;
	}

	@Override
	public int compareTo(Anamnese arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
