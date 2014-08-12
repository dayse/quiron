package carga;

import java.util.List;

import service.exception.AplicacaoException;

/**
 * 
 * Carga para ser usada como base, pois possui o method executar.
 * 
 * Nesse m�todo "executar" � que � chamado pelos outros m�todos que s�o as
 * etapas dessa carga. Portanto se � necessario rodar um m�todo depois do outro,
 * eles devem ser chamados na ordem correta. Ex: incluiHP() vem antes de
 * inicializaHP(), portanto no m�todo executar() eles devem ser chamados nessa
 * ordem.
 * 
 * Terminado de executar todas as etapas � preciso retornar true. Se houver
 * algum problema(exce��o) na execu��o de uma das etapas, essa exce��o deve ser
 * lancada.
 * 
 * @author felipe.arruda
 * 
 */
public abstract class CargaBase {
	/**
	 * 
	 * Executa m�todos dessa carga.
	 * 
	 * @return Boolean - True se a carga for executada com sucesso ou false caso
	 *         ocorra algum erro.
	 *         
	 * @throws AplicacaoException - Pode retornar um erro de aplica��o.
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
