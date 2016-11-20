package com.project.gta.demo;

/**
 * Created by TInf on 19.11.2016.
 */

public class Plant {
    private int id;
    private String name;

    //Constructor

    public Plant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Setter, getter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
