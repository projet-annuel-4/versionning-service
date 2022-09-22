package com.example.versionningservice.response;

public class CommitResponse {

    private String sha;
    private String name;

    public CommitResponse(String sha, String name) {
        this.sha = sha;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }
}
