package org.r2.devkit.json.field;

import java.io.Serializable;

/**
 * 表明JSON中的Boolean值
 * JSONString表现为
 * true / false
 *
 * @author ruan4261
 */
public final class JSONValueBoolean extends JSONValue implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JSONValueBoolean;
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private boolean container;

    public boolean getContainer() {
        return container;
    }

    public void setContainer(boolean container) {
        this.container = container;
    }

    @Override
    public String toString() {
        return toJSONString();
    }

    @Override
    public String toJSONString() {
        return container ? TRUE : FALSE;
    }

    @Override
    public JSONField getEnumType() {
        return TYPE;
    }

}