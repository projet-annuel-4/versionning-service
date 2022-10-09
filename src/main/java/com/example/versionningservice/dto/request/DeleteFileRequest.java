package com.example.versionningservice.dto.request;

public class DeleteFileRequest {

    private String fileNameUrl;

    public void setFileNameUrl(String fileNameUrl) {
        this.fileNameUrl = fileNameUrl;
    }

    public String getFileNameUrl() {
        return fileNameUrl;
    }
}
