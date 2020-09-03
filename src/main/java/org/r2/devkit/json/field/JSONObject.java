package org.r2.devkit.json.field;

import static org.r2.devkit.json.JSONToken.*;

import org.r2.devkit.exception.StringParseException;
import org.r2.devkit.json.JSON;
import org.r2.devkit.json.JSONToken;
import org.r2.devkit.json.util.ParseHolder;
import org.r2.devkit.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * 对象类型Json格式化工具
 * JSONString表现为
 * {
 * JSONDomain,
 * JSONDomain
 * }
 *
 * @author ruan4261
 */
public final class JSONObject extends JSON implements List<JSONDomain>, Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JSONObject;
    private final List<JSONDomain> container;

    public JSONObject() {
        this(8);
    }

    public JSONObject(int initialCapacity) {
        this.container = new ArrayList<>(initialCapacity);
    }

    public JSONObject(List<JSONDomain> list) {
        Assert.notNull(list);
        this.container = new ArrayList<>(list);
    }

    /**
     * 对完整字符串进行解析
     */
    public static JSONObject parse(String str) {
        ParseHolder<JSONObject> holder = parse(str.toCharArray(), 0);

        // 判断字符串剩余部分是否可忽略，不可忽略则抛出异常
        int len = str.length();
        int last = holder.getOffset();
        for (int i = last; i < len; i++) {
            char c = str.charAt(i);
            if (!JSONToken.isIgnorable(c))
                throw new StringParseException("CharSequence cannot parse to json : " + str);
        }

        return holder.getObject();
    }

    /**
     * 单元解析
     */
    public static ParseHolder<JSONObject> parse(char[] chars, int offset) {
        Assert.legalOffset(chars, offset);
        JSONObject body = new JSONObject();

        // 循环控制变量
        boolean run = false;
        int brace = 0;

        for (; offset < chars.length; ) {
            char c = chars[offset];
            // 枚举
            if (!run) {// 寻找第一个可用字符

                if (!isIgnorable(c)) {
                    if (LBRACE != c) break;// 首字符错误，抛出异常
                    run = true;
                    brace = 1;
                }// else 情况下为可忽视字符，直接去下一个循环

            }

            // 寻找一个JSONDomain


            offset++;
            // 已构成一个JSONObject
            if (brace == 0) {
                ParseHolder<JSONObject> res = new ParseHolder<>();
                res.setObject(body);
                res.setOffset(offset);
                return res;
            }
        }
        throw new StringParseException("CharSequence cannot parse to json : " + new String(chars));
    }

    @Override
    public String toString() {
        return toJSONString();
    }

    @Override
    public String toJSONString() {
        final StringBuilder builder = new StringBuilder(String.valueOf(LBRACE));
        container.forEach(domain -> {
            builder.append(domain.toJSONString()).append(COMMA);
        });

        int last = builder.length() - 1;
        if (builder.charAt(last) == COMMA)
            builder.deleteCharAt(last);

        builder.append(RBRACE);
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
    public Iterator<JSONDomain> iterator() {
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
    public boolean add(JSONDomain o) {
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
    public boolean addAll(Collection<? extends JSONDomain> c) {
        return this.container.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends JSONDomain> c) {
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
    public JSONDomain get(int index) {
        return this.container.get(index);
    }

    @Override
    public JSONDomain set(int index, JSONDomain element) {
        return this.container.set(index, element);
    }

    @Override
    public void add(int index, JSONDomain element) {
        this.container.add(index, element);
    }

    @Override
    public JSONDomain remove(int index) {
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
    public ListIterator<JSONDomain> listIterator() {
        return this.container.listIterator();
    }

    @Override
    public ListIterator<JSONDomain> listIterator(int index) {
        return this.container.listIterator(index);
    }

    @Override
    public List<JSONDomain> subList(int fromIndex, int toIndex) {
        return this.container.subList(fromIndex, toIndex);
    }
}
