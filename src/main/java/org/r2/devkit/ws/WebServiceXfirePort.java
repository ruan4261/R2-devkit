package org.r2.devkit.ws;

import org.codehaus.xfire.client.Client;
import java.net.URL;

/**
 * WebService通用调用方式
 * 依赖Xfire
 * 如果参数是自定义对象，不推荐使用此类
 *
 * @author gsw
 */
public final class WebServiceXfirePort {

    /**
     * 通过WSDL地址动态创建客户端进行调用
     *
     * @param url    定位符，需要后缀?wsdl
     * @param method 调用方法名
     * @param params 参数
     * @return 接口返回值
     * @throws Exception 任何异常
     */
    public static Object[] call(String url, String method, Object... params) throws Exception {
        Client client = null;
        try {
            client = new Client(new URL(url));
            return client.invoke(method, params);
        } finally {
            if (client != null)
                client.close();
        }
    }

}
