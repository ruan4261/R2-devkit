package org.r2.devkit.json.serialize;

import static org.r2.devkit.json.JSONToken.DOUBLE_QUOT;

import org.r2.devkit.json.custom.CustomizableSerialize;
import org.r2.devkit.json.JSON;
import org.r2.devkit.json.custom.CustomSerializer;
import org.r2.devkit.json.field.JSONValueNull;

/**
 * JSON序列化器
 *
 * @author ruan4261
 */
public final class JSONSerializer {

    public static String serializer(Object object, CustomSerializer serializer) {
        if (object == null) return JSONValueNull.getInstance().toString();

        // 将父对象序列化机制同步到子类中
        if (object instanceof CustomizableSerialize)
            ((CustomizableSerialize) object).addCustomSerializer(serializer);

        if (object instanceof JSON)
            return ((JSON) object).toJSONString();
        if (serializer != null && serializer.hasOperator(object))
            return serializer.serialize(object);
        return DOUBLE_QUOT + object.toString() + DOUBLE_QUOT;
    }

}
