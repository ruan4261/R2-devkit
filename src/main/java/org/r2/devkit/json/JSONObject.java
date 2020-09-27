package org.r2.devkit.json;

import org.r2.devkit.serialize.CustomSerializer;
import org.r2.devkit.json.custom.CustomizableSerialization;
import org.r2.devkit.json.serialize.JSONSerializer;
import org.r2.devkit.json.util.JSONParseCheck;
import org.r2.devkit.json.util.JSONStringParser;
import org.r2.devkit.json.util.Holder;
import org.r2.devkit.Assert;

import java.util.*;

/**
 * @author ruan4261
 */
public final class JSONObject extends JSON implements CustomizableSerialization, Map<String, Object> {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_CAPACITY = 8;
    private final Map<String, Object> container;
    // serialization solution
    private CustomSerializer customSerializer;

    public JSONObject() {
        this(DEFAULT_CAPACITY);
    }

    public JSONObject(int initialCapacity) {
        this.container = new HashMap<>(initialCapacity);
    }

    public JSONObject(Map<String, Object> map) {
        Assert.notNull(map, "map");
        this.container = map;
    }

    /**
     * newContainer为true的情况下，将构造一个新的Map用于内部数据存储
     * newContainer为false的情况下，该构造等同于普通的Map构造
     */
    public JSONObject(Map<String, Object> map, boolean newContainer) {
        Assert.notNull(map, "map");
        if (newContainer)
            this.container = new HashMap<>(map);
        else
            this.container = map;
    }

    /**
     * 完全替换当前对象序列化机制1
     */
    @Override
    public void setCustomSerializer(CustomSerializer customSerializer) {
        this.customSerializer = customSerializer;
    }

    @Override
    public CustomSerializer getCustomSerializer() {
        return this.customSerializer;
    }

    /**
     * 删除当前对象序列化机制
     */
    @Override
    public void removeCustomSerializer() {
        this.customSerializer = null;
    }

    /**
     * 完整字符串解析调用接口
     */
    public static JSONObject parseObject(String str) {
        Holder<JSONObject> holder = JSONStringParser.parse2JSONObject(str, 0);

        // 判断字符串剩余部分是否可忽略，不可忽略则抛出异常
        JSONParseCheck.ignore(str, holder.getOffset(), "String cannot parse to JSONObject : " + str);

        return holder.getObject();
    }

    public Map<String, Object> innerMap() {
        return this.container;
    }

    /**
     * output json string
     */
    @Override
    public String toJSONString() {
        return JSONSerializer.map2JSONString(this, this.customSerializer);
    }

    @Override
    public String toString() {
        return this.toJSONString();
    }

    @Override
    public Object clone() {
        return new JSONObject(new HashMap<>(this.container));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        JSONObject that = (JSONObject) object;
        return Objects.equals(container, that.container);
    }

    @Override
    public int hashCode() {
        return Objects.hash(container);
    }

    /* Override */

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

    public Object get(String key) {
        return this.container.get(key);
    }

    @Override
    public Object get(Object key) {
        return this.container.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        Assert.notNull(key, "key");
        return this.container.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return this.container.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        this.container.putAll(m);
    }

    @Override
    public void clear() {
        this.container.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.container.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.container.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.container.entrySet();
    }

}
