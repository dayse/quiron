package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries(
		{
			@NamedQuery(name = "Paciente.recuperaUmPacienteComAtendimento",
						query = "select p from Paciente p " +
								"left outer join fetch p.atendimentos"
			),
			@NamedQuery(name = "Paciente.recuperaListaDePacientes",
						query = "select p from Paciente p " +
								"order by p.codPaciente"
			),
			@NamedQuery(name = "Paciente.recuperaPacientePorCodigo",
						query = "select p from Paciente p " +
								"where p.codPaciente = ? " +
								"order by p.codPaciente"
			),
			@NamedQuery(name = "Paciente.recuperaPacientePorCodigoLike",
						query = "select p from Paciente p " +
								"where p.codPaciente like '%' || upper(?) || '%' " +
								"order by p.codPaciente"
			),
			@NamedQuery(name = "Paciente.recuperaPacientePorNome",
						query = "select p from Paciente p " +
								"where upper(p.nome) like '%' || upper(?) || '%' " +
								"order by p.codPaciente"
			),
			@NamedQuery(name = "Paciente.recuperaListaDePacientesComTotal",
						query = "select count(p) from Paciente p "
			),
			@NamedQuery(name = "Paciente.recuperaListaDePacientesPaginada",
						query = "select p from Paciente p " +
								"order by p.codPaciente"
			),
			@NamedQuery(name = "Paciente.recuperaListaDePacientesPaginadaCount",
						query = "select count(p) from Paciente p "
			),
			@NamedQuery(name = "Paciente.recuperaListaDePacientesPaginadaComListaDeAtendimentos",
						query = "select p from Paciente p " +
								"left outer join fetch p.atendimentos pa " +
								"order by p.codPaciente"
			),
			@NamedQuery(name = "Paciente.recuperaListaDePacientesPaginadaComListaDeAtendimentosCount",
						query = "select count(p) from Paciente p "
			)
		}
)


/**
 * @author bruno.oliveira
 *
 */
@Entity
@Table(name = "PACIENTE")
@SequenceGenerator(name = "SEQUENCIA", sequenceName = "SEQ_PACIENTE", allocationSize = 1)
public class Paciente implements Serializable, Comparable<Paciente> {

	private static final long serialVersionUID = 1L;
	
	public static final String FLAG_MASCULINO = "Masculino";
	public static final String FLAG_FEMININO = "Feminino";
	
	/**
	 * Identificador do registro de pacientes.
	 */
	private Long id;
	
	/**
	 * Código do paciente.
	 */
	private String codPaciente;
	
	/**
	 * Nome do paciente.
	 */
	private String nome;
	
	/**
	 * Endereço do paciente.
	 */
	private String endereco;
	
	/**
	 * Nome do responsável
	 */
	private String nomeResponsavel;
	
	/**
	 * Sexo do paciente.
	 */
	private Boolean sexo;
	
	/**
	 * Data de nascimento do paciente.
	 */
	private Calendar dataNascimento;
	
	/**
	 * Documento de identificação, ou do responsável caso seja menor.
	 */
	private String documento;
	
	/**
	 * Relacionamento com Atendimento
	 */
	private List<Atendimento> atendimentos = new ArrayList<Atendimento>();
	
	/**
	 * Este atributo contém informações que ajudam a definir o perfil do paciente e
	 *  consta de observações normalmente são feitas num primeiro atendimento, 
	 *  tais como "doenças infantis", cirurgias realizadas, etc.
	 */
	private String informacoesGerais;

	// ********* Construtor *********
	
	public Paciente(){
		
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
	public String getCodPaciente() {
		return codPaciente;
	}

	public void setCodPaciente(String codPaciente) {
		this.codPaciente = codPaciente;
	}

	@Column(length = 50)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(length = 9)
	public Boolean getSexo() {
		return sexo;
	}

	public void setSexo(Boolean sexo) {
		this.sexo = sexo;
	}
	
	@OneToMany(mappedBy = "paciente", cascade=CascadeType.REMOVE)
	@OrderBy
	public List<Atendimento> getAtendimentos() {
		return atendimentos;
	}

	public void setAtendimentos(List<Atendimento> atendimentos) {
		this.atendimentos = atendimentos;
	}
	
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}
	
	public String getInformacoesGerais(){
		return informacoesGerais;
	}
	
	public void setInformacoesGerais(String informacoesGerais){
		this.informacoesGerais = informacoesGerais;
	}
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Temporal(value = TemporalType.DATE)
	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}	
	
	// *********  Métodos para associação ********* 
	

	@Override
	public String toString() {
		return this.codPaciente  + " - " + this.nome;
	}
	
	/**
	 * Este método poder ser gerado AUTOMATICAMENTE pelo Java , juntamente com o método  "equals(Object obj)".
	 * Eles São necessários para determinarmos um criterio de igualdade entre 2 objetos.
	 * 
	 * Obs.: É primoridal dar atenção para este detalhe, principalmente quando trabalhamos com Estruturas
	 * 		 de Dados como Set.
	 * 
	 *  @return int
	 *  
	 *  @author bruno.oliveira
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codPaciente == null) ? 0 : codPaciente.hashCode());
		return result;
	}	
	
	/**
	 * 
	 * Este método pode ser gerado AUTOMATICAMENTE pelo Java , juntamente com o método 
	 * "hashCode()". Estes metodos são necessários para se determinar um critério principal
	 * de igualdade entre 2 objetos. 
	 * 
	 *  @param O objeto a ser verificado.
	 *  @return Retorna true caso os objetos sejam iguais, do contrário retorna false.
	 *  
	 *  @author bruno.oliveira
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Paciente)) {
			return false;
		}
		Paciente other = (Paciente) obj;
		if (codPaciente == null) {
			if (other.codPaciente != null) {
				return false;
			}
		} else if (!codPaciente.equals(other.codPaciente)) {
			return false;
		}
		return true;
	}	
	
	/**
	 * Esse método consiste em definir o critério de orientação entre 2 objetos Paciente, que deve estar
	 * associado a um de seus atributos. Neste caso, o atributo em questão é "codPaciente", que é do tipo
	 * nativo 'String', que sabe internamente se auto-ordenar, graças a implementação da API Java que o realiza.
	 * 
	 * @param - O objeto paciente que será comparado.
	 * @return - Se as duas Strings forem iguais o método retorna 1, senão retorna o resultado da comparação que representa a diferença entre as Strings.
	 *
	 * @author bruno.oliveira
	 */
	@Override
	public int compareTo(Paciente o) {
		int valor = codPaciente.compareTo(o.codPaciente);
		return 	(valor != 0 ? valor : 1);
	}

}
