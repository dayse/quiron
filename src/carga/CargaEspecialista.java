package carga;

import modelo.Especialista;
import service.EspecialistaAppService;
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
	 * Construdor da carga, responsável por instanciar os services.
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
		this.incluirEspecialistas();
		return true;
	}
	

	/**
	 * Metodo responsável por preparar e inserir os valores padrões de
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
