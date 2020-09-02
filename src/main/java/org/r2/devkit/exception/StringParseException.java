package org.r2.devkit.exception;

/**
 * 字符串解析异常。
 *
 * @author ruan4261
 */
public class StringParseException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String exceptionString;

    public StringParseException(String string) {
        super();
        this.exceptionString = string;
    }

    public StringParseException(String message, String string) {
        super(message);
        this.exceptionString = string;
    }

    public StringParseException(Throwable ex, String string) {
        super(ex);
        this.exceptionString = string;
    }

    public String getExceptionString() {
        return exceptionString;
    }

    public void setExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ", exception string : " + exceptionString;
    }
}
