package org.r2.devkit.test.system;

import com.sun.management.OperatingSystemMXBean;
import org.r2.devkit.test.TestCore;

import java.lang.management.ManagementFactory;

public class OperatingSystemMXBeanTest extends TestCore {

    public static void main(String[] args) {
        OperatingSystemMXBean mxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        print("OS", mxBean.getName());// 系统
        print("OSVersion", mxBean.getVersion());// 系统版本
        print("CPU", mxBean.getArch());// 处理器名称
        print("CPUs", mxBean.getAvailableProcessors());// 处理器数量（超线程也会纳入计算）
        print("CpuTime", mxBean.getProcessCpuTime());// 虚拟机进程使用的CPU时间，单位为纳秒
        print("CpuLoad", mxBean.getProcessCpuLoad());// JVM的CPU使用率，需要多次监控取平均值
        print("SystemCpuLoad", mxBean.getSystemCpuLoad());// 整个系统cpu使用率，需要多次监控取平均值
        print("SystemLoadAverage", mxBean.getSystemLoadAverage());// 系统平均负载（很多平台好像不可用）
        print("FreeSwapSpaceSize", mxBean.getFreeSwapSpaceSize() / MBytes);// 空闲交换空间
        print("TotalSwapSpaceSize", mxBean.getTotalSwapSpaceSize() / MBytes);// 总交换空间
        print("FreePhysicalMemorySize", mxBean.getFreePhysicalMemorySize() / MBytes);// 空闲物理内存
        print("TotalPhysicalMemorySize", mxBean.getTotalPhysicalMemorySize() / MBytes);// 总物理内存
        print("CommittedVirtualMemorySize", mxBean.getCommittedVirtualMemorySize() / MBytes);// 简单来说就是可用虚拟内存量
    }

}
