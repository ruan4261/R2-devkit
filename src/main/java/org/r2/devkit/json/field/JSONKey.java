package org.r2.devkit.json.field;

import org.r2.devkit.exception.StringParseException;
import org.r2.devkit.json.JSON;
import org.r2.devkit.json.util.ParseHolder;
import org.r2.devkit.util.Assert;

import java.io.Serializable;

import static org.r2.devkit.json.JSONToken.*;

/**
 * JSON字符串的键名，其只可能是字符串
 * JSONString表现为
 * 'container'
 *
 * @author ruan4261
 */
public class JSONKey extends JSON implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static final JSONField TYPE = JSONField.JSONKey;
    private String container;

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    /**
     * 解析字符串，取得一个JSONKey对象
     */
    public static ParseHolder<JSONKey> parse(char[] chars, int offset) {
        Assert.legalOffset(chars, offset);
        JSONKey body = new JSONKey();

        // 寻找一个引号
        for (; offset < chars.length; ) {
            char c = chars[offset];


        }

        // 寻找下一个引号

        throw new StringParseException("CharSequence cannot parse to json : " + new String(chars));
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