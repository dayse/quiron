package DAO.Impl;

import modelo.Parametro;
import DAO.ParametroDAO;
import DAO.generico.JPADaoGenerico;

public abstract class ParametroDAOImpl extends JPADaoGenerico<Parametro, Long> implements ParametroDAO {

	public ParametroDAOImpl() {
		super(Parametro.class);
	}

}
