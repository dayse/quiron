package DAO;

import java.util.List;

import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;
import modelo.Algoritmo;
import modelo.Parametro;

/**
 * 
 * Interface criada no padrão DAO para intermediar os pedidos de requisição de
 * banco de dados ao modelo Algoritmo.
 * 
 * @author bruno.oliveira
 * 
 */
public interface AlgoritmoDAO extends DaoGenerico<Algoritmo, Long> {

	/**
	 * Consulta que recupera uma lista de todos os algoritmos do banco.
	 * 
	 * @return lista atualizada de algoritmos
	 * 
	 * @author bruno.oliveira, patricia.lima
	 * 
	 */
	@RecuperaLista
	public List<Algoritmo> recuperaListaDeAlgoritmo();
	
	/**
	 * Consulta que recupera o algoritmo atualmente marcado como ativo no sistema.
	 *
	 * @return algoritmo ativo
	 * 
	 * @author bruno.oliveira, patricia.lima
	 * 
	 */
	@RecuperaObjeto
	public Algoritmo recuperaAlgoritmoAtivo();
	
	/**
	 * 
	 * Consulta que recupera um algoritmo a partir de seu nome.
	 * 
	 * @param nome do algoritmo
	 * @return algoritmo buscado
	 * @throws ObjetoNaoEncontradoException - Caso o algoritmo não esteja no banco por algum motivo estranho
	 * 
	 * @author bruno.oliveira, patricia.lima
	 * 
	 */
	@RecuperaObjeto
	public Algoritmo recuperaAlgoritmoPorNome(String nome) throws ObjetoNaoEncontradoException;
}	
	