package org.r2.devkit.json.serialize;

import org.r2.devkit.CharLinkedSequence;
import org.r2.devkit.json.JSON;
import org.r2.devkit.json.JSONObject;
import org.r2.devkit.serialize.CustomSerializer;
import org.r2.devkit.json.field.JSONValueNull;
import org.r2.devkit.Assert;
import org.r2.devkit.bean.BeanUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.r2.devkit.json.JSONToken.*;

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
     * 2.确切的类自定义序列化方案
     * 3.Map，集合框架，数组
     * 4.除框架以外的JSON类型使用toJSONString
     * 5.Number实现，直接使用toString，返回无引号数值
     * 6.字符串序列实现，使用toString加边界双引号
     * 7.有可继承的自定义序列化方案
     * 8.重写了toString()的JavaBean
     * 9.没有重写toString的JavaBean，默认为贫血对象，通过反射构造出实例状态的JSON对象
     */
    @SuppressWarnings("unchecked")
    public static <T> String serializer(Object object, CustomSerializer serializer) {
        // 1
        if (object == null)
            return JSONValueNull.getInstance().toString();
        // 2
        if (serializer != null && serializer.isExistClassSerializer(object))
            return escapeAndQuot(serializer.classSerializer(object).serialize(object));
        // 3
        if (object instanceof Map)
            return map2JSONString((Map) object, serializer);
        if (object instanceof Collection)
            return collection2JSONString((Collection) object, serializer);
        if (object.getClass().isArray())
            return array2JSONString(object, serializer);
        // 4
        if (object instanceof JSON)
            return ((JSON) object).toJSONString();
        // 5
        if (object instanceof Number)
            return object.toString();
        // 6
        if (object instanceof CharSequence)
            return escapeAndQuot(object.toString());
        // 7
        if (serializer != null && serializer.hasCustomizer(object))
            return escapeAndQuot(serializer.serialize(object));
        // 8
        if (BeanUtil.hasOwnMethod(object.getClass(), "toString", 8))
            return escapeAndQuot(object.toString());
        // 9
        return reflect2JSONString(object);
    }

    /**
     * 通过反射将贫血模型转换为JSONObject再输出字符串
     * static, transient 关键字修饰的字段将被过滤
     */
    public static String reflect2JSONString(Object object) {
        return new JSONObject(BeanUtil.object2Map(object, (8 + 128))).toJSONString();
    }

    /**
     * 生成一个JSONValueString字段
     * 此方法会根据JSON标准对字符串内的字符进行转义
     * 并且在字符串边界加上双引号
     */
    public static String escapeAndQuot(String str) {
        CharLinkedSequence sequence = new CharLinkedSequence(str);
        sequence.replace(c -> {
            if (isEscapeChar(c))
                return escape(c);
            else
                return null;
        });
        return DOUBLE_QUOT + sequence.toString() + DOUBLE_QUOT;
    }

    @SuppressWarnings("unchecked")
    public static String map2JSONString(Map map, CustomSerializer serializer) {
        StringBuilder builder = new StringBuilder((map.size() << 3) + 4);
        builder.append(LBRACE);

        // body
        map.forEach((k, v) -> {
            // key
            builder.append(escapeAndQuot(k.toString()));

            // :
            builder.append(COLON);

            // value
            builder.append(serializer(v, serializer));

            // ,
            builder.append(COMMA);
        });

        // delete last comma
        int last = builder.length() - 1;
        if (builder.charAt(last) == COMMA)
            builder.deleteCharAt(last);

        builder.append(RBRACE);
        return builder.toString();
    }

    @SuppressWarnings("unchecked")
    public static String collection2JSONString(Collection collection, CustomSerializer serializer) {
        final StringBuilder builder = new StringBuilder((collection.size() << 2) + 4);
        builder.append(LBRACKET);

        collection.forEach(
                object -> builder.append(serializer(object, serializer)).append(COMMA)
        );

        // delete last comma
        int last = builder.length() - 1;
        if (builder.charAt(last) == COMMA)
            builder.deleteCharAt(last);

        builder.append(RBRACKET);
        return builder.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> String array2JSONString(Object object, CustomSerializer serializer) {
        Assert.judge(() -> !object.getClass().isArray());
        return array2JSONString((T[]) object, serializer);
    }

    public static <T> String array2JSONString(T[] a, CustomSerializer serializer) {
        final StringBuilder builder = new StringBuilder((a.length << 2) + 4);
        builder.append(LBRACKET);

        Arrays.stream(a).forEach(
                object -> builder.append(serializer(object, serializer)).append(COMMA)
        );

        // delete last comma
        int last = builder.length() - 1;
        if (builder.charAt(last) == COMMA)
            builder.deleteCharAt(last);

        builder.append(RBRACKET);
        return builder.toString();
    }
}
