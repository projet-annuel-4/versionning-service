package com.example.versionningservice.dto.request;

public class MergeBranchRequest {

    private String branchToMerge;

    public String getBranchToMerge() {
        return branchToMerge;
    }

    public void setBranchToMerge(String branchToMerge) {
        this.branchToMerge = branchToMerge;
    }
}
