package org.r2.devkit.test.system;

import org.r2.devkit.test.TestCore;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class MemoryMXBeanTest extends TestCore {

    public static void main(String[] args) {
        MemoryMXBean mxBean = ManagementFactory.getMemoryMXBean();
        print("ObjectPendingFinalizationCount", mxBean.getObjectPendingFinalizationCount());// 待处理的对象的大致数量
        print("HeapMemoryUsage", mxBean.getHeapMemoryUsage());// 堆内存使用情况
        print("NonHeapMemoryUsage", mxBean.getNonHeapMemoryUsage());// 非堆内存使用情况
        print("Verbose", mxBean.isVerbose());// 是否启用了内存系统的详细输出
    }

}
