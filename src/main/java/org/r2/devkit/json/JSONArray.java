package org.r2.devkit.json;

import org.r2.devkit.serialize.CustomSerializer;
import org.r2.devkit.json.custom.CustomizableSerialization;
import org.r2.devkit.json.serialize.JSONSerializer;
import org.r2.devkit.json.util.JSONParseCheck;
import org.r2.devkit.json.util.JSONStringParser;
import org.r2.devkit.json.util.Holder;
import org.r2.devkit.util.Assert;

import java.util.*;

/**
 * @author ruan4261
 */
public final class JSONArray extends JSON implements CustomizableSerialization, List<Object> {
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

    public <T> JSONArray(T[] a) {
        Assert.notNull(a);
        this.container = new ArrayList<>(Arrays.asList(a));
    }

    public JSONArray(List<Object> list) {
        Assert.notNull(list, "list");
        this.container = list;
    }

    /**
     * newContainer为true的情况下，将构造一个新的List用于内部容器
     * newContainer为false的情况下，该构造等同于普通的list构造
     */
    public JSONArray(List<Object> list, boolean newContainer) {
        Assert.notNull(list, "list");
        if (newContainer)
            this.container = new ArrayList<>(list);
        else
            this.container = list;
    }

    /**
     * 通过collection复制一个新的list用于内部容器
     */
    public JSONArray(Collection<Object> collection) {
        Assert.notNull(collection, "collection");
        this.container = new ArrayList<>(collection);
    }

    /**
     * 完全替换当前对象序列化机制
     */
    @Override
    public void setCustomSerializer(CustomSerializer customSerializer) {
        this.customSerializer = customSerializer;
    }

    @Override
    public CustomSerializer getCustomSerializer() {
        return this.customSerializer;
    }

    /**
     * 删除当前对象序列化机制
     */
    @Override
    public void removeCustomSerializer() {
        this.customSerializer = null;
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

    public List<Object> innerList() {
        return this.container;
    }

    /**
     * output json string
     */
    @Override
    public String toJSONString() {
        return JSONSerializer.collection2JSONString(this, this.customSerializer);
    }

    @Override
    public String toString() {
        return this.toJSONString();
    }

    @Override
    public Object clone() {
        return new JSONArray(new ArrayList<>(this.container));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        JSONArray jsonArray = (JSONArray) object;
        return Objects.equals(container, jsonArray.container);
    }

    @Override
    public int hashCode() {
        return Objects.hash(container);
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
