package DAO.Impl;

import modelo.Algoritmo;
import DAO.AlgoritmoDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles m�todos que s�o espec�ficos,
 * ou que ainda n�o foram generalizados
 * 
 * @author bruno.oliveira, patricia.lima
 *
 */
public abstract class AlgoritmoDAOImpl extends JPADaoGenerico<Algoritmo, Long> implements AlgoritmoDAO {

	public AlgoritmoDAOImpl() {
		super(Algoritmo.class);
	}

}
