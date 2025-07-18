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
        String path = exchange.getRequestURI().getPath();
        String routeKey = getHttpMethod(exchange) + ":" + path; //  "GET:/customer" -> Mesma coisa da primeira chave do Singleton!

        if(!HttpRepository.getCurrency().isHere(routeKey)){
            //verifica se existe a rota em cache!
            //se sim, ele apenas continua!
            exchange.sendResponseHeaders(405,-1);
            return;

        }
        RepositoryClassMethod element = HttpRepository.getCurrency().getElement(routeKey); //puxa o elemento perante a key!
        Class<?> clazz =element.getClazz();
        Method method = element.getMethod();

        if(exchange.getRequestMethod().equals(HttpMethodRequest.POST.name())){
            String payload = getValorGet(exchange);
            /*
                - Extrair o body da requisição!
                - Desarialização em uma classe!

             */

        }

        try{
            Constructor<?> ctr = clazz.getConstructor();
            Object object = ctr.newInstance();
            Object invoke = method.invoke(object);
            sendJsonResponse(exchange,invoke);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public String getHttpMethod(HttpExchange exchange){
        return exchange.getRequestMethod();
    }

    private void sendJsonResponse(HttpExchange exchange, Object data) throws IOException {
        String json = gson.toJson(data);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    public String getValorGet(HttpExchange httpExchange){
        return httpExchange.getRequestURI().getQuery().split("=")[1];
    }




}
