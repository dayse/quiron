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
 * na ordem correta. Ex: incluiPaciente() vem antes de incluiAtendimento(), portanto no método executar()
 * 
 * Terminado de executar todas as etapas é preciso retornar true.
 * Se houver algum problema(exceção) na execução de uma das etapas, essa exceção deve ser lancada.
 * 
 * Essa Carga:
 * Responsavel por fazer a carga das avaliações dadas pelos especilistas
 * para cada indicação cadastrada. Estamos usandos os novos parametros e atendimentos
 * sugeridos pelo Pedro Peloso e a Dayse Arruda.
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
	
	/**
	 * 
	 * Método herdado de CargaBase e que retona uma lista de cargas que esta
	 * carga depende para ser executada de maneira completa.
	 * 
	 * @return lista de dependencias.
	 * 
	 * @author bruno.oliveira
	 * 
	 */	
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
			
			Parametro gravidez = parametroService.recuperaParametroPorCodigo("P012");
			Parametro polaciuria = parametroService.recuperaParametroPorCodigo("P013");
			Parametro desconforto = parametroService.recuperaParametroPorCodigo("P014");
			Parametro infeccao = parametroService.recuperaParametroPorCodigo("P015");
			Parametro usoDeAntibiotico = parametroService.recuperaParametroPorCodigo("P016");			
			
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
				
			AvalIndicacaoEspec av1g = criarAvalIndicacaoDeEspec(espec1, cefalexina, gravidez);
				av1g.setValor(0);
				avsParaIncluir.add(av1g);				
			AvalIndicacaoEspec av2g = criarAvalIndicacaoDeEspec(espec1, axetil, gravidez);
				av2g.setValor(0);
				avsParaIncluir.add(av2g);	
			AvalIndicacaoEspec av3g = criarAvalIndicacaoDeEspec(espec1, cefaclor, gravidez);
				av3g.setValor(0);
				avsParaIncluir.add(av3g);	
			AvalIndicacaoEspec av4g = criarAvalIndicacaoDeEspec(espec1, cefixima, gravidez);
				av4g.setValor(0);
				avsParaIncluir.add(av4g);	
			AvalIndicacaoEspec av5g = criarAvalIndicacaoDeEspec(espec1, ampicilina, gravidez);
				av5g.setValor(0);
				avsParaIncluir.add(av5g);	
			AvalIndicacaoEspec av6g = criarAvalIndicacaoDeEspec(espec1, amoxacilina, gravidez);
				av6g.setValor(0);
				avsParaIncluir.add(av6g);	
			AvalIndicacaoEspec av7g = criarAvalIndicacaoDeEspec(espec1, tazobactam, gravidez);
				av7g.setValor(0);
				avsParaIncluir.add(av7g);	
			AvalIndicacaoEspec av8g = criarAvalIndicacaoDeEspec(espec1, clavulanato, gravidez);
				av8g.setValor(0);
				avsParaIncluir.add(av8g);	
			AvalIndicacaoEspec av9g = criarAvalIndicacaoDeEspec(espec1, nitro, gravidez);
				av9g.setValor(0);
				avsParaIncluir.add(av9g);	
			AvalIndicacaoEspec av10g = criarAvalIndicacaoDeEspec(espec1, cipro, gravidez);
				av10g.setValor(0);
				avsParaIncluir.add(av10g);	
			AvalIndicacaoEspec av11g = criarAvalIndicacaoDeEspec(espec1, fosfomicina, gravidez);
				av11g.setValor(0);
				avsParaIncluir.add(av11g);	
			AvalIndicacaoEspec av12g = criarAvalIndicacaoDeEspec(espec1, norfloxacina, gravidez);
				av12g.setValor(0);
				avsParaIncluir.add(av12g);	
			AvalIndicacaoEspec av13g  = criarAvalIndicacaoDeEspec(espec1, sulfatri, gravidez);
				av13g.setValor(0);
				avsParaIncluir.add(av13g);
				
			AvalIndicacaoEspec av1h = criarAvalIndicacaoDeEspec(espec1, cefalexina, polaciuria);
				av1h.setValor(0);
				avsParaIncluir.add(av1h);				
			AvalIndicacaoEspec av2h = criarAvalIndicacaoDeEspec(espec1, axetil, polaciuria);
				av2h.setValor(0);
				avsParaIncluir.add(av2h);	
			AvalIndicacaoEspec av3h = criarAvalIndicacaoDeEspec(espec1, cefaclor, polaciuria);
				av3h.setValor(0);
				avsParaIncluir.add(av3h);	
			AvalIndicacaoEspec av4h = criarAvalIndicacaoDeEspec(espec1, cefixima, polaciuria);
				av4h.setValor(0);
				avsParaIncluir.add(av4h);	
			AvalIndicacaoEspec av5h = criarAvalIndicacaoDeEspec(espec1, ampicilina, polaciuria);
				av5h.setValor(0);
				avsParaIncluir.add(av5h);	
			AvalIndicacaoEspec av6h = criarAvalIndicacaoDeEspec(espec1, amoxacilina, polaciuria);
				av6h.setValor(0);
				avsParaIncluir.add(av6h);	
			AvalIndicacaoEspec av7h = criarAvalIndicacaoDeEspec(espec1, tazobactam, polaciuria);
				av7h.setValor(0);
				avsParaIncluir.add(av7h);	
			AvalIndicacaoEspec av8h = criarAvalIndicacaoDeEspec(espec1, clavulanato, polaciuria);
				av8h.setValor(0);
				avsParaIncluir.add(av8h);	
			AvalIndicacaoEspec av9h = criarAvalIndicacaoDeEspec(espec1, nitro, polaciuria);
				av9h.setValor(0);
				avsParaIncluir.add(av9h);	
			AvalIndicacaoEspec av10h = criarAvalIndicacaoDeEspec(espec1, cipro, polaciuria);
				av10h.setValor(0);
				avsParaIncluir.add(av10h);	
			AvalIndicacaoEspec av11h = criarAvalIndicacaoDeEspec(espec1, fosfomicina, polaciuria);
				av11h.setValor(0);
				avsParaIncluir.add(av11h);	
			AvalIndicacaoEspec av12h = criarAvalIndicacaoDeEspec(espec1, norfloxacina, polaciuria);
				av12h.setValor(0);
				avsParaIncluir.add(av12h);	
			AvalIndicacaoEspec av13h  = criarAvalIndicacaoDeEspec(espec1, sulfatri, polaciuria);
				av13h.setValor(0);
				avsParaIncluir.add(av13h);
				
			AvalIndicacaoEspec av1i = criarAvalIndicacaoDeEspec(espec1, cefalexina, desconforto);
				av1i.setValor(0);
				avsParaIncluir.add(av1i);				
			AvalIndicacaoEspec av2i = criarAvalIndicacaoDeEspec(espec1, axetil, desconforto);
				av2i.setValor(0);
				avsParaIncluir.add(av2i);	
			AvalIndicacaoEspec av3i = criarAvalIndicacaoDeEspec(espec1, cefaclor, desconforto);
				av3i.setValor(0);
				avsParaIncluir.add(av3i);	
			AvalIndicacaoEspec av4i = criarAvalIndicacaoDeEspec(espec1, cefixima, desconforto);
				av4i.setValor(0);
				avsParaIncluir.add(av4i);	
			AvalIndicacaoEspec av5i = criarAvalIndicacaoDeEspec(espec1, ampicilina, desconforto);
				av5i.setValor(0);
				avsParaIncluir.add(av5i);	
			AvalIndicacaoEspec av6i = criarAvalIndicacaoDeEspec(espec1, amoxacilina, desconforto);
				av6i.setValor(0);
				avsParaIncluir.add(av6i);	
			AvalIndicacaoEspec av7i = criarAvalIndicacaoDeEspec(espec1, tazobactam, desconforto);
				av7i.setValor(0);
				avsParaIncluir.add(av7i);	
			AvalIndicacaoEspec av8i = criarAvalIndicacaoDeEspec(espec1, clavulanato, desconforto);
				av8i.setValor(0);
				avsParaIncluir.add(av8i);	
			AvalIndicacaoEspec av9i = criarAvalIndicacaoDeEspec(espec1, nitro, desconforto);
				av9i.setValor(0);
				avsParaIncluir.add(av9i);	
			AvalIndicacaoEspec av10i = criarAvalIndicacaoDeEspec(espec1, cipro, desconforto);
				av10i.setValor(0);
				avsParaIncluir.add(av10i);	
			AvalIndicacaoEspec av11i = criarAvalIndicacaoDeEspec(espec1, fosfomicina, desconforto);
				av11i.setValor(0);
				avsParaIncluir.add(av11i);	
			AvalIndicacaoEspec av12i = criarAvalIndicacaoDeEspec(espec1, norfloxacina, desconforto);
				av12i.setValor(0);
				avsParaIncluir.add(av12i);	
			AvalIndicacaoEspec av13i  = criarAvalIndicacaoDeEspec(espec1, sulfatri, desconforto);
				av13i.setValor(0);
				avsParaIncluir.add(av13i);
				
			AvalIndicacaoEspec av1j = criarAvalIndicacaoDeEspec(espec1, cefalexina, infeccao);
				av1j.setValor(0);
				avsParaIncluir.add(av1j);				
			AvalIndicacaoEspec av2j = criarAvalIndicacaoDeEspec(espec1, axetil, infeccao);
				av2j.setValor(0);
				avsParaIncluir.add(av2j);	
			AvalIndicacaoEspec av3j = criarAvalIndicacaoDeEspec(espec1, cefaclor, infeccao);
				av3j.setValor(0);
				avsParaIncluir.add(av3j);	
			AvalIndicacaoEspec av4j = criarAvalIndicacaoDeEspec(espec1, cefixima, infeccao);
				av4j.setValor(0);
				avsParaIncluir.add(av4j);	
			AvalIndicacaoEspec av5j = criarAvalIndicacaoDeEspec(espec1, ampicilina, infeccao);
				av5j.setValor(0);
				avsParaIncluir.add(av5j);	
			AvalIndicacaoEspec av6j = criarAvalIndicacaoDeEspec(espec1, amoxacilina, infeccao);
				av6j.setValor(0);
				avsParaIncluir.add(av6j);	
			AvalIndicacaoEspec av7j = criarAvalIndicacaoDeEspec(espec1, tazobactam, infeccao);
				av7j.setValor(0);
				avsParaIncluir.add(av7j);	
			AvalIndicacaoEspec av8j = criarAvalIndicacaoDeEspec(espec1, clavulanato, infeccao);
				av8j.setValor(0);
				avsParaIncluir.add(av8j);	
			AvalIndicacaoEspec av9j = criarAvalIndicacaoDeEspec(espec1, nitro, infeccao);
				av9j.setValor(0);
				avsParaIncluir.add(av9j);	
			AvalIndicacaoEspec av10j = criarAvalIndicacaoDeEspec(espec1, cipro, infeccao);
				av10j.setValor(0);
				avsParaIncluir.add(av10j);	
			AvalIndicacaoEspec av11j = criarAvalIndicacaoDeEspec(espec1, fosfomicina, infeccao);
				av11j.setValor(0);
				avsParaIncluir.add(av11j);	
			AvalIndicacaoEspec av12j = criarAvalIndicacaoDeEspec(espec1, norfloxacina, infeccao);
				av12j.setValor(0);
				avsParaIncluir.add(av12j);	
			AvalIndicacaoEspec av13j  = criarAvalIndicacaoDeEspec(espec1, sulfatri, infeccao);
				av13j.setValor(0);
				avsParaIncluir.add(av13j);
				
			AvalIndicacaoEspec av1k = criarAvalIndicacaoDeEspec(espec1, cefalexina, usoDeAntibiotico);
				av1k.setValor(0);
				avsParaIncluir.add(av1k);				
			AvalIndicacaoEspec av2k = criarAvalIndicacaoDeEspec(espec1, axetil, usoDeAntibiotico);
				av2k.setValor(0);
				avsParaIncluir.add(av2k);	
			AvalIndicacaoEspec av3k = criarAvalIndicacaoDeEspec(espec1, cefaclor, usoDeAntibiotico);
				av3k.setValor(0);
				avsParaIncluir.add(av3k);	
			AvalIndicacaoEspec av4k = criarAvalIndicacaoDeEspec(espec1, cefixima, usoDeAntibiotico);
				av4k.setValor(0);
				avsParaIncluir.add(av4k);	
			AvalIndicacaoEspec av5k = criarAvalIndicacaoDeEspec(espec1, ampicilina, usoDeAntibiotico);
				av5k.setValor(0);
				avsParaIncluir.add(av5k);	
			AvalIndicacaoEspec av6k = criarAvalIndicacaoDeEspec(espec1, amoxacilina, usoDeAntibiotico);
				av6k.setValor(0);
				avsParaIncluir.add(av6k);	
			AvalIndicacaoEspec av7k = criarAvalIndicacaoDeEspec(espec1, tazobactam, usoDeAntibiotico);
				av7k.setValor(0);
				avsParaIncluir.add(av7k);	
			AvalIndicacaoEspec av8k = criarAvalIndicacaoDeEspec(espec1, clavulanato, usoDeAntibiotico);
				av8k.setValor(0);
				avsParaIncluir.add(av8k);	
			AvalIndicacaoEspec av9k = criarAvalIndicacaoDeEspec(espec1, nitro, usoDeAntibiotico);
				av9k.setValor(0);
				avsParaIncluir.add(av9k);	
			AvalIndicacaoEspec av10k = criarAvalIndicacaoDeEspec(espec1, cipro, usoDeAntibiotico);
				av10k.setValor(0);
				avsParaIncluir.add(av10k);	
			AvalIndicacaoEspec av11k = criarAvalIndicacaoDeEspec(espec1, fosfomicina, usoDeAntibiotico);
				av11k.setValor(0);
				avsParaIncluir.add(av11k);	
			AvalIndicacaoEspec av12k = criarAvalIndicacaoDeEspec(espec1, norfloxacina, usoDeAntibiotico);
				av12k.setValor(0);
				avsParaIncluir.add(av12k);	
			AvalIndicacaoEspec av13k  = criarAvalIndicacaoDeEspec(espec1, sulfatri, usoDeAntibiotico);
				av13k.setValor(0);
				avsParaIncluir.add(av13k);
				
			AvalIndicacaoEspec av1l = criarAvalIndicacaoDeEspec(espec2, cefalexina, gravidez);
				av1l.setValor(0);
				avsParaIncluir.add(av1l);				
			AvalIndicacaoEspec av2l = criarAvalIndicacaoDeEspec(espec2, axetil, gravidez);
				av2l.setValor(0);
				avsParaIncluir.add(av2l);	
			AvalIndicacaoEspec av3l = criarAvalIndicacaoDeEspec(espec2, cefaclor, gravidez);
				av3l.setValor(0);
				avsParaIncluir.add(av3l);	
			AvalIndicacaoEspec av4l = criarAvalIndicacaoDeEspec(espec2, cefixima, gravidez);
				av4l.setValor(0);
				avsParaIncluir.add(av4l);	
			AvalIndicacaoEspec av5l = criarAvalIndicacaoDeEspec(espec2, ampicilina, gravidez);
				av5l.setValor(0);
				avsParaIncluir.add(av5l);	
			AvalIndicacaoEspec av6l = criarAvalIndicacaoDeEspec(espec2, amoxacilina, gravidez);
				av6l.setValor(0);
				avsParaIncluir.add(av6l);	
			AvalIndicacaoEspec av7l = criarAvalIndicacaoDeEspec(espec2, tazobactam, gravidez);
				av7l.setValor(0);
				avsParaIncluir.add(av7l);	
			AvalIndicacaoEspec av8l = criarAvalIndicacaoDeEspec(espec2, clavulanato, gravidez);
				av8l.setValor(0);
				avsParaIncluir.add(av8l);	
			AvalIndicacaoEspec av9l = criarAvalIndicacaoDeEspec(espec2, nitro, gravidez);
				av9l.setValor(0);
				avsParaIncluir.add(av9l);	
			AvalIndicacaoEspec av10l = criarAvalIndicacaoDeEspec(espec2, cipro, gravidez);
				av10l.setValor(0);
				avsParaIncluir.add(av10l);	
			AvalIndicacaoEspec av11l = criarAvalIndicacaoDeEspec(espec2, fosfomicina, gravidez);
				av11l.setValor(0);
				avsParaIncluir.add(av11l);	
			AvalIndicacaoEspec av12l = criarAvalIndicacaoDeEspec(espec2, norfloxacina, gravidez);
				av12l.setValor(0);
				avsParaIncluir.add(av12l);	
			AvalIndicacaoEspec av13l  = criarAvalIndicacaoDeEspec(espec2, sulfatri, gravidez);
				av13l.setValor(0);
				avsParaIncluir.add(av13l);
				
			AvalIndicacaoEspec av1m = criarAvalIndicacaoDeEspec(espec2, cefalexina, polaciuria);
				av1m.setValor(0);
				avsParaIncluir.add(av1m);				
			AvalIndicacaoEspec av2m = criarAvalIndicacaoDeEspec(espec2, axetil, polaciuria);
				av2m.setValor(0);
				avsParaIncluir.add(av2m);	
			AvalIndicacaoEspec av3m = criarAvalIndicacaoDeEspec(espec2, cefaclor, polaciuria);
				av3m.setValor(0);
				avsParaIncluir.add(av3m);	
			AvalIndicacaoEspec av4m = criarAvalIndicacaoDeEspec(espec2, cefixima, polaciuria);
				av4m.setValor(0);
				avsParaIncluir.add(av4m);	
			AvalIndicacaoEspec av5m = criarAvalIndicacaoDeEspec(espec2, ampicilina, polaciuria);
				av5m.setValor(0);
				avsParaIncluir.add(av5m);	
			AvalIndicacaoEspec av6m = criarAvalIndicacaoDeEspec(espec2, amoxacilina, polaciuria);
				av6m.setValor(0);
				avsParaIncluir.add(av6m);	
			AvalIndicacaoEspec av7m = criarAvalIndicacaoDeEspec(espec2, tazobactam, polaciuria);
				av7m.setValor(0);
				avsParaIncluir.add(av7m);	
			AvalIndicacaoEspec av8m = criarAvalIndicacaoDeEspec(espec2, clavulanato, polaciuria);
				av8m.setValor(0);
				avsParaIncluir.add(av8m);	
			AvalIndicacaoEspec av9m = criarAvalIndicacaoDeEspec(espec2, nitro, polaciuria);
				av9m.setValor(0);
				avsParaIncluir.add(av9m);	
			AvalIndicacaoEspec av10m = criarAvalIndicacaoDeEspec(espec2, cipro, polaciuria);
				av10m.setValor(0);
				avsParaIncluir.add(av10m);	
			AvalIndicacaoEspec av11m = criarAvalIndicacaoDeEspec(espec2, fosfomicina, polaciuria);
				av11m.setValor(0);
				avsParaIncluir.add(av11m);	
			AvalIndicacaoEspec av12m = criarAvalIndicacaoDeEspec(espec2, norfloxacina, polaciuria);
				av12m.setValor(0);
				avsParaIncluir.add(av12m);	
			AvalIndicacaoEspec av13m  = criarAvalIndicacaoDeEspec(espec2, sulfatri, polaciuria);
				av13m.setValor(0);
				avsParaIncluir.add(av13m);
				
			AvalIndicacaoEspec av1o = criarAvalIndicacaoDeEspec(espec2, cefalexina, desconforto);
				av1o.setValor(0);
				avsParaIncluir.add(av1o);				
			AvalIndicacaoEspec av2o = criarAvalIndicacaoDeEspec(espec2, axetil, desconforto);
				av2o.setValor(0);
				avsParaIncluir.add(av2o);	
			AvalIndicacaoEspec av3o = criarAvalIndicacaoDeEspec(espec2, cefaclor, desconforto);
				av3o.setValor(0);
				avsParaIncluir.add(av3o);	
			AvalIndicacaoEspec av4o = criarAvalIndicacaoDeEspec(espec2, cefixima, desconforto);
				av4o.setValor(0);
				avsParaIncluir.add(av4o);	
			AvalIndicacaoEspec av5o = criarAvalIndicacaoDeEspec(espec2, ampicilina, desconforto);
				av5o.setValor(0);
				avsParaIncluir.add(av5o);	
			AvalIndicacaoEspec av6o = criarAvalIndicacaoDeEspec(espec2, amoxacilina, desconforto);
				av6o.setValor(0);
				avsParaIncluir.add(av6o);	
			AvalIndicacaoEspec av7o = criarAvalIndicacaoDeEspec(espec2, tazobactam, desconforto);
				av7o.setValor(0);
				avsParaIncluir.add(av7o);	
			AvalIndicacaoEspec av8o = criarAvalIndicacaoDeEspec(espec2, clavulanato, desconforto);
				av8o.setValor(0);
				avsParaIncluir.add(av8o);	
			AvalIndicacaoEspec av9o = criarAvalIndicacaoDeEspec(espec2, nitro, desconforto);
				av9o.setValor(0);
				avsParaIncluir.add(av9o);	
			AvalIndicacaoEspec av10o = criarAvalIndicacaoDeEspec(espec2, cipro, desconforto);
				av10o.setValor(0);
				avsParaIncluir.add(av10o);	
			AvalIndicacaoEspec av11o = criarAvalIndicacaoDeEspec(espec2, fosfomicina, desconforto);
				av11o.setValor(0);
				avsParaIncluir.add(av11o);	
			AvalIndicacaoEspec av12o = criarAvalIndicacaoDeEspec(espec2, norfloxacina, desconforto);
				av12o.setValor(0);
				avsParaIncluir.add(av12o);	
			AvalIndicacaoEspec av13o  = criarAvalIndicacaoDeEspec(espec2, sulfatri, desconforto);
				av13o.setValor(0);
				avsParaIncluir.add(av13o);
				
			AvalIndicacaoEspec av1p = criarAvalIndicacaoDeEspec(espec2, cefalexina, infeccao);
				av1p.setValor(0);
				avsParaIncluir.add(av1p);				
			AvalIndicacaoEspec av2p = criarAvalIndicacaoDeEspec(espec2, axetil, infeccao);
				av2p.setValor(0);
				avsParaIncluir.add(av2p);	
			AvalIndicacaoEspec av3p = criarAvalIndicacaoDeEspec(espec2, cefaclor, infeccao);
				av3p.setValor(0);
				avsParaIncluir.add(av3p);	
			AvalIndicacaoEspec av4p = criarAvalIndicacaoDeEspec(espec2, cefixima, infeccao);
				av4p.setValor(0);
				avsParaIncluir.add(av4p);	
			AvalIndicacaoEspec av5p = criarAvalIndicacaoDeEspec(espec2, ampicilina, infeccao);
				av5p.setValor(0);
				avsParaIncluir.add(av5p);	
			AvalIndicacaoEspec av6p = criarAvalIndicacaoDeEspec(espec2, amoxacilina, infeccao);
				av6p.setValor(0);
				avsParaIncluir.add(av6p);	
			AvalIndicacaoEspec av7p = criarAvalIndicacaoDeEspec(espec2, tazobactam, infeccao);
				av7p.setValor(0);
				avsParaIncluir.add(av7p);	
			AvalIndicacaoEspec av8p = criarAvalIndicacaoDeEspec(espec2, clavulanato, infeccao);
				av8p.setValor(0);
				avsParaIncluir.add(av8p);	
			AvalIndicacaoEspec av9p = criarAvalIndicacaoDeEspec(espec2, nitro, infeccao);
				av9p.setValor(0);
				avsParaIncluir.add(av9p);	
			AvalIndicacaoEspec av10p = criarAvalIndicacaoDeEspec(espec2, cipro, infeccao);
				av10p.setValor(0);
				avsParaIncluir.add(av10p);	
			AvalIndicacaoEspec av11p = criarAvalIndicacaoDeEspec(espec2, fosfomicina, infeccao);
				av11p.setValor(0);
				avsParaIncluir.add(av11p);	
			AvalIndicacaoEspec av12p = criarAvalIndicacaoDeEspec(espec2, norfloxacina, infeccao);
				av12p.setValor(0);
				avsParaIncluir.add(av12p);	
			AvalIndicacaoEspec av13p  = criarAvalIndicacaoDeEspec(espec2, sulfatri, infeccao);
				av13p.setValor(0);
				avsParaIncluir.add(av13p);
				
			AvalIndicacaoEspec av1q = criarAvalIndicacaoDeEspec(espec2, cefalexina, usoDeAntibiotico);
				av1q.setValor(0);
				avsParaIncluir.add(av1q);				
			AvalIndicacaoEspec av2q = criarAvalIndicacaoDeEspec(espec2, axetil, usoDeAntibiotico);
				av2q.setValor(0);
				avsParaIncluir.add(av2q);	
			AvalIndicacaoEspec av3q = criarAvalIndicacaoDeEspec(espec2, cefaclor, usoDeAntibiotico);
				av3q.setValor(0);
				avsParaIncluir.add(av3q);	
			AvalIndicacaoEspec av4q = criarAvalIndicacaoDeEspec(espec2, cefixima, usoDeAntibiotico);
				av4q.setValor(0);
				avsParaIncluir.add(av4q);	
			AvalIndicacaoEspec av5q = criarAvalIndicacaoDeEspec(espec2, ampicilina, usoDeAntibiotico);
				av5q.setValor(0);
				avsParaIncluir.add(av5q);	
			AvalIndicacaoEspec av6q = criarAvalIndicacaoDeEspec(espec2, amoxacilina, usoDeAntibiotico);
				av6q.setValor(0);
				avsParaIncluir.add(av6q);	
			AvalIndicacaoEspec av7q = criarAvalIndicacaoDeEspec(espec2, tazobactam, usoDeAntibiotico);
				av7q.setValor(0);
				avsParaIncluir.add(av7q);	
			AvalIndicacaoEspec av8q = criarAvalIndicacaoDeEspec(espec2, clavulanato, usoDeAntibiotico);
				av8q.setValor(0);
				avsParaIncluir.add(av8q);	
			AvalIndicacaoEspec av9q = criarAvalIndicacaoDeEspec(espec2, nitro, usoDeAntibiotico);
				av9q.setValor(0);
				avsParaIncluir.add(av9q);	
			AvalIndicacaoEspec av10q = criarAvalIndicacaoDeEspec(espec2, cipro, usoDeAntibiotico);
				av10q.setValor(0);
				avsParaIncluir.add(av10q);	
			AvalIndicacaoEspec av11q = criarAvalIndicacaoDeEspec(espec2, fosfomicina, usoDeAntibiotico);
				av11q.setValor(0);
				avsParaIncluir.add(av11q);	
			AvalIndicacaoEspec av12q = criarAvalIndicacaoDeEspec(espec2, norfloxacina, usoDeAntibiotico);
				av12q.setValor(0);
				avsParaIncluir.add(av12q);	
			AvalIndicacaoEspec av13q  = criarAvalIndicacaoDeEspec(espec2, sulfatri, usoDeAntibiotico);
				av13q.setValor(0);
				avsParaIncluir.add(av13q);					
			
			// Incluir todas as avaliações	
			for(AvalIndicacaoEspec avalIndicacaoEspec : avsParaIncluir) {
				avalIndicacaoEspecService.altera(avalIndicacaoEspec);
			}
			
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException(e);
		}
	}
	
	/**
	 * 
	 * Método responsável por recuperar uma Avaliação
	 * de um especialista para um parametro e para uma
	 * determinada indicação.
	 * 
	 * @param especialista
	 * @param indicacao
	 * @param parametro
	 * @return
	 * @throws ObjetoNaoEncontradoException
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
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
