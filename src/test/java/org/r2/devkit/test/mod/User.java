package org.r2.devkit.test.mod;

import org.r2.devkit.time.DateTimeAPI;
import org.r2.devkit.util.ArrayUtil;
import org.r2.devkit.util.ReflectUtil;

import java.util.Arrays;

public class User extends People implements DateTimeAPI, Cloneable {

    private static final long serialVersionUID = 7733097292216608671L;
    public int code;
    private String address;
    private String password;

    private User() {
    }

    public User(int code, String address) {
        this.code = code;
        this.address = address;
    }
}
