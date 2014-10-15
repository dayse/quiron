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
public class CargaEstudoMultiplasIndicacao extends CargaBase{
  
	// Services
	private static IndicacaoAppService indicacaoService;
	
	/**
	 * 
	 * Construdor da carga, responsável por instanciar os services.
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
		this.incluirIndicacoes();
		return true;
	}
	

	/**
	 * Metodo responsável por preparar e inserir os valores padrões de
	 * indicações no banco de dados
	 * 
	 * @author felipe.pontes
	 * @throws AplicacaoException
	 */
	public void incluirIndicacoes() throws AplicacaoException {
		
		// 01 a 10 indicacões
		Indicacao aClavulanico = new Indicacao();
		aClavulanico.setNome("Ácido Clavulânico");
		aClavulanico.setCodIndicacao("aClav");
		aClavulanico.setDosagem("Falta inserir");
		aClavulanico.setApresentacao("Exemplo genérico");

		Indicacao aFusidico = new Indicacao();
		aFusidico.setNome("Ácido Fusídico");
		aFusidico.setCodIndicacao("aFusi");
		aFusidico.setDosagem("Falta inserir");
		aFusidico.setApresentacao("Exemplo genérico");

		Indicacao aNalidixico = new Indicacao();
		aNalidixico.setNome("Ácido Nalidixico");
		aNalidixico.setCodIndicacao("aNalid");
		aNalidixico.setDosagem("Falta inserir");
		aNalidixico.setApresentacao("Exemplo genérico");

		Indicacao aOxolinico = new Indicacao();
		aOxolinico.setNome("Ácido Oxolinico");
		aOxolinico.setCodIndicacao("aOxol");
		aOxolinico.setDosagem("Falta inserir");
		aOxolinico.setApresentacao("Exemplo genérico");
		
		Indicacao aPipemidico = new Indicacao();
		aPipemidico.setNome("Ácido Pipemidico");
		aPipemidico.setCodIndicacao("aPipe");
		aPipemidico.setDosagem("Falta inserir");
		aPipemidico.setApresentacao("Exemplo genérico");
		
		Indicacao amicacina = new Indicacao();
		amicacina.setNome("Amicacina");
		amicacina.setCodIndicacao("amica");
		amicacina.setDosagem("Falta inserir");
		amicacina.setApresentacao("Exemplo genérico");

		Indicacao ampicilina = new Indicacao();
		ampicilina.setNome("Ampicilina");
		ampicilina.setCodIndicacao("ampic");
		ampicilina.setDosagem("Falta inserir");
		ampicilina.setApresentacao("Exemplo genérico");

		Indicacao axetilcefuroxima = new Indicacao();
		axetilcefuroxima.setNome("Axetilcefuroxima");
		axetilcefuroxima.setCodIndicacao("axetil");
		axetilcefuroxima.setDosagem("Falta inserir");
		axetilcefuroxima.setApresentacao("Exemplo genérico");

		Indicacao azitromicina = new Indicacao();
		azitromicina.setNome("Azitromicina");
		azitromicina.setCodIndicacao("azitrom");
		azitromicina.setDosagem("Falta inserir");
		azitromicina.setApresentacao("Exemplo genérico");
		
		Indicacao aztreonam = new Indicacao();
		aztreonam.setNome("Aztreonam");
		aztreonam.setCodIndicacao("aztre");
		aztreonam.setDosagem("Falta inserir");
		aztreonam.setApresentacao("Exemplo genérico");
		
		// 11 a 20
		Indicacao bactracina = new Indicacao();
		bactracina.setNome("Bactracina");
		bactracina.setCodIndicacao("bactr");
		bactracina.setDosagem("Falta inserir");
		bactracina.setApresentacao("Exemplo genérico");

		Indicacao brodimoprima = new Indicacao();
		brodimoprima.setNome("Brodimoprima");
		brodimoprima.setCodIndicacao("brodim");
		brodimoprima.setDosagem("Falta inserir");
		brodimoprima.setApresentacao("Exemplo genérico");

		Indicacao capreomicina = new Indicacao();
		capreomicina.setNome("Capreomicina");
		capreomicina.setCodIndicacao("capre");
		capreomicina.setDosagem("Falta inserir");
		capreomicina.setApresentacao("Exemplo genérico");

		Indicacao carbenicilina = new Indicacao();
		carbenicilina.setNome("Carbenicilina");
		carbenicilina.setCodIndicacao("carben");
		carbenicilina.setDosagem("Falta inserir");
		carbenicilina.setApresentacao("Exemplo genérico");
		
		Indicacao cafaclor = new Indicacao();
		cafaclor.setNome("Cafaclor");
		cafaclor.setCodIndicacao("cafaclor");
		cafaclor.setDosagem("Falta inserir");
		cafaclor.setApresentacao("Exemplo genérico");
		
		Indicacao cefadroxil = new Indicacao();
		cefadroxil.setNome("Cefadroxil");
		cefadroxil.setCodIndicacao("cefadr");
		cefadroxil.setDosagem("Falta inserir");
		cefadroxil.setApresentacao("Exemplo genérico");

		Indicacao cefalexina = new Indicacao();
		cefalexina.setNome("Cefalexina");
		cefalexina.setCodIndicacao("cefale");
		cefalexina.setDosagem("Falta inserir");
		cefalexina.setApresentacao("Exemplo genérico");

		Indicacao cefalotina = new Indicacao();
		cefalotina.setNome("Cefalotina");
		cefalotina.setCodIndicacao("cefalot");
		cefalotina.setDosagem("Falta inserir");
		cefalotina.setApresentacao("Exemplo genérico");

		Indicacao cefazolina = new Indicacao();
		cefazolina.setNome("Cefazolina");
		cefazolina.setCodIndicacao("cefazol");
		cefazolina.setDosagem("Falta inserir");
		cefazolina.setApresentacao("Exemplo genérico");
		
		Indicacao cefepima = new Indicacao();
		cefepima.setNome("Cefepima");
		cefepima.setCodIndicacao("cefepima");
		cefepima.setDosagem("Falta inserir");
		cefepima.setApresentacao("Exemplo genérico");
		
		// 21 a 30
		Indicacao cefodizima = new Indicacao();
		cefodizima.setNome("Cefodizima");
		cefodizima.setCodIndicacao("cefodizima");
		cefodizima.setDosagem("Falta inserir");
		cefodizima.setApresentacao("Exemplo genérico");

		Indicacao cefoperazona = new Indicacao();
		cefoperazona.setNome("Cefoperazona");
		cefoperazona.setCodIndicacao("cefoper");
		cefoperazona.setDosagem("Falta inserir");
		cefoperazona.setApresentacao("Exemplo genérico");

		Indicacao cefotaxima = new Indicacao();
		cefotaxima.setNome("Cefotaxima");
		cefotaxima.setCodIndicacao("cefotax");
		cefotaxima.setDosagem("Falta inserir");
		cefotaxima.setApresentacao("Exemplo genérico");

		Indicacao cefoxitina = new Indicacao();
		cefoxitina.setNome("Cefoxitina");
		cefoxitina.setCodIndicacao("cefoxit");
		cefoxitina.setDosagem("Falta inserir");
		cefoxitina.setApresentacao("Exemplo genérico");
		
		Indicacao cefpodoxima = new Indicacao();
		cefpodoxima.setNome("Cefpodoxima");
		cefpodoxima.setCodIndicacao("cefpodoxi");
		cefpodoxima.setDosagem("Falta inserir");
		cefpodoxima.setApresentacao("Exemplo genérico");
		
		Indicacao cefpiroma = new Indicacao();
		cefpiroma.setNome("Cefpiroma");
		cefpiroma.setCodIndicacao("cefpir");
		cefpiroma.setDosagem("Falta inserir");
		cefpiroma.setApresentacao("Exemplo genérico");

		Indicacao cefprozil = new Indicacao();
		cefprozil.setNome("Cefprozil");
		cefprozil.setCodIndicacao("cefprozil");
		cefprozil.setDosagem("Falta inserir");
		cefprozil.setApresentacao("Exemplo genérico");

		Indicacao ceftadizima = new Indicacao();
		ceftadizima.setNome("Ceftadizima");
		ceftadizima.setCodIndicacao("ceftadiz");
		ceftadizima.setDosagem("Falta inserir");
		ceftadizima.setApresentacao("Exemplo genérico");

		Indicacao ceftriaxoma = new Indicacao();
		ceftriaxoma.setNome("Ceftriaxoma");
		ceftriaxoma.setCodIndicacao("ceftriax");
		ceftriaxoma.setDosagem("Falta inserir");
		ceftriaxoma.setApresentacao("Exemplo genérico");
		
		Indicacao cefuroxima = new Indicacao();
		cefuroxima.setNome("Cefuroxima");
		cefuroxima.setCodIndicacao("cefurox");
		cefuroxima.setDosagem("Falta inserir");
		cefuroxima.setApresentacao("Exemplo genérico");

		// 31 a 40
		Indicacao ciprofloxacina = new Indicacao();
		ciprofloxacina.setNome("Ciprofloxacina");
		ciprofloxacina.setCodIndicacao("ciproflo");
		ciprofloxacina.setDosagem("Falta inserir");
		ciprofloxacina.setApresentacao("Exemplo genérico");

		Indicacao claritromicina = new Indicacao();
		claritromicina.setNome("Claritromicina");
		claritromicina.setCodIndicacao("clarit");
		claritromicina.setDosagem("Falta inserir");
		claritromicina.setApresentacao("Exemplo genérico");

		Indicacao clindamicina = new Indicacao();
		clindamicina.setNome("Clindamicina");
		clindamicina.setCodIndicacao("clin");
		clindamicina.setDosagem("Falta inserir");
		clindamicina.setApresentacao("Exemplo genérico");

		Indicacao clofazimina = new Indicacao();
		clofazimina.setNome("Clofazimina");
		clofazimina.setCodIndicacao("clofazi");
		clofazimina.setDosagem("Falta inserir");
		clofazimina.setApresentacao("Exemplo genérico");
		
		Indicacao cloranfenicol = new Indicacao();
		cloranfenicol.setNome("Cloranfenicol");
		cloranfenicol.setCodIndicacao("cloran");
		cloranfenicol.setDosagem("Falta inserir");
		cloranfenicol.setApresentacao("Exemplo genérico");
		
		Indicacao cloxacilina = new Indicacao();
		cloxacilina.setNome("Cloxacilina");
		cloxacilina.setCodIndicacao("cloxac");
		cloxacilina.setDosagem("Falta inserir");
		cloxacilina.setApresentacao("Exemplo genérico");

		Indicacao daptomicina = new Indicacao();
		daptomicina.setNome("Daptomicina");
		daptomicina.setCodIndicacao("daptomi");
		daptomicina.setDosagem("Falta inserir");
		daptomicina.setApresentacao("Exemplo genérico");

		Indicacao dapsona = new Indicacao();
		dapsona.setNome("Dapsona");
		dapsona.setCodIndicacao("dapsona");
		dapsona.setDosagem("Falta inserir");
		dapsona.setApresentacao("Exemplo genérico");

		Indicacao dicloxacilina = new Indicacao();
		dicloxacilina.setNome("Dicloxacilina");
		dicloxacilina.setCodIndicacao("dicloxac");
		dicloxacilina.setDosagem("Falta inserir");
		dicloxacilina.setApresentacao("Exemplo genérico");
		
		Indicacao difenilsulfona = new Indicacao();
		difenilsulfona.setNome("Difenilsulfona");
		difenilsulfona.setCodIndicacao("difenils");
		difenilsulfona.setDosagem("Falta inserir");
		difenilsulfona.setApresentacao("Exemplo genérico");

		// 41 a 50
		Indicacao didroestreptomicina = new Indicacao();
		didroestreptomicina.setNome("Didroestreptomicina");
		didroestreptomicina.setCodIndicacao("didroes");
		didroestreptomicina.setDosagem("Falta inserir");
		didroestreptomicina.setApresentacao("Exemplo genérico");

		Indicacao diritromicina = new Indicacao();
		diritromicina.setNome("Diritromicina");
		diritromicina.setCodIndicacao("diritr");
		diritromicina.setDosagem("Falta inserir");
		diritromicina.setApresentacao("Exemplo genérico");

		Indicacao doripenem = new Indicacao();
		doripenem.setNome("Doripenem");
		doripenem.setCodIndicacao("dorip");
		doripenem.setDosagem("Falta inserir");
		doripenem.setApresentacao("Exemplo genérico");

		Indicacao doxiciclina = new Indicacao();
		doxiciclina.setNome("Doxiciclina");
		doxiciclina.setCodIndicacao("doxici");
		doxiciclina.setDosagem("Falta inserir");
		doxiciclina.setApresentacao("Exemplo genérico");
		
		Indicacao eritromicina = new Indicacao();
		eritromicina.setNome("Eritromicina");
		eritromicina.setCodIndicacao("eritrom");
		eritromicina.setDosagem("Falta inserir");
		eritromicina.setApresentacao("Exemplo genérico");
		
		Indicacao ertapenem = new Indicacao();
		ertapenem.setNome("Ertapenem");
		ertapenem.setCodIndicacao("ertap");
		ertapenem.setDosagem("Falta inserir");
		ertapenem.setApresentacao("Exemplo genérico");

		Indicacao espectinomicina = new Indicacao();
		espectinomicina.setNome("Espectinomicina");
		espectinomicina.setCodIndicacao("espectin");
		espectinomicina.setDosagem("Falta inserir");
		espectinomicina.setApresentacao("Exemplo genérico");

		Indicacao espiramicina = new Indicacao();
		espiramicina.setNome("Espiramicina");
		espiramicina.setCodIndicacao("espira");
		espiramicina.setDosagem("Falta inserir");
		espiramicina.setApresentacao("Exemplo genérico");

		Indicacao estreptomicina = new Indicacao();
		estreptomicina.setNome("Estreptomicina");
		estreptomicina.setCodIndicacao("estrept");
		estreptomicina.setDosagem("Falta inserir");
		estreptomicina.setApresentacao("Exemplo genérico");
		
		Indicacao etambutol = new Indicacao();
		etambutol.setNome("Etambutol");
		etambutol.setCodIndicacao("etambutol");
		etambutol.setDosagem("Falta inserir");
		etambutol.setApresentacao("Exemplo genérico");

		// 51 a 60
		Indicacao etionamida = new Indicacao();
		etionamida.setNome("Etionamida");
		etionamida.setCodIndicacao("etion");
		etionamida.setDosagem("Falta inserir");
		etionamida.setApresentacao("Exemplo genérico");

		Indicacao fosfomicina = new Indicacao();
		fosfomicina.setNome("Fosfomicina");
		fosfomicina.setCodIndicacao("fosfomi");
		fosfomicina.setDosagem("Falta inserir");
		fosfomicina.setApresentacao("Exemplo genérico");

		Indicacao ftalilsulfatiazol = new Indicacao();
		ftalilsulfatiazol.setNome("Ftalilsulfatiazol");
		ftalilsulfatiazol.setCodIndicacao("ftalilsu");
		ftalilsulfatiazol.setDosagem("Falta inserir");
		ftalilsulfatiazol.setApresentacao("Exemplo genérico");

		Indicacao gatifolacina = new Indicacao();
		gatifolacina.setNome("Gatifolacina");
		gatifolacina.setCodIndicacao("gatifol");
		gatifolacina.setDosagem("Falta inserir");
		gatifolacina.setApresentacao("Exemplo genérico");
		
		Indicacao gemifloxacino = new Indicacao();
		gemifloxacino.setNome("Gemifloxacino");
		gemifloxacino.setCodIndicacao("gemiflox");
		gemifloxacino.setDosagem("Falta inserir");
		gemifloxacino.setApresentacao("Exemplo genérico");
		
		Indicacao gentamicina = new Indicacao();
		gentamicina.setNome("Gentamicina");
		gentamicina.setCodIndicacao("gentam");
		gentamicina.setDosagem("Falta inserir");
		gentamicina.setApresentacao("Exemplo genérico");

		Indicacao imipenem = new Indicacao();
		imipenem.setNome("Imipenem");
		imipenem.setCodIndicacao("imipenem");
		imipenem.setDosagem("Falta inserir");
		imipenem.setApresentacao("Exemplo genérico");

		Indicacao isoniazida = new Indicacao();
		isoniazida.setNome("Isoniazida");
		isoniazida.setCodIndicacao("isoni");
		isoniazida.setDosagem("Falta inserir");
		isoniazida.setApresentacao("Exemplo genérico");

		Indicacao linezolida = new Indicacao();
		linezolida.setNome("Linezolida");
		linezolida.setCodIndicacao("linez");
		linezolida.setDosagem("Falta inserir");
		linezolida.setApresentacao("Exemplo genérico");
		
		Indicacao limeciclina = new Indicacao();
		limeciclina.setNome("Limeciclina");
		limeciclina.setCodIndicacao("limec");
		limeciclina.setDosagem("Falta inserir");
		limeciclina.setApresentacao("Exemplo genérico");
		
		// 61 a 70
		Indicacao lincomicina = new Indicacao();
		lincomicina.setNome("Lincomicina");
		lincomicina.setCodIndicacao("lincom");
		lincomicina.setDosagem("Falta inserir");
		lincomicina.setApresentacao("Exemplo genérico");

		Indicacao lomefloxacina = new Indicacao();
		lomefloxacina.setNome("Lomefloxacina");
		lomefloxacina.setCodIndicacao("lomeflo");
		lomefloxacina.setDosagem("Falta inserir");
		lomefloxacina.setApresentacao("Exemplo genérico");

		Indicacao loracarbef = new Indicacao();
		loracarbef.setNome("Loracarbef");
		loracarbef.setCodIndicacao("loracarbef");
		loracarbef.setDosagem("Falta inserir");
		loracarbef.setApresentacao("Exemplo genérico");

		Indicacao mandelamina = new Indicacao();
		mandelamina.setNome("Mandelamina");
		mandelamina.setCodIndicacao("mandel");
		mandelamina.setDosagem("Falta inserir");
		mandelamina.setApresentacao("Exemplo genérico");
		
		Indicacao meropenem = new Indicacao();
		meropenem.setNome("Meropenem");
		meropenem.setCodIndicacao("merop");
		meropenem.setDosagem("Falta inserir");
		meropenem.setApresentacao("Exemplo genérico");
		
		Indicacao metampicilina = new Indicacao();
		metampicilina.setNome("Metampicilina");
		metampicilina.setCodIndicacao("metamp");
		metampicilina.setDosagem("Falta inserir");
		metampicilina.setApresentacao("Exemplo genérico");

		Indicacao metronidazol = new Indicacao();
		metronidazol.setNome("Metronidazol");
		metronidazol.setCodIndicacao("metron");
		metronidazol.setDosagem("Falta inserir");
		metronidazol.setApresentacao("Exemplo genérico");

		Indicacao minociclina = new Indicacao();
		minociclina.setNome("Minociclina");
		minociclina.setCodIndicacao("minoci");
		minociclina.setDosagem("Falta inserir");
		minociclina.setApresentacao("Exemplo genérico");

		Indicacao miocamicina = new Indicacao();
		miocamicina.setNome("Miocamicina");
		miocamicina.setCodIndicacao("mioca");
		miocamicina.setDosagem("Falta inserir");
		miocamicina.setApresentacao("Exemplo genérico");
		
		Indicacao moxifloxacino = new Indicacao();
		moxifloxacino.setNome("Moxifloxacino");
		moxifloxacino.setCodIndicacao("moxif");
		moxifloxacino.setDosagem("Falta inserir");
		moxifloxacino.setApresentacao("Exemplo genérico");

		// 71 a 80
		Indicacao mupirocina = new Indicacao();
		mupirocina.setNome("Mupirocina");
		mupirocina.setCodIndicacao("mupir");
		mupirocina.setDosagem("Falta inserir");
		mupirocina.setApresentacao("Exemplo genérico");

		Indicacao neomicina = new Indicacao();
		neomicina.setNome("Neomicina");
		neomicina.setCodIndicacao("neomi");
		neomicina.setDosagem("Falta inserir");
		neomicina.setApresentacao("Exemplo genérico");

		Indicacao netilmicina = new Indicacao();
		netilmicina.setNome("Netilmicina");
		netilmicina.setCodIndicacao("netil");
		netilmicina.setDosagem("Falta inserir");
		netilmicina.setApresentacao("Exemplo genérico");

		Indicacao nitrofurantoina = new Indicacao();
		nitrofurantoina.setNome("Nitrofurantoina");
		nitrofurantoina.setCodIndicacao("nitrofur");
		nitrofurantoina.setDosagem("Falta inserir");
		nitrofurantoina.setApresentacao("Exemplo genérico");
		
		Indicacao nitroxolina = new Indicacao();
		nitroxolina.setNome("Nitroxolina");
		nitroxolina.setCodIndicacao("nitro");
		nitroxolina.setDosagem("Falta inserir");
		nitroxolina.setApresentacao("Exemplo genérico");
		
		Indicacao norfloxacina = new Indicacao();
		norfloxacina.setNome("Norfloxacina");
		norfloxacina.setCodIndicacao("norfl");
		norfloxacina.setDosagem("Falta inserir");
		norfloxacina.setApresentacao("Exemplo genérico");

		Indicacao ofloxacina = new Indicacao();
		ofloxacina.setNome("Ofloxacina");
		ofloxacina.setCodIndicacao("oflox");
		ofloxacina.setDosagem("Falta inserir");
		ofloxacina.setApresentacao("Exemplo genérico");

		Indicacao oxacilina = new Indicacao();
		oxacilina.setNome("Oxacilina");
		oxacilina.setCodIndicacao("oxacilina");
		oxacilina.setDosagem("Falta inserir");
		oxacilina.setApresentacao("Exemplo genérico");

		Indicacao oxitetraciclina = new Indicacao();
		oxitetraciclina.setNome("Oxitetraciclina");
		oxitetraciclina.setCodIndicacao("oxitet");
		oxitetraciclina.setDosagem("Falta inserir");
		oxitetraciclina.setApresentacao("Exemplo genérico");
		
		Indicacao pefloxacina = new Indicacao();
		pefloxacina.setNome("Pefloxacina");
		pefloxacina.setCodIndicacao("peflox");
		pefloxacina.setDosagem("Falta inserir");
		pefloxacina.setApresentacao("Exemplo genérico");

		// 81 a 90
		Indicacao penicilinaG = new Indicacao();
		penicilinaG.setNome("Penicilina G");
		penicilinaG.setCodIndicacao("peniciG");
		penicilinaG.setDosagem("Falta inserir");
		penicilinaG.setApresentacao("Exemplo genérico");

		Indicacao penicilinaV = new Indicacao();
		penicilinaV.setNome("Penicilina V");
		penicilinaV.setCodIndicacao("peniciV");
		penicilinaV.setDosagem("Falta inserir");
		penicilinaV.setApresentacao("Exemplo genérico");

		Indicacao piperacilina = new Indicacao();
		piperacilina.setNome("Piperacilina");
		piperacilina.setCodIndicacao("piperac");
		piperacilina.setDosagem("Falta inserir");
		piperacilina.setApresentacao("Exemplo genérico");

		Indicacao pirazinamida = new Indicacao();
		pirazinamida.setNome("Pirazinamida");
		pirazinamida.setCodIndicacao("pirazi");
		pirazinamida.setDosagem("Falta inserir");
		pirazinamida.setApresentacao("Exemplo genérico");
		
		Indicacao polimixinaB = new Indicacao();
		polimixinaB.setNome("Polimixina B");
		polimixinaB.setCodIndicacao("polimixB");
		polimixinaB.setDosagem("Falta inserir");
		polimixinaB.setApresentacao("Exemplo genérico");
		
		Indicacao pristinamicina = new Indicacao();
		pristinamicina.setNome("Pristinamicina");
		pristinamicina.setCodIndicacao("pristin");
		pristinamicina.setDosagem("Falta inserir");
		pristinamicina.setApresentacao("Exemplo genérico");

		Indicacao protionamida = new Indicacao();
		protionamida.setNome("Protionamida");
		protionamida.setCodIndicacao("protion");
		protionamida.setDosagem("Falta inserir");
		protionamida.setApresentacao("Exemplo genérico");

		Indicacao retapamulina = new Indicacao();
		retapamulina.setNome("Retapamulina");
		retapamulina.setCodIndicacao("retapam");
		retapamulina.setDosagem("Falta inserir");
		retapamulina.setApresentacao("Exemplo genérico");

		Indicacao rifamicina = new Indicacao();
		rifamicina.setNome("Rifamicina");
		rifamicina.setCodIndicacao("rifam");
		rifamicina.setDosagem("Falta inserir");
		rifamicina.setApresentacao("Exemplo genérico");
		
		Indicacao rifampicina = new Indicacao();
		rifampicina.setNome("Rifampicina");
		rifampicina.setCodIndicacao("rifamp");
		rifampicina.setDosagem("Falta inserir");
		rifampicina.setApresentacao("Exemplo genérico");

		// 91 a 100
		Indicacao rifapetina = new Indicacao();
		rifapetina.setNome("Rifapetina");
		rifapetina.setCodIndicacao("rifapet");
		rifapetina.setDosagem("Falta inserir");
		rifapetina.setApresentacao("Exemplo genérico");

		Indicacao rosoxacina = new Indicacao();
		rosoxacina.setNome("Rosoxacina");
		rosoxacina.setCodIndicacao("rosox");
		rosoxacina.setDosagem("Falta inserir");
		rosoxacina.setApresentacao("Exemplo genérico");

		Indicacao roxitromicina = new Indicacao();
		roxitromicina.setNome("Roxitromicina");
		roxitromicina.setCodIndicacao("roxitro");
		roxitromicina.setDosagem("Falta inserir");
		roxitromicina.setApresentacao("Exemplo genérico");

		Indicacao sulbactam = new Indicacao();
		sulbactam.setNome("Sulbactam");
		sulbactam.setCodIndicacao("sulbactam");
		sulbactam.setDosagem("Falta inserir");
		sulbactam.setApresentacao("Exemplo genérico");
		
		Indicacao sulfadiazina = new Indicacao();
		sulfadiazina.setNome("Sulfadiazina");
		sulfadiazina.setCodIndicacao("sulfadiaz");
		sulfadiazina.setDosagem("Falta inserir");
		sulfadiazina.setApresentacao("Exemplo genérico");
		
		Indicacao sulfadoxina = new Indicacao();
		sulfadoxina.setNome("Sulfadoxina");
		sulfadoxina.setCodIndicacao("sulfadox");
		sulfadoxina.setDosagem("Falta inserir");
		sulfadoxina.setApresentacao("Exemplo genérico");

		Indicacao sulfaguanidina = new Indicacao();
		sulfaguanidina.setNome("Sulfaguanidina");
		sulfaguanidina.setCodIndicacao("sulfagu");
		sulfaguanidina.setDosagem("Falta inserir");
		sulfaguanidina.setApresentacao("Exemplo genérico");

		Indicacao sulfamerazina = new Indicacao();
		sulfamerazina.setNome("Sulfamerazina");
		sulfamerazina.setCodIndicacao("sulfameraz");
		sulfamerazina.setDosagem("Falta inserir");
		sulfamerazina.setApresentacao("Exemplo genérico");

		Indicacao sulfanilamida = new Indicacao();
		sulfanilamida.setNome("Sulfanilamida");
		sulfanilamida.setCodIndicacao("sulfanilam");
		sulfanilamida.setDosagem("Falta inserir");
		sulfanilamida.setApresentacao("Exemplo genérico");
		
		Indicacao sulfametizol = new Indicacao();
		sulfametizol.setNome("Sulfametizol");
		sulfametizol.setCodIndicacao("sulfameti");
		sulfametizol.setDosagem("Falta inserir");
		sulfametizol.setApresentacao("Exemplo genérico");

		// 91 a 100
		Indicacao sulfametoxazol = new Indicacao();
		sulfametoxazol.setNome("Sulfametoxazol");
		sulfametoxazol.setCodIndicacao("sulfametox");
		sulfametoxazol.setDosagem("Falta inserir");
		sulfametoxazol.setApresentacao("Exemplo genérico");

		Indicacao sulfametoxipiridazina = new Indicacao();
		sulfametoxipiridazina.setNome("Sulfametoxipiridazina");
		sulfametoxipiridazina.setCodIndicacao("sulfametoxi");
		sulfametoxipiridazina.setDosagem("Falta inserir");
		sulfametoxipiridazina.setApresentacao("Exemplo genérico");

		Indicacao sulfametoxipirimidina = new Indicacao();
		sulfametoxipirimidina.setNome("Sulfametoxipirimidina");
		sulfametoxipirimidina.setCodIndicacao("sulfametoxip");
		sulfametoxipirimidina.setDosagem("Falta inserir");
		sulfametoxipirimidina.setApresentacao("Exemplo genérico");

		Indicacao sulfatiazol = new Indicacao();
		sulfatiazol.setNome("Sulfatiazol");
		sulfatiazol.setCodIndicacao("sulfatia");
		sulfatiazol.setDosagem("Falta inserir");
		sulfatiazol.setApresentacao("Exemplo genérico");
		
		Indicacao sultamicilina = new Indicacao();
		sultamicilina.setNome("Sultamicilina");
		sultamicilina.setCodIndicacao("sultamic");
		sultamicilina.setDosagem("Falta inserir");
		sultamicilina.setApresentacao("Exemplo genérico");
		
		Indicacao tazobactam = new Indicacao();
		tazobactam.setNome("Tazobactam");
		tazobactam.setCodIndicacao("tazobac");
		tazobactam.setDosagem("Falta inserir");
		tazobactam.setApresentacao("Exemplo genérico");

		Indicacao teicoplanina = new Indicacao();
		teicoplanina.setNome("Teicoplanina");
		teicoplanina.setCodIndicacao("teicoplan");
		teicoplanina.setDosagem("Falta inserir");
		teicoplanina.setApresentacao("Exemplo genérico");

		Indicacao telitromicina = new Indicacao();
		telitromicina.setNome("Telitromicina");
		telitromicina.setCodIndicacao("telitrom");
		telitromicina.setDosagem("Falta inserir");
		telitromicina.setApresentacao("Exemplo genérico");

		Indicacao tetraciclina = new Indicacao();
		tetraciclina.setNome("Tetraciclina");
		tetraciclina.setCodIndicacao("tetrac");
		tetraciclina.setDosagem("Falta inserir");
		tetraciclina.setApresentacao("Exemplo genérico");
		
		Indicacao tianfenicol = new Indicacao();
		tianfenicol.setNome("Tianfenicol");
		tianfenicol.setCodIndicacao("tianfen");
		tianfenicol.setDosagem("Falta inserir");
		tianfenicol.setApresentacao("Exemplo genérico");

		// 91 a 100
		Indicacao ticarcilina = new Indicacao();
		ticarcilina.setNome("Ticarcilina");
		ticarcilina.setCodIndicacao("ticarc");
		ticarcilina.setDosagem("Falta inserir");
		ticarcilina.setApresentacao("Exemplo genérico");

		Indicacao tigeciclina = new Indicacao();
		tigeciclina.setNome("Tigeciclina");
		tigeciclina.setCodIndicacao("tigec");
		tigeciclina.setDosagem("Falta inserir");
		tigeciclina.setApresentacao("Exemplo genérico");

		Indicacao tirotricina = new Indicacao();
		tirotricina.setNome("Tirotricina");
		tirotricina.setCodIndicacao("tirotr");
		tirotricina.setDosagem("Falta inserir");
		tirotricina.setApresentacao("Exemplo genérico");

		Indicacao tobramicina = new Indicacao();
		tobramicina.setNome("Tobramicina");
		tobramicina.setCodIndicacao("tobram");
		tobramicina.setDosagem("Falta inserir");
		tobramicina.setApresentacao("Exemplo genérico");
		
		Indicacao trimetoprina = new Indicacao();
		trimetoprina.setNome("Trimetoprina");
		trimetoprina.setCodIndicacao("trimeto");
		trimetoprina.setDosagem("Falta inserir");
		trimetoprina.setApresentacao("Exemplo genérico");
		
		Indicacao trovafloxacina = new Indicacao();
		trovafloxacina.setNome("Trovafloxacina");
		trovafloxacina.setCodIndicacao("trovaflox");
		trovafloxacina.setDosagem("Falta inserir");
		trovafloxacina.setApresentacao("Exemplo genérico");

		Indicacao vancomicina = new Indicacao();
		vancomicina.setNome("Vancomicina");
		vancomicina.setCodIndicacao("vancomic");
		vancomicina.setDosagem("Falta inserir");
		vancomicina.setApresentacao("Exemplo genérico");
	
		
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
