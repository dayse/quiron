package carga;

import java.util.List;

import service.exception.AplicacaoException;

/**
 * 
 * Carga para ser usada como base, pois possui o method executar.
 * 
 * Nesse método "executar" é que é chamado pelos outros métodos que são as
 * etapas dessa carga. Portanto se é necessario rodar um método depois do outro,
 * eles devem ser chamados na ordem correta. Ex: incluiHP() vem antes de
 * inicializaHP(), portanto no método executar() eles devem ser chamados nessa
 * ordem.
 * 
 * Terminado de executar todas as etapas é preciso retornar true. Se houver
 * algum problema(exceção) na execução de uma das etapas, essa exceção deve ser
 * lancada.
 * 
 * @author felipe.arruda
 * 
 */
public abstract class CargaBase {
	/**
	 * 
	 * Executa métodos dessa carga.
	 * 
	 * @return Boolean - True se a carga for executada com sucesso ou false caso
	 *         ocorra algum erro.
	 *         
	 * @throws AplicacaoException - Pode retornar um erro de aplicação.
	 * 
	 * @author felipe.arruda
	 * 
	 */
	public abstract boolean executar() throws AplicacaoException;
	
	/**
	 * Lista de cargas que a carga atual depende para rodar com sucesso
	 */
	public abstract List<CargaBase> getCargasDependentes();
	
}
