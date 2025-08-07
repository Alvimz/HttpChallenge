package com.alvim.endpoints;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.http.HttpMethodRequest;

import java.sql.Timestamp;
import java.time.Instant;

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
    public AnsibleCallback handleCallback(String status){
        AnsibleCallback dummy = new AnsibleCallback();
        dummy.setStatus(status);

        System.out.println("Recebido :" + status + " hora: " + Instant.now());

        return dummy;

    }
}
