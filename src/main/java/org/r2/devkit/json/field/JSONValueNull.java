package org.r2.devkit.json.field;

import org.r2.devkit.json.JSON;

/**
 * 表明JSON中的NULL值
 * JSONString表现为
 * null
 *
 * @author ruan4261
 */
public final class JSONValueNull extends JSON {
    private static final long serialVersionUID = 1L;
    private static final String NULL = "null";
    private static final JSONValueNull instance;

    static {
        instance = new JSONValueNull();
    }

    private JSONValueNull() {
    }

    public static JSONValueNull getInstance() {
        return instance;
    }

    @Override
    public String toJSONString() {
        return NULL;
    }

    @Override
    public String toString() {
        return this.toJSONString();
    }

    /**
     * 本类禁止克隆
     */
    @Override
    public Object clone() {
        return this;
    }
}
