package org.r2.devkit.json;

import org.r2.devkit.util.Assert;

import java.io.Serializable;

/**
 * JSON键值对中的值字段
 *
 * @author ruan4261
 */
public abstract class JSON implements JSONAware, Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return this.toJSONString();
    }

    @Override
    public abstract Object clone();

    /**
     * 解析一个完整的json字符串的接口
     *
     * @return JSON对象的实现：JSONObject或JSONArray, 其他情况会抛出异常。
     * @throws JSONException 字符串不规范，无法解析
     */
    public static JSON parse(String json) {
        Assert.notEmpty(json);
        int len = json.length();
        for (int i = 0; i < len; i++) {
            char c = json.charAt(i);
            if (JSONToken.isIgnorable(c)) continue;
            if (JSONToken.LBRACE == c)
                return JSONObject.parseObject(json);
            if (JSONToken.LBRACKET == c)
                return JSONArray.parseArray(json);
            throw new JSONException("Cannot parse string : " + json);
        }
        throw new JSONException("Cannot parse string : " + json);
    }
}