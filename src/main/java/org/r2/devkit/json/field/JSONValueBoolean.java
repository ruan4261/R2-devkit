package org.r2.devkit.json.field;

import org.r2.devkit.json.JSON;

import java.util.Objects;

/**
 * 表明JSON中的Boolean值
 * JSONString表现为
 * true / false
 *
 * @author ruan4261
 */
public final class JSONValueBoolean extends JSON {
    private static final long serialVersionUID = 1L;
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final JSONValueBoolean INSTANCE_TRUE;
    private static final JSONValueBoolean INSTANCE_FALSE;
    private boolean container;

    static {
        INSTANCE_TRUE = new JSONValueBoolean(true);
        INSTANCE_FALSE = new JSONValueBoolean(false);
    }

    public boolean getContainer() {
        return container;
    }

    public void setContainer(boolean container) {
        this.container = container;
    }

    public static JSONValueBoolean getInstanceFalse() {
        return INSTANCE_FALSE;
    }

    public static JSONValueBoolean getInstanceTrue() {
        return INSTANCE_TRUE;
    }

    private JSONValueBoolean() {
        this.container = false;
    }

    private JSONValueBoolean(boolean container) {
        this.container = container;
    }

    @Override
    public String toJSONString() {
        return container ? TRUE : FALSE;
    }

    @Override
    public String toString() {
        return this.toJSONString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        JSONValueBoolean that = (JSONValueBoolean) object;
        return container == that.container;
    }

    @Override
    public int hashCode() {
        return Objects.hash(container);
    }

    /**
     * 本类禁止克隆
     */
    @Override
    public Object clone() {
        return this;
    }
}