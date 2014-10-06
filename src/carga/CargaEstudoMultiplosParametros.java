package carga;

import java.util.ArrayList;
import java.util.List;

import modelo.Parametro;
import service.ParametroAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;

/**
 * 
 * Sobre a Carga: � uma Carga do sistema, portanto deve herdar de CargaBase e
 * implementar o m�todo executar(). Nesse m�todo "executar" � que � chamado
 * pelos outros m�todos que s�o as etapas dessa carga. Portanto se � necessario
 * rodar um m�todo depois do outro, eles devem ser chamados na ordem correta.
 * Ex: incluiHP() vem antes de inicializaHP(), portanto no m�todo executar()
 * eles devem ser chamados nessa ordem.
 * 
 * Terminado de executar todas as etapas � preciso retornar true. Se houver
 * algum problema(exce��o) na execu��o de uma das etapas, essa exce��o deve ser
 * lancada.
 * 
 * Essa Carga: Classe respons�vel pela inclus�o dos tipos de par�metros. Esses
 * dados s�o utilizados apenas para carater ilustrativo, dando ao usu�rio melhor
 * compreens�o de quais par�metros ser�o utilizados para se chegar a uma
 * determinada avalia��o.
 * 
 * @author bruno.oliveira
 * 
 */
public class CargaEstudoMultiplosParametros extends CargaBase {

	// Service
	public ParametroAppService parametroService;

	/**
	 * 
	 * Construdor da carga, respons�vel por instanciar os services.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public CargaEstudoMultiplosParametros() {
		try {
			parametroService = FabricaDeAppService
					.getAppService(ParametroAppService.class);
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
	 * M�todo herdado de CargaBase e utilizado para definir as etapas
	 * de execu��o desta carga.
	 * 
	 * @return Boolean - True se n�o ocorrer nenhum problema (exce��o).
	 * @throws AplicacaoException
	 *             Retorna uma AplicacaoException caso ocorra uma exce��o deste
	 *             tipo.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	@Override
	public boolean executar() throws AplicacaoException {
		this.incluirParametros();
		return true;
	}

	/**
	 * 
	 * M�todo respons�vel por preparar e inserir os valores padr�es dos
	 * par�metros no banco.
	 * 
	 * @throws AplicacaoException
	 *             Retorna uma AplicacaoException caso ocorra uma exce��o deste
	 *             tipo.
	 *             
	 * @author bruno.oliveira
	 * 
	 */
	public void incluirParametros() throws AplicacaoException {
		Parametro parametro1 = new Parametro();
		Parametro parametro2 = new Parametro();
		Parametro parametro3 = new Parametro();
		Parametro parametro4 = new Parametro();
		Parametro parametro5 = new Parametro();
		Parametro parametro6 = new Parametro();
		Parametro parametro7 = new Parametro();
		Parametro parametro8 = new Parametro();
		Parametro parametro9 = new Parametro();
		Parametro parametro10 = new Parametro();
		Parametro parametro11 = new Parametro();
		Parametro parametro12 = new Parametro();
		Parametro parametro13 = new Parametro();
		
		parametro1.setCodParametro("P009");
		parametro1.setNome("Parametro Gen�rico 1");
		parametro1.setDescricao("Falta Inserir.");

		parametro2.setCodParametro("P010");
		parametro2.setNome("Parametro Gen�rico 2");
		parametro2.setDescricao("Falta Inserir.");

		parametro3.setCodParametro("P011");
		parametro3.setNome("Parametro Gen�rico 3");
		parametro3.setDescricao("Falta Inserir.");

		parametro4.setCodParametro("P012");
		parametro4.setNome("Parametro Gen�rico 4");
		parametro4.setDescricao("Falta Inserir.");

		parametro5.setCodParametro("P013");
		parametro5.setNome("Parametro Gen�rico 5");
		parametro5.setDescricao("Falta Inserir.");

		parametro6.setCodParametro("P014");
		parametro6.setNome("Parametro Gen�rico 6");
		parametro6.setDescricao("Falta Inserir.");

		parametro7.setCodParametro("P015");
		parametro7.setNome("Parametro Gen�rico 7");
		parametro7.setDescricao("Falta Inserir.");

		parametro8.setCodParametro("P016");
		parametro8.setNome("Parametro Gen�rico 8");
		parametro8.setDescricao("Falta Inserir.");
		
		parametro9.setCodParametro("P017");
		parametro9.setNome("Parametro Gen�rico 9");
		parametro9.setDescricao("Falta Inserir.");
		
		parametro10.setCodParametro("P018");
		parametro10.setNome("Parametro Gen�rico 10");
		parametro10.setDescricao("Falta Inserir.");
		
		parametro11.setCodParametro("P019");
		parametro11.setNome("Parametro Gen�rico 11");
		parametro11.setDescricao("Falta Inserir.");
		
		parametro12.setCodParametro("P020");
		parametro12.setNome("Parametro Gen�rico 12");
		parametro12.setDescricao("Falta Inserir.");
		
		parametro13.setCodParametro("P021");
		parametro13.setNome("Parametro Gen�rico 13");
		parametro13.setDescricao("Falta Inserir.");		
		
		parametroService.inclui(parametro1);
		parametroService.inclui(parametro2);
		parametroService.inclui(parametro3);
		parametroService.inclui(parametro4);
		parametroService.inclui(parametro5);
		parametroService.inclui(parametro6);
		parametroService.inclui(parametro7);
		parametroService.inclui(parametro8);
		parametroService.inclui(parametro9);
		parametroService.inclui(parametro10);
		parametroService.inclui(parametro11);
		parametroService.inclui(parametro12);
		parametroService.inclui(parametro13);		
	}

}
