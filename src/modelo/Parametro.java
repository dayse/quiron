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
			@NamedQuery(name = "Parametro.recuperaListaDeParametrosPaginada",
						query = "select m from Parametro m " +
								"order by m.codParametro"
			),
			@NamedQuery(name = "Parametro.recuperaListaDeParametrosPaginadaCount",
						query = "Select count(m) from Parametro m "
				
			),
			@NamedQuery(name = "Parametro.recuperaParametroPorCodigo", 
						query = "select m from Parametro m " +
								"where m.codParametro = ? "
			),
			@NamedQuery(name = "Parametro.recuperaParametroPorNome",
						query = "select m from Parametro m " +
								"where m.nome = ? "
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
	
	
	// ********* Construtor *********
	
	public Parametro(){
		
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
	
	@Override
	public int compareTo(Parametro arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
