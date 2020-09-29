package org.r2.devkit;

import static org.r2.devkit.core.SystemAPI.*;

/**
 * 抽象异常类，继承自{@code Exception}，新增dateTime属性用于记录异常发生时间。
 *
 * @author ruan4261
 */
public abstract class AbstractException extends Exception {
    private static final long serialVersionUID = 1L;
    private final long occurrenceTimestamp;

    {
        occurrenceTimestamp = currentTimestamp();
    }

    public AbstractException() {
        super();
    }

    public AbstractException(String message) {
        super(message);
    }

    public AbstractException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Print:
     * #Exception Message:cause xxx.
     * #Occurrence Timestamp:[timestamp]
     */
    @Override
    public String getMessage() {
        return "#Exception Message:" + super.getMessage() + LINE_SEPARATOR +
                "#Occurrence Timestamp:" + occurrenceTimestamp + LINE_SEPARATOR;
    }
}
