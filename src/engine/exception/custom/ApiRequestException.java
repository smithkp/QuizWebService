package engine.exception.custom;

public class ApiRequestException extends RuntimeException{
    public ApiRequestException(String msg) {
        super(msg);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
