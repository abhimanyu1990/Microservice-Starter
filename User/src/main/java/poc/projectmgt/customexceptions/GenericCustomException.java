package poc.projectmgt.customexceptions;

public class GenericCustomException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public GenericCustomException(String msg) {
		super(msg);
	}
}
