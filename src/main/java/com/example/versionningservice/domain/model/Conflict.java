package com.example.versionningservice.domain.model;

public class Conflict {

    private String filenameUrl;
    private ConflictType type;

    public Conflict() {
    }

    public Conflict(String filenameUrl, ConflictType type) {
        this.filenameUrl = filenameUrl;
        this.type = type;
    }

    public String getFilenameUrl() {
        return filenameUrl;
    }

    public void setFilenameUrl(String filenameUrl) {
        this.filenameUrl = filenameUrl;
    }

    public ConflictType getType() {
        return type;
    }

    public void setType(ConflictType type) {
        this.type = type;
    }
}
