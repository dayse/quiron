package DAO.controle;

import net.sf.cglib.proxy.Enhancer;

public class FabricaDeDao
{
    
	@SuppressWarnings("unchecked")  
    public static <T> T getDao(Class classeDoDao) 
        throws Exception 
    {
		return (T)Enhancer.create (classeDoDao, new InterceptadorDeDAO());

    }
}