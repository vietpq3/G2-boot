package exception;

public class SystemException extends Exception {
	private static final long serialVersionUID = -8931040232031288903L;
	private String message;
	public SystemException(String message){
		this.message = message;
	}
	
	@Override
	public String getMessage(){
		return this.message;
	}
}
