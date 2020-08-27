package org.r2.devkit.env;

import org.r2.devkit.io.IOAPI;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;

/**
 * 应用资源文件操作
 * 单例调用
 * 修改资源文件时，根据资源文件名称进行加锁
 *
 * @author ruan4261
 */
public final class AppResources {

    private static AppResources instance;

    static {
        instance = new AppResources();
    }

    private AppResources() {
    }

    public AppResources app() {
        return instance;
    }

    /**
     * 根据资源文件名称获取其完整路径。
     *
     * @param filename 配置文件名
     * @throws IOException 找不到指定配置文件
     */
    public String getResourcePath(final String filename) throws IOException {
        URL url = AppResources.class.getClassLoader().getResource(filename);
        if (url == null) throw new IOException("Resource file [" + filename + "] cannot found.");
        return url.getPath();
    }

    /**
     * 读取配置文件内容，适用于所有类型文件
     *
     * @param filename 资源文件名
     * @param charset  指定字符编码集
     * @throws IOException 无法找到配置文件或其他IO流异常
     */
    public String read(final String filename, Charset charset) throws IOException {
        final String path = getResourcePath(filename);
        return new String(IOAPI.getInputStreamData(new FileInputStream(path)), charset);
    }

    /**
     * 更新配置文件，适用于所有类型文件
     *
     * @param filename 配置文件名
     * @param content  完整的内容，其将覆盖原配置内容，请谨慎操作
     * @throws IOException 无法找到配置文件或其他IO流异常
     */
    public synchronized void update(final String filename, String content) throws IOException {
        final String path = getResourcePath(filename);

        synchronized (filename.intern()) {
            try (BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(path, false))) {
                output.write(content.getBytes());
                output.flush();
            }
        }
    }

    /**
     * 更新配置文件，适用于{@code Properties}文件
     * 不存在{@code updateData}中的键值将会被忽略，其仍然保存在配置文件中
     *
     * @param filename   配置文件名
     * @param updateData 需要更新的键值信息
     * @throws IOException IO流异常
     */
    public void updateProperties(final String filename, Map<String, String> updateData) throws IOException {
        final String path = getResourcePath(filename);

        synchronized (filename.intern()) {
            final Properties prop = new Properties();
            try (InputStream input = new FileInputStream(path)) {
                prop.load(input);

                updateData.forEach(prop::setProperty);

                // 输出流创建不可以放到properties加载之前，否则将会刷掉文件内容
                try (OutputStream output = new FileOutputStream(path)) {
                    prop.store(output, "Update Resource.");
                }
            }
        }
    }

}
