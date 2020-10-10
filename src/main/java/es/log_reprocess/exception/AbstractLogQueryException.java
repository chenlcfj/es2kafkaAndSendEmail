package es.log_reprocess.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenlc
 * @date 2020/9/14
 */
@Setter
@Getter
public abstract class AbstractLogQueryException extends RuntimeException {

    public AbstractLogQueryException() {
    }

    public AbstractLogQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractLogQueryException(String message) {
        super(message);
    }
}
