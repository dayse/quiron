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
			@NamedQuery(name = "HistoricoAvaliacao.recuperaListaHistoricoPorAtendimento",					
						query = "select h from HistoricoAvaliacao h " +
								"where h.atendimento = ? " +
								"order by h.ranking"
			)
		}
)
/**
 * 
 * Classe que representa o Histórico da Avaliação
 * de um Atendimento. Esse modelo representa no banco 
 * de dados um atendimento encerrado, cuja avaliação
 * não será mais calculada por questões de integridade dos
 * dados.
 * 
 * Cada objeto do tipo HistoricoAvaliacao representa uma
 * indicação dentro do ranking de avaliação do sistema.
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
	
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador do registro.
	 */
	private Long id;

	/**
	 * Atendimento ao qual este Histórico de Avaliação pertence.
	 */
	private Atendimento atendimento;
	
	/**
	 * Indicação presente na avaliação gerada.
	 */
	private Indicacao indicacao;

	/**
	 * Valor dado para indicação durante a avaliação gerada.
	 */
	private double avaliacao;

	/**
	 * Ranking alcançado pela indicação por causa da avaliação obtida.
	 */
	private int ranking;
	
	// ================================== Métodos get() e set()	// ============================== //
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

	public void setAvaliacao(double avaliacao) {
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

}
