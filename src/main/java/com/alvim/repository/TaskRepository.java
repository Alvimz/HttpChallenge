package com.alvim.repository;

import com.alvim.endpoints.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TaskRepository {
    private static Map<UUID, Task> taskRepositoryCache = new ConcurrentHashMap<>();
    //UUID - id, Task a task em si!
    //todo esse carinha tem que ser singleton!


    /*
    - Méto_do para adicionar a task no repositório
    -    "      "   remover   "          ".
    -
     */

    public static void addTaskRepository(UUID jobID,Task task){
        taskRepositoryCache.put(jobID,task);
    }

    public static void removeTaskRepository(UUID jobID){
        taskRepositoryCache.remove(jobID);
    }

    public static Task getTaskRepository(UUID jobID){
        if(isHere(jobID)){
            return taskRepositoryCache.get(jobID);
        }
        return  null;
    }

    private static boolean isHere(UUID jobID){
        return taskRepositoryCache.containsKey(jobID);
    }

}
