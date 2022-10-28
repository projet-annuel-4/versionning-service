package com.example.versionningservice.domain.model;

import java.time.LocalDateTime;

public class Commit {

    private String code;
    private String name;
    private String date;

    public Commit(String code, String name,String date) {
        this.code = code;
        this.name = name;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
