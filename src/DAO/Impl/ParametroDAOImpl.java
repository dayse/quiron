package DAO.Impl;

import modelo.Parametro;
import DAO.ParametroDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles métodos que são específicos,
 * ou que ainda não foram generalizados
 * 
 * @author bruno.oliveira
 *
 */
public abstract class ParametroDAOImpl extends JPADaoGenerico<Parametro, Long> implements ParametroDAO {

	public ParametroDAOImpl() {
		super(Parametro.class);
	}

}
