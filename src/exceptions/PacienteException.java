package exceptions;
	
public class PacienteException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public PacienteException(String mensagem) {
		super(mensagem);
	}
}