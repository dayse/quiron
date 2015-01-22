package DAO.Impl;

import modelo.AvalIndicacaoEspec;
import DAO.AvalIndicacaoEspecDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles m�todos que s�o espec�ficos,
 * ou que ainda n�o foram generalizados
 * 
 * @author daysemou (Atualiza��o)
 *
 */
public abstract class AvalIndicacaoEspecDAOImpl extends JPADaoGenerico<AvalIndicacaoEspec, Long> implements AvalIndicacaoEspecDAO {

	public AvalIndicacaoEspecDAOImpl() {
		super(AvalIndicacaoEspec.class);
	}

}
