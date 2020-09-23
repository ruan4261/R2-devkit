package org.r2.devkit;

/**
 * Bean操作异常
 *
 * @author ruan4261
 */
public class BeanException extends ReflectiveOperationException {
    private static final long serialVersionUID = 123456789L;

    public BeanException() {
    }

    public BeanException(String message) {
        super(message);
    }

    public BeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanException(Throwable cause) {
        super(cause);
    }
}
