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
 * Essa Carga: Classe responsável pela inclusão dos tipos de parâmetros. Esses
 * dados são utilizados apenas para carater ilustrativo, dando ao usuário melhor
 * compreensão de quais parâmetros serão utilizados para se chegar a uma
 * determinada avaliação.
 * 
 * @author bruno.oliveira
 * 
 */
public class CargaAlgoritmo extends CargaBase {

	// Service
	public AlgoritmoAppService algoritmoService;

	/**
	 * 
	 * Construdor da carga, responsável por instanciar os services.
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
		this.incluirAlgoritmo();
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
	public void incluirAlgoritmo() throws AplicacaoException {
		
		List<Algoritmo> configuracoes = new ArrayList<Algoritmo>();
		
		Algoritmo grauSemelhanca = new Algoritmo();
		Algoritmo grauInclusao = new Algoritmo();
		Algoritmo grauDistancia = new Algoritmo();
		
		
		grauSemelhanca.setNome("Grau de Semelhança");
		grauSemelhanca.setDescricao("Falta inserir.");
		grauSemelhanca.setStatus("Ativo");
			configuracoes.add(grauSemelhanca);

		grauInclusao.setNome("Índice de Descartes por Superação-Distância");
		grauInclusao.setDescricao("Falta inserir.");
		grauInclusao.setStatus("Inativo");
			configuracoes.add(grauInclusao);

		grauDistancia.setNome("Grau de Distância");
		grauDistancia.setDescricao("Falta inserir.");
		grauDistancia.setStatus("Inativo");
			configuracoes.add(grauDistancia);

		
		for(Algoritmo algoritmo : configuracoes){
			algoritmoService.inclui(algoritmo);
		}
	}
	
	

}
