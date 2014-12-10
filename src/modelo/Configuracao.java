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
			
			@NamedQuery(name = "Configuracao.recuperaListaDeConfiguracaoPaginada",
						query = "select c from Configuracao c " +
								"order by c.nome"
			),
			@NamedQuery(name = "Configuracao.recuperaConfiguracaoPorNome",
			query = "select c from Configuracao c " +
					"where c.nome = ? "
			)
			
		}
)

/**
 * 
 * @author bruno.oliveira
 *
 */
@Entity
@Table(name = "CONFIGURACAO")
@SequenceGenerator(name = "SEQUENCIA", sequenceName = "SEQ_CONFIGURACAO", allocationSize = 1)
public class Configuracao implements Serializable, Comparable<Configuracao>{

	private Long id;
	
	private String nome;
	
	
	private String descricao;
	

	private String status;
	
	
	// ********* Construtor *********
	
	public Configuracao(){

		
		
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
	public int compareTo(Configuracao o) {
		// TODO Auto-generated method stub
		return 0;
	}


}
