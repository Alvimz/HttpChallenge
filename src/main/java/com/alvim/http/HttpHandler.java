package com.alvim.http;

import com.alvim.repository.HttpRepository;
import com.alvim.repository.RepositoryClassMethod;
import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;

public class HttpHandler implements com.sun.net.httpserver.HttpHandler {
    Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String routeKey = getHttpMethod(exchange) + ":" + path; //  "GET:/customer" -> Mesma coisa da primeira chave do Singleton!

        RepositoryClassMethod element = null;


        if(HttpRepository.getCurrency().isHere(routeKey)){
            element = HttpRepository.getCurrency().getElement(routeKey); //puxa a Classe e o méto_do caso sejam fixos!
        }else {
            String[] httpRequestPathCutted = cutPath(routeKey); //["POST","/customer/2123"]
            for(Map.Entry<String, RepositoryClassMethod> entry: HttpRepository.getCurrency().getRepository().entrySet() ){
                String routeRegistred = entry.getKey(); //rota registrada em cache > POST:/customer/{id}
                RepositoryClassMethod classAndMethod = entry.getValue(); // classe , método e pattern

                if(classAndMethod.getPattern()== null) continue; //deve conter pattern preenchido!

                String[] registredPathCutted = cutPath(routeRegistred); //["POST","/customer/{id}"]

                if(!registredPathCutted[0].equals(httpRequestPathCutted[0])) continue; //HttpRequest == HttpRequestCache

                Matcher matcher = classAndMethod.getPattern().matcher(httpRequestPathCutted[1]);
                if(matcher.matches()){
                    element = classAndMethod;
                    break;
                }

            }

        }

        if(element == null){
            exchange.sendResponseHeaders(405,-1);
        }

        Class<?> clazz =element.getClazz();
        Method method = element.getMethod();


        try{
            Constructor<?> ctr = clazz.getConstructor();
            Object object = ctr.newInstance();
            String bodyHttpRequest = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

            Object[] parametrs = null;

            if(!bodyHttpRequest.isBlank()){
                Class<?>[] paramType = method.getParameterTypes();
                Parameter[] parametersMethod = method.getParameters();
                JsonObject jsonObject  = JsonParser.parseString(bodyHttpRequest).getAsJsonObject();
                parametrs = new Object[paramType.length];

                for(int i=0; i<paramType.length; i++){
                    String paramName = parametersMethod[i].getName();
                    JsonElement paramJson = jsonObject.get(paramName);

                parametrs[i] = gson.fromJson(paramJson,paramType[i]);
                }
            }


            Object invoke = method.invoke(object,parametrs);
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

    private String[] cutPath(String path){
        return path.split(":", 2); //["POST" , "/customer/123"]
    }



}
