package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries(
		{
			@NamedQuery(name = "Especialista.recuperaListaDeEspecialistasPaginada",
						query = "select e from Especialista e " +
								"order by e.codEspecialista"
			),
			@NamedQuery(name = "Especialista.recuperaListaDeEspecialistasPaginadaCount",
						query = "select count(e) from Especialista e "
			),
			@NamedQuery(name = "Especialista.recuperaEspecialistaPorCodigo", 
						query = "select e from Especialista e " +
								"where e.codEspecialista = ? "
			),
			@NamedQuery(name = "Especialista.recuperaListaEspecialista", 
						query = "select e from Especialista e " +
								"order by e.codEspecialista"
			),
			@NamedQuery(name = "Especialista.recuperaMediaDoPesoAvaliadorDosEspecialistas",  
						query = "select SUM(pesoAvaliador) from Especialista e"
			)
		}
)

/**
 * 
 * @author bruno.oliveira
 *
 */
@Entity
@Table(name = "ESPECIALISTA")
@SequenceGenerator(name = "SEQUENCIA", sequenceName = "SEQ_ESPECIALISTA", allocationSize = 1)
public class Especialista implements Serializable, Comparable<Especialista>{

	/**
	 * Identificador do registro de indicações.
	 */
	private Long id;
	
	/**
	 * Código do especialista.
	 */
	private String codEspecialista;
	
	/**
	 * Nome do especialista.
	 */
	private String nome;
	
	/**
	 * Peso avaliador do especialista.
	 */
	private double pesoAvaliador;
	
	/**
	 * Lista de avaliações dadas as indicações pelos especiliastas.
	 */
	private List<AvalIndicacaoEspec> listAvalIndicacaoEspec = new ArrayList<AvalIndicacaoEspec>();
		
	// ********* Construtor *********
	
	public Especialista(){
		
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
	public String getCodEspecialista() {
		return codEspecialista;
	}
	
	public void setCodEspecialista(String codEspecialista) {
		this.codEspecialista = codEspecialista;
	}
	
	@Column(length = 50)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public double getPesoAvaliador() {
		return pesoAvaliador;
	}
	
	public void setPesoAvaliador(double pesoAvaliador) {
		this.pesoAvaliador = pesoAvaliador;
	}
	
	@OneToMany(mappedBy = "especialista", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	public List<AvalIndicacaoEspec> getListAvalIndicacaoEspec() {
		return listAvalIndicacaoEspec;
	}
	
	public void setListAvalIndicacaoEspec(List<AvalIndicacaoEspec> listAvalIndicacaoEspec) {
		this.listAvalIndicacaoEspec = listAvalIndicacaoEspec;
	}
	
	@Override
	public int compareTo(Especialista arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString(){
		return this.nome;
	}	
}
