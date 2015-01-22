package DAO.Impl;

import modelo.Paciente;
import DAO.PacienteDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles m�todos que s�o espec�ficos,
 * ou que ainda n�o foram generalizados
 * 
 * @author bruno.olveira
 *
 */
public abstract class PacienteDAOImpl extends JPADaoGenerico<Paciente, Long> implements PacienteDAO{

	public PacienteDAOImpl() {
		super(Paciente.class);
	}

}
