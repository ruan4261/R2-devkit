## org.r2.devkit.json
提供基于[RFC4627标准](../../rfc4627.txt)实现的JSON解析器。

* [通过JSON字符串生成JSON对象实例](#通过JSON字符串生成JSON对象实例)
* [通过JSON字符串生成JavaBean对象实例](#通过JSON字符串生成JavaBean对象实例)
* [通过JavaBean生成JSON对象](#通过JavaBean生成JSON对象)
* [toJSONString序列化方案](#toJSONString序列化方案)
* 通过JSON对象实例生成JSON字符串，统一使用对象实例（他们都继承了JSONAware）的`toJSONString`方法。

### JSONBean
> JSON包的核心类为`org.r2.devkit.json.JSON`，他是所有JSONBean的超类，有通用的方法入口，通过任何JSONBean均可直接调用JSON的静态方法。\
> JSONBean分为6类，除`JSONObject`和`JSONArray`外还有4类，用于辅助字符串解析。
>
>**JSONObject**\
>实现了`Map<String, Object>`接口\
>实现了`CustomizableSerialization`接口，可以使用`CustomSerializer`类设置子元素的字符串序列化方案，如果不设置字符串序列化，对象会被解析为JSONObject或JSONArray（如果子元素实现了Collection框架）。
>
>**JSONArray**\
>实现了`List<Object>`接口\
>实现了`CustomizableSerialization`接口，可以使用`CustomSerializer`类设置子元素的字符串序列化方案，如果不设置字符串序列化，对象会被解析为JSONObject或JSONArray（如果子元素实现了Collection框架）。
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
* `JSON#parse(String, Class<?>)`返回参数中Class的实例
* `JSON#parseArray(String, Class<?>)`返回List实例，泛型为参数Class的实际类型`?`

### 通过JavaBean生成JSON对象
* `JSON#toJSON(Object)`传入对象为JSON直接返回，为数组或Collection框架时返回JSONArray实例，其他情况下默认为贫血模型转换为JSONObject实例返回。

### toJSONString序列化方案
任何JSONBean（六类）都实现了`toJSONString`
* JSONValueNull——固定返回`null`
* JSONValueBoolean——固定返回`true`和`false`
* JSONValueNumber——返回实际数值，调用`BigDecimal`实例的`toString()`方法
* JSONValueString——返回转义后的字符串，并且第一个字符和最后一个字符都为`"`
* JSONObject——返回json对象，如`{"a": "tom", "b": null}`，内部可设置`CustomSerializer`
* JSONArray——返回json数组，如`["jerry", null, false, 1.67E+10, {"a": "tom"}]`，内部可设置`CustomSerializer`

`JSONObject`和`JSONArray`这两类有比较特殊的序列化方案，他们都实现了`org.r2.devkit.json.custom.CustomizableSerialization`接口。
该接口有三个方法，分别用于设置，置换，删除实现类实例内部的`CustomSerializer`实例。
> 在这两类进行序列化时，如果内部的`CustomSerializer`（以下称作"方案组"）不为空：\
> **子元素**将在方案组内寻找其所属类的指定序列化器进行序列化（序列化的结果类型为`String`），如果没有找到指定类的序列化器，将尝试其他预置方案（继承了Number、实现了Map或Collection、为数组、为字符串序列、重写了toString）。
> 如果预置方案不可行，将在方案组内寻找其超类或实现接口的序列化器列表，选择优先级最大且为正数的序列化器调用。

### 其他
待补充