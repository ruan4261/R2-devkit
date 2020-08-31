package org.r2.devkit.test.system;

import org.r2.devkit.test.TestCore;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;

public class ClassLoadingMXBeanTest extends TestCore {

    public static void main(String[] args) {
        ClassLoadingMXBean mxBean = ManagementFactory.getClassLoadingMXBean();
        print("TotalLoadedClassCount", mxBean.getTotalLoadedClassCount());// Java虚拟机开始执行以来已加载的类的总数
        print("LoadedClassCount", mxBean.getLoadedClassCount());// 当前在Java虚拟机中加载的类的数量
        print("UnloadedClassCount", mxBean.getUnloadedClassCount());// Java虚拟机开始执行以来卸载的类的总数
        print("Verbose", mxBean.isVerbose());// 加载系统的详细输出是否启用
    }

}
