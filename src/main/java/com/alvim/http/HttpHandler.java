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
        String[] pathValues = null;
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
                    //String group = matcher.group(1);
                    element = classAndMethod;


                    pathValues = new String[matcher.groupCount()];
                    for (int i = 1; i <= matcher.groupCount(); i++) {
                        System.out.println(matcher.group(i));
                        pathValues[i-1] = matcher.group(i);
                    }
                    break;
                }
            }
        }

        if(element == null){
            exchange.sendResponseHeaders(405,-1);
            return;
        }

        Class<?> clazz =element.getClazz();
        Method method = element.getMethod();

        try{
            Constructor<?> ctr = clazz.getConstructor();
            Object object = ctr.newInstance();
            String bodyHttpRequest = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Class<?>[] paramType = method.getParameterTypes();
            Parameter[] methodParams = method.getParameters();
            Object[] parameters = new Object[paramType.length];

            JsonObject bodyJson = bodyHttpRequest.isBlank() ? new JsonObject() : JsonParser.parseString(bodyHttpRequest).getAsJsonObject();
            JsonObject JsonMap = new JsonObject();

            if (pathValues != null) { //caso contenha path dynamic!

                for (int i = 0; i < pathValues.length; i++) {
                    String name = methodParams[i].getName();
                    JsonMap.add(name, new JsonPrimitive(pathValues[i]));
                }
            }


            for (Map.Entry<String, JsonElement> entry : bodyJson.entrySet()) { //união do body do json com o do path
                JsonMap.add(entry.getKey(), entry.getValue());
            }


            for (int i = 0; i < paramType.length; i++) {
                String name = methodParams[i].getName();
                JsonElement elem = JsonMap.get(name);
                if (elem == null) { //caso a key não bata para a desarilização!
                    exchange.sendResponseHeaders(422, -1);
                    return;
                }
                parameters[i] = gson.fromJson(elem, paramType[i]); //desarilização
            }

            Object result = method.invoke(object, parameters);
            sendJsonResponse(exchange, result);


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