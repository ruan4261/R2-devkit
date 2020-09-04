package org.r2.devkit.json.field;

/**
 * 表明JSON中的NULL值
 * JSONString表现为
 * null
 *
 * @author ruan4261
 */
public final class JSONValueNull extends JSONValue {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JSONValueNull;
    private static final String NULL = "null";

    @Override
    public String toString() {
        return NULL;
    }

    @Override
    public String toJSONString() {
        return NULL;
    }

    @Override
    public JSONField getEnumType() {
        return TYPE;
    }
}
