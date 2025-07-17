package com.alvim.http;

import com.alvim.repository.HttpRepository;
import com.alvim.repository.RepositoryClassMethod;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class HttpHandler implements com.sun.net.httpserver.HttpHandler {
    Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Dentro do HTTPHANDLER");
        String path = exchange.getRequestURI().getPath();
        String routeKey = getHttpMethod(exchange) + ":" + path; //  "GET:/customer" -> Mesma coisa da primeira chave do Singleton!

        if(!HttpRepository.getCurrency().isHere(routeKey)){
            exchange.sendResponseHeaders(405,-1);
            return;
            //retorna code 405 caso não exista o carinha em cache
        }
        RepositoryClassMethod element = HttpRepository.getCurrency().getElement(routeKey); //puxa o elemento perante a key!
        Class<?> clazz =element.getClazz();
        Method method = element.getMethod();

        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor ctr = null;
        for(int i = 0;i< constructors.length; i++){ // procura o construtor vazio!
            ctr = constructors[i];
            if(ctr.getGenericParameterTypes().length == 0){
                break;
            }
        }
        try{
            ctr.setAccessible(true);
            Object object = ctr.newInstance();
            Object invoke = method.invoke(object);
            String json = gson.toJson(invoke);
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200,bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public String getHttpMethod(HttpExchange exchange){
        return exchange.getRequestMethod();
    }
}
