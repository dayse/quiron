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
 * TipoUsuarioAppService é uma classe de serviço que possui as regras de negócio para manipular inicialmente
 * a entidade TipoUsuario. Estas manipulações incluem quando necessário chamadas as interfaces DAOs e
 * acessos a informações do banco.
 * 
 * A classe TipoUsuarioAppService fora criada para atender ao Padrão MVC, Model Vision Control, sendo a mesma uma
 * classe de serviço que é capaz de efetuar: controle de transação, ou seja esta classe possui o recurso de
 * abrir transaçao, commitar e fechar uma transaçao através de um interceptador de serviço.
 * Neste interceptador será definido se o método é transacional ou não e em função desta informação
 * o interceptador irá usar ou não uma transação.
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