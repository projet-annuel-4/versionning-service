package com.example.versionningservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateFileRequest {

    private String fileNameUrl;

    public String getFileNameUrl() {
        return fileNameUrl;
    }

    public void setFileNameUrl(String fileNameUrl) {
        this.fileNameUrl = fileNameUrl;
    }
}
