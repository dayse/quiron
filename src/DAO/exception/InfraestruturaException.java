package DAO.exception;

/**
 * 
 * Exception relacionado a problemas de infra estrutura.
 * 
 * @author bruno.oliveira (Atualização)
 *
 */
public class InfraestruturaException extends RuntimeException
{	
	private final static long serialVersionUID = 1L;
	
	public InfraestruturaException(Exception e)
	{	super(e);
	}

	public InfraestruturaException(String msg)
	{	super(msg);
	}
}	