package org.r2.devkit.json.custom;

/**
 * 继承该接口的对象应该持有CustomSerializer对象
 *
 * @author ruan4261
 */
public interface CustomizableSerialize {

    /**
     * 同步并更新序列化机制
     */
    void addCustomSerializer(CustomSerializer customSerializer);

    /**
     * 完全替换序列化机制
     */
    void setCustomSerializer(CustomSerializer customSerializer);

    CustomSerializer getCustomSerializer();
}