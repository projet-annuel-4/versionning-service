package com.example.versionningservice.service;

import com.example.versionningservice.domain.model.Commit;
import com.example.versionningservice.domain.model.Conflict;
import com.example.versionningservice.domain.model.ProcessResponse;
import com.example.versionningservice.dto.request.RevertCommitRequest;
import com.example.versionningservice.utils.GitCommand;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommitService {
    private static final Type REVIEW_TYPE = new TypeToken<List<Commit>>() {
    }.getType();
    private final CommandExecutorService commandExecutorService;
    private final BranchService branchService;
    private final FileService fileService;
    private final ConflictService conflictService;
    @Value("${versioning.dir-path}")
    public String activeDir;
    public CommitService(CommandExecutorService commandExecutorService, @Lazy BranchService branchService, FileService fileService, ConflictService conflictService) {
        this.commandExecutorService = commandExecutorService;
        this.branchService = branchService;
        this.fileService = fileService;
        this.conflictService = conflictService;
    }

    public void commit(String commitName, Long projectId) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.COMMIT, projectPath, commitName)
        );
        System.out.println("exit code commit : " + processResponse.exitCode);
    }

    public List<Commit> getAllCommit(Long projectId) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String projectPath = activeDir + "/" + projectId;
         commandExecutorService.execute(
                 String.format(GitCommand.LIST_COMMIT, projectPath ,uuid)
        );
         var path = String.format("/tmp/%s.json", uuid);
        System.out.println("uuid: " + uuid);
        return parseObjectCommitFile(path);
    }

    private List<Commit> parseObjectCommitFile(String filePath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filePath));
        reader.setLenient(true);
        List<Commit> commitList = gson.fromJson(reader, REVIEW_TYPE);
        System.out.println("commitList: " + commitList);
        return commitList.stream().filter(commit -> commit != null && !commit.getCode().isEmpty()).collect(Collectors.toList());
    }

    public List<Conflict> revertCommit(RevertCommitRequest request, Long projectId) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.REVERT_COMMIT, projectPath, request.getCommitToRevert())
        );
        System.out.println("exit code revert commit : " + processResponse.exitCode);
        List<Conflict> conflicts = new ArrayList<>();
        if ( processResponse.exitCode == 0 ){
            commit("Revert_commit_" + request.commitToRevert, projectId);
            return conflicts;
        }
        processResponse = commandExecutorService.execute(
                String.format(GitCommand.STATUS, projectPath)
        );
        return conflictService.conflictsStringToModel(conflictService.getConflict(processResponse.outputs));
    }

    public List<Conflict> revertCommitContinue(Long projectId, RevertCommitRequest request) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.STATUS, projectPath)
        );
        System.out.println("exit code : " + processResponse.exitCode);
        // if exit code = 1 => conflict
        List<Conflict> conflicts = conflictService.conflictsStringToModel(conflictService.getConflict(processResponse.outputs));
        if( conflicts.size() == 0){
            commit("Revert commit " + request.commitToRevert, projectId);
            return conflicts;
        }
        return conflicts;
    }

    public void revertCommitAbort(Long projectId) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.REVERT_COMMIT_ABORT, projectPath)
        );
        System.out.println("exit code : " + processResponse.exitCode);
        // if exit code = 1 => conflict
    }

/*    private List<Commit> commitParser(List<String> commitToParse){
        List<Commit> commits = new ArrayList<>();
        for(String toParse : commitToParse){
            String[] split = toParse.trim().split("|", 3);
            Timestamp timestamp =  new Timestamp(Long.valueOf(split[2]));
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            Commit newCommit = new Commit(split[0], split[1], localDateTime);
            commits.add(newCommit);
        }
        return commits;
    }*/


}
