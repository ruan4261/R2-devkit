package org.r2.devkit.json.field;

/**
 * JSON中只可能有如下几个类型
 * 复杂类型如LocalDate等需由调用方手动提取字符串，自行解析
 *
 * @author ruan4261
 */
public enum JSONField {

    JSONObject(JSONObject.class),
    JSONArray(JSONArray.class),
    JSONKey(JSONStandardString.class),
    JSONValueString(JSONStandardString.class),
    JSONValueNumber(JSONValueNumber.class),
    JSONValueBoolean(JSONValueBoolean.class),
    JSONValueNull(JSONValueNull.class);

    public final Class clazz;

    JSONField(Class clazz) {
        this.clazz = clazz;
    }
}
