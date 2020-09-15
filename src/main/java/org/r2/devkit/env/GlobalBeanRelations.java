package org.r2.devkit.env;

/**
 * 全局环境下的Bean关系表
 *
 * @author ruan4261
 */
public final class GlobalBeanRelations {
    private static final GlobalBeanRelations instance;

    static {
        instance = new GlobalBeanRelations();
    }

    private GlobalBeanRelations() {
        if (instance != null) throw new AssertionError("GlobalBeanRelations is singleton!");
    }

    public static GlobalBeanRelations getInstance() {
        return instance;
    }

    // todo

}
