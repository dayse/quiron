package DAO.Impl;

import modelo.TipoUsuario;
import DAO.TipoUsuarioDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles métodos que são específicos,
 * ou que ainda não foram generalizados
 * 
 * @author daysemou
 *
 */
public abstract class TipoUsuarioDAOImpl
       extends JPADaoGenerico<TipoUsuario, Long> implements TipoUsuarioDAO 
{   
    public TipoUsuarioDAOImpl()
    { 	super(TipoUsuario.class); 
    }
}
    
