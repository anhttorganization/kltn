package vn.edu.vnua.dse.stcalendar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import vn.edu.vnua.dse.stcalendar.common.ErrorCode;
import vn.edu.vnua.dse.stcalendar.common.ErrorResponse;

@RestControllerAdvice
public class ApiExceptionHandler {
	 /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAllException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        return ErrorResponse.of(ex.getMessage(), ErrorCode.GLOBAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * IndexOutOfBoundsException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse TodoException(Exception ex, WebRequest request) {
    	return ErrorResponse.of(ex.getMessage(), ErrorCode.GLOBAL, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(AlreadyExisted.class)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ErrorResponse handleAlreadyExistedException(Exception ex, WebRequest request){
		return ErrorResponse.of(ex.getMessage(), ErrorCode.CREATED, HttpStatus.CREATED);
    }

    
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCustomException(Exception ex, WebRequest request){
		return ErrorResponse.of(ex.getMessage(), ErrorCode.GLOBAL, HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler(AlreadyExisted.class)
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public ErrorResponse handleAlreadyExistedException(Exception ex, WebRequest request){
//    	
//		return null;
//    	
//    }

}
