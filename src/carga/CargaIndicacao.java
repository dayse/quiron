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
public class CargaIndicacao extends CargaBase{
  
	// Services
	private static IndicacaoAppService indicacaoService;
	
	/**
	 * 
	 * Construdor da carga, respons�vel por instanciar os services.
	 * 
	 * @author felipe.arruda
	 * 
	 */
	public CargaIndicacao(){
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
		Indicacao amox500 = new Indicacao();
		amox500.setNome("Amoxicilina");
		amox500.setCodIndicacao("amox500");
		amox500.setDosagem("12hrs x 3 dias");
		amox500.setApresentacao("500 mg");

		Indicacao bactrim = new Indicacao();
		bactrim.setNome("Bactrim");
		bactrim.setCodIndicacao("bactrim");
		bactrim.setDosagem("12hrs x 3 dias");
		bactrim.setApresentacao("500 mg");

		Indicacao amox1 = new Indicacao();
		amox1.setNome("Amoxicilina");
		amox1.setCodIndicacao("amox1");
		amox1.setDosagem("�nica");
		amox1.setApresentacao("1000 mg");

		Indicacao levoflox = new Indicacao();
		levoflox.setNome("Levofloxacina");
		levoflox.setCodIndicacao("levoflox");
		levoflox.setDosagem("24h x 3 dias");
		levoflox.setApresentacao("500 mg");
		
		Indicacao fluco = new Indicacao();
		fluco.setNome("Flucozanol");
		fluco.setCodIndicacao("fluco");
		fluco.setDosagem("�nica");
		fluco.setApresentacao("200 mg");

		indicacaoService.inclui(amox500);
		indicacaoService.inclui(bactrim);
		indicacaoService.inclui(amox1);
		indicacaoService.inclui(levoflox);
		indicacaoService.inclui(fluco);
		
		
	}
	

}
