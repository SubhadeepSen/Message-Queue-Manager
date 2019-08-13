package sdp.queuemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import sdp.queuemanager.model.ExceptionResponse;

@ControllerAdvice
public class QueueManagerExceptionHandler {

	@ExceptionHandler(QueueManagerException.class)
	public ResponseEntity<ExceptionResponse> handleQueueManagerException(QueueManagerException ex) {
		ExceptionResponse response = new ExceptionResponse(ex.getMessage(), ex.getErrorCode());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_ACCEPTABLE);
	}
}
