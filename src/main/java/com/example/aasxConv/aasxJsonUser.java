package com.example.aasxConv;


import jakarta.persistence.*;

@Entity(name = "aasxjsonuser")
@Table(name = "aasxjsonuser")
public class aasxJsonUser {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String json_object;

    private String name;

    private Integer version;

    private String userID;

    public aasxJsonUser() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJson_object() {
        return json_object;
    }

    public void setJson_object(String json_object) {
        this.json_object = json_object;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public aasxJsonUser(Integer id, String json_object, String name, Integer version, String userID) {
        this.id = id;
        this.json_object = json_object;
        this.name = name;
        this.version = version;
        this.userID = userID;
    }
}
