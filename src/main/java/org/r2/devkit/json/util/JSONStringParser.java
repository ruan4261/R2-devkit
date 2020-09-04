package org.r2.devkit.json.util;

import org.r2.devkit.exception.StringParseException;
import org.r2.devkit.exception.runtime.IllegalDataException;
import org.r2.devkit.json.field.*;
import org.r2.devkit.util.Assert;

import static org.r2.devkit.json.JSONToken.*;

/**
 * 字符串解析算法
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
     * 3.找到一个JSONValue，构成一个桶，插入JSONObject中
     * 4.找到一个COMMA / 找到一个尾字符，结束
     * }
     * 在此循环中忽略JSON语法规范的可忽略字符
     *
     * @param str    被解析的字符串
     * @param offset 初始偏移量，栈内可变量，下标
     * @throws IllegalDataException 参数为null，或offset非法
     * @throws StringParseException 字符串解析失败
     */
    public static ParseHolder<JSONObject> parse2JSONObject(String str, int offset) {
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
                    throw new StringParseException("CharSequence cannot parse, miss left brace(off " + offset + ") : " + str);
            }
        }


        // 循环的当前状态记录
        int state = 1;
        JSONStandardString key = null;
        JSONValue value;

        /*
        循环1,2,3,4
        此处offset变量:
        todo
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
                        return new ParseHolder<>(body, ++offset);

                    ParseHolder<JSONStandardString> jsonKey = parse2JSONValueString(str, offset);
                    key = jsonKey.getObject();
                    offset = jsonKey.getOffset();

                    state = 2;
                    continue loop;
                case 2:
                    if (COLON != c)
                        throw new StringParseException("CharSequence cannot parse to JSONObject(off " + offset + ") : " + str);

                    offset++;

                    state = 3;
                    continue loop;
                case 3:
                    ParseHolder<? extends JSONValue> jsonValue = JSONValue.parseValue(str, offset);
                    value = jsonValue.getObject();
                    offset = jsonValue.getOffset();

                    body.put(key, value);

                    state = 4;
                    continue loop;
                case 4:
                    if (RBRACE == c)
                        return new ParseHolder<>(body, ++offset);
                    if (COMMA != c)
                        throw new StringParseException("CharSequence cannot parse to JSONObject(off " + offset + ") : " + str);

                    offset++;
                    state = 1;
                    continue loop;
                default:
                    Assert.fail("JSONObject#parse(String,int) has bug.");
            }
        }
        throw new StringParseException("CharSequence cannot parse to JSONObject : " + str);
    }

    public static ParseHolder<JSONArray> parse2JSONArray(String str, int offset) {
        // todo
        return new ParseHolder<>();
    }

    /**
     * 根据首字符调用不同子类的静态解析器
     * 有四种情况
     * 1.字符串，例如: "tom"（需要被单引号或双引号修饰，这是最常见的）
     * 2.null，只允许: null
     * 3.boolean，只允许: false / true
     * 4.number，十进制，允许科学计数法，详见{@link JSONValueNumber}
     *
     * 在发现第一个可用符号前，所有字符都会引发解析异常
     * 但有四个字符（JSON语法规范的可忽略字符）除外
     *
     * @param str    被解析的字符串
     * @param offset 初始偏移量，栈内可变量，下标
     * @return object:JSONValue实例
     *         offset:字符串的结束点下标+1
     * @throws IllegalDataException 参数为null，或offset非法
     * @throws StringParseException 字符串解析失败
     */
    public static ParseHolder<? extends JSONValue> parse2JSONValue(String str, int offset) {
        Assert.legalOffset(str, offset);
        final int len = str.length();

        for (; offset < len; offset++) {
            char c = str.charAt(offset);
            if (isIgnorable(c)) continue;

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
                default:
                    // number
                    if (c == '-' || c == '+' || (c >= '0' && c <= '9'))
                        return p2Number(str, offset);

                    // 无法解析
                    throw new StringParseException("CharSequence cannot parse(off " + offset + ") : " + str);
            }
        }
        throw new StringParseException("CharSequence cannot parse(off " + offset + ") : " + str);
    }

    /**
     * 解析出一个JSONKey
     * 其必定是一个JSONValueString，他们具有同等效力
     *
     * 在发现第一个可用符号前，所有字符都会引发解析异常
     * 但有四个字符（JSON语法规范的可忽略字符）除外
     *
     * @param str    被解析的字符串
     * @param offset 初始偏移量，栈内可变量，下标
     * @return object:JSONValue实例
     *         offset:字符串的结束点下标+1
     * @throws IllegalDataException 参数为null，或offset非法
     * @throws StringParseException 字符串解析失败
     */
    public static ParseHolder<JSONStandardString> parse2JSONValueString(String str, int offset) {
        Assert.legalOffset(str, offset);
        final int len = str.length();

        for (; offset < len; offset++) {
            char c = str.charAt(offset);
            if (isIgnorable(c)) continue;

            // 解析到第一个字符时，直接调用下层解析器，offset由下层控制
            // 走到此处，必定return或throw
            switch (c) {
                case QUOT:
                case DOUBLE_QUOT:
                    return p2String(str, offset, c);
                default:
                    throw new StringParseException("CharSequence cannot parse(off " + offset + ") : " + str);
            }
        }
        throw new StringParseException("CharSequence cannot parse(off " + offset + ") : " + str);
    }

    /**
     * 将字符串解析为一个JSON字符串，将会遵循RFC4627进行转义
     * 解析时跳过第一个offset字符，默认其为quot
     * 而后再次解析到quot字符时（非转义情况下；如果是转义情况下，quot是可用字符）返回解析内容
     * 该方法不解析首字符，首字符应该由上层调用者解析，并将首字符赋予参数quot
     *
     * 转义大致内容：
     * 转义标识符号为反斜杠，可转义的字符有8个
     * 有转义标识符号但缺少跟随的可转义字符将被报错（转义标识符号自己也属于可转义）
     * 4个可忽略字符，8个可转义字符详见{@link org.r2.devkit.json.JSONToken}
     *
     * @param str    被解析的字符串
     * @param offset 初始偏移量，栈内可变量，下标
     * @param quot   结束的标识符
     * @return object:字符串内容
     *         offset:字符串的结束点下标+1
     * @throws StringParseException 字符串解析失败
     */
    private static ParseHolder<JSONStandardString> p2String(String str, int offset, char quot) {
        offset++;// 跳过第一个quot符
        final int len = str.length();
        StringBuilder body = new StringBuilder();
        boolean preEscape = false;// 前一个字符为反斜杠

        loop:
        for (; offset < len; ) {
            char c = str.charAt(offset++);

            // 当前字符需要被转义
            if (preEscape) {
                preEscape = false;
                switch (c) {
                    case REVERSE_SOLIDUS:
                        body.append(REVERSE_SOLIDUS);
                        continue loop;
                    case SOLIDUS:
                        body.append(SOLIDUS);
                        continue loop;
                    case LINE_FEED_CHAR:
                        body.append(LINE_FEED);
                        continue loop;
                    case TAB_CHAR:
                        body.append(TAB);
                        continue loop;
                    case CARRIAGE_RETURN_CHAR:
                        body.append(CARRIAGE_RETURN);
                        continue loop;
                    case BACKSPACE_CHAR:
                        body.append(BACKSPACE);
                        continue loop;
                    case FORM_FEED_CHAR:
                        body.append(FORM_FEED);
                        continue loop;
                    case UNICODE_CHAR:
                        if ((len - offset) < 4)
                            throw new StringParseException("CharSequence cannot escape(off " + offset + ") : " + str);

                        int unicode = 0;
                        for (int i = 0; i < 4; i++) {
                            int uni = str.charAt(offset++);
                            if (uni >= '0' && uni <= '9') {
                                unicode = (unicode << 4) + uni - '0';
                            } else if (uni >= 'a' && uni <= 'z') {
                                unicode = (unicode << 4) + uni - 'a' + 10;
                            } else if (uni >= 'A' && uni <= 'Z') {
                                unicode = (unicode << 4) + uni - 'A' + 10;
                            } else
                                throw new StringParseException("CharSequence cannot escape(off " + offset + ") : " + str);
                        }
                        body.append((char) unicode);
                        continue loop;
                    default:
                        throw new StringParseException("CharSequence cannot escape(off " + offset + ") : " + str);
                }
            }

            // 非转义
            if (quot == c)
                return new ParseHolder<>(new JSONStandardString(body.toString()), offset);
            else if (REVERSE_SOLIDUS == c)
                // 下一个字符转义
                preEscape = true;
            else body.append(c);
        }
        throw new StringParseException("CharSequence cannot parse, miss quotation mark(off " + offset + ") : " + str);
    }

    /**
     * 寻找第一个boolean标识
     * 标识有两种(false / true)，禁止任何字符不标准
     *
     * @param str    被解析的字符串
     * @param offset 初始偏移量，栈内可变量，下标
     * @return object:直接返回JSONValue实例
     *         offset:字符串的结束点下标+1
     * @throws StringParseException 字符串解析失败
     */
    private static ParseHolder<JSONValueBoolean> p2Boolean(String str, int offset) {
        final int len = str.length();
        char c = str.charAt(offset);
        int last;

        if (c == 'f') {
            last = offset + 5;
            if (last <= len && "false".equals(str.substring(offset, last)))
                return new ParseHolder<>(new JSONValueBoolean(false), last);
        } else if (c == 't') {
            last = offset + 4;
            if (last <= len && "true".equals(str.substring(offset, last)))
                return new ParseHolder<>(new JSONValueBoolean(true), last);
        }

        throw new StringParseException("CharSequence cannot parse to boolean(off " + offset + ") : " + str);
    }

    /**
     * 寻找第一个null标识
     * 只允许解析4个字符'null'，禁止任何字符不标准
     *
     * @param str    被解析的字符串
     * @param offset 初始偏移量，栈内可变量，下标
     * @return object:直接返回JSONValue实例
     *         offset:入参offset+4
     * @throws StringParseException 字符串解析失败
     */
    private static ParseHolder<JSONValueNull> p2Null(String str, int offset) {
        final int len = str.length();
        final int last = offset + 4;

        if (last <= len && "null".equals(str.substring(offset, last)))
            return new ParseHolder<>(new JSONValueNull(), last);

        throw new StringParseException("CharSequence cannot parse to null(off " + offset + ") : " + str);
    }

    /**
     * 寻找第一个JSON规范数值
     * 数值规范见{@link JSONValueNumber}
     *
     * @param str    被解析的字符串
     * @param offset 初始偏移量，栈内可变量，下标
     * @return object:直接返回JSONValue实例
     *         offset:字符串的结束点下标+1
     * @throws StringParseException 字符串解析失败
     */
    private static ParseHolder<JSONValueNumber> p2Number(String str, int offset) {
        // todo
        return null;
    }
}
