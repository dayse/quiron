package carga;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import modelo.Especialista;
import modelo.Paciente;
import service.EspecialistaAppService;
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
 * na ordem correta. Ex: incluiPaciente() vem antes de incluiAtendimento(), portanto no método executar()
 * 
 * Terminado de executar todas as etapas é preciso retornar true.
 * Se houver algum problema(exceção) na execução de uma das etapas, essa exceção deve ser lancada.
 * 
 * Essa Carga:
 * Responsavel por fazer a carga dos pacientes contidas nos
 * arquivos de modelagens e dados iniciais do sistema. Compatível com os slides do Pedro Peloso
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

		paciente1.setDataNascimento(DataUtil.dateToCalendar(
				retornaDataNascimentoPorIdade(68).getTime())
				);

		Paciente paciente2 = new Paciente();
		paciente2.setCodPaciente("paciente2");
		paciente2.setNome("Paciente 2");
		paciente2.setSexo(true);
		paciente2.setDocumento("1234567-9");

		paciente2.setDataNascimento(DataUtil.dateToCalendar(
				retornaDataNascimentoPorIdade(68).getTime())
				);

		pacienteService.inclui(paciente1);
		pacienteService.inclui(paciente2);
		
		
	}
	
	/**
	 * calcula e retorna uma data de nascimento pela idade
	 * 
	 * @param idade
	 * @return
	 * 
	 * @author felipe.arruda
	 * 
	 */
	public Calendar retornaDataNascimentoPorIdade(int idade){

       	Calendar dataAtual = Calendar.getInstance();
       	
		Calendar nascimento = new GregorianCalendar();
		nascimento.setTime(dataAtual.getTime());
    	nascimento.add(Calendar.YEAR, -idade);
    	return nascimento;
	}
	

}
