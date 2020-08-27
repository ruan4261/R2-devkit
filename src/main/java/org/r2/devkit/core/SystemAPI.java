package org.r2.devkit.core;

import static org.r2.devkit.core.CacheBase.LINE_SEPARATOR;

/**
 * java语言完全通用的接口
 *
 * @author ruan4261
 */
public interface SystemAPI {

    /**
     * 返回非显式调用的异常栈，性能并没有多大提高
     */
    static String getCompleteStackTraceInfo(String title) {
        title = title == null ? "StackTrace : Without Message" : title;
        StackTraceElement[] trace = getStackTrace();
        StringBuilder builder = new StringBuilder(title).append(LINE_SEPARATOR);
        for (int i = 2; i < trace.length; i++) {
            StackTraceElement traceElement = trace[i];
            builder.append("\tat ").append(traceElement).append(LINE_SEPARATOR);
        }
        return builder.toString();
    }

    /**
     * 获取方法调用栈
     */
    static StackTraceElement[] getStackTrace() {
        return (new Throwable()).getStackTrace();
    }

    /**
     * 当前标准时间戳
     * UTC +0 1970-01-01 00:00:00.0至当前的毫秒数
     * 基于计算机系统，会产生误差
     */
    static long currentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 纳秒级时间，此方法没有固定基准
     * 仅可用于计时运算，此方法可能会返回负值
     * 在不同实例（虚拟机运行时）中，此方法同一时间的返回值不相同
     */
    static long nanoTime() {
        return System.nanoTime();
    }
}
