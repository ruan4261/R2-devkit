package org.r2.devkit.json.field;

import java.io.Serializable;

import static org.r2.devkit.json.JSONToken.*;

/**
 * 表明JSON中的String值
 * JSONString表现为
 * 'container'
 *
 * @author ruan4261
 */
public final class JSONValueString extends JSONValue implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JSONValueString;
    private String container;

    public String getContainer() {
        return container;
    }

    public JSONValueString setContainer(String container) {
        this.container = container;
        return this;
    }

    @Override
    public String toString() {
        return this.container;
    }

    @Override
    public String toJSONString() {
        return DOUBLE_QUOT + container + DOUBLE_QUOT;
    }

    @Override
    public JSONField getEnumType() {
        return TYPE;
    }
}
