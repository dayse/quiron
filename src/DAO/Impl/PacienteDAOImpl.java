package DAO.Impl;

import modelo.Paciente;
import DAO.PacienteDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles métodos que são específicos,
 * ou que ainda não foram generalizados
 * 
 * @author bruno.olveira
 *
 */
public abstract class PacienteDAOImpl extends JPADaoGenerico<Paciente, Long> implements PacienteDAO{

	public PacienteDAOImpl() {
		super(Paciente.class);
	}

}
