package carga;

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
public class CargaParametros extends CargaBase {

	// Service
	public ParametroAppService parametroService;

	/**
	 * 
	 * Construdor da carga, respons�vel por instanciar os services.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public CargaParametros() {
		try {
			parametroService = FabricaDeAppService
					.getAppService(ParametroAppService.class);
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
		Parametro febre = new Parametro();
		Parametro disuria = new Parametro();
		Parametro diabetes = new Parametro();
		Parametro enterococos = new Parametro();
		Parametro escherichia = new Parametro();
		Parametro candida = new Parametro();
		Parametro efeitosColaterais = new Parametro();

		febre.setCodParametro("P1");
		febre.setNome("Febre");
		febre.setDescricao("Temperatura do paciente.");

		disuria.setCodParametro("P2");
		disuria.setNome("Dis�ria");
		disuria
				.setDescricao("Intensidade da dor, desconforto ou sensa��o de queima��o no paciente ao urinar.");

		diabetes.setCodParametro("P3");
		diabetes.setNome("Diabetes");
		diabetes.setDescricao("Tipo de diabetes do paciente, caso tenha.");

		enterococos.setCodParametro("P4");
		enterococos.setNome("Etiologia Enterococos");
		enterococos
				.setDescricao("Ocorr�ncia de etiologia do tipo Enterococos.");

		escherichia.setCodParametro("P5");
		escherichia.setNome("Etiologia Escherichia");
		escherichia
				.setDescricao("Ocorr�ncia de etiologia do tipo Escherichia.");

		candida.setCodParametro("P6");
		candida.setNome("Etiologia C�ndida");
		candida.setDescricao("Ocorr�ncia de etiologia do tipo C�ndida.");

		efeitosColaterais.setCodParametro("P7");
		efeitosColaterais.setNome("N�o causar Efeitos Colaterais");
		efeitosColaterais
				.setDescricao("Sobre o grau de ocorr�ncia de efeitos colaterais.");

		parametroService.inclui(febre);
		parametroService.inclui(disuria);
		parametroService.inclui(diabetes);
		parametroService.inclui(enterococos);
		parametroService.inclui(escherichia);
		parametroService.inclui(candida);
		parametroService.inclui(efeitosColaterais);
	}

}
