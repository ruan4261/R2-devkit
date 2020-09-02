package org.r2.devkit.json.field;

import org.r2.devkit.json.JSON;
import org.r2.devkit.json.field.JSONField;
import org.r2.devkit.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 对象类型Json格式化工具
 *
 * @author ruan4261
 */
public final class JSONObject extends JSON implements Map<String, Object>, Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JsonObject;
    private final Map<String, Object> container;

    public JSONObject() {
        this(8);
    }

    public JSONObject(int initialCapacity) {
        this.container = new HashMap<>(initialCapacity);
    }

    public JSONObject(Map<String, Object> map) {
        Assert.notNull(map);
        this.container = new HashMap<>(map);
    }

    public static JSONObject parse(Object object) {
        if (object instanceof String) {
            return parse(((String) object).toCharArray(), 0);
        }
        return null;
    }

    public static JSONObject parse(char[] chars, int offset) {
        return null;
    }

    @Override
    public String toString() {
        return null;
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
    public Object get(Object key) {
        return this.container.get(key);
    }

    @Override
    public Object put(String key, Object value) {
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
