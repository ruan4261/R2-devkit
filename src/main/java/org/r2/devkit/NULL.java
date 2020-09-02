package org.r2.devkit;

import java.io.Serializable;

/**
 * 以其代替一个空指针......
 * 这个类是不是有点蠢- -？好像不止一点
 *
 * @author ruan4261
 */
public final class NULL implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    private final static String LOW = "null";
    private final static String UP = "NULL";

    private NULL() {
    }

    private final static NULL NULL = new NULL();

    public static NULL construct() {
        return NULL;
    }

    public String s() {
        return LOW;
    }

    public String S() {
        return UP;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return s();
    }
}
