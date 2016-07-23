package exception;

public class RepeatException extends Exception {
	private static final long serialVersionUID = 2L;

	public String message = null;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	public  RepeatException(String message) {
		super();
		this.message = message;
	}
	
	public  RepeatException() {
		
	}

}
