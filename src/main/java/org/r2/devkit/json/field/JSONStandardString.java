package org.r2.devkit.json.field;

import org.r2.devkit.json.util.JSONStringParser;
import org.r2.devkit.json.util.ParseHolder;

import static org.r2.devkit.json.JSONToken.*;

/**
 * 表明JSON中的String值
 * JSONString表现为
 * "container"
 *
 * @author ruan4261
 */
public final class JSONStandardString extends JSONValue {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JSONValueString;
    private String container;

    public String getContainer() {
        return container;
    }

    public JSONStandardString setContainer(String container) {
        this.container = container;
        return this;
    }

    public JSONStandardString() {
    }

    public JSONStandardString(String container) {
        this.container = container;
    }


    public static ParseHolder<JSONStandardString> parseValueString(String str, int offset) {
        return JSONStringParser.parse2JSONValueString(str, offset);
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
