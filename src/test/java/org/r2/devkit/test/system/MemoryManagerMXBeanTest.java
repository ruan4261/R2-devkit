package org.r2.devkit.test.system;

import org.r2.devkit.test.TestCore;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryManagerMXBean;
import java.util.List;

public class MemoryManagerMXBeanTest extends TestCore {

    public static void main(String[] args) {
        List<MemoryManagerMXBean> mxBeans = ManagementFactory.getMemoryManagerMXBeans();
        for (MemoryManagerMXBean mxBean : mxBeans) {
            print("---------------------------------------- " + mxBean.getName() + " ----------------------------------------");
            print("Name", mxBean.getName());// 内存管理器名称
            print("Valid", mxBean.isValid());// 此内存管理器在虚拟机中是否有效
            print("MemoryPoolNames", mxBean.getMemoryPoolNames());// 此内存管理器管理的内存池的名称
        }
    }

}
