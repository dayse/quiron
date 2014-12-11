package carga;

import java.util.ArrayList;
import java.util.List;

import modelo.Algoritmo;
import modelo.Parametro;
import service.AlgoritmoAppService;
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
 * Essa Carga: Classe respons�vel pela inclus�o dos tipos de par�metros. Esses
 * dados s�o utilizados apenas para carater ilustrativo, dando ao usu�rio melhor
 * compreens�o de quais par�metros ser�o utilizados para se chegar a uma
 * determinada avalia��o.
 * 
 * @author bruno.oliveira
 * 
 */
public class CargaAlgoritmo extends CargaBase {

	// Service
	public AlgoritmoAppService algoritmoService;

	/**
	 * 
	 * Construdor da carga, respons�vel por instanciar os services.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	public CargaAlgoritmo() {
		try {
			algoritmoService = FabricaDeAppService
					.getAppService(AlgoritmoAppService.class);
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
	 * @author bruno.oliveira
	 * 
	 */
	@Override
	public boolean executar() throws AplicacaoException {
		this.incluirAlgoritmo();
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
	public void incluirAlgoritmo() throws AplicacaoException {
		
		List<Algoritmo> configuracoes = new ArrayList<Algoritmo>();
		
		Algoritmo grauSemelhanca = new Algoritmo();
		Algoritmo grauInclusao = new Algoritmo();
		Algoritmo grauDistancia = new Algoritmo();
		
		
		grauSemelhanca.setNome("Grau de Semelhan�a");
		grauSemelhanca.setDescricao("Falta inserir.");
		grauSemelhanca.setStatus("Ativo");
			configuracoes.add(grauSemelhanca);

		grauInclusao.setNome("�ndice de Descartes por Supera��o-Dist�ncia");
		grauInclusao.setDescricao("Falta inserir.");
		grauInclusao.setStatus("Inativo");
			configuracoes.add(grauInclusao);

		grauDistancia.setNome("Grau de Dist�ncia");
		grauDistancia.setDescricao("Falta inserir.");
		grauDistancia.setStatus("Inativo");
			configuracoes.add(grauDistancia);

		
		for(Algoritmo algoritmo : configuracoes){
			algoritmoService.inclui(algoritmo);
		}
	}
	
	

}
