package org.r2.devkit.util;

import org.r2.devkit.exception.BeanException;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author ruan4261
 */
public final class BeanUtil {

    /**
     * 通过字段键值对生成对象
     * 禁止空参数
     *
     * @throws BeanException 无法成功构造类实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) throws BeanException {
        Assert.notNull(map);
        Assert.notNull(clazz);
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            // 任意一个可行的构造器
            try {
                if (!constructor.isAccessible())
                    constructor.setAccessible(true);

                Parameter[] params = constructor.getParameters();
                Object[] args = new Object[params.length];

                for (int i = 0; i < params.length; i++) {
                    Parameter param = params[i];
                    String paramName = param.getName();
                    Class fieldType = param.getType();
                    Object body = map.get(paramName);
                    if (body == null || fieldType == Object.class)
                        args[i] = body;
                    else
                        args[i] = convert(fieldType, body);
                }

                // construct
                T instance = (T) constructor.newInstance(args);
                return fillObject(map, instance);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException ignore) {
                // try next constructor
            }
        }
        throw new BeanException("Cannot create " + clazz.getTypeName() + " instance.");
    }

    /**
     * 将所有实例字段作为键值
     * 值为实例当前的字段状态
     *
     * @param filter 该参数bit对应关键字将被过滤
     * @see #queryFields(Class, int)
     */
    public static Map<String, Object> object2Map(Object object, int filter) {
        Assert.notNull(object);
        Class clazz = object.getClass();

        Field[] fields = queryFields(clazz, filter);

        Map<String, Object> map = new HashMap<>(fields.length);
        for (Field field : fields) {
            try {
                if (!field.isAccessible())
                    field.setAccessible(true);

                map.put(field.getName(), field.get(object));
            } catch (IllegalAccessException ignore) {
            }
        }
        return map;
    }

    /**
     * 获取类的字段，参数为true时过滤对应关键字字段
     * 进制从低位到高位的分部如下，step=1     位值
     * public                              1
     * private                             2
     * protected                           4
     * static                              8
     * final                               16
     * synchronized                        32
     * volatile                            64
     * transient                           128
     * native                              256
     * interface                           512
     * abstract                            1024
     * strict                              2048
     *
     * @param filter 该参数bit对应关键字将被过滤
     */
    public static Field[] queryFields(Class clazz, final int filter) {
        Field[] fields = ArrayUtil.concat(clazz.getFields(), clazz.getDeclaredFields());
        return Arrays.stream(fields)
                .distinct()
                .filter(field -> {
                    int modifier = field.getModifiers();
                    return (modifier & filter) == 0;
                }).toArray(Field[]::new);
    }

    /**
     * 填充实例对象字段
     * static, final 关键字修饰的字段不会发生更改
     * 类型不匹配且无法转换的字段将被跳过
     */
    public static <T> T fillObject(Map<String, Object> state, T object) {
        Class clazz = object.getClass();
        Field[] fields = queryFields(clazz, (8 + 16));
        for (Field field : fields) {
            String key = field.getName();
            Object body = state.get(key);
            if (body != null) {
                try {
                    if (!field.isAccessible())
                        field.setAccessible(true);

                    field.set(object, convert(field.getType(), body));
                } catch (IllegalAccessException | BeanException ignore) {
                }
            }
        }
        return object;
    }

    /**
     * @param clazz  目标类型，不可为空
     * @param object 源，该参数为空，整个方法返回值为空
     * @throws BeanException 无法转换类型实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T convert(Class<T> clazz, Object object) throws BeanException {
        Assert.notNull(clazz);
        if (object == null) return null;
        Class origin = object.getClass();

        // 继承关系
        if (clazz.isInstance(object)) {
            return (T) object;
        }

        // 目标类型为数组
        else if (clazz.isArray()) {
            // 数组元素类型
            Class type = clazz.getComponentType();
            if (origin.isArray()) {
                // array2array
                int len = Array.getLength(origin);
                if (len == 0) return (T) Array.newInstance(type, 0);
                // todo

            } else if (Collection.class.isAssignableFrom(clazz)) {
                // collection2array
                Collection collection = (Collection) object;
                int len = collection.size();
                if (len == 0) return (T) Array.newInstance(type, 0);
                // 依次填充，T是数组类型
                T arr = (T) Array.newInstance(type, len);
                {
                    int idx = 0;
                    for (Object ele : collection) {
                        Array.set(arr, idx++, convert(type, ele));
                    }
                    return arr;
                }
            }
        }

        // 目标类型为集合框架
        else if (Collection.class.isAssignableFrom(clazz)) {
            // todo
        }

        // 源为字符串
        else if (object instanceof CharSequence) {
            if (isPrimitive(clazz))
                return o2Primitive(clazz, object);
        }

        // 目标为字符串
        else if (CharSequence.class.isAssignableFrom(clazz)) {
            return (T) object.toString();
        }

        throw new BeanException(origin.toString() + " instance cannot convert to " + clazz.toString() + " instance.");
    }

    public static boolean isPrimitive(Class clazz) {
        switch (clazz.getSimpleName()) {
            case "Byte":
            case "Integer":
            case "Float":
            case "Double":
            case "Short":
            case "Long":
            case "Boolean":
            case "Character":
            case "Void":
                return true;
            default:
                return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T o2Primitive(Class<T> clazz, Object val) throws BeanException {
        switch (clazz.getSimpleName()) {
            case "Byte":
                return (T) o2Byte(val);
            case "Integer":
                return (T) o2Integer(val);
            case "Float":
                return (T) o2Float(val);
            case "Double":
                return (T) o2Double(val);
            case "Short":
                return (T) o2Short(val);
            case "Long":
                return (T) o2Long(val);
            case "Boolean":
                return (T) o2Boolean(val);
            case "Character":
                return (T) o2Character(val);
            case "Void":
                return null;
            default:
                throw new BeanException(clazz.toString() + " is not a primitive class.");
        }
    }

    /**
     * 解析失败后不会抛出异常，默认返回false
     */
    public static Boolean o2Boolean(Object val) throws BeanException {
        if (val == null) return false;
        if (val instanceof Boolean)
            return (Boolean) val;
        if (val instanceof Number)
            return ((Number) val).intValue() > 0;
        if (val instanceof Character) {
            char c = (char) val;
            return c == 'T'
                    || c == 't'
                    || c == 'Y'
                    || c == 'y'
                    || c == '1';
        }
        if (!(val instanceof String))
            throw new BeanException(val.getClass().toString() + "cannot convert to Boolean.");

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
        } else if (firstChar == '+' || (firstChar >= '0' && firstChar <= '9')) {
            try {
                return new BigDecimal(str).compareTo(BigDecimal.ZERO) > 0;
            } catch (NumberFormatException ignore) {
                return false;
            }
        } else return false;
    }

    public static Integer o2Integer(Object val) throws BeanException {
        if (val == null) return null;
        if (val instanceof Number) return ((Number) val).intValue();
        if (val instanceof Boolean) return ((Boolean) val) ? 1 : 0;
        if (val instanceof Character) return val.hashCode();
        if (val instanceof CharSequence) {
            try {
                String v = val.toString();
                return new BigDecimal(v).intValue();
            } catch (NumberFormatException e) {
                throw new BeanException(e);
            }
        }
        throw new BeanException(val.getClass().toString() + " cannot convert to Integer.");
    }

    public static Double o2Double(Object val) throws BeanException {
        if (val == null) return null;
        if (val instanceof Number) return ((Number) val).doubleValue();
        if (val instanceof Boolean) return ((Boolean) val) ? 1D : 0D;
        if (val instanceof CharSequence) {
            try {
                String v = val.toString();
                return new BigDecimal(v).doubleValue();
            } catch (NumberFormatException e) {
                throw new BeanException(e);
            }
        }
        throw new BeanException(val.getClass().toString() + " cannot convert to Double.");
    }

    public static Float o2Float(Object val) throws BeanException {
        if (val == null) return null;
        if (val instanceof Number) return ((Number) val).floatValue();
        if (val instanceof Boolean) return ((Boolean) val) ? 1F : 0F;
        if (val instanceof CharSequence) {
            try {
                String v = val.toString();
                return new BigDecimal(v).floatValue();
            } catch (NumberFormatException e) {
                throw new BeanException(e);
            }
        }
        throw new BeanException(val.getClass().toString() + " cannot convert to Float.");
    }

    public static Short o2Short(Object val) throws BeanException {
        if (val == null) return null;
        if (val instanceof Number) return ((Number) val).shortValue();
        if (val instanceof Boolean) return ((Boolean) val) ? (short) 1 : (short) 0;
        if (val instanceof CharSequence) {
            try {
                String v = val.toString();
                return new BigDecimal(v).shortValue();
            } catch (NumberFormatException e) {
                throw new BeanException(e);
            }
        }
        throw new BeanException(val.getClass().toString() + " cannot convert to Short.");
    }

    public static Long o2Long(Object val) throws BeanException {
        if (val == null) return null;
        if (val instanceof Number) return ((Number) val).longValue();
        if (val instanceof Boolean) return ((Boolean) val) ? 1L : 0L;
        if (val instanceof CharSequence) {
            try {
                String v = val.toString();
                return new BigDecimal(v).longValue();
            } catch (NumberFormatException e) {
                throw new BeanException(e);
            }
        }
        throw new BeanException(val.getClass().toString() + " cannot convert to Long.");
    }

    public static Byte o2Byte(Object val) throws BeanException {
        if (val == null) return null;
        if (val instanceof Number) return ((Number) val).byteValue();
        if (val instanceof Boolean) return ((Boolean) val) ? (byte) 1 : (byte) 0;
        if (val instanceof CharSequence) {
            try {
                String v = val.toString();
                return new BigDecimal(v).byteValue();
            } catch (NumberFormatException e) {
                throw new BeanException(e);
            }
        }
        throw new BeanException(val.getClass().toString() + " cannot convert to Byte.");
    }

    public static Character o2Character(Object val) throws BeanException {
        if (val == null) return null;
        if (val instanceof Character) return (Character) val;
        if (val instanceof Number) return (char) ((Number) val).intValue();
        if (val instanceof CharSequence) {
            String v = val.toString();
            return v.charAt(0);
        }
        throw new BeanException(val.getClass().toString() + " cannot convert to Byte.");
    }

}
