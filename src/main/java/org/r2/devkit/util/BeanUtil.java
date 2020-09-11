package org.r2.devkit.util;

import org.r2.devkit.exception.runtime.IllegalDataException;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ruan4261
 */
public final class BeanUtil {

    /**
     * 通过字段键值对生成对象
     * 禁止空参数
     */
    @SuppressWarnings("unchecked")
    public static <T> T map2Object(Map<String, ?> map, Class<T> clazz) {
        Assert.notNull(map);
        Assert.notNull(clazz);
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            // try construct
            try {
                if (!constructor.isAccessible())
                    constructor.setAccessible(true);

                Parameter[] params = constructor.getParameters();
                Object[] args = new Object[params.length];

                for (int i = 0; i < params.length; i++) {
                    Parameter param = params[i];
                    String paramName = param.getName();
                    Class fieldType = param.getType();
                    args[i] = convert(fieldType, map.get(paramName));
                }

                T instance = (T) constructor.newInstance(args);
                return instance;// todo 待填充字段
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException ignore) {
            }
        }
        throw new IllegalDataException("Cannot create " + clazz.getTypeName() + " instance.");
    }

    /**
     * 将所有实例字段作为键值
     * 值为实例当前的字段状态
     *
     * @param allowStatic    是否包括静态字段
     * @param allowTransient 是否包括不可序列化字段
     */
    public static Map<String, Object> object2Map(Object object, boolean allowStatic, boolean allowTransient) {
        Assert.notNull(object);
        Class clazz = object.getClass();

        Field[] fields = ArrayUtil.delRepeat(Field.class, ArrayUtil.concat(Field.class, clazz.getFields(), clazz.getDeclaredFields()));
        Map<String, Object> map = new HashMap<>(fields.length);
        for (Field field : fields) {
            try {
                int modifier = field.getModifiers();
                if (!allowStatic)
                    if (Modifier.isStatic(modifier)) continue;
                if (!allowTransient)
                    if (Modifier.isTransient(modifier)) continue;
                if (!field.isAccessible())
                    field.setAccessible(true);
                map.put(field.getName(), field.get(object));
            } catch (IllegalAccessException ignore) {
            }
        }
        return map;
    }

    /**
     * 将对象类型转换
     * 任意参数为空返回空值，不抛异常
     * todo
     */
    @SuppressWarnings("unchecked")
    public static <T> T convert(Class<T> clazz, Object object) {
        if (clazz == null || object == null) return null;
        Class origin = object.getClass();

        if (clazz.isInstance(object))
            return (T) object;

        if (clazz.isArray() || Collection.class.isAssignableFrom(clazz)) {
            if (origin.isArray()) {

            }
            return null;
        }

        if (Map.class.isAssignableFrom(clazz)) {

        }

        if (object instanceof String) {
            try {
                switch (clazz.getSimpleName()) {
                    case "Integer":
                        return (T) (Integer) new BigDecimal((String) object).intValue();
                    case "Boolean":
                        return (T) boolOf(object);
                    case "Float":
                        return (T) (Float) new BigDecimal((String) object).floatValue();
                    case "Double":
                        return (T) (Double) new BigDecimal((String) object).doubleValue();
                    case "Short":
                        return (T) (Short) new BigDecimal((String) object).shortValue();
                    case "Long":
                        return (T) (Long) new BigDecimal((String) object).longValue();
                    case "Character":
                        return (T) (Character) ((String) object).charAt(0);
                    case "Byte":
                        return (T) (Byte) Integer.valueOf((String) object).byteValue();
                    case "BigDecimal":
                        return (T) new BigDecimal((String) object);
                    case "BigInteger":
                        return (T) new BigInteger((String) object);
                    default:
                        return null;
                }
            } catch (RuntimeException ignore) {
                return null;
            }
        }

        if (String.class.isAssignableFrom(clazz)) {
        }

        return null;
    }


    public static Boolean boolOf(Object val) {
        if (val == null) return false;
        if (val instanceof Boolean)
            return (Boolean) val;
        if (val instanceof Number)
            return ((Number) val).intValue() > 0;
        if (!(val instanceof String))
            return false;

        String str = (String) val;
        final int len = str.length();

        char firstChar = str.charAt(0);
        if (firstChar == 't'
                || firstChar == 'T'
                || firstChar == 'y'
                || firstChar == 'Y'
                || firstChar == 's'
                || firstChar == 'S') {
            if (len == 1)
                return true;
            return "true".equalsIgnoreCase(str) || "yes".equalsIgnoreCase(str) || "success".equalsIgnoreCase(str);
        } else if (firstChar == '+'
                || (firstChar >= '0' && firstChar <= '9')) {
            try {
                return new BigDecimal(str).compareTo(BigDecimal.ZERO) > 0;
            } catch (NumberFormatException ignore) {
                return false;
            }
        } else return false;
    }

}
