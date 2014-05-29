package carga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.Parametro;
import modelo.TipoUsuario;
import modelo.Usuario;
import service.AvalIndicacaoEspecAppService;
import service.EspecialistaAppService;
import service.IndicacaoAppService;
import service.ParametroAppService;
import service.TipoUsuarioAppService;
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.Constantes;
import util.JsonConfigLoader;
import DAO.exception.ObjetoNaoEncontradoException;

import com.google.gson.Gson;

/**
 * 
 * Sobre a Carga:
 * É uma Carga do sistema, portanto deve herdar de CargaBase e
 * implementar o método executar().
 * Nesse método "executar" é que é chamado pelos outros métodos que são 
 * as etapas dessa carga.
 * Portanto se é necessario rodar um método depois do outro, eles devem ser chamados
 * na ordem correta. Ex:
 * IncluiParametro() vem antes de IncluiAvalIndicacaoEspec(), portanto no método executar() eles devem ser chamados nessa ordem.
 * 
 * Terminado de executar todas as etapas é preciso retornar true.
 * Se houver algum problema(exceção) na execução de uma das etapas, essa exceção deve ser lancada.
 * 
 * Essa Carga:
 * Responsavel por fazer a carga das avaliações dadas pelos especilistas
 * para cada indicação cadastrada. Estamos usandos os valores fornecidos pelo Pedro Peloso.
 * 
 * @author bruno.oliveira
 *
 */
public class CargaAvalIndicacaoEspec extends CargaBase{
  
	// Services
	public static EspecialistaAppService especialistaService;
	private static IndicacaoAppService indicacaoService;
	public static ParametroAppService parametroService;
	public static AvalIndicacaoEspecAppService avalIndicacaoEspecService;
	
	/**
	 * 
	 * Construdor da carga, responsável por instanciar os services.
	 * 
	 * @author felipe.arruda
	 * 
	 */
	public CargaAvalIndicacaoEspec(){
		try {
			especialistaService = FabricaDeAppService
					.getAppService(EspecialistaAppService.class);
			indicacaoService = FabricaDeAppService
					.getAppService(IndicacaoAppService.class);
			parametroService = FabricaDeAppService
					.getAppService(ParametroAppService.class);
			avalIndicacaoEspecService = FabricaDeAppService
					.getAppService(AvalIndicacaoEspecAppService.class);
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
		this.incluirAvaliacoesDeIndicacoesPorEspecialistas();
		return true;
	}
	

	/**
	 * Metodo responsável por preparar e inserir os valores padrões de
	 * avaliações de indicações por especialistas no banco de dados
	 * 
	 * @author felipe.pontes
	 * @throws AplicacaoException
	 */
	public void incluirAvaliacoesDeIndicacoesPorEspecialistas() throws AplicacaoException {
		
				
		ArrayList<AvalIndicacaoEspec> avsParaIncluir = new ArrayList<AvalIndicacaoEspec>();
		try {
		
		Indicacao amox500 = indicacaoService.recuperaIndicacaoPorCodigo("amox500");
		Indicacao bactrim = indicacaoService.recuperaIndicacaoPorCodigo("bactrim");
		Indicacao amox1 = indicacaoService.recuperaIndicacaoPorCodigo("amox1");
		Indicacao levoflox = indicacaoService.recuperaIndicacaoPorCodigo("levoflox");
		Indicacao fluco = indicacaoService.recuperaIndicacaoPorCodigo("fluco");

		Especialista espec1 = especialistaService.recuperaEspecialistaPorCodigo("espec1");
		Especialista espec2 = especialistaService.recuperaEspecialistaPorCodigo("espec2");
		
		Parametro febre = parametroService.recuperaParametroPorCodigio("P1");
		Parametro disuria = parametroService.recuperaParametroPorCodigio("P2");
		Parametro diabetes = parametroService.recuperaParametroPorCodigio("P3");
		Parametro enterococos = parametroService.recuperaParametroPorCodigio("P4");
		Parametro escherichia = parametroService.recuperaParametroPorCodigio("P5");
		Parametro candida = parametroService.recuperaParametroPorCodigio("P6");
		Parametro efeitosColaterais = parametroService.recuperaParametroPorCodigio("P7");
		
		// Avaliações do especialista 1
		
		// Amoxicilina 500
		AvalIndicacaoEspec av1 = criarAvalIndicacaoDeEspec(espec1, amox500, febre, 0.6); 
		avsParaIncluir.add(av1);
		AvalIndicacaoEspec av2 = criarAvalIndicacaoDeEspec(espec1, amox500, disuria, 1.0); 
		avsParaIncluir.add(av2);
		AvalIndicacaoEspec av3 = criarAvalIndicacaoDeEspec(espec1, amox500, diabetes, 0.7); 
		avsParaIncluir.add(av3);
		AvalIndicacaoEspec av4 = criarAvalIndicacaoDeEspec(espec1, amox500, enterococos, 1.0); 
		avsParaIncluir.add(av4);
		AvalIndicacaoEspec av5 = criarAvalIndicacaoDeEspec(espec1, amox500, escherichia, 0.7); 
		avsParaIncluir.add(av5);
		AvalIndicacaoEspec av6 = criarAvalIndicacaoDeEspec(espec1, amox500, candida, 0.2); 
		avsParaIncluir.add(av6);
		AvalIndicacaoEspec av7 = criarAvalIndicacaoDeEspec(espec1, amox500, efeitosColaterais, 0.8); 
		avsParaIncluir.add(av7);
		
		// Bactrim
		AvalIndicacaoEspec av8 = criarAvalIndicacaoDeEspec(espec1, bactrim, febre, 0.5); 
		avsParaIncluir.add(av8);
		AvalIndicacaoEspec av9 = criarAvalIndicacaoDeEspec(espec1, bactrim, disuria, 0.8); 
		avsParaIncluir.add(av9);
		AvalIndicacaoEspec av10 = criarAvalIndicacaoDeEspec(espec1, bactrim, diabetes, 0.5); 
		avsParaIncluir.add(av10);
		AvalIndicacaoEspec av11 = criarAvalIndicacaoDeEspec(espec1, bactrim, enterococos, 0.7); 
		avsParaIncluir.add(av11);
		AvalIndicacaoEspec av12 = criarAvalIndicacaoDeEspec(espec1, bactrim, escherichia, 0.5); 
		avsParaIncluir.add(av12);
		AvalIndicacaoEspec av13 = criarAvalIndicacaoDeEspec(espec1, bactrim, candida, 0.1); 
		avsParaIncluir.add(av13);
		AvalIndicacaoEspec av14 = criarAvalIndicacaoDeEspec(espec1, bactrim, efeitosColaterais, 0.7); 
		avsParaIncluir.add(av14);		
		
		// Amoxicilina 100
		AvalIndicacaoEspec av15 = criarAvalIndicacaoDeEspec(espec1, amox1, febre, 0.8); 
		avsParaIncluir.add(av15);
		AvalIndicacaoEspec av16 = criarAvalIndicacaoDeEspec(espec1, amox1, disuria, 0.4); 
		avsParaIncluir.add(av16);
		AvalIndicacaoEspec av17 = criarAvalIndicacaoDeEspec(espec1, amox1, diabetes, 0.8); 
		avsParaIncluir.add(av17);
		AvalIndicacaoEspec av18 = criarAvalIndicacaoDeEspec(espec1, amox1, enterococos, 0.5); 
		avsParaIncluir.add(av18);
		AvalIndicacaoEspec av19 = criarAvalIndicacaoDeEspec(espec1, amox1, escherichia, 0.8); 
		avsParaIncluir.add(av19);
		AvalIndicacaoEspec av20 = criarAvalIndicacaoDeEspec(espec1, amox1, candida, 0.1); 
		avsParaIncluir.add(av20);
		AvalIndicacaoEspec av21 = criarAvalIndicacaoDeEspec(espec1, amox1, efeitosColaterais, 0.5); 
		avsParaIncluir.add(av21);		
		
		// Levofloxacina
		AvalIndicacaoEspec av22 = criarAvalIndicacaoDeEspec(espec1, levoflox, febre, 1.0); 
		avsParaIncluir.add(av22);
		AvalIndicacaoEspec av23 = criarAvalIndicacaoDeEspec(espec1, levoflox, disuria, 0.5); 
		avsParaIncluir.add(av23);
		AvalIndicacaoEspec av24 = criarAvalIndicacaoDeEspec(espec1, levoflox, diabetes, 1.0); 
		avsParaIncluir.add(av24);
		AvalIndicacaoEspec av25 = criarAvalIndicacaoDeEspec(espec1, levoflox, enterococos, 0.1); 
		avsParaIncluir.add(av25);
		AvalIndicacaoEspec av26 = criarAvalIndicacaoDeEspec(espec1, levoflox, escherichia, 0.7); 
		avsParaIncluir.add(av26);
		AvalIndicacaoEspec av27 = criarAvalIndicacaoDeEspec(espec1, levoflox, candida, 1.0); 
		avsParaIncluir.add(av27);
		AvalIndicacaoEspec av28 = criarAvalIndicacaoDeEspec(espec1, levoflox, efeitosColaterais, 0.8); 
		avsParaIncluir.add(av28);			

		// Fluconazol
		AvalIndicacaoEspec av29 = criarAvalIndicacaoDeEspec(espec1, fluco, febre, 0.8); 
		avsParaIncluir.add(av29);
		AvalIndicacaoEspec av30 = criarAvalIndicacaoDeEspec(espec1, fluco, disuria, 0.8); 
		avsParaIncluir.add(av30);
		AvalIndicacaoEspec av31 = criarAvalIndicacaoDeEspec(espec1, fluco, diabetes, 0.8); 
		avsParaIncluir.add(av31);
		AvalIndicacaoEspec av32 = criarAvalIndicacaoDeEspec(espec1, fluco, enterococos, 0.1); 
		avsParaIncluir.add(av32);
		AvalIndicacaoEspec av33 = criarAvalIndicacaoDeEspec(espec1, fluco, escherichia, 0.5); 
		avsParaIncluir.add(av33);
		AvalIndicacaoEspec av34 = criarAvalIndicacaoDeEspec(espec1, fluco, candida, 0.7); 
		avsParaIncluir.add(av34);
		AvalIndicacaoEspec av35 = criarAvalIndicacaoDeEspec(espec1, fluco, efeitosColaterais, 0.5); 
		avsParaIncluir.add(av35);		

		// Avaliações do especialista 2
		
		// Amoxicilina 500
		AvalIndicacaoEspec av1b = criarAvalIndicacaoDeEspec(espec2, amox500, febre, 0.9); 
		avsParaIncluir.add(av1b);
		AvalIndicacaoEspec av2b = criarAvalIndicacaoDeEspec(espec2, amox500, disuria, 1.0); 
		avsParaIncluir.add(av2b);
		AvalIndicacaoEspec av3b = criarAvalIndicacaoDeEspec(espec2, amox500, diabetes, 0.85); 
		avsParaIncluir.add(av3b);
		AvalIndicacaoEspec av4b = criarAvalIndicacaoDeEspec(espec2, amox500, enterococos, 1.0); 
		avsParaIncluir.add(av4b);
		AvalIndicacaoEspec av5b = criarAvalIndicacaoDeEspec(espec2, amox500, escherichia, 0.85); 
		avsParaIncluir.add(av5b);
		AvalIndicacaoEspec av6b = criarAvalIndicacaoDeEspec(espec2, amox500, candida, 0.2); 
		avsParaIncluir.add(av6b);
		AvalIndicacaoEspec av7b = criarAvalIndicacaoDeEspec(espec2, amox500, efeitosColaterais, 0.95); 
		avsParaIncluir.add(av7b);
		
		// Bactrim
		AvalIndicacaoEspec av8b = criarAvalIndicacaoDeEspec(espec2, bactrim, febre, 0.8); 
		avsParaIncluir.add(av8b);
		AvalIndicacaoEspec av9b = criarAvalIndicacaoDeEspec(espec2, bactrim, disuria, 0.95); 
		avsParaIncluir.add(av9b);
		AvalIndicacaoEspec av10b = criarAvalIndicacaoDeEspec(espec2, bactrim, diabetes, 0.8); 
		avsParaIncluir.add(av10b);
		AvalIndicacaoEspec av11b = criarAvalIndicacaoDeEspec(espec2, bactrim, enterococos, 0.85); 
		avsParaIncluir.add(av11b);
		AvalIndicacaoEspec av12b = criarAvalIndicacaoDeEspec(espec2, bactrim, escherichia, 0.8); 
		avsParaIncluir.add(av12b);
		AvalIndicacaoEspec av13b = criarAvalIndicacaoDeEspec(espec2, bactrim, candida, 0.1); 
		avsParaIncluir.add(av13b);
		AvalIndicacaoEspec av14b = criarAvalIndicacaoDeEspec(espec2, bactrim, efeitosColaterais, 0.85); 
		avsParaIncluir.add(av14b);		
		
		// Amoxicilina 100
		AvalIndicacaoEspec av15b = criarAvalIndicacaoDeEspec(espec2, amox1, febre, 0.5); 
		avsParaIncluir.add(av15b);
		AvalIndicacaoEspec av16b = criarAvalIndicacaoDeEspec(espec2, amox1, disuria, 0.4); 
		avsParaIncluir.add(av16b);
		AvalIndicacaoEspec av17b = criarAvalIndicacaoDeEspec(espec2, amox1, diabetes, 0.5); 
		avsParaIncluir.add(av17b);
		AvalIndicacaoEspec av18b = criarAvalIndicacaoDeEspec(espec2, amox1, enterococos, 0.8); 
		avsParaIncluir.add(av18b);
		AvalIndicacaoEspec av19b = criarAvalIndicacaoDeEspec(espec2, amox1, escherichia, 0.5); 
		avsParaIncluir.add(av19b);
		AvalIndicacaoEspec av20b = criarAvalIndicacaoDeEspec(espec2, amox1, candida, 0.1); 
		avsParaIncluir.add(av20b);
		AvalIndicacaoEspec av21b = criarAvalIndicacaoDeEspec(espec2, amox1, efeitosColaterais, 0.8); 
		avsParaIncluir.add(av21b);	
		
		// Levofloxacina
		AvalIndicacaoEspec av22b = criarAvalIndicacaoDeEspec(espec2, levoflox, febre, 1.0); 
		avsParaIncluir.add(av22b);
		AvalIndicacaoEspec av23b = criarAvalIndicacaoDeEspec(espec2, levoflox, disuria, 0.8); 
		avsParaIncluir.add(av23b);
		AvalIndicacaoEspec av24b = criarAvalIndicacaoDeEspec(espec2, levoflox, diabetes, 1.0); 
		avsParaIncluir.add(av24b);
		AvalIndicacaoEspec av25b = criarAvalIndicacaoDeEspec(espec2, levoflox, enterococos, 0.1); 
		avsParaIncluir.add(av25b);
		AvalIndicacaoEspec av26b = criarAvalIndicacaoDeEspec(espec2, levoflox, escherichia, 0.85); 
		avsParaIncluir.add(av26b);
		AvalIndicacaoEspec av27b = criarAvalIndicacaoDeEspec(espec2, levoflox, candida, 1.0); 
		avsParaIncluir.add(av27b);
		AvalIndicacaoEspec av28b = criarAvalIndicacaoDeEspec(espec2, levoflox, efeitosColaterais, 0.5); 
		avsParaIncluir.add(av28b);		

		// Fluconazol
		AvalIndicacaoEspec av29b = criarAvalIndicacaoDeEspec(espec2, fluco, febre, 0.95); 
		avsParaIncluir.add(av29b);
		AvalIndicacaoEspec av30b = criarAvalIndicacaoDeEspec(espec2, fluco, disuria, 0.5); 
		avsParaIncluir.add(av30b);
		AvalIndicacaoEspec av31b = criarAvalIndicacaoDeEspec(espec2, fluco, diabetes, 0.95); 
		avsParaIncluir.add(av31b);
		AvalIndicacaoEspec av32b = criarAvalIndicacaoDeEspec(espec2, fluco, enterococos, 0.1); 
		avsParaIncluir.add(av32b);
		AvalIndicacaoEspec av33b = criarAvalIndicacaoDeEspec(espec2, fluco, escherichia, 0.8); 
		avsParaIncluir.add(av33b);
		AvalIndicacaoEspec av34b = criarAvalIndicacaoDeEspec(espec2, fluco, candida, 0.85); 
		avsParaIncluir.add(av34b);
		AvalIndicacaoEspec av35b = criarAvalIndicacaoDeEspec(espec2, fluco, efeitosColaterais, 0.5); 
		avsParaIncluir.add(av35b);	

		//...
		
		//Incluir todas as avaliações
		for (AvalIndicacaoEspec avalIndicacaoEspec : avsParaIncluir) {
			avalIndicacaoEspecService.altera(avalIndicacaoEspec);
			
		}
		
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException(e);
		}
		
	}
	
	public AvalIndicacaoEspec criarAvalIndicacaoDeEspec(
									Especialista espec,
									Indicacao indicacao,
									Parametro paramentro,
									Double valor) throws ObjetoNaoEncontradoException{
		AvalIndicacaoEspec av =  avalIndicacaoEspecService.recuperaAvalIndicacaoEspecPorIndicacaoPorEspec(
				indicacao, espec);
		
		return av;
	}
	

}
