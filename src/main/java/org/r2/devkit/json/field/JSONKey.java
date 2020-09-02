package org.r2.devkit.json.field;

import org.r2.devkit.json.JSON;

import java.io.Serializable;

/**
 * JSON字符串的键名，其只可能是字符串
 *
 * @author ruan4261
 */
public class JSONKey extends JSON implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JsonKey;
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