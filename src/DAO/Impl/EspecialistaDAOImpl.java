package DAO.Impl;

import modelo.Especialista;
import DAO.EspecialistaDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles m�todos que s�o espec�ficos,
 * ou que ainda n�o foram generalizados
 * 
 * @author daysemou (Atualiza��o)
 *
 */
public abstract class EspecialistaDAOImpl extends JPADaoGenerico<Especialista, Long> implements EspecialistaDAO {

	public EspecialistaDAOImpl() {
		super(Especialista.class);
	}

}
