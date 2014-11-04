package DAO;

import java.util.List;

import modelo.Especialista;
import modelo.Usuario;
import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaListaPaginada;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

/**
 * 
 * Interface criada no padrão DAO para intermediar os pedidos de requisição de
 * banco de dados ao modelo Especialista.
 * 
 * @author bruno.oliveira
 * 
 */
public interface EspecialistaDAO extends DaoGenerico<Especialista, Long> {
	
	@RecuperaLista
	public List<Especialista> recuperaListaEspecialista();

	@RecuperaListaPaginada(tamanhoPagina=10)
	public List<Especialista> recuperaListaDeEspecialistasPaginada();
	
	@RecuperaObjeto
	public Especialista recuperaEspecialistaPorCodigo(String codEspecialista) throws ObjetoNaoEncontradoException;
	
	@RecuperaObjeto
	public Double recuperaMediaDoPesoAvaliadorDosEspecialistas();
	
	@RecuperaLista
	public List<Especialista> recuperaEspecialistaPorNomeLike(String nome);
	
	@RecuperaLista
	public List<Especialista> recuperaEspecialistaPorCodigoLike(String codigo);
	
}
