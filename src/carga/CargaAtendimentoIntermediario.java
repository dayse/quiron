package carga;

import java.util.ArrayList;
import java.util.List;

import modelo.Anamnese;
import modelo.Atendimento;
import modelo.Parametro;
import service.AnamneseAppService;
import service.AtendimentoAppService;
import service.PacienteAppService;
import service.ParametroAppService;
import service.UsuarioAppService;
import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;

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
 * Responsavel por fazer a carga dos atendimentos contidas nos
 * arquivos de modelagens e dados iniciais do sistema
 * 
 * @author bruno.oliveira
 *
 */
public class CargaAtendimentoIntermediario extends CargaBase {

	// Services
	private static PacienteAppService pacienteService;
	private static AtendimentoAppService atendimentoService;
	private static ParametroAppService parametroService;
	private static AnamneseAppService anamneseService;
	private static UsuarioAppService usuarioService;	
	
	/**
	 * 
	 * Construdor da carga, responsável por instanciar os services.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public CargaAtendimentoIntermediario(){
		try {
			pacienteService = FabricaDeAppService.getAppService(PacienteAppService.class);
			atendimentoService = FabricaDeAppService.getAppService(AtendimentoAppService.class);
			parametroService = FabricaDeAppService.getAppService(ParametroAppService.class);
			anamneseService = FabricaDeAppService
					.getAppService(AnamneseAppService.class);
			usuarioService = FabricaDeAppService
					.getAppService(UsuarioAppService.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public List<CargaBase> getCargasDependentes() {
		List<CargaBase> dependencias = new ArrayList<CargaBase>();
		dependencias.add(new CargaIndicacaoIntermediaria());
		dependencias.add(new CargaParametroIntermediario());
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
		this.incluirAtendimentosIntermediarios();
		return true;
	}
	
	/**
	 * Metodo responsável por preparar e inserir os valores padrões de
	 * Atendimentos no banco de dados
	 * 
	 * @author bruno.oliveira
	 * @throws AplicacaoException
	 */
	public void incluirAtendimentosIntermediarios() throws AplicacaoException {
		Atendimento atendimentoPaciente1 = atendimentoService.recuperaAtendimentoPorCodigo("atp1");
		Atendimento atendimentoPaciente2 = atendimentoService.recuperaAtendimentoPorCodigo("atp2");
		
		Parametro klebsiella = parametroService.recuperaParametroPorCodigo("P009");
		Parametro proteus = parametroService.recuperaParametroPorCodigo("P010");
		Parametro enterobacter = parametroService.recuperaParametroPorCodigo("P011");
		
		Anamnese atendimentoPc1_klebsiella = new Anamnese(atendimentoPaciente1, klebsiella, 0.0);
		Anamnese atendimentoPc1_proteus = new Anamnese(atendimentoPaciente1, proteus, 0.0);
		Anamnese atendimentoPc1_enterobacter = new Anamnese(atendimentoPaciente1, enterobacter, 0.0);
		
		Anamnese atendimentoPc2_klebsiella = new Anamnese(atendimentoPaciente2, klebsiella, 0.0);
		Anamnese atendimentoPc2_proteus = new Anamnese(atendimentoPaciente2, proteus, 0.0);
		Anamnese atendimentoPc2_enterobacter = new Anamnese(atendimentoPaciente2, enterobacter, 0.0);
		
		anamneseService.inclui(atendimentoPc1_klebsiella);
		anamneseService.inclui(atendimentoPc1_proteus);
		anamneseService.inclui(atendimentoPc1_enterobacter);
		anamneseService.inclui(atendimentoPc2_klebsiella);
		anamneseService.inclui(atendimentoPc2_proteus);
		anamneseService.inclui(atendimentoPc2_enterobacter);
	}

}
