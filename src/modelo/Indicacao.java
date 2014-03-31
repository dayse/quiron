package modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries(
		{
			@NamedQuery(name = "Indicacao.recuperaListaDeIndicacoesPaginada",
						query = "select m from Indicacao m " +
								"order by m.codIndicacao"
			),
			@NamedQuery(name = "Indicacao.recuperaListaDeIndicacoesPaginadaCount",
						query = "select count(m) from Indicacao m "
			),
			@NamedQuery(name = "Indicacao.recuperaIndicacaoPorCodigo", 
						query = "select m from Indicacao m " +
								"where m.codIndicacao = ? "
			),
			@NamedQuery(name = "Indicacao.recuperaIndicacaoPorCodigoLike",
						query = "select m from Indicacao m " +
								"where upper(m.codIndicacao) like '%' || upper(?) || '%' " +
								"order by m.codIndicacao"
			),
			@NamedQuery(name = "Indicacao.recuperaIndicacaoPorNome",
						query = "select m from Indicacao m " +
								"where upper(m.nome) like '%' || upper(?) || '%' " +
								"order by m.codIndicacao"
			),
			@NamedQuery(name = "Indicacao.recuperaListaIndicacao",
						query = "select i from Indicacao i " +
								"order by i.codIndicacao"
			)
		}
)

/**
 * 
 * @author bruno.oliveira
 *
 */
@Entity
@Table(name = "INDICACAO")
@SequenceGenerator(name = "SEQUENCIA", sequenceName = "SEQ_INDICACAO", allocationSize = 1)
public class Indicacao implements Serializable, Comparable<Indicacao>{

	/**
	 * Identificador do registro de indicações.
	 */
	private Long id;
	
	/**
	 * Código do indicacao.
	 */
	private String codIndicacao;
	
	/**
	 * Nome do indicacao.
	 */
	private String nome;
	
	/**
	 * Posologia recomendada para o uso.
	 */
	private String dosagem;
	
	
	// ********* Construtor *********
	
	public Indicacao(){
		
	}
	
	// ********* Métodos get/set *********
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCIA")
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = false, length = 15, unique = true)
	public String getCodIndicacao() {
		return codIndicacao;
	}
	
	public void setCodIndicacao(String codIndicacao) {
		this.codIndicacao = codIndicacao;
	}
	
	@Column(length = 50)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDosagem() {
		return dosagem;
	}
	
	public void setDosagem(String dosagem) {
		this.dosagem = dosagem;
	}
	
	@Override
	public int compareTo(Indicacao arg0) {
		return 0;
	}
}
