package com.example.versionningservice.dto.request;

public class RevertCommitRequest {

    public String commitToRevert;

    public String getCommitToRevert() {
        return commitToRevert;
    }

    public void setCommitToRevert(String commitToRevert) {
        this.commitToRevert = commitToRevert;
    }
}
