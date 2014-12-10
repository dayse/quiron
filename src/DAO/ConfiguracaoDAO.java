package DAO;

import java.util.List;

import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;
import modelo.Configuracao;
import modelo.Parametro;

public interface ConfiguracaoDAO extends DaoGenerico<Configuracao, Long> {

	@RecuperaLista
	public List<Configuracao> recuperaListaDeConfiguracaoPaginada();
	@RecuperaObjeto
	public Configuracao recuperaConfiguracaoPorNome(String nome) throws ObjetoNaoEncontradoException;
}	
	