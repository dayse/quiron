package DAO;

import java.util.List;

import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;
import modelo.Atendimento;
import modelo.HistoricoAvaliacao;
import modelo.Parametro;

/**
 * 
 * Interface criada no padr�o DAO para intermediar os pedidos de requisi��o de
 * banco de dados ao modelo HistoricoAvaliacao.
 * 
 * @author bruno.oliveira
 * 
 */
public interface HistoricoAvaliacaoDAO extends DaoGenerico<HistoricoAvaliacao, Long> {
	
	/**
	 * 
	 * Consulta que retorna lista de Historicos atrav�s de da busca pelos hist�ricos
	 * de um determinado atendimento.
	 * 
	 * @param atendimento - atendimento ao qual os historicos est�o vinculados
	 * @return lista paginada de historicos de atendimentos.
	 * 
	 * @author bruno.oliveira (Atualiza��o)
	 * 
	 */
	@RecuperaLista
	public List<HistoricoAvaliacao> recuperaListaHistoricoPorAtendimento(Atendimento atendimento);
}


