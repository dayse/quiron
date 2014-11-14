package carga;

import java.util.ArrayList;
import java.util.List;

import DAO.exception.ObjetoNaoEncontradoException;
import modelo.AvalIndicacaoEspec;
import modelo.Especialista;
import modelo.Indicacao;
import modelo.Parametro;
import service.AvalIndicacaoEspecAppService;
import service.EspecialistaAppService;
import service.IndicacaoAppService;
import service.ParametroAppService;
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
public class CargaAvalIndicacaoEspecIntermediaria extends CargaBase {
	
	// Services
	public static EspecialistaAppService especialistaService;
	private static IndicacaoAppService indicacaoService;
	public static ParametroAppService parametroService;
	public static AvalIndicacaoEspecAppService avalIndicacaoEspecService;	
	
	/**
	 * 
	 * Construdor da carga, responsável por instanciar os services.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public CargaAvalIndicacaoEspecIntermediaria(){
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
	public List<CargaBase> getCargasDependentes() {
		List<CargaBase> dependencias = new ArrayList<CargaBase>();
		dependencias.add(new CargaParametroIntermediario());
		dependencias.add(new CargaIndicacaoIntermediaria());
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
		this.incluiAvalIndicacaoEspecIntermediaria();
		return true;
	}
	
	/**
	 * Metodo responsável por preparar e inserir os valores padrões de
	 * avaliações de indicações por especialistas no banco de dados
	 * 
	 * @author bruno.oliveira
	 * @throws AplicacaoException
	 */	
	public void incluiAvalIndicacaoEspecIntermediaria() throws AplicacaoException {
		ArrayList<AvalIndicacaoEspec> avsParaIncluir = new ArrayList<AvalIndicacaoEspec>();
		try{
			Indicacao cefalexina = indicacaoService.recuperaIndicacaoPorCodigo("cefalexina");
			Indicacao axetil = indicacaoService.recuperaIndicacaoPorCodigo("cefuroxima");
			Indicacao cefaclor = indicacaoService.recuperaIndicacaoPorCodigo("cefaclor");
			Indicacao cefixima = indicacaoService.recuperaIndicacaoPorCodigo("cefixima");
			Indicacao ampicilina = indicacaoService.recuperaIndicacaoPorCodigo("ampicilina");
			Indicacao amoxacilina = indicacaoService.recuperaIndicacaoPorCodigo("amoxacilina");
			Indicacao tazobactam = indicacaoService.recuperaIndicacaoPorCodigo("amp-tazo");
			Indicacao clavulanato = indicacaoService.recuperaIndicacaoPorCodigo("amox-clavun");
			Indicacao nitro = indicacaoService.recuperaIndicacaoPorCodigo("nitro");
			Indicacao cipro = indicacaoService.recuperaIndicacaoPorCodigo("ciprofl");	
			Indicacao fosfomicina = indicacaoService.recuperaIndicacaoPorCodigo("fosfomicina");
			Indicacao norfloxacina = indicacaoService.recuperaIndicacaoPorCodigo("norfloxacina");
			Indicacao sulfatri = indicacaoService.recuperaIndicacaoPorCodigo("sulfatr-meto");				
	
			Especialista espec1 = especialistaService.recuperaEspecialistaPorCodigo("espec1");
			Especialista espec2 = especialistaService.recuperaEspecialistaPorCodigo("espec2");
			
			Parametro klebsiella = parametroService.recuperaParametroPorCodigo("P009");
			Parametro proteus = parametroService.recuperaParametroPorCodigo("P010");
			Parametro enterobacter = parametroService.recuperaParametroPorCodigo("P011");
			
			AvalIndicacaoEspec av1 = criarAvalIndicacaoDeEspec(espec1, cefalexina, klebsiella);
				av1.setValor(0);
				avsParaIncluir.add(av1);
			AvalIndicacaoEspec av2 = criarAvalIndicacaoDeEspec(espec1, axetil, klebsiella);
			   av2.setValor(0);
			   avsParaIncluir.add(av2); 
			AvalIndicacaoEspec av3 = criarAvalIndicacaoDeEspec(espec1, cefaclor, klebsiella);
			   av3.setValor(0);
			   avsParaIncluir.add(av3);
			AvalIndicacaoEspec av4 = criarAvalIndicacaoDeEspec(espec1, cefixima, klebsiella);
			   av4.setValor(0);
			   avsParaIncluir.add(av4);
			AvalIndicacaoEspec av5 = criarAvalIndicacaoDeEspec(espec1, ampicilina, klebsiella);
			   av5.setValor(0);
			   avsParaIncluir.add(av5);
			AvalIndicacaoEspec av6 = criarAvalIndicacaoDeEspec(espec1, amoxacilina, klebsiella);
			   av6.setValor(0);
			   avsParaIncluir.add(av6);
			AvalIndicacaoEspec av7 = criarAvalIndicacaoDeEspec(espec1, tazobactam, klebsiella);
			   av7.setValor(0);
			   avsParaIncluir.add(av7);
			AvalIndicacaoEspec av8 = criarAvalIndicacaoDeEspec(espec1, clavulanato, klebsiella);
			   av8.setValor(0);
			   avsParaIncluir.add(av8);
			AvalIndicacaoEspec av9 = criarAvalIndicacaoDeEspec(espec1, nitro, klebsiella);
			   av9.setValor(0);
			   avsParaIncluir.add(av9);
			AvalIndicacaoEspec av10 = criarAvalIndicacaoDeEspec(espec1, cipro, klebsiella);
			   av10.setValor(0);
			   avsParaIncluir.add(av10);
			AvalIndicacaoEspec av11 = criarAvalIndicacaoDeEspec(espec1, fosfomicina, klebsiella);
			   av11.setValor(0);
			   avsParaIncluir.add(av11);
			AvalIndicacaoEspec av12 = criarAvalIndicacaoDeEspec(espec1, norfloxacina, klebsiella);
			   av12.setValor(0);
			   avsParaIncluir.add(av12);
			AvalIndicacaoEspec av13  = criarAvalIndicacaoDeEspec(espec1, sulfatri, klebsiella);
			   av13.setValor(0);
			   avsParaIncluir.add(av13);
			
			AvalIndicacaoEspec av1b = criarAvalIndicacaoDeEspec(espec1, cefalexina, proteus);
				av1b.setValor(0);
				avsParaIncluir.add(av1b);
			AvalIndicacaoEspec av2b = criarAvalIndicacaoDeEspec(espec1, axetil, proteus);
				av2b.setValor(0);
				avsParaIncluir.add(av2b); 
			AvalIndicacaoEspec av3b = criarAvalIndicacaoDeEspec(espec1, cefaclor, proteus);
				av3b.setValor(0);
				avsParaIncluir.add(av3b);;
			AvalIndicacaoEspec av4b = criarAvalIndicacaoDeEspec(espec1, cefixima, proteus);
				av4b.setValor(0);
				avsParaIncluir.add(av4b);
			AvalIndicacaoEspec av5b = criarAvalIndicacaoDeEspec(espec1, ampicilina, proteus);
				av5b.setValor(0);
				avsParaIncluir.add(av5b);
			AvalIndicacaoEspec av6b = criarAvalIndicacaoDeEspec(espec1, amoxacilina, proteus);
				av6b.setValor(0);
				avsParaIncluir.add(av6b);
			AvalIndicacaoEspec av7b = criarAvalIndicacaoDeEspec(espec1, tazobactam, proteus);
				av7b.setValor(0);
				avsParaIncluir.add(av7b);
			AvalIndicacaoEspec av8b = criarAvalIndicacaoDeEspec(espec1, clavulanato, proteus);
				av8b.setValor(0);
				avsParaIncluir.add(av8b);
			AvalIndicacaoEspec av9b = criarAvalIndicacaoDeEspec(espec1, nitro, proteus);
				av9b.setValor(0);
				avsParaIncluir.add(av9b);
			AvalIndicacaoEspec av10b = criarAvalIndicacaoDeEspec(espec1, cipro, proteus);
				av10b.setValor(0);
				avsParaIncluir.add(av10b);
			AvalIndicacaoEspec av11b = criarAvalIndicacaoDeEspec(espec1, fosfomicina, proteus);
				av11b.setValor(0);
				avsParaIncluir.add(av11b);
			AvalIndicacaoEspec av12b = criarAvalIndicacaoDeEspec(espec1, norfloxacina, proteus);
				av12b.setValor(0);
				avsParaIncluir.add(av12b);
			AvalIndicacaoEspec av13b  = criarAvalIndicacaoDeEspec(espec1, sulfatri, proteus);
				av13b.setValor(0);
				avsParaIncluir.add(av13b);
			
			AvalIndicacaoEspec av1c = criarAvalIndicacaoDeEspec(espec1, cefalexina, enterobacter);
				av1c.setValor(0);
				avsParaIncluir.add(av1c);				
			AvalIndicacaoEspec av2c = criarAvalIndicacaoDeEspec(espec1, axetil, enterobacter);
				av2c.setValor(0);
				avsParaIncluir.add(av2c);	
			AvalIndicacaoEspec av3c = criarAvalIndicacaoDeEspec(espec1, cefaclor, enterobacter);
				av3c.setValor(0);
				avsParaIncluir.add(av3c);	
			AvalIndicacaoEspec av4c = criarAvalIndicacaoDeEspec(espec1, cefixima, enterobacter);
				av4c.setValor(0);
				avsParaIncluir.add(av4c);	
			AvalIndicacaoEspec av5c = criarAvalIndicacaoDeEspec(espec1, ampicilina, enterobacter);
				av5c.setValor(0);
				avsParaIncluir.add(av5c);	
			AvalIndicacaoEspec av6c = criarAvalIndicacaoDeEspec(espec1, amoxacilina, enterobacter);
				av6c.setValor(0);
				avsParaIncluir.add(av6c);	
			AvalIndicacaoEspec av7c = criarAvalIndicacaoDeEspec(espec1, tazobactam, enterobacter);
				av7c.setValor(0);
				avsParaIncluir.add(av7c);	
			AvalIndicacaoEspec av8c = criarAvalIndicacaoDeEspec(espec1, clavulanato, enterobacter);
				av8c.setValor(0);
				avsParaIncluir.add(av8c);	
			AvalIndicacaoEspec av9c = criarAvalIndicacaoDeEspec(espec1, nitro, enterobacter);
				av9c.setValor(0);
				avsParaIncluir.add(av9c);	
			AvalIndicacaoEspec av10c = criarAvalIndicacaoDeEspec(espec1, cipro, enterobacter);
				av10c.setValor(0);
				avsParaIncluir.add(av10c);	
			AvalIndicacaoEspec av11c = criarAvalIndicacaoDeEspec(espec1, fosfomicina, enterobacter);
				av11c.setValor(0);
				avsParaIncluir.add(av11c);	
			AvalIndicacaoEspec av12c = criarAvalIndicacaoDeEspec(espec1, norfloxacina, enterobacter);
				av12c.setValor(0);
				avsParaIncluir.add(av12c);	
			AvalIndicacaoEspec av13c  = criarAvalIndicacaoDeEspec(espec1, sulfatri, enterobacter);
				av13c.setValor(0);
				avsParaIncluir.add(av13c);				

			AvalIndicacaoEspec av1d = criarAvalIndicacaoDeEspec(espec2, cefalexina, klebsiella);
				av1d.setValor(0);
				avsParaIncluir.add(av1d);			
			AvalIndicacaoEspec av2d = criarAvalIndicacaoDeEspec(espec2, axetil, klebsiella); 
				av2d.setValor(0);
				avsParaIncluir.add(av2d);	
			AvalIndicacaoEspec av3d = criarAvalIndicacaoDeEspec(espec2, cefaclor, klebsiella);
				av3d.setValor(0);
				avsParaIncluir.add(av3d);	
			AvalIndicacaoEspec av4d = criarAvalIndicacaoDeEspec(espec2, cefixima, klebsiella);
				av4d.setValor(0);
				avsParaIncluir.add(av4d);	
			AvalIndicacaoEspec av5d = criarAvalIndicacaoDeEspec(espec2, ampicilina, klebsiella);
				av5d.setValor(0);
				avsParaIncluir.add(av5d);	
			AvalIndicacaoEspec av6d = criarAvalIndicacaoDeEspec(espec2, amoxacilina, klebsiella);
				av6d.setValor(0);
				avsParaIncluir.add(av6d);	
			AvalIndicacaoEspec av7d = criarAvalIndicacaoDeEspec(espec2, tazobactam, klebsiella);
				av7d.setValor(0);
				avsParaIncluir.add(av7d);	
			AvalIndicacaoEspec av8d = criarAvalIndicacaoDeEspec(espec2, clavulanato, klebsiella);
				av8d.setValor(0);
				avsParaIncluir.add(av8d);	
			AvalIndicacaoEspec av9d = criarAvalIndicacaoDeEspec(espec2, nitro, klebsiella);
				av9d.setValor(0);
				avsParaIncluir.add(av9d);	
			AvalIndicacaoEspec av10d = criarAvalIndicacaoDeEspec(espec2, cipro, klebsiella);
				av10d.setValor(0);
				avsParaIncluir.add(av10d);	
			AvalIndicacaoEspec av11d = criarAvalIndicacaoDeEspec(espec2, fosfomicina, klebsiella);
				av11d.setValor(0);
				avsParaIncluir.add(av11d);	
			AvalIndicacaoEspec av12d = criarAvalIndicacaoDeEspec(espec2, norfloxacina, klebsiella);
				av12d.setValor(0);
				avsParaIncluir.add(av12d);	
			AvalIndicacaoEspec av13d  = criarAvalIndicacaoDeEspec(espec2, sulfatri, klebsiella);
				av13d.setValor(0);
				avsParaIncluir.add(av13d);	
			
			AvalIndicacaoEspec av1e = criarAvalIndicacaoDeEspec(espec2, cefalexina, proteus);
				av1e.setValor(0);
				avsParaIncluir.add(av1e);
			AvalIndicacaoEspec av2e = criarAvalIndicacaoDeEspec(espec2, axetil, proteus);
				av2e.setValor(0);
				avsParaIncluir.add(av2e); 
			AvalIndicacaoEspec av3e = criarAvalIndicacaoDeEspec(espec2, cefaclor, proteus);
				av3e.setValor(0);
				avsParaIncluir.add(av3e);
			AvalIndicacaoEspec av4e = criarAvalIndicacaoDeEspec(espec2, cefixima, proteus);
				av4e.setValor(0);
				avsParaIncluir.add(av4e);
			AvalIndicacaoEspec av5e = criarAvalIndicacaoDeEspec(espec2, ampicilina, proteus);
				av5e.setValor(0);
				avsParaIncluir.add(av5e);
			AvalIndicacaoEspec av6e = criarAvalIndicacaoDeEspec(espec2, amoxacilina, proteus);
				av6e.setValor(0);
				avsParaIncluir.add(av6e);
			AvalIndicacaoEspec av7e = criarAvalIndicacaoDeEspec(espec2, tazobactam, proteus);
				av7e.setValor(0);
				avsParaIncluir.add(av7e);
			AvalIndicacaoEspec av8e = criarAvalIndicacaoDeEspec(espec2, clavulanato, proteus);
				av8e.setValor(0);
				avsParaIncluir.add(av8e);
			AvalIndicacaoEspec av9e = criarAvalIndicacaoDeEspec(espec2, nitro, proteus);
				av9e.setValor(0);
				avsParaIncluir.add(av9e);
			AvalIndicacaoEspec av10e = criarAvalIndicacaoDeEspec(espec2, cipro, proteus);
				av10e.setValor(0);
				avsParaIncluir.add(av10e);
			AvalIndicacaoEspec av11e = criarAvalIndicacaoDeEspec(espec2, fosfomicina, proteus);
				av11e.setValor(0);
				avsParaIncluir.add(av11e);
			AvalIndicacaoEspec av12e = criarAvalIndicacaoDeEspec(espec2, norfloxacina, proteus);
				av12e.setValor(0);
				avsParaIncluir.add(av12e);
			AvalIndicacaoEspec av13e  = criarAvalIndicacaoDeEspec(espec2, sulfatri, proteus);
				av13e.setValor(0);
				avsParaIncluir.add(av13e);
			
			AvalIndicacaoEspec av1f = criarAvalIndicacaoDeEspec(espec2, cefalexina, enterobacter);
				av1f.setValor(0);
				avsParaIncluir.add(av1f);
			AvalIndicacaoEspec av2f = criarAvalIndicacaoDeEspec(espec2, axetil, enterobacter);
				av2f.setValor(0);
				avsParaIncluir.add(av2f); 
			AvalIndicacaoEspec av3f = criarAvalIndicacaoDeEspec(espec2, cefaclor, enterobacter);
				av3f.setValor(0);
				avsParaIncluir.add(av3f);
			AvalIndicacaoEspec av4f = criarAvalIndicacaoDeEspec(espec2, cefixima, enterobacter);
				av4f.setValor(0);
				avsParaIncluir.add(av4f);
			AvalIndicacaoEspec av5f = criarAvalIndicacaoDeEspec(espec2, ampicilina, enterobacter);
				av5f.setValor(0);
				avsParaIncluir.add(av5f);
			AvalIndicacaoEspec av6f = criarAvalIndicacaoDeEspec(espec2, amoxacilina, enterobacter);
				av6f.setValor(0);
				avsParaIncluir.add(av6f);
			AvalIndicacaoEspec av7f = criarAvalIndicacaoDeEspec(espec2, tazobactam, enterobacter);
				av7f.setValor(0);
				avsParaIncluir.add(av7f);
			AvalIndicacaoEspec av8f = criarAvalIndicacaoDeEspec(espec2, clavulanato, enterobacter);
				av8f.setValor(0);
				avsParaIncluir.add(av8f);
			AvalIndicacaoEspec av9f = criarAvalIndicacaoDeEspec(espec2, nitro, enterobacter);
				av9f.setValor(0);
				avsParaIncluir.add(av9f);
			AvalIndicacaoEspec av10f = criarAvalIndicacaoDeEspec(espec2, cipro, enterobacter);
				av10f.setValor(0);
				avsParaIncluir.add(av10f);
			AvalIndicacaoEspec av11f = criarAvalIndicacaoDeEspec(espec2, fosfomicina, enterobacter);
				av11f.setValor(0);
				avsParaIncluir.add(av11f);
			AvalIndicacaoEspec av12f = criarAvalIndicacaoDeEspec(espec2, norfloxacina, enterobacter);
				av12f.setValor(0);
				avsParaIncluir.add(av12f);
			AvalIndicacaoEspec av13f  = criarAvalIndicacaoDeEspec(espec2, sulfatri, enterobacter);	
				av13f.setValor(0);
				avsParaIncluir.add(av13f);
			
			// Incluir todas as avaliações	
			for(AvalIndicacaoEspec avalIndicacaoEspec : avsParaIncluir) {
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
