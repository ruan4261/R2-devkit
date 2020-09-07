package org.r2.devkit.json;

import org.r2.devkit.json.util.Holder;
import org.r2.devkit.json.util.JSONParseCheck;
import org.r2.devkit.json.util.JSONStringParser;
import org.r2.devkit.util.Assert;

import java.io.Serializable;

/**
 * JSON键值对中的值字段
 *
 * @author ruan4261
 */
public abstract class JSON implements JSONAware, Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    /*
     常用信息
     JSON的基本信息以及本解析器的基本信息
     */
    public static final String MIME = "application/json";
    public static final String VERSION = "0.0.1";

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
    public static JSON parse(String str) {
        Assert.notEmpty(str);

        Holder<? extends JSON> holder = JSONStringParser.parse2JSON(str, 0);

        // 判断字符串剩余部分是否可忽略，不可忽略则抛出异常
        JSONParseCheck.ignore(str, holder.getOffset(), "String cannot parse to JSON : " + str);
        return holder.getObject();
    }
}