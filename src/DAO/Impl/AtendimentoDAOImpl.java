package DAO.Impl;

import modelo.Atendimento;
import DAO.AtendimentoDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles métodos que são específicos,
 * ou que ainda não foram generalizados
 * 
 * @author daysemou (Atualização)
 *
 */
public abstract class AtendimentoDAOImpl extends JPADaoGenerico<Atendimento, Long> implements AtendimentoDAO {

	public AtendimentoDAOImpl() {
		super(Atendimento.class);
	}

}
