package org.r2.devkit.json;

import org.r2.devkit.json.custom.CustomSerializer;
import org.r2.devkit.json.custom.CustomizableSerialize;
import org.r2.devkit.json.serialize.JSONSerializer;
import org.r2.devkit.json.util.JSONParseCheck;
import org.r2.devkit.json.util.JSONStringParser;
import org.r2.devkit.json.util.Holder;
import org.r2.devkit.util.Assert;

import java.util.*;

import static org.r2.devkit.json.JSONToken.*;

/**
 * @author ruan4261
 */
public final class JSONArray extends JSON implements CustomizableSerialize, List<Object> {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_CAPACITY = 8;
    private final List<Object> container;
    // serialization solution
    private CustomSerializer customSerializer;

    public JSONArray() {
        this(DEFAULT_CAPACITY);
    }

    public JSONArray(int initialCapacity) {
        this.container = new ArrayList<>(initialCapacity);
    }

    public JSONArray(Collection<Object> list) {
        Assert.notNull(list, "list");
        this.container = new ArrayList<>(list);
    }

    @Override
    public void setCustomSerializer(CustomSerializer customSerializer) {
        this.customSerializer = customSerializer;
    }

    @Override
    public void addCustomSerializer(CustomSerializer customSerializer) {
        if (this.customSerializer == null)
            this.setCustomSerializer(customSerializer);
        else
            this.customSerializer.putAll(customSerializer);
    }

    @Override
    public CustomSerializer getCustomSerializer() {
        return this.customSerializer;
    }

    /**
     * 完整字符串解析调用接口
     */
    public static JSONArray parseArray(String str) {
        Holder<JSONArray> holder = JSONStringParser.parse2JSONArray(str, 0);

        // 判断字符串剩余部分是否可忽略，不可忽略则抛出异常
        JSONParseCheck.ignore(str, holder.getOffset(), "String cannot parse to JSONArray : " + str);

        return holder.getObject();
    }

    @Override
    public String toString() {
        return toJSONString();
    }


    /**
     * output json string
     */
    @Override
    public String toJSONString() {
        final StringBuilder builder = new StringBuilder(String.valueOf(LBRACKET));
        container.forEach(
                object -> builder.append(JSONSerializer.serializer(object, this.customSerializer)).append(COMMA)
        );

        // delete last comma
        int last = builder.length() - 1;
        if (builder.charAt(last) == COMMA)
            builder.deleteCharAt(last);

        builder.append(RBRACKET);
        return builder.toString();
    }

    @Override
    public Object clone() {
        return new JSONArray(this.container);
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
    public Iterator<Object> iterator() {
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
    public boolean add(Object o) {
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
    public boolean addAll(Collection<?> c) {
        return this.container.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<?> c) {
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
    public Object get(int index) {
        return this.container.get(index);
    }

    @Override
    public Object set(int index, Object element) {
        return this.container.set(index, element);
    }

    @Override
    public void add(int index, Object element) {
        this.container.add(index, element);
    }

    @Override
    public Object remove(int index) {
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
    public ListIterator<Object> listIterator() {
        return this.container.listIterator();
    }

    @Override
    public ListIterator<Object> listIterator(int index) {
        return this.container.listIterator(index);
    }

    @Override
    public List<Object> subList(int fromIndex, int toIndex) {
        return this.container.subList(fromIndex, toIndex);
    }

}
