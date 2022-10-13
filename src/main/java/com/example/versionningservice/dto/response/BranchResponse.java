package com.example.versionningservice.dto.response;

public class BranchResponse {

    private String name;

    public BranchResponse( String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
