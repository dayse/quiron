package service;

import java.util.List;

import modelo.TipoUsuario;
import service.anotacao.Transacional;
import service.exception.AplicacaoException;
import DAO.TipoUsuarioDAO;
import DAO.Impl.TipoUsuarioDAOImpl;
import DAO.controle.FabricaDeDao;
import DAO.exception.ObjetoNaoEncontradoException;

/**
 * 
 * TipoUsuarioAppService � uma classe de servi�o que possui as regras de neg�cio para manipular inicialmente
 * a entidade TipoUsuario. Estas manipula��es incluem quando necess�rio chamadas as interfaces DAOs e
 * acessos a informa��es do banco.
 * 
 * A classe TipoUsuarioAppService fora criada para atender ao Padr�o MVC, Model Vision Control, sendo a mesma uma
 * classe de servi�o que � capaz de efetuar: controle de transa��o, ou seja esta classe possui o recurso de
 * abrir transa�ao, commitar e fechar uma transa�ao atrav�s de um interceptador de servi�o.
 * Neste interceptador ser� definido se o m�todo � transacional ou n�o e em fun��o desta informa��o
 * o interceptador ir� usar ou n�o uma transa��o.
 * 
 * @author marques.araujo
 * 
 */

public class TipoUsuarioAppService {
	// DAOs
	private TipoUsuarioDAO tipoUsuarioDAO;

	public TipoUsuarioAppService() {
		try {
			// DAOs
			tipoUsuarioDAO = FabricaDeDao.getDao(TipoUsuarioDAOImpl.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transacional
	public void inclui(TipoUsuario tipoUsuario) {
		try {
			tipoUsuarioDAO.recuperaTipoUsuarioPorTipo(tipoUsuario.getTipoUsuario());
		} catch (ObjetoNaoEncontradoException e) {
			tipoUsuarioDAO.inclui(tipoUsuario);
		}

	}
	
	public TipoUsuario recuperaTipoUsuarioAdministrador() throws AplicacaoException {
		try {
			return tipoUsuarioDAO.recuperaTipoUsuarioPorTipo(TipoUsuario.ADMINISTRADOR);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("tipoUsuario.NAO_ENCONTRADO");
		}
	}
	
	public TipoUsuario recuperaTipoUsuarioClinico() throws AplicacaoException {
		try {
			return tipoUsuarioDAO.recuperaTipoUsuarioPorTipo(TipoUsuario.CLINICO);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("tipoUsuario.NAO_ENCONTRADO");
		}
	}
	
	public TipoUsuario recuperaTipoUsuarioTecnico() throws AplicacaoException {
		try {
			return tipoUsuarioDAO.recuperaTipoUsuarioPorTipo(TipoUsuario.TECNICO);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AplicacaoException("tipoUsuario.NAO_ENCONTRADO");
		}
	}
	
	public List<TipoUsuario> recuperaListaDeTipoUsuario(){
		return tipoUsuarioDAO.recuperaListaDeTipoUsuario();
	}
}