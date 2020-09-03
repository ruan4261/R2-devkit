package org.r2.devkit.json.field;

import static org.r2.devkit.json.JSONToken.*;

import org.r2.devkit.exception.StringParseException;
import org.r2.devkit.json.JSON;
import org.r2.devkit.json.util.ParseHolder;
import org.r2.devkit.util.Assert;

import java.io.Serializable;

/**
 * JSONDomain是一组键值
 * JSONString表现为
 * JSONKey:JSONValue
 *
 * @author ruan4261
 */
public final class JSONDomain extends JSON implements Cloneable, Serializable {
    private static final long serialVersionUID = 3391896330484292636L;
    private static final JSONField TYPE = JSONField.JSONDomain;
    private JSONKey key;
    private JSONValue value;

    public JSONKey getKey() {
        return key;
    }

    public void setKey(JSONKey key) {
        this.key = key;
    }

    public JSONValue getValue() {
        return value;
    }

    public void setValue(JSONValue value) {
        this.value = value;
    }

    /**
     * 解析字符串，取得一个JSONDomain对象
     */
    public static ParseHolder<JSONDomain> parse(char[] chars, int offset) {
        Assert.legalOffset(chars, offset);
        JSONDomain body = new JSONDomain();

        // 寻找JSONKey

        // 寻找JSONValue

        throw new StringParseException("CharSequence cannot parse to json : " + new String(chars));
    }

    @Override
    public String toString() {
        return toJSONString();
    }

    @Override
    public String toJSONString() {
        return key.toJSONString() + COLON + value.toJSONString();
    }

    @Override
    public JSONField getEnumType() {
        return TYPE;
    }
}
