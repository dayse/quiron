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
		
		parametroService.inclui(klebsiella);
		parametroService.inclui(proteus);
		parametroService.inclui(enterobacter);
	}

}
