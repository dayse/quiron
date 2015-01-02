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
 * Ex: incluiPaciente() vem antes de incluiAtendimento(), portanto no m�todo executar()
 * 
 * Terminado de executar todas as etapas � preciso retornar true. Se houver
 * algum problema(exce��o) na execu��o de uma das etapas, essa exce��o deve ser
 * lancada.
 * 
 * Essa Carga: Classe respons�vel pela inclus�o dos tipos de par�metros intermedi�rios.
 * S�o os dados mais atualizados fornecidos pelo Pedro Peloso.
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

	/**
	 * 
	 * M�todo herdado de CargaBase e que retona uma lista de cargas que esta
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
		List<Parametro> parametros = new ArrayList<Parametro>();
		
		Parametro gravidez = new Parametro();
		Parametro polaciuria = new Parametro();
		Parametro desconfortoAbdominal = new Parametro();
		Parametro infeccaoUrinariaAnterior = new Parametro();
		Parametro usoDeAntibiotico = new Parametro();	
		
		Parametro klebsiella = new Parametro();
		Parametro proteus = new Parametro();
		Parametro enterobacter = new Parametro();
		
		Parametro pseudomonas = new Parametro();
		Parametro staphylococcus = new Parametro();
		Parametro streptoccus = new Parametro();
		Parametro citrobacter = new Parametro();
		Parametro staphylococcusEp = new Parametro();
		Parametro enterobacterClo = new Parametro();
		Parametro citrobacterFre = new Parametro();
		Parametro morganella = new Parametro();
		Parametro staphylococcusAur = new Parametro();
		Parametro serratia = new Parametro();		
		
		klebsiella.setCodParametro("P009");
		klebsiella.setNome("Klebsiella pneumoniae");
		klebsiella.setDescricao("Ocorr�ncia de etiologia do tipo Klebsiella Pneumoniae");
		klebsiella.setPeso(1.0);
			parametros.add(klebsiella);
		
		proteus.setCodParametro("P010");
		proteus.setNome("Proteus Mirabilis");
		proteus.setDescricao("Ocorr�ncia de etiologia do tipo Proteus Mirabilis");
		proteus.setPeso(1.0);
			parametros.add(proteus);
		
		enterobacter.setCodParametro("P011");
		enterobacter.setNome("Enterobacter Aerogenes");
		enterobacter.setDescricao("Ocorr�ncia de etiologia do tipo Enterobacter Aerogenes");
		enterobacter.setPeso(1.0);
			parametros.add(enterobacter);
		
		gravidez.setCodParametro("P012");
		gravidez.setNome("Gravidez");
		gravidez.setDescricao("Paciente encontra-se gr�vida");
		gravidez.setPeso(1.0);
			parametros.add(gravidez);
		
		polaciuria.setCodParametro("P013");
		polaciuria.setNome("Polaci�ria");
		polaciuria.setDescricao("Ocorr�na de diminui��o significativa da quantidade de urina excretada");
		polaciuria.setPeso(1.0);
			parametros.add(polaciuria);
		
		desconfortoAbdominal.setCodParametro("P014");
		desconfortoAbdominal.setNome("Desconforto abdominal");
		desconfortoAbdominal.setDescricao("Ocorr�ncia de desconforto na regi�o abdominal");
		desconfortoAbdominal.setPeso(1.0);
			parametros.add(desconfortoAbdominal);
		
		infeccaoUrinariaAnterior.setCodParametro("P015");
		infeccaoUrinariaAnterior.setNome("Infec��o Urin�ria Anterior");
		infeccaoUrinariaAnterior.setDescricao("Hist�rico de ocorr�ncia de infec��o urin�ria anterior");
		infeccaoUrinariaAnterior.setPeso(1.0);
			parametros.add(infeccaoUrinariaAnterior);
		
		usoDeAntibiotico.setCodParametro("P016");
		usoDeAntibiotico.setNome("Uso de Antibi�tico");
		usoDeAntibiotico.setDescricao("Verifica��o do uso atual de algum antibiotico por parte do usu�rio. Seja por automedica��o ou preci��o anterior");
		usoDeAntibiotico.setPeso(1.0);
			parametros.add(usoDeAntibiotico);
			
		pseudomonas.setCodParametro("P017");
		pseudomonas.setNome("Pseudomonas Aeruginosa");
		pseudomonas.setDescricao("Ocorr�ncia de etiologia do tipo Pseudomonas Aeruginosa.");
		pseudomonas.setPeso(1.0);
			parametros.add(pseudomonas);
			
		staphylococcus.setCodParametro("P018");
		staphylococcus.setNome("Staphylococcus Saprophyticus");
		staphylococcus.setDescricao("Ocorr�ncia de etiologia do tipo Staphylococcus Saprophyticus.");
		staphylococcus.setPeso(1.0);
			parametros.add(staphylococcus);
			
		streptoccus.setCodParametro("P019");
		streptoccus.setNome("Streptococcus Agalactiae");
		streptoccus.setDescricao("Ocorr�ncia de etiologia do tipo Streptococcus Agalactiae (Grupo B).");
		streptoccus.setPeso(1.0);
			parametros.add(streptoccus);
			
		citrobacter.setCodParametro("P020");
		citrobacter.setNome("Citrobacter Koseri");
		citrobacter.setDescricao("Ocorr�ncia de etiologia do tipo Citrobacter Koseri.");
		citrobacter.setPeso(1.0);
			parametros.add(citrobacter);
			
		staphylococcusEp.setCodParametro("P021");
		staphylococcusEp.setNome("Staphylococcus Epidermidis");
		staphylococcusEp.setDescricao("Ocorr�ncia de etiologia do tipo Staphylococcus Epidermidis.");
		staphylococcusEp.setPeso(1.0);
			parametros.add(staphylococcusEp);
			
		enterobacterClo.setCodParametro("P022");
		enterobacterClo.setNome("Enterobacter Cloacae");
		enterobacterClo.setDescricao("Ocorr�ncia de etiologia do tipo Enterobacter Cloacae.");
		enterobacterClo.setPeso(1.0);
			parametros.add(enterobacterClo);
		
		citrobacterFre.setCodParametro("P023");
		citrobacterFre.setNome("Citrobacter  Freundili Complex");
		citrobacterFre.setDescricao("Ocorr�ncia de etiologia do tipo Citrobacter Freundili Complex.");
		citrobacterFre.setPeso(1.0);
			parametros.add(citrobacterFre);

		morganella.setCodParametro("P024");
		morganella.setNome("Morganella Morganii");
		morganella.setDescricao("Ocorr�ncia de etiologia do tipo Morganella Morganii.");
		morganella.setPeso(1.0);
			parametros.add(morganella);
			
		staphylococcusAur.setCodParametro("P025");
		staphylococcusAur.setNome("Staphylococcus Aureus");
		staphylococcusAur.setDescricao("Ocorr�ncia de etiologia do tipo Staphylococcus Aures.");
		staphylococcusAur.setPeso(1.0);
			parametros.add(staphylococcusAur);

		serratia.setCodParametro("P026");
		serratia.setNome("Serratia Marcescens");
		serratia.setDescricao("Ocorr�ncia de etiologia do tipo Serratia Marcescens.");
		serratia.setPeso(1.0);
			parametros.add(serratia);	
			
		for(Parametro parametro : parametros){
			parametroService.inclui(parametro);
		}			
	}

}
