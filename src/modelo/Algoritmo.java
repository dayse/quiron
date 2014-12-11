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
			@NamedQuery(name = "Algoritmo.recuperaListaDeAlgoritmo",
						query = "select c from Algoritmo c " +
								"order by c.nome"
			),
			@NamedQuery(name = "Algoritmo.recuperaAlgoritmoPorNome",
						query = "select c from Algoritmo c " +
								"where c.nome = ?"
			),
			@NamedQuery(name = "Algoritmo.recuperaAlgoritmoAtivo", 
						query = "select c from Algoritmo c " +
								"where c.status = 'Ativo'"
			)
		}
)

/**
 * 
 * @author bruno.oliveira
 *
 */
@Entity
@Table(name = "ALGORITMO")
@SequenceGenerator(name = "SEQUENCIA", sequenceName = "SEQ_ALGORITMO", allocationSize = 1)
public class Algoritmo implements Serializable, Comparable<Algoritmo>{

	private Long id;
	
	private String nome;
	
	
	private String descricao;
	

	private String status;
	
	
	// ********* Construtor *********
	
	public Algoritmo(){

		
		
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
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int compareTo(Algoritmo o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Algoritmo other = (Algoritmo) obj;
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
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	

}
