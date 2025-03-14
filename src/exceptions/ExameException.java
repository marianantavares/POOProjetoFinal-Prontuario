package exceptions;

public class ExameException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ExameException(String mensagem) {
		super(mensagem);
	}
}