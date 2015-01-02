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
 * Ex: incluiPaciente() vem antes de incluiAtendimento(), portanto no método executar()
 * 
 * Terminado de executar todas as etapas é preciso retornar true.
 * Se houver algum problema(exceção) na execução de uma das etapas, essa exceção deve ser lancada.
 * 
 * Essa Carga:
 * Responsavel por fazer a carga dos atendimentos contidas nos
 * arquivos de modelagens e dados iniciais do sistema. Como eram os slides do Pedro Peloso
 * 
 * @author felipe.arruda
 *
 */
public class CargaAtendimento extends CargaBase{
  
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
	 * @author felipe.arruda
	 * 
	 */
	public CargaAtendimento(){
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
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@Override
	public List<CargaBase> getCargasDependentes(){
		List<CargaBase> dependencias = new ArrayList<CargaBase>();
		dependencias.add(new CargaUsuario());
		dependencias.add(new CargaParametros());
		dependencias.add(new CargaIndicacao());
		dependencias.add(new CargaEspecialista());
		dependencias.add(new CargaAvalIndicacaoEspec());
		dependencias.add(new CargaPaciente());
		
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
	 * @author felipe.arruda
	 * 
	 */
	@Override
	public boolean executar() throws AplicacaoException {
		this.incluirAtendimentos();
		return true;
	}
	

	/**
	 * Metodo responsável por preparar e inserir os valores padrões de
	 * Atendimentos no banco de dados
	 * 
	 * @author felipe.pontes
	 * @throws AplicacaoException
	 */
	public void incluirAtendimentos() throws AplicacaoException {

       	Calendar dataAtual = Calendar.getInstance();
       	Calendar dataAtual_anterior = Calendar.getInstance();
       	dataAtual_anterior.add(Calendar.DAY_OF_YEAR, -1);

		Paciente paciente1 = pacienteService.recuperaPacientePorCodigo("paciente1");
		Paciente paciente2 = pacienteService.recuperaPacientePorCodigo("paciente2");

		Usuario clinico = usuarioService.recuperaPorLogin("clinico");
		Usuario tecnico = usuarioService.recuperaPorLogin("tecnico");

		Atendimento atendimentoPaciente1 = new Atendimento(); 
		atendimentoPaciente1.setCodAtendimento("atp1");
		atendimentoPaciente1.setPaciente(paciente1);
		atendimentoPaciente1.setDataAtendimento(dataAtual);
		atendimentoPaciente1.setMedico(clinico);
		atendimentoPaciente1.setStatus("Aberto");
		String prognostico = "Bactéria ou candidúria em pacientes compromentidos podem";
			   prognostico += " causar pielonefrites/bacteriamias.";
		atendimentoPaciente1.setPrognostico(prognostico);
		

		Atendimento atendimentoPaciente2 = new Atendimento(); 
		atendimentoPaciente2.setCodAtendimento("atp2");
		atendimentoPaciente2.setPaciente(paciente2);
		atendimentoPaciente2.setDataAtendimento(dataAtual_anterior);
		atendimentoPaciente2.setMedico(clinico);
		atendimentoPaciente2.setTecnico(tecnico);
		atendimentoPaciente2.setStatus("Aberto");
		atendimentoPaciente2.setPrognostico("Excelente.");

		atendimentoService.inclui(atendimentoPaciente1);
		atendimentoService.inclui(atendimentoPaciente2);
		
		//inclui as anamneses
		incluirAnamneses(atendimentoPaciente1, atendimentoPaciente2);
	}

	/**
	 * Metodo responsável por preparar e inserir os valores padrões de
	 * Anamneses no banco de dados
	 * 
	 * @author felipe.pontes
	 * @throws AplicacaoException
	 */
	public void incluirAnamneses(Atendimento atendimentoPaciente1, Atendimento atendimentoPaciente2) 
									throws AplicacaoException {

		Parametro febre = parametroService.recuperaParametroPorCodigo("P001");
		Parametro disuria = parametroService.recuperaParametroPorCodigo("P002");
		Parametro diabetes = parametroService.recuperaParametroPorCodigo("P003");
		Parametro enterococos = parametroService.recuperaParametroPorCodigo("P004");
		Parametro escherichia = parametroService.recuperaParametroPorCodigo("P005");
		Parametro candida = parametroService.recuperaParametroPorCodigo("P006");
		Parametro efeitosColaterais = parametroService.recuperaParametroPorCodigo("P007");
		Parametro alergia = parametroService.recuperaParametroPorCodigo("P008");
		
		
		Anamnese atendimentoPc1_febre = new Anamnese(atendimentoPaciente1, febre, 0.7);
		Anamnese atendimentoPc1_disuria = new Anamnese(atendimentoPaciente1, disuria, 0.8);
		Anamnese atendimentoPc1_diabetes = new Anamnese(atendimentoPaciente1, diabetes, 0.7);
		Anamnese atendimentoPc1_enterococos = new Anamnese(atendimentoPaciente1, enterococos, 0.0);
		Anamnese atendimentoPc1_escherichia = new Anamnese(atendimentoPaciente1, escherichia, 0.0);
		Anamnese atendimentoPc1_candida = new Anamnese(atendimentoPaciente1, candida, 1.0);
		Anamnese atendimentoPc1_efeitosColaterais = new Anamnese(atendimentoPaciente1, efeitosColaterais, 1.0);
		Anamnese atendimentoPc1_alergia = new Anamnese(atendimentoPaciente1, alergia, 1.0);
		
		anamneseService.inclui(atendimentoPc1_febre);
		anamneseService.inclui(atendimentoPc1_disuria);
		anamneseService.inclui(atendimentoPc1_diabetes);
		anamneseService.inclui(atendimentoPc1_enterococos);
		anamneseService.inclui(atendimentoPc1_escherichia);
		anamneseService.inclui(atendimentoPc1_candida);
		anamneseService.inclui(atendimentoPc1_efeitosColaterais);
		anamneseService.inclui(atendimentoPc1_alergia);
		

		Anamnese atendimentoPc2_febre = new Anamnese(atendimentoPaciente2, febre, 0.2);
		Anamnese atendimentoPc2_disuria = new Anamnese(atendimentoPaciente2, disuria, 0.4);
		Anamnese atendimentoPc2_diabetes = new Anamnese(atendimentoPaciente2, diabetes, 0.3);
		Anamnese atendimentoPc2_enterococos = new Anamnese(atendimentoPaciente2, enterococos, 1.0);
		Anamnese atendimentoPc2_escherichia = new Anamnese(atendimentoPaciente2, escherichia, 0.0);
		Anamnese atendimentoPc2_candida = new Anamnese(atendimentoPaciente2, candida, 0.0);
		Anamnese atendimentoPc2_efeitosColaterais = new Anamnese(atendimentoPaciente2, efeitosColaterais, 1.0);
		Anamnese atendimentoPc2_alergia = new Anamnese(atendimentoPaciente2, alergia, 0.0);

		anamneseService.inclui(atendimentoPc2_febre);
		anamneseService.inclui(atendimentoPc2_disuria);
		anamneseService.inclui(atendimentoPc2_diabetes);
		anamneseService.inclui(atendimentoPc2_enterococos);
		anamneseService.inclui(atendimentoPc2_escherichia);
		anamneseService.inclui(atendimentoPc2_candida);
		anamneseService.inclui(atendimentoPc2_efeitosColaterais);
		anamneseService.inclui(atendimentoPc2_alergia);
		
	}
	

}
