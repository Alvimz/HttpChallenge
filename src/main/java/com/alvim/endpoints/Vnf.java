package com.alvim.endpoints;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.boot.TemporaryFolders;
import com.alvim.http.HttpMethodRequest;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.CreateVolumeResponse;
import com.github.dockerjava.api.command.InspectVolumeResponse;
import com.github.dockerjava.api.command.ListVolumesResponse;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DockerClientBuilder;

import java.awt.*;
import java.util.List;
import java.util.UUID;

@Endpoint(path = "/vnf")
public class Vnf {
    private UUID id;
    private String name;
    DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://192.168.2.93:2375").build();

    @EndPointMethod(path = "/create", method = HttpMethodRequest.POST)
    public String createVnf(){
        //todo pensar como usar o dinamismo das portas!
        CreateVolumeResponse volumeResponse = dockerClient.createVolumeCmd().withName("vsec").exec();
        //Criar o volume!
        HostConfig hostConfig = TemporaryFolders.createBind();
        CreateContainerResponse container = dockerClient.createContainerCmd("vsec:0.1")
                .withHostConfig(hostConfig).withCmd("sh","-c","echo preparado").exec();

        String containerId = container.getId();
        return  containerId;
        /*

        - Criar a pasta input/output! :check:
        - Colocar os arquivos internamente! :check:
        TODO: GITHUB DOCUMENTAÇÃO : - Passar -e = "creation_vnf".
        - Passar o arquivo para o volume

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
