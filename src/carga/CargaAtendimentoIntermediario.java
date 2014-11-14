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
	 * Construdor da carga, respons�vel por instanciar os services.
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
		this.incluirAtendimentosIntermediarios();
		return true;
	}
	
	/**
	 * Metodo respons�vel por preparar e inserir os valores padr�es de
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
