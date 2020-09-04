package org.r2.devkit.json;

/**
 * 此处枚举了Json规范的所有可用符号
 *
 * @author ruan4261
 */
public final class JSONToken {

    private JSONToken() {
        throw new AssertionError("No JSONToken instance for you!");
    }

    /*
    JSON语法符号
    [需转义]
    代表其符号如果出现在JSON字符串中，并无特殊含义，必须用反斜杠转义
    [可转义]
    代表其符号如果出现在JSON字符串中，且其前一个字符为反斜杠，则其将会被转义，否则不转义
    */
    public static final char LBRACE = '{';              // {
    public static final char RBRACE = '}';              // }
    public static final char LBRACKET = '[';            // [
    public static final char RBRACKET = ']';            // ]
    public static final char COMMA = ',';               // ,
    public static final char COLON = ':';               // :

    /*
    [特殊的转义]
    JSON格式中的标准引号为双引号
    本解析器允许调用方以单引号作为引号，但本解析器输出字符串总是会以双引号输出
    在解析字符串时，使用了某一种引号，则另一种引号在其内部无需被转义（也可以转义，并无区别)
    输出时，双引号总会被转义，单引号总不会被转义
    例如：
    解析{'key':'value'}字符串时，内部增加双引号无需反斜杠转义，但使用toJSONString输出时，双引号前会有反斜杠
    解析{"key":"value"}字符串时，内部增加单引号无影响，输出不会增加反斜杠；如果内部增加双引号，输出时会有反斜杠
    */
    public static final char QUOT = '\'';               // '
    public static final char DOUBLE_QUOT = '\"';        // " 这是规范的引号

    /* RFC4627允许json中的任何一个结构字符之前或之后都可以使用无意义的空格，包括如下四种 */
    public static final char SPACE = ' ';               // u0020 %20 空格
    public static final char LINE_FEED = '\n';          // u0010 %0A \n
    public static final char CARRIAGE_RETURN = '\r';    // u0013 %0D \r
    public static final char TAB = '\t';                // u0009 %09 \t

    /*
    反斜杠，表转义，其在JSON字符串中无法单独出现
    如JSON字符串中需出现此符号，则应用双反斜杠表不转义，如 \\
    由此，在IDE中显式地书写JSON字符串时，欲表示单个反斜杠，应用四个反斜杠 \\\\ 表示
    */
    public static final char REVERSE_SOLIDUS = '\\'; // u005c %5C \

    /* 其他可转义的符号 */
    public static final char SOLIDUS = '/';             // u002F [其转义不转义无任何区别，但如果其之前有反斜杠，反斜杠将被忽略]
    public static final char UNICODE_CHAR = 'u';        // u0075 -> uXXXX 代表Unicode编码的转义，此字符后还需要4个十六进制单位
    public static final char LINE_FEED_CHAR = 'n';      // u006E -> u0010
    public static final char TAB_CHAR = 't';            // u0074 -> u0009
    public static final char CARRIAGE_RETURN_CHAR = 'r';// u0072 -> u000D
    public static final char BACKSPACE_CHAR = 'b';      // u0062 -> u0008
    public static final char FORM_FEED_CHAR = 'f';      // u0066 -> u000C

    /* 其他转义后的符号枚举 */
    public static final char BACKSPACE = '\b';          // u0008 %08 \b
    public static final char FORM_FEED = '\f';          // u000C %0C \f

    /**
     * 在非键值内容的情况下，即解析JSON语法模式的情况下
     * 判断一个字符是否可忽视
     * 语法模式下的任何字符不可被转义
     */
    public static boolean isIgnorable(char c) {
        switch (c) {
            case SPACE:
            case LINE_FEED:
            case CARRIAGE_RETURN:
            case TAB:
                return true;
            default:
                return false;
        }
    }

    /**
     * 其是否可以接续在反斜杠 \ 之后
     */
    public static boolean isTransferable(char c) {
        switch (c) {
            case REVERSE_SOLIDUS:
            case QUOT:
            case DOUBLE_QUOT:
            case SOLIDUS:
            case UNICODE_CHAR:
            case BACKSPACE_CHAR:
            case LINE_FEED_CHAR:
            case FORM_FEED_CHAR:
            case CARRIAGE_RETURN_CHAR:
            case TAB_CHAR:
                return true;
            default:
                return false;
        }
    }
}
