package org.r2.devkit.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 通过反射获取类的实例
 *
 * @author ruan4261
 */
public final class ConstructorFactory {

    public ConstructorFactory() {
    }

    /**
     * 通过无参构造获得参数类的一个实例
     */
    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor constructor = clazz.getDeclaredConstructor();
        if (!constructor.isAccessible()) constructor.setAccessible(true);
        return (T) constructor.newInstance();
    }

}
