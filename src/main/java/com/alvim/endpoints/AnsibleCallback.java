package com.alvim.endpoints;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.http.HttpMethodRequest;
import com.alvim.repository.TaskRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

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
    public void handleCallback(UUID job_id, String status){
        Task taskRepository = TaskRepository.getTaskRepository(job_id);
        taskRepository.setFinishedAt(Instant.now());
        taskRepository.setStatus(status);



        System.out.println("Recebido :" + status + " hora: " + Instant.now());



    }
}
