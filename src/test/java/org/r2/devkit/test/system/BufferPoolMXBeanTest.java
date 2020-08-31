package org.r2.devkit.test.system;

import org.r2.devkit.test.TestCore;

import java.lang.management.BufferPoolMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class BufferPoolMXBeanTest extends TestCore {

    public static void main(String[] args) {
        List<BufferPoolMXBean> mxBeans = ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class);
        for (BufferPoolMXBean mxBean : mxBeans) {
            print("---------------------------------------- " + mxBean.getName() + " ----------------------------------------");
            print("Name", mxBean.getName());// 当前缓冲池名称
            print("Count", mxBean.getCount());// 当前池内缓冲区数量（估计值）
            print("TotalCapacity", mxBean.getTotalCapacity() / MBytes);// 当前池内缓冲区总容量（估计值）
            print("MemoryUsed", mxBean.getMemoryUsed() / MBytes);// 当前池所使用的内存（估计值）
        }
    }

}
