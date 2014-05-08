package carga;

import java.util.Calendar;

import modelo.Anamnese;
import modelo.Atendimento;
import modelo.Paciente;
import modelo.Usuario;
import service.AnamneseAppService;
import service.AtendimentoAppService;
import service.PacienteAppService;
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;

/**
 * 
 * Sobre a Carga:
 * � uma Carga do sistema, portanto deve herdar de CargaBase e
 * implementar o m�todo executar().
 * Nesse m�todo "executar" � que � chamado pelos outros m�todos que s�o 
 * as etapas dessa carga.
 * Portanto se � necessario rodar um m�todo depois do outro, eles devem ser chamados
 * na ordem correta. Ex:
 * incluiHP() vem antes de inicializaHP(), portanto no m�todo executar() eles devem ser chamados nessa ordem.
 * 
 * Terminado de executar todas as etapas � preciso retornar true.
 * Se houver algum problema(exce��o) na execu��o de uma das etapas, essa exce��o deve ser lancada.
 * 
 * Essa Carga:
 * Responsavel por fazer a carga dos atendimentos contidas nos
 * arquivos de modelagens e dados iniciais do sistema
 * 
 * @author felipe.arruda
 *
 */
public class CargaAtendimento extends CargaBase{
  
	// Services
	private static PacienteAppService pacienteService;
	private static AtendimentoAppService atendimentoService;
	private static AnamneseAppService anamneseService;
	private static UsuarioAppService usuarioService;
	
	/**
	 * 
	 * Construdor da carga, respons�vel por instanciar os services.
	 * 
	 * @author felipe.arruda
	 * 
	 */
	public CargaAtendimento(){
		try {
			pacienteService = FabricaDeAppService.getAppService(PacienteAppService.class);
			atendimentoService = FabricaDeAppService.getAppService(AtendimentoAppService.class);
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
	 * M�todo herdado de CargaBase e utilizado para definir as etapas
	 * de execu��o desta carga.
	 * 
	 * @return Boolean - True se n�o ocorrer nenhum problema (exce��o).
	 * @throws AplicacaoException
	 *             Retorna uma AplicacaoException caso ocorra uma exce��o deste
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
	 * Metodo respons�vel por preparar e inserir os valores padr�es de
	 * Atendimentos no banco de dados
	 * 
	 * @author felipe.pontes
	 * @throws AplicacaoException
	 */
	public void incluirAtendimentos() throws AplicacaoException {

       	Calendar dataAtual = Calendar.getInstance();

		Paciente paciente1 = pacienteService.recuperaPacientePorCodigo("paciente1");
		Paciente paciente2 = pacienteService.recuperaPacientePorCodigo("paciente2");

		Usuario clinico = usuarioService.recuperaPorLogin("clinico");
		Usuario tecnico = usuarioService.recuperaPorLogin("tecnico");

		Atendimento atendimentoPaciente1 = new Atendimento(); 
		atendimentoPaciente1.setCodAtendimento("atp1");
		atendimentoPaciente1.setPaciente(paciente1);
		atendimentoPaciente1.setDataAtendimento(dataAtual);
		atendimentoPaciente1.setMedico(clinico);
		atendimentoPaciente1.setStatus("Encerrado");
		String prognostico = "Bact�ria ou candid�ria em pacientes compromentidos podem";
			   prognostico += " causar pielonefrites/bacteriamias.";
		atendimentoPaciente1.setPrognostico(prognostico);
		

		Atendimento atendimentoPaciente2 = new Atendimento(); 
		atendimentoPaciente2.setCodAtendimento("atp2");
		atendimentoPaciente2.setPaciente(paciente2);
		atendimentoPaciente2.setDataAtendimento(dataAtual);
		atendimentoPaciente2.setMedico(clinico);
		atendimentoPaciente2.setTecnico(tecnico);
		atendimentoPaciente2.setStatus("Encerrado");
		atendimentoPaciente2.setPrognostico("Excelente.");

		atendimentoService.inclui(atendimentoPaciente1);
		atendimentoService.inclui(atendimentoPaciente2);
		
		//inclui as anamneses
		incluirAnamneses(atendimentoPaciente1, atendimentoPaciente2);
	}

	/**
	 * Metodo respons�vel por preparar e inserir os valores padr�es de
	 * Anamneses no banco de dados
	 * 
	 * @author felipe.pontes
	 * @throws AplicacaoException
	 */
	public void incluirAnamneses(Atendimento atendimentoPaciente1, Atendimento atendimentoPaciente2) 
									throws AplicacaoException {

		Anamnese anamneseAtendimentoPc1 = new Anamnese();
		anamneseAtendimentoPc1.setAtendimento(atendimentoPaciente1);
		anamneseAtendimentoPc1.setFebre(0.7);
		anamneseAtendimentoPc1.setDisuria(0.8);
		anamneseAtendimentoPc1.setDiabetes(0.7);
		anamneseAtendimentoPc1.setEnterococos(0.0);
		anamneseAtendimentoPc1.setEscherichia(0.0);
		anamneseAtendimentoPc1.setCandida(1.0);
		anamneseAtendimentoPc1.setEfeitosColaterais(1.0);

		Anamnese anamneseAtendimentoPc2 = new Anamnese();
		anamneseAtendimentoPc2.setAtendimento(atendimentoPaciente2);
		anamneseAtendimentoPc2.setFebre(0.2);
		anamneseAtendimentoPc2.setDisuria(0.4);
		anamneseAtendimentoPc2.setDiabetes(0.3);
		anamneseAtendimentoPc2.setEnterococos(1.0);
		anamneseAtendimentoPc2.setEscherichia(0.0);
		anamneseAtendimentoPc2.setCandida(0.0);
		anamneseAtendimentoPc2.setEfeitosColaterais(1.0);

		anamneseService.inclui(anamneseAtendimentoPc1);
		anamneseService.inclui(anamneseAtendimentoPc2);
	}
	

}
