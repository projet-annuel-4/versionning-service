package com.example.versionningservice.dto.request;

public class DeleteBranchRequest {

    private String branchToDelete;

    public String getBranchToDelete() {
        return branchToDelete;
    }

    public void setBranchToDelete(String branchToDelete) {
        this.branchToDelete = branchToDelete;
    }
}
