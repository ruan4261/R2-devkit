package org.r2.devkit.core;

import java.io.File;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

/**
 * 公共的静态常理缓存，被缓存的类必须被{@code final}修饰。
 *
 * @author ruan4261
 */
public interface CacheBase {
    String NULL = "null";
    String EMPTY = "";
    String LINE_SEPARATOR = System.lineSeparator();// 系统换行符('\n'或'\r\n'或其他)
    String FILE_SEPARATOR = File.separator;// 文件系统名称分隔符('/'或'\'或其他)
    String PATH_SEPARATOR = File.pathSeparator;// 文件系统路径分隔符(':'或';'或其他)
    DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    DateTimeFormatter MILLIS_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    DateTimeFormatter MILLIS_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    DateTimeFormatter DATETIME_WITH_T = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
}