package com.alvim.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpRepository {
    private static final HttpRepository CURRENCY = new HttpRepository();
    private final Map<String, RepositoryClassMethod> classesMap = new ConcurrentHashMap<>(); //thread safe!

    public static HttpRepository getCurrency(){
        return CURRENCY;
    }

    private HttpRepository() { }

    public void addElement(String valueString, RepositoryClassMethod httpRepositoryClasses){
        classesMap.put(valueString,httpRepositoryClasses);
    }

    public boolean isHere(String keyValue){
        return classesMap.containsKey(keyValue);
    }

    public RepositoryClassMethod getElement(String keyValue){
        return classesMap.get(keyValue);
    }


}
