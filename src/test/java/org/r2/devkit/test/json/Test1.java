package org.r2.devkit.test.json;

import org.r2.devkit.json.JSONArray;
import org.r2.devkit.json.JSONObject;
import org.r2.devkit.json.custom.CustomSerializer;

import java.time.LocalDateTime;

public class Test1 {

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("object", new JSONObject());
        jsonObject.put("date", LocalDateTime.now());
        jsonObject.put("xxx", null);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("date");
        jsonArray.add(LocalDateTime.now().plusYears(24));
        jsonObject.put("array", jsonArray);
        jsonObject.setCustomSerializer(customSerializer());
        System.out.println(jsonObject);
    }

    private static CustomSerializer customSerializer() {
        CustomSerializer customSerializer = new CustomSerializer();
        customSerializer.put(LocalDateTime.class, (object) -> "成功自定义序列化" + object.toString());
        return customSerializer;
    }

}
