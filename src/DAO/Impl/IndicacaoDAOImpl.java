package DAO.Impl;

import modelo.Indicacao;
import DAO.IndicacaoDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles métodos que são específicos,
 * ou que ainda não foram generalizados
 * 
 * @author bruno.oliveira
 *
 */
public abstract class IndicacaoDAOImpl extends JPADaoGenerico<Indicacao, Long> implements IndicacaoDAO {

	public IndicacaoDAOImpl() {
		super(Indicacao.class);
	}

}
