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
			@NamedQuery(name = "Anamnese.recuperaListaDeAnamnesePorAtendimento", 
						query = "select a from Anamnese a " +
								"where a.atendimento = ?" +
								" order by a.parametro"
			),
			@NamedQuery(name = "Anamnese.recuperaListaDeAnamnesePorParametro", 
						query = "select a from Anamnese a " +
								"where a.parametro = ?" +
								" order by a.parametro"
			),
			@NamedQuery(name = "Anamnese.recuperaAnamnesePorAtendimentoPorParametro", 
						query = "select a from Anamnese a " +
								"where a.atendimento = ?" +
								"and a.parametro = ?"
			)
		}
)

/**
 * 
 * A entidade Anamnese na verdade está representando
 * os diversos itens de uma anamnese. Onde cada item
 * representa o valor do parâmetro para um dado atendimento.
 * 
 * @author bruno.oliveira
 *
 */
@Entity
@Table(name = "ANAMNESE")
@SequenceGenerator(name = "SEQUENCIA", sequenceName = "SEQ_ANAMNESE", allocationSize = 1)
public class Anamnese implements Serializable, Comparable<Anamnese> {

	
	public Anamnese() {
	}
	
	public Anamnese(Atendimento atendimento, Parametro parametro, Double valor){
		this.atendimento = atendimento;
		this.parametro = parametro;
		this.valor = valor;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador do registro.
	 */
	private Long id;

	/**
	 * Atendimento ao qual este item de anamnese pertence.
	 */
	private Atendimento atendimento;
	
	/**
	 * Parâmetro que foi avaliado neste registro.
	 */
	private Parametro parametro;

	/**
	 * Valor dado para este item da anamnese.
	 */
	private double valor;


	/* Getters and Setters */
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
		return 0;
	}

	@Override
	public String toString() {
		return "Anamnese [parametro=" + parametro + ", valor=" + valor + "]";
	}	

}
