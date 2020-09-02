package org.r2.devkit.json;

import org.r2.devkit.exception.runtime.IllegalDataException;

/**
 * Json规范的可用符号
 * 除键值外其余所有字符必须在此规定内
 *
 * @author ruan4261
 */
public enum JSONToken {

    LBRACE,// {
    RBRACE,// }
    LBRACKET,// [
    RBRACKET,// ]
    COMMA,// ,
    COLON,// :
    /* RFC4627允许json中的任何一个结构字符之前或之后都可以使用无意义的空格，包括如下四种 */
    SPACE(true),// u0020 %20 空格
    NEXT(true),// u0010 %0A \n
    RETURN(true),// u0013 %0D \r
    TAB(true),// u0009 %09 \t
    OTHERS;// 保留值，代表不符合规范的所有值

    boolean ignorable;

    JSONToken(boolean ignorable) {
        this.ignorable = ignorable;
    }

    JSONToken() {
        this.ignorable = false;
    }

    /**
     * 通过枚举类型获得枚举的实际符号
     */
    public char getChar() {
        switch (this) {
            case LBRACE:
                return '{';
            case RBRACE:
                return '}';
            case LBRACKET:
                return '[';
            case RBRACKET:
                return ']';
            case COMMA:
                return ',';
            case COLON:
                return ':';
            case SPACE:
                return ' ';
            case NEXT:
                return '\n';
            case RETURN:
                return '\r';
            case TAB:
                return '\t';
            default:
                throw new IllegalDataException("JsonToken do not support " + this.toString());
        }
    }

    /**
     * 通过符号匹配枚举
     * 如果符号不属于JSONToken，则返回OTHERS类型枚举
     */
    public static JSONToken get(char c) {
        switch (c) {
            case '{':
                return LBRACE;
            case '}':
                return RBRACE;
            case '[':
                return LBRACKET;
            case ']':
                return RBRACKET;
            case ',':
                return COMMA;
            case ':':
                return COLON;
            case ' ':
                return SPACE;
            case '\n':
                return NEXT;
            case '\r':
                return RETURN;
            case '\t':
                return TAB;
            default:
                return OTHERS;
        }
    }

    /**
     * 判断一个字符是否可忽视
     */
    public static boolean isIgnorable(char c) {
        return get(c).ignorable;
    }
}
