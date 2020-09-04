package org.r2.devkit.json.field;

/**
 * 表明JSON中的Number值
 * JSONString表现为等科学计数法，只允许传输十进制数据
 * 100 / 123.456 / -789 / 9.99e+7 / 0.8e-8
 *
 * todo
 * @author ruan4261
 */
public final class JSONValueNumber extends JSONValue {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JSONValueNumber;
    private Number container;

    @Override
    public String toString() {
        return this.container.toString();
    }

    @Override
    public String toJSONString() {
        return null;
    }

    @Override
    public JSONField getEnumType() {
        return TYPE;
    }
}