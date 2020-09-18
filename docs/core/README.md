## org.r2.devkit.core
* [SystemAPI](#systemapi)
* [CacheBase](#cachebase)

### SystemAPI
**\#String getCompleteStackTraceInfo(String)**\
获取栈跟踪信息的方法，顶栈为调用本方法的方法栈。

**Param**
* title(String) 自定义一条信息，类似于`Throwable`类的`detailMessage`

**Example**\
测试类如下
```java
 public class SystemAPIDemo {
 
     public static void main(String[] args) {
         SystemAPIDEmo test = new SystemAPIDemo();
         test.outStackMethod();
     }
 
     public void outStackMethod() {
         business();
     }
 
     public void business() {
         System.err.println(org.r2.devkit.core.SystemAPI.getCompleteStackTraceInfo("Business call title."));
     }
 }
```
控制台打印信息如下
```
Business call title.
 	at org.r2.devkit.test.core.SystemAPIDemo.business(SystemAPITest.java:40)
 	at org.r2.devkit.test.core.SystemAPIDemo.outStackMethod(SystemAPITest.java:36)
 	at org.r2.devkit.test.core.SystemAPIDemo.main(SystemAPITest.java:32)

 
 Process finished with exit code 0
```
**\#StackTraceElement\[] getStackTrace()**\
获取栈跟踪信息，以本方法栈为顶栈的`StackTraceElement`数组。
```java
    return (new Throwable()).getStackTrace();
```
**\#long currentTimestamp()**\
当前时间戳，完全等同于`java.lang.System#currentTimeMillis`。

**\#long nanoTime()**\
纳秒记数，只能用于计时、相对比较，完全等同于`java.lang.System#nanoTime`。

### CacheBase
```java
    String NULL = "null";
    String EMPTY = "";
    String LINE_SEPARATOR = System.lineSeparator();// 系统换行符('\n'或'\r\n'或其他)
    String FILE_SEPARATOR = File.separator;// 文件系统名称分隔符('/'或'\'或其他)
    String PATH_SEPARATOR = File.pathSeparator;// 文件系统路径分隔符(':'或';'或其他)
    DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    DateTimeFormatter MILLIS_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    DateTimeFormatter MILLIS_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    DateTimeFormatter DATETIME_WITH_T = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
```