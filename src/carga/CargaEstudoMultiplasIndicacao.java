package carga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modelo.Indicacao;
import modelo.TipoUsuario;
import modelo.Usuario;
import service.IndicacaoAppService;
import service.TipoUsuarioAppService;
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.Constantes;
import util.JsonConfigLoader;

import com.google.gson.Gson;

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
 * @author felipe.arruda
 *
 */
public class CargaEstudoMultiplasIndicacao extends CargaBase{
  
	// Services
	private static IndicacaoAppService indicacaoService;
	
	/**
	 * 
	 * Construdor da carga, respons�vel por instanciar os services.
	 * 
	 * @author felipe.arruda
	 * 
	 */
	public CargaEstudoMultiplasIndicacao(){
		try {
			indicacaoService = FabricaDeAppService.getAppService(IndicacaoAppService.class);
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
	 * @author felipe.arruda
	 * 
	 */
	@Override
	public boolean executar() throws AplicacaoException {
		this.incluirIndicacoes();
		return true;
	}
	

	/**
	 * Metodo respons�vel por preparar e inserir os valores padr�es de
	 * indica��es no banco de dados
	 * 
	 * @author felipe.pontes
	 * @throws AplicacaoException
	 */
	public void incluirIndicacoes() throws AplicacaoException {
		
		// 01 a 10 indicac�es
		Indicacao aClavulanico = new Indicacao();
		aClavulanico.setNome("�cido Clavul�nico");
		aClavulanico.setCodIndicacao("aClav");
		aClavulanico.setDosagem("Falta inserir");
		aClavulanico.setApresentacao("Exemplo gen�rico");

		Indicacao aFusidico = new Indicacao();
		aFusidico.setNome("�cido Fus�dico");
		aFusidico.setCodIndicacao("aFusi");
		aFusidico.setDosagem("Falta inserir");
		aFusidico.setApresentacao("Exemplo gen�rico");

		Indicacao aNalidixico = new Indicacao();
		aNalidixico.setNome("�cido Nalidixico");
		aNalidixico.setCodIndicacao("aNalid");
		aNalidixico.setDosagem("Falta inserir");
		aNalidixico.setApresentacao("Exemplo gen�rico");

		Indicacao aOxolinico = new Indicacao();
		aOxolinico.setNome("�cido Oxolinico");
		aOxolinico.setCodIndicacao("aOxol");
		aOxolinico.setDosagem("Falta inserir");
		aOxolinico.setApresentacao("Exemplo gen�rico");
		
		Indicacao aPipemidico = new Indicacao();
		aPipemidico.setNome("�cido Pipemidico");
		aPipemidico.setCodIndicacao("aPipe");
		aPipemidico.setDosagem("Falta inserir");
		aPipemidico.setApresentacao("Exemplo gen�rico");
		
		Indicacao amicacina = new Indicacao();
		amicacina.setNome("Amicacina");
		amicacina.setCodIndicacao("amica");
		amicacina.setDosagem("Falta inserir");
		amicacina.setApresentacao("Exemplo gen�rico");

		Indicacao ampicilina = new Indicacao();
		ampicilina.setNome("Ampicilina");
		ampicilina.setCodIndicacao("ampic");
		ampicilina.setDosagem("Falta inserir");
		ampicilina.setApresentacao("Exemplo gen�rico");

		Indicacao axetilcefuroxima = new Indicacao();
		axetilcefuroxima.setNome("Axetilcefuroxima");
		axetilcefuroxima.setCodIndicacao("axetil");
		axetilcefuroxima.setDosagem("Falta inserir");
		axetilcefuroxima.setApresentacao("Exemplo gen�rico");

		Indicacao azitromicina = new Indicacao();
		azitromicina.setNome("Azitromicina");
		azitromicina.setCodIndicacao("azitrom");
		azitromicina.setDosagem("Falta inserir");
		azitromicina.setApresentacao("Exemplo gen�rico");
		
		Indicacao aztreonam = new Indicacao();
		aztreonam.setNome("Aztreonam");
		aztreonam.setCodIndicacao("aztre");
		aztreonam.setDosagem("Falta inserir");
		aztreonam.setApresentacao("Exemplo gen�rico");
		
		// 11 a 20
		Indicacao bactracina = new Indicacao();
		bactracina.setNome("Bactracina");
		bactracina.setCodIndicacao("bactr");
		bactracina.setDosagem("Falta inserir");
		bactracina.setApresentacao("Exemplo gen�rico");

		Indicacao brodimoprima = new Indicacao();
		brodimoprima.setNome("Brodimoprima");
		brodimoprima.setCodIndicacao("brodim");
		brodimoprima.setDosagem("Falta inserir");
		brodimoprima.setApresentacao("Exemplo gen�rico");

		Indicacao capreomicina = new Indicacao();
		capreomicina.setNome("Capreomicina");
		capreomicina.setCodIndicacao("capre");
		capreomicina.setDosagem("Falta inserir");
		capreomicina.setApresentacao("Exemplo gen�rico");

		Indicacao carbenicilina = new Indicacao();
		carbenicilina.setNome("Carbenicilina");
		carbenicilina.setCodIndicacao("carben");
		carbenicilina.setDosagem("Falta inserir");
		carbenicilina.setApresentacao("Exemplo gen�rico");
		
		Indicacao cafaclor = new Indicacao();
		cafaclor.setNome("Cafaclor");
		cafaclor.setCodIndicacao("cafaclor");
		cafaclor.setDosagem("Falta inserir");
		cafaclor.setApresentacao("Exemplo gen�rico");
		
		Indicacao cefadroxil = new Indicacao();
		cefadroxil.setNome("Cefadroxil");
		cefadroxil.setCodIndicacao("cefadr");
		cefadroxil.setDosagem("Falta inserir");
		cefadroxil.setApresentacao("Exemplo gen�rico");

		Indicacao cefalexina = new Indicacao();
		cefalexina.setNome("Cefalexina");
		cefalexina.setCodIndicacao("cefale");
		cefalexina.setDosagem("Falta inserir");
		cefalexina.setApresentacao("Exemplo gen�rico");

		Indicacao cefalotina = new Indicacao();
		cefalotina.setNome("Cefalotina");
		cefalotina.setCodIndicacao("cefalot");
		cefalotina.setDosagem("Falta inserir");
		cefalotina.setApresentacao("Exemplo gen�rico");

		Indicacao cefazolina = new Indicacao();
		cefazolina.setNome("Cefazolina");
		cefazolina.setCodIndicacao("cefazol");
		cefazolina.setDosagem("Falta inserir");
		cefazolina.setApresentacao("Exemplo gen�rico");
		
		Indicacao cefepima = new Indicacao();
		cefepima.setNome("Cefepima");
		cefepima.setCodIndicacao("cefepima");
		cefepima.setDosagem("Falta inserir");
		cefepima.setApresentacao("Exemplo gen�rico");
		
		// 21 a 30
		Indicacao cefodizima = new Indicacao();
		cefodizima.setNome("Cefodizima");
		cefodizima.setCodIndicacao("cefodizima");
		cefodizima.setDosagem("Falta inserir");
		cefodizima.setApresentacao("Exemplo gen�rico");

		Indicacao cefoperazona = new Indicacao();
		cefoperazona.setNome("Cefoperazona");
		cefoperazona.setCodIndicacao("cefoper");
		cefoperazona.setDosagem("Falta inserir");
		cefoperazona.setApresentacao("Exemplo gen�rico");

		Indicacao cefotaxima = new Indicacao();
		cefotaxima.setNome("Cefotaxima");
		cefotaxima.setCodIndicacao("cefotax");
		cefotaxima.setDosagem("Falta inserir");
		cefotaxima.setApresentacao("Exemplo gen�rico");

		Indicacao cefoxitina = new Indicacao();
		cefoxitina.setNome("Cefoxitina");
		cefoxitina.setCodIndicacao("cefoxit");
		cefoxitina.setDosagem("Falta inserir");
		cefoxitina.setApresentacao("Exemplo gen�rico");
		
		Indicacao cefpodoxima = new Indicacao();
		cefpodoxima.setNome("Cefpodoxima");
		cefpodoxima.setCodIndicacao("cefpodoxi");
		cefpodoxima.setDosagem("Falta inserir");
		cefpodoxima.setApresentacao("Exemplo gen�rico");
		
		Indicacao cefpiroma = new Indicacao();
		cefpiroma.setNome("Cefpiroma");
		cefpiroma.setCodIndicacao("cefpir");
		cefpiroma.setDosagem("Falta inserir");
		cefpiroma.setApresentacao("Exemplo gen�rico");

		Indicacao cefprozil = new Indicacao();
		cefprozil.setNome("Cefprozil");
		cefprozil.setCodIndicacao("cefprozil");
		cefprozil.setDosagem("Falta inserir");
		cefprozil.setApresentacao("Exemplo gen�rico");

		Indicacao ceftadizima = new Indicacao();
		ceftadizima.setNome("Ceftadizima");
		ceftadizima.setCodIndicacao("ceftadiz");
		ceftadizima.setDosagem("Falta inserir");
		ceftadizima.setApresentacao("Exemplo gen�rico");

		Indicacao ceftriaxoma = new Indicacao();
		ceftriaxoma.setNome("Ceftriaxoma");
		ceftriaxoma.setCodIndicacao("ceftriax");
		ceftriaxoma.setDosagem("Falta inserir");
		ceftriaxoma.setApresentacao("Exemplo gen�rico");
		
		Indicacao cefuroxima = new Indicacao();
		cefuroxima.setNome("Cefuroxima");
		cefuroxima.setCodIndicacao("cefurox");
		cefuroxima.setDosagem("Falta inserir");
		cefuroxima.setApresentacao("Exemplo gen�rico");

		// 31 a 40
		Indicacao ciprofloxacina = new Indicacao();
		ciprofloxacina.setNome("Ciprofloxacina");
		ciprofloxacina.setCodIndicacao("ciproflo");
		ciprofloxacina.setDosagem("Falta inserir");
		ciprofloxacina.setApresentacao("Exemplo gen�rico");

		Indicacao claritromicina = new Indicacao();
		claritromicina.setNome("Claritromicina");
		claritromicina.setCodIndicacao("clarit");
		claritromicina.setDosagem("Falta inserir");
		claritromicina.setApresentacao("Exemplo gen�rico");

		Indicacao clindamicina = new Indicacao();
		clindamicina.setNome("Clindamicina");
		clindamicina.setCodIndicacao("clin");
		clindamicina.setDosagem("Falta inserir");
		clindamicina.setApresentacao("Exemplo gen�rico");

		Indicacao clofazimina = new Indicacao();
		clofazimina.setNome("Clofazimina");
		clofazimina.setCodIndicacao("clofazi");
		clofazimina.setDosagem("Falta inserir");
		clofazimina.setApresentacao("Exemplo gen�rico");
		
		Indicacao cloranfenicol = new Indicacao();
		cloranfenicol.setNome("Cloranfenicol");
		cloranfenicol.setCodIndicacao("cloran");
		cloranfenicol.setDosagem("Falta inserir");
		cloranfenicol.setApresentacao("Exemplo gen�rico");
		
		Indicacao cloxacilina = new Indicacao();
		cloxacilina.setNome("Cloxacilina");
		cloxacilina.setCodIndicacao("cloxac");
		cloxacilina.setDosagem("Falta inserir");
		cloxacilina.setApresentacao("Exemplo gen�rico");

		Indicacao daptomicina = new Indicacao();
		daptomicina.setNome("Daptomicina");
		daptomicina.setCodIndicacao("daptomi");
		daptomicina.setDosagem("Falta inserir");
		daptomicina.setApresentacao("Exemplo gen�rico");

		Indicacao dapsona = new Indicacao();
		dapsona.setNome("Dapsona");
		dapsona.setCodIndicacao("dapsona");
		dapsona.setDosagem("Falta inserir");
		dapsona.setApresentacao("Exemplo gen�rico");

		Indicacao dicloxacilina = new Indicacao();
		dicloxacilina.setNome("Dicloxacilina");
		dicloxacilina.setCodIndicacao("dicloxac");
		dicloxacilina.setDosagem("Falta inserir");
		dicloxacilina.setApresentacao("Exemplo gen�rico");
		
		Indicacao difenilsulfona = new Indicacao();
		difenilsulfona.setNome("Difenilsulfona");
		difenilsulfona.setCodIndicacao("difenils");
		difenilsulfona.setDosagem("Falta inserir");
		difenilsulfona.setApresentacao("Exemplo gen�rico");

		// 41 a 50
		Indicacao didroestreptomicina = new Indicacao();
		didroestreptomicina.setNome("Didroestreptomicina");
		didroestreptomicina.setCodIndicacao("didroes");
		didroestreptomicina.setDosagem("Falta inserir");
		didroestreptomicina.setApresentacao("Exemplo gen�rico");

		Indicacao diritromicina = new Indicacao();
		diritromicina.setNome("Diritromicina");
		diritromicina.setCodIndicacao("diritr");
		diritromicina.setDosagem("Falta inserir");
		diritromicina.setApresentacao("Exemplo gen�rico");

		Indicacao doripenem = new Indicacao();
		doripenem.setNome("Doripenem");
		doripenem.setCodIndicacao("dorip");
		doripenem.setDosagem("Falta inserir");
		doripenem.setApresentacao("Exemplo gen�rico");

		Indicacao doxiciclina = new Indicacao();
		doxiciclina.setNome("Doxiciclina");
		doxiciclina.setCodIndicacao("doxici");
		doxiciclina.setDosagem("Falta inserir");
		doxiciclina.setApresentacao("Exemplo gen�rico");
		
		Indicacao eritromicina = new Indicacao();
		eritromicina.setNome("Eritromicina");
		eritromicina.setCodIndicacao("eritrom");
		eritromicina.setDosagem("Falta inserir");
		eritromicina.setApresentacao("Exemplo gen�rico");
		
		Indicacao ertapenem = new Indicacao();
		ertapenem.setNome("Ertapenem");
		ertapenem.setCodIndicacao("ertap");
		ertapenem.setDosagem("Falta inserir");
		ertapenem.setApresentacao("Exemplo gen�rico");

		Indicacao espectinomicina = new Indicacao();
		espectinomicina.setNome("Espectinomicina");
		espectinomicina.setCodIndicacao("espectin");
		espectinomicina.setDosagem("Falta inserir");
		espectinomicina.setApresentacao("Exemplo gen�rico");

		Indicacao espiramicina = new Indicacao();
		espiramicina.setNome("Espiramicina");
		espiramicina.setCodIndicacao("espira");
		espiramicina.setDosagem("Falta inserir");
		espiramicina.setApresentacao("Exemplo gen�rico");

		Indicacao estreptomicina = new Indicacao();
		estreptomicina.setNome("Estreptomicina");
		estreptomicina.setCodIndicacao("estrept");
		estreptomicina.setDosagem("Falta inserir");
		estreptomicina.setApresentacao("Exemplo gen�rico");
		
		Indicacao etambutol = new Indicacao();
		etambutol.setNome("Etambutol");
		etambutol.setCodIndicacao("etambutol");
		etambutol.setDosagem("Falta inserir");
		etambutol.setApresentacao("Exemplo gen�rico");

		// 51 a 60
		Indicacao etionamida = new Indicacao();
		etionamida.setNome("Etionamida");
		etionamida.setCodIndicacao("etion");
		etionamida.setDosagem("Falta inserir");
		etionamida.setApresentacao("Exemplo gen�rico");

		Indicacao fosfomicina = new Indicacao();
		fosfomicina.setNome("Fosfomicina");
		fosfomicina.setCodIndicacao("fosfomi");
		fosfomicina.setDosagem("Falta inserir");
		fosfomicina.setApresentacao("Exemplo gen�rico");

		Indicacao ftalilsulfatiazol = new Indicacao();
		ftalilsulfatiazol.setNome("Ftalilsulfatiazol");
		ftalilsulfatiazol.setCodIndicacao("ftalilsu");
		ftalilsulfatiazol.setDosagem("Falta inserir");
		ftalilsulfatiazol.setApresentacao("Exemplo gen�rico");

		Indicacao gatifolacina = new Indicacao();
		gatifolacina.setNome("Gatifolacina");
		gatifolacina.setCodIndicacao("gatifol");
		gatifolacina.setDosagem("Falta inserir");
		gatifolacina.setApresentacao("Exemplo gen�rico");
		
		Indicacao gemifloxacino = new Indicacao();
		gemifloxacino.setNome("Gemifloxacino");
		gemifloxacino.setCodIndicacao("gemiflox");
		gemifloxacino.setDosagem("Falta inserir");
		gemifloxacino.setApresentacao("Exemplo gen�rico");
		
		Indicacao gentamicina = new Indicacao();
		gentamicina.setNome("Gentamicina");
		gentamicina.setCodIndicacao("gentam");
		gentamicina.setDosagem("Falta inserir");
		gentamicina.setApresentacao("Exemplo gen�rico");

		Indicacao imipenem = new Indicacao();
		imipenem.setNome("Imipenem");
		imipenem.setCodIndicacao("imipenem");
		imipenem.setDosagem("Falta inserir");
		imipenem.setApresentacao("Exemplo gen�rico");

		Indicacao isoniazida = new Indicacao();
		isoniazida.setNome("Isoniazida");
		isoniazida.setCodIndicacao("isoni");
		isoniazida.setDosagem("Falta inserir");
		isoniazida.setApresentacao("Exemplo gen�rico");

		Indicacao linezolida = new Indicacao();
		linezolida.setNome("Linezolida");
		linezolida.setCodIndicacao("linez");
		linezolida.setDosagem("Falta inserir");
		linezolida.setApresentacao("Exemplo gen�rico");
		
		Indicacao limeciclina = new Indicacao();
		limeciclina.setNome("Limeciclina");
		limeciclina.setCodIndicacao("limec");
		limeciclina.setDosagem("Falta inserir");
		limeciclina.setApresentacao("Exemplo gen�rico");
		
		// 61 a 70
		Indicacao lincomicina = new Indicacao();
		lincomicina.setNome("Lincomicina");
		lincomicina.setCodIndicacao("lincom");
		lincomicina.setDosagem("Falta inserir");
		lincomicina.setApresentacao("Exemplo gen�rico");

		Indicacao lomefloxacina = new Indicacao();
		lomefloxacina.setNome("Lomefloxacina");
		lomefloxacina.setCodIndicacao("lomeflo");
		lomefloxacina.setDosagem("Falta inserir");
		lomefloxacina.setApresentacao("Exemplo gen�rico");

		Indicacao loracarbef = new Indicacao();
		loracarbef.setNome("Loracarbef");
		loracarbef.setCodIndicacao("loracarbef");
		loracarbef.setDosagem("Falta inserir");
		loracarbef.setApresentacao("Exemplo gen�rico");

		Indicacao mandelamina = new Indicacao();
		mandelamina.setNome("Mandelamina");
		mandelamina.setCodIndicacao("mandel");
		mandelamina.setDosagem("Falta inserir");
		mandelamina.setApresentacao("Exemplo gen�rico");
		
		Indicacao meropenem = new Indicacao();
		meropenem.setNome("Meropenem");
		meropenem.setCodIndicacao("merop");
		meropenem.setDosagem("Falta inserir");
		meropenem.setApresentacao("Exemplo gen�rico");
		
		Indicacao metampicilina = new Indicacao();
		metampicilina.setNome("Metampicilina");
		metampicilina.setCodIndicacao("metamp");
		metampicilina.setDosagem("Falta inserir");
		metampicilina.setApresentacao("Exemplo gen�rico");

		Indicacao metronidazol = new Indicacao();
		metronidazol.setNome("Metronidazol");
		metronidazol.setCodIndicacao("metron");
		metronidazol.setDosagem("Falta inserir");
		metronidazol.setApresentacao("Exemplo gen�rico");

		Indicacao minociclina = new Indicacao();
		minociclina.setNome("Minociclina");
		minociclina.setCodIndicacao("minoci");
		minociclina.setDosagem("Falta inserir");
		minociclina.setApresentacao("Exemplo gen�rico");

		Indicacao miocamicina = new Indicacao();
		miocamicina.setNome("Miocamicina");
		miocamicina.setCodIndicacao("mioca");
		miocamicina.setDosagem("Falta inserir");
		miocamicina.setApresentacao("Exemplo gen�rico");
		
		Indicacao moxifloxacino = new Indicacao();
		moxifloxacino.setNome("Moxifloxacino");
		moxifloxacino.setCodIndicacao("moxif");
		moxifloxacino.setDosagem("Falta inserir");
		moxifloxacino.setApresentacao("Exemplo gen�rico");

		// 71 a 80
		Indicacao mupirocina = new Indicacao();
		mupirocina.setNome("Mupirocina");
		mupirocina.setCodIndicacao("mupir");
		mupirocina.setDosagem("Falta inserir");
		mupirocina.setApresentacao("Exemplo gen�rico");

		Indicacao neomicina = new Indicacao();
		neomicina.setNome("Neomicina");
		neomicina.setCodIndicacao("neomi");
		neomicina.setDosagem("Falta inserir");
		neomicina.setApresentacao("Exemplo gen�rico");

		Indicacao netilmicina = new Indicacao();
		netilmicina.setNome("Netilmicina");
		netilmicina.setCodIndicacao("netil");
		netilmicina.setDosagem("Falta inserir");
		netilmicina.setApresentacao("Exemplo gen�rico");

		Indicacao nitrofurantoina = new Indicacao();
		nitrofurantoina.setNome("Nitrofurantoina");
		nitrofurantoina.setCodIndicacao("nitrofur");
		nitrofurantoina.setDosagem("Falta inserir");
		nitrofurantoina.setApresentacao("Exemplo gen�rico");
		
		Indicacao nitroxolina = new Indicacao();
		nitroxolina.setNome("Nitroxolina");
		nitroxolina.setCodIndicacao("nitro");
		nitroxolina.setDosagem("Falta inserir");
		nitroxolina.setApresentacao("Exemplo gen�rico");
		
		Indicacao norfloxacina = new Indicacao();
		norfloxacina.setNome("Norfloxacina");
		norfloxacina.setCodIndicacao("norfl");
		norfloxacina.setDosagem("Falta inserir");
		norfloxacina.setApresentacao("Exemplo gen�rico");

		Indicacao ofloxacina = new Indicacao();
		ofloxacina.setNome("Ofloxacina");
		ofloxacina.setCodIndicacao("oflox");
		ofloxacina.setDosagem("Falta inserir");
		ofloxacina.setApresentacao("Exemplo gen�rico");

		Indicacao oxacilina = new Indicacao();
		oxacilina.setNome("Oxacilina");
		oxacilina.setCodIndicacao("oxacilina");
		oxacilina.setDosagem("Falta inserir");
		oxacilina.setApresentacao("Exemplo gen�rico");

		Indicacao oxitetraciclina = new Indicacao();
		oxitetraciclina.setNome("Oxitetraciclina");
		oxitetraciclina.setCodIndicacao("oxitet");
		oxitetraciclina.setDosagem("Falta inserir");
		oxitetraciclina.setApresentacao("Exemplo gen�rico");
		
		Indicacao pefloxacina = new Indicacao();
		pefloxacina.setNome("Pefloxacina");
		pefloxacina.setCodIndicacao("peflox");
		pefloxacina.setDosagem("Falta inserir");
		pefloxacina.setApresentacao("Exemplo gen�rico");

		// 81 a 90
		Indicacao penicilinaG = new Indicacao();
		penicilinaG.setNome("Penicilina G");
		penicilinaG.setCodIndicacao("peniciG");
		penicilinaG.setDosagem("Falta inserir");
		penicilinaG.setApresentacao("Exemplo gen�rico");

		Indicacao penicilinaV = new Indicacao();
		penicilinaV.setNome("Penicilina V");
		penicilinaV.setCodIndicacao("peniciV");
		penicilinaV.setDosagem("Falta inserir");
		penicilinaV.setApresentacao("Exemplo gen�rico");

		Indicacao piperacilina = new Indicacao();
		piperacilina.setNome("Piperacilina");
		piperacilina.setCodIndicacao("piperac");
		piperacilina.setDosagem("Falta inserir");
		piperacilina.setApresentacao("Exemplo gen�rico");

		Indicacao pirazinamida = new Indicacao();
		pirazinamida.setNome("Pirazinamida");
		pirazinamida.setCodIndicacao("pirazi");
		pirazinamida.setDosagem("Falta inserir");
		pirazinamida.setApresentacao("Exemplo gen�rico");
		
		Indicacao polimixinaB = new Indicacao();
		polimixinaB.setNome("Polimixina B");
		polimixinaB.setCodIndicacao("polimixB");
		polimixinaB.setDosagem("Falta inserir");
		polimixinaB.setApresentacao("Exemplo gen�rico");
		
		Indicacao pristinamicina = new Indicacao();
		pristinamicina.setNome("Pristinamicina");
		pristinamicina.setCodIndicacao("pristin");
		pristinamicina.setDosagem("Falta inserir");
		pristinamicina.setApresentacao("Exemplo gen�rico");

		Indicacao protionamida = new Indicacao();
		protionamida.setNome("Protionamida");
		protionamida.setCodIndicacao("protion");
		protionamida.setDosagem("Falta inserir");
		protionamida.setApresentacao("Exemplo gen�rico");

		Indicacao retapamulina = new Indicacao();
		retapamulina.setNome("Retapamulina");
		retapamulina.setCodIndicacao("retapam");
		retapamulina.setDosagem("Falta inserir");
		retapamulina.setApresentacao("Exemplo gen�rico");

		Indicacao rifamicina = new Indicacao();
		rifamicina.setNome("Rifamicina");
		rifamicina.setCodIndicacao("rifam");
		rifamicina.setDosagem("Falta inserir");
		rifamicina.setApresentacao("Exemplo gen�rico");
		
		Indicacao rifampicina = new Indicacao();
		rifampicina.setNome("Rifampicina");
		rifampicina.setCodIndicacao("rifamp");
		rifampicina.setDosagem("Falta inserir");
		rifampicina.setApresentacao("Exemplo gen�rico");

		// 91 a 100
		Indicacao rifapetina = new Indicacao();
		rifapetina.setNome("Rifapetina");
		rifapetina.setCodIndicacao("rifapet");
		rifapetina.setDosagem("Falta inserir");
		rifapetina.setApresentacao("Exemplo gen�rico");

		Indicacao rosoxacina = new Indicacao();
		rosoxacina.setNome("Rosoxacina");
		rosoxacina.setCodIndicacao("rosox");
		rosoxacina.setDosagem("Falta inserir");
		rosoxacina.setApresentacao("Exemplo gen�rico");

		Indicacao roxitromicina = new Indicacao();
		roxitromicina.setNome("Roxitromicina");
		roxitromicina.setCodIndicacao("roxitro");
		roxitromicina.setDosagem("Falta inserir");
		roxitromicina.setApresentacao("Exemplo gen�rico");

		Indicacao sulbactam = new Indicacao();
		sulbactam.setNome("Sulbactam");
		sulbactam.setCodIndicacao("sulbactam");
		sulbactam.setDosagem("Falta inserir");
		sulbactam.setApresentacao("Exemplo gen�rico");
		
		Indicacao sulfadiazina = new Indicacao();
		sulfadiazina.setNome("Sulfadiazina");
		sulfadiazina.setCodIndicacao("sulfadiaz");
		sulfadiazina.setDosagem("Falta inserir");
		sulfadiazina.setApresentacao("Exemplo gen�rico");
		
		Indicacao sulfadoxina = new Indicacao();
		sulfadoxina.setNome("Sulfadoxina");
		sulfadoxina.setCodIndicacao("sulfadox");
		sulfadoxina.setDosagem("Falta inserir");
		sulfadoxina.setApresentacao("Exemplo gen�rico");

		Indicacao sulfaguanidina = new Indicacao();
		sulfaguanidina.setNome("Sulfaguanidina");
		sulfaguanidina.setCodIndicacao("sulfagu");
		sulfaguanidina.setDosagem("Falta inserir");
		sulfaguanidina.setApresentacao("Exemplo gen�rico");

		Indicacao sulfamerazina = new Indicacao();
		sulfamerazina.setNome("Sulfamerazina");
		sulfamerazina.setCodIndicacao("sulfameraz");
		sulfamerazina.setDosagem("Falta inserir");
		sulfamerazina.setApresentacao("Exemplo gen�rico");

		Indicacao sulfanilamida = new Indicacao();
		sulfanilamida.setNome("Sulfanilamida");
		sulfanilamida.setCodIndicacao("sulfanilam");
		sulfanilamida.setDosagem("Falta inserir");
		sulfanilamida.setApresentacao("Exemplo gen�rico");
		
		Indicacao sulfametizol = new Indicacao();
		sulfametizol.setNome("Sulfametizol");
		sulfametizol.setCodIndicacao("sulfameti");
		sulfametizol.setDosagem("Falta inserir");
		sulfametizol.setApresentacao("Exemplo gen�rico");

		// 91 a 100
		Indicacao sulfametoxazol = new Indicacao();
		sulfametoxazol.setNome("Sulfametoxazol");
		sulfametoxazol.setCodIndicacao("sulfametox");
		sulfametoxazol.setDosagem("Falta inserir");
		sulfametoxazol.setApresentacao("Exemplo gen�rico");

		Indicacao sulfametoxipiridazina = new Indicacao();
		sulfametoxipiridazina.setNome("Sulfametoxipiridazina");
		sulfametoxipiridazina.setCodIndicacao("sulfametoxi");
		sulfametoxipiridazina.setDosagem("Falta inserir");
		sulfametoxipiridazina.setApresentacao("Exemplo gen�rico");

		Indicacao sulfametoxipirimidina = new Indicacao();
		sulfametoxipirimidina.setNome("Sulfametoxipirimidina");
		sulfametoxipirimidina.setCodIndicacao("sulfametoxip");
		sulfametoxipirimidina.setDosagem("Falta inserir");
		sulfametoxipirimidina.setApresentacao("Exemplo gen�rico");

		Indicacao sulfatiazol = new Indicacao();
		sulfatiazol.setNome("Sulfatiazol");
		sulfatiazol.setCodIndicacao("sulfatia");
		sulfatiazol.setDosagem("Falta inserir");
		sulfatiazol.setApresentacao("Exemplo gen�rico");
		
		Indicacao sultamicilina = new Indicacao();
		sultamicilina.setNome("Sultamicilina");
		sultamicilina.setCodIndicacao("sultamic");
		sultamicilina.setDosagem("Falta inserir");
		sultamicilina.setApresentacao("Exemplo gen�rico");
		
		Indicacao tazobactam = new Indicacao();
		tazobactam.setNome("Tazobactam");
		tazobactam.setCodIndicacao("tazobac");
		tazobactam.setDosagem("Falta inserir");
		tazobactam.setApresentacao("Exemplo gen�rico");

		Indicacao teicoplanina = new Indicacao();
		teicoplanina.setNome("Teicoplanina");
		teicoplanina.setCodIndicacao("teicoplan");
		teicoplanina.setDosagem("Falta inserir");
		teicoplanina.setApresentacao("Exemplo gen�rico");

		Indicacao telitromicina = new Indicacao();
		telitromicina.setNome("Telitromicina");
		telitromicina.setCodIndicacao("telitrom");
		telitromicina.setDosagem("Falta inserir");
		telitromicina.setApresentacao("Exemplo gen�rico");

		Indicacao tetraciclina = new Indicacao();
		tetraciclina.setNome("Tetraciclina");
		tetraciclina.setCodIndicacao("tetrac");
		tetraciclina.setDosagem("Falta inserir");
		tetraciclina.setApresentacao("Exemplo gen�rico");
		
		Indicacao tianfenicol = new Indicacao();
		tianfenicol.setNome("Tianfenicol");
		tianfenicol.setCodIndicacao("tianfen");
		tianfenicol.setDosagem("Falta inserir");
		tianfenicol.setApresentacao("Exemplo gen�rico");

		// 91 a 100
		Indicacao ticarcilina = new Indicacao();
		ticarcilina.setNome("Ticarcilina");
		ticarcilina.setCodIndicacao("ticarc");
		ticarcilina.setDosagem("Falta inserir");
		ticarcilina.setApresentacao("Exemplo gen�rico");

		Indicacao tigeciclina = new Indicacao();
		tigeciclina.setNome("Tigeciclina");
		tigeciclina.setCodIndicacao("tigec");
		tigeciclina.setDosagem("Falta inserir");
		tigeciclina.setApresentacao("Exemplo gen�rico");

		Indicacao tirotricina = new Indicacao();
		tirotricina.setNome("Tirotricina");
		tirotricina.setCodIndicacao("tirotr");
		tirotricina.setDosagem("Falta inserir");
		tirotricina.setApresentacao("Exemplo gen�rico");

		Indicacao tobramicina = new Indicacao();
		tobramicina.setNome("Tobramicina");
		tobramicina.setCodIndicacao("tobram");
		tobramicina.setDosagem("Falta inserir");
		tobramicina.setApresentacao("Exemplo gen�rico");
		
		Indicacao trimetoprina = new Indicacao();
		trimetoprina.setNome("Trimetoprina");
		trimetoprina.setCodIndicacao("trimeto");
		trimetoprina.setDosagem("Falta inserir");
		trimetoprina.setApresentacao("Exemplo gen�rico");
		
		Indicacao trovafloxacina = new Indicacao();
		trovafloxacina.setNome("Trovafloxacina");
		trovafloxacina.setCodIndicacao("trovaflox");
		trovafloxacina.setDosagem("Falta inserir");
		trovafloxacina.setApresentacao("Exemplo gen�rico");

		Indicacao vancomicina = new Indicacao();
		vancomicina.setNome("Vancomicina");
		vancomicina.setCodIndicacao("vancomic");
		vancomicina.setDosagem("Falta inserir");
		vancomicina.setApresentacao("Exemplo gen�rico");
	
		
		// 01 a 10
		indicacaoService.inclui(aClavulanico);
		indicacaoService.inclui(aFusidico);
		indicacaoService.inclui(aNalidixico);
		indicacaoService.inclui(aOxolinico);
		indicacaoService.inclui(aPipemidico);
		
		indicacaoService.inclui(amicacina);
		indicacaoService.inclui(ampicilina);
		indicacaoService.inclui(axetilcefuroxima);
		indicacaoService.inclui(azitromicina);		
		indicacaoService.inclui(aztreonam);
		
		// 11 a 20
		indicacaoService.inclui(bactracina);
		indicacaoService.inclui(brodimoprima);
		indicacaoService.inclui(capreomicina);
		indicacaoService.inclui(carbenicilina);		
		indicacaoService.inclui(cafaclor);
		
		indicacaoService.inclui(cefadroxil);
		indicacaoService.inclui(cefalexina);
		indicacaoService.inclui(cefalotina);
		indicacaoService.inclui(cefazolina);		
		indicacaoService.inclui(cefepima);	
		
		// 21 a 30
		indicacaoService.inclui(cefodizima);
		indicacaoService.inclui(cefoperazona);
		indicacaoService.inclui(cefotaxima);
		indicacaoService.inclui(cefoxitina);		
		indicacaoService.inclui(cefpodoxima);
		
		indicacaoService.inclui(cefpiroma);
		indicacaoService.inclui(cefprozil);
		indicacaoService.inclui(ceftadizima);
		indicacaoService.inclui(ceftriaxoma);		
		indicacaoService.inclui(cefuroxima);	

		// 31 a 40
		indicacaoService.inclui(ciprofloxacina);
		indicacaoService.inclui(claritromicina);
		indicacaoService.inclui(clindamicina);
		indicacaoService.inclui(clofazimina);		
		indicacaoService.inclui(cloranfenicol);
		
		indicacaoService.inclui(cloxacilina);
		indicacaoService.inclui(daptomicina);
		indicacaoService.inclui(dapsona);
		indicacaoService.inclui(dicloxacilina);		
		indicacaoService.inclui(difenilsulfona);	
		
		// 41 a 50
		indicacaoService.inclui(didroestreptomicina);
		indicacaoService.inclui(diritromicina);
		indicacaoService.inclui(doripenem);
		indicacaoService.inclui(doxiciclina);		
		indicacaoService.inclui(eritromicina);
		
		indicacaoService.inclui(ertapenem);
		indicacaoService.inclui(espectinomicina);
		indicacaoService.inclui(espiramicina);
		indicacaoService.inclui(estreptomicina);		
		indicacaoService.inclui(etambutol);
		
		// 51 a 60
		indicacaoService.inclui(etionamida);
		indicacaoService.inclui(fosfomicina);
		indicacaoService.inclui(ftalilsulfatiazol);
		indicacaoService.inclui(gatifolacina);		
		indicacaoService.inclui(gemifloxacino);
		
		indicacaoService.inclui(gentamicina);
		indicacaoService.inclui(imipenem);
		indicacaoService.inclui(isoniazida);
		indicacaoService.inclui(linezolida);		
		indicacaoService.inclui(limeciclina);
		
		// 61 a 70
		indicacaoService.inclui(lincomicina);
		indicacaoService.inclui(lomefloxacina);
		indicacaoService.inclui(loracarbef);
		indicacaoService.inclui(mandelamina);		
		indicacaoService.inclui(meropenem);
		
		indicacaoService.inclui(metampicilina);
		indicacaoService.inclui(metronidazol);
		indicacaoService.inclui(minociclina);
		indicacaoService.inclui(miocamicina);		
		indicacaoService.inclui(moxifloxacino);		
		
		// 71 a 80
		indicacaoService.inclui(mupirocina);
		indicacaoService.inclui(neomicina);
		indicacaoService.inclui(netilmicina);
		indicacaoService.inclui(nitrofurantoina);		
		indicacaoService.inclui(nitroxolina);
		
		indicacaoService.inclui(norfloxacina);
		indicacaoService.inclui(ofloxacina);
		indicacaoService.inclui(oxacilina);
		indicacaoService.inclui(oxitetraciclina);		
		indicacaoService.inclui(pefloxacina);	
		
		// 81 a 90
		indicacaoService.inclui(penicilinaG);
		indicacaoService.inclui(penicilinaV);
		indicacaoService.inclui(piperacilina);
		indicacaoService.inclui(pirazinamida);		
		indicacaoService.inclui(polimixinaB);
		
		indicacaoService.inclui(pristinamicina);
		indicacaoService.inclui(protionamida);
		indicacaoService.inclui(retapamulina);
		indicacaoService.inclui(rifamicina);		
		indicacaoService.inclui(rifampicina);		

		// 91 a 100
		indicacaoService.inclui(rifapetina);
		indicacaoService.inclui(rosoxacina);
		indicacaoService.inclui(roxitromicina);
		indicacaoService.inclui(sulbactam);		
		indicacaoService.inclui(sulfadiazina);
		
		indicacaoService.inclui(sulfadoxina);
		indicacaoService.inclui(sulfaguanidina);
		indicacaoService.inclui(sulfamerazina);
		indicacaoService.inclui(sulfanilamida);		
		indicacaoService.inclui(sulfametizol);
		
		// 101 a 110
		indicacaoService.inclui(sulfametoxazol);
		indicacaoService.inclui(sulfametoxipiridazina);
		indicacaoService.inclui(sulfametoxipirimidina);
		indicacaoService.inclui(sulfatiazol);		
		indicacaoService.inclui(sultamicilina);
		
		indicacaoService.inclui(tazobactam);
		indicacaoService.inclui(teicoplanina);
		indicacaoService.inclui(telitromicina);
		indicacaoService.inclui(tetraciclina);		
		indicacaoService.inclui(tianfenicol);			
	
		// 111 a 117
		indicacaoService.inclui(ticarcilina);
		indicacaoService.inclui(tigeciclina);
		indicacaoService.inclui(tirotricina);
		indicacaoService.inclui(tobramicina);		
		indicacaoService.inclui(trimetoprina);
		
		indicacaoService.inclui(trovafloxacina);
		indicacaoService.inclui(vancomicina);
	
	}
	

}
