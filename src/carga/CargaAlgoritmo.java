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
	 * algoritmos no banco.
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
		
		grauSemelhanca.setCodigo("A01");
		grauSemelhanca.setNome("Grau de Semelhança");
		grauSemelhanca.setDescricao("O grau de Semelhança é um número entre [0] e [1] que corresponde ao nível de semelhança entre dois conjuntos fuzzy."
				+ " Pode ser calculados por diversas formas. Um dos mais populares é conhecido como metódo Max-Min (Ross,2004)."
				+ " Onde a semelhança é calculada a partir da razão entre a somatória dos graus de interseção"
				+ " entre os elementos de dois conjuntos e a somatória dos graus de união entre os elementos desses mesmos conjuntos (GANTE,2012).");
		grauSemelhanca.setStatus("Ativo");
		grauSemelhanca.setCaracteristica("Quanto maior o Grau de Semelhança,"
				+ " mais adequado é o medicamento (indicação). Penaliza"
				+ " o grau final de um medicamento (indicação) se um ou mais de seus"
				+ " parâmetros (sintomas) excede(m) as necessidades do paciente.");
			configuracoes.add(grauSemelhanca);

		grauDistancia.setCodigo("A02");
		grauDistancia.setNome("Índice de Descartes por Superação-Distância");
		grauDistancia.setDescricao("Falta inserir.");
		grauDistancia.setStatus("Inativo");
		grauDistancia.setCaracteristica("Consiste numa combinação das Distâncias de Hamming e de Descartes."
				+ " Aqui, cabe ao decisor escolher parâmetros (sintomas) que podem ou não ultrapassar as"
				+ " necessidades do paciente.");
			configuracoes.add(grauDistancia);			
			
		grauInclusao.setCodigo("A03");	
		grauInclusao.setNome("Grau de Inclusão");
		grauInclusao.setDescricao("Através do Grau de Inclusão, de Kosko (1992),"
				+ " é possível determinar o nível de pertinência de um elemento em um conjunto fuzzy"
				+ " e consequentemente, calcular a distância perceptiva entre eles.");
		grauInclusao.setStatus("Inativo");
		grauInclusao.setCaracteristica("Quanto maior melhor, análogo ao Grau de Semelhança."
				+ " Porém, não penaliza o grau final de um medicamento (indicação)"
				+ " se um ou mais de seus parâmetros (sintomas) excede(m) as necessidades do paciente.");
			configuracoes.add(grauInclusao);

		for(Algoritmo algoritmo : configuracoes){
			algoritmoService.inclui(algoritmo);
		}
	}
	
	

}
