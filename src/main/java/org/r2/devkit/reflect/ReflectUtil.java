package org.r2.devkit.reflect;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ruan4261
 */
public final class ReflectUtil {

    private ReflectUtil(){
    }

    /**
     * 获取类的所有实现的接口以及超类
     * 包括更上层的超类和他们实现的接口
     */
    public static Set<Class> getAllSuper(Class clazz) {
        Set<Class> classes = new HashSet<>();

        while (clazz != null) {
            classes.add(clazz);
            classes.addAll(Arrays.asList(clazz.getInterfaces()));

            clazz = clazz.getSuperclass();
        }

        return classes;
    }

}
