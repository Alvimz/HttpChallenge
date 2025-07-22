package com.alvim.repository;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.regex.Pattern;

public class RepositoryClassMethod {
    private final Class<?> clazz;
    private final Method method;
    private final Pattern pattern;

    public RepositoryClassMethod(Class<?> clazz, Method method, Pattern pattern) {
        this.clazz = clazz;
        this.method = method;
        this.pattern = pattern;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Method getMethod() {
        return method;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
