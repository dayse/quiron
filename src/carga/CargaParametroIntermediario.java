package carga;

import java.util.ArrayList;
import java.util.List;

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
 * Essa Carga: Classe respons�vel pela inclus�o dos tipos de par�metros. 
 * 
 * @author bruno.oliveira
 * 
 */
public class CargaParametroIntermediario extends CargaBase {
	
	// Service
	public ParametroAppService parametroService;
	
	/**
	 * 
	 * Construdor da carga, respons�vel por instanciar os services.
	 * 
	 * @author bruno.oliveira
	 *
	 */	
	public CargaParametroIntermediario(){
		try{
			parametroService = FabricaDeAppService.getAppService(ParametroAppService.class);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List<CargaBase> getCargasDependentes() {
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
		this.incluirParametroIntermediario();
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
	public void incluirParametroIntermediario() throws AplicacaoException {
		Parametro klebsiella = new Parametro();
		Parametro proteus = new Parametro();
		Parametro enterobacter = new Parametro();
		
		Parametro gravidez = new Parametro();
		Parametro polaciuria = new Parametro();
		Parametro desconfortoAbdominal = new Parametro();
		Parametro infeccaoUrinariaAnterior = new Parametro();
		Parametro usoDeAntibiotico = new Parametro();
		
		klebsiella.setCodParametro("P009");
		klebsiella.setNome("Klebsiella pneumoniae");
		klebsiella.setDescricao("Ocorr�ncia de etiologia do tipo Klebsiella Pneumoniae");
		klebsiella.setPeso(1.0);
		
		proteus.setCodParametro("P010");
		proteus.setNome("Proteus Mirabilis");
		proteus.setDescricao("Ocorr�ncia de etiologia do tipo Proteus Mirabilis");
		proteus.setPeso(1.0);
		
		enterobacter.setCodParametro("P011");
		enterobacter.setNome("Enterobacter Aerogenes");
		enterobacter.setDescricao("Ocorr�ncia de etiologia do tipo Enterobacter Aerogenes");
		enterobacter.setPeso(1.0);
		
		gravidez.setCodParametro("P012");
		gravidez.setNome("Gravidez");
		gravidez.setDescricao("Paciente encontra-se gr�vida");
		gravidez.setPeso(1.0);
		
		polaciuria.setCodParametro("P013");
		polaciuria.setNome("Polaci�ria");
		polaciuria.setDescricao("Ocorr�na de diminui��o significativa da quantidade de urina excretada");
		polaciuria.setPeso(1.0);
		
		desconfortoAbdominal.setCodParametro("P014");
		desconfortoAbdominal.setNome("Desconforto abdominal");
		desconfortoAbdominal.setDescricao("Ocorr�ncia de desconforto na regi�o abdominal");
		desconfortoAbdominal.setPeso(1.0);
		
		infeccaoUrinariaAnterior.setCodParametro("P015");
		infeccaoUrinariaAnterior.setNome("Infec��o Urin�ria Anterior");
		infeccaoUrinariaAnterior.setDescricao("Hist�rico de ocorr�ncia de infec��o urin�ria anterior");
		infeccaoUrinariaAnterior.setPeso(1.0);
		
		usoDeAntibiotico.setCodParametro("P016");
		usoDeAntibiotico.setNome("Uso de Antibi�tico");
		usoDeAntibiotico.setDescricao("Verifica��o do uso atual de algum antibiotico por parte do usu�rio. Seja por automedica��o ou preci��o anterior");
		usoDeAntibiotico.setPeso(1.0);
		
		parametroService.inclui(klebsiella);
		parametroService.inclui(proteus);
		parametroService.inclui(enterobacter);
		parametroService.inclui(gravidez);
		parametroService.inclui(polaciuria);
		parametroService.inclui(desconfortoAbdominal);
		parametroService.inclui(infeccaoUrinariaAnterior);
		parametroService.inclui(usoDeAntibiotico);
	}

}
