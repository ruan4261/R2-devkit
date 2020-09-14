package org.r2.devkit.test;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Arrays;

public abstract class TestCore {

    public static PrintStream out = System.out;

    public static PrintStream err = System.err;

    public final static int MBytes = 1024 * 1024;

    public static void print(String key, Object value) {
        Class klass = value.getClass();
        if (klass.isArray())
            out.println(key + " -> " + Arrays.toString((Object[]) value));
        else
            out.println(key + " -> " + value);
    }

    public static void print(String val) {
        out.println(val);
    }

    public static void print(Object obj) {
        if (obj.getClass().isArray())
            print(Arrays.toString((Object[]) obj));
        else
            print(obj.toString());
    }

    public static void printf(String format, Object... args) {
        out.printf(format, args);
    }

    public static void line() {
        line(1);
    }

    public static void line(int val) {
        for (int i = 0; i < val; i++) {
            out.println();
        }
    }
}
