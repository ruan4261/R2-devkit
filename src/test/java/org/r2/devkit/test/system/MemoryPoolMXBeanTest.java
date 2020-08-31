package org.r2.devkit.test.system;

import org.r2.devkit.test.TestCore;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;

public class MemoryPoolMXBeanTest extends TestCore {

    public static void main(String[] args) {
        List<MemoryPoolMXBean> mxBeans = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean mxBean : mxBeans) {
            print("---------------------------------------- " + mxBean.getName() + " ----------------------------------------");
            print("Name", mxBean.getName());// 内存池名称
            print("Type", mxBean.getType());// 内存池类型
            print("Usage", mxBean.getUsage());// 内存池当前使用情况
            print("PeakUsage", mxBean.getPeakUsage());// 内存池最高峰使用情况
            print("Valid", mxBean.isValid());// 内存池在虚拟机内是否有效
            print("MemoryManagerNames", mxBean.getMemoryManagerNames());// 此内存池的内存管理器列表
            print("UsageThresholdSupported", mxBean.isUsageThresholdSupported());// 内存池是否支持使用阈值
            if (mxBean.isUsageThresholdSupported()) {
                print("UsageThresholdExceeded", mxBean.isUsageThresholdExceeded());// 此内存池的内存使用量是否达到或超过其使用阈值
                print("UsageThreshold", mxBean.getUsageThreshold() / MBytes);// 此内存池的使用阈值
                print("UsageThresholdCount", mxBean.getUsageThresholdCount());// 内存使用量超过阈值的次数
            }
            print("CollectionUsageThresholdSupported", mxBean.isCollectionUsageThresholdSupported());// 内存池是否支持集合使用阈值
            if (mxBean.isCollectionUsageThresholdSupported()) {
                print("CollectionUsageThreshold", mxBean.getCollectionUsageThreshold() / MBytes);// 此内存池的收集使用阈值
                print("CollectionUsageThresholdExceeded", mxBean.isCollectionUsageThresholdExceeded());// 此内存池的内存使用量达到或超过最近集合中的集合使用阈值
                print("CollectionUsageThresholdCount", mxBean.getCollectionUsageThresholdCount());// 内存使用量达到或超过集合使用率阈值的次数
                print("CollectionUsage", mxBean.getCollectionUsage());// Java虚拟机最近花费在回收未使用对象之后努力的内存池的内存使用情况
            }
        }
    }

}
