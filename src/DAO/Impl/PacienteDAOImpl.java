package DAO.Impl;

import modelo.Paciente;
import DAO.PacienteDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * @author bruno.oliveira
 *
 */
public abstract class PacienteDAOImpl extends JPADaoGenerico<Paciente, Long> implements PacienteDAO{

	public PacienteDAOImpl() {
		super(Paciente.class);
	}

}
