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
			@NamedQuery(name = "Parametro.recuperaListaDeParametros", 
						query = "select p from Parametro p " +
								"order by p.codParametro"
			),
			@NamedQuery(name = "Parametro.recuperaListaDeParametrosPaginada",
						query = "select p from Parametro p " +
								"order by p.codParametro"
			),
			@NamedQuery(name = "Parametro.recuperaListaDeParametrosPaginadaCount",
						query = "Select count(p) from Parametro p "
				
			),
			@NamedQuery(name = "Parametro.recuperaParametroPorCodigo", 
						query = "select p from Parametro p " +
								"where p.codParametro = ? "
			),
			@NamedQuery(name = "Parametro.recuperaParametroPorNome",
						query = "select p from Parametro p " +
								"where p.nome = ? "
			),
			@NamedQuery(name = "Parametro.recuperaParametroPorCodigoLike",
						query = "select p from Parametro p " +
								"where upper(p.codParametro) like '%' || upper(?) || '%' " +
								"order by p.codParametro"
			),
			@NamedQuery(name = "Parametro.recuperaParametroPorNomeLike",
						query = "select p from Parametro p " +
								"where upper(p.nome) like '%' || upper(?) || '%' " +
								"order by p.codParametro"
			)
		}
)

/**
 * 
 * @author bruno.oliveira
 *
 */
@Entity
@Table(name = "PARAMETROS")
@SequenceGenerator(name = "SEQUENCIA", sequenceName = "SEQ_PARAMETROS", allocationSize = 1)
public class Parametro implements Serializable, Comparable<Parametro>{

	/**
	 * Identificador do registro de indicações.
	 */
	private Long id;
	
	/**
	 * Código do parametro.
	 */
	private String codParametro;
	
	/**
	 * Nome do parametro.
	 */
	private String nome;
	
	/**
	 * Descrição recomendada para o uso.
	 */
	private String descricao;
	
	/**
	 * peso que define a importância do parâmetro no algoritmo de sugestão de medicação
	 */
	private Double peso;
	
	/**
	 * Lista de avaliações dadas as indicações pelos especialistas.
	 */
	private List<AvalIndicacaoEspec> listAvalIndicacaoEspec = new ArrayList<AvalIndicacaoEspec>();
	
	// ********* Construtor *********
	
	public Parametro(){
		this.peso = 1.0;
		
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
	public String getCodParametro() {
		return codParametro;
	}
	
	public void setCodParametro(String codParametro) {
		this.codParametro = codParametro;
	}
	
	@Column(length = 50)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@OneToMany(mappedBy = "parametro", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<AvalIndicacaoEspec> getListAvalIndicacaoEspec(){
		return listAvalIndicacaoEspec;
	}
	
	public void setListAvalIndicacaoEspec(List<AvalIndicacaoEspec> listAvalIndicacaoEspec){
		this.listAvalIndicacaoEspec = listAvalIndicacaoEspec;
	}
	
	@Override
	public int compareTo(Parametro arg0) {
		return 0;
	}

	public String toString(){
		return this.nome;
	}

	/**
	 * Este método poder ser gerado AUTOMATICAMENTE pelo Java, juntamente com o método  "equals(Object obj)".
	 * Eles São necessários para determinarmos um criterio de igualdade entre 2 objetos.
	 * 
	 * Obs.: É primoridal dar atenção para este detalhe, principalmente quando trabalhamos com Estruturas
	 * 		 de Dados como Set.
	 * 
	 * @return int
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codParametro == null) ? 0 : codParametro.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((listAvalIndicacaoEspec == null) ? 0
						: listAvalIndicacaoEspec.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parametro other = (Parametro) obj;
		if (codParametro == null) {
			if (other.codParametro != null)
				return false;
		} else if (!codParametro.equals(other.codParametro))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
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
		return true;
	}
	
	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}
}
