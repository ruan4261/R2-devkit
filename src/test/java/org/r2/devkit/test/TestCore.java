package org.r2.devkit.test;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Arrays;

public abstract class TestCore {

    public static PrintStream out = System.out;

    public static PrintStream err = System.err;

    public final static int MBytes = 1024 * 1024;

    public static void print(String key, Object value) {
        Class<?> klass = value.getClass();
        if (klass.isArray())
            out.println(key + " -> " + array2String(value));
        else
            out.println(key + " -> " + value);
    }

    public static void errPrint(String key, Object value) {
        Class<?> klass = value.getClass();
        if (klass.isArray())
            err.println(key + " -> " + array2String(value));
        else
            err.println(key + " -> " + value);
    }

    public static void print(String val) {
        out.println(val);
    }

    public static void errPrint(String val) {
        err.println(val);
    }

    public static void errPrint(Object obj) {
        if (obj.getClass().isArray())
            print(array2String(obj));
        else
            errPrint(obj.toString());
    }

    public static void print(Object obj) {
        if (obj.getClass().isArray()) {
            print(array2String(obj));
        } else
            print(obj.toString());
    }

    public static String array2String(Object a) {
        if (a == null)
            return "null";
        if (!a.getClass().isArray())
            return a.toString();

        int iMax = Array.getLength(a) - 1;
        if (iMax < 1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            Object ele = Array.get(a, i);
            if (ele == null)
                b.append("null");
            else if (ele.getClass().isArray())
                b.append(array2String(ele));
            else
                b.append(ele.toString());

            if (i == iMax)
                return b.append(']').toString();
            else// continue
                b.append(", ");
        }
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
