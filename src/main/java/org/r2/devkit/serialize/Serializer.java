package org.r2.devkit.serialize;

/**
 * 序列化器接口
 * 本接口仅作为匿名函数式接口，请勿实现本接口！
 * 就算实现本接口，其他序列化器也不会调用实现类实现的接口方法！
 *
 * @author ruan4261
 */
@FunctionalInterface
public interface Serializer<T> {

    String serialize(T object);

}
