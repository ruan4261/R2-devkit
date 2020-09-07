package org.r2.devkit.test.json;

import org.r2.devkit.json.JSONArray;
import org.r2.devkit.json.JSONObject;
import org.r2.devkit.serialize.CustomSerializer;
import org.r2.devkit.test.TestCore;

import java.time.LocalDateTime;

public class Test1 extends TestCore {

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("object", new JSONObject());
        jsonObject.put("date", LocalDateTime.now());
        jsonObject.put("xxx", null);
        // jsonObject.put(null, "xxx");             NPE
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("date");
        jsonArray.add(LocalDateTime.now().plusYears(24));
        jsonObject.put("array", jsonArray);
        jsonObject.syncCustomSerializer(customSerializer());

        print(jsonObject);
    }

    private static CustomSerializer customSerializer() {
        CustomSerializer customSerializer = new CustomSerializer();
        customSerializer.register(LocalDateTime.class, 0, (object) -> "成功自定义序列化" + object.toString());
        return customSerializer;
    }

}
