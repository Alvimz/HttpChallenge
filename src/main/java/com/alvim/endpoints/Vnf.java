package com.alvim.endpoints;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.boot.FoldersVolume;
import com.alvim.http.HttpMethodRequest;
import com.alvim.repository.RepositoryClassMethod;
import com.alvim.repository.TaskRepository;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;

import com.github.dockerjava.api.model.HostConfig;

import com.github.dockerjava.core.DockerClientBuilder;

import java.util.UUID;

@Endpoint(path = "/vnf")
public class Vnf {
    private UUID id;
    private String name;
    DockerClient dockerClient = DockerClientBuilder.getInstance("unix:///var/run/docker.sock").build();



    @EndPointMethod(path = "/create", method = HttpMethodRequest.POST)
    public Task createVnf(){

        Task task = new Task(); //cria a task
        task.setObjetive("VNF CREATION");

        UUID jobID = task.getJobID();
        TaskRepository.addTaskRepository(jobID,task); //guarda em cache!


        HostConfig hostConfig = FoldersVolume.createBind();
        CreateContainerResponse container = dockerClient.createContainerCmd("vsec:0.11") //todo ficar atento aqui
                .withHostConfig(hostConfig).withEnv("TASK=creation_vnf", "JOB_ID="+jobID.toString()).exec();
        //o env TASK <- Dita qual vai ser a ação!
        String containerId = container.getId(); //pega o id do container!
        dockerClient.startContainerCmd(containerId).exec();
        System.out.println("Container iniciado: " + containerId);

        return task;
        /*


        TODO: criação da imagem do Dockerfile! < deixar para criar quando estiver completinho!
        TODO:

         */
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
