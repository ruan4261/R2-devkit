package org.r2.devkit.json.field;

/**
 * Json中只可能有如下几个类型
 * 复杂类型如LocalDate等需由调用方手动提取字符串，自行解析
 *
 * @author ruan4261
 */
public enum JSONField {

    JsonObject(JSONObject.class) {
        @Override
        public JSONObject getFieldInstance() {
            return new JSONObject();
        }
    },
    JsonArray(JSONArray.class) {
        @Override
        public JSONArray getFieldInstance() {
            return new JSONArray();
        }
    },
    JsonKey(JSONKey.class) {
        @Override
        public JSONKey getFieldInstance() {
            return new JSONKey();
        }
    },
    JsonValueString(JSONValueString.class) {
        @Override
        public JSONValueString getFieldInstance() {
            return new JSONValueString();
        }
    },
    JsonValueNumber(JSONValueNumber.class) {
        @Override
        public JSONValueNumber getFieldInstance() {
            return new JSONValueNumber();
        }
    },
    JsonValueBoolean(JSONValueBoolean.class) {
        @Override
        public JSONValueBoolean getFieldInstance() {
            return new JSONValueBoolean();
        }
    },
    JsonValueNull(JSONValueNull.class) {
        @Override
        public JSONValueNull getFieldInstance() {
            return new JSONValueNull();
        }
    };

    Class clazz;

    JSONField(Class clazz) {
        this.clazz = clazz;
    }

    public Class getFieldClass() {
        return this.clazz;
    }

    public abstract Object getFieldInstance();
}
