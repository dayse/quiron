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
	private Parametro parametro;

	private double valor;


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
	@JoinColumn(name = "PARAMETRO_ID", nullable = false)
	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@ManyToOne
	@JoinColumn(name = "ATENDIMENTO_ID", nullable = false)
	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}


	@Override
	public int compareTo(Anamnese arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
