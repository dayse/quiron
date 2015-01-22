package DAO.controle;

import net.sf.cglib.proxy.Enhancer;

/**
 * 
 * Classe genérica responsável pela
 * construção de DAOs do sistema.
 * 
 * @author bruno.oliveira (Atualização)
 *
 */
public class FabricaDeDao
{
    
	@SuppressWarnings("unchecked")  
    public static <T> T getDao(Class classeDoDao) 
        throws Exception 
    {
		return (T)Enhancer.create (classeDoDao, new InterceptadorDeDAO());

    }
}