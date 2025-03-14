package exceptions;

public class ParameterException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParameterException(String mensagem) {
	        super(mensagem);
	    }
	}