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
 * na ordem correta. Ex:
 * incluiHP() vem antes de inicializaHP(), portanto no método executar() eles devem ser chamados nessa ordem.
 * 
 * Terminado de executar todas as etapas é preciso retornar true.
 * Se houver algum problema(exceção) na execução de uma das etapas, essa exceção deve ser lancada.
 * 
 * Essa Carga:
 * Responsavel por fazer a carga dos atendimentos contidas nos
 * arquivos de modelagens e dados iniciais do sistema
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
		
		Parametro klebsiella = parametroService.recuperaParametroPorCodigo("P009");
		Parametro proteus = parametroService.recuperaParametroPorCodigo("P010");
		Parametro enterobacter = parametroService.recuperaParametroPorCodigo("P011");
		Parametro gravidez = parametroService.recuperaParametroPorCodigo("P012");
		Parametro polaciuria = parametroService.recuperaParametroPorCodigo("P013");
		Parametro desconforto = parametroService.recuperaParametroPorCodigo("P014");
		Parametro infeccao = parametroService.recuperaParametroPorCodigo("P015");
		Parametro usoDeAntibiotico = parametroService.recuperaParametroPorCodigo("P016");
		
		
		
		/* Complemento dos atendimentos de exemplo dos slides iniciais do Pedro Peloso */
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
		
		/* Novos atendimentos fornecidos pelo Pedro Peloso */	
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
			
		for(Anamnese anamnese : anamneses){
			anamneseService.inclui(anamnese);
		}
	}

}
