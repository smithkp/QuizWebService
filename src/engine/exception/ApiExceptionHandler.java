package engine.exception;

import engine.exception.custom.ApiRequestException;
import engine.exception.custom.ForbiddenException;
import engine.exception.custom.QuizNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    private final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    private final HttpStatus notFound = HttpStatus.NOT_FOUND;
    private final HttpStatus forbidden = HttpStatus.FORBIDDEN;

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<Object>(apiException, badRequest);
    }

    @ExceptionHandler(value = {QuizNotFoundException.class})
    public ResponseEntity<Object> handleQuizNotFoundException(QuizNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<Object>(apiException, notFound);
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                forbidden,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<Object>(apiException, forbidden);
    }
}
