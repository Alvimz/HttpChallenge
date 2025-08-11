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

        Task task = new Task("VNF CREATION");

        UUID jobID = task.getJobID();
        TaskRepository.addTaskRepository(jobID,task);


        HostConfig hostConfig = FoldersVolume.createBind();
        CreateContainerResponse container = dockerClient.createContainerCmd("vsec:0.11") //todo ficar atento aqui
                .withHostConfig(hostConfig).withEnv("TASK=creation_vnf", "JOB_ID="+jobID.toString()).exec();
        //o env TASK <- Dita qual vai ser a ação! Adicionar no ansible e no Java!
        String containerId = container.getId(); //pega o id do container!
        dockerClient.startContainerCmd(containerId).exec();



        System.out.println("Container iniciado: " + containerId);

        return task;
        /*


        TODO: criação da imagem do DOckerfile!
        TODO: adicionar keep alive and ping na vnf. Ansible uri!

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
