package com.alvim.boot;

import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TemporaryFolders {

    public static void createFolders() {
        try{
            Path rootDir = Paths.get("volumeVSEC");
            Path input = rootDir.resolve("input");
            Path output = rootDir.resolve("output");

            Files.createDirectories(input);
            Files.createDirectories(output);

            System.out.println("Pastas criadas com sucesso!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteFolders(){
        //todo
    }

    public static HostConfig createBind(){
        Path volumeVSEC = Paths.get("volumeVSEC").toAbsolutePath();
        String hostDir = volumeVSEC.toAbsolutePath().toString();
        Volume containerVolume = new Volume("/vsec");
        Bind bind = new Bind(hostDir,containerVolume);
        return  HostConfig.newHostConfig().withBinds(bind);
    }


}
