package com.example.versionningservice.service;

import com.example.versionningservice.domain.model.Conflict;
import com.example.versionningservice.domain.model.ProcessResponse;
import com.example.versionningservice.dto.request.CheckoutBranchRequest;
import com.example.versionningservice.dto.request.CreateBranchRequest;
import com.example.versionningservice.dto.request.DeleteBranchRequest;
import com.example.versionningservice.dto.request.MergeBranchRequest;
import com.example.versionningservice.utils.GitCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BranchService {

    private final CommandExecutorService commandExecutorService;
    private final FileService fileService;
    private final CommitService commitService;
    private final ConflictService conflictService;
    @Value("${versioning.dir-path}")
    public String activeDir;
    public BranchService(CommandExecutorService commandExecutorService, FileService fileService, CommitService commitService, ConflictService conflictService) {
        this.commandExecutorService = commandExecutorService;
        this.fileService = fileService;
        this.commitService = commitService;
        this.conflictService = conflictService;
    }

    public void createBranch(CreateBranchRequest branchName, Long projectId) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(String.format(GitCommand.CREATE_BRANCH, projectPath ,branchName.getBranchName()));
        System.out.println("exit code : " + processResponse.exitCode);
        //TODO
    }

    public String getActualBranch(Long projectId) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.ACTUAL_BRANCH, projectPath)
        );
        return processResponse.outputs.get(0);
    }

    public List<String> getAllBranches(Long projectId) throws IOException {
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.LIST_BRANCHES, activeDir + "/" + projectId)
        );
        for( int i = 0; i < processResponse.outputs.size(); i++){
            processResponse.outputs.set(i, processResponse.outputs.get(i).replace("*", "").trim());
        }
        if (processResponse.outputs.size() == 0){
            processResponse.outputs.add("master");
        }
        return processResponse.outputs;
    }

    public void checkoutBranch(CheckoutBranchRequest branchName, Long projectId) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.CHECKOUT, projectPath, branchName.getTargetBranch())
        );
    }

    public List<Conflict> mergeBranch(MergeBranchRequest request, Long projectId) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.MERGE, projectPath, request.getBranchToMerge())
        );
        System.out.println("exit code merge : " + processResponse.exitCode);
        // if exit code = 1 || 128 => conflict
        List<Conflict> conflicts = new ArrayList<>();
        if ( processResponse.exitCode == 0 ){
            commitService.commit("Merge branche " + request.getBranchToMerge(), projectId);
            return conflicts;
        }
        processResponse = commandExecutorService.execute(
                String.format(GitCommand.STATUS, projectPath)
        );
        return conflictService.conflictsStringToModel(conflictService.getConflict(processResponse.outputs));
    }

    public List<Conflict> mergeBranchContinue(MergeBranchRequest branchToMerge, Long projectId) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.STATUS, projectPath)
        );
        System.out.println("exit code status : " + processResponse.exitCode);
        // if exit code = 1 => conflict

        List<Conflict> conflicts = conflictService.conflictsStringToModel(conflictService.getConflict(processResponse.outputs));
        if( conflicts.size() == 0){
            commitService.commit("Merge branche " + branchToMerge.getBranchToMerge(), projectId);
            return conflicts;
        }
        return conflicts;
    }

    public void mergeBranchAbort(Long projectId) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.MERGE_ABORT, projectPath)
        );
        System.out.println("exit code merge abort  : " + processResponse.exitCode);
    }

    public void deleteBranch(DeleteBranchRequest branchToDelete, Long projectId) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.DELETE_BRANCH, projectPath, branchToDelete.getBranchToDelete())
        );
    }
}
