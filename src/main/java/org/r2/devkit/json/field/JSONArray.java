package org.r2.devkit.json.field;

import org.r2.devkit.exception.StringParseException;
import org.r2.devkit.json.JSONToken;
import org.r2.devkit.json.util.JSONParseCheck;
import org.r2.devkit.json.util.JSONStringParser;
import org.r2.devkit.json.util.ParseHolder;
import org.r2.devkit.util.Assert;

import java.util.*;

import static org.r2.devkit.json.JSONToken.*;

/**
 * JSONArray是一个数组，其中每个元素必定为JSONObject
 * JSONString表现为
 * [JSONObject,JSONObject]
 *
 * @author ruan4261
 */
public final class JSONArray extends JSONValue implements List<JSONObject> {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JSONArray;
    private final List<JSONObject> container;

    public JSONArray() {
        this(8);
    }

    public JSONArray(int initialCapacity) {
        this.container = new ArrayList<>(initialCapacity);
    }

    public JSONArray(Collection<JSONObject> list) {
        Assert.notNull(list);
        this.container = new ArrayList<>(list);
    }

    /**
     * 用户调用接口
     * 完整解析
     */
    public static JSONArray parse(String str) {
        ParseHolder<JSONArray> holder = JSONStringParser.parse2JSONArray(str, 0);

        // 判断字符串剩余部分是否可忽略，不可忽略则抛出异常
        JSONParseCheck.ignore(str, holder.getOffset(), "CharSequence cannot parse to JSONArray : " + str);

        return holder.getObject();
    }

    @Override
    public String toString() {
        return toJSONString();
    }

    @Override
    public String toJSONString() {
        final StringBuilder builder = new StringBuilder(String.valueOf(LBRACKET));
        container.forEach(object -> builder.append(object.toJSONString()).append(COMMA));

        int last = builder.length() - 1;
        if (builder.charAt(last) == COMMA)
            builder.deleteCharAt(last);

        builder.append(RBRACKET);
        return builder.toString();
    }

    @Override
    public JSONField getEnumType() {
        return TYPE;
    }

    /* List Override */

    @Override
    public int size() {
        return this.container.size();
    }

    @Override
    public boolean isEmpty() {
        return this.container.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.container.contains(o);
    }

    @Override
    public Iterator<JSONObject> iterator() {
        return this.container.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.container.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.container.toArray(a);
    }

    @Override
    public boolean add(JSONObject o) {
        return this.container.add(o);
    }

    @Override
    public boolean remove(Object o) {
        return this.container.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.container.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends JSONObject> c) {
        return this.container.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends JSONObject> c) {
        return this.container.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.container.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.container.retainAll(c);
    }

    @Override
    public void clear() {
        this.container.clear();
    }

    @Override
    public JSONObject get(int index) {
        return this.container.get(index);
    }

    @Override
    public JSONObject set(int index, JSONObject element) {
        return this.container.set(index, element);
    }

    @Override
    public void add(int index, JSONObject element) {
        this.container.add(index, element);
    }

    @Override
    public JSONObject remove(int index) {
        return this.container.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.container.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.container.lastIndexOf(o);
    }

    @Override
    public ListIterator<JSONObject> listIterator() {
        return this.container.listIterator();
    }

    @Override
    public ListIterator<JSONObject> listIterator(int index) {
        return this.container.listIterator(index);
    }

    @Override
    public List<JSONObject> subList(int fromIndex, int toIndex) {
        return this.container.subList(fromIndex, toIndex);
    }
}
