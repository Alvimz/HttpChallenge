package com.alvim.endpoints;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.http.HttpMethodRequest;

@Endpoint(path = "/ansible")
public class AnsibleCallback {

    @EndPointMethod(path = "/callback", method = HttpMethodRequest.POST)
    public void handleCallback(String body){
        System.out.println("Recebido :" + body);

    }
}
