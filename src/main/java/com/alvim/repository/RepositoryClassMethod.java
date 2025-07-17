package com.alvim.repository;

import java.lang.reflect.Method;

public class RepositoryClassMethod {
    private final Class<?> clazz;
    private final Method method;

    public RepositoryClassMethod(Class<?> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Method getMethod() {
        return method;
    }
}
