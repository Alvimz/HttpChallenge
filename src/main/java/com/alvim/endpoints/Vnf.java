package com.alvim.endpoints;

import com.alvim.annotations.EndPointMethod;
import com.alvim.annotations.Endpoint;
import com.alvim.http.HttpMethodRequest;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateVolumeResponse;
import com.github.dockerjava.api.command.InspectVolumeResponse;
import com.github.dockerjava.api.command.ListVolumesResponse;
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
    public void createVnf(){
        //todo pensar como usar o dinamismo das portas!
        CreateVolumeResponse volumeResponse = dockerClient.createVolumeCmd().withName("vsec").exec();
        //Criar o volume!


        /*

        - Criar a pasta input/output!
        - Colocar os arquivos internamente!
        - Passar -e = "creation_vnf".

        - Docker local para cada DC.
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
