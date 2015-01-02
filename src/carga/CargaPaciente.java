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
 * � uma Carga do sistema, portanto deve herdar de CargaBase e
 * implementar o m�todo executar().
 * Nesse m�todo "executar" � que � chamado pelos outros m�todos que s�o 
 * as etapas dessa carga.
 * Portanto se � necessario rodar um m�todo depois do outro, eles devem ser chamados
 * na ordem correta. Ex: incluiPaciente() vem antes de incluiAtendimento(), portanto no m�todo executar()
 * 
 * Terminado de executar todas as etapas � preciso retornar true.
 * Se houver algum problema(exce��o) na execu��o de uma das etapas, essa exce��o deve ser lancada.
 * 
 * Essa Carga:
 * Responsavel por fazer a carga dos pacientes contidas nos
 * arquivos de modelagens e dados iniciais do sistema. Compat�vel com os slides do Pedro Peloso
 * 
 * @author felipe.arruda
 *
 */
public class CargaPaciente extends CargaBase{
  
	// Services
	private static PacienteAppService pacienteService;
	
	/**
	 * 
	 * Construdor da carga, respons�vel por instanciar os services.
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
	 * M�todo herdado de CargaBase e que retona uma lista de cargas que esta
	 * carga depende para ser executada de maneira completa.
	 * 
	 * @return lista de dependencias.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */	
	@Override
	public List<CargaBase> getCargasDependentes(){
		List<CargaBase> dependencias = new ArrayList<CargaBase>();
		return dependencias;
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
		this.incluirPacientes();
		return true;
	}
	

	/**
	 * Metodo respons�vel por preparar e inserir os valores padr�es de
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
