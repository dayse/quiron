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
 * Ex: incluiHP() vem antes de inicializaHP(), portanto no método executar()
 * eles devem ser chamados nessa ordem.
 * 
 * Terminado de executar todas as etapas é preciso retornar true. Se houver
 * algum problema(exceção) na execução de uma das etapas, essa exceção deve ser
 * lancada.
 * 
 * Essa Carga: Classe responsável pela inclusão dos tipos de parâmetros. 
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
		klebsiella.setDescricao("Ocorrência de etiologia do tipo Klebsiella Pneumoniae");
		klebsiella.setPeso(1.0);
		
		proteus.setCodParametro("P010");
		proteus.setNome("Proteus Mirabilis");
		proteus.setDescricao("Ocorrência de etiologia do tipo Proteus Mirabilis");
		proteus.setPeso(1.0);
		
		enterobacter.setCodParametro("P011");
		enterobacter.setNome("Enterobacter Aerogenes");
		enterobacter.setDescricao("Ocorrência de etiologia do tipo Enterobacter Aerogenes");
		enterobacter.setPeso(1.0);
		
		gravidez.setCodParametro("P012");
		gravidez.setNome("Gravidez");
		gravidez.setDescricao("Paciente encontra-se grávida");
		gravidez.setPeso(1.0);
		
		polaciuria.setCodParametro("P013");
		polaciuria.setNome("Polaciúria");
		polaciuria.setDescricao("Ocorrêna de diminuição significativa da quantidade de urina excretada");
		polaciuria.setPeso(1.0);
		
		desconfortoAbdominal.setCodParametro("P014");
		desconfortoAbdominal.setNome("Desconforto abdominal");
		desconfortoAbdominal.setDescricao("Ocorrência de desconforto na região abdominal");
		desconfortoAbdominal.setPeso(1.0);
		
		infeccaoUrinariaAnterior.setCodParametro("P015");
		infeccaoUrinariaAnterior.setNome("Infecção Urinária Anterior");
		infeccaoUrinariaAnterior.setDescricao("Histórico de ocorrência de infecção urinária anterior");
		infeccaoUrinariaAnterior.setPeso(1.0);
		
		usoDeAntibiotico.setCodParametro("P016");
		usoDeAntibiotico.setNome("Uso de Antibiótico");
		usoDeAntibiotico.setDescricao("Verificação do uso atual de algum antibiotico por parte do usuário. Seja por automedicação ou precição anterior");
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
