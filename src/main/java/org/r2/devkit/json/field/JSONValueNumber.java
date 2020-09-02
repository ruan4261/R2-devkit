package org.r2.devkit.json.field;

import org.r2.devkit.json.JSON;

import java.io.Serializable;

/**
 * 表明JSON中的Number值
 *
 * @author ruan4261
 */
public final class JSONValueNumber extends JSON implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JsonValueNumber;
    private Number container;

    @Override
    public String toString() {
        return this.container.toString();
    }

    @Override
    public JSONField getEnumType() {
        return TYPE;
    }
}