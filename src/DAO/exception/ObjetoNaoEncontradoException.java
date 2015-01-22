package DAO.exception;

/**
 * 
 * Exception relacionadas a erros de buscas
 * ao banco de dados que n�o retornam nada, n�o
 * encontram uma informa��o desejada pelo usu�rio.
 * 
 * @author bruno.oliveira (Atualiza��o)
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