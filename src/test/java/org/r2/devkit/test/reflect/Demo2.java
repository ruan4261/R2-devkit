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
package org.r2.devkit.test.reflect;

import org.r2.devkit.test.TestCore;
import org.r2.devkit.test.mod.User;
import org.r2.devkit.util.BeanUtil;

public class Demo2 extends TestCore {

    public static void main(String[] args) {
        System.out.println(BeanUtil.object2Map(new User(1, "23"), true, true));
        System.out.println(BeanUtil.object2Map(new User(1, "23"), true, false));
        System.out.println(BeanUtil.object2Map(new User(1, "23"), false, true));
    }

}
