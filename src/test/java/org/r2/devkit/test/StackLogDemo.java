package org.r2.devkit.test;

import org.r2.devkit.core.SystemAPI;

import java.io.PrintStream;

public class StackLogDemo {

    public static void main(String[] args) {
        PrintStream out = System.err;
        out.println(SystemAPI.getCompleteStackTraceInfo("No Exception Here~"));
    }

}
