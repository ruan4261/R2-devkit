package org.r2.devkit.test.system;

import org.r2.devkit.test.TestCore;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class GarbageCollectorMXBeanTest extends TestCore {

    public static void main(String[] args) {
        List<GarbageCollectorMXBean> mxBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean mxBean : mxBeans) {
            print("---------------------------------------- " + mxBean.getName() + " ----------------------------------------");
            print("Name", mxBean.getName());// 内存管理器名称
            print("CollectionCount", mxBean.getCollectionCount());// 已发生的集合总数
            print("CollectionTime", mxBean.getCollectionTime());// 大概累积收集时间（单位ms）
        }
    }

}
