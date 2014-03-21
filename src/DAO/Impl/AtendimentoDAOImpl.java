package DAO.Impl;

import modelo.Atendimento;
import DAO.AtendimentoDAO;
import DAO.generico.JPADaoGenerico;

public abstract class AtendimentoDAOImpl extends JPADaoGenerico<Atendimento, Long> implements AtendimentoDAO {

	public AtendimentoDAOImpl() {
		super(Atendimento.class);
	}

}
