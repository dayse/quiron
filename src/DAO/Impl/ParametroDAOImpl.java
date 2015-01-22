package DAO.Impl;

import modelo.Parametro;
import DAO.ParametroDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles m�todos que s�o espec�ficos,
 * ou que ainda n�o foram generalizados
 * 
 * @author bruno.oliveira
 *
 */
public abstract class ParametroDAOImpl extends JPADaoGenerico<Parametro, Long> implements ParametroDAO {

	public ParametroDAOImpl() {
		super(Parametro.class);
	}

}
