package org.r2.devkit.bean;

import org.r2.devkit.Assert;
import org.r2.devkit.env.GlobalBeanConverter;
import org.r2.devkit.BeanException;
import org.r2.devkit.util.ReflectUtil;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author ruan4261
 */
public final class BeanUtil {

    public static final GlobalBeanConverter CONVERTER = GlobalBeanConverter.getInstance();

    /**
     * 通过字段键值对生成对象
     * 对于非空参构造来说，map中必须拥有构造参数的名称映射实例的键值，否则无法完成对象构造
     *
     * @throws BeanException 无法构造类实例
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
                // static和final字段被过滤
                return fillObject(map, instance, (8 + 16));
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException ignore) {
                // try next constructor
            }
        }
        throw new BeanException("Cannot create " + clazz.getTypeName() + " instance.");
    }

    /**
     * 将所有实例字段作为键值
     * 值为实例当前的字段状态
     * 获取的字段包括父类
     *
     * @param filter 该参数bit对应关键字将被过滤
     * @see ReflectUtil#queryFields(Class, int, boolean)
     */
    public static Map<String, Object> object2Map(Object object, int filter) {
        Assert.notNull(object);
        Class clazz = object.getClass();

        Field[] fields = ReflectUtil.queryFields(clazz, filter, true);

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
     * 填充实例对象字段
     * 类型不匹配且无法转换的字段将被跳过
     *
     * @param filter 被过滤的字段，将不会产生修改
     */
    public static <T> T fillObject(Map<String, Object> state, T object, int filter) {
        Class clazz = object.getClass();
        Field[] fields = ReflectUtil.queryFields(clazz, filter, true);
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
     * 该方法尽最大可能允许任意类型间的转换
     * CASE
     * 1.继承关系
     * 2.全局自定义转换器
     * 3.目标为基本类型
     * 4.目标为字符串（任何类型都会强行转换为字符串）
     * 5.目标为数组或集合框架（该情况下，如果源类型不为数组或集合框架，会抛出异常）
     * 6.不同Map子类之间的数据转移
     * 7.map源转换为贫血模型
     * 8.贫血模型转换为map实例
     * 9.贫血模型转换为贫血模型
     *
     * @param clazz    目标类型，不可为空
     * @param object   源，该参数为空，整个方法返回值为空
     * @param <T>      目标对象类型
     * @param <TChild> 如果目标对象类型T是集合框架，此泛型是T的类型参数，否则TChild不存在
     * @throws BeanException 无法转换类型实例
     */
    @SuppressWarnings("unchecked")
    public static <T, TChild> T convert(Class<T> clazz, Object object) throws BeanException {
        Assert.notNull(clazz);
        if (object == null) return null;
        Class origin = object.getClass();

        // 1.继承关系
        if (clazz.isInstance(object)) {
            return (T) object;
        }

        // 2.全局自定义转换器
        if (CONVERTER.isExist(object.getClass(), clazz)) {
            return CONVERTER.convert(clazz, object);
        }

        // 3.目标为基本类型
        if (isPrimitive(clazz)) {
            return o2Primitive(clazz, object);
        }

        // 4.目标为字符串
        if (CharSequence.class.isAssignableFrom(clazz)) {
            return (T) object.toString();
        }

        // 5.1.目标类型为数组
        if (clazz.isArray()) {
            // 数组元素类型
            Class type = clazz.getComponentType();

            // 辨别源类型
            if (origin.isArray()) {
                // array2array
                int len = Array.getLength(origin);
                if (len == 0) return (T) Array.newInstance(type, 0);

                // 依次填充，T是数组类型
                T arr = (T) Array.newInstance(type, len);
                {
                    for (int i = 0; i < len; i++)
                        Array.set(arr, i, convert(type, Array.get(object, i)));

                    return arr;
                }
            } else if (object instanceof Collection) {
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

        // 5.2.目标类型为集合框架
        if (Collection.class.isAssignableFrom(clazz)) {
            // 获取目标泛型
            Class<TChild> type = null;
            {
                // 尝试获取泛型
                Type superclass = clazz.getGenericSuperclass();
                if (superclass instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) superclass;
                    Type[] paramTypes = parameterizedType.getActualTypeArguments();
                    if (paramTypes != null && paramTypes.length > 0) {
                        Type t = paramTypes[0];
                        if (t instanceof ParameterizedType)
                            type = (Class<TChild>) ((ParameterizedType) t).getRawType();
                        else if (t instanceof Class)
                            type = (Class<TChild>) t;
                    }
                }
            }

            if (type == null) type = (Class<TChild>) Object.class;
            // 此对象将作为返回值
            Collection<TChild> targetCollection;
            try {
                targetCollection = (Collection<TChild>) clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new BeanException("Cannot create collection instance, cause : " + e.getMessage(), e);
            }

            // 辨别源类型
            if (origin.isArray()) {
                // array2collection
                int len = Array.getLength(origin);
                for (int i = 0; i < len; i++) {
                    targetCollection.add(convert(type, Array.get(origin, i)));
                }

            } else if (object instanceof Collection) {
                // collection2collection
                Collection collection = (Collection) object;
                for (Object ele : collection) {
                    targetCollection.add(convert(type, ele));
                }
            }
            return (T) targetCollection;
        }

        // 6.map -> map
        if (object instanceof Map && Map.class.isAssignableFrom(clazz)) {
            try {
                Map ori = (Map) object;
                Map dest = (Map) clazz.newInstance();
                dest.putAll(ori);
                return (T) dest;
            } catch (IllegalAccessException | InstantiationException e) {
                throw new BeanException(e);
            }
        }

        // 7.map -> 贫血模型
        if (object instanceof Map) {
            return map2Object((Map<String, Object>) object, clazz);
        }

        // 8.贫血模型 -> map
        if (Map.class.isAssignableFrom(clazz)) {
            // 过滤了static和final字段
            Map<String, Object> dat = object2Map(object, 8 + 16);
            try {
                Map dest = (Map) clazz.newInstance();
                dest.putAll(dat);
                return (T) dest;
            } catch (IllegalAccessException | InstantiationException e) {
                throw new BeanException(e);
            }
        }

        // 9.贫血模型 -> 贫血模型
        Map<String, Object> dat = object2Map(object, 8 + 16);
        return map2Object(dat, clazz);
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
     * 如果目标类型重写了toString方法，则在LastCase下会判断toString
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
        if (!(val instanceof String) && !hasOwnMethod(val.getClass(), "toString", 8))
            throw new BeanException(val.getClass().toString() + "cannot convert to Boolean.");

        String str = val.toString();
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
        if (val instanceof CharSequence || hasOwnMethod(val.getClass(), "toString", 8)) {
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
        if (val instanceof CharSequence || hasOwnMethod(val.getClass(), "toString", 8)) {
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
        if (val instanceof CharSequence || hasOwnMethod(val.getClass(), "toString", 8)) {
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
        if (val instanceof CharSequence || hasOwnMethod(val.getClass(), "toString", 8)) {
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
        if (val instanceof CharSequence || hasOwnMethod(val.getClass(), "toString", 8)) {
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
        if (val instanceof CharSequence || hasOwnMethod(val.getClass(), "toString", 8)) {
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
        if (val instanceof CharSequence || hasOwnMethod(val.getClass(), "toString", 8)) {
            String v = val.toString();
            return v.charAt(0);
        }
        throw new BeanException(val.getClass().toString() + " cannot convert to Byte.");
    }

    /**
     * 检查目标类型是否有对应方法
     * 继承的方法不算在内
     * 这个方法一般被用于检查子类是否重写了父类方法
     *
     * @param clazz      检查的目标类型
     * @param methodName 目标方法
     * @param filter     修饰符过滤器
     * @param paramTypes 方法参数类型
     * @see ReflectUtil#queryFields(Class, int, boolean) filter描述
     */
    public static <T> boolean hasOwnMethod(Class<T> clazz, String methodName, int filter, Class<?>... paramTypes) {
        Assert.notNull(clazz);
        Assert.notNull(methodName);
        int paramCount = paramTypes.length;
        Method[] ownMethods = clazz.getDeclaredMethods();

        outer:
        for (Method method : ownMethods) {
            // 优先检查参数数量和修饰符，比先检查类型名称的效率高
            int pc = method.getParameterCount();
            int modifier = method.getModifiers();

            if ((pc == paramCount) && ((modifier & filter) == 0)) {
                if (methodName.equals(method.getName())) {
                    Class<?>[] types = method.getParameterTypes();
                    for (int i = 0; i < pc; i++) {
                        if (types[i] != paramTypes[i])
                            continue outer;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 复制对象属性
     *
     * @param origin 源
     * @param dest   目标
     * @param filter 过滤字段
     */
    public static void copyProperties(Object origin, Object dest, int filter) {
        Map<String, Object> dat = object2Map(origin, filter);
        fillObject(dat, dest, filter);
    }
}