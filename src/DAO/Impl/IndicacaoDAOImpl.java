package DAO.Impl;

import modelo.Indicacao;
import DAO.IndicacaoDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles m�todos que s�o espec�ficos,
 * ou que ainda n�o foram generalizados
 * 
 * @author bruno.oliveira
 *
 */
public abstract class IndicacaoDAOImpl extends JPADaoGenerico<Indicacao, Long> implements IndicacaoDAO {

	public IndicacaoDAOImpl() {
		super(Indicacao.class);
	}

}
