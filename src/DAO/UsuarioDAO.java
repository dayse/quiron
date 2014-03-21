package DAO;

import java.util.List;

import modelo.TipoUsuario;
import modelo.Usuario;

import DAO.anotacao.RecuperaLista;
import DAO.anotacao.RecuperaListaPaginada;
import DAO.anotacao.RecuperaObjeto;
import DAO.exception.ObjetoNaoEncontradoException;
import DAO.generico.DaoGenerico;

public interface UsuarioDAO extends DaoGenerico<Usuario, Long>
{

	@RecuperaObjeto
	public Usuario recuperaPorLoginESenha(String login, String senha) throws ObjetoNaoEncontradoException;

	@RecuperaObjeto
	public Usuario recuperaPorLogin(String login) throws ObjetoNaoEncontradoException;

	@RecuperaLista
	public List<Usuario> recuperaListaDeUsuarios();
	
	@RecuperaLista
	public List<Usuario> recuperaListaDeUsuariosComTipo();

	@RecuperaListaPaginada
	public List<Usuario> recuperaListaPaginadaPorNome(String nome);

	@RecuperaLista
	public List<Usuario> recuperaListaDeUsuarioPorTipo(TipoUsuario tipoUsuario);

}
