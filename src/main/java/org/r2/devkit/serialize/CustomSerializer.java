package org.r2.devkit.serialize;

import org.r2.devkit.exception.runtime.IllegalDataException;
import org.r2.devkit.exception.runtime.UnsupportedClassException;
import org.r2.devkit.reflect.ReflectUtil;
import org.r2.devkit.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * 自定义多类型的序列化方案
 *
 * @author ruan4261
 */
public final class CustomSerializer implements Cloneable, Serializable {
    private static final long serialVersionUID = 8773279300874944769L;
    private static final int DEFAULT_CAPACITY = 8;
    private final Map<Class, Bucket> customize;

    public CustomSerializer() {
        this.customize = new HashMap<>(DEFAULT_CAPACITY);
    }

    public CustomSerializer(int initialCapacity) {
        this.customize = new HashMap<>(initialCapacity);
    }

    public CustomSerializer(Map<Class, Bucket> map) {
        Assert.notNull(map);
        this.customize = new HashMap<>(map);
    }

    /**
     * 使用自定义序列化函数进行序列化
     *
     * @throws UnsupportedClassException 不支持此类型的自定义序列化
     */
    @SuppressWarnings("unchecked")
    public String serialize(Object object) {
        Assert.notNull(object);
        return this.prioritySerializer(object).serialize(object);
    }

    /** @see CustomSerializer#prioritySerializer(Class) */
    public Serializer prioritySerializer(Object object) {
        return this.prioritySerializer(object.getClass());
    }

    /**
     * 获取对于目标类型优先级最高的序列化器
     *
     * @throws UnsupportedClassException 不支持此类型的自定义序列化
     */
    @SuppressWarnings("unchecked")
    public <T> Serializer<T> prioritySerializer(Class<T> clazz) {
        Set<Class> classes = ReflectUtil.getAllSuper(clazz);

        final int[] max = {Integer.MIN_VALUE};
        final Serializer[] functions = {null};

        classes.stream().anyMatch(c -> {
            Bucket bucket = this.customize.get(c);
            if (bucket != null) {
                if (bucket.level > max[0]) {
                    max[0] = bucket.level;
                    functions[0] = bucket.serializer;

                    return max[0] == Integer.MAX_VALUE;
                }
            }
            return false;
        });

        if (functions[0] == null) throw new UnsupportedClassException(clazz);
        return functions[0];
    }

    /** @see CustomSerializer#hasCustomizer(Class) */
    public boolean hasCustomizer(Object object) {
        return this.hasCustomizer(object.getClass());
    }

    /**
     * 判断一个类有没有自定义的序列化机制
     * 如果其实现的接口或其超类或其超类实现的接口有自定义序列化机制，也算作其的自定义序列化机制
     */
    public boolean hasCustomizer(Class clazz) {
        Set<Class> allClass = ReflectUtil.getAllSuper(clazz);
        for (Class c : allClass)
            if (this.customize.containsKey(c)) return true;

        return false;
    }

    public <T> void register(Class<T> clazz, int level, Serializer<T> function) {
        this.customize.put(clazz, new Bucket(level, function));
    }

    public int queryLevel(Class clazz) {
        return this.customize.get(clazz).level;
    }

    @SuppressWarnings("unchecked")
    public <T> Serializer<T> querySerializer(Class<T> clazz) {
        return this.customize.get(clazz).serializer;
    }

    public void update(Class clazz, int level) {
        Bucket bucket = this.customize.get(clazz);
        if (bucket == null)
            throw new IllegalDataException(clazz.getTypeName() + " doesn't have custom serializer.");

        bucket.level = level;
    }

    public <T> void update(Class<T> clazz, Serializer<T> function) {
        Bucket bucket = this.customize.get(clazz);
        if (bucket == null)
            throw new IllegalDataException(clazz.getTypeName() + " doesn't have custom serializer.");

        bucket.serializer = function;
    }

    public void delete(Class clazz) {
        this.customize.remove(clazz);
    }

    @Override
    public Object clone() {
        return new CustomSerializer(this.customize);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CustomSerializer that = (CustomSerializer) object;
        return Objects.equals(customize, that.customize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customize);
    }

    @Override
    public String toString() {
        return customize.toString();
    }

    /**
     * 序列化函数以及其优先级
     */
    private static final class Bucket {
        private int level;
        private Serializer serializer;

        public Bucket(int level, Serializer serializer) {
            this.level = level;
            this.serializer = serializer;
        }
    }

}