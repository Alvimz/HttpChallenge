package com.alvim.boot;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.repository.HttpRepository;
import com.alvim.repository.RepositoryClassMethod;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

public class HandlerEndpoints {

    public static void start(){

        Reflections reflections = new Reflections("com.alvim.endpoints");
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Endpoint.class); // pega todos as classes que tem a annotation do tipo Endpoint!

        for (Class<?> clazz: classSet){
            Endpoint endpoint = clazz.getAnnotation(Endpoint.class); //"destrincha" a Annotation!
            String classPath = endpoint.path(); //salva o "path" aqui! >/customer <

            for(Method method: clazz.getMethods()){
                if(!method.isAnnotationPresent(EndPointMethod.class)) continue;

                EndPointMethod endPointMethod = method.getAnnotation(EndPointMethod.class);
                String pathMethod = endPointMethod.path(); // /all

                String methodRequest = endPointMethod.method().name(); // GET,POST
                String finalPath = methodRequest +":"+ classPath + pathMethod; // GET:/customer/all


                RepositoryClassMethod classesMethods = new RepositoryClassMethod(clazz,method);

                HttpRepository.getCurrency().addElement(finalPath,classesMethods);


                System.out.println(finalPath);

            }


        }
    }
}
