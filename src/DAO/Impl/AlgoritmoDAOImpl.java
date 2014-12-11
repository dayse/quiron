package DAO.Impl;

import modelo.Algoritmo;
import DAO.AlgoritmoDAO;
import DAO.generico.JPADaoGenerico;

public abstract class AlgoritmoDAOImpl extends JPADaoGenerico<Algoritmo, Long> implements AlgoritmoDAO {

	public AlgoritmoDAOImpl() {
		super(Algoritmo.class);
	}

}
