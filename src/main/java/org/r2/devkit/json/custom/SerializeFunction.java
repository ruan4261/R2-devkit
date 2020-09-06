package org.r2.devkit.json.custom;

/**
 * 自定义类型序列化方式接口
 *
 * @author ruan4261
 */
@FunctionalInterface
public interface SerializeFunction {

    String serialize(Object object);

}
