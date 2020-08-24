package org.r2.devkit.env;

import org.r2.devkit.core.CacheBase;
import org.r2.devkit.util.Cast;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 当前应用环境变量
 * 请注意控制值的类型
 *
 * @author ruan4261
 */
public final class EnvArgument implements CacheBase {

    private static final Map<String, Object> env;

    static {
        env = new ConcurrentHashMap<>();
    }

    private EnvArgument() {
    }

    public static void put(String key, Object value) {
        env.put(key, value);
    }

    public static Object get(String key) {
        return env.get(key);
    }

    public static String getString(String key) {
        return Cast.toString(env.get(key));
    }

    public static void clear() {
        env.clear();
    }

    public static int size() {
        return env.size();
    }

}
