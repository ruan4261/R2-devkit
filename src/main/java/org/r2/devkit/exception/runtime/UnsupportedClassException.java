package org.r2.devkit.exception.runtime;

/**
 * 表明本开发组件不支持某个类的使用。
 * 程序某个地方获得了错误的类型。
 *
 * @author ruan4261
 */
public class UnsupportedClassException extends RuntimeException {
    private static final long serialVersionUID = 1456898464438935089L;
    private Class clazz;

    public UnsupportedClassException(Class clazz) {
        super(clazz.getTypeName() + " is not support class.");
        this.clazz = clazz;
    }

    public UnsupportedClassException(Class clazz, String message) {
        super(message);
        this.clazz = clazz;
    }


    public UnsupportedClassException(Class clazz, String message, Throwable cause) {
        super(message, cause);
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

}
