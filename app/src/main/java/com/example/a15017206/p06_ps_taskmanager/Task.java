package com.example.a15017206.p06_ps_taskmanager;

import java.io.Serializable;

public class Task implements Serializable{
    private int id;
    private String name;
    private String description;
    private int remindInSeconds;

    public Task(int id, String name, String description, int remindInSeconds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.remindInSeconds = remindInSeconds;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRemindInSeconds() {
        return remindInSeconds;
    }
}
