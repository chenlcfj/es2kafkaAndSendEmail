package es.log_reprocess.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author chenlc
 * @date 2020/9/14
 */
@Data
public class LogException extends AbstractLogQueryException {
    HttpStatus httpStatus= HttpStatus.INTERNAL_SERVER_ERROR;
    public LogException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus=httpStatus;
    }
    public LogException(HttpStatus httpStatus, String message, Throwable cause) {
        super(message,cause);
        this.httpStatus=httpStatus;
    }
    public LogException() {
    }

    public LogException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogException(String message) {
        super(message);
    }
}
