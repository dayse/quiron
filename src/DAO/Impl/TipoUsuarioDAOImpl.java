package DAO.Impl;

import modelo.TipoUsuario;
import DAO.TipoUsuarioDAO;
import DAO.generico.JPADaoGenerico;

/**
 * 
 * As classes DAOImpl implementam aqueles m�todos que s�o espec�ficos,
 * ou que ainda n�o foram generalizados
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
    
