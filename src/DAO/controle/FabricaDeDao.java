package DAO.controle;

import net.sf.cglib.proxy.Enhancer;

/**
 * 
 * Classe gen�rica respons�vel pela
 * constru��o de DAOs do sistema.
 * 
 * @author bruno.oliveira (Atualiza��o)
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