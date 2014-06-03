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
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaAvalIndicacaoEspecPorID", 
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
			),
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaAvalIndicacaoEspecPorIndicacaoPorEspec", 
						query = "select a from AvalIndicacaoEspec a " +
								"where a.indicacao = ?" +
								"and a.especialista = ?"
			),
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaAvaliacaoPorEspecialistaIndicacaoParametro", 
						query = "Select a from AvalIndicacaoEspec a " +
								"where a.especialista = ? " +
								"and a.indicacao = ? " +
								"and a.parametro = ?"
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
	
	/**
	 * ID do parametro
	 */
	private Parametro parametro;
	
	/**
	 * Valor do parametro
	 */
	private double valor;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARAMETRO_ID", nullable = false)	
	public Parametro getParametro(){
		return parametro;
	}
	
	public void setParametro(Parametro parametro){
		this.parametro = parametro;
	}
	
	public double getValor(){
		return valor;
	}
	
	public void setValor(double valor){
		this.valor = valor;
	}
	
	@Override
	public int compareTo(AvalIndicacaoEspec arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
