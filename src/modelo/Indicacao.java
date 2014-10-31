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
			@NamedQuery(name = "Indicacao.recuperaListaDeIndicacoesPaginada",
						query = "select m from Indicacao m "
			),
			@NamedQuery(name = "Indicacao.recuperaListaDeIndicacoesPaginadaCount",
						query = "select count(m) from Indicacao m "
			),
			@NamedQuery(name = "Indicacao.recuperaIndicacaoPorCodigo", 
						query = "select m from Indicacao m " +
								"where m.codIndicacao = ? "
			),
			@NamedQuery(name = "Indicacao.recuperaIndicacaoPorCodigoLike",
						query = "select m from Indicacao m " +
								"where upper(m.codIndicacao) like '%' || upper(?) || '%' " +
								"order by m.codIndicacao"
			),
			@NamedQuery(name = "Indicacao.recuperaIndicacaoPorNome",
						query = "select m from Indicacao m " +
								"where upper(m.nome) like '%' || upper(?) || '%' " +
								"order by m.codIndicacao"
			),
			@NamedQuery(name = "Indicacao.recuperaListaIndicacao",
						query = "select i from Indicacao i " +
								"order by i.codIndicacao"
			)
		}
)

/**
 * 
 * @author bruno.oliveira
 *
 */
@Entity
@Table(name = "INDICACAO")
@SequenceGenerator(name = "SEQUENCIA", sequenceName = "SEQ_INDICACAO", allocationSize = 1)
public class Indicacao implements Serializable, Comparable<Indicacao>{

	/**
	 * Identificador do registro de indicações.
	 */
	private Long id;
	
	/**
	 * Código do indicacao.
	 */
	private String codIndicacao;
	
	/**
	 * Nome do indicacao.
	 */
	private String nome;
	
	/**
	 * Posologia recomendada para o uso.
	 * Esse atributo deve o seu nome alterado no frontend para o termo "Posologia",
	 * mas ambos significam a mesma coisa.
	 */
	private String dosagem;
	
	/**
	 * Apresentação da indicação.
	 */
	private String apresentacao;
	
	/**
	 * Lista de avaliações dadas as indicações pelos especiliastas.
	 */
	private List<AvalIndicacaoEspec> listAvalIndicacaoEspec = new ArrayList<AvalIndicacaoEspec>();
	
	
	// ********* Construtor *********
	
	public Indicacao(){
		
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
	public String getCodIndicacao() {
		return codIndicacao;
	}
	
	public void setCodIndicacao(String codIndicacao) {
		this.codIndicacao = codIndicacao;
	}
	
	@Column(length = 50)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDosagem() {
		return dosagem;
	}
	
	public void setDosagem(String dosagem) {
		this.dosagem = dosagem;
	}
	
	@OneToMany(mappedBy = "indicacao", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	public List<AvalIndicacaoEspec> getListAvalIndicacaoEspec() {
		return listAvalIndicacaoEspec;
	}
	
	public void setListAvalIndicacaoEspec(List<AvalIndicacaoEspec> listAvalIndicacaoEspec) {
		this.listAvalIndicacaoEspec = listAvalIndicacaoEspec;
	}

	public String getApresentacao() {
		return apresentacao;
	}

	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}

	@Override
	public int compareTo(Indicacao arg0) {
		return 0;
	}
	
	public String toString(){
		return this.nome + " " + this.apresentacao;
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
				+ ((codIndicacao == null) ? 0 : codIndicacao.hashCode());
		result = prime * result + ((dosagem == null) ? 0 : dosagem.hashCode());
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
		Indicacao other = (Indicacao) obj;
		if (codIndicacao == null) {
			if (other.codIndicacao != null)
				return false;
		} else if (!codIndicacao.equals(other.codIndicacao))
			return false;
		if (dosagem == null) {
			if (other.dosagem != null)
				return false;
		} else if (!dosagem.equals(other.dosagem))
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

}
