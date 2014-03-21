package DAO.Impl;

import modelo.Usuario;
import DAO.UsuarioDAO;
import DAO.generico.JPADaoGenerico;

/**
 * As classes DAOImpl implementam aqueles m�todos que s�o espec�ficos,
 * ou que ainda n�o foram generalizados
 * 
 * @author daysemou
 *
 */
public abstract class UsuarioDAOImpl
       extends JPADaoGenerico<Usuario, Long> implements UsuarioDAO 
{   
    public UsuarioDAOImpl()
    { 	super(Usuario.class); 
    }
}
    
