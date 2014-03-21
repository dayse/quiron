package DAO.Impl;

import modelo.AvalIndicacaoEspec;
import DAO.AvalIndicacaoEspecDAO;
import DAO.generico.JPADaoGenerico;

public abstract class AvalIndicacaoEspecDAOImpl extends JPADaoGenerico<AvalIndicacaoEspec, Long> implements AvalIndicacaoEspecDAO {

	public AvalIndicacaoEspecDAOImpl() {
		super(AvalIndicacaoEspec.class);
	}

}
