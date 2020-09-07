package org.r2.devkit.test.json;

import org.r2.devkit.json.JSON;
import org.r2.devkit.test.TestCore;

public class Demo3 extends TestCore {

    private static final String str = "{\"string\":\"a\",\"nums\":[0,-1,10,0.123,1e5,-1e+6,0.1e-7],\"object\":{\"empty\":{},\"list\":[]},\"list\":[\"object\",{\"true\":true,\"false\":false,\"null\":null}]}";

    public static void main(String[] args) {
        JSON json = JSON.parse(str);
        print(json.toJSONString());
    }

}
