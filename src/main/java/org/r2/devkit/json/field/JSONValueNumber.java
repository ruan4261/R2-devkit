package org.r2.devkit.json.field;

import org.r2.devkit.json.JSON;
import org.r2.devkit.json.JSONException;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 表明JSON中的Number值
 * JSONString表现为等科学计数法，只允许传输十进制数据
 * 使用科学计数法时禁止前置0
 * 禁止首字符为 +
 * 正确示例：
 * 100 / 123.456 / -789 / 9.99e+7 / 1.8e-8 / 3e3
 *
 * @author ruan4261
 */
public final class JSONValueNumber extends JSON {
    private static final long serialVersionUID = 1L;
    private BigDecimal container;

    public JSONValueNumber() {
    }

    public JSONValueNumber(BigDecimal container) {
        this.container = container;
    }

    public JSONValueNumber(String container) {
        try {
            this.container = new BigDecimal(container);
        } catch (NumberFormatException e) {
            throw new JSONException(e);
        }
    }

    public BigDecimal getContainer() {
        return container;
    }

    public void setContainer(BigDecimal container) {
        this.container = container;
    }

    @Override
    public String toJSONString() {
        return this.container.toString();
    }

    @Override
    public String toString() {
        return this.toJSONString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        JSONValueNumber number = (JSONValueNumber) object;
        return Objects.equals(container, number.container);
    }

    @Override
    public int hashCode() {
        return Objects.hash(container);
    }

    @Override
    public Object clone() {
        return new JSONValueNumber(this.container);
    }
}