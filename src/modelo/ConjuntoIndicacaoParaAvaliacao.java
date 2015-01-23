package modelo;

import java.util.List;

/**
 * 
 * Para que uma tabela com colunas e linhas din�micas fosse vi�vel
 * sentimos a necessidade de criar uma entidade intermedi�ria para
 * fornecer os recursos necess�rios ao componente DataTable.
 * 
 * Essa entidade foi criada nos moldes da entidade ConjuntoAvaliacao 
 * idealizada e criada pelo felipe.arruda, contudo eu senti a necessidade
 * de criar uma entidade para essa situa��o, j� que a entidade do felipe.arruda
 * n�o se encaixa na situa��o da tela de Avalia��o do especialista para Cada Indica��o
 * onde temos edi��o de informa��es.
 * 
 * @author bruno.oliveira
 *
 */
public class ConjuntoIndicacaoParaAvaliacao {

	/**
	 * 
	 * A solu��o para a tabela sair da maneira certa foi
	 * fornecer ao componente DataTable uma lista de ConjuntoIndicacaoParaAvaliacao.
	 * Cada "linha" dessa lista de conjuntos � identificada por uma Indica��o que no final
	 * � usada para crias linhas din�micas. Por causa deste atributo
	 * cada elemento dentro da lista representa uma Indicaca��o 
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	private Indicacao indicacao;
	
	/**
	 * 
	 * Essa lista de AvalIndicacaoEspec recebe as avalia��es de todos os
	 * parametros de uma determinada Indicaca��o e Especialista.
	 * 
	 * Logo, esse atributo � necess�rio para se obter as notas previamente
	 * guardadas no banco para cada par�metro cadastrado.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	private List<AvalIndicacaoEspec> avaliacao;

	// ================================== M�todos get() e set()	// ================================== //
	
	public Indicacao getIndicacao() {
		return indicacao;
	}

	public void setIndicacao(Indicacao indicacao) {
		this.indicacao = indicacao;
	}

	public List<AvalIndicacaoEspec> getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(List<AvalIndicacaoEspec> avaliacao) {
		this.avaliacao = avaliacao;
	}
	
}
