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
	public List<Indicacao> recuperaListaDeMedicamentosPaginada();
	
	@RecuperaObjeto
	public Indicacao recuperaMedicamentoPorCodigo(String codMedicamento) throws ObjetoNaoEncontradoException;
	
	@RecuperaLista
	public List<Indicacao> recuperaMedicamentoPorCodigoLike(String codMedicamento);
	
	@RecuperaLista
	public List<Indicacao> recuperaMedicamentoPorNome(String nomeMedicamento);

	@RecuperaLista
	public List<Indicacao> recuperaListaIndicacao();
}
