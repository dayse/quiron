package carga;

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
public class CargaParametros extends CargaBase {

	// Service
	public ParametroAppService parametroService;

	/**
	 * 
	 * Construdor da carga, responsável por instanciar os services.
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
		disuria.setNome("Disúria");
		disuria
				.setDescricao("Intensidade da dor, desconforto ou sensação de queimação no paciente ao urinar.");

		diabetes.setCodParametro("P3");
		diabetes.setNome("Diabetes");
		diabetes.setDescricao("Tipo de diabetes do paciente, caso tenha.");

		enterococos.setCodParametro("P4");
		enterococos.setNome("Etiologia Enterococos");
		enterococos
				.setDescricao("Ocorrência de etiologia do tipo Enterococos.");

		escherichia.setCodParametro("P5");
		escherichia.setNome("Etiologia Escherichia");
		escherichia
				.setDescricao("Ocorrência de etiologia do tipo Escherichia.");

		candida.setCodParametro("P6");
		candida.setNome("Etiologia Cândida");
		candida.setDescricao("Ocorrência de etiologia do tipo Cândida.");

		efeitosColaterais.setCodParametro("P7");
		efeitosColaterais.setNome("Não causar Efeitos Colaterais");
		efeitosColaterais
				.setDescricao("Sobre o grau de ocorrência de efeitos colaterais.");

		parametroService.inclui(febre);
		parametroService.inclui(disuria);
		parametroService.inclui(diabetes);
		parametroService.inclui(enterococos);
		parametroService.inclui(escherichia);
		parametroService.inclui(candida);
		parametroService.inclui(efeitosColaterais);
	}

}
