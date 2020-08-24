package org.r2.devkit.serialize;

/**
 * Json格式化工具通用接口
 *
 * @author ruan4261
 */
public interface JSON {

    JSON parse(Object object);

    String toJSONString();

}
