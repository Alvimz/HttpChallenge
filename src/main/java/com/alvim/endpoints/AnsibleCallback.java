package com.alvim.endpoints;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.http.HttpMethodRequest;

@Endpoint(path = "/ansible")
public class AnsibleCallback {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @EndPointMethod(path = "/callback", method = HttpMethodRequest.POST)
    public void handleCallback(String status){
        System.out.println("Recebido :" + status);

    }
}
