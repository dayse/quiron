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

	@Override
	public List<CargaBase> getCargasDependentes(){
		List<CargaBase> dependencias = new ArrayList<CargaBase>();
		dependencias.add(new CargaEspecialista());
		dependencias.add(new CargaIndicacao());
		dependencias.add(new CargaParametros());
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
		
		Parametro febre = parametroService.recuperaParametroPorCodigo("P001");
		Parametro disuria = parametroService.recuperaParametroPorCodigo("P002");
		Parametro diabetes = parametroService.recuperaParametroPorCodigo("P003");
		Parametro enterococos = parametroService.recuperaParametroPorCodigo("P004");
		Parametro escherichia = parametroService.recuperaParametroPorCodigo("P005");
		Parametro candida = parametroService.recuperaParametroPorCodigo("P006");
		Parametro efeitosColaterais = parametroService.recuperaParametroPorCodigo("P007");
		Parametro alergia = parametroService.recuperaParametroPorCodigo("P008");
		
		// Avaliações do especialista 1
		
		// Amoxicilina 500
		AvalIndicacaoEspec av1 = criarAvalIndicacaoDeEspec(espec1, amox500, febre); 
			av1.setValor(0.6);
			avsParaIncluir.add(av1);
		AvalIndicacaoEspec av2 = criarAvalIndicacaoDeEspec(espec1, amox500, disuria);
			av2.setValor(1.0);
			avsParaIncluir.add(av2);
		AvalIndicacaoEspec av3 = criarAvalIndicacaoDeEspec(espec1, amox500, diabetes);
			av3.setValor(0.7);
			avsParaIncluir.add(av3);
		AvalIndicacaoEspec av4 = criarAvalIndicacaoDeEspec(espec1, amox500, enterococos);
			av4.setValor(1.0);
			avsParaIncluir.add(av4);
		AvalIndicacaoEspec av5 = criarAvalIndicacaoDeEspec(espec1, amox500, escherichia);
			av5.setValor(0.7);
			avsParaIncluir.add(av5);
		AvalIndicacaoEspec av6 = criarAvalIndicacaoDeEspec(espec1, amox500, candida); 
			av6.setValor(0.2);
			avsParaIncluir.add(av6);
		AvalIndicacaoEspec av7 = criarAvalIndicacaoDeEspec(espec1, amox500, efeitosColaterais); 
			av7.setValor(0.8);
			avsParaIncluir.add(av7);			
		AvalIndicacaoEspec av8 = criarAvalIndicacaoDeEspec(espec1, amox500, alergia); 
			av8.setValor(0.0);
			avsParaIncluir.add(av8);	
				
		// Bactrim
		AvalIndicacaoEspec av9 = criarAvalIndicacaoDeEspec(espec1, bactrim, febre);
			av9.setValor(0.5);
			avsParaIncluir.add(av9);
		AvalIndicacaoEspec av10 = criarAvalIndicacaoDeEspec(espec1, bactrim, disuria); 
			av10.setValor(0.8);
			avsParaIncluir.add(av10);
		AvalIndicacaoEspec av11 = criarAvalIndicacaoDeEspec(espec1, bactrim, diabetes);
			av11.setValor(0.5);
			avsParaIncluir.add(av11);
		AvalIndicacaoEspec av12 = criarAvalIndicacaoDeEspec(espec1, bactrim, enterococos);
			av12.setValor(0.7);
			avsParaIncluir.add(av12);
		AvalIndicacaoEspec av13 = criarAvalIndicacaoDeEspec(espec1, bactrim, escherichia);
			av13.setValor(0.5);
			avsParaIncluir.add(av13);
		AvalIndicacaoEspec av14 = criarAvalIndicacaoDeEspec(espec1, bactrim, candida); 
			av14.setValor(0.1);
			avsParaIncluir.add(av14);
		AvalIndicacaoEspec av15 = criarAvalIndicacaoDeEspec(espec1, bactrim, efeitosColaterais); 
			av15.setValor(0.7);
			avsParaIncluir.add(av15);
		AvalIndicacaoEspec av16 = criarAvalIndicacaoDeEspec(espec1, bactrim, alergia); 
			av16.setValor(1.0);
			avsParaIncluir.add(av16);
		
		// Amoxicilina 100
		AvalIndicacaoEspec av17 = criarAvalIndicacaoDeEspec(espec1, amox1, febre); 
			av17.setValor(0.8);
			avsParaIncluir.add(av17);
		AvalIndicacaoEspec av18 = criarAvalIndicacaoDeEspec(espec1, amox1, disuria); 
			av18.setValor(0.4);
			avsParaIncluir.add(av18);
		AvalIndicacaoEspec av19 = criarAvalIndicacaoDeEspec(espec1, amox1, diabetes); 
			av19.setValor(0.8);
			avsParaIncluir.add(av19);
		AvalIndicacaoEspec av20 = criarAvalIndicacaoDeEspec(espec1, amox1, enterococos); 
			av20.setValor(0.5);
			avsParaIncluir.add(av20);
		AvalIndicacaoEspec av21 = criarAvalIndicacaoDeEspec(espec1, amox1, escherichia); 
			av21.setValor(0.8);
			avsParaIncluir.add(av21);
		AvalIndicacaoEspec av22 = criarAvalIndicacaoDeEspec(espec1, amox1, candida); 
			av22.setValor(0.1);
			avsParaIncluir.add(av22);
		AvalIndicacaoEspec av23 = criarAvalIndicacaoDeEspec(espec1, amox1, efeitosColaterais); 
			av23.setValor(0.5);
			avsParaIncluir.add(av23);	
		AvalIndicacaoEspec av24 = criarAvalIndicacaoDeEspec(espec1, amox1, alergia); 
			av24.setValor(0.0);
			avsParaIncluir.add(av24);			
		
		// Levofloxacina
		AvalIndicacaoEspec av25 = criarAvalIndicacaoDeEspec(espec1, levoflox, febre);
			av25.setValor(1.0);
			avsParaIncluir.add(av25);
		AvalIndicacaoEspec av26 = criarAvalIndicacaoDeEspec(espec1, levoflox, disuria); 
			av26.setValor(0.5);
			avsParaIncluir.add(av26);
		AvalIndicacaoEspec av27 = criarAvalIndicacaoDeEspec(espec1, levoflox, diabetes); 
			av27.setValor(1.0);
			avsParaIncluir.add(av27);
		AvalIndicacaoEspec av28 = criarAvalIndicacaoDeEspec(espec1, levoflox, enterococos); 
			av28.setValor(0.1);
			avsParaIncluir.add(av28);
		AvalIndicacaoEspec av29 = criarAvalIndicacaoDeEspec(espec1, levoflox, escherichia); 
			av29.setValor(0.7);
			avsParaIncluir.add(av29);
		AvalIndicacaoEspec av30 = criarAvalIndicacaoDeEspec(espec1, levoflox, candida); 
			av30.setValor(1.0);
			avsParaIncluir.add(av30);
		AvalIndicacaoEspec av31 = criarAvalIndicacaoDeEspec(espec1, levoflox, efeitosColaterais); 
			av31.setValor(0.8);
			avsParaIncluir.add(av31);			
		AvalIndicacaoEspec av32 = criarAvalIndicacaoDeEspec(espec1, levoflox, alergia); 
			av32.setValor(1.0);
			avsParaIncluir.add(av32);			

		// Fluconazol
		AvalIndicacaoEspec av33 = criarAvalIndicacaoDeEspec(espec1, fluco, febre); 
			av33.setValor(0.8);
			avsParaIncluir.add(av33);
		AvalIndicacaoEspec av34 = criarAvalIndicacaoDeEspec(espec1, fluco, disuria); 
			av34.setValor(0.8);
			avsParaIncluir.add(av34);
		AvalIndicacaoEspec av35 = criarAvalIndicacaoDeEspec(espec1, fluco, diabetes); 
			av35.setValor(0.8);
			avsParaIncluir.add(av35);
		AvalIndicacaoEspec av36 = criarAvalIndicacaoDeEspec(espec1, fluco, enterococos); 
			av36.setValor(0.1);
			avsParaIncluir.add(av36);
		AvalIndicacaoEspec av37 = criarAvalIndicacaoDeEspec(espec1, fluco, escherichia); 
			av37.setValor(0.5);
			avsParaIncluir.add(av37);
		AvalIndicacaoEspec av38 = criarAvalIndicacaoDeEspec(espec1, fluco, candida); 
			av38.setValor(0.7);
			avsParaIncluir.add(av38);
		AvalIndicacaoEspec av39 = criarAvalIndicacaoDeEspec(espec1, fluco, efeitosColaterais); 
			av39.setValor(0.5);
			avsParaIncluir.add(av39);
		AvalIndicacaoEspec av40 = criarAvalIndicacaoDeEspec(espec1, fluco, alergia); 
			av40.setValor(1.0);
			avsParaIncluir.add(av40);

		// Avaliações do especialista 2
		
		// Amoxicilina 500
		AvalIndicacaoEspec av1b = criarAvalIndicacaoDeEspec(espec2, amox500, febre); 
			av1b.setValor(0.9);
			avsParaIncluir.add(av1b);
		AvalIndicacaoEspec av2b = criarAvalIndicacaoDeEspec(espec2, amox500, disuria); 
			av2b.setValor(1.0);
			avsParaIncluir.add(av2b);
		AvalIndicacaoEspec av3b = criarAvalIndicacaoDeEspec(espec2, amox500, diabetes); 
			av3b.setValor(0.85);
			avsParaIncluir.add(av3b);
		AvalIndicacaoEspec av4b = criarAvalIndicacaoDeEspec(espec2, amox500, enterococos); 
			av4b.setValor(1.0);
			avsParaIncluir.add(av4b);
		AvalIndicacaoEspec av5b = criarAvalIndicacaoDeEspec(espec2, amox500, escherichia); 
			av5b.setValor(0.85);
			avsParaIncluir.add(av5b);
		AvalIndicacaoEspec av6b = criarAvalIndicacaoDeEspec(espec2, amox500, candida); 
			av6b.setValor(0.2);
			avsParaIncluir.add(av6b);
		AvalIndicacaoEspec av7b = criarAvalIndicacaoDeEspec(espec2, amox500, efeitosColaterais); 
			av7b.setValor(0.95);
			avsParaIncluir.add(av7b);
		AvalIndicacaoEspec av8b = criarAvalIndicacaoDeEspec(espec2, amox500, alergia); 
			av8b.setValor(0.0);
			avsParaIncluir.add(av8b);			
		
		// Bactrim
		AvalIndicacaoEspec av9b = criarAvalIndicacaoDeEspec(espec2, bactrim, febre); 
			av9b.setValor(0.8);
			avsParaIncluir.add(av9b);
		AvalIndicacaoEspec av10b = criarAvalIndicacaoDeEspec(espec2, bactrim, disuria); 
			av10b.setValor(0.95);
			avsParaIncluir.add(av10b);
		AvalIndicacaoEspec av11b = criarAvalIndicacaoDeEspec(espec2, bactrim, diabetes); 
			av11b.setValor(0.8);
			avsParaIncluir.add(av11b);
		AvalIndicacaoEspec av12b = criarAvalIndicacaoDeEspec(espec2, bactrim, enterococos); 
			av12b.setValor(0.85);
			avsParaIncluir.add(av12b);
		AvalIndicacaoEspec av13b = criarAvalIndicacaoDeEspec(espec2, bactrim, escherichia); 
			av13b.setValor(0.8);
			avsParaIncluir.add(av13b);
		AvalIndicacaoEspec av14b = criarAvalIndicacaoDeEspec(espec2, bactrim, candida); 
			av14b.setValor(0.1);
			avsParaIncluir.add(av14b);
		AvalIndicacaoEspec av15b = criarAvalIndicacaoDeEspec(espec2, bactrim, efeitosColaterais); 
			av15b.setValor(0.85);
			avsParaIncluir.add(av15b);
		AvalIndicacaoEspec av16b = criarAvalIndicacaoDeEspec(espec2, bactrim, alergia); 
			av16b.setValor(1.0);
			avsParaIncluir.add(av16b);			
		
		// Amoxicilina 100
		AvalIndicacaoEspec av17b = criarAvalIndicacaoDeEspec(espec2, amox1, febre); 
			av17b.setValor(0.5);
			avsParaIncluir.add(av17b);
		AvalIndicacaoEspec av18b = criarAvalIndicacaoDeEspec(espec2, amox1, disuria); 
			av18b.setValor(0.4);
			avsParaIncluir.add(av18b);
		AvalIndicacaoEspec av19b = criarAvalIndicacaoDeEspec(espec2, amox1, diabetes); 
			av19b.setValor(0.5);
			avsParaIncluir.add(av19b);
		AvalIndicacaoEspec av20b = criarAvalIndicacaoDeEspec(espec2, amox1, enterococos); 
			av20b.setValor(0.8);
			avsParaIncluir.add(av20b);
		AvalIndicacaoEspec av21b = criarAvalIndicacaoDeEspec(espec2, amox1, escherichia); 
			av21b.setValor(0.5);
			avsParaIncluir.add(av21b);
		AvalIndicacaoEspec av22b = criarAvalIndicacaoDeEspec(espec2, amox1, candida); 
			av22b.setValor(0.1);
			avsParaIncluir.add(av22b);
		AvalIndicacaoEspec av23b = criarAvalIndicacaoDeEspec(espec2, amox1, efeitosColaterais); 
			av23b.setValor(0.8);
			avsParaIncluir.add(av23b);
		AvalIndicacaoEspec av24b = criarAvalIndicacaoDeEspec(espec2, amox1, alergia); 
			av24b.setValor(0.0);
			avsParaIncluir.add(av24b);			
		
		// Levofloxacina
		AvalIndicacaoEspec av25b = criarAvalIndicacaoDeEspec(espec2, levoflox, febre); 
			av25b.setValor(1.0);
			avsParaIncluir.add(av25b);
		AvalIndicacaoEspec av26b = criarAvalIndicacaoDeEspec(espec2, levoflox, disuria); 
			av26b.setValor(0.8);
			avsParaIncluir.add(av26b);
		AvalIndicacaoEspec av27b = criarAvalIndicacaoDeEspec(espec2, levoflox, diabetes); 
			av27b.setValor(1.0);
			avsParaIncluir.add(av27b);
		AvalIndicacaoEspec av28b = criarAvalIndicacaoDeEspec(espec2, levoflox, enterococos); 
			av28b.setValor(0.1);
			avsParaIncluir.add(av28b);
		AvalIndicacaoEspec av29b = criarAvalIndicacaoDeEspec(espec2, levoflox, escherichia); 
			av29b.setValor(0.85);
			avsParaIncluir.add(av29b);
		AvalIndicacaoEspec av30b = criarAvalIndicacaoDeEspec(espec2, levoflox, candida); 
			av30b.setValor(1.0);
			avsParaIncluir.add(av30b);
		AvalIndicacaoEspec av31b = criarAvalIndicacaoDeEspec(espec2, levoflox, efeitosColaterais); 
			av31b.setValor(0.5);
			avsParaIncluir.add(av31b);	
		AvalIndicacaoEspec av32b = criarAvalIndicacaoDeEspec(espec2, levoflox, alergia); 
			av32b.setValor(1.0);
			avsParaIncluir.add(av32b);				

		// Fluconazol
		AvalIndicacaoEspec av33b = criarAvalIndicacaoDeEspec(espec2, fluco, febre); 
			av33b.setValor(0.95);
			avsParaIncluir.add(av33b);
		AvalIndicacaoEspec av34b = criarAvalIndicacaoDeEspec(espec2, fluco, disuria); 
			av34b.setValor(0.5);
			avsParaIncluir.add(av34b);
		AvalIndicacaoEspec av35b = criarAvalIndicacaoDeEspec(espec2, fluco, diabetes); 
			av35b.setValor(0.95);
			avsParaIncluir.add(av35b);
		AvalIndicacaoEspec av36b = criarAvalIndicacaoDeEspec(espec2, fluco, enterococos); 
			av36b.setValor(0.1);
			avsParaIncluir.add(av36b);
		AvalIndicacaoEspec av37b = criarAvalIndicacaoDeEspec(espec2, fluco, escherichia); 
			av37b.setValor(0.8);
			avsParaIncluir.add(av37b);
		AvalIndicacaoEspec av38b = criarAvalIndicacaoDeEspec(espec2, fluco, candida); 
			av38b.setValor(0.85);
			avsParaIncluir.add(av38b);
		AvalIndicacaoEspec av39b = criarAvalIndicacaoDeEspec(espec2, fluco, efeitosColaterais); 
			av39b.setValor(0.5);
			avsParaIncluir.add(av39b);	
		AvalIndicacaoEspec av40b = criarAvalIndicacaoDeEspec(espec2, fluco, alergia); 
			av40b.setValor(1.0);
			avsParaIncluir.add(av40b);				

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
									Especialista especialista,
									Indicacao indicacao,
									Parametro parametro) throws ObjetoNaoEncontradoException{
		
		AvalIndicacaoEspec av =  avalIndicacaoEspecService
				.recuperaAvaliacaoPorEspecialistaIndicacaoParametro
						(especialista, indicacao, parametro);
		
		return av;
	}
	

}
