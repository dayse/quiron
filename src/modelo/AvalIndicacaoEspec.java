package modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaListaDeAvalIndicacaoEspecsPaginada",
						query = "select m from AvalIndicacaoEspec m " +
								"order by m.id"
			),
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaListaDeAvalIndicacaoEspecsPaginadaCount",
						query = "select count(m) from AvalIndicacaoEspec m "
			),
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaAvalIndicacaoEspecPorCodigo", 
						query = "select m from AvalIndicacaoEspec m " +
								"where m.id = ? "
			),
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaListaDeAvaliacaoEspecComIndicacaoDeUmEspec", 
						query = "select a from AvalIndicacaoEspec a " +
								"left outer join fetch a.indicacao i " +
								"where a.especialista = ? " +
								"order by i.nome, a.id"
			),
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaListaDeAvaliacaoEspecComIndicacaoComEspec", 
						query = "select a from AvalIndicacaoEspec a " +
								"left outer join fetch a.indicacao i " +
								"left outer join fetch a.especialista e"
			),
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaListaDeAvaliacaoEspecPorIndicacao", 
						query = "select a from AvalIndicacaoEspec a " +
								"where a.indicacao = ?"
			)
		}
)

/**
 * 
 * @author bruno.oliveira
 *
 */
@Entity
@Table(name = "AVALINDICACAOESPEC")
@SequenceGenerator(name = "SEQUENCIA", sequenceName = "SEQ_AVALINDICACAOESPEC", allocationSize = 1)
public class AvalIndicacaoEspec implements Serializable, Comparable<AvalIndicacaoEspec>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Identificador do registro de indicações.
	 */
	private Long id;
	
	
	/**
	 * Id do especialista
	 */
	private Especialista especialista;
	
	/**
	 * Id da indicação
	 */
	private Indicacao indicacao;
	
	private double febre = 0;

	private double disuria = 0;

	private double diabetes = 0;

	private double enterococos = 0;

	private double escherichia = 0;

	private double candida = 0;

	private double efeitosColaterais = 0;
	// ********* Construtor *********
	
	public AvalIndicacaoEspec(){
		
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ESPECIALISTA_ID", nullable = false)
	public Especialista getEspecialista() {
		return especialista;
	}

	public void setEspecialista(Especialista especialista) {
		this.especialista = especialista;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INDICACAO_ID", nullable = false)
	public Indicacao getIndicacao() {
		return indicacao;
	}

	public void setIndicacao(Indicacao indicacao) {
		this.indicacao = indicacao;
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
	public int compareTo(AvalIndicacaoEspec arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
