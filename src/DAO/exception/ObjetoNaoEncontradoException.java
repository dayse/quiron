package DAO.exception;

/**
 * 
 * Exception relacionadas a erros de buscas
 * ao banco de dados que não retornam nada, não
 * encontram uma informação desejada pelo usuário.
 * 
 * @author bruno.oliveira (Atualização)
 *
 */
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