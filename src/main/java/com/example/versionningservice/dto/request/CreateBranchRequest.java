package com.example.versionningservice.dto.request;

public class CreateBranchRequest {

    private String branchName;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
