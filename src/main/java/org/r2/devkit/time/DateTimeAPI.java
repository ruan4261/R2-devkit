package org.r2.devkit.time;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 日期与时间接口，基于{@code java.time}
 * 以系统当前时区为准，获取时间戳请使用{@link org.r2.devkit.core.SystemAPI#currentTimestamp()}
 *
 * @author ruan4261
 */
public interface DateTimeAPI {

    enum PatternType {
        DEFAULT_DATETIME_FORMATTER("yyyy-MM-dd HH:mm:ss"),
        DEFAULT_DATE_FORMATTER("yyyy-MM-dd"),
        DEFAULT_TIME_FORMATTER("HH:mm:ss"),
        MILLIS_DATETIME_FORMATTER("yyyy-MM-dd HH:mm:ss.SSS"),
        MILLIS_TIME_FORMATTER("HH:mm:ss.SSS"),
        DATETIME_WITH_T("yyyy-MM-dd'T'HH:mm:ss");

        PatternType(String pattern) {
            this.pattern = pattern;
            this.formatter = DateTimeFormatter.ofPattern(pattern);
        }

        public final String pattern;
        public final DateTimeFormatter formatter;

        public static DateTimeFormatter of(String pattern) {
            switch (pattern) {
                case "yyyy-MM-dd HH:mm:ss":
                    return DEFAULT_DATETIME_FORMATTER.formatter;
                case "yyyy-MM-dd":
                    return DEFAULT_DATE_FORMATTER.formatter;
                case "HH:mm:ss":
                    return DEFAULT_TIME_FORMATTER.formatter;
                case "yyyy-MM-dd HH:mm:ss.SSS":
                    return MILLIS_DATETIME_FORMATTER.formatter;
                case "HH:mm:ss.SSS":
                    return MILLIS_TIME_FORMATTER.formatter;
                case "yyyy-MM-dd'T'HH:mm:ss":
                    return DATETIME_WITH_T.formatter;
                default:
                    return DateTimeFormatter.ofPattern(pattern);
            }
        }
    }

    int FAIL_ZONE_ID = 0x7f;// 127:一个不存在的时区偏移量，他可以是-18~18以外的任何数，至于为什么是127（:D）

    static LocalDateTime CURRENT_DATE_TIME() {
        return LocalDateTime.now();
    }

    static LocalDate CURRENT_DATE() {
        return LocalDate.now();
    }

    static LocalTime CURRENT_TIME() {
        return LocalTime.now();
    }

    /**
     * 使用系统默认时区
     * Formatter: yyyy-MM-dd
     */
    static String date() {
        return CURRENT_DATE().format(PatternType.DEFAULT_DATE_FORMATTER.formatter);
    }

    /**
     * 使用系统默认时区
     * Formatter: HH:mm:ss
     */
    static String time() {
        return CURRENT_TIME().format(PatternType.DEFAULT_TIME_FORMATTER.formatter);
    }

    /**
     * 使用系统默认时区
     * Formatter: yyyy-MM-dd HH:mm:ss
     */
    static String dateTime() {
        return CURRENT_DATE_TIME().format(PatternType.DEFAULT_DATETIME_FORMATTER.formatter);
    }

    /* 日期偏移量、秒偏移量 */

    static String dateWithOffset(long offsetDay) {
        return CURRENT_DATE().plusDays(offsetDay).format(PatternType.DEFAULT_DATE_FORMATTER.formatter);
    }

    static String timeWithOffset(long offsetSecond) {
        return CURRENT_TIME().plusSeconds(offsetSecond).format(PatternType.DEFAULT_TIME_FORMATTER.formatter);
    }

    static String dateTimeWithOffset(long offsetDay, long offsetSecond) {
        return CURRENT_DATE_TIME().plusDays(offsetDay).plusSeconds(offsetSecond).format(PatternType.DEFAULT_DATETIME_FORMATTER.formatter);
    }

    /**
     * 将时间戳格式化
     * 使用自定义的时区偏移量
     *
     * @param timestamp  时间戳
     * @param zoneOffset 时区与格林威治标准的小时偏移量，比如中国时区的偏移量是+8
     * @param formatter  格式化标准
     */
    static String format(long timestamp, int zoneOffset, DateTimeFormatter formatter) {
        ZoneId zone;
        if (zoneOffset < -18 || zoneOffset > 18)
            zone = ZoneId.systemDefault();
        else
            zone = ZoneOffset.ofHours(zoneOffset);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zone).format(formatter);
    }

    /** overload */
    static String format(long timestamp, DateTimeFormatter formatter) {
        return format(timestamp, FAIL_ZONE_ID, formatter);
    }

    /* Custom pattern */

    static String format(long timestamp, int zoneOffset, String pattern) {
        return format(timestamp, zoneOffset, PatternType.of(pattern));
    }

    static String format(long timestamp, String pattern) {
        return format(timestamp, FAIL_ZONE_ID, PatternType.of(pattern));
    }

    static String format(String pattern) {
        return CURRENT_DATE_TIME().format(PatternType.of(pattern));
    }

    /* DateTime */

    static String dateTime(long timestamp, int zoneOffset) {
        return format(timestamp, zoneOffset, PatternType.DEFAULT_DATETIME_FORMATTER.formatter);
    }

    static String dateTime(long timestamp) {
        return format(timestamp, FAIL_ZONE_ID, PatternType.DEFAULT_DATETIME_FORMATTER.formatter);
    }

    /* DateTimeWithMillis */

    static String dateTimeWithMillis(long timestamp, int zoneOffset) {
        return format(timestamp, zoneOffset, PatternType.MILLIS_DATETIME_FORMATTER.formatter);
    }

    static String dateTimeWithMillis(long timestamp) {
        return format(timestamp, FAIL_ZONE_ID, PatternType.MILLIS_DATETIME_FORMATTER.formatter);
    }

    /* Date */

    static String date(long timestamp, int zoneOffset) {
        return format(timestamp, zoneOffset, PatternType.DEFAULT_DATE_FORMATTER.formatter);
    }

    static String date(long timestamp) {
        return format(timestamp, FAIL_ZONE_ID, PatternType.DEFAULT_DATE_FORMATTER.formatter);
    }

    /* Time */

    static String time(long timestamp, int zoneOffset) {
        return format(timestamp, zoneOffset, PatternType.DEFAULT_TIME_FORMATTER.formatter);
    }

    static String time(long timestamp) {
        return format(timestamp, FAIL_ZONE_ID, PatternType.DEFAULT_TIME_FORMATTER.formatter);
    }

    /* TimeWithMillis */

    static String timeWithMillis(long timestamp, int zoneOffset) {
        return format(timestamp, zoneOffset, PatternType.MILLIS_TIME_FORMATTER.formatter);
    }

    static String timeWithMillis(long timestamp) {
        return format(timestamp, FAIL_ZONE_ID, PatternType.MILLIS_TIME_FORMATTER.formatter);
    }

    /* Pattern Convert */

    static String convertPattern(String time, String originPattern, String destPattern) {
        return LocalDateTime.parse(time, PatternType.of(originPattern)).format(PatternType.of(destPattern));
    }

    static String convertPattern(String time, DateTimeFormatter originPattern, String destPattern) {
        return LocalDateTime.parse(time, originPattern).format(PatternType.of(destPattern));
    }

    static String convertPattern(String time, String originPattern, DateTimeFormatter destPattern) {
        return LocalDateTime.parse(time, PatternType.of(originPattern)).format(destPattern);
    }

    static String convertPattern(String time, DateTimeFormatter originPattern, DateTimeFormatter destPattern) {
        return LocalDateTime.parse(time, originPattern).format(destPattern);
    }

}
