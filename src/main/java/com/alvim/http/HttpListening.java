package com.alvim.http;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HttpListening {
    public HttpServer httpServer;

    public HttpServer start(int port) throws IOException {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        httpServer = HttpServer.create(new InetSocketAddress(port),0);
        httpServer.createContext("/",new HttpHandler());
        httpServer.createContext("/customer/all", new HandlerCustomerGetAll());
        httpServer.createContext("/all", new HandlerAllGet());
        httpServer.setExecutor(threadPoolExecutor);
        httpServer.start();
        return  httpServer;
    }

    public void close(){
        System.out.println("Desligando!");
        httpServer.stop(1);
    }
}
