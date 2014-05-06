package carga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.TipoUsuario;
import modelo.Usuario;
import service.AvalIndicacaoEspecAppService;
import service.EspecialistaAppService;
import service.IndicacaoAppService;
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
 * incluiHP() vem antes de inicializaHP(), portanto no método executar() eles devem ser chamados nessa ordem.
 * 
 * Terminado de executar todas as etapas é preciso retornar true.
 * Se houver algum problema(exceção) na execução de uma das etapas, essa exceção deve ser lancada.
 * 
 * Essa Carga:
 * Responsavel por fazer a carga das indicações contidas nos
 * arquivos de modelagens e dados iniciais do sistema
 * 
 * @author felipe.arruda
 *
 */
public class CargaAvalIndicacaoEspec extends CargaBase{
  
	// Services
	public static EspecialistaAppService especialistaService;
	private static IndicacaoAppService indicacaoService;
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
		

		//avals do espec 1:
		AvalIndicacaoEspec av1 = criarAvalIndicacaoDeEspec(
									espec1, amox500,
									0.8, 1.0, 0.8, 1.0, 0.8, 0.2, 0.9 );
		avsParaIncluir.add(av1);
		
		AvalIndicacaoEspec av2 = criarAvalIndicacaoDeEspec(
									espec1, bactrim,
									0.7, 0.9, 0.7, 0.8, 0.7, 0.1, 0.8 );
		avsParaIncluir.add(av2);
		
		AvalIndicacaoEspec av3 = criarAvalIndicacaoDeEspec(
									espec1, amox1,
									0.6, 0.4, 0.6, 0.7, 0.6, 0.1, 0.7 );
		avsParaIncluir.add(av3);

		AvalIndicacaoEspec av4 = criarAvalIndicacaoDeEspec(
									espec1, levoflox,
									1.0, 0.7, 1.0, 0.1, 0.8, 1.0, 0.6 );
		avsParaIncluir.add(av4);

		AvalIndicacaoEspec av5 = criarAvalIndicacaoDeEspec(
									espec1, fluco,
									0.9, 0.6, 0.9, 0.1, 0.7, 0.8, 0.5 );
		avsParaIncluir.add(av5);
		
		
		//avals do espec 2:
		
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
									Double febre,
									Double disuria,
									Double diabetes,
									Double enterococos,
									Double escherichia,
									Double candida,
									Double efeitosColaterais) throws ObjetoNaoEncontradoException{
		AvalIndicacaoEspec av =  avalIndicacaoEspecService.recuperaAvalIndicacaoEspecPorIndicacaoPorEspec(
				indicacao, espec);
		
		av.setFebre(febre);
		av.setDisuria(disuria);
		av.setDiabetes(diabetes);
		av.setEnterococos(enterococos);
		av.setEscherichia(escherichia);
		av.setCandida(candida);
		av.setEfeitosColaterais(efeitosColaterais);
		return av;
	}
	

}
