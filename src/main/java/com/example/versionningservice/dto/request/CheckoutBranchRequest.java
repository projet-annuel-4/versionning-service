package com.example.versionningservice.dto.request;

public class CheckoutBranchRequest {

    private String targetBranch;

    public String getTargetBranch() {
        return targetBranch;
    }

    public void setTargetBranch(String targetBranch) {
        this.targetBranch = targetBranch;
    }
}
