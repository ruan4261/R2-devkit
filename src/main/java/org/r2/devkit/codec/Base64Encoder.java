package org.r2.devkit.codec;

import org.r2.devkit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * Base64编码工具
 * 通过{@link java.util.Base64}实现
 * 遵循RFC4648
 *
 * @author ruan4261
 */
public interface Base64Encoder {

    /**
     * 输入流转base64
     *
     * @param inputStream 输入流
     * @return Base64编码内容，以平台默认编码集输出string内容
     * @throws IOException 异常必定从输入流抛出，本方法抛出异常时输入流必定被关闭
     */
    static String toBase64String(InputStream inputStream, boolean isUrlSafe) throws IOException {
        Assert.notNull(inputStream);
        try (InputStream in = inputStream;
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buff = new byte[1024];
            int read;
            while ((read = in.read(buff)) > 0) out.write(buff, 0, read);
            return toBase64String(out.toByteArray(), isUrlSafe);
        }
    }

    static byte[] toBase64(byte[] bytes, boolean isUrlSafe) {
        Assert.notNull(bytes);
        Base64.Encoder encoder = isUrlSafe ? Base64.getUrlEncoder() : Base64.getEncoder();
        return encoder.encode(bytes);
    }

    static String toBase64String(byte[] bytes, boolean isUrlSafe) {
        Assert.notNull(bytes);
        return new String(toBase64(bytes, isUrlSafe));
    }

    static String toBase64String(String origin, boolean isUrlSafe) {
        Assert.notNull(origin);
        return toBase64String(origin.getBytes(), isUrlSafe);
    }

}
