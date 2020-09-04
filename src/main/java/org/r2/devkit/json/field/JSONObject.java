package org.r2.devkit.json.field;

import static org.r2.devkit.json.JSONToken.*;

import org.r2.devkit.exception.StringParseException;
import org.r2.devkit.json.JSONToken;
import org.r2.devkit.json.util.JSONParseCheck;
import org.r2.devkit.json.util.JSONStringParser;
import org.r2.devkit.json.util.ParseHolder;
import org.r2.devkit.util.Assert;

import java.util.*;

/**
 * 对象类型Json格式化工具
 * JSONString表现为
 * {
 * JSONKey:JSONValue,
 * JSONKey:JSONValue
 * }
 *
 * @author ruan4261
 */
public final class JSONObject extends JSONValue implements Map<JSONStandardString, JSONValue> {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JSONObject;
    private final Map<JSONStandardString, JSONValue> container;

    public JSONObject() {
        this(8);
    }

    public JSONObject(int initialCapacity) {
        this.container = new HashMap<>(initialCapacity);
    }

    public JSONObject(Map<JSONStandardString, JSONValue> map) {
        Assert.notNull(map);
        this.container = new HashMap<>(map);
    }

    /**
     * 用户调用接口
     * 完整解析
     */
    public static JSONObject parse(String str) {
        ParseHolder<JSONObject> holder = JSONStringParser.parse2JSONObject(str, 0);

        // 判断字符串剩余部分是否可忽略，不可忽略则抛出异常
        JSONParseCheck.ignore(str, holder.getOffset(), "CharSequence cannot parse to JSONObject : " + str);

        return holder.getObject();
    }

    @Override
    public String toString() {
        return toJSONString();
    }

    /**
     * 输出对象类型的JSON字符串
     */
    @Override
    public String toJSONString() {
        final StringBuilder builder = new StringBuilder(String.valueOf(LBRACE));
        container.forEach((k, v) -> {
            // key
            builder.append(k.toJSONString());
            // :
            builder.append(COLON);
            // value
            builder.append(v.toJSONString());
            // ,
            builder.append(COMMA);
        });

        int last = builder.length() - 1;
        if (builder.charAt(last) == COMMA)
            builder.deleteCharAt(last);

        builder.append(RBRACE);
        return builder.toString();
    }

    @Override
    public JSONField getEnumType() {
        return TYPE;
    }

    /* Map Override */

    @Override
    public int size() {
        return this.container.size();
    }

    @Override
    public boolean isEmpty() {
        return this.container.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.container.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.container.containsValue(value);
    }

    @Override
    public JSONValue get(Object key) {
        return this.container.get(key);
    }

    @Override
    public JSONValue put(JSONStandardString key, JSONValue value) {
        return this.container.put(key, value);
    }

    @Override
    public JSONValue remove(Object key) {
        return this.container.remove(key);
    }

    @Override
    public void putAll(Map<? extends JSONStandardString, ? extends JSONValue> m) {
        this.container.putAll(m);
    }

    @Override
    public void clear() {
        this.container.clear();
    }

    @Override
    public Set<JSONStandardString> keySet() {
        return this.container.keySet();
    }

    @Override
    public Collection<JSONValue> values() {
        return this.container.values();
    }

    @Override
    public Set<Entry<JSONStandardString, JSONValue>> entrySet() {
        return this.container.entrySet();
    }

}
