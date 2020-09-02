package org.r2.devkit.json.field;

import java.io.Serializable;

/**
 * 表明JSON中的String值
 *
 * @author ruan4261
 */
public final class JSONValueString extends JSONValue implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JsonValueString;
    private String container;

    @Override
    public String toString() {
        return this.container;
    }

    @Override
    public JSONField getEnumType() {
        return TYPE;
    }
}
