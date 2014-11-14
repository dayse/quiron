package carga;

import java.util.ArrayList;
import java.util.List;

import modelo.Indicacao;
import service.IndicacaoAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;

/**
 * 
 * Sobre a Carga:
 * � uma Carga do sistema, portanto deve herdar de CargaBase e
 * implementar o m�todo executar().
 * Nesse m�todo "executar" � que � chamado pelos outros m�todos que s�o 
 * as etapas dessa carga.
 * Portanto se � necessario rodar um m�todo depois do outro, eles devem ser chamados
 * na ordem correta. Ex:
 * incluiHP() vem antes de inicializaHP(), portanto no m�todo executar() eles devem ser chamados nessa ordem.
 * 
 * Terminado de executar todas as etapas � preciso retornar true.
 * Se houver algum problema(exce��o) na execu��o de uma das etapas, essa exce��o deve ser lancada.
 * 
 * Essa Carga:
 * Responsavel por fazer a carga das indica��es contidas nos
 * arquivos de modelagens e dados iniciais do sistema
 * 
 * @author bruno.oliveira
 *
 */
public class CargaIndicacaoIntermediaria extends CargaBase {
	
	// Services
	private static IndicacaoAppService indicacaoService;

	public CargaIndicacaoIntermediaria(){
		try{
			indicacaoService = FabricaDeAppService.getAppService(IndicacaoAppService.class);
		} catch (Exception e){
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
		this.incluirIndicacoesIntermediarias();
		return true;
	}

	/**
	 * Metodo respons�vel por preparar e inserir os valores padr�es de
	 * indica��es apresentadas pelo Pedro Peloso no banco de dados.
	 * 
	 * @author bruno.oliveira
	 * @throws AplicacaoException
	 */
	public void incluirIndicacoesIntermediarias() throws AplicacaoException{
		Indicacao cefalexina = new Indicacao();
		cefalexina.setNome("Cefalexina");
		cefalexina.setCodIndicacao("cefalexina");
		cefalexina.setDosagem("12hrs x 3 dias");
		cefalexina.setApresentacao("500 mg");
		
		Indicacao axetil =  new Indicacao();
		axetil.setNome("Cefuroxima Axetil");
		axetil.setCodIndicacao("cefuroxima");
		axetil.setDosagem("12hrs x 3 dias");
		axetil.setApresentacao("500 mg");
		
		Indicacao cefaclor = new Indicacao();
		cefaclor.setNome("Cefaclor");
		cefaclor.setCodIndicacao("cefaclor");
		cefaclor.setDosagem("12hrs x 3 dias");
		cefaclor.setApresentacao("500 mg");
		
		Indicacao cefixima = new Indicacao();
		cefixima.setNome("Cefixima");
		cefixima.setCodIndicacao("cefixima");
		cefixima.setDosagem("12hrs x 3 dias");
		cefixima.setApresentacao("500 mg");
		
		Indicacao ampicilina = new Indicacao();
		ampicilina.setNome("Ampicilina");
		ampicilina.setCodIndicacao("ampicilina");
		ampicilina.setDosagem("12hrs x 3 dias");
		ampicilina.setApresentacao("500 mg");
		
		Indicacao amoxacilina = new Indicacao();
		amoxacilina.setNome("Amoxacilina");
		amoxacilina.setCodIndicacao("amoxacilina");
		amoxacilina.setDosagem("12hrs x 3 dias");
		amoxacilina.setApresentacao("500 mg");
		
		Indicacao tazobactam = new Indicacao();
		tazobactam.setNome("Ampicilina - Tazobactam");
		tazobactam.setCodIndicacao("amp-tazo");
		tazobactam.setDosagem("12hrs x 3 dias");
		tazobactam.setApresentacao("500 mg");
		
		Indicacao clavulanato = new Indicacao();
		clavulanato.setNome("Amoxacilina - Clavulanato");
		clavulanato.setCodIndicacao("amox-clavun");
		clavulanato.setDosagem("12hrs x 3 dias");
		clavulanato.setApresentacao("500 mg");
		
		Indicacao nitro = new Indicacao();
		nitro.setNome("Nitrofurantoina");
		nitro.setCodIndicacao("nitro");
		nitro.setDosagem("12hrs x 3 dias");
		nitro.setApresentacao("500 mg");
		
		Indicacao cipro = new Indicacao();
		cipro.setNome("Ciprofloxacina");
		cipro.setCodIndicacao("ciprofl");
		cipro.setDosagem("12hrs x 3 dias");
		cipro.setApresentacao("500 mg");
		
		Indicacao fosfomicina = new Indicacao();
		fosfomicina.setNome("Fosfomicina");
		fosfomicina.setCodIndicacao("fosfomicina");
		fosfomicina.setDosagem("12hrs x 3 dias");
		fosfomicina.setApresentacao("500 mg");
		
		Indicacao norfloxacina = new Indicacao();
		norfloxacina.setNome("Norfloxacina");
		norfloxacina.setCodIndicacao("norfloxacina");
		norfloxacina.setDosagem("12hrs x 3 dias");
		norfloxacina.setApresentacao("500 mg ");
		
		Indicacao sulfatri = new Indicacao();
		sulfatri.setNome("Sulfatrimetoprim - Metoxazol");
		sulfatri.setCodIndicacao("sulfatr-meto");
		sulfatri.setDosagem("12hrs x 3  dias");
		sulfatri.setApresentacao("500 mg");
		
		indicacaoService.inclui(cefalexina);
		indicacaoService.inclui(axetil);
		indicacaoService.inclui(cefaclor);
		indicacaoService.inclui(cefixima);	
		indicacaoService.inclui(ampicilina);
		indicacaoService.inclui(amoxacilina);
		indicacaoService.inclui(tazobactam);
		indicacaoService.inclui(clavulanato);
		indicacaoService.inclui(nitro);
		indicacaoService.inclui(cipro);
		indicacaoService.inclui(fosfomicina);
		indicacaoService.inclui(norfloxacina);
		indicacaoService.inclui(sulfatri);		
	}

}
