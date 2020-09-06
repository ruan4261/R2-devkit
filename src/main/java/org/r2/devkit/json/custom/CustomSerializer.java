package org.r2.devkit.json.custom;

import org.r2.devkit.exception.runtime.UnsupportedClassException;
import org.r2.devkit.util.Assert;

import static org.r2.devkit.json.JSONToken.DOUBLE_QUOT;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 自定义类型的序列化方案
 *
 * @author ruan4261
 */
public final class CustomSerializer implements Map<Class, SerializeFunction>, Cloneable, Serializable {
    private static final long serialVersionUID = 3292434928445663128L;
    private static final int DEFAULT_CAPACITY = 8;
    private final Map<Class, SerializeFunction> customize;

    public CustomSerializer() {
        this.customize = new HashMap<>(DEFAULT_CAPACITY);
    }

    public CustomSerializer(int initialCapacity) {
        this.customize = new HashMap<>(initialCapacity);
    }

    public CustomSerializer(Map<Class, SerializeFunction> map) {
        Assert.notNull(map);
        this.customize = map;
    }

    /**
     * 使用自定义序列化函数进行序列化
     * 内容会被加上双引号
     *
     * @throws UnsupportedClassException 不支持此类型的自定义序列化
     */
    public String serialize(Object object) {
        return DOUBLE_QUOT + serializeContent(object) + DOUBLE_QUOT;
    }

    public String serializeContent(Object object) {
        // 对象自身带有序列化函数
        if (object instanceof SerializeFunction)
            return ((SerializeFunction) object).serialize(object);

        Class clazz = object.getClass();
        SerializeFunction function = customize.get(clazz);

        if (function == null)
            throw new UnsupportedClassException(clazz);
        else
            return function.serialize(object);
    }

    /** 判断一个对象有没有自定义的序列化机制 */
    public boolean hasOperator(Object object) {
        return this.hasOperator(object.getClass());
    }

    /** 判断一个类有没有自定义的序列化机制 */
    public boolean hasOperator(Class clazz) {
        if (SerializeFunction.class.isAssignableFrom(clazz))
            return true;
        else
            return this.containsKey(clazz);
    }

    public void register(Class clazz, SerializeFunction function) {
        this.put(clazz, function);
    }

    public void update(Class clazz, SerializeFunction function) {
        this.put(clazz, function);
    }

    public void delete(Class clazz) {
        this.remove(clazz);
    }

    @Override
    public Object clone() {
        return new CustomSerializer(this.customize);
    }

    /* Override */

    @Override
    public int size() {
        return this.customize.size();
    }

    @Override
    public boolean isEmpty() {
        return this.customize.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.customize.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.customize.containsValue(value);
    }

    @Override
    public SerializeFunction get(Object key) {
        return this.customize.get(key);
    }

    @Override
    public SerializeFunction put(Class key, SerializeFunction value) {
        return this.customize.put(key, value);
    }

    @Override
    public SerializeFunction remove(Object key) {
        return this.customize.remove(key);
    }

    @Override
    public void putAll(Map<? extends Class, ? extends SerializeFunction> m) {
        this.customize.putAll(m);
    }

    @Override
    public void clear() {
        this.customize.clear();
    }

    @Override
    public Set<Class> keySet() {
        return this.customize.keySet();
    }

    @Override
    public Collection<SerializeFunction> values() {
        return this.customize.values();
    }

    @Override
    public Set<Entry<Class, SerializeFunction>> entrySet() {
        return this.customize.entrySet();
    }
}