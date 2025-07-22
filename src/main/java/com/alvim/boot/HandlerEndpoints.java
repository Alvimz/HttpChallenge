package com.alvim.boot;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.repository.HttpRepository;
import com.alvim.repository.RepositoryClassMethod;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.regex.Pattern;

public class HandlerEndpoints {

    public static void start(){

        Reflections reflections = new Reflections("com.alvim.endpoints");
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Endpoint.class); // pega todos as classes que tem a annotation do tipo Endpoint!

        for (Class<?> clazz: classSet){
            Endpoint endpoint = clazz.getAnnotation(Endpoint.class); //"destrincha" a Annotation!
            String classPath = endpoint.path(); //salva o "path" aqui! >/customer <

            for(Method method: clazz.getMethods()){
                if(!method.isAnnotationPresent(EndPointMethod.class)) continue;

                Pattern pattern = null;
                EndPointMethod endPointMethod = method.getAnnotation(EndPointMethod.class);
                String pathMethod = endPointMethod.path(); // /all

                String methodRequest = endPointMethod.method().name(); // GET,POST
                String finalPath = methodRequest +":"+ classPath + pathMethod; // GET:/customer/all
                String pathWithoutMethod = classPath + pathMethod;

                if(pathWithoutMethod.contains("{")){
                    pattern = HandlerEndpoints.regexToPattern(pathWithoutMethod);
                }
                RepositoryClassMethod classesMethods = new RepositoryClassMethod(clazz,method,pattern);

                HttpRepository.getCurrency().addElement(finalPath,classesMethods);


                System.out.println(finalPath);

            }


        }

    }
    private static Pattern regexToPattern(String link){
        String regex = link.replaceAll("\\{([^/]+)\\}", "(?<$1>[^/]+)");
        return Pattern.compile("^" + regex + "$");
    }
}
