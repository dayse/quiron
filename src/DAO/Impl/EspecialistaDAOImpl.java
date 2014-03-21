package DAO.Impl;

import modelo.Especialista;
import DAO.EspecialistaDAO;
import DAO.generico.JPADaoGenerico;

public abstract class EspecialistaDAOImpl extends JPADaoGenerico<Especialista, Long> implements EspecialistaDAO {

	public EspecialistaDAOImpl() {
		super(Especialista.class);
	}

}
