package DAO;

import java.util.List;

import modelo.TipoUsuario;

import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

public interface TipoUsuarioDAO extends DaoGenerico<TipoUsuario, Long>
{

	@RecuperaObjeto
	public TipoUsuario recuperaTipoUsuarioPorTipo(String tipoUsuario) throws ObjetoNaoEncontradoException;
	
	@RecuperaLista
	public List<TipoUsuario> recuperaListaDeTipoUsuario();
}
