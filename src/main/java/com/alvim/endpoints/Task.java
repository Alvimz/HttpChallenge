package com.alvim.endpoints;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.http.HttpMethodRequest;
import com.alvim.repository.TaskRepository;

import java.security.Timestamp;
import java.util.UUID;

@Endpoint(path = "/task")
public class Task {
    private UUID jobID = UUID.randomUUID();
    private String status; //todo colocar enum aqui! [ERROR, COMPLETED]
    private Timestamp startedAt;
    private Timestamp finishedAt;
    private String objetive; //todo CRIACAO_VNF


    @EndPointMethod(path = "/{jobID}", method = HttpMethodRequest.GET)
    public Task getTask(UUID jobID){
        return TaskRepository.getTaskRepository(jobID);
        //todo procura a task por id no repositório
    }

    public void setFinishedAt(Timestamp finishedAt) {
        this.finishedAt = finishedAt;
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

    public Timestamp getStartedAt() {
        return startedAt;
    }

    public Timestamp getFinishedAt() {
        return finishedAt;
    }

    public String getObjetive() {
        return objetive;
    }
}
