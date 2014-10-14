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
	 * Lista de avaliações dadas as indicações pelos especialistas.
	 */
	private List<AvalIndicacaoEspec> listAvalIndicacaoEspec = new ArrayList<AvalIndicacaoEspec>();
	
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
	
	@OneToMany(mappedBy = "parametro", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<AvalIndicacaoEspec> getListAvalIndicacaoEspec(){
		return listAvalIndicacaoEspec;
	}
	
	public void setListAvalIndicacaoEspec(List<AvalIndicacaoEspec> listAvalIndicacaoEspec){
		this.listAvalIndicacaoEspec = listAvalIndicacaoEspec;
	}
	
	@Override
	public int compareTo(Parametro arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String toString(){
		return this.nome;
	}
}
