package org.r2.devkit.json.field;

import java.io.Serializable;

/**
 * 表明JSON中的Boolean值
 *
 * @author ruan4261
 */
public final class JSONValueBoolean extends JSONValue implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JsonArray;
    private boolean container;

    @Override
    public String toString() {
        return String.valueOf(container);
    }

    @Override
    public JSONField getEnumType() {
        return TYPE;
    }

}