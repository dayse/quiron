package DAO.Impl;

import modelo.Anamnese;
import DAO.AnamneseDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles m�todos que s�o espec�ficos,
 * ou que ainda n�o foram generalizados
 * 
 * @author daysemou (Atualiza��o)
 *
 */
public abstract class AnamneseDAOImpl extends JPADaoGenerico<Anamnese, Long> implements AnamneseDAO{

	public AnamneseDAOImpl() {
		super(Anamnese.class);
	}

}
