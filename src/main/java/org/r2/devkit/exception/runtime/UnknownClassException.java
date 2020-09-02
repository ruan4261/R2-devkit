package org.r2.devkit.exception.runtime;

/**
 * 表明本开发组件不支持某个类的使用。
 * 程序某个地方获得了错误的类型。
 *
 * @author ruan4261
 */
public class UnknownClassException extends RuntimeException {

    private Class clazz;

    public UnknownClassException() {
        super();
    }

    public UnknownClassException(String message) {
        super(message);
    }

    public UnknownClassException(Class clazz) {
        super(clazz.getTypeName() + " is not support class.");
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
