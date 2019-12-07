package vn.edu.vnua.dse.stcalendar.exceptions;

public class AlreadyExisted extends RuntimeException {

	private static final long serialVersionUID = 1842339132691282585L;

	public AlreadyExisted(String message) {
		super(message);
	}

}
