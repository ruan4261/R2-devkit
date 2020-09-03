package org.r2.devkit.json.field;

import org.r2.devkit.NULL;

import java.io.Serializable;

/**
 * 表明JSON中的NULL值
 * JSONString表现为
 * null
 *
 * @author ruan4261
 */
public final class JSONValueNull extends JSONValue implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JSONValueNull;
    private static final NULL NULL = org.r2.devkit.NULL.construct();

    @Override
    public String toString() {
        return toJSONString();
    }

    @Override
    public String toJSONString() {
        return NULL.s();
    }

    @Override
    public JSONField getEnumType() {
        return TYPE;
    }
}
