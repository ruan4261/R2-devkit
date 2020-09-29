## org.r2.devkit.serialize
提供序列化接口及实现方案。

* [Serializer](#Serializer)
* [CustomSerializer](#CustomSerializer)
    * [注册序列化器](#注册序列化器)
    * [判断是否有指定类的序列化器](#判断是否有指定类的序列化器)
    * [判断是否有可用的序列化器](#判断是否有可用的序列化器)
    * [获取指定类独栋序列化器](#获取指定类独栋序列化器)

### Serializer
序列化器，函数式接口，入参为类的定义泛型的实例，返回值为String，即该入参序列化的结果。

---
### CustomSerializer
一组自定义的序列化方案的集合，内部维护了一个ConcurrentHashMap，用于类型向指定序列化器的映射。\
该类构造器支持设置初始容量及加载因子，支持使用Map参数构造。

#### 注册序列化器
* `void register(Class<T>, int, Serializer<T>)`
* `void register(Class<T>, Serializer<T>)`此方法默认优先级为-1，仅T类自身可调用

#### 判断是否有指定类的序列化器
* `boolean isExistClassSerializer(Class<?>)`
* `boolean isExistClassSerializer(Object)`

#### 判断是否有可用的序列化器
* `boolean hasCustomizer(Class)`
* `boolean hasCustomizer(Object)`

#### 获取指定类的序列化器
如果不存指定类的序列化器，将会抛出`UnsupportedClassException`异常。
* `Serializer<T> classSerializer(Class<T>)`
* `Serializer<T> classSerializer(T)`

#### 获取优先级最高的序列化器
如果不存在可用的序列化器，将会抛出`UnsupportedClassException`异常。
* `Serializer<T> prioritySerializer(Class<T>)`
* `Serializer<T> prioritySerializer(T)`

本类中还提供了remove方法删除指定Class序列化器，update可修改指定Class序列化器或其优先级，请查看code source。

---