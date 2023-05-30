package com.example.aasxConv;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "aasxjsonobjects")
@Table(name = "aasxjsonobjects")
public class aasxJsonObjects {

    @Id
    private String name;

    private String json_objects;

    public aasxJsonObjects() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJson_objects() {
        return json_objects;
    }

    public void setJson_objects(String json_objects) {
        this.json_objects = json_objects;
    }

    public aasxJsonObjects(String name, String json_objects) {
        this.name = name;
        this.json_objects = json_objects;
    }
}
