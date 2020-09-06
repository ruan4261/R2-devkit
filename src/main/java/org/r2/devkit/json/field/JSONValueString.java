package org.r2.devkit.json.field;

import org.r2.devkit.json.JSON;

import static org.r2.devkit.json.JSONToken.*;

/**
 * 表明JSON中的String值
 * JSONString表现为
 * "container"
 *
 * @author ruan4261
 */
public final class JSONValueString extends JSON {
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
        return DOUBLE_QUOT + container + DOUBLE_QUOT;
    }

    @Override
    public Object clone() {
        return new JSONValueString(this.container);
    }
}
