package org.r2.devkit.env;

import org.r2.devkit.custom.CustomConverter;

/**
 * 全局环境下的Bean单向转换器
 *
 * @author ruan4261
 */
public final class GlobalBeanConverter extends CustomConverter {
    private static final GlobalBeanConverter instance;

    static {
        instance = new GlobalBeanConverter();
    }

    private GlobalBeanConverter() {
        if (instance != null) throw new AssertionError("GlobalBeanConverter is singleton!");
    }

    public static GlobalBeanConverter getInstance() {
        return instance;
    }

}
