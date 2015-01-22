package DAO.Impl;

import modelo.AvalIndicacaoEspec;
import DAO.AvalIndicacaoEspecDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles métodos que são específicos,
 * ou que ainda não foram generalizados
 * 
 * @author daysemou (Atualização)
 *
 */
public abstract class AvalIndicacaoEspecDAOImpl extends JPADaoGenerico<AvalIndicacaoEspec, Long> implements AvalIndicacaoEspecDAO {

	public AvalIndicacaoEspecDAOImpl() {
		super(AvalIndicacaoEspec.class);
	}

}
