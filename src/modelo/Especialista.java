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
			),
			@NamedQuery(name = "Especialista.recuperaEspecialistaPorCodigoLike",
						query = "select e from Especialista e " + 
								"where upper(e.codEspecialista) like '%' || upper(?) || '%' " +
								"order by e.codEspecialista"
			),
			@NamedQuery(name = "Especialista.recuperaEspecialistaPorNomeLike",
						query = "select e from Especialista e " +
								"where upper(e.nome) like '%' || upper(?) || '%' " +
								"order by e.codEspecialista"
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
	
	/**
	 * Este método poder ser gerado AUTOMATICAMENTE pelo Java, juntamente com o método  "equals(Object obj)".
	 * Eles São necessários para determinarmos um criterio de igualdade entre 2 objetos.
	 * 
	 * Obs.: É primoridal dar atenção para este detalhe, principalmente quando trabalhamos com Estruturas
	 * 		 de Dados como Set.
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codEspecialista == null) ? 0 : codEspecialista.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((listAvalIndicacaoEspec == null) ? 0
						: listAvalIndicacaoEspec.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		long temp;
		temp = Double.doubleToLongBits(pesoAvaliador);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Este método poder ser gerado AUTOMATICAMENTE pelo Java, juntamente com o método  "hashCode()".
	 * Eles São necessários para determinarmos um criterio de igualdade entre 2 objetos.
	 * 
	 * Obs.: É primoridal dar atenção para este detalhe, principalmente quando trabalhamos com Estruturas
	 * 		 de Dados como Set.
	 * 
	 * @param Object  
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Especialista other = (Especialista) obj;
		if (codEspecialista == null) {
			if (other.codEspecialista != null)
				return false;
		} else if (!codEspecialista.equals(other.codEspecialista))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listAvalIndicacaoEspec == null) {
			if (other.listAvalIndicacaoEspec != null)
				return false;
		} else if (!listAvalIndicacaoEspec.equals(other.listAvalIndicacaoEspec))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (Double.doubleToLongBits(pesoAvaliador) != Double
				.doubleToLongBits(other.pesoAvaliador))
			return false;
		return true;
	}

	public String toString(){
		return this.nome;
	}

	@Override
	public int compareTo(Especialista arg0) {
		return 0;
	}	
}
