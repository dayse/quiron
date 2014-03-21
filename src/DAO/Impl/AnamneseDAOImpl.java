package DAO.Impl;

import modelo.Anamnese;
import DAO.AnamneseDAO;
import DAO.generico.JPADaoGenerico;

public abstract class AnamneseDAOImpl extends JPADaoGenerico<Anamnese, Long> implements AnamneseDAO{

	public AnamneseDAOImpl() {
		super(Anamnese.class);
	}

}
