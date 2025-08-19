package com.alvim.boot;

import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FoldersVolume {

    public static void createFolders() {
        try{
            Path rootDir = Paths.get("volumeVSEC");
            Path input = rootDir.resolve("input");
            Path output = rootDir.resolve("output");

            Files.createDirectories(input);
            Files.createDirectories(output);

            System.out.println("Pastas criadas com sucesso > " +  rootDir.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static HostConfig createBind(){
        Path volumeVSEC = Paths.get("volumeVSEC").toAbsolutePath();
        String hostDir = volumeVSEC.toAbsolutePath().toString();
        System.out.println(hostDir);

        Volume containerVolume = new Volume("/task");
        Bind bind = new Bind(hostDir,containerVolume);
        return  HostConfig.newHostConfig().withBinds(bind);
    }


}
