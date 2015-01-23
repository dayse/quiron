package modelo;

import java.util.List;

/**
 * 
 * Para que uma tabela com colunas e linhas dinâmicas fosse viável
 * sentimos a necessidade de criar uma entidade intermediária para
 * fornecer os recursos necessários ao componente DataTable.
 * 
 * Essa entidade foi criada nos moldes da entidade ConjuntoAvaliacao 
 * idealizada e criada pelo felipe.arruda, contudo eu senti a necessidade
 * de criar uma entidade para essa situação, já que a entidade do felipe.arruda
 * não se encaixa na situação da tela de Avaliação do especialista para Cada Indicação
 * onde temos edição de informações.
 * 
 * @author bruno.oliveira
 *
 */
public class ConjuntoIndicacaoParaAvaliacao {

	/**
	 * 
	 * A solução para a tabela sair da maneira certa foi
	 * fornecer ao componente DataTable uma lista de ConjuntoIndicacaoParaAvaliacao.
	 * Cada "linha" dessa lista de conjuntos é identificada por uma Indicação que no final
	 * é usada para crias linhas dinâmicas. Por causa deste atributo
	 * cada elemento dentro da lista representa uma Indicacação 
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	private Indicacao indicacao;
	
	/**
	 * 
	 * Essa lista de AvalIndicacaoEspec recebe as avaliações de todos os
	 * parametros de uma determinada Indicacação e Especialista.
	 * 
	 * Logo, esse atributo é necessário para se obter as notas previamente
	 * guardadas no banco para cada parâmetro cadastrado.
	 * 
	 * @author bruno.oliveira
	 * 
	 */
	private List<AvalIndicacaoEspec> avaliacao;

	// ================================== Métodos get() e set()	// ================================== //
	
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
