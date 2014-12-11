package DAO;

import java.util.List;

import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;
import modelo.Algoritmo;
import modelo.Parametro;

public interface AlgoritmoDAO extends DaoGenerico<Algoritmo, Long> {

	@RecuperaLista
	public List<Algoritmo> recuperaListaDeAlgoritmo();
	
	@RecuperaObjeto
	public Algoritmo recuperaAlgoritmoAtivo();
	
	@RecuperaObjeto
	public Algoritmo recuperaAlgoritmoPorNome(String nome) throws ObjetoNaoEncontradoException;
}	
	