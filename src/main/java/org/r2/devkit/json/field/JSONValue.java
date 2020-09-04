package org.r2.devkit.json.field;

import org.r2.devkit.exception.StringParseException;
import org.r2.devkit.exception.runtime.IllegalDataException;
import org.r2.devkit.json.JSON;
import org.r2.devkit.json.util.JSONStringParser;
import org.r2.devkit.json.util.ParseHolder;

import java.io.Serializable;

/**
 * JSON键值对中的值字段
 *
 * @author ruan4261
 */
public abstract class JSONValue extends JSON implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 单元解析
     * 找到一个首字符(" / ' / f / t / n)
     * 调用不同子类的静态解析器
     *
     * @param str    被解析的字符串
     * @param offset 初始偏移量，栈内可变量，下标
     * @throws IllegalDataException 参数为null或offset非法
     * @throws StringParseException 字符串不规范，无法解析
     */
    public static ParseHolder<? extends JSONValue> parseValue(String str, int offset) {
        return JSONStringParser.parse2JSONValue(str, offset);
    }
}