package com.alvim.annotations;


import com.alvim.http.HttpMethodRequest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EndPointMethod {
    String path();
    HttpMethodRequest method();
}
