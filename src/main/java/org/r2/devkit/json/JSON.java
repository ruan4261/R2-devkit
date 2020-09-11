package org.r2.devkit.json;

import org.r2.devkit.json.util.Holder;
import org.r2.devkit.json.util.JSONParseCheck;
import org.r2.devkit.json.util.JSONStringParser;
import org.r2.devkit.util.Assert;
import org.r2.devkit.util.BeanUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON键值对中的值字段
 *
 * @author ruan4261
 */
public abstract class JSON implements JSONAware, Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    public static final String MIME = "application/json";

    @Override
    public String toString() {
        return this.toJSONString();
    }

    @Override
    public abstract Object clone();

    /**
     * 解析JSON字符串
     * 如果传入的值不是JSON字符串，而是JSON字符串中的某一类型，也会解析成功
     *
     * @return JSON对象的实现
     * @throws JSONException 字符串不规范，无法解析
     */
    public static JSON parse(String str) {
        Assert.notEmpty(str);

        Holder<? extends JSON> holder = JSONStringParser.parse2JSON(str, 0);

        // 判断字符串剩余部分是否可忽略，不可忽略则抛出异常
        JSONParseCheck.ignore(str, holder.getOffset(), "String cannot parse to JSON : " + str);
        return holder.getObject();
    }

    public static <T> T parse(String str, Class<T> clazz) {
        JSON json = parse(str);
        if (json instanceof JSONObject) {
            return BeanUtil.map2Object((JSONObject) json, clazz);
        } else throw new JSONException(json.getClass().toString() + " cannot convert to " + clazz.toString());
    }

    public static <T> List<T> parseArray(String str, Class<T> clazz) {
        JSON json = parse(str);
        if (json instanceof JSONArray) {
            JSONArray arr = (JSONArray) json;
            final List<T> res = new ArrayList<>(arr.size());
            arr.forEach(object -> {
                if (object instanceof JSONObject)
                    res.add(BeanUtil.map2Object((JSONObject) object, clazz));
                // do nothing
            });
            return res;
        } else throw new JSONException(json.getClass().toString() + " cannot convert to " + clazz.toString());
    }
}