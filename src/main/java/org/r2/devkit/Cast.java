/*
 * MIT License
 *
 * Copyright (c) 2019-present,  ruan4261
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.r2.devkit;

import org.r2.devkit.bean.BeanUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.r2.devkit.core.CacheBase.*;

/**
 * 强制转换数据类型，你可以通过选定默认值来避免抛出异常。
 *
 * @author ruan4261
 */
public final class Cast {

    private Cast() {
        throw new AssertionError("No Cast instances for you!");
    }

    /**
     * 调用{@code val.toString()}，如果{@code val}为{@code null}，返回参数{@code defaultVal}，
     */
    public static String o2String(Object val, String defaultVal) {
        String s;
        return val == null ? defaultVal : (s = val.toString()) == null ? defaultVal : s;
    }

    /**
     * 参数为NULL时返回空字符串
     */
    public static String o2String(Object val) {
        return val == null ? NULL : o2String(val, EMPTY);
    }

    /**
     * 默认返回{@code BigDecimal.ZERO}
     */
    public static BigDecimal o2BigDecimal(Object val) {
        if (val == null) return BigDecimal.ZERO;
        try {
            if (val instanceof Integer) {
                return new BigDecimal((Integer) val);
            } else if (val instanceof Long) {
                return new BigDecimal((Long) val);
            } else if (val instanceof BigInteger) {
                return new BigDecimal((BigInteger) val);
            } else if (val instanceof Number) {
                return new BigDecimal(val.toString());
            } else if (val instanceof CharSequence || BeanUtil.hasOwnMethod(val.getClass(), "toString", 8)) {
                return new BigDecimal(val.toString());
            }
        } catch (ArithmeticException | NumberFormatException ignore) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;
    }

    /**
     * 默认返回-1
     */
    public static Integer o2Integer(Object val) {
        if (val == null) return -1;
        try {
            if (val instanceof Number) {
                return ((Number) val).intValue();
            } else if (val instanceof CharSequence || BeanUtil.hasOwnMethod(val.getClass(), "toString", 8)) {
                return Integer.parseInt(val.toString());
            }
        } catch (NumberFormatException ignore) {
            return -1;
        }
        return -1;
    }

}
