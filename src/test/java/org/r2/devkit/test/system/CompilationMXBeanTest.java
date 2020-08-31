package org.r2.devkit.test.system;

import org.r2.devkit.test.TestCore;

import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;

public class CompilationMXBeanTest extends TestCore {

    public static void main(String[] args) {
        CompilationMXBean mxBean = ManagementFactory.getCompilationMXBean();
        print("Name", mxBean.getName());// JIT编译器的名称
        print("CompilationTimeMonitoringSupported", mxBean.isCompilationTimeMonitoringSupported());// 是否支持编译时间的监视
        if (mxBean.isCompilationTimeMonitoringSupported())
            print("TotalCompilationTime", mxBean.getTotalCompilationTime());// 总的花费的编译时间（单位ms）
    }

}
