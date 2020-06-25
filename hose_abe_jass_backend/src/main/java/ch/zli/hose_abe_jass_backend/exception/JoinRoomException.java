package ch.zli.hose_abe_jass_backend.exception;

public class JoinRoomException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	
	public JoinRoomException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
