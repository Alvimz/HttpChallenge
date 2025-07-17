package com.alvim.http;

import com.alvim.repository.HttpRepository;
import com.alvim.repository.RepositoryClassMethod;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

public class HandlerAllGet implements HttpHandler {
    Gson gson = new Gson();
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(!exchange.getRequestMethod().equals(HttpMethodRequest.GET.name())){
            exchange.sendResponseHeaders(405,-1);
        }
        String query = exchange.getRequestURI().getQuery();
        if(query.isEmpty()){
            exchange.sendResponseHeaders(405,-1);

        }
        String typeValue = query.startsWith("type=") ? query.substring(5) : null ;

        String fullPath = "GET:/" + typeValue+ "/all";

        if(!HttpRepository.getCurrency().isHere(fullPath)){
            exchange.sendResponseHeaders(406,-1);

        }

        RepositoryClassMethod element = HttpRepository.getCurrency().getElement(fullPath);


        try{
            Object invoke = element.getMethod().invoke(null);
            String json = gson.toJson(invoke);
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200,bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }
}
