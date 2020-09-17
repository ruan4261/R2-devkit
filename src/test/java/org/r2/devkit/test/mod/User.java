package org.r2.devkit.test.mod;

import org.r2.devkit.time.DateTimeAPI;

import java.util.Arrays;

public class User extends People implements DateTimeAPI {

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
