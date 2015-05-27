package carga;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import modelo.Anamnese;
import modelo.Atendimento;
import modelo.Paciente;
import modelo.Parametro;
import modelo.Usuario;
import service.AnamneseAppService;
import service.AtendimentoAppService;
import service.PacienteAppService;
import service.ParametroAppService;
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;

/**
 * 
 * Sobre a Carga:
 * É uma Carga do sistema, portanto deve herdar de CargaBase e
 * implementar o método executar().
 * Nesse método "executar" é que é chamado pelos outros métodos que são 
 * as etapas dessa carga.
 * Portanto se é necessario rodar um método depois do outro, eles devem ser chamados
 * na ordem correta.  
 * Ex: incluiPaciente() vem antes de incluiAtendimento(), portanto no método executar()
 * 
 * Terminado de executar todas as etapas é preciso retornar true.
 * Se houver algum problema(exceção) na execução de uma das etapas, essa exceção deve ser lancada.
 * 
 * Essa Carga:
 * Responsavel por fazer a carga dos atendimentos contidas nos
 * arquivos de modelagens e dados intermediários do sistema. Já contém informações novas
 * passadas pelo Pedro Peloso.
 * 
 * @author bruno.oliveira
 *
 */
public class CargaAtendimentoIntermediario extends CargaBase {

	// Services
	private static PacienteAppService pacienteService;
	private static AtendimentoAppService atendimentoService;
	private static ParametroAppService parametroService;
	private static AnamneseAppService anamneseService;
	private static UsuarioAppService usuarioService;	
	
	/**
	 * 
	 * Construdor da carga, responsável por instanciar os services.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public CargaAtendimentoIntermediario(){
		try {
			pacienteService = FabricaDeAppService.getAppService(PacienteAppService.class);
			atendimentoService = FabricaDeAppService.getAppService(AtendimentoAppService.class);
			parametroService = FabricaDeAppService.getAppService(ParametroAppService.class);
			anamneseService = FabricaDeAppService
					.getAppService(AnamneseAppService.class);
			usuarioService = FabricaDeAppService
					.getAppService(UsuarioAppService.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * 
	 * Método herdado de CargaBase e que retona uma lista de cargas que esta
	 * carga depende para ser executada de maneira completa.
	 * 
	 * @return lista de dependencias.
	 * 
	 * @author bruno.oliveira
	 * 
	 */	
	@Override
	public List<CargaBase> getCargasDependentes() {
		List<CargaBase> dependencias = new ArrayList<CargaBase>();
		dependencias.add(new CargaIndicacaoIntermediaria());
		dependencias.add(new CargaParametroIntermediario());
		return dependencias;
	}
	
	/**
	 * 
	 * Método herdado de CargaBase e utilizado para definir as etapas
	 * de execução desta carga.
	 * 
	 * @return Boolean - True se não ocorrer nenhum problema (exceção).
	 * @throws AplicacaoException
	 *             Retorna uma AplicacaoException caso ocorra uma exceção deste
	 *             tipo.
	 * 
	 * @author bruno.oliveira
	 * 
	 */	
	@Override
	public boolean executar() throws AplicacaoException {
		this.incluirAtendimentosIntermediarios();
		this.incluirAnamnesesIntermediarios();
		return true;
	}
	
	/**
	 * 
	 * Método que inclui todos os dados de atendimentos que serão
	 * inseridos no banco de dados.
	 * 
	 * @throws AplicacaoException - Retorna uma AplicacaoException caso ocorra algum erro na inclusão de um atendimento.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public void incluirAtendimentosIntermediarios()  throws AplicacaoException{
	  	Calendar dataAtual = Calendar.getInstance();
       	Calendar dataAtual_anterior = Calendar.getInstance();
       	dataAtual_anterior.add(Calendar.DAY_OF_YEAR, -1);
       	
       	ArrayList<Atendimento> atendimentos = new ArrayList<Atendimento>();

		Paciente paciente1pp = pacienteService.recuperaPacientePorCodigo("paciente1pp");
		Paciente paciente2pp = pacienteService.recuperaPacientePorCodigo("paciente2pp");
		Paciente paciente3pp = pacienteService.recuperaPacientePorCodigo("paciente3pp");
		Paciente paciente4pp = pacienteService.recuperaPacientePorCodigo("paciente4pp");
		Paciente paciente5pp = pacienteService.recuperaPacientePorCodigo("paciente5pp");
		Paciente paciente6pp = pacienteService.recuperaPacientePorCodigo("paciente6pp");
		Paciente paciente7pp = pacienteService.recuperaPacientePorCodigo("paciente7pp");
		Paciente paciente8pp = pacienteService.recuperaPacientePorCodigo("paciente8pp");
		Paciente paciente9pp = pacienteService.recuperaPacientePorCodigo("paciente9pp");
		Paciente paciente10pp = pacienteService.recuperaPacientePorCodigo("paciente10pp");

		Usuario clinico = usuarioService.recuperaPorLogin("clinico");
		Usuario tecnico = usuarioService.recuperaPorLogin("tecnico");

		Atendimento atendimentoPaciente1pp = new Atendimento(); 
		atendimentoPaciente1pp.setCodAtendimento("atp1pp");
		atendimentoPaciente1pp.setPaciente(paciente1pp);
		atendimentoPaciente1pp.setDataAtendimento(dataAtual);
		atendimentoPaciente1pp.setMedico(clinico);
		atendimentoPaciente1pp.setStatus("Encerrado");
		atendimentoPaciente1pp.setPrognostico("indeterminado.");
		
		atendimentos.add(atendimentoPaciente1pp);
		
		Atendimento atendimentoPaciente2pp = new Atendimento(); 
		atendimentoPaciente2pp.setCodAtendimento("atp2pp");
		atendimentoPaciente2pp.setPaciente(paciente2pp);
		atendimentoPaciente2pp.setDataAtendimento(dataAtual_anterior);
		atendimentoPaciente2pp.setMedico(clinico);
		atendimentoPaciente2pp.setTecnico(tecnico);
		atendimentoPaciente2pp.setStatus("Encerrado");
		atendimentoPaciente2pp.setPrognostico("indeterminado.");
		
		atendimentos.add(atendimentoPaciente2pp);

		Atendimento atendimentoPaciente3pp = new Atendimento(); 
		atendimentoPaciente3pp.setCodAtendimento("atp3pp");
		atendimentoPaciente3pp.setPaciente(paciente3pp);
		atendimentoPaciente3pp.setDataAtendimento(dataAtual);
		atendimentoPaciente3pp.setMedico(clinico);
		atendimentoPaciente3pp.setStatus("Encerrado");
		atendimentoPaciente3pp.setPrognostico("indeterminado");
		
		atendimentos.add(atendimentoPaciente3pp);
		
		Atendimento atendimentoPaciente4pp = new Atendimento(); 
		atendimentoPaciente4pp.setCodAtendimento("atp4pp");
		atendimentoPaciente4pp.setPaciente(paciente4pp);
		atendimentoPaciente4pp.setDataAtendimento(dataAtual_anterior);
		atendimentoPaciente4pp.setMedico(clinico);
		atendimentoPaciente4pp.setTecnico(tecnico);
		atendimentoPaciente4pp.setStatus("Encerrado");
		atendimentoPaciente4pp.setPrognostico("indeterminado.");
		
		atendimentos.add(atendimentoPaciente4pp);
		
		Atendimento atendimentoPaciente5pp = new Atendimento(); 
		atendimentoPaciente5pp.setCodAtendimento("atp5pp");
		atendimentoPaciente5pp.setPaciente(paciente5pp);
		atendimentoPaciente5pp.setDataAtendimento(dataAtual);
		atendimentoPaciente5pp.setMedico(clinico);
		atendimentoPaciente5pp.setStatus("Encerrado");
		atendimentoPaciente5pp.setPrognostico("indeterminado");

		atendimentos.add(atendimentoPaciente5pp);
		
		Atendimento atendimentoPaciente6pp = new Atendimento(); 
		atendimentoPaciente6pp.setCodAtendimento("atp6pp");
		atendimentoPaciente6pp.setPaciente(paciente6pp);
		atendimentoPaciente6pp.setDataAtendimento(dataAtual_anterior);
		atendimentoPaciente6pp.setMedico(clinico);
		atendimentoPaciente6pp.setTecnico(tecnico);
		atendimentoPaciente6pp.setStatus("Encerrado");
		atendimentoPaciente6pp.setPrognostico("indeterminado.");
		
		atendimentos.add(atendimentoPaciente6pp);
		
		Atendimento atendimentoPaciente7pp = new Atendimento(); 
		atendimentoPaciente7pp.setCodAtendimento("atp7pp");
		atendimentoPaciente7pp.setPaciente(paciente7pp);
		atendimentoPaciente7pp.setDataAtendimento(dataAtual);
		atendimentoPaciente7pp.setMedico(clinico);
		atendimentoPaciente7pp.setStatus("Encerrado");
		atendimentoPaciente7pp.setPrognostico("indeterminado");

		atendimentos.add(atendimentoPaciente7pp);
		
		Atendimento atendimentoPaciente8pp = new Atendimento(); 
		atendimentoPaciente8pp.setCodAtendimento("atp8pp");
		atendimentoPaciente8pp.setPaciente(paciente8pp);
		atendimentoPaciente8pp.setDataAtendimento(dataAtual_anterior);
		atendimentoPaciente8pp.setMedico(clinico);
		atendimentoPaciente8pp.setTecnico(tecnico);
		atendimentoPaciente8pp.setStatus("Encerrado");
		atendimentoPaciente8pp.setPrognostico("indeterminado.");
		
		atendimentos.add(atendimentoPaciente8pp);
		
		Atendimento atendimentoPaciente9pp = new Atendimento(); 
		atendimentoPaciente9pp.setCodAtendimento("atp9pp");
		atendimentoPaciente9pp.setPaciente(paciente9pp);
		atendimentoPaciente9pp.setDataAtendimento(dataAtual);
		atendimentoPaciente9pp.setMedico(clinico);
		atendimentoPaciente9pp.setStatus("Encerrado");
		atendimentoPaciente9pp.setPrognostico("indeterminado");

		atendimentos.add(atendimentoPaciente9pp);
		
		Atendimento atendimentoPaciente10pp = new Atendimento(); 
		atendimentoPaciente10pp.setCodAtendimento("atp10pp");
		atendimentoPaciente10pp.setPaciente(paciente10pp);
		atendimentoPaciente10pp.setDataAtendimento(dataAtual_anterior);
		atendimentoPaciente10pp.setMedico(clinico);
		atendimentoPaciente10pp.setTecnico(tecnico);
		atendimentoPaciente10pp.setStatus("Encerrado");
		atendimentoPaciente10pp.setPrognostico("indeterminado.");
		
		atendimentos.add(atendimentoPaciente10pp);
		
		for(Atendimento atendimento : atendimentos){
			atendimentoService.inclui(atendimento);
		}		
	}
	
	/**
	 * Metodo responsável por preparar e inserir os valores padrões de
	 * Atendimentos no banco de dados
	 * 
	 * @author bruno.oliveira
	 * @throws AplicacaoException
	 */
	public void incluirAnamnesesIntermediarios() throws AplicacaoException {
		ArrayList<Anamnese> anamneses = new ArrayList<Anamnese>();
		
		/* Atendimento dos slides do Pedro Peloso */
		Atendimento atendimentoPaciente1 = atendimentoService.recuperaAtendimentoPorCodigo("atp1");
		Atendimento atendimentoPaciente2 = atendimentoService.recuperaAtendimentoPorCodigo("atp2");
		
		/* Atendimentos dos novos estudos de casos do Pedro Peloso */
		Atendimento atendimentoPaciente1pp = atendimentoService.recuperaAtendimentoPorCodigo("atp1pp");
		Atendimento atendimentoPaciente2pp = atendimentoService.recuperaAtendimentoPorCodigo("atp2pp");
		Atendimento atendimentoPaciente3pp = atendimentoService.recuperaAtendimentoPorCodigo("atp3pp");
		Atendimento atendimentoPaciente4pp = atendimentoService.recuperaAtendimentoPorCodigo("atp4pp");
		Atendimento atendimentoPaciente5pp = atendimentoService.recuperaAtendimentoPorCodigo("atp5pp");
		Atendimento atendimentoPaciente6pp = atendimentoService.recuperaAtendimentoPorCodigo("atp6pp");
		Atendimento atendimentoPaciente7pp = atendimentoService.recuperaAtendimentoPorCodigo("atp7pp");
		Atendimento atendimentoPaciente8pp = atendimentoService.recuperaAtendimentoPorCodigo("atp8pp");
		Atendimento atendimentoPaciente9pp = atendimentoService.recuperaAtendimentoPorCodigo("atp9pp");
		Atendimento atendimentoPaciente10pp = atendimentoService.recuperaAtendimentoPorCodigo("atp10pp");
		
		/* Parametros antigos para serem inseridos só nos atendimentos novos */
		Parametro febre = parametroService.recuperaParametroPorCodigo("P001");
		Parametro disuria = parametroService.recuperaParametroPorCodigo("P002");
		Parametro diabetes = parametroService.recuperaParametroPorCodigo("P003");
		Parametro enterococos = parametroService.recuperaParametroPorCodigo("P004");
		Parametro escherichia = parametroService.recuperaParametroPorCodigo("P005");
		Parametro candida = parametroService.recuperaParametroPorCodigo("P006");
		Parametro efeitosColaterais = parametroService.recuperaParametroPorCodigo("P007");
		Parametro alergia = parametroService.recuperaParametroPorCodigo("P008");
		
		/* Parametros novos para serem inseridos em todos os atendimentos (novos e antigos) */
		Parametro klebsiella = parametroService.recuperaParametroPorCodigo("P009");
		Parametro proteus = parametroService.recuperaParametroPorCodigo("P010");
		Parametro enterobacter = parametroService.recuperaParametroPorCodigo("P011");
		Parametro gravidez = parametroService.recuperaParametroPorCodigo("P012");
		Parametro polaciuria = parametroService.recuperaParametroPorCodigo("P013");
		Parametro desconforto = parametroService.recuperaParametroPorCodigo("P014");
		Parametro infeccao = parametroService.recuperaParametroPorCodigo("P015");
		Parametro usoDeAntibiotico = parametroService.recuperaParametroPorCodigo("P016");
		
		Parametro pseudomonas = parametroService.recuperaParametroPorCodigo("P017");
		Parametro staphylococcus = parametroService.recuperaParametroPorCodigo("P018");
		Parametro streptoccus = parametroService.recuperaParametroPorCodigo("P019");
		Parametro citrobacter = parametroService.recuperaParametroPorCodigo("P020");
		Parametro staphylococcusEp = parametroService.recuperaParametroPorCodigo("P021");
		Parametro enterobacterClo = parametroService.recuperaParametroPorCodigo("P022");
		Parametro citrobacterFre = parametroService.recuperaParametroPorCodigo("P023");
		Parametro morganella = parametroService.recuperaParametroPorCodigo("P024");
		Parametro staphycoccusAur = parametroService.recuperaParametroPorCodigo("P025");
		Parametro serratia = parametroService.recuperaParametroPorCodigo("P026");
		Parametro imunossupressao = parametroService.recuperaParametroPorCodigo("P027");
		
		/* Inserido a anamnese dos parametros antigos nos atendimentos novos. */
		Anamnese atendimentoPc1_febre = new Anamnese(atendimentoPaciente1pp, febre, 0.7);
			anamneses.add(atendimentoPc1_febre);
		Anamnese atendimentoPc1_disuria = new Anamnese(atendimentoPaciente1pp, disuria, 0.8);
			anamneses.add(atendimentoPc1_disuria);
		Anamnese atendimentoPc1_diabetes = new Anamnese(atendimentoPaciente1pp, diabetes, 0.7);
			anamneses.add(atendimentoPc1_diabetes);
		Anamnese atendimentoPc1_enterococos = new Anamnese(atendimentoPaciente1pp, enterococos, 0.0);
			anamneses.add(atendimentoPc1_enterococos);
		Anamnese atendimentoPc1_escherichia = new Anamnese(atendimentoPaciente1pp, escherichia, 0.0);
			anamneses.add(atendimentoPc1_escherichia);
		Anamnese atendimentoPc1_candida = new Anamnese(atendimentoPaciente1pp, candida, 1.0);
			anamneses.add(atendimentoPc1_candida);
		Anamnese atendimentoPc1_efeitosColaterais = new Anamnese(atendimentoPaciente1pp, efeitosColaterais, 1.0);
			anamneses.add(atendimentoPc1_efeitosColaterais);
		Anamnese atendimentoPc1_alergia = new Anamnese(atendimentoPaciente1pp, alergia, 1.0);
			anamneses.add(atendimentoPc1_alergia);
		
		Anamnese atendimentoPc2_febre = new Anamnese(atendimentoPaciente2pp, febre, 0.7);
			anamneses.add(atendimentoPc2_febre);
		Anamnese atendimentoPc2_disuria = new Anamnese(atendimentoPaciente2pp, disuria, 0.8);
			anamneses.add(atendimentoPc2_disuria);
		Anamnese atendimentoPc2_diabetes = new Anamnese(atendimentoPaciente2pp, diabetes, 0.7);
			anamneses.add(atendimentoPc2_diabetes);
		Anamnese atendimentoPc2_enterococos = new Anamnese(atendimentoPaciente2pp, enterococos, 0.0);
			anamneses.add(atendimentoPc2_enterococos);
		Anamnese atendimentoPc2_escherichia = new Anamnese(atendimentoPaciente2pp, escherichia, 0.0);
			anamneses.add(atendimentoPc2_escherichia);
		Anamnese atendimentoPc2_candida = new Anamnese(atendimentoPaciente2pp, candida, 1.0);
			anamneses.add(atendimentoPc2_candida);
		Anamnese atendimentoPc2_efeitosColaterais = new Anamnese(atendimentoPaciente2pp, efeitosColaterais, 1.0);
			anamneses.add(atendimentoPc2_efeitosColaterais);
		Anamnese atendimentoPc2_alergia = new Anamnese(atendimentoPaciente2pp, alergia, 1.0);
			anamneses.add(atendimentoPc2_alergia);
		
		Anamnese atendimentoPc3_febre = new Anamnese(atendimentoPaciente3pp, febre, 0.7);
			anamneses.add(atendimentoPc3_febre);
		Anamnese atendimentoPc3_disuria = new Anamnese(atendimentoPaciente3pp, disuria, 0.8);
			anamneses.add(atendimentoPc3_disuria);
		Anamnese atendimentoPc3_diabetes = new Anamnese(atendimentoPaciente3pp, diabetes, 0.7);
			anamneses.add(atendimentoPc3_diabetes);
		Anamnese atendimentoPc3_enterococos = new Anamnese(atendimentoPaciente3pp, enterococos, 0.0);
			anamneses.add(atendimentoPc3_enterococos);
		Anamnese atendimentoPc3_escherichia = new Anamnese(atendimentoPaciente3pp, escherichia, 0.0);
			anamneses.add(atendimentoPc3_escherichia);
		Anamnese atendimentoPc3_candida = new Anamnese(atendimentoPaciente3pp, candida, 1.0);
			anamneses.add(atendimentoPc3_candida);
		Anamnese atendimentoPc3_efeitosColaterais = new Anamnese(atendimentoPaciente3pp, efeitosColaterais, 1.0);
			anamneses.add(atendimentoPc3_efeitosColaterais);
		Anamnese atendimentoPc3_alergia = new Anamnese(atendimentoPaciente3pp, alergia, 1.0);
			anamneses.add(atendimentoPc3_alergia);
		
		Anamnese atendimentoPc4_febre = new Anamnese(atendimentoPaciente4pp, febre, 0.7);
			anamneses.add(atendimentoPc4_febre);
		Anamnese atendimentoPc4_disuria = new Anamnese(atendimentoPaciente4pp, disuria, 0.8);
			anamneses.add(atendimentoPc4_disuria);
		Anamnese atendimentoPc4_diabetes = new Anamnese(atendimentoPaciente4pp, diabetes, 0.7);
			anamneses.add(atendimentoPc4_diabetes);
		Anamnese atendimentoPc4_enterococos = new Anamnese(atendimentoPaciente4pp, enterococos, 0.0);
			anamneses.add(atendimentoPc4_enterococos);
		Anamnese atendimentoPc4_escherichia = new Anamnese(atendimentoPaciente4pp, escherichia, 0.0);
			anamneses.add(atendimentoPc4_escherichia);
		Anamnese atendimentoPc4_candida = new Anamnese(atendimentoPaciente4pp, candida, 1.0);
			anamneses.add(atendimentoPc4_candida);
		Anamnese atendimentoPc4_efeitosColaterais = new Anamnese(atendimentoPaciente4pp, efeitosColaterais, 1.0);
			anamneses.add(atendimentoPc4_efeitosColaterais);
		Anamnese atendimentoPc4_alergia = new Anamnese(atendimentoPaciente4pp, alergia, 1.0);
			anamneses.add(atendimentoPc4_alergia);
		
		Anamnese atendimentoPc5_febre = new Anamnese(atendimentoPaciente5pp, febre, 0.7);
			anamneses.add(atendimentoPc5_febre);
		Anamnese atendimentoPc5_disuria = new Anamnese(atendimentoPaciente5pp, disuria, 0.8);
			anamneses.add(atendimentoPc5_disuria);
		Anamnese atendimentoPc5_diabetes = new Anamnese(atendimentoPaciente5pp, diabetes, 0.7);
			anamneses.add(atendimentoPc5_diabetes);
		Anamnese atendimentoPc5_enterococos = new Anamnese(atendimentoPaciente5pp, enterococos, 0.0);
			anamneses.add(atendimentoPc5_enterococos);
		Anamnese atendimentoPc5_escherichia = new Anamnese(atendimentoPaciente5pp, escherichia, 0.0);
			anamneses.add(atendimentoPc5_escherichia);
		Anamnese atendimentoPc5_candida = new Anamnese(atendimentoPaciente5pp, candida, 1.0);
			anamneses.add(atendimentoPc5_candida);
		Anamnese atendimentoPc5_efeitosColaterais = new Anamnese(atendimentoPaciente5pp, efeitosColaterais, 1.0);
			anamneses.add(atendimentoPc5_efeitosColaterais);
		Anamnese atendimentoPc5_alergia = new Anamnese(atendimentoPaciente5pp, alergia, 1.0);
			anamneses.add(atendimentoPc5_alergia);
		
		Anamnese atendimentoPc6_febre = new Anamnese(atendimentoPaciente6pp, febre, 0.7);
			anamneses.add(atendimentoPc6_febre);
		Anamnese atendimentoPc6_disuria = new Anamnese(atendimentoPaciente6pp, disuria, 0.8);
			anamneses.add(atendimentoPc6_disuria);
		Anamnese atendimentoPc6_diabetes = new Anamnese(atendimentoPaciente6pp, diabetes, 0.7);
			anamneses.add(atendimentoPc6_diabetes);
		Anamnese atendimentoPc6_enterococos = new Anamnese(atendimentoPaciente6pp, enterococos, 0.0);
			anamneses.add(atendimentoPc6_enterococos);
		Anamnese atendimentoPc6_escherichia = new Anamnese(atendimentoPaciente6pp, escherichia, 0.0);
			anamneses.add(atendimentoPc6_escherichia);
		Anamnese atendimentoPc6_candida = new Anamnese(atendimentoPaciente6pp, candida, 1.0);
			anamneses.add(atendimentoPc6_candida);
		Anamnese atendimentoPc6_efeitosColaterais = new Anamnese(atendimentoPaciente6pp, efeitosColaterais, 1.0);
			anamneses.add(atendimentoPc6_efeitosColaterais);
		Anamnese atendimentoPc6_alergia = new Anamnese(atendimentoPaciente6pp, alergia, 1.0);
			anamneses.add(atendimentoPc6_alergia);
		
		Anamnese atendimentoPc7_febre = new Anamnese(atendimentoPaciente7pp, febre, 0.7);
			anamneses.add(atendimentoPc7_febre);
		Anamnese atendimentoPc7_disuria = new Anamnese(atendimentoPaciente7pp, disuria, 0.8);
			anamneses.add(atendimentoPc7_disuria);
		Anamnese atendimentoPc7_diabetes = new Anamnese(atendimentoPaciente7pp, diabetes, 0.7);
			anamneses.add(atendimentoPc7_diabetes);
		Anamnese atendimentoPc7_enterococos = new Anamnese(atendimentoPaciente7pp, enterococos, 0.0);
			anamneses.add(atendimentoPc7_enterococos);
		Anamnese atendimentoPc7_escherichia = new Anamnese(atendimentoPaciente7pp, escherichia, 0.0);
			anamneses.add(atendimentoPc7_escherichia);
		Anamnese atendimentoPc7_candida = new Anamnese(atendimentoPaciente7pp, candida, 1.0);
			anamneses.add(atendimentoPc7_candida);
		Anamnese atendimentoPc7_efeitosColaterais = new Anamnese(atendimentoPaciente7pp, efeitosColaterais, 1.0);
			anamneses.add(atendimentoPc7_efeitosColaterais);
		Anamnese atendimentoPc7_alergia = new Anamnese(atendimentoPaciente7pp, alergia, 1.0);
			anamneses.add(atendimentoPc7_alergia);
		
		Anamnese atendimentoPc8_febre = new Anamnese(atendimentoPaciente8pp, febre, 0.7);
			anamneses.add(atendimentoPc8_febre);
		Anamnese atendimentoPc8_disuria = new Anamnese(atendimentoPaciente8pp, disuria, 0.8);
			anamneses.add(atendimentoPc8_disuria);
		Anamnese atendimentoPc8_diabetes = new Anamnese(atendimentoPaciente8pp, diabetes, 0.7);
			anamneses.add(atendimentoPc8_diabetes);
		Anamnese atendimentoPc8_enterococos = new Anamnese(atendimentoPaciente8pp, enterococos, 0.0);
			anamneses.add(atendimentoPc8_enterococos);
		Anamnese atendimentoPc8_escherichia = new Anamnese(atendimentoPaciente8pp, escherichia, 0.0);
			anamneses.add(atendimentoPc8_escherichia);
		Anamnese atendimentoPc8_candida = new Anamnese(atendimentoPaciente8pp, candida, 1.0);
			anamneses.add(atendimentoPc8_candida);
		Anamnese atendimentoPc8_efeitosColaterais = new Anamnese(atendimentoPaciente8pp, efeitosColaterais, 1.0);
			anamneses.add(atendimentoPc8_efeitosColaterais);
		Anamnese atendimentoPc8_alergia = new Anamnese(atendimentoPaciente8pp, alergia, 1.0);
			anamneses.add(atendimentoPc8_alergia);
		
		Anamnese atendimentoPc9_febre = new Anamnese(atendimentoPaciente9pp, febre, 0.7);
			anamneses.add(atendimentoPc9_febre);
		Anamnese atendimentoPc9_disuria = new Anamnese(atendimentoPaciente9pp, disuria, 0.8);
			anamneses.add(atendimentoPc9_disuria);
		Anamnese atendimentoPc9_diabetes = new Anamnese(atendimentoPaciente9pp, diabetes, 0.7);
			anamneses.add(atendimentoPc9_diabetes);
		Anamnese atendimentoPc9_enterococos = new Anamnese(atendimentoPaciente9pp, enterococos, 0.0);
			anamneses.add(atendimentoPc9_enterococos);
		Anamnese atendimentoPc9_escherichia = new Anamnese(atendimentoPaciente9pp, escherichia, 0.0);
			anamneses.add(atendimentoPc9_escherichia);
		Anamnese atendimentoPc9_candida = new Anamnese(atendimentoPaciente9pp, candida, 1.0);
			anamneses.add(atendimentoPc9_candida);
		Anamnese atendimentoPc9_efeitosColaterais = new Anamnese(atendimentoPaciente9pp, efeitosColaterais, 1.0);
			anamneses.add(atendimentoPc9_efeitosColaterais);
		Anamnese atendimentoPc9_alergia = new Anamnese(atendimentoPaciente9pp, alergia, 1.0);
			anamneses.add(atendimentoPc9_alergia);
		
		Anamnese atendimentoPc10_febre = new Anamnese(atendimentoPaciente10pp, febre, 0.7);
			anamneses.add(atendimentoPc10_febre);
		Anamnese atendimentoPc10_disuria = new Anamnese(atendimentoPaciente10pp, disuria, 0.8);
			anamneses.add(atendimentoPc10_disuria);
		Anamnese atendimentoPc10_diabetes = new Anamnese(atendimentoPaciente10pp, diabetes, 0.7);
			anamneses.add(atendimentoPc10_diabetes);
		Anamnese atendimentoPc10_enterococos = new Anamnese(atendimentoPaciente10pp, enterococos, 0.0);
			anamneses.add(atendimentoPc10_enterococos);
		Anamnese atendimentoPc10_escherichia = new Anamnese(atendimentoPaciente10pp, escherichia, 0.0);
			anamneses.add(atendimentoPc10_escherichia);
		Anamnese atendimentoPc10_candida = new Anamnese(atendimentoPaciente10pp, candida, 1.0);
			anamneses.add(atendimentoPc10_candida);
		Anamnese atendimentoPc10_efeitosColaterais = new Anamnese(atendimentoPaciente10pp, efeitosColaterais, 1.0);
			anamneses.add(atendimentoPc10_efeitosColaterais);
		Anamnese atendimentoPc10_alergia = new Anamnese(atendimentoPaciente10pp, alergia, 1.0);
			anamneses.add(atendimentoPc10_alergia);
		
		/* Complemento dos atendimentos de exemplo dos slides iniciais do Pedro Peloso */
		/* colocando parametros novos nos atendimentos antigos*/	
		Anamnese atendimentoPc1_klebsiella = new Anamnese(atendimentoPaciente1, klebsiella, 0.0);
			anamneses.add(atendimentoPc1_klebsiella);
		Anamnese atendimentoPc1_proteus = new Anamnese(atendimentoPaciente1, proteus, 0.0);
			anamneses.add(atendimentoPc1_proteus);
		Anamnese atendimentoPc1_enterobacter = new Anamnese(atendimentoPaciente1, enterobacter, 0.0);
			anamneses.add(atendimentoPc1_enterobacter);
		Anamnese atendimentoPc1_gravidez = new Anamnese(atendimentoPaciente1, gravidez, 0.0);
			anamneses.add(atendimentoPc1_gravidez);
		Anamnese atendimentoPc1_polaciuria = new Anamnese(atendimentoPaciente1, polaciuria, 0.0);
			anamneses.add(atendimentoPc1_polaciuria);
		Anamnese atendimentoPc1_desconforto = new Anamnese(atendimentoPaciente1, desconforto, 0.0);
			anamneses.add(atendimentoPc1_desconforto);
		Anamnese atendimentoPc1_infeccao = new Anamnese(atendimentoPaciente1, infeccao, 0.0);
			anamneses.add(atendimentoPc1_infeccao);
		Anamnese atendimentoPc1_usoDeAntibiotico = new Anamnese(atendimentoPaciente1, usoDeAntibiotico, 0.0);
			anamneses.add(atendimentoPc1_usoDeAntibiotico);
			
		Anamnese atendimentoPc1_pseudomonas = new Anamnese(atendimentoPaciente1, pseudomonas, 0.0);
			anamneses.add(atendimentoPc1_pseudomonas);
		Anamnese atendimentoPc1_staphylococcus = new Anamnese(atendimentoPaciente1, staphylococcus, 0.0);
			anamneses.add(atendimentoPc1_staphylococcus);
		Anamnese atendimentoPc1_streptoccus = new Anamnese(atendimentoPaciente1, streptoccus, 0.0);
			anamneses.add(atendimentoPc1_streptoccus);
		Anamnese atendimentoPc1_citrobacter = new Anamnese(atendimentoPaciente1, citrobacter, 0.0);
			anamneses.add(atendimentoPc1_citrobacter);
		Anamnese atendimentoPc1_staphylococcusEp = new Anamnese(atendimentoPaciente1, staphylococcusEp, 0.0);
			anamneses.add(atendimentoPc1_staphylococcusEp);
		Anamnese atendimentoPc1_enterobacterClo = new Anamnese(atendimentoPaciente1, enterobacterClo, 0.0);
			anamneses.add(atendimentoPc1_enterobacterClo);
		Anamnese atendimentoPc1_citrobacterFre = new Anamnese(atendimentoPaciente1, citrobacterFre, 0.0);
			anamneses.add(atendimentoPc1_citrobacterFre);
		Anamnese atendimentoPc1_morganella = new Anamnese(atendimentoPaciente1, morganella, 0.0);
			anamneses.add(atendimentoPc1_morganella);
		Anamnese atendimentoPc1_staphycoccusAur = new Anamnese(atendimentoPaciente1, staphycoccusAur, 0.0);
			anamneses.add(atendimentoPc1_staphycoccusAur);	
		Anamnese atendimentoPc1_serratia = new Anamnese(atendimentoPaciente1, serratia, 0.0);
			anamneses.add(atendimentoPc1_serratia);
		Anamnese atendimentoPc1_imunossupressao = new Anamnese(atendimentoPaciente1, imunossupressao, 0.0);
			anamneses.add(atendimentoPc1_imunossupressao);
			
		  
		Anamnese atendimentoPc2_klebsiella = new Anamnese(atendimentoPaciente2, klebsiella, 0.0);
			anamneses.add(atendimentoPc2_klebsiella);
		Anamnese atendimentoPc2_proteus = new Anamnese(atendimentoPaciente2, proteus, 0.0);
			anamneses.add(atendimentoPc2_proteus);
		Anamnese atendimentoPc2_enterobacter = new Anamnese(atendimentoPaciente2, enterobacter, 0.0);
			anamneses.add(atendimentoPc2_enterobacter);
		Anamnese atendimentoPc2_gravidez = new Anamnese(atendimentoPaciente2, gravidez, 0.0);
			anamneses.add(atendimentoPc2_gravidez);
		Anamnese atendimentoPc2_polaciuria = new Anamnese(atendimentoPaciente2, polaciuria, 0.0);
			anamneses.add(atendimentoPc2_polaciuria);
		Anamnese atendimentoPc2_desconforto = new Anamnese(atendimentoPaciente2, desconforto, 0.0);
			anamneses.add(atendimentoPc2_desconforto);
		Anamnese atendimentoPc2_infeccao = new Anamnese(atendimentoPaciente2, infeccao, 0.0);
			anamneses.add(atendimentoPc2_infeccao);
		Anamnese atendimentoPc2_usoDeAntibiotico = new Anamnese(atendimentoPaciente2, usoDeAntibiotico, 0.0);
			anamneses.add(atendimentoPc2_usoDeAntibiotico);
			
		Anamnese atendimentoPc2_pseudomonas = new Anamnese(atendimentoPaciente2, pseudomonas, 0.0);
			anamneses.add(atendimentoPc2_pseudomonas);
		Anamnese atendimentoPc2_staphylococcus = new Anamnese(atendimentoPaciente2, staphylococcus, 0.0);
			anamneses.add(atendimentoPc2_staphylococcus);
		Anamnese atendimentoPc2_streptoccus = new Anamnese(atendimentoPaciente2, streptoccus, 0.0);
			anamneses.add(atendimentoPc2_streptoccus);
		Anamnese atendimentoPc2_citrobacter = new Anamnese(atendimentoPaciente2, citrobacter, 0.0);
			anamneses.add(atendimentoPc2_citrobacter);
		Anamnese atendimentoPc2_staphylococcusEp = new Anamnese(atendimentoPaciente2, staphylococcusEp, 0.0);
			anamneses.add(atendimentoPc2_staphylococcusEp);
		Anamnese atendimentoPc2_enterobacterClo = new Anamnese(atendimentoPaciente2, enterobacterClo, 0.0);
			anamneses.add(atendimentoPc2_enterobacterClo);
		Anamnese atendimentoPc2_citrobacterFre = new Anamnese(atendimentoPaciente2, citrobacterFre, 0.0);
			anamneses.add(atendimentoPc2_citrobacterFre);
		Anamnese atendimentoPc2_morganella = new Anamnese(atendimentoPaciente2, morganella, 0.0);
			anamneses.add(atendimentoPc2_morganella);
		Anamnese atendimentoPc2_staphycoccusAur = new Anamnese(atendimentoPaciente2, staphycoccusAur, 0.0);
			anamneses.add(atendimentoPc2_staphycoccusAur);	
		Anamnese atendimentoPc2_serratia = new Anamnese(atendimentoPaciente2, serratia, 0.0);
			anamneses.add(atendimentoPc2_serratia);			
		Anamnese atendimentoPc2_imunossupressao = new Anamnese(atendimentoPaciente2, imunossupressao, 0.0);
			anamneses.add(atendimentoPc2_imunossupressao);

		/* Novos atendimentos fornecidos pelo Pedro Peloso */	
		/* Novo paciente 1 */
		Anamnese atendimentoPc1a_klebsiella = new Anamnese(atendimentoPaciente1pp, klebsiella, 0.0);
			anamneses.add(atendimentoPc1a_klebsiella);
		Anamnese atendimentoPc1a_proteus = new Anamnese(atendimentoPaciente1pp, proteus, 0.0);
			anamneses.add(atendimentoPc1a_proteus);
		Anamnese atendimentoPc1a_enterobacter = new Anamnese(atendimentoPaciente1pp, enterobacter, 0.0);
			anamneses.add(atendimentoPc1a_enterobacter);
		Anamnese atendimentoPc1a_gravidez = new Anamnese(atendimentoPaciente1pp, gravidez, 0.0);
			anamneses.add(atendimentoPc1a_gravidez);
		Anamnese atendimentoPc1a_polaciuria = new Anamnese(atendimentoPaciente1pp, polaciuria, 0.0);
			anamneses.add(atendimentoPc1a_polaciuria);
		Anamnese atendimentoPc1a_desconforto = new Anamnese(atendimentoPaciente1pp, desconforto, 0.0);
			anamneses.add(atendimentoPc1a_desconforto);
		Anamnese atendimentoPc1a_infeccao = new Anamnese(atendimentoPaciente1pp, infeccao, 0.0);
			anamneses.add(atendimentoPc1a_infeccao);
		Anamnese atendimentoPc1a_usoDeAntibiotico = new Anamnese(atendimentoPaciente1pp, usoDeAntibiotico, 0.0);
			anamneses.add(atendimentoPc1a_usoDeAntibiotico);
			
		Anamnese atendimentoPc1a_pseudomonas = new Anamnese(atendimentoPaciente1pp, pseudomonas, 0.0);
			anamneses.add(atendimentoPc1a_pseudomonas);
		Anamnese atendimentoPc1a_staphylococcus = new Anamnese(atendimentoPaciente1pp, staphylococcus, 0.0);
			anamneses.add(atendimentoPc1a_staphylococcus);
		Anamnese atendimentoPc1a_streptoccus = new Anamnese(atendimentoPaciente1pp, streptoccus, 0.0);
			anamneses.add(atendimentoPc1a_streptoccus);
		Anamnese atendimentoPc1a_citrobacter = new Anamnese(atendimentoPaciente1pp, citrobacter, 0.0);
			anamneses.add(atendimentoPc1a_citrobacter);
		Anamnese atendimentoPc1a_staphylococcusEp = new Anamnese(atendimentoPaciente1pp, staphylococcusEp, 0.0);
			anamneses.add(atendimentoPc1a_staphylococcusEp);
		Anamnese atendimentoPc1a_enterobacterClo = new Anamnese(atendimentoPaciente1pp, enterobacterClo, 0.0);
			anamneses.add(atendimentoPc1a_enterobacterClo);
		Anamnese atendimentoPc1a_citrobacterFre = new Anamnese(atendimentoPaciente1pp, citrobacterFre, 0.0);
			anamneses.add(atendimentoPc1a_citrobacterFre);
		Anamnese atendimentoPc1a_morganella = new Anamnese(atendimentoPaciente1pp, morganella, 0.0);
			anamneses.add(atendimentoPc1a_morganella);
		Anamnese atendimentoPc1a_staphycoccusAur = new Anamnese(atendimentoPaciente1pp, staphycoccusAur, 0.0);
			anamneses.add(atendimentoPc1a_staphycoccusAur);	
		Anamnese atendimentoPc1a_serratia = new Anamnese(atendimentoPaciente1pp, serratia, 0.0);
			anamneses.add(atendimentoPc1a_serratia);				

		/* Novo paciente 2 */
		Anamnese atendimentoPc2a_klebsiella = new Anamnese(atendimentoPaciente2pp, klebsiella, 0.0);
			anamneses.add(atendimentoPc2a_klebsiella);
		Anamnese atendimentoPc2a_proteus = new Anamnese(atendimentoPaciente2pp, proteus, 0.0);
			anamneses.add(atendimentoPc2a_proteus);
		Anamnese atendimentoPc2a_enterobacter = new Anamnese(atendimentoPaciente2pp, enterobacter, 0.0);
			anamneses.add(atendimentoPc2a_enterobacter);
		Anamnese atendimentoPc2a_gravidez = new Anamnese(atendimentoPaciente2pp, gravidez, 0.0);
			anamneses.add(atendimentoPc2a_gravidez);
		Anamnese atendimentoPc2a_polaciuria = new Anamnese(atendimentoPaciente2pp, polaciuria, 0.0);
			anamneses.add(atendimentoPc2a_polaciuria);
		Anamnese atendimentoPc2a_desconforto = new Anamnese(atendimentoPaciente2pp, desconforto, 0.0);
			anamneses.add(atendimentoPc2a_desconforto);
		Anamnese atendimentoPc2a_infeccao = new Anamnese(atendimentoPaciente2pp, infeccao, 0.0);
			anamneses.add(atendimentoPc2a_infeccao);
		Anamnese atendimentoPc2a_usoDeAntibiotico = new Anamnese(atendimentoPaciente2pp, usoDeAntibiotico, 0.0);
			anamneses.add(atendimentoPc2a_usoDeAntibiotico);
			
		Anamnese atendimentoPc2a_pseudomonas = new Anamnese(atendimentoPaciente2pp, pseudomonas, 0.0);
			anamneses.add(atendimentoPc2a_pseudomonas);
		Anamnese atendimentoPc2a_staphylococcus = new Anamnese(atendimentoPaciente2pp, staphylococcus, 0.0);
			anamneses.add(atendimentoPc2a_staphylococcus);
		Anamnese atendimentoPc2a_streptoccus = new Anamnese(atendimentoPaciente2pp, streptoccus, 0.0);
			anamneses.add(atendimentoPc2a_streptoccus);
		Anamnese atendimentoPc2a_citrobacter = new Anamnese(atendimentoPaciente2pp, citrobacter, 0.0);
			anamneses.add(atendimentoPc2a_citrobacter);
		Anamnese atendimentoPc2a_staphylococcusEp = new Anamnese(atendimentoPaciente2pp, staphylococcusEp, 0.0);
			anamneses.add(atendimentoPc2a_staphylococcusEp);
		Anamnese atendimentoPc2a_enterobacterClo = new Anamnese(atendimentoPaciente2pp, enterobacterClo, 0.0);
			anamneses.add(atendimentoPc2a_enterobacterClo);
		Anamnese atendimentoPc2a_citrobacterFre = new Anamnese(atendimentoPaciente2pp, citrobacterFre, 0.0);
			anamneses.add(atendimentoPc2a_citrobacterFre);
		Anamnese atendimentoPc2a_morganella = new Anamnese(atendimentoPaciente2pp, morganella, 0.0);
			anamneses.add(atendimentoPc2a_morganella);
		Anamnese atendimentoPc2a_staphycoccusAur = new Anamnese(atendimentoPaciente2pp, staphycoccusAur, 0.0);
			anamneses.add(atendimentoPc2a_staphycoccusAur);	
		Anamnese atendimentoPc2a_serratia = new Anamnese(atendimentoPaciente2pp, serratia, 0.0);
			anamneses.add(atendimentoPc2a_serratia);	
			
		/* Novo paciente 3 */
		Anamnese atendimentoPc3a_klebsiella = new Anamnese(atendimentoPaciente3pp, klebsiella, 0.0);
			anamneses.add(atendimentoPc3a_klebsiella);
		Anamnese atendimentoPc3a_proteus = new Anamnese(atendimentoPaciente3pp, proteus, 0.0);
			anamneses.add(atendimentoPc3a_proteus);
		Anamnese atendimentoPc3a_enterobacter = new Anamnese(atendimentoPaciente3pp, enterobacter, 0.0);
			anamneses.add(atendimentoPc3a_enterobacter);
		Anamnese atendimentoPc3a_gravidez = new Anamnese(atendimentoPaciente3pp, gravidez, 0.0);
			anamneses.add(atendimentoPc3a_gravidez);
		Anamnese atendimentoPc3a_polaciuria = new Anamnese(atendimentoPaciente3pp, polaciuria, 0.0);
			anamneses.add(atendimentoPc3a_polaciuria);
		Anamnese atendimentoPc3a_desconforto = new Anamnese(atendimentoPaciente3pp, desconforto, 0.0);
			anamneses.add(atendimentoPc3a_desconforto);
		Anamnese atendimentoPc3a_infeccao = new Anamnese(atendimentoPaciente3pp, infeccao, 0.0);
			anamneses.add(atendimentoPc3a_infeccao);
		Anamnese atendimentoPc3a_usoDeAntibiotico = new Anamnese(atendimentoPaciente3pp, usoDeAntibiotico, 0.0);
			anamneses.add(atendimentoPc3a_usoDeAntibiotico);
			
		Anamnese atendimentoPc3a_pseudomonas = new Anamnese(atendimentoPaciente3pp, pseudomonas, 0.0);
			anamneses.add(atendimentoPc3a_pseudomonas);
		Anamnese atendimentoPc3a_staphylococcus = new Anamnese(atendimentoPaciente3pp, staphylococcus, 0.0);
			anamneses.add(atendimentoPc3a_staphylococcus);
		Anamnese atendimentoPc3a_streptoccus = new Anamnese(atendimentoPaciente3pp, streptoccus, 0.0);
			anamneses.add(atendimentoPc3a_streptoccus);
		Anamnese atendimentoPc3a_citrobacter = new Anamnese(atendimentoPaciente3pp, citrobacter, 0.0);
			anamneses.add(atendimentoPc3a_citrobacter);
		Anamnese atendimentoPc3a_staphylococcusEp = new Anamnese(atendimentoPaciente3pp, staphylococcusEp, 0.0);
			anamneses.add(atendimentoPc3a_staphylococcusEp);
		Anamnese atendimentoPc3a_enterobacterClo = new Anamnese(atendimentoPaciente3pp, enterobacterClo, 0.0);
			anamneses.add(atendimentoPc3a_enterobacterClo);
		Anamnese atendimentoPc3a_citrobacterFre = new Anamnese(atendimentoPaciente3pp, citrobacterFre, 0.0);
			anamneses.add(atendimentoPc3a_citrobacterFre);
		Anamnese atendimentoPc3a_morganella = new Anamnese(atendimentoPaciente3pp, morganella, 0.0);
			anamneses.add(atendimentoPc3a_morganella);
		Anamnese atendimentoPc3a_staphycoccusAur = new Anamnese(atendimentoPaciente3pp, staphycoccusAur, 0.0);
			anamneses.add(atendimentoPc3a_staphycoccusAur);	
		Anamnese atendimentoPc3a_serratia = new Anamnese(atendimentoPaciente3pp, serratia, 0.0);
			anamneses.add(atendimentoPc3a_serratia);
			
		/* Novo paciente 4 */	
		Anamnese atendimentoPc4a_klebsiella = new Anamnese(atendimentoPaciente4pp, klebsiella, 0.0);
			anamneses.add(atendimentoPc4a_klebsiella);
		Anamnese atendimentoPc4a_proteus = new Anamnese(atendimentoPaciente4pp, proteus, 0.0);
			anamneses.add(atendimentoPc4a_proteus);
		Anamnese atendimentoPc4a_enterobacter = new Anamnese(atendimentoPaciente4pp, enterobacter, 0.0);
			anamneses.add(atendimentoPc4a_enterobacter);
		Anamnese atendimentoPc4a_gravidez = new Anamnese(atendimentoPaciente4pp, gravidez, 0.0);
			anamneses.add(atendimentoPc4a_gravidez);
		Anamnese atendimentoPc4a_polaciuria = new Anamnese(atendimentoPaciente4pp, polaciuria, 0.0);
			anamneses.add(atendimentoPc4a_polaciuria);
		Anamnese atendimentoPc4a_desconforto = new Anamnese(atendimentoPaciente4pp, desconforto, 0.0);
			anamneses.add(atendimentoPc4a_desconforto);
		Anamnese atendimentoPc4a_infeccao = new Anamnese(atendimentoPaciente4pp, infeccao, 0.0);
			anamneses.add(atendimentoPc4a_infeccao);
		Anamnese atendimentoPc4a_usoDeAntibiotico = new Anamnese(atendimentoPaciente4pp, usoDeAntibiotico, 0.0);
			anamneses.add(atendimentoPc4a_usoDeAntibiotico);

		Anamnese atendimentoPc4a_pseudomonas = new Anamnese(atendimentoPaciente4pp, pseudomonas, 0.0);
			anamneses.add(atendimentoPc4a_pseudomonas);
		Anamnese atendimentoPc4a_staphylococcus = new Anamnese(atendimentoPaciente4pp, staphylococcus, 0.0);
			anamneses.add(atendimentoPc4a_staphylococcus);
		Anamnese atendimentoPc4a_streptoccus = new Anamnese(atendimentoPaciente4pp, streptoccus, 0.0);
			anamneses.add(atendimentoPc4a_streptoccus);
		Anamnese atendimentoPc4a_citrobacter = new Anamnese(atendimentoPaciente4pp, citrobacter, 0.0);
			anamneses.add(atendimentoPc4a_citrobacter);
		Anamnese atendimentoPc4a_staphylococcusEp = new Anamnese(atendimentoPaciente4pp, staphylococcusEp, 0.0);
			anamneses.add(atendimentoPc4a_staphylococcusEp);
		Anamnese atendimentoPc4a_enterobacterClo = new Anamnese(atendimentoPaciente4pp, enterobacterClo, 0.0);
			anamneses.add(atendimentoPc4a_enterobacterClo);
		Anamnese atendimentoPc4a_citrobacterFre = new Anamnese(atendimentoPaciente4pp, citrobacterFre, 0.0);
			anamneses.add(atendimentoPc4a_citrobacterFre);
		Anamnese atendimentoPc4a_morganella = new Anamnese(atendimentoPaciente4pp, morganella, 0.0);
			anamneses.add(atendimentoPc4a_morganella);
		Anamnese atendimentoPc4a_staphycoccusAur = new Anamnese(atendimentoPaciente4pp, staphycoccusAur, 0.0);
			anamneses.add(atendimentoPc4a_staphycoccusAur);	
		Anamnese atendimentoPc4a_serratia = new Anamnese(atendimentoPaciente4pp, serratia, 0.0);
			anamneses.add(atendimentoPc4a_serratia);
			
		/* Novo paciente 5 */	
		Anamnese atendimentoPc5a_klebsiella = new Anamnese(atendimentoPaciente5pp, klebsiella, 0.0);
			anamneses.add(atendimentoPc5a_klebsiella);
		Anamnese atendimentoPc5a_proteus = new Anamnese(atendimentoPaciente5pp, proteus, 0.0);
			anamneses.add(atendimentoPc5a_proteus);
		Anamnese atendimentoPc5a_enterobacter = new Anamnese(atendimentoPaciente5pp, enterobacter, 0.0);
			anamneses.add(atendimentoPc5a_enterobacter);
		Anamnese atendimentoPc5a_gravidez = new Anamnese(atendimentoPaciente5pp, gravidez, 0.0);
			anamneses.add(atendimentoPc5a_gravidez);
		Anamnese atendimentoPc5a_polaciuria = new Anamnese(atendimentoPaciente5pp, polaciuria, 0.0);
			anamneses.add(atendimentoPc5a_polaciuria);
		Anamnese atendimentoPc5a_desconforto = new Anamnese(atendimentoPaciente5pp, desconforto, 0.0);
			anamneses.add(atendimentoPc5a_desconforto);
		Anamnese atendimentoPc5a_infeccao = new Anamnese(atendimentoPaciente5pp, infeccao, 0.0);
			anamneses.add(atendimentoPc5a_infeccao);
		Anamnese atendimentoPc5a_usoDeAntibiotico = new Anamnese(atendimentoPaciente5pp, usoDeAntibiotico, 0.0);
			anamneses.add(atendimentoPc5a_usoDeAntibiotico);
			
		Anamnese atendimentoPc5a_pseudomonas = new Anamnese(atendimentoPaciente5pp, pseudomonas, 0.0);
			anamneses.add(atendimentoPc5a_pseudomonas);
		Anamnese atendimentoPc5a_staphylococcus = new Anamnese(atendimentoPaciente5pp, staphylococcus, 0.0);
			anamneses.add(atendimentoPc5a_staphylococcus);
		Anamnese atendimentoPc5a_streptoccus = new Anamnese(atendimentoPaciente5pp, streptoccus, 0.0);
			anamneses.add(atendimentoPc5a_streptoccus);
		Anamnese atendimentoPc5a_citrobacter = new Anamnese(atendimentoPaciente5pp, citrobacter, 0.0);
			anamneses.add(atendimentoPc5a_citrobacter);
		Anamnese atendimentoPc5a_staphylococcusEp = new Anamnese(atendimentoPaciente5pp, staphylococcusEp, 0.0);
			anamneses.add(atendimentoPc5a_staphylococcusEp);
		Anamnese atendimentoPc5a_enterobacterClo = new Anamnese(atendimentoPaciente5pp, enterobacterClo, 0.0);
			anamneses.add(atendimentoPc5a_enterobacterClo);
		Anamnese atendimentoPc5a_citrobacterFre = new Anamnese(atendimentoPaciente5pp, citrobacterFre, 0.0);
			anamneses.add(atendimentoPc5a_citrobacterFre);
		Anamnese atendimentoPc5a_morganella = new Anamnese(atendimentoPaciente5pp, morganella, 0.0);
			anamneses.add(atendimentoPc5a_morganella);
		Anamnese atendimentoPc5a_staphycoccusAur = new Anamnese(atendimentoPaciente5pp, staphycoccusAur, 0.0);
			anamneses.add(atendimentoPc5a_staphycoccusAur);	
		Anamnese atendimentoPc5a_serratia = new Anamnese(atendimentoPaciente5pp, serratia, 0.0);
			anamneses.add(atendimentoPc5a_serratia);			
		
		/* Novo paciente 6 */
		Anamnese atendimentoPc6a_klebsiella = new Anamnese(atendimentoPaciente6pp, klebsiella, 0.0);
			anamneses.add(atendimentoPc6a_klebsiella);
		Anamnese atendimentoPc6a_proteus = new Anamnese(atendimentoPaciente6pp, proteus, 0.0);
			anamneses.add(atendimentoPc6a_proteus);
		Anamnese atendimentoPc6a_enterobacter = new Anamnese(atendimentoPaciente6pp, enterobacter, 0.0);
			anamneses.add(atendimentoPc6a_enterobacter);
		Anamnese atendimentoPc6a_gravidez = new Anamnese(atendimentoPaciente6pp, gravidez, 0.0);
			anamneses.add(atendimentoPc6a_gravidez);
		Anamnese atendimentoPc6a_polaciuria = new Anamnese(atendimentoPaciente6pp, polaciuria, 0.0);
			anamneses.add(atendimentoPc6a_polaciuria);
		Anamnese atendimentoPc6a_desconforto = new Anamnese(atendimentoPaciente6pp, desconforto, 0.0);
			anamneses.add(atendimentoPc6a_desconforto);
		Anamnese atendimentoPc6a_infeccao = new Anamnese(atendimentoPaciente6pp, infeccao, 0.0);
			anamneses.add(atendimentoPc6a_infeccao);
		Anamnese atendimentoPc6a_usoDeAntibiotico = new Anamnese(atendimentoPaciente6pp, usoDeAntibiotico, 0.0);
			anamneses.add(atendimentoPc6a_usoDeAntibiotico);
			
		Anamnese atendimentoPc6a_pseudomonas = new Anamnese(atendimentoPaciente6pp, pseudomonas, 0.0);
			anamneses.add(atendimentoPc6a_pseudomonas);
		Anamnese atendimentoPc6a_staphylococcus = new Anamnese(atendimentoPaciente6pp, staphylococcus, 0.0);
			anamneses.add(atendimentoPc6a_staphylococcus);
		Anamnese atendimentoPc6a_streptoccus = new Anamnese(atendimentoPaciente6pp, streptoccus, 0.0);
			anamneses.add(atendimentoPc6a_streptoccus);
		Anamnese atendimentoPc6a_citrobacter = new Anamnese(atendimentoPaciente6pp, citrobacter, 0.0);
			anamneses.add(atendimentoPc6a_citrobacter);
		Anamnese atendimentoPc6a_staphylococcusEp = new Anamnese(atendimentoPaciente6pp, staphylococcusEp, 0.0);
			anamneses.add(atendimentoPc6a_staphylococcusEp);
		Anamnese atendimentoPc6a_enterobacterClo = new Anamnese(atendimentoPaciente6pp, enterobacterClo, 0.0);
			anamneses.add(atendimentoPc6a_enterobacterClo);
		Anamnese atendimentoPc6a_citrobacterFre = new Anamnese(atendimentoPaciente6pp, citrobacterFre, 0.0);
			anamneses.add(atendimentoPc6a_citrobacterFre);
		Anamnese atendimentoPc6a_morganella = new Anamnese(atendimentoPaciente6pp, morganella, 0.0);
			anamneses.add(atendimentoPc6a_morganella);
		Anamnese atendimentoPc6a_staphycoccusAur = new Anamnese(atendimentoPaciente6pp, staphycoccusAur, 0.0);
			anamneses.add(atendimentoPc6a_staphycoccusAur);	
		Anamnese atendimentoPc6a_serratia = new Anamnese(atendimentoPaciente6pp, serratia, 0.0);
			anamneses.add(atendimentoPc6a_serratia);				
			
		/* Novo paciente 7 */
		Anamnese atendimentoPc7a_klebsiella = new Anamnese(atendimentoPaciente7pp, klebsiella, 0.0);
			anamneses.add(atendimentoPc7a_klebsiella);
		Anamnese atendimentoPc7a_proteus = new Anamnese(atendimentoPaciente7pp, proteus, 0.0);
			anamneses.add(atendimentoPc7a_proteus);
		Anamnese atendimentoPc7a_enterobacter = new Anamnese(atendimentoPaciente7pp, enterobacter, 0.0);
			anamneses.add(atendimentoPc7a_enterobacter);
		Anamnese atendimentoPc7a_gravidez = new Anamnese(atendimentoPaciente7pp, gravidez, 0.0);
			anamneses.add(atendimentoPc7a_gravidez);
		Anamnese atendimentoPc7a_polaciuria = new Anamnese(atendimentoPaciente7pp, polaciuria, 0.0);
			anamneses.add(atendimentoPc7a_polaciuria);
		Anamnese atendimentoPc7a_desconforto = new Anamnese(atendimentoPaciente7pp, desconforto, 0.0);
			anamneses.add(atendimentoPc7a_desconforto);
		Anamnese atendimentoPc7a_infeccao = new Anamnese(atendimentoPaciente7pp, infeccao, 0.0);
			anamneses.add(atendimentoPc7a_infeccao);
		Anamnese atendimentoPc7a_usoDeAntibiotico = new Anamnese(atendimentoPaciente7pp, usoDeAntibiotico, 0.0);
			anamneses.add(atendimentoPc7a_usoDeAntibiotico);
			
		Anamnese atendimentoPc7a_pseudomonas = new Anamnese(atendimentoPaciente7pp, pseudomonas, 0.0);
			anamneses.add(atendimentoPc7a_pseudomonas);
		Anamnese atendimentoPc7a_staphylococcus = new Anamnese(atendimentoPaciente7pp, staphylococcus, 0.0);
			anamneses.add(atendimentoPc7a_staphylococcus);
		Anamnese atendimentoPc7a_streptoccus = new Anamnese(atendimentoPaciente7pp, streptoccus, 0.0);
			anamneses.add(atendimentoPc7a_streptoccus);
		Anamnese atendimentoPc7a_citrobacter = new Anamnese(atendimentoPaciente7pp, citrobacter, 0.0);
			anamneses.add(atendimentoPc7a_citrobacter);
		Anamnese atendimentoPc7a_staphylococcusEp = new Anamnese(atendimentoPaciente7pp, staphylococcusEp, 0.0);
			anamneses.add(atendimentoPc7a_staphylococcusEp);
		Anamnese atendimentoPc7a_enterobacterClo = new Anamnese(atendimentoPaciente7pp, enterobacterClo, 0.0);
			anamneses.add(atendimentoPc7a_enterobacterClo);
		Anamnese atendimentoPc7a_citrobacterFre = new Anamnese(atendimentoPaciente7pp, citrobacterFre, 0.0);
			anamneses.add(atendimentoPc7a_citrobacterFre);
		Anamnese atendimentoPc7a_morganella = new Anamnese(atendimentoPaciente7pp, morganella, 0.0);
			anamneses.add(atendimentoPc7a_morganella);
		Anamnese atendimentoPc7a_staphycoccusAur = new Anamnese(atendimentoPaciente7pp, staphycoccusAur, 0.0);
			anamneses.add(atendimentoPc7a_staphycoccusAur);	
		Anamnese atendimentoPc7a_serratia = new Anamnese(atendimentoPaciente7pp, serratia, 0.0);
			anamneses.add(atendimentoPc7a_serratia);
			
		/* Novo paciente 8 */ 	
		Anamnese atendimentoPc8a_klebsiella = new Anamnese(atendimentoPaciente8pp, klebsiella, 0.0);
			anamneses.add(atendimentoPc8a_klebsiella);
		Anamnese atendimentoPc8a_proteus = new Anamnese(atendimentoPaciente8pp, proteus, 0.0);
			anamneses.add(atendimentoPc8a_proteus);
		Anamnese atendimentoPc8a_enterobacter = new Anamnese(atendimentoPaciente8pp, enterobacter, 0.0);
			anamneses.add(atendimentoPc8a_enterobacter);
		Anamnese atendimentoPc8a_gravidez = new Anamnese(atendimentoPaciente8pp, gravidez, 0.0);
			anamneses.add(atendimentoPc8a_gravidez);
		Anamnese atendimentoPc8a_polaciuria = new Anamnese(atendimentoPaciente8pp, polaciuria, 0.0);
			anamneses.add(atendimentoPc8a_polaciuria);
		Anamnese atendimentoPc8a_desconforto = new Anamnese(atendimentoPaciente8pp, desconforto, 0.0);
			anamneses.add(atendimentoPc8a_desconforto);
		Anamnese atendimentoPc8a_infeccao = new Anamnese(atendimentoPaciente8pp, infeccao, 0.0);
			anamneses.add(atendimentoPc8a_infeccao);
		Anamnese atendimentoPc8a_usoDeAntibiotico = new Anamnese(atendimentoPaciente8pp, usoDeAntibiotico, 0.0);
			anamneses.add(atendimentoPc8a_usoDeAntibiotico);
			
		Anamnese atendimentoPc8a_pseudomonas = new Anamnese(atendimentoPaciente8pp, pseudomonas, 0.0);
			anamneses.add(atendimentoPc8a_pseudomonas);
		Anamnese atendimentoPc8a_staphylococcus = new Anamnese(atendimentoPaciente8pp, staphylococcus, 0.0);
			anamneses.add(atendimentoPc8a_staphylococcus);
		Anamnese atendimentoPc8a_streptoccus = new Anamnese(atendimentoPaciente8pp, streptoccus, 0.0);
			anamneses.add(atendimentoPc8a_streptoccus);
		Anamnese atendimentoPc8a_citrobacter = new Anamnese(atendimentoPaciente8pp, citrobacter, 0.0);
			anamneses.add(atendimentoPc8a_citrobacter);
		Anamnese atendimentoPc8a_staphylococcusEp = new Anamnese(atendimentoPaciente8pp, staphylococcusEp, 0.0);
			anamneses.add(atendimentoPc8a_staphylococcusEp);
		Anamnese atendimentoPc8a_enterobacterClo = new Anamnese(atendimentoPaciente8pp, enterobacterClo, 0.0);
			anamneses.add(atendimentoPc8a_enterobacterClo);
		Anamnese atendimentoPc8a_citrobacterFre = new Anamnese(atendimentoPaciente8pp, citrobacterFre, 0.0);
			anamneses.add(atendimentoPc8a_citrobacterFre);
		Anamnese atendimentoPc8a_morganella = new Anamnese(atendimentoPaciente8pp, morganella, 0.0);
			anamneses.add(atendimentoPc8a_morganella);
		Anamnese atendimentoPc8a_staphycoccusAur = new Anamnese(atendimentoPaciente8pp, staphycoccusAur, 0.0);
			anamneses.add(atendimentoPc8a_staphycoccusAur);	
		Anamnese atendimentoPc8a_serratia = new Anamnese(atendimentoPaciente8pp, serratia, 0.0);
			anamneses.add(atendimentoPc8a_serratia);			
			
		/* Novo paciente 9 */	
		Anamnese atendimentoPc9a_klebsiella = new Anamnese(atendimentoPaciente9pp, klebsiella, 0.0);
			anamneses.add(atendimentoPc9a_klebsiella);
		Anamnese atendimentoPc9a_proteus = new Anamnese(atendimentoPaciente9pp, proteus, 0.0);
			anamneses.add(atendimentoPc9a_proteus);
		Anamnese atendimentoPc9a_enterobacter = new Anamnese(atendimentoPaciente9pp, enterobacter, 0.0);
			anamneses.add(atendimentoPc9a_enterobacter);
		Anamnese atendimentoPc9a_gravidez = new Anamnese(atendimentoPaciente9pp, gravidez, 0.0);
			anamneses.add(atendimentoPc9a_gravidez);
		Anamnese atendimentoPc9a_polaciuria = new Anamnese(atendimentoPaciente9pp, polaciuria, 0.0);
			anamneses.add(atendimentoPc9a_polaciuria);
		Anamnese atendimentoPc9a_desconforto = new Anamnese(atendimentoPaciente9pp, desconforto, 0.0);
			anamneses.add(atendimentoPc9a_desconforto);
		Anamnese atendimentoPc9a_infeccao = new Anamnese(atendimentoPaciente9pp, infeccao, 0.0);
			anamneses.add(atendimentoPc9a_infeccao);
		Anamnese atendimentoPc9a_usoDeAntibiotico = new Anamnese(atendimentoPaciente9pp, usoDeAntibiotico, 0.0);
			anamneses.add(atendimentoPc9a_usoDeAntibiotico);
	
		Anamnese atendimentoPc9a_pseudomonas = new Anamnese(atendimentoPaciente9pp, pseudomonas, 0.0);
			anamneses.add(atendimentoPc9a_pseudomonas);
		Anamnese atendimentoPc9a_staphylococcus = new Anamnese(atendimentoPaciente9pp, staphylococcus, 0.0);
			anamneses.add(atendimentoPc9a_staphylococcus);
		Anamnese atendimentoPc9a_streptoccus = new Anamnese(atendimentoPaciente9pp, streptoccus, 0.0);
			anamneses.add(atendimentoPc9a_streptoccus);
		Anamnese atendimentoPc9a_citrobacter = new Anamnese(atendimentoPaciente9pp, citrobacter, 0.0);
			anamneses.add(atendimentoPc9a_citrobacter);
		Anamnese atendimentoPc9a_staphylococcusEp = new Anamnese(atendimentoPaciente9pp, staphylococcusEp, 0.0);
			anamneses.add(atendimentoPc9a_staphylococcusEp);
		Anamnese atendimentoPc9a_enterobacterClo = new Anamnese(atendimentoPaciente9pp, enterobacterClo, 0.0);
			anamneses.add(atendimentoPc9a_enterobacterClo);
		Anamnese atendimentoPc9a_citrobacterFre = new Anamnese(atendimentoPaciente9pp, citrobacterFre, 0.0);
			anamneses.add(atendimentoPc9a_citrobacterFre);
		Anamnese atendimentoPc9a_morganella = new Anamnese(atendimentoPaciente9pp, morganella, 0.0);
			anamneses.add(atendimentoPc9a_morganella);
		Anamnese atendimentoPc9a_staphycoccusAur = new Anamnese(atendimentoPaciente9pp, staphycoccusAur, 0.0);
			anamneses.add(atendimentoPc9a_staphycoccusAur);	
		Anamnese atendimentoPc9a_serratia = new Anamnese(atendimentoPaciente9pp, serratia, 0.0);
			anamneses.add(atendimentoPc9a_serratia);				
			
		/* Novo paciente 10 */	
		Anamnese atendimentoPc10a_klebsiella = new Anamnese(atendimentoPaciente10pp, klebsiella, 0.0);
			anamneses.add(atendimentoPc10a_klebsiella);
		Anamnese atendimentoPc10a_proteus = new Anamnese(atendimentoPaciente10pp, proteus, 0.0);
			anamneses.add(atendimentoPc10a_proteus);
		Anamnese atendimentoPc10a_enterobacter = new Anamnese(atendimentoPaciente10pp, enterobacter, 0.0);
			anamneses.add(atendimentoPc10a_enterobacter);
		Anamnese atendimentoPc10a_gravidez = new Anamnese(atendimentoPaciente10pp, gravidez, 0.0);
			anamneses.add(atendimentoPc10a_gravidez);
		Anamnese atendimentoPc10a_polaciuria = new Anamnese(atendimentoPaciente10pp, polaciuria, 0.0);
			anamneses.add(atendimentoPc10a_polaciuria);
		Anamnese atendimentoPc10a_desconforto = new Anamnese(atendimentoPaciente10pp, desconforto, 0.0);
			anamneses.add(atendimentoPc10a_desconforto);
		Anamnese atendimentoPc10a_infeccao = new Anamnese(atendimentoPaciente10pp, infeccao, 0.0);
			anamneses.add(atendimentoPc10a_infeccao);
		Anamnese atendimentoPc10_usoDeAntibiotico = new Anamnese(atendimentoPaciente10pp, usoDeAntibiotico, 0.0);
			anamneses.add(atendimentoPc10_usoDeAntibiotico);
		
		Anamnese atendimentoPc10_pseudomonas = new Anamnese(atendimentoPaciente10pp, pseudomonas, 0.0);
			anamneses.add(atendimentoPc10_pseudomonas);
		Anamnese atendimentoPc10_staphylococcus = new Anamnese(atendimentoPaciente10pp, staphylococcus, 0.0);
			anamneses.add(atendimentoPc10_staphylococcus);
		Anamnese atendimentoPc10_streptoccus = new Anamnese(atendimentoPaciente10pp, streptoccus, 0.0);
			anamneses.add(atendimentoPc10_streptoccus);
		Anamnese atendimentoPc10_citrobacter = new Anamnese(atendimentoPaciente10pp, citrobacter, 0.0);
			anamneses.add(atendimentoPc10_citrobacter);
		Anamnese atendimentoPc10_staphylococcusEp = new Anamnese(atendimentoPaciente10pp, staphylococcusEp, 0.0);
			anamneses.add(atendimentoPc10_staphylococcusEp);
		Anamnese atendimentoPc10_enterobacterClo = new Anamnese(atendimentoPaciente10pp, enterobacterClo, 0.0);
			anamneses.add(atendimentoPc10_enterobacterClo);
		Anamnese atendimentoPc10_citrobacterFre = new Anamnese(atendimentoPaciente10pp, citrobacterFre, 0.0);
			anamneses.add(atendimentoPc10_citrobacterFre);
		Anamnese atendimentoPc10_morganella = new Anamnese(atendimentoPaciente10pp, morganella, 0.0);
			anamneses.add(atendimentoPc10_morganella);
		Anamnese atendimentoPc10_staphycoccusAur = new Anamnese(atendimentoPaciente10pp, staphycoccusAur, 0.0);
			anamneses.add(atendimentoPc10_staphycoccusAur);	
		Anamnese atendimentoPc10_serratia = new Anamnese(atendimentoPaciente10pp, serratia, 0.0);
			anamneses.add(atendimentoPc10_serratia);
		
		for(Anamnese anamnese : anamneses){
			anamneseService.inclui(anamnese);
		}
	}

}
