## org.r2.devkit
> #### Package
* [core](/core) ~~没什么用的包~~
* [json](/json) JSON解析生成器
* [serialize](/serialize) 序列化
* [bean](/bean) JavaBean工具包
* [util](/util) 通用工具包
* [io](/io) IO流工具包
* [http](/http) HTTP工具包
* [time](/time) 时间接口
* [env](/env) 运行环境
* [codec](/codec) 编码解码工具包
> #### Class
* [CharLinkedSequence](#CharLinkedSequence) 链表模型的字符串修改
* [MutableString](#MutableString) 可变字符串类
* [Assert](#Assert) 断言校验，提供函数式接口可自定义校验
* [Cast](#Cast) 基本类型转换

### CharLinkedSequence
以链表实现的字符串序列，并没有实现CharSequence接口，提供比StringBuilder更快的字符串修改方式。\
这个类是非线程安全的。
>Construct
>1. 无参构造将提供一个空字符串实例
>2. 带参构造(String)将提供一个以参数作为实际内容的实例

#### 操作手册
* 通用操作
    > `void Replace(CharEditor)`实例方法将提供一个字符操作的函数式接口。函数内部接收一个`char`作为参数，返回值为`char[]`，调用方需提供函数实现。\
    方法将调用接口实现循环实例下的每一个字符，返回值为`null`时，不做操作，如果返回值不为`null`，则用返回的字符数组替换原字符，同理，返回空数组即可删除原字符。\
    新加入的字符不会进入本轮的修改，这个方法是安全的。
* 追加
    >类实现了Appendable接口，可以通过与StringBuilder类似的方式追加字符串。
* 删除
    > 1. `int removeHead(int)`从前往后，删除指定长度的字符串，返回值为删除后实例字符串的长度。
    > 2. `int removeTail(int)`从后往前，删除指定长度的字符串，返回值为删除后实例字符串的长度。
* 生成
    > 使用`toString()`实例方法返回修改后的字符串。他生成的字符串的时间复杂度是O(n)；`StringBuilder`的`toString()`方法的时间复杂度是O(1)。

### MutableString
可修改的字符串，其仅仅只是在内部维护了一个`String`，并提供方法可将其直接替换。\
将此实例作用于进程内部缓存，即可实现一次修改，全局同步。
> 通过方法`String val()`取出实际字符串，修改后再通过`void val(String)`放回即可实现修改。
> 构造方法可传入两个参数，`String`和`boolean`，`String`参数将作为实例内部的字符串，`boolean`确定其字符串可否为`null`，值为`false`时，字符串不可为空，否则引发NPE。

### Assert
内部函数式接口`Judgement`，可以执行任何操作，并最终返回一个`boolean`值
* `void judge(Judgement)`如果`judgement`返回值为`true`，将会引发异常。
* `void judge(Judgement, String)`同上，可定制报错信息。
* 其他校验方法请直接查看code source。

### Cast
* `String o2String(Object, String)`返回`arg0.toString()`，如果`arg0`或`arg0.toString()`为`null`，直接返回`arg1`。
* `String o2String(Object)`如果`arg0`或`arg0.toString()`为`null`，将返回空字符串`''`。
* `BigDecimal o2BigDecimal(Object)`如果无法解析`arg0`，默认返回`BigDecimal.ZERO`。
* `Integer o2Integer(Object)`如果无法解析`arg0`，默认返回`-1`。