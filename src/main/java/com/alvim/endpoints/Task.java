package com.alvim.endpoints;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.http.HttpMethodRequest;
import com.alvim.repository.TaskRepository;

import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Endpoint(path = "/task")
public class Task {
    private UUID jobID = UUID.randomUUID();
    private String status = "CRIADA"; //todo colocar enum aqui! [ERROR, COMPLETED]
    private String objetive; //todo CRIACAO_VNF

    public Task(String objetive) {
        this.objetive = objetive;
    }

    @EndPointMethod(path = "/{jobID}", method = HttpMethodRequest.GET)
    public Task getTask(UUID jobID){
        return TaskRepository.getTaskRepository(jobID);
        //todo procura a task por id no repositório
    }



    public void setObjetive(String objetive) {
        this.objetive = objetive;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getJobID() {
        return jobID;
    }

    public String getStatus() {
        return status;
    }


    public String getObjetive() {
        return objetive;
    }

}
