package org.r2.devkit.test.system;

import org.r2.devkit.test.TestCore;

public class RuntimeTest extends TestCore {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        print("CPU", runtime.availableProcessors());
        print("FreeMemory(MB)", runtime.freeMemory() / MBytes);
        print("TotalMemory(MB)", runtime.totalMemory() / MBytes);
        print("MaxMemory(MB)", runtime.maxMemory() / MBytes);
    }

}
