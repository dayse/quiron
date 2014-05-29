package DAO;

import java.util.List;

import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

import modelo.Parametro;

public interface ParametroDAO extends DaoGenerico<Parametro, Long> {

	@RecuperaLista
	public List<Parametro> recuperaListaDeParametros();
	
	@RecuperaLista
	public List<Parametro> recuperaListaDeParametrosPaginada();
	
	@RecuperaObjeto
	public Parametro recuperaParametroPorCodigo(String codParametro) throws ObjetoNaoEncontradoException;
	
	@RecuperaObjeto
	public Parametro recuperaParametroPorNome(String nome) throws ObjetoNaoEncontradoException;
}
