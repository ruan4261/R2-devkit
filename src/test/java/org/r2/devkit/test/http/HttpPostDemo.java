package org.r2.devkit.test.http;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.r2.devkit.http.CommonHttpAPI;
import org.r2.devkit.json.JSONObject;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpPostDemo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        sendComment();
    }

    /**
     * post json
     */
    private static void sendComment() throws IOException, URISyntaxException {
        PrintStream out = System.out;
        String uri = "";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "tom");

        // 使用try-with-resource节省代码调用，不使用这种方式则可以选择CommonHttpAPI.closeClient
        try (CloseableHttpClient client = CommonHttpAPI.BUILD_DEFAULT_CLIENT()) {
            HttpResponse response = CommonHttpAPI.doPostJson(client, uri, null, jsonObject.toJSONString());
            int code = CommonHttpAPI.getStatusCode(response);
            String mes = CommonHttpAPI.getReasonPhrase(response);
            long len = CommonHttpAPI.getContentLength(response);
            String html = CommonHttpAPI.getText(response, StandardCharsets.UTF_8);
            Header[] headers = CommonHttpAPI.getAllHeaders(response);

            out.println(code);
            out.println(mes);
            out.println(html);
            out.println(len);
            out.println(Arrays.toString(headers));
        }
    }

}
