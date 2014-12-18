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
	 * algoritmos no banco.
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
		
		grauSemelhanca.setCodigo("A01");
		grauSemelhanca.setNome("Grau de Semelhan�a");
		grauSemelhanca.setDescricao("O grau de Semelhan�a � um n�mero entre [0] e [1] que corresponde ao n�vel de semelhan�a entre dois conjuntos fuzzy."
				+ " Pode ser calculados por diversas formas. Um dos mais populares � conhecido como met�do Max-Min (Ross,2004)."
				+ " Onde a semelhan�a � calculada a partir da raz�o entre a somat�ria dos graus de interse��o"
				+ " entre os elementos de dois conjuntos e a somat�ria dos graus de uni�o entre os elementos desses mesmos conjuntos (GANTE,2012).");
		grauSemelhanca.setStatus("Ativo");
		grauSemelhanca.setCaracteristica("Quanto maior o Grau de Semelhan�a,"
				+ " mais adequado � o medicamento (indica��o). Penaliza"
				+ " o grau final de um medicamento (indica��o) se um ou mais de seus"
				+ " par�metros (sintomas) excede(m) as necessidades do paciente.");
			configuracoes.add(grauSemelhanca);

		grauDistancia.setCodigo("A02");
		grauDistancia.setNome("�ndice de Descartes por Supera��o-Dist�ncia");
		grauDistancia.setDescricao("Falta inserir.");
		grauDistancia.setStatus("Inativo");
		grauDistancia.setCaracteristica("Consiste numa combina��o das Dist�ncias de Hamming e de Descartes."
				+ " Aqui, cabe ao decisor escolher par�metros (sintomas) que podem ou n�o ultrapassar as"
				+ " necessidades do paciente.");
			configuracoes.add(grauDistancia);			
			
		grauInclusao.setCodigo("A03");	
		grauInclusao.setNome("Grau de Inclus�o");
		grauInclusao.setDescricao("Atrav�s do Grau de Inclus�o, de Kosko (1992),"
				+ " � poss�vel determinar o n�vel de pertin�ncia de um elemento em um conjunto fuzzy"
				+ " e consequentemente, calcular a dist�ncia perceptiva entre eles.");
		grauInclusao.setStatus("Inativo");
		grauInclusao.setCaracteristica("Quanto maior melhor, an�logo ao Grau de Semelhan�a."
				+ " Por�m, n�o penaliza o grau final de um medicamento (indica��o)"
				+ " se um ou mais de seus par�metros (sintomas) excede(m) as necessidades do paciente.");
			configuracoes.add(grauInclusao);

		for(Algoritmo algoritmo : configuracoes){
			algoritmoService.inclui(algoritmo);
		}
	}
	
	

}
