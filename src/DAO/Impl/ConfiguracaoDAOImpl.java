package DAO.Impl;

import modelo.Configuracao;
import DAO.ConfiguracaoDAO;
import DAO.generico.JPADaoGenerico;

public abstract class ConfiguracaoDAOImpl extends JPADaoGenerico<Configuracao, Long> implements ConfiguracaoDAO {

	public ConfiguracaoDAOImpl() {
		super(Configuracao.class);
	}

}
