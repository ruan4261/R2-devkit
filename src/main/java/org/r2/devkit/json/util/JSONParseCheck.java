package org.r2.devkit.json.util;

import org.r2.devkit.json.JSONException;
import org.r2.devkit.json.JSONToken;

/**
 * 对JSON解析检查的工具类
 */
public final class JSONParseCheck {

    private JSONParseCheck() {
    }

    /**
     * 判断在参数str中下标offset之后的内容是否可以忽视
     * 可忽视的字符见{@link JSONToken}
     *
     * @param mes 自定义异常信息
     * @throws JSONException offset之后有不可忽视字符
     */
    public static void ignore(String str, int offset, String mes) {
        final int len = str.length();
        for (; offset < len; ) {
            char c = str.charAt(offset++);
            if (!JSONToken.isIgnorable(c))
                throw new JSONException(mes);
        }
    }

}
