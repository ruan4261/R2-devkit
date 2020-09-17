## org.r2.devkit.json
提供基于[RFC4627标准](../rfc4627.txt)实现的JSON解析器。

* [通过JSON字符串生成JSON对象实例](#通过JSON字符串生成JSON对象实例)
* [通过JSON字符串生成JavaBean对象实例](#通过JSON字符串生成JavaBean对象实例)
* 通过JSON对象实例生成JSON字符串，统一使用对象实例（他们都继承了JSONAware）的`toJSONString`方法。

### JSONBean
> JSON包的核心类为`org.r2.devkit.json.JSON`，他是所有JSONBean的超类，有通用的方法入口。\
> JSONBean分为6类，除`JSONObject`和`JSONArray`外还有4类，用于辅助字符串解析。
>
>**JSONObject**\
>实现了Map<String, Object>接口\
>实现了CustomizableSerialization接口，可以使用CustomSerializer类设置子元素的字符串序列化方案，如果不设置字符串序列化，对象会被解析为JSONObject或JSONArray（如果子元素实现了Collection框架）。
>
>**JSONArray**\
>实现了List\<Object>接口\
>实现了CustomizableSerialization接口，可以使用CustomSerializer类设置子元素的字符串序列化方案，如果不设置字符串序列化，对象会被解析为JSONObject或JSONArray（如果子元素实现了Collection框架）。
>
>**Other**
>* org.r2.devkit.json.field.JSONValueString
>* org.r2.devkit.json.field.JSONValueNumber
>* org.r2.devkit.json.field.JSONValueBoolean
>* org.r2.devkit.json.field.JSONValueNull
>
> 这四类也作为JSON的对象实例，可以从字符串解析中生成。\
> 例如不带引号的字符串 `null` 会被解析为JSONValueNull实例。\
> 字符串`-1.41E-1`等十进制数值表达式会被解析为JSONValueNumber实例。\
> 字符串`false`或`true`会被解析为JSONValueBoolean实例，他们是大小写严格的。\
> JSONValueString比较特殊，`tom`、`jerry`这样的字符串不会被解析成功，而会产生异常。因为JSON语法规范内的字符串必须被双引号（规范）或单引号（不规范）包括在内。只有像`"tom"`、`'jerry'`这样的字符串才能被解析。\
> 在JSON中的字符串内的引号等特殊符号（见RFC4627）都必须被转义，否则会引发异常。

### 通过JSON字符串生成JSON对象实例
>如果你能够提前知晓字符串生成的JSON实例类型（`JSONObject`或`JSONArray`），你可以通过指定类的静态方法生成实例。\
>如果你无法提前知晓字符串生成的JSON实例类型，请使用`JSON`的`parse(String)`方法进行解析，而后使用instanceof判断实例类型。

能够解析JSONString并返回JSON对象实例的方法：
* `JSON#parse(String)` 返回JSON的6种Bean之一
* `JSONObject#parseObject(String)`返回JSONObject实例
* `JSONArray#parseArray(String)`返回JSONArray实例

### 通过JSON字符串生成JavaBean对象实例

todo