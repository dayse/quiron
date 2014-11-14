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
public class CargaIndicacao extends CargaBase{
  
	// Services
	private static IndicacaoAppService indicacaoService;
	
	/**
	 * 
	 * Construdor da carga, responsável por instanciar os services.
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
		amox1.setDosagem("Única");
		amox1.setApresentacao("1000 mg");

		Indicacao levoflox = new Indicacao();
		levoflox.setNome("Levofloxacina");
		levoflox.setCodIndicacao("levoflox");
		levoflox.setDosagem("24h x 3 dias");
		levoflox.setApresentacao("500 mg");
		
		Indicacao fluco = new Indicacao();
		fluco.setNome("Flucozanol");
		fluco.setCodIndicacao("fluco");
		fluco.setDosagem("Única");
		fluco.setApresentacao("200 mg");

		indicacaoService.inclui(amox500);
		indicacaoService.inclui(bactrim);
		indicacaoService.inclui(amox1);
		indicacaoService.inclui(levoflox);
		indicacaoService.inclui(fluco);
		
		
	}
	

}
