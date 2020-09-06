package org.r2.devkit.json.field;

import org.r2.devkit.json.JSON;

/**
 * 表明JSON中的Number值
 * JSONString表现为等科学计数法，只允许传输十进制数据
 * 100 / 123.456 / -789 / 9.99e+7 / 0.8e-8
 *
 * todo
 * @author ruan4261
 */
public final class JSONValueNumber extends JSON {
    private static final long serialVersionUID = 1L;
    private Number container;

    @Override
    public String toJSONString() {
        return null;
    }

    @Override
    public Object clone() {
        return null;
    }
}