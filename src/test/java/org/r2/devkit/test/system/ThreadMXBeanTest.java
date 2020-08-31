package org.r2.devkit.test.system;

import com.sun.management.ThreadMXBean;
import org.r2.devkit.test.TestCore;

import java.lang.management.ManagementFactory;

public class ThreadMXBeanTest extends TestCore {

    public static void main(String[] args) {
        ThreadMXBean threadMXBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
        print("ThreadCount", threadMXBean.getThreadCount());// 当前虚拟机总线程数
        print("PeakThreadCount", threadMXBean.getPeakThreadCount());// 虚拟机峰值总线程数
        print("TotalStartedThreadCount", threadMXBean.getTotalStartedThreadCount());// 虚拟机启动以来总线程数
        print("DaemonThreadCount", threadMXBean.getDaemonThreadCount());// 当前虚拟机守护线程数
    }

}
