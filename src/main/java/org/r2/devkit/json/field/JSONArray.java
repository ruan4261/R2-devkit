package org.r2.devkit.json.field;

import org.r2.devkit.json.JSON;
import org.r2.devkit.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * 数组类型Json格式化工具
 *
 * @author ruan4261
 */
public final class JSONArray extends JSON implements List<Object>, Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JsonArray;
    private final List<Object> container;

    public JSONArray() {
        this(8);
    }

    public JSONArray(int initialCapacity) {
        this.container = new ArrayList<>(initialCapacity);
    }

    public JSONArray(Collection<Object> list) {
        Assert.notNull(list);
        this.container = new ArrayList<>(list);
    }

    public static JSONArray parse(Object object) {
        if (object instanceof String) {
            return parse(((String) object).toCharArray(), 0);
        }
        return null;
    }

    public static JSONArray parse(char[] chars, int offset) {
        return null;
    }

    @Override
    public String toString() {
        return null;
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
