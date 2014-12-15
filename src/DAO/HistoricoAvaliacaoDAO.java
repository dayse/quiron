package DAO;

import java.util.List;

import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;
import modelo.Atendimento;
import modelo.HistoricoAvaliacao;
import modelo.Parametro;

public interface HistoricoAvaliacaoDAO extends DaoGenerico<HistoricoAvaliacao, Long> {
	
	@RecuperaLista
	public List<HistoricoAvaliacao> recuperaListaHistoricoPorAtendimento(Atendimento atendimento);
}


