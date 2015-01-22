package DAO.Impl;

import modelo.Algoritmo;
import DAO.AlgoritmoDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles métodos que são específicos,
 * ou que ainda não foram generalizados
 * 
 * @author bruno.oliveira, patricia.lima
 *
 */
public abstract class AlgoritmoDAOImpl extends JPADaoGenerico<Algoritmo, Long> implements AlgoritmoDAO {

	public AlgoritmoDAOImpl() {
		super(Algoritmo.class);
	}

}
