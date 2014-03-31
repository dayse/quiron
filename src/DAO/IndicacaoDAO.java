package DAO;

import java.util.List;

import modelo.Indicacao;
import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaListaPaginada;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

/**
 * 
 * @author bruno.oliveira
 *
 */
public interface IndicacaoDAO extends DaoGenerico<Indicacao, Long> {
	
	@RecuperaListaPaginada(tamanhoPagina=10)
	public List<Indicacao> recuperaListaDeIndicacoesPaginada();
	
	@RecuperaObjeto
	public Indicacao recuperaIndicacaoPorCodigo(String codIndicacao) throws ObjetoNaoEncontradoException;
	
	@RecuperaLista
	public List<Indicacao> recuperaIndicacaoPorCodigoLike(String codIndicacao);
	
	@RecuperaLista
	public List<Indicacao> recuperaIndicacaoPorNome(String nomeIndicacao);

	@RecuperaLista
	public List<Indicacao> recuperaListaIndicacao();
}
