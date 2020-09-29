package org.r2.devkit.env;

import org.r2.devkit.Cast;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 当前应用环境变量
 * 请注意控制值的类型
 *
 * @author ruan4261
 */
public final class EnvArgument implements ConcurrentMap<String, Object> {

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

    public Object put(String key, Object value) {
        return this.env.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return this.env.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        this.env.putAll(m);
    }

    @Override
    public Object get(Object key) {
        return this.env.get(key);
    }

    public String getString(String key) {
        return Cast.o2String(this.env.get(key));
    }

    public void clear() {
        this.env.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.env.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.env.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.env.entrySet();
    }

    public int size() {
        return this.env.size();
    }

    @Override
    public boolean isEmpty() {
        return this.env.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.env.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.env.containsValue(value);
    }

    @Override
    public Object putIfAbsent(String key, Object value) {
        return this.env.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return this.env.remove(key, value);
    }

    @Override
    public boolean replace(String key, Object oldValue, Object newValue) {
        return this.env.replace(key, oldValue, newValue);
    }

    @Override
    public Object replace(String key, Object value) {
        return this.env.replace(key, value);
    }
}
