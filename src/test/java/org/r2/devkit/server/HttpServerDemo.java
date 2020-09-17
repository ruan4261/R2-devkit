/*
 * MIT License
 *
 * Copyright (c) 2019-present,  ruan4261
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.r2.devkit.server;

import com.sun.net.httpserver.*;
import org.r2.devkit.io.IOAPI;
import org.r2.devkit.test.TestCore;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpServerDemo extends TestCore implements HttpHandler {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new HttpServerDemo());
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        print("LocalAddress", httpExchange.getLocalAddress().toString());
        print("Protocol", httpExchange.getProtocol());
        print("RemoteAddress", httpExchange.getRemoteAddress().toString());
        print("RequestBody", new String(IOAPI.getInputStreamData(httpExchange.getRequestBody())));
        printHeaders(httpExchange.getRequestHeaders());
        print("RequestMethod", httpExchange.getRequestMethod());
        print("RequestURI", httpExchange.getRequestURI().toString());
        HttpContext context = httpExchange.getHttpContext();
        print("ContextAttr", context.getAttributes().toString());
        print("ContextPath", context.getPath());

        Headers response = httpExchange.getResponseHeaders();
        response.add("Content-Type:", "text/html;charset=utf-8");
        httpExchange.sendResponseHeaders(200, 18);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write("请求结束了。".getBytes());
        outputStream.flush();
        outputStream.close();
        line(3);
    }

    void printHeaders(Headers headers) {
        headers.forEach(TestCore::errPrint);
    }

}
