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
    String LINE_SEPARATOR = System.lineSeparator();// 系统换行符('\n'或'\r\n'或其他)
    String FILE_SEPARATOR = File.separator;// 文件系统名称分隔符('/'或'\'或其他)
    String PATH_SEPARATOR = File.pathSeparator;// 文件系统路径分隔符(':'或';'或其他)
}