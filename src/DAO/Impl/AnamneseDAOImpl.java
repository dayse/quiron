package DAO.Impl;

import modelo.Anamnese;
import DAO.AnamneseDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles métodos que são específicos,
 * ou que ainda não foram generalizados
 * 
 * @author daysemou (Atualização)
 *
 */
public abstract class AnamneseDAOImpl extends JPADaoGenerico<Anamnese, Long> implements AnamneseDAO{

	public AnamneseDAOImpl() {
		super(Anamnese.class);
	}

}
