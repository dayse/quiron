package DAO.Impl;

import modelo.Atendimento;
import DAO.AtendimentoDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles m�todos que s�o espec�ficos,
 * ou que ainda n�o foram generalizados
 * 
 * @author daysemou (Atualiza��o)
 *
 */
public abstract class AtendimentoDAOImpl extends JPADaoGenerico<Atendimento, Long> implements AtendimentoDAO {

	public AtendimentoDAOImpl() {
		super(Atendimento.class);
	}

}
