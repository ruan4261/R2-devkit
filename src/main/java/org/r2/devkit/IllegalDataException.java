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

/**
 * 随时起飞，异常特别开心。
 * 该异常表明某数据输入非法，或是程序运行过程中产生了非法数据。
 * 概念比较宽泛，本包仅使用其作为断言失败时抛出的异常。
 * 你可以认为该异常涵盖的意义包括（但不限于）:
 * {@link IllegalArgumentException}
 * {@link NumberFormatException}
 * {@link NullPointerException}
 *
 * @author ruan4261
 */
@SuppressWarnings("serial")
public class IllegalDataException extends RuntimeException {

    public IllegalDataException() {
        super();
    }

    public IllegalDataException(String message) {
        super(message);
    }

    public IllegalDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalDataException(Throwable cause) {
        super(cause);
    }
}
