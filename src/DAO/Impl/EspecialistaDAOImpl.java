package DAO.Impl;

import modelo.Especialista;
import DAO.EspecialistaDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles métodos que são específicos,
 * ou que ainda não foram generalizados
 * 
 * @author daysemou (Atualização)
 *
 */
public abstract class EspecialistaDAOImpl extends JPADaoGenerico<Especialista, Long> implements EspecialistaDAO {

	public EspecialistaDAOImpl() {
		super(Especialista.class);
	}

}
