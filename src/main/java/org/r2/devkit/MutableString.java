package org.r2.devkit;

import java.io.Serializable;

import static org.r2.devkit.core.CacheBase.EMPTY;

/**
 * 可变字符串
 * 当{@code NullAble}为{@code false}时，内置字符串对象不可能为空，将其置为空会触发异常
 * 其他情况下，请自行控制
 * 需要使用字符串实例方法时，请通过{@link #val()}方法获取不可变字符串操作
 *
 * @author ruan4261
 */
public final class MutableString implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private boolean nullAble;
    private volatile String value;

    public MutableString(String value, boolean nullAble) {
        this.nullAble = nullAble;
        if (!nullAble) Assert.notNull(value, "value");
        this.value = value;
    }

    public String val() {
        return this.value;
    }

    public void val(String value) {
        if (!nullAble) Assert.notNull(value, "value");
        this.value = value;
    }

    public void empty() {
        this.value = EMPTY;
    }

    public boolean isEmpty() {
        return this.value == null || length() == 0;
    }

    public int length() {
        return this.value.length();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        MutableString that = (MutableString) o;
        return this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}
