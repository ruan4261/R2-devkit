package org.r2.devkit.env;

import org.r2.devkit.core.CacheBase;
import org.r2.devkit.Cast;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 当前应用环境变量
 * 请注意控制值的类型
 *
 * @author ruan4261
 */
public final class EnvArgument implements CacheBase {

    private static final EnvArgument instance;

    private final Map<String, Object> env;

    static {
        instance = new EnvArgument();
    }

    private EnvArgument() {
        env = new ConcurrentHashMap<>();
    }

    public EnvArgument env() {
        return instance;
    }

    public void put(String key, Object value) {
        this.env.put(key, value);
    }

    public Object get(String key) {
        return this.env.get(key);
    }

    public String getString(String key) {
        return Cast.o2String(this.env.get(key));
    }

    public void clear() {
        this.env.clear();
    }

    public int size() {
        return this.env.size();
    }

}
