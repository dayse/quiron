package carga;

import java.util.ArrayList;
import java.util.List;

import modelo.Parametro;
import service.ParametroAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;

/**
 * 
 * Sobre a Carga: É uma Carga do sistema, portanto deve herdar de CargaBase e
 * implementar o método executar(). Nesse método "executar" é que é chamado
 * pelos outros métodos que são as etapas dessa carga. Portanto se é necessario
 * rodar um método depois do outro, eles devem ser chamados na ordem correta.
 * Ex: incluiHP() vem antes de inicializaHP(), portanto no método executar()
 * eles devem ser chamados nessa ordem.
 * 
 * Terminado de executar todas as etapas é preciso retornar true. Se houver
 * algum problema(exceção) na execução de uma das etapas, essa exceção deve ser
 * lancada.
 * 
 * Essa Carga: Classe responsável pela inclusão dos tipos de parâmetros. Esses
 * dados são utilizados apenas para carater ilustrativo, dando ao usuário melhor
 * compreensão de quais parâmetros serão utilizados para se chegar a uma
 * determinada avaliação.
 * 
 * @author bruno.oliveira
 * 
 */
public class CargaEstudoMultiplosParametros extends CargaBase {

	// Service
	public ParametroAppService parametroService;

	/**
	 * 
	 * Construdor da carga, responsável por instanciar os services.
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
		this.incluirParametros();
		return true;
	}

	/**
	 * 
	 * Método responsável por preparar e inserir os valores padrões dos
	 * parâmetros no banco.
	 * 
	 * @throws AplicacaoException
	 *             Retorna uma AplicacaoException caso ocorra uma exceção deste
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
		parametro1.setNome("Parametro Genérico 1");
		parametro1.setDescricao("Falta Inserir.");

		parametro2.setCodParametro("P010");
		parametro2.setNome("Parametro Genérico 2");
		parametro2.setDescricao("Falta Inserir.");

		parametro3.setCodParametro("P011");
		parametro3.setNome("Parametro Genérico 3");
		parametro3.setDescricao("Falta Inserir.");

		parametro4.setCodParametro("P012");
		parametro4.setNome("Parametro Genérico 4");
		parametro4.setDescricao("Falta Inserir.");

		parametro5.setCodParametro("P013");
		parametro5.setNome("Parametro Genérico 5");
		parametro5.setDescricao("Falta Inserir.");

		parametro6.setCodParametro("P014");
		parametro6.setNome("Parametro Genérico 6");
		parametro6.setDescricao("Falta Inserir.");

		parametro7.setCodParametro("P015");
		parametro7.setNome("Parametro Genérico 7");
		parametro7.setDescricao("Falta Inserir.");

		parametro8.setCodParametro("P016");
		parametro8.setNome("Parametro Genérico 8");
		parametro8.setDescricao("Falta Inserir.");
		
		parametro9.setCodParametro("P017");
		parametro9.setNome("Parametro Genérico 9");
		parametro9.setDescricao("Falta Inserir.");
		
		parametro10.setCodParametro("P018");
		parametro10.setNome("Parametro Genérico 10");
		parametro10.setDescricao("Falta Inserir.");
		
		parametro11.setCodParametro("P019");
		parametro11.setNome("Parametro Genérico 11");
		parametro11.setDescricao("Falta Inserir.");
		
		parametro12.setCodParametro("P020");
		parametro12.setNome("Parametro Genérico 12");
		parametro12.setDescricao("Falta Inserir.");
		
		parametro13.setCodParametro("P021");
		parametro13.setNome("Parametro Genérico 13");
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
