package com.it.model;


import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String world;

    public User(String name, String world) {
        this.name = name;
        this.world = world;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", world='" + world + '\'' +
                '}';
    }
}
