package org.r2.devkit.json.serialize;

import static org.r2.devkit.json.JSONToken.DOUBLE_QUOT;

import org.r2.devkit.json.JSON;
import org.r2.devkit.serialize.CustomSerializer;
import org.r2.devkit.json.field.JSONValueNull;

/**
 * JSON序列化器
 *
 * @author ruan4261
 */
public final class JSONSerializer {

    private JSONSerializer() {
    }

    /**
     * 如果序列化对象继承了可迭代的序列化机制，本方法的序列化机制将同步
     * 优先级：
     * 1.null值的序列化只可能是null
     * 2.JSON类型使用toJSONString
     * 3.预置处理类型，如Number
     * 4.有自定义序列化方案使用自定义序列化方案
     * 5.无自定义序列化方案使用withQuotation(toString)
     */
    public static String serializer(Object object, CustomSerializer serializer) {
        if (object == null) return JSONValueNull.getInstance().toString();

        if (object instanceof JSON)
            return ((JSON) object).toJSONString();
        if (object instanceof Number) {
            return object.toString();
        }

        if (serializer != null && serializer.hasCustomizer(object))
            return withQuotation(serializer.serialize(object));

        return withQuotation(object.toString());
    }

    private static String withQuotation(String str) {
        return DOUBLE_QUOT + str + DOUBLE_QUOT;
    }

}
