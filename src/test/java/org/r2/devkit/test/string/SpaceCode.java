package org.r2.devkit.test.string;

import org.r2.devkit.test.TestCore;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SpaceCode extends TestCore {

    public static void main(String[] args) {
        // next 下一行
        print((int) '\n');
        print(utf8('\n'));

        // return 回到本行头
        print((int) '\r');
        print(utf8('\r'));

        // tab
        print((int) '\t');
        print(utf8('\t'));

        // 退格
        print((int) '\b');
        print(utf8('\b'));
    }

    private static String utf8(Object obj) {
        try {
            return URLEncoder.encode(obj.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
