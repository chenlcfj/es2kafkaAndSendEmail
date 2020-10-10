package es.log_reprocess.exception;

/**
 * @author chenlc
 * @date 2020/9/14
 */
public class LogNotFoundException extends LogException {

    public LogNotFoundException(){
    }

    public LogNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogNotFoundException(String message) {
        super(message);
    }
}
