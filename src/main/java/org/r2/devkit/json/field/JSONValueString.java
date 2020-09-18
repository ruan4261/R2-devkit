package org.r2.devkit.json.field;

import org.r2.devkit.json.JSON;
import org.r2.devkit.json.serialize.JSONSerializer;
import org.r2.devkit.Assert;

import java.util.Objects;

/**
 * 表明JSON中的String值
 * JSONString表现为
 * "container"
 *
 * @author ruan4261
 */
public final class JSONValueString extends JSON implements CharSequence {
    private static final long serialVersionUID = 1L;
    private String container;

    public String getContainer() {
        return container;
    }

    public JSONValueString setContainer(String container) {
        this.container = container;
        return this;
    }

    public JSONValueString() {
    }

    public JSONValueString(String container) {
        this.container = container;
    }

    @Override
    public String toString() {
        return this.container;
    }

    @Override
    public String toJSONString() {
        return JSONSerializer.escapeAndQuot(this.container);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        JSONValueString that = (JSONValueString) object;
        return Objects.equals(container, that.container);
    }

    @Override
    public int hashCode() {
        return Objects.hash(container);
    }

    @Override
    public Object clone() {
        return new JSONValueString(this.container);
    }

    @Override
    public int length() {
        if (container == null) return 0;
        return this.container.length();
    }

    @Override
    public char charAt(int index) {
        Assert.notNull(this.container);
        return this.container.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        Assert.notNull(this.container);
        return this.container.subSequence(start, end);
    }
}
