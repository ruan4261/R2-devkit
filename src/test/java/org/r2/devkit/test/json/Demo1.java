package org.r2.devkit.test.json;

import org.r2.devkit.json.JSONObject;
import org.r2.devkit.test.TestCore;

import java.math.BigDecimal;

public class Demo1 extends TestCore {

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Jobs");
        jsonObject.put("age", 0xffff);
        jsonObject.put("salary", new BigDecimal("888888.888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888"));

        String text = jsonObject.toJSONString();
        System.out.println(text);
    }

}
