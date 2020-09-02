package org.r2.devkit.json;

import org.r2.devkit.exception.StringParseException;
import org.r2.devkit.json.field.JSONArray;
import org.r2.devkit.json.field.JSONField;
import org.r2.devkit.json.field.JSONObject;
import org.r2.devkit.util.Assert;

/**
 * @author ruan4261
 */
public interface JSONAware {

    JSONField getEnumType();

    /**
     * 通过接口静态方法解析一个json字符串
     *
     * @return JSON对象的实现：JSONObject或JSONArray, 其他情况会抛出异常。
     */
    static JSON parse(String json) {
        Assert.notEmpty(json);
        int len = json.length();
        for (int i = 0; i < len; i++) {
            char c = json.charAt(i);
            if (JSONToken.isIgnorable(c)) continue;
            if (JSONToken.LBRACE.getChar() == c)
                return JSONObject.parse(json);
            if (JSONToken.LBRACKET.getChar() == c)
                return JSONArray.parse(json);
            throw new StringParseException("JsonAware Cannot parse string : " + json);
        }
        throw new StringParseException("JsonAware Cannot parse string : " + json);
    }
}