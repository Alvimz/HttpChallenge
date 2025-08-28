package com.alvim.endpoints;

import com.alvim.annotations.Endpoint;
import com.alvim.boot.FoldersVolume;
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



    //@EndPointMethod(path = "/create", method = HttpMethodRequest.POST)
    public void createVnf(){
//        HostConfig hostConfig = FoldersVolume.createBind();
//        CreateContainerResponse container = dockerClient.createContainerCmd("vsec:0.11") //todo lembrar da imagem ser essa
//                .withHostConfig(hostConfig).withEnv("TASK=creation_vnf", "JOB_ID="+jobID.toString()).exec();
//        //o env TASK <- Dita qual vai ser a ação!
//        //String containerId = container.getId(); //pega o id do container!
//        dockerClient.startContainerCmd(containerId).exec();
//        System.out.println("Container iniciado: " + containerId);


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
