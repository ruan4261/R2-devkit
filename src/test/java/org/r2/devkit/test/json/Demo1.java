package org.r2.devkit.test.json;

import org.r2.devkit.json.JSONObject;

public class Demo1 {

    public static void main(String[] args) {
        String res1 = "{\"return_code\":\"0000\",\"return_message\":\"调用成功\",\"reslut\":[]}";
        String res2 = "{\\\"return_code\\\":\\\"0000\\\",\\\"return_message\\\":\\\"调用成功\\\",\\\"reslut\\\":[]}";
        String res3 = "\"{\\\"return_code\\\":\\\"0000\\\",\\\"return_message\\\":\\\"调用成功\\\",\\\"reslut\\\":[]}\"";

        parseAndPrint(res1);
        parseAndPrint(res2);
        parseAndPrint(res3);
    }

    static void parseAndPrint(String res) {
        try {
            System.out.println(JSONObject.parseObject(res));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
