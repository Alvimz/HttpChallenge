package com.alvim.repository;

import com.github.dockerjava.api.async.ResultCallback;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import com.github.dockerjava.api.model.Frame;


public class LoggerContainer implements ResultCallback<Frame> {
    private final Path logPath;
    private BufferedWriter writer;

    public LoggerContainer(String containerId){
        Path outputFolder = Paths.get("volumeVSEC").toAbsolutePath().resolve("output");
        this.logPath = outputFolder.resolve(containerId + ".log");
    }

    @Override
    public void onStart(Closeable stream) {
        try{
            writer = Files.newBufferedWriter(logPath, StandardOpenOption.CREATE,StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {
        try{
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onNext(Frame frame) {
        String text = new String(frame.getPayload(), StandardCharsets.UTF_8);

        try{
            writer.write("["+ frame.getStreamType() + "]" + text);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void close() throws IOException {

    }
}
