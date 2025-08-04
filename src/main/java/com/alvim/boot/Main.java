package com.alvim.boot;

import com.alvim.http.HttpListening;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        HttpListening httpListening = new HttpListening();
        HandlerEndpoints.start();
        TemporaryFolders.createFolders();

        try{
            httpListening.start(8989);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(httpListening::close));
        //Runtime.getRuntime().addShutdownHook(new Thread(TemporaryFolders::deleteFolders));

    }
}
