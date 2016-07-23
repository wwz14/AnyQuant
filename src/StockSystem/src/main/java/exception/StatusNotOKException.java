package exception;

public class StatusNotOKException extends Exception{

	/**
	 * status not ok
	 */
	private static final long serialVersionUID = 1L;

	public String message = null;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	public StatusNotOKException(String message) {
		super();
		this.message = message;
	}
	
	public StatusNotOKException() {
		
	}
}
