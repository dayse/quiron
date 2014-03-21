package exception;

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