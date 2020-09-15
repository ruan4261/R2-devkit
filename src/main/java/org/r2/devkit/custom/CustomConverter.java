package org.r2.devkit.custom;

import org.r2.devkit.exception.runtime.IllegalDataException;
import org.r2.devkit.util.Assert;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义的JavaBean单向转换器组
 * 不推荐注册子类向父类的转换，该情况应该使用Java强制转换类型
 * 如果注册了子类向父类的转换，并不能一定保证该转换器生效
 *
 * @author ruan4261
 */
public class CustomConverter {

    private final ConcurrentHashMap<String, ConvertGroup> beanConvertGroup;

    {
        this.beanConvertGroup = new ConcurrentHashMap<>();
    }

    /**
     * 注册一个单向转换器
     * 这个方法不允许覆盖之前的转换器
     *
     * @return 是否注册成功，如果已存在相应类型转换器，则返回false
     */
    public <O, T> boolean register(Class<O> origin, Class<T> target, Converter<O, T> converter) {
        Assert.notNull(origin);
        Assert.notNull(target);
        Assert.notNull(converter);
        String key = origin.toString() + target.toString();
        return this.beanConvertGroup.putIfAbsent(key, new ConvertGroup<>(origin, target, converter)) == null;
    }

    /**
     * 更新一个单向转换器，此方法一定成功
     */
    public <O, T> void update(Class<O> origin, Class<T> target, Converter<O, T> converter) {
        Assert.notNull(origin);
        Assert.notNull(target);
        Assert.notNull(converter);
        String key = origin.toString() + target.toString();
        this.beanConvertGroup.put(key, new ConvertGroup<>(origin, target, converter));
    }

    /**
     * 删除一个单向转换器，此方法一定成功
     */
    public <O, T> void remove(Class<O> origin, Class<T> target, Converter<O, T> converter) {
        Assert.notNull(origin);
        Assert.notNull(target);
        Assert.notNull(converter);
        String key = origin.toString() + target.toString();
        this.beanConvertGroup.remove(key);
    }

    /**
     * 使用预先注册的转换器将实例origin转换为target实例
     *
     * @throws IllegalDataException 不存在相应类型转换器
     */
    @SuppressWarnings("unchecked")
    public <O, T> T convert(Class<T> target, O origin) {
        Assert.notNull(origin);
        Assert.notNull(target);
        String key = origin.getClass().toString() + target.toString();
        ConvertGroup<O, T> convertGroup = this.beanConvertGroup.get(key);
        Assert.notNull(convertGroup, "Cannot find Converter, may it was not registered.");
        return convertGroup.converter.convert(origin);
    }

    /**
     * 是否存在arg0向arg1的转换器
     */
    public <O, T> boolean isExist(Class<O> origin, Class<T> target) {
        Assert.notNull(origin);
        Assert.notNull(target);
        String key = origin.toString() + target.toString();
        return this.beanConvertGroup.containsKey(key);
    }

    private static class ConvertGroup<O, T> {
        Class<O> origin;
        Class<T> target;
        Converter<O, T> converter;

        ConvertGroup(Class<O> origin, Class<T> target, Converter<O, T> converter) {
            this.origin = origin;
            this.target = target;
            this.converter = converter;
        }

        T convert(O o) {
            return this.converter.convert(o);
        }

        @Override
        public String toString() {
            return origin.toString() + target.toString();
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            ConvertGroup<?, ?> that = (ConvertGroup<?, ?>) object;
            return Objects.equals(origin, that.origin) &&
                    Objects.equals(target, that.target) &&
                    Objects.equals(converter, that.converter);
        }

        @Override
        public int hashCode() {
            return Objects.hash(origin, target, converter);
        }
    }

    @FunctionalInterface
    public interface Converter<O, T> {
        T convert(O o);
    }

}
