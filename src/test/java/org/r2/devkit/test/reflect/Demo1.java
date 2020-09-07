package org.r2.devkit.test.reflect;

import org.r2.devkit.json.JSONObject;
import org.r2.devkit.test.TestCore;

public class Demo1 extends TestCore {

    public static void main(String[] args) {
        Class clazz = JSONObject.class.getSuperclass();
        while (clazz != null) {
            print(clazz);
            clazz = clazz.getSuperclass();
        }
    }

}
