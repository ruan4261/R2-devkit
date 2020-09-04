package org.r2.devkit.exception;

/**
 * 字符串解析异常。
 *
 * @author ruan4261
 */
public class StringParseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public StringParseException(String message) {
        super(message);
    }

    public StringParseException(Throwable ex) {
        super(ex);
    }
}
