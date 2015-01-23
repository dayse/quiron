package exception;


/**
 * 
 * Classe que representa uma exception que pode
 * ocorrer durante a execu��o de um relat�rio do
 * sistema.
 * 
 * @author bruno.oliveira (Atualiza��o)
 *
 */
public class RelatorioException extends Exception {

	private static final long serialVersionUID = 1L;

	public RelatorioException() {
    }

    public RelatorioException(String msg) {
        super(msg);
    }

    public RelatorioException(Throwable t) {
        super(t);
    }

    public RelatorioException(String msg, Throwable t) {
        super(msg, t);
    }
}