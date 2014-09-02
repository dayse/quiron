package util;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import DAO.exception.InfraestruturaException;

public class JPAUtil 
{	
	private static EntityManagerFactory emf = null;
	private static final ThreadLocal<EntityManager> threadEntityManager = new ThreadLocal<EntityManager>();
	private static final ThreadLocal<EntityTransaction> threadTransaction = new ThreadLocal<EntityTransaction>();

	/**
	 * Metodo que retorna a url do banco de dados relativa ao docker
	 * de banco de dados chamado de DB, através das variaveis de ambiente
	 * relativas ao mesmo.
	 * @param env
	 * @return
	 */
	public static String getDBURLFromEnv(Map<String, String> env){
		//ex: tcp://172.17.0.32:5432
		String dbTPCURL = env.get("DB_PORT");
		String dbNoTPCURL = dbTPCURL.replaceFirst("tcp://", "");
		
		//ex: QUIRON_DESENVOLVIMENTO
		String dbName = env.get("QUIRON_DB_NAME");
		
		//ex: jdbc:postgresql://172.17.0.32:5432/QUIRON_DESENVOLVIMENTO
		String dbURl = "jdbc:postgresql://";
		
		dbURl = new StringBuilder()
	    .append(dbURl)
	    .append(dbNoTPCURL)
	    .append("/")
	    .append(dbName)
	    .toString();
//		dbURl.concat(dbTPCURL.replace("tpc://", "")).concat("/").concat(dbName);
		return dbURl;
	}
	
	/**
	 * Usado para gerar dados para sobreescrever as configuracoes do persistence
	 * no caso se no ambiente da maquina houver a variavel de ambiente: ON_PRODUCTION
	 * entao ele troca os dados de url do banco de dados, username, password e schema para 
	 * os dados da maquina.
	 * Muito interessante usar em producao, assim remove a necessidade colocar a informacoes
	 * sensiveis como senhas no arquivo persistence.xml.
	 * 
	 * Para desenvolvimento, como nao existe a variavel de ambiente ON_PRODUCTION ele simplesmente ignora
	 * e nao faz alteracao nenhuma, utilizando aquilo que foi configurado pelo persistence.xml
	 * 
	 * OBS: no tomcat pode ser necessario configurar para que o mesmo tenha estas variaveis de ambiente
	 * configuradas. 
	 * @return
	 */
	public static Map<String, Object> getConfigOverrides(){
		Map<String, String> env = System.getenv();
		Map<String, Object> configOverrides = new HashMap<String, Object>();
		for (String envName : env.keySet()) {
		    if (envName.contains("ON_PRODUCTION")) {
		    	System.out.println("SYSTEM ON PRODUCTION");
				System.out.println(env.keySet());
		        configOverrides.put("hibernate.connection.url", getDBURLFromEnv(env)); 
		         
		        configOverrides.put("hibernate.default_schema", env.get("QUIRON_DB_SCHEMA")); 
		        configOverrides.put("hibernate.connection.username", env.get("QUIRON_DB_USER"));
		        configOverrides.put("hibernate.connection.password", env.get("QUIRON_DB_PASS")); 
		          
		    }
		}
		System.out.println(configOverrides.values());
		return configOverrides;
		
	}
	/**
	 * cria o entity manager factory
	 */
	public static void JPAstartUp() 
	{
		try
		{	
			emf = Persistence.createEntityManagerFactory("prototipo", getConfigOverrides());
		}
		catch(Throwable e)
		{	
			e.printStackTrace();
		}
	}

	public static EntityManager getEntityManager() 
	{
	
		EntityManager s = threadEntityManager.get();
		// Abre uma nova Sessï¿½o, se a thread ainda nï¿½o possui uma.
		try 
		{	if (s == null) 
			{	
				s = emf.createEntityManager();
				threadEntityManager.set(s);
			}
		} 
		catch (RuntimeException ex) 
		{	
			throw new InfraestruturaException(ex);
		}
		return s;
	}

	public static void closeEntityManager() 
	{	

		try 
		{	
			EntityManager s = threadEntityManager.get();
			threadEntityManager.set(null);
			if (s != null && s.isOpen())
			{	
				s.close();
			}

			EntityTransaction tx = threadTransaction.get();
			if ( tx != null && tx.isActive())
			{	
				rollbackTransaction();
				throw new RuntimeException("EntityManager sendo fechado " +
						                   "com transaï¿½ï¿½o ativa.");
			}
		} 	
		catch (RuntimeException ex) 
		{	
			throw new InfraestruturaException(ex);
		}
	}

	public static void beginTransaction() 
	{	

		EntityTransaction tx = threadTransaction.get();
		try 
		{   
			//Verifica se existe alguma transacao aberta na execucao dessa requisicao.
			if (tx == null) 
			{	
				tx = getEntityManager().getTransaction();
				tx.begin();
				threadTransaction.set(tx);
			}
			else
			{	

			}
		} 
		catch (RuntimeException ex) 
		{	
			throw new InfraestruturaException(ex);
		}
	}

	public static void commitTransaction() 
	{	
		EntityTransaction tx = threadTransaction.get();
		try 
		{	if ( tx != null && tx.isActive())
			{	
				tx.commit();
			}
			threadTransaction.set(null);
		} 
		catch (RuntimeException ex) 
		{	try
			{	
				rollbackTransaction();
			}
			catch(RuntimeException e)
			{
			}
			
			throw new InfraestruturaException(ex);
		}
	}

	public static void rollbackTransaction() 
	{
	
		EntityTransaction tx = threadTransaction.get();
		try 
		{	threadTransaction.set(null);
			if ( tx != null && tx.isActive()) 
			{	
				tx.rollback();
			}
		} 
		catch (RuntimeException ex)
		{	
			throw new InfraestruturaException(ex);
		} 
		finally 
		{	
			closeEntityManager();
		}
	}
}
