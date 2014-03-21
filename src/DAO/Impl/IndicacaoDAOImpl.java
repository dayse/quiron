package DAO.Impl;

import modelo.Indicacao;
import DAO.IndicacaoDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * @author bruno.oliveira
 *
 */
public abstract class IndicacaoDAOImpl extends JPADaoGenerico<Indicacao, Long> implements IndicacaoDAO {

	public IndicacaoDAOImpl() {
		super(Indicacao.class);
	}

}
