package com.example.versionningservice.domain.model;

import java.time.LocalDateTime;

public class Commit {

    private String code;
    private String name;
    private LocalDateTime date;

    public Commit(String code, String name,LocalDateTime date) {
        this.code = code;
        this.name = name;
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
}
