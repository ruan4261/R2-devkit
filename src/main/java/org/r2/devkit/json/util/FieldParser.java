package org.r2.devkit.json.util;

import org.r2.devkit.json.JSONException;
import org.r2.devkit.json.field.*;

import static org.r2.devkit.json.JSONToken.*;
import static org.r2.devkit.json.JSONToken.REVERSE_SOLIDUS;

/**
 * 此protected接口用于解析不可再分解的JSON字段
 * 且本接口要求入参完全标准
 * 也就是说，从首个开始解析的字符起，每个字符都要有效，不支持JSON语法可忽略字符等
 * 所有方法默认首字符是正确的
 *
 * @author ruan4261
 * @see JSONValueString
 * @see JSONValueNumber
 * @see JSONValueBoolean
 * @see JSONValueNull
 */
interface FieldParser {

    /**
     * 从str的下标offset开始，解析出一个JSONValueString对象，将会遵循RFC4627对内容进行转义
     *
     * [入参规则]
     * str以offset为起点的首个字符必须为quot
     * 例如：
     * str = "'xxx'", offset = 0, char = '\''
     * str = "\"xxx\"", offset = 0, char = '\"'
     *
     * [说明]
     * 解析时会跳过第一个offset字符，因为其默认为quot
     * 再次解析到quot字符时（非转义情况下；如果是转义情况下，quot是可用字符，继续解析）解析结束
     *
     * [转义]
     * 4个可忽略字符，8个可转义字符详见{@link org.r2.devkit.json.JSONToken}
     *
     * @param str    被解析的字符串
     * @param offset 解析开始的偏移量
     * @param quot   该字符串使用的边界引号
     * @throws JSONException 解析失败
     */
    static Holder<JSONValueString> p2String(String str, int offset, char quot) {
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
                            throw new JSONException("String cannot escape(off " + offset + ") : " + str);

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
                                throw new JSONException("String cannot escape(off " + offset + ") : " + str);
                        }
                        body.append((char) unicode);
                        continue loop;
                    default:
                        throw new JSONException("String cannot escape(off " + offset + ") : " + str);
                }
            }

            // 非转义
            if (quot == c)
                return new Holder<>(new JSONValueString(body.toString()), offset);
            else if (REVERSE_SOLIDUS == c)
                // 下一个字符转义
                preEscape = true;
            else body.append(c);
        }
        throw new JSONException("String cannot parse, miss quotation mark(off " + offset + ") : " + str);
    }

    /**
     * 从str的下标offset开始，解析出一个JSONValueNumber对象
     *
     * [说明]
     * 遇到JSON语法可忽略字符或 , 或 } 或 ] 字符时返回解析结果
     *
     * [模式]
     * 仅支持十进制模式，允许科学计数法，详见{@link org.r2.devkit.json.field.JSONValueNumber}
     *
     * @param str    被解析的字符串
     * @param offset 解析开始的偏移量
     * @throws JSONException 解析失败
     */
    static Holder<JSONValueNumber> p2Number(String str, int offset) {
        final int len = str.length();
        final int start = offset;
        offset++;

        // verify
        boolean point = false;
        boolean exponent = false;
        boolean sign = false;

        for (; offset < len; offset++) {
            char c = str.charAt(offset);
            // end
            if (isIgnorable(c) || c == COMMA || c == RBRACE || c == RBRACKET) {
                JSONValueNumber number = new JSONValueNumber(str.substring(start, offset));
                return new Holder<>(number, offset);
            } else if (c == '.') {
                if (point)
                    throw new JSONException("String cannot parse to number(off " + offset + ") : " + str);
                point = true;
            } else if (c == 'e' || c == 'E') {
                if (exponent)
                    throw new JSONException("String cannot parse to number(off " + offset + ") : " + str);
                exponent = point = true;
            } else if (c == '-' || c == '+') {
                if (sign)
                    throw new JSONException("String cannot parse to number(off " + offset + ") : " + str);
                if (!point || !exponent)
                    throw new JSONException("String cannot parse to number(off " + offset + ") : " + str);

                char prev = str.charAt(offset - 1);
                if (prev != 'e' && prev != 'E')
                    throw new JSONException("String cannot parse to number(off " + offset + ") : " + str);

                sign = true;
            } else if (!(c >= '0' && c <= '9'))
                throw new JSONException("String cannot parse to number(off " + offset + ") : " + str);
        }

        throw new JSONException("String cannot parse to number(off " + offset + ") : " + str);
    }

    /**
     * 从str的下标offset开始，解析出一个JSONValueBoolean对象
     *
     * [说明]
     * 根据首字符抽取相应长度的str内容
     * 与标准boolean值进行比较
     *
     * @param str    被解析的字符串
     * @param offset 解析开始的偏移量
     * @throws JSONException 解析失败
     */
    static Holder<JSONValueBoolean> p2Boolean(String str, int offset) {
        final int len = str.length();
        char c = str.charAt(offset);
        int last;

        if (c == 'f') {
            last = offset + 5;
            if (last <= len && "false".equals(str.substring(offset, last)))
                return new Holder<>(JSONValueBoolean.getInstanceFalse(), last);
        } else if (c == 't') {
            last = offset + 4;
            if (last <= len && "true".equals(str.substring(offset, last)))
                return new Holder<>(JSONValueBoolean.getInstanceTrue(), last);
        }

        throw new JSONException("String cannot parse to boolean(off " + offset + ") : " + str);
    }

    /**
     * 从str的下标offset开始，解析出一个JSONValueNumber对象
     *
     * [说明]
     * 遇到 , 或 } 或 ] 字符时返回解析结果
     *
     * [模式]
     * 仅支持十进制模式，允许科学计数法，详见{@link org.r2.devkit.json.field.JSONValueNumber}
     *
     * @param str    被解析的字符串
     * @param offset 解析开始的偏移量
     * @throws JSONException 解析失败
     */
    static Holder<JSONValueNull> p2Null(String str, int offset) {
        final int len = str.length();
        final int last = offset + 4;

        if (last <= len && "null".equals(str.substring(offset, last)))
            return new Holder<>(JSONValueNull.getInstance(), last);

        throw new JSONException("String cannot parse to null(off " + offset + ") : " + str);
    }
}
