package org.r2.devkit.codec;

import org.r2.devkit.util.Assert;

import java.util.Base64;

/**
 * Base64解码工具
 * 通过{@link java.util.Base64}实现
 * 遵循RFC4648
 *
 * @author ruan4261
 */
public interface Base64Decoder {

    static byte[] base64ToByteArray(byte[] base64, boolean isUrlSafe) {
        Assert.notNull(base64);
        Base64.Decoder decoder = isUrlSafe ? Base64.getUrlDecoder() : Base64.getDecoder();
        return decoder.decode(base64);
    }

    static String base64ToString(byte[] base64, boolean isUrlSafe) {
        Assert.notNull(base64);
        return new String(base64ToByteArray(base64, isUrlSafe));
    }

    static String base64ToString(String base64, boolean isUrlSafe) {
        Assert.notNull(base64);
        return base64ToString(base64.getBytes(), isUrlSafe);
    }
}
