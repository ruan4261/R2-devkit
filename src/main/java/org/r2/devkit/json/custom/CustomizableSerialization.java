package org.r2.devkit.json.custom;

import org.r2.devkit.serialize.CustomSerializer;

/**
 * 继承该接口的对象应该持有CustomSerializer对象
 *
 * @author ruan4261
 */
public interface CustomizableSerialization {

    /**
     * 完全替换当前对象序列化机制
     * 并且其内部所继承的CustomizableSerialization的元素都应该被同步
     */
    void syncCustomSerializer(CustomSerializer customSerializer);

    /**
     * 完全替换当前对象序列化机制
     */
    void setCustomSerializer(CustomSerializer customSerializer);

    CustomSerializer getCustomSerializer();

    /**
     * 删除当前对象序列化机制
     */
    void removeCustomSerializer();

    /**
     * 删除当前对象序列化机制
     * 并且其内部所继承的CustomizableSerialization的元素都应该被同步
     */
    void removeAllCustomSerializer();

}