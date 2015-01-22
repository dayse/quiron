package DAO.Impl;

import modelo.HistoricoAvaliacao;
import modelo.Paciente;
import DAO.HistoricoAvaliacaoDAO;
import DAO.PacienteDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles métodos que são específicos,
 * ou que ainda não foram generalizados
 * 
 * @author bruno.oliveira
 *
 */
public abstract class HistoricoAvaliacaoDAOImpl extends JPADaoGenerico<HistoricoAvaliacao, Long> implements HistoricoAvaliacaoDAO{

	public HistoricoAvaliacaoDAOImpl() {
		super(HistoricoAvaliacao.class);
	}

}
