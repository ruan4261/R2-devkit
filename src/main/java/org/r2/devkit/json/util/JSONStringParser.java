package org.r2.devkit.json.util;

import org.r2.devkit.exception.runtime.IllegalDataException;
import org.r2.devkit.json.JSON;
import org.r2.devkit.json.JSONArray;
import org.r2.devkit.json.JSONException;
import org.r2.devkit.json.JSONObject;
import org.r2.devkit.json.field.*;
import org.r2.devkit.util.Assert;

import static org.r2.devkit.json.JSONToken.*;
import static org.r2.devkit.json.util.FieldParser.*;

/**
 * JSON字符串解析算法
 *
 * @author ruan4261
 */
public final class JSONStringParser {

    /**
     * 解析出一个JSONObject
     * 0.找到一个首字符
     * loop{
     * 1.找到一个JSONKey（等同于JSONValueString） / 找到一个尾字符，结束
     * 2.找到一个COLON / 异常
     * 3.找到一个JSON的实现，构成一个键值对，插入JSONObject中
     * 4.找到一个COMMA / 找到一个尾字符，结束
     * }
     * 在此循环中忽略JSON语法规范的可忽略字符
     *
     * @param str    被解析的字符串
     * @param offset 解析开始的偏移量
     * @throws IllegalDataException 参数为null，或offset非法
     * @throws JSONException        解析失败
     */
    public static Holder<JSONObject> parse2JSONObject(String str, int offset) {
        Assert.legalOffset(str, offset);
        JSONObject body = new JSONObject();

        final int len = str.length();

        // 0:首字符，必须为 {
        for (; offset < len; ) {
            char c = str.charAt(offset++);
            if (!isIgnorable(c)) {
                if (LBRACE == c)
                    break;
                else
                    throw new JSONException("String cannot parse, miss left brace(off " + offset + ") : " + str);
            }
        }

        // 循环的状态记录
        int state = 1;
        // 一组键值对
        String key = null;
        JSON value;

        /*
        循环1,2,3,4
        */
        loop:
        for (; offset < len; ) {
            char c = str.charAt(offset);
            if (isIgnorable(c)) {
                offset++;
                continue;
            }

            switch (state) {
                case 1:
                    if (RBRACE == c)
                        // 解析结束
                        return new Holder<>(body, ++offset);

                    // 拿到当前键
                    Holder<JSONValueString> jsonKey = parse2JSONKey(str, offset);
                    key = jsonKey.getObject().toString();
                    offset = jsonKey.getOffset();

                    state = 2;
                    continue loop;
                case 2:
                    if (COLON != c)
                        // 非COLON，异常
                        throw new JSONException("String cannot parse, miss colon(off " + offset + ") : " + str);

                    offset++;

                    state = 3;
                    continue loop;
                case 3:
                    Holder<? extends JSON> jsonValue = parse2JSON(str, offset);
                    value = jsonValue.getObject();
                    offset = jsonValue.getOffset();

                    // 键值对构成
                    body.put(key, value);

                    state = 4;
                    continue loop;
                case 4:
                    if (RBRACE == c)
                        // 对象解析结束
                        return new Holder<>(body, ++offset);
                    if (COMMA != c)
                        // 非COMMA，异常
                        throw new JSONException("String cannot parse, miss comma(off " + offset + ") : " + str);

                    offset++;
                    state = 1;
                    continue loop;
                default:
                    throw new JSONException("JSONStringParser#parse2JSONObject(String, int) loop state has bug.");
            }
        }
        throw new JSONException("String cannot parse : " + str);
    }

    public static Holder<JSONArray> parse2JSONArray(String str, int offset) {
        // todo
        return new Holder<>();
    }

    /**
     * 根据首字符调用不同子类的静态解析器
     * 有六种情况
     * 1.字符串，例如: "tom"（需要被单引号或双引号修饰，这是最常见的）
     * 2.null，只允许: null
     * 3.boolean，只允许: false / true
     * 4.number，十进制，允许科学计数法，详见{@link JSONValueNumber}
     * 5.JSONObject，以 { 开头
     * 6.JSONArray，以 [ 开头
     *
     * 在发现第一个可用符号前，所有字符都会引发解析异常
     * 但有四个字符（JSON语法规范的可忽略字符）除外
     *
     * @param str    被解析的字符串
     * @param offset 解析开始的偏移量
     * @throws IllegalDataException 参数为null，或offset非法
     * @throws JSONException        解析失败
     */
    public static Holder<? extends JSON> parse2JSON(String str, int offset) {
        Assert.legalOffset(str, offset);
        final int len = str.length();

        for (; offset < len; ) {
            char c = str.charAt(offset);
            if (isIgnorable(c)) {
                offset++;
                continue;
            }

            // 解析到第一个字符时，直接调用下层解析器，offset由下层控制
            // 走到此处，必定return或throw
            switch (c) {
                case QUOT:
                case DOUBLE_QUOT:
                    return p2String(str, offset, c);
                case 'f':
                case 't':
                    return p2Boolean(str, offset);
                case 'n':
                    return p2Null(str, offset);
                case LBRACE:
                    return parse2JSONObject(str, offset);
                case LBRACKET:
                    return parse2JSONArray(str, offset);
                default:
                    // number
                    if (c == '-' || c == '+' || (c >= '0' && c <= '9'))
                        return p2Number(str, offset);

                    // 首字符不可用
                    throw new JSONException("String cannot parse(off " + offset + ") : " + str);
            }
        }
        throw new JSONException("String cannot parse : " + str);
    }

    /**
     * 从str的下标offset开始，解析出一个JSONKey对象，其形式完全等同于JSONValueString
     *
     * @param str    被解析的字符串
     * @param offset 解析开始的偏移量
     * @throws IllegalDataException 参数为null，或offset非法
     * @throws JSONException        解析失败
     */
    public static Holder<JSONValueString> parse2JSONKey(String str, int offset) {
        Assert.legalOffset(str, offset);
        final int len = str.length();

        for (; offset < len; ) {
            char c = str.charAt(offset);
            if (isIgnorable(c)) {
                offset++;
                continue;
            }

            // 解析到第一个字符时，直接调用下层解析器，offset由下层控制
            // 走到此处，必定return或throw
            switch (c) {
                case QUOT:
                case DOUBLE_QUOT:
                    return p2String(str, offset, c);
                default:
                    throw new JSONException("String cannot parse, miss quotation(off " + offset + ") : " + str);
            }
        }
        throw new JSONException("String cannot parse : " + str);
    }
}
