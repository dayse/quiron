package carga;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import modelo.Especialista;
import modelo.Paciente;
import service.EspecialistaAppService;
import service.PacienteAppService;
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
 * Responsavel por fazer a carga dos pacientes contidas nos
 * arquivos de modelagens e dados iniciais do sistema
 * 
 * @author felipe.arruda
 *
 */
public class CargaPaciente extends CargaBase{
  
	// Services
	private static PacienteAppService pacienteService;
	
	/**
	 * 
	 * Construdor da carga, responsável por instanciar os services.
	 * 
	 * @author felipe.arruda
	 * 
	 */
	public CargaPaciente(){
		try {
			pacienteService = FabricaDeAppService.getAppService(PacienteAppService.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		this.incluirPacientes();
		return true;
	}
	

	/**
	 * Metodo responsável por preparar e inserir os valores padrões de
	 * Pacientes no banco de dados
	 * 
	 * @author felipe.pontes
	 * @throws AplicacaoException
	 */
	public void incluirPacientes() throws AplicacaoException {

		
		Paciente paciente1 = new Paciente();
		paciente1.setCodPaciente("paciente1");
		paciente1.setNome("Paciente 1");
		paciente1.setSexo(true);
		paciente1.setDocumento("1234567-8");

		paciente1.setDataNascimento(
				retornaDataNascimentoPorIdade(68).getTime()
				);
		
		pacienteService.inclui(paciente1);
		
		
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
