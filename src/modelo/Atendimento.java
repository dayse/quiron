package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries(
		{
			@NamedQuery(name = "Atendimento.recuperaAtendimentoPorCodigo",
						query = "select a from Atendimento a " +
								"where a.codAtendimento = ? " +
								"order by a.codAtendimento"
			),
			@NamedQuery(name = "Atendimento.recuperaAtendimentoPorCodigoComPaciente",
						query = "select a from Atendimento a " +
								"left outer join fetch a.paciente p " +
								"where a.codAtendimento = ? " +
								"order by a.codAtendimento"
			),
			@NamedQuery(name = "Atendimento.recuperaListaDeAtendimentosPaginada",
						query = "select a from Atendimento a " +
								"inner join a.paciente pa " +
								"order by a.dataAtendimento"
			),
			@NamedQuery(name = "Atendimento.recuperaListaDeAtendimentosPaginadaCount",
						query = "select count(a) from Atendimento a "
			),
			@NamedQuery(name = "Atendimento.recuperaListaDeAtendimentosComPacientePaginada",
						query = "select a from Atendimento a " +
								"inner join fetch a.paciente pa " +
								"order by a.dataAtendimento"
			),
			@NamedQuery(name = "Atendimento.recuperaListaDeAtendimentosComPacientePaginadaCount",
						query = "select count(a) from Atendimento a "
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentosPorPacientePorCodigoLike",
						query = "select a from Atendimento a " +
								"where a.codAtendimento like '%' || upper(?) || '%' " +
								"order by a.codAtendimento"
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentosPorPacientePorCodigoLikeCount",
						query = "select count(a) from Atendimento a " +
								"where a.codAtendimento like '%' || upper(?) || '%' "
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentosPorPacientePorNome",
						query = "select a from Atendimento a " +
								"inner join a.paciente pa " +
								"where upper(pa.nome) like '%' || upper(?) || '%' " +
								"order by a.codAtendimento"
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentosPorPacientePorNomeCount",
						query = "select count(a) from Atendimento a " +
								"inner join a.paciente pa " +
								"where upper(pa.nome) like '%' || upper(?) || '%' "
			),
			@NamedQuery(name = "Atendimento.recuperaUltimoAtendimento",
						query = "select Max(a) from Atendimento a"
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLike",
						query = "select a from Atendimento a " +
							"left outer join a.medico md " +
//							"left outer join fetch a.paciente p " +
							"where upper(md.nome) like '%' || upper(?) || '%' " +
							"order by a.dataAtendimento"
			),

			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLikeCount",
						query = "select count(a) from Atendimento a " +
							"left outer join a.medico md " +
//							"left outer join fetch a.paciente p " +
							"where upper(md.nome) like '%' || upper(?) || '%' "
			),
			
		}
)

/**
 * @author bruno.oliveira
 *
 */
@Entity
@Table(name = "Atendimento")
@SequenceGenerator(name = "SEQUENCIA", sequenceName = "SEQ_PACIENTE", allocationSize = 1)
public class Atendimento implements Serializable, Comparable<Atendimento> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identificador do registro de atendimento
	 */
	private Long id;
	
	/**
	 * Código do atendimento.
	 */
	private String codAtendimento;
	
	/**
	 * Date e Hora do atendimento
	 */
	private Calendar dataAtendimento;
	
	/**
	 * Prognóstico
	 */
	private String prognostico;
	
	/**
	 * Nome do responsável caso o paciente seja menor de idade.
	 */
	private String nomeResponsavel;
	
	/**
	 * CPF de identificação do responsável caso o paciente seja menor de idade.
	 */
	private String cpfResponsavel;
	
	/**
	 * Define o status em que o atendimento se encontra.
	 * Se o atendimento acaba de ser criado o mesmo fica marcado como 'Aberto',
	 * 'Em atendimento' caso já esteja em curso do atendimento com o médico ou aguardando exames ou
	 * 'Encerrado' quando o atendimento chegar ao fim.  
	 */
	private String status;
	
	/**
	 * Paciente vinculado a este registro
	 */
	private Paciente paciente;
	
	/**
	 * Médico que foi escalado realizar o atendimento
	 */
	private Usuario medico;
	
	/**
	 * Técnico que realizou ou foi escalado para auxiliar o atendimento
	 */
	private Usuario tecnico;
	

	private List<Anamnese> anamneses = new ArrayList<Anamnese>();
	
	@Override
	public int compareTo(Atendimento arg0) {
		// TODO Auto-generated method stub
		return 0;
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

	public String getCodAtendimento() {
		return codAtendimento;
	}

	public void setCodAtendimento(String codAtendimento) {
		this.codAtendimento = codAtendimento;
	}

	@Temporal(value = TemporalType.DATE)
	public Calendar getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Calendar dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public String getPrognostico() {
		return prognostico;
	}

	public void setPrognostico(String prognostico) {
		this.prognostico = prognostico;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PACIENTE_ID", nullable = false)
	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MEDICO_ID", nullable = false)
	public Usuario getMedico() {
		return medico;
	}

	public void setMedico(Usuario medico) {
		this.medico = medico;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TECNICO_ID", nullable = true)
	public Usuario getTecnico() {
		return tecnico;
	}

	public void setTecnico(Usuario tecnico) {
		this.tecnico = tecnico;
	}

	@OneToMany(mappedBy = "atendimento", cascade=CascadeType.REMOVE)
	public List<Anamnese> getAnamneses() {
		return anamneses;
	}

	public void setAnamneses(List<Anamnese> anamneses) {
		this.anamneses = anamneses;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getCpfResponsavel() {
		return cpfResponsavel;
	}

	public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
