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
import javax.persistence.Transient;

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
								"order by a.dataAtendimento desc"
			),
			@NamedQuery(name = "Atendimento.recuperaListaDeAtendimentosPaginadaCount",
						query = "select count(a) from Atendimento a "
			),
			@NamedQuery(name = "Atendimento.recuperaListaDeAtendimentosComPacientePaginada",
						query = "select a from Atendimento a " +
								"inner join fetch a.paciente pa " +
								"order by a.dataAtendimento desc"
			),
			@NamedQuery(name = "Atendimento.recuperaListaDeAtendimentosComPacientePaginadaCount",
						query = "select count(a) from Atendimento a "
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentosPorPacientePorCodigoLike",
						query = "select a from Atendimento a " +
								"where upper(a.codAtendimento) like '%' || upper(?) || '%' " +
								"order by a.codAtendimento"
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentosPorPacientePorCodigoLikeCount",
						query = "select count(a) from Atendimento a " +
								"where upper(a.codAtendimento) like '%' || upper(?) || '%' "
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
								"inner join a.medico md " +
								"left outer join fetch a.paciente p " +
								"where upper(md.nome) like '%' || upper(?) || '%' " 
								+
								"order by a.dataAtendimento desc"
			),

			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLikeCount",
					query = "select count(a) from Atendimento a " +
							"inner join a.medico md " +
							"where upper(md.nome) like '%' || upper(?) || '%' " 
			),

			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLike",
						query = "select a from Atendimento a " +
								"inner join fetch a.paciente p " +
								"where upper(p.nome) like '%' || upper(?) || '%' " 
								+
								"order by a.dataAtendimento desc"
			),

			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLikeCount",
					query = "select count(a) from Atendimento a " +
							"left outer join a.paciente p " +
							"where upper(p.nome) like '%' || upper(?) || '%' " 
			),

			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentosComPacientePorCodigoPaciente",
						query = "select a from Atendimento a " +
								"inner join fetch a.paciente pa " +
								"where pa.codPaciente = ? " +
								"order by a.dataAtendimento desc"
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentosComPacientePorCodigoPacienteCount",
						query = "select count(a) from Atendimento a " +
								"inner join a.paciente pa " +
								"where pa.codPaciente = ? "
			),

			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentosComPacienteComAnamnesePorCodigoPaciente",
						query = "select a from Atendimento a " +
								"inner join fetch a.anamneses an " +
								"left outer join fetch a.paciente pa " +
								"where pa.codPaciente = ? " +
								"order by a.dataAtendimento desc"
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentosComPacienteComAnamnesePorCodigoPacienteCount",
						query = "select count(a) from Atendimento a " +
								"inner join a.paciente pa " +
								"where pa.codPaciente = ? "
			),
			@NamedQuery(name = "Atendimento.recuperaListaDeAtendimentosComPacienteComAnamnesePorCodigoPaciente",
						query = "select distinct a from Atendimento a " +
								"inner join fetch a.anamneses an " +
								"left outer join fetch a.paciente pa " +
								"where pa.codPaciente = ? " +
								"order by a.dataAtendimento desc"
			),
			@NamedQuery(name = "Atendimento.recuperaListaAtendimento", 
			query = "select a from Atendimento a " +
					"order by a.codAtendimento"
			),
			@NamedQuery(name = "Atendimento.recuperaListaDeAntendimentosParaUmClinico",
						query = "select a from Atendimento a " +
								"where a.medico = ?"
			),
			@NamedQuery(name = "Atendimento.recuperaListaDeAntendimentosParaUmTecnico",
			query = "select a from Atendimento a " +
					"where a.tecnico = ?"
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentosComPacientePorStatus",
						query = "select a from Atendimento a " +
								"inner join fetch a.paciente p " +
								"where a.status = ? " +
								"order by a.dataAtendimento desc"
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentosComPacientePorStatusCount",
						query = "select count(a) from Atendimento a " +
								"where a.status = ?"
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLikePorStatus", 
						query = "select a from Atendimento a " +
								"inner join a.medico md " +
								"left outer join fetch a.paciente p " +
								"where upper(md.nome) like '%' || upper(?) || '%' " +
								"and a.status = ? " +
								"order by a.dataAtendimento desc"
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentoComPacientePorNomeMedicoLikePorStatusCount", 
						query = "select count(a) from Atendimento a " +
								"where upper(a.medico.nome) like '%' || upper(?) || '%' " +
								"and a.status = ?"
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLikePorStatus", 
						query = "select a from Atendimento a " +
								"inner join fetch a.paciente p " +
								"where upper(p.nome) like '%' || upper(?) || '%' " +
								"and a.status = ? " +
								"order by a.dataAtendimento desc"
			),
			@NamedQuery(name = "Atendimento.recuperaListaPaginadaDeAtendimentoComPacientePorNomePacienteLikePorStatusCount", 
						query = "select count(a) from Atendimento a " +
								"where upper(a.paciente.nome) like '%' || upper(?) || '%' " +
								"and a.status = ?"
			)
		}
)

/**
 * 
 * Classe que representa cada um dos atendimentos
 * registrados pelo sistema e suas relações.
 * 
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
	 * Data e Hora do atendimento
	 */
	private Calendar dataAtendimento;
	
	/**
	 * Prognóstico
	 */
	private String prognostico;

	/**
	 * Diagnóstico
	 */
	private String diagnostico;

	/**
	 * Prescrição
	 */
	private String prescricao;
	
	/**
	 * Observações que o médico queira colocar, mas que não se enquadrem nos outros campos.
	 */
	private String observacoes;
	
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
	 * Médico que foi escalado para realizar o atendimento
	 */
	private Usuario medico;
	
	/**
	 * Técnico que realizou ou foi escalado para auxiliar o atendimento
	 */
	private Usuario tecnico;
	
	/**
	 * Anamnese realizada pelo médico. O retorno é uma lista, onde cada
	 * item da lista representa um campo do "questionário de anamneses"
	 * preenchido pelo médico. Essa lista define o conjunto das necessidades do paciente identificadas para cada um dos parâmetros
	 */
	private List<Anamnese> anamneses = new ArrayList<Anamnese>();
	
	/**
	 * Lista do historico de avaliações anteriores aplicadas a esse 
	 * paciente em atendimentos antigos.
	 */
	private List<HistoricoAvaliacao> historicos = new ArrayList<HistoricoAvaliacao>();

	/**
	 * Atributo que não é persistido no banco.
	 * Ele só existe para retornar a tela se um boolean true
	 * caso o atendimento esteja fechado. Essa lógica está implementada
	 * no get do atributo, onde também se encontra a anotação @Transient
	 * que faz com que ele não seja persistido em banco.
	 * 
	 * Esse atributo é utilizado para verificar se o atendimento possui avaliação
	 * guardada no banco, já quê as informações de atendimento encerrados precisam
	 * ser persistidas.
	 */
	private Boolean statusEncerrado;
	
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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "atendimento", cascade=CascadeType.REMOVE)
	public List<Anamnese> getAnamneses() {
		return anamneses;
	}

	public void setAnamneses(List<Anamnese> anamneses) {
		this.anamneses = anamneses;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "atendimento", cascade=CascadeType.REMOVE)
	public List<HistoricoAvaliacao> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<HistoricoAvaliacao> historicos) {
		this.historicos = historicos;
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

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getPrescricao() {
		return prescricao;
	}

	public void setPrescricao(String prescricao) {
		this.prescricao = prescricao;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public void setStatusEcerrado(Boolean status){
		this.statusEncerrado = status;
	}
	
	@Transient
	public boolean isStatusEncerrado(){
		if(this.status.equals("Encerrado")){
			return true;
		}else{
			return false;
		}
			
	}	
	
}
