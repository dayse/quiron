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
@Table(name = "HISTORICOAVALIACAO")
@SequenceGenerator(name = "SEQUENCIA", sequenceName = "SEQ_HISTORICOAVALIACAO", allocationSize = 1)
public class HistoricoAvaliacao implements Serializable, Comparable<HistoricoAvaliacao> {

	
	public HistoricoAvaliacao() {
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
	private Indicacao indicacao;

	/**
	 * Valor dado para este item da anamnese.
	 */
	private double avaliacao;

	private int ranking;
	
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
	@JoinColumn(name = "INDICACAO_ID", nullable = false)
	public Indicacao getIndicacao() {
		return indicacao;
	}

	public void setIndicacao(Indicacao indicacao) {
		this.indicacao = indicacao;
	}

	public double getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(double valor) {
		this.avaliacao = avaliacao;
	}

	@ManyToOne
	@JoinColumn(name = "ATENDIMENTO_ID", nullable = false)
	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	@Override
	public int compareTo(HistoricoAvaliacao arg0) {
		return 0;
	}

	/*@Override
	public String toString() {
		return "Anamnese [parametro=" + parametro + ", valor=" + valor + "]";
	}	*/

}
