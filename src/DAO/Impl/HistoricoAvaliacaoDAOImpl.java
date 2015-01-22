package DAO.Impl;

import modelo.HistoricoAvaliacao;
import modelo.Paciente;
import DAO.HistoricoAvaliacaoDAO;
import DAO.PacienteDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles m�todos que s�o espec�ficos,
 * ou que ainda n�o foram generalizados
 * 
 * @author bruno.oliveira
 *
 */
public abstract class HistoricoAvaliacaoDAOImpl extends JPADaoGenerico<HistoricoAvaliacao, Long> implements HistoricoAvaliacaoDAO{

	public HistoricoAvaliacaoDAOImpl() {
		super(HistoricoAvaliacao.class);
	}

}
