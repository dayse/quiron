package carga;

import java.util.ArrayList;
import java.util.List;

import modelo.Parametro;
import service.ParametroAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;

/**
 * 
 * Sobre a Carga: É uma Carga do sistema, portanto deve herdar de CargaBase e
 * implementar o método executar(). Nesse método "executar" é que é chamado
 * pelos outros métodos que são as etapas dessa carga. Portanto se é necessario
 * rodar um método depois do outro, eles devem ser chamados na ordem correta.
 * Ex: incluiPaciente() vem antes de incluiAtendimento(), portanto no método executar()
 * 
 * Terminado de executar todas as etapas é preciso retornar true. Se houver
 * algum problema(exceção) na execução de uma das etapas, essa exceção deve ser
 * lancada.
 * 
 * Essa Carga: Classe responsável pela inclusão dos tipos de parâmetros intermediários.
 * São os dados mais atualizados fornecidos pelo Pedro Peloso.
 * 
 * @author bruno.oliveira
 * 
 */
public class CargaParametroIntermediario extends CargaBase {
	
	// Service
	public ParametroAppService parametroService;
	
	/**
	 * 
	 * Construdor da carga, responsável por instanciar os services.
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
		this.incluirParametroIntermediario();
		return true;
	}

	/**
	 * 
	 * Método responsável por preparar e inserir os valores padrões dos
	 * parâmetros no banco.
	 * 
	 * @throws AplicacaoException
	 *             Retorna uma AplicacaoException caso ocorra uma exceção deste
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
		klebsiella.setDescricao("Ocorrência de etiologia do tipo Klebsiella Pneumoniae");
		klebsiella.setPeso(1.0);
			parametros.add(klebsiella);
		
		proteus.setCodParametro("P010");
		proteus.setNome("Proteus Mirabilis");
		proteus.setDescricao("Ocorrência de etiologia do tipo Proteus Mirabilis");
		proteus.setPeso(1.0);
			parametros.add(proteus);
		
		enterobacter.setCodParametro("P011");
		enterobacter.setNome("Enterobacter Aerogenes");
		enterobacter.setDescricao("Ocorrência de etiologia do tipo Enterobacter Aerogenes");
		enterobacter.setPeso(1.0);
			parametros.add(enterobacter);
		
		gravidez.setCodParametro("P012");
		gravidez.setNome("Gravidez");
		gravidez.setDescricao("Paciente encontra-se grávida");
		gravidez.setPeso(1.0);
			parametros.add(gravidez);
		
		polaciuria.setCodParametro("P013");
		polaciuria.setNome("Polaciúria");
		polaciuria.setDescricao("Ocorrêna de diminuição significativa da quantidade de urina excretada");
		polaciuria.setPeso(1.0);
			parametros.add(polaciuria);
		
		desconfortoAbdominal.setCodParametro("P014");
		desconfortoAbdominal.setNome("Desconforto abdominal");
		desconfortoAbdominal.setDescricao("Ocorrência de desconforto na região abdominal");
		desconfortoAbdominal.setPeso(1.0);
			parametros.add(desconfortoAbdominal);
		
		infeccaoUrinariaAnterior.setCodParametro("P015");
		infeccaoUrinariaAnterior.setNome("Infecção Urinária Anterior");
		infeccaoUrinariaAnterior.setDescricao("Histórico de ocorrência de infecção urinária anterior");
		infeccaoUrinariaAnterior.setPeso(1.0);
			parametros.add(infeccaoUrinariaAnterior);
		
		usoDeAntibiotico.setCodParametro("P016");
		usoDeAntibiotico.setNome("Uso de Antibiótico");
		usoDeAntibiotico.setDescricao("Verificação do uso atual de algum antibiotico por parte do usuário. Seja por automedicação ou precição anterior");
		usoDeAntibiotico.setPeso(1.0);
			parametros.add(usoDeAntibiotico);
			
		pseudomonas.setCodParametro("P017");
		pseudomonas.setNome("Pseudomonas Aeruginosa");
		pseudomonas.setDescricao("Ocorrência de etiologia do tipo Pseudomonas Aeruginosa.");
		pseudomonas.setPeso(1.0);
			parametros.add(pseudomonas);
			
		staphylococcus.setCodParametro("P018");
		staphylococcus.setNome("Staphylococcus Saprophyticus");
		staphylococcus.setDescricao("Ocorrência de etiologia do tipo Staphylococcus Saprophyticus.");
		staphylococcus.setPeso(1.0);
			parametros.add(staphylococcus);
			
		streptoccus.setCodParametro("P019");
		streptoccus.setNome("Streptococcus Agalactiae");
		streptoccus.setDescricao("Ocorrência de etiologia do tipo Streptococcus Agalactiae (Grupo B).");
		streptoccus.setPeso(1.0);
			parametros.add(streptoccus);
			
		citrobacter.setCodParametro("P020");
		citrobacter.setNome("Citrobacter Koseri");
		citrobacter.setDescricao("Ocorrência de etiologia do tipo Citrobacter Koseri.");
		citrobacter.setPeso(1.0);
			parametros.add(citrobacter);
			
		staphylococcusEp.setCodParametro("P021");
		staphylococcusEp.setNome("Staphylococcus Epidermidis");
		staphylococcusEp.setDescricao("Ocorrência de etiologia do tipo Staphylococcus Epidermidis.");
		staphylococcusEp.setPeso(1.0);
			parametros.add(staphylococcusEp);
			
		enterobacterClo.setCodParametro("P022");
		enterobacterClo.setNome("Enterobacter Cloacae");
		enterobacterClo.setDescricao("Ocorrência de etiologia do tipo Enterobacter Cloacae.");
		enterobacterClo.setPeso(1.0);
			parametros.add(enterobacterClo);
		
		citrobacterFre.setCodParametro("P023");
		citrobacterFre.setNome("Citrobacter  Freundili Complex");
		citrobacterFre.setDescricao("Ocorrência de etiologia do tipo Citrobacter Freundili Complex.");
		citrobacterFre.setPeso(1.0);
			parametros.add(citrobacterFre);

		morganella.setCodParametro("P024");
		morganella.setNome("Morganella Morganii");
		morganella.setDescricao("Ocorrência de etiologia do tipo Morganella Morganii.");
		morganella.setPeso(1.0);
			parametros.add(morganella);
			
		staphylococcusAur.setCodParametro("P025");
		staphylococcusAur.setNome("Staphylococcus Aureus");
		staphylococcusAur.setDescricao("Ocorrência de etiologia do tipo Staphylococcus Aures.");
		staphylococcusAur.setPeso(1.0);
			parametros.add(staphylococcusAur);

		serratia.setCodParametro("P026");
		serratia.setNome("Serratia Marcescens");
		serratia.setDescricao("Ocorrência de etiologia do tipo Serratia Marcescens.");
		serratia.setPeso(1.0);
			parametros.add(serratia);	
			
		for(Parametro parametro : parametros){
			parametroService.inclui(parametro);
		}			
	}

}
