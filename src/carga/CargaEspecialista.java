package carga;

import modelo.Especialista;
import service.EspecialistaAppService;
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
 * Responsavel por fazer a carga dos especialistas contidas nos
 * arquivos de modelagens e dados iniciais do sistema
 * 
 * @author felipe.arruda
 *
 */
public class CargaEspecialista extends CargaBase{
  
	// Services
	public static EspecialistaAppService especialistaService;
	
	/**
	 * 
	 * Construdor da carga, respons�vel por instanciar os services.
	 * 
	 * @author felipe.arruda
	 * 
	 */
	public CargaEspecialista(){
		try {
			especialistaService = FabricaDeAppService.getAppService(EspecialistaAppService.class);
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
		this.incluirEspecialistas();
		return true;
	}
	

	/**
	 * Metodo respons�vel por preparar e inserir os valores padr�es de
	 * especialistas no banco de dados
	 * 
	 * @author felipe.pontes
	 * @throws AplicacaoException
	 */
	public void incluirEspecialistas() throws AplicacaoException {

		Especialista espec1 = new Especialista();
		espec1.setNome("Especialista 1");
		espec1.setCodEspecialista("espec1");
		espec1.setPesoAvaliador(1);
		
		Especialista espec2 = new Especialista();
		espec2.setNome("Especialista 2");
		espec2.setCodEspecialista("espec2");
		espec2.setPesoAvaliador(2);

		especialistaService.inclui(espec1);
		especialistaService.inclui(espec2);
		
	}
	

}
