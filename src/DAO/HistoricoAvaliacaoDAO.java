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
 * Interface criada no padrão DAO para intermediar os pedidos de requisição de
 * banco de dados ao modelo HistoricoAvaliacao.
 * 
 * @author bruno.oliveira
 * 
 */
public interface HistoricoAvaliacaoDAO extends DaoGenerico<HistoricoAvaliacao, Long> {
	
	/**
	 * 
	 * Consulta que retorna lista de Historicos através de da busca pelos históricos
	 * de um determinado atendimento.
	 * 
	 * @param atendimento - atendimento ao qual os historicos estão vinculados
	 * @return lista paginada de historicos de atendimentos.
	 * 
	 * @author bruno.oliveira (Atualização)
	 * 
	 */
	@RecuperaLista
	public List<HistoricoAvaliacao> recuperaListaHistoricoPorAtendimento(Atendimento atendimento);
}


