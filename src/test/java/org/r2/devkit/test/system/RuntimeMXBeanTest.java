package org.r2.devkit.test.system;

import org.r2.devkit.test.TestCore;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class RuntimeMXBeanTest extends TestCore {

    public static void main(String[] args) {
        RuntimeMXBean mxBean = ManagementFactory.getRuntimeMXBean();
        print("Name", mxBean.getName());// 返回正在运行的虚拟机的名称（他可能是任意名称，例如电脑用户）
        print("SpecName", mxBean.getSpecName());// 虚拟机规范名称，此方法相当于System.getProperty("java.vm.specification.name")
        print("SpecVendor", mxBean.getSpecVendor());// 虚拟机规范供应商，此方法相当于System.getProperty("java.vm.specification.name")
        print("SpecVersion", mxBean.getSpecVersion());// 虚拟机规范版本，此方法相当于System.getProperty("java.vm.specification.version")
        print("ManagementSpecVersion", mxBean.getManagementSpecVersion());// 运行的Java虚拟机实现的管理接口的规范版本
        print("VmName", mxBean.getVmName());// 虚拟机名称，此方法相当于System.getProperty("java.vm.name")
        print("VmVendor", mxBean.getVmVendor());// 虚拟机供应商，此方法相当于System.getProperty("java.vm.vendor")
        print("VmVersion", mxBean.getVmVersion());// 虚拟机版本，此方法相当于System.getProperty("java.vm.version")
        print("InputArguments", mxBean.getInputArguments());// 虚拟机的输入参数
        print("BootClassPathSupported", mxBean.isBootClassPathSupported());// Java虚拟机是否支持bootstrap类加载器用于搜索类文件的引导类路径机制
        if (mxBean.isBootClassPathSupported())
            print("BootClassPath", mxBean.getBootClassPath());// bootstrap类加载器用于搜索类文件的引导类路径，isBootClassPathSupported()可判断此方法是否可用
        print("ClassPath", mxBean.getClassPath());// 此方法相当于System.getProperty("java.class.path")
        print("LibraryPath", mxBean.getLibraryPath());// 此方法相当于System.getProperty("java.library.path")
        print("Uptime", mxBean.getUptime());// Java虚拟机的正常运行时间（单位ms）
        print("StartTime", mxBean.getStartTime());// Java虚拟机的开始时间（单位ms）
        print("SystemProperties", mxBean.getSystemProperties());//  此方法调用System.getProperties()获取所有系统属性
    }

}
