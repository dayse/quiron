package carga;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import modelo.Paciente;
import service.PacienteAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.DataUtil;

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
 * Responsavel por fazer a carga dos pacientes contidas nos
 * arquivos de modelagens e dados iniciais do sistema
 * 
 * @author bruno.oliveira
 *
 */
public class CargaPacienteIntermediaria extends CargaBase {

	// Services
	private static PacienteAppService pacienteService;
	
	/**
	 * 
	 * Construdor da carga, responsável por instanciar os services.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public CargaPacienteIntermediaria(){
		try {
			pacienteService = FabricaDeAppService.getAppService(PacienteAppService.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<CargaBase> getCargasDependentes(){
		List<CargaBase> dependencias = new ArrayList<CargaBase>();
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
		this.incluirPacientes();
		return true;
	}
	

	/**
	 * Metodo responsável por preparar e inserir os valores padrões de
	 * Pacientes no banco de dados
	 * 
	 * @author bruno.oliveira
	 * @throws AplicacaoException
	 */
	public void incluirPacientes() throws AplicacaoException {
		ArrayList<Paciente> pacientes = new ArrayList<Paciente>();

		Paciente paciente1 = new Paciente();
		paciente1.setCodPaciente("paciente1pp");
		paciente1.setNome("Antonio Carlos Silva");
		paciente1.setSexo(true);
		paciente1.setDocumento("1234567-8");

		paciente1.setDataNascimento(DataUtil.dateToCalendar(
				retornaDataNascimentoPorIdade(60).getTime())
				);
		
		pacientes.add(paciente1);

		Paciente paciente2 = new Paciente();
		paciente2.setCodPaciente("paciente2pp");
		paciente2.setNome("Luzia Costa Barros");
		paciente2.setSexo(false);
		paciente2.setDocumento("1234567-9");

		paciente2.setDataNascimento(DataUtil.dateToCalendar(
				retornaDataNascimentoPorIdade(10).getTime())
				);
		
		pacientes.add(paciente2);
		
		Paciente paciente3 = new Paciente();
		paciente3.setCodPaciente("paciente3pp");
		paciente3.setNome("Luiz Barbosa Bulhões");
		paciente3.setSexo(true);
		paciente3.setDocumento("1234567-9");

		paciente3.setDataNascimento(DataUtil.dateToCalendar(
				retornaDataNascimentoPorIdade(80).getTime())
				);
		
		pacientes.add(paciente3);
		
		Paciente paciente4 = new Paciente();
		paciente4.setCodPaciente("paciente4pp");
		paciente4.setNome("Maria Silva Barbosa");
		paciente4.setSexo(false);
		paciente4.setDocumento("1234567-9");

		paciente4.setDataNascimento(DataUtil.dateToCalendar(
				retornaDataNascimentoPorIdade(78).getTime())
				);
		
		pacientes.add(paciente4);
		
		Paciente paciente5 = new Paciente();
		paciente5.setCodPaciente("paciente5pp");
		paciente5.setNome("Claudio Batista Coelho");
		paciente5.setSexo(true);
		paciente5.setDocumento("1234567-9");

		paciente5.setDataNascimento(DataUtil.dateToCalendar(
				retornaDataNascimentoPorIdade(25).getTime())
				);
		
		pacientes.add(paciente5);
		
		Paciente paciente6 = new Paciente();
		paciente6.setCodPaciente("paciente6pp");
		paciente6.setNome("Amanda Costa");
		paciente6.setSexo(false);
		paciente6.setDocumento("1234567-9");

		paciente6.setDataNascimento(DataUtil.dateToCalendar(
				retornaDataNascimentoPorIdade(12).getTime())
				);
		
		pacientes.add(paciente6);
		
		Paciente paciente7 = new Paciente();
		paciente7.setCodPaciente("paciente7pp");
		paciente7.setNome("Claudia Vieira");
		paciente7.setSexo(false);
		paciente7.setDocumento("1234567-9");

		paciente7.setDataNascimento(DataUtil.dateToCalendar(
				retornaDataNascimentoPorIdade(30).getTime())
				);
		
		pacientes.add(paciente7);
		
		Paciente paciente8 = new Paciente();
		paciente8.setCodPaciente("paciente8pp");
		paciente8.setNome("Alexandre Monteiro");
		paciente8.setSexo(true);
		paciente8.setDocumento("1234567-9");

		paciente8.setDataNascimento(DataUtil.dateToCalendar(
				retornaDataNascimentoPorIdade(90).getTime())
				);
		
		pacientes.add(paciente8);
		
		Paciente paciente9 = new Paciente();
		paciente9.setCodPaciente("paciente9pp");
		paciente9.setNome("Pedro Peloso");
		paciente9.setSexo(true);
		paciente9.setDocumento("1234567-9");

		paciente9.setDataNascimento(DataUtil.dateToCalendar(
				retornaDataNascimentoPorIdade(50).getTime())
				);
		
		pacientes.add(paciente9);
		
		Paciente paciente10 = new Paciente();
		paciente10.setCodPaciente("paciente10pp");
		paciente10.setNome("Tatiana Oviedo");
		paciente10.setSexo(false);
		paciente10.setDocumento("1234567-9");

		paciente10.setDataNascimento(DataUtil.dateToCalendar(
				retornaDataNascimentoPorIdade(55).getTime())
				);
		
		pacientes.add(paciente10);
		
		for(Paciente paciente : pacientes){
			pacienteService.inclui(paciente);
		}	
		
	}
	
	/**
	 * calcula e retorna uma data de nascimento pela idade
	 * 
	 * @param idade
	 * @return
	 */
	public Calendar retornaDataNascimentoPorIdade(int idade){

       	Calendar dataAtual = Calendar.getInstance();
       	
		Calendar nascimento = new GregorianCalendar();
		nascimento.setTime(dataAtual.getTime());
    	nascimento.add(Calendar.YEAR, -idade);
    	return nascimento;
	}
	

}
