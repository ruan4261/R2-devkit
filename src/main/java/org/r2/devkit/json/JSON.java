package org.r2.devkit.json;

/**
 * Json对象
 * 必须重写toString，输出实际内容
 *
 * @author ruan4261
 */
public abstract class JSON implements JsonAware {

    public abstract String toString();

}
