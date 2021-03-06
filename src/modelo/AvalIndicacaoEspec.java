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
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaListaDeAvaliacaoEspecPorEspecialistaPaginada",
						query = "select m from AvalIndicacaoEspec m " +
								"where m.especialista = ? " +
								"order by m.id"
			),
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaListaDeAvaliacaoEspecPorEspecialistaPaginadaCount",
						query = "select count(m) from AvalIndicacaoEspec m " +
								"where m.especialista = ?"
			),
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaAvalIndicacaoEspecPorID", 
						query = "select m from AvalIndicacaoEspec m " +
								"where m.id = ? "
			),
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaListaDeAvaliacaoEspecComIndicacaoDeUmEspec", 
						query = "select a from AvalIndicacaoEspec a " +
								"left outer join fetch a.indicacao i " +
								"left outer join fetch a.parametro p " +
								"left outer join fetch a.especialista e " +
								"where a.especialista = ? " +
								"order by i.id, p.id"
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
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaListaDeAvaliacaoEspecPorParametro", 
			query = "select a from AvalIndicacaoEspec a " +
					"where a.parametro = ?"
			),
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaListaDeAvaliacaoPorEspecialistaPorIndicacao", 
						query = "select a from AvalIndicacaoEspec a " +
								"left outer join fetch a.indicacao i " +
								"left outer join fetch a.especialista e " +
								"left outer join fetch a.parametro p " +			
								"where a.especialista = ?" +
								"and a.indicacao = ? " +
								"order by a.parametro.codParametro"
			),
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaAvaliacaoPorIndicacaoParametro", 
						query = "Select a from AvalIndicacaoEspec a " +
								"where a.indicacao = ? " +
								"and a.parametro = ?"
						),
			@NamedQuery(name = "AvalIndicacaoEspec.recuperaAvaliacaoPorEspecialistaIndicacaoParametro", 
						query = "Select a from AvalIndicacaoEspec a " +
								"left outer join fetch a.indicacao i " +
								"left outer join fetch a.especialista e " +
								"left outer join fetch a.parametro p " +
								"where a.especialista = ? " +
								"and a.indicacao = ? " +
								"and a.parametro = ?"
						)
		}
)

/**
 * 
 * Classe que representa um modelo de uma
 * Avalia��o dada por um Especialista para uma
 * Indica��o (rem�dio) espec�fica.
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
	 * Identificador do registro de indica��es.
	 */
	private Long id;
	
	
	/**
	 * Id do especialista
	 */
	private Especialista especialista;
	
	/**
	 * Id da indica��o
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
	
	// ********* M�todos get/set *********
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
	
	@Override
	public boolean equals(Object object){
		AvalIndicacaoEspec avaliacao = (AvalIndicacaoEspec) object;
		
		if(this.especialista == avaliacao.especialista 
				&& this.indicacao == avaliacao.indicacao
					&& this.parametro == avaliacao.parametro
						&& this.valor == avaliacao.valor){
			return true;
		}
		return false;
	}
}
