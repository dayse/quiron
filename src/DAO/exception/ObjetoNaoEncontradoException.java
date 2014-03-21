package DAO.exception;

public class ObjetoNaoEncontradoException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	public ObjetoNaoEncontradoException()
	{
	}

	public ObjetoNaoEncontradoException(String msg)
	{	super(msg);
	}
}	