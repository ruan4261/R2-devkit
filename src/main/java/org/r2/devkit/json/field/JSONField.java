package org.r2.devkit.json.field;

/**
 * Json中只可能有如下几个类型
 * 复杂类型如LocalDate等需由调用方手动提取字符串，自行解析
 *
 * @author ruan4261
 */
public enum JSONField {

    JSONObject(JSONObject.class) {
        @Override
        public JSONObject getEmptyInstance() {
            return new JSONObject();
        }
    },
    JSONArray(JSONArray.class) {
        @Override
        public JSONArray getEmptyInstance() {
            return new JSONArray();
        }
    },
    JSONDomain(JSONDomain.class) {
        @Override
        public JSONDomain getEmptyInstance() {
            return new JSONDomain();
        }
    },
    JSONKey(JSONKey.class) {
        @Override
        public JSONKey getEmptyInstance() {
            return new JSONKey();
        }
    },
    JSONValueString(JSONValueString.class) {
        @Override
        public JSONValueString getEmptyInstance() {
            return new JSONValueString();
        }
    },
    JSONValueNumber(JSONValueNumber.class) {
        @Override
        public JSONValueNumber getEmptyInstance() {
            return new JSONValueNumber();
        }
    },
    JSONValueBoolean(JSONValueBoolean.class) {
        @Override
        public JSONValueBoolean getEmptyInstance() {
            return new JSONValueBoolean();
        }
    },
    JSONValueNull(JSONValueNull.class) {
        @Override
        public JSONValueNull getEmptyInstance() {
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

    public abstract Object getEmptyInstance();
}
