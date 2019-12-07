package vn.edu.vnua.dse.stcalendar.exceptions;

public class CustomException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9043760865071651327L;

	   public CustomException()
       {
       }

       public CustomException(String message)
       {
           super(message);
       }

       public CustomException(Throwable cause)
       {
           super(cause);
       }

       public CustomException(String message, Throwable cause)
       {
           super(message, cause);
       }

       public CustomException(String message, Throwable cause, 
                                          boolean enableSuppression, boolean writableStackTrace)
       {
           super(message, cause, enableSuppression, writableStackTrace);
       }
}
