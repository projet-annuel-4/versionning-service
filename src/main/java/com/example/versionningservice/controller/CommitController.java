package com.example.versionningservice.controller;

import com.example.versionningservice.domain.model.Commit;
import com.example.versionningservice.domain.model.Conflict;
import com.example.versionningservice.domain.model.Project;
import com.example.versionningservice.request.CommitRequest;
import com.example.versionningservice.request.CreateProjectRequest;
import com.example.versionningservice.request.RevertCommitRequest;
import com.example.versionningservice.service.CommitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project/{projectId}/commit")
public class CommitController {

    private final CommitService commitService;

    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }

    @PostMapping("/commit")
    public ResponseEntity<?> commit(@RequestBody CommitRequest request, @PathVariable Long projectId) throws IOException {
        commitService.commit(request.getCommitName(), projectId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllCommits")
    public List<Commit> getAllCommits(@PathVariable Long projectId) throws IOException {
        return commitService.getAllCommit(projectId);
    }

    @PostMapping("/revert")
    public ResponseEntity<List<Conflict>> revert(@PathVariable Long projectId, @RequestBody RevertCommitRequest request) throws IOException {
        return ResponseEntity.ok(commitService.revertCommit(request, projectId));
    }

    @PostMapping("/abortRevert")
    public ResponseEntity<?> abortRevert(@PathVariable Long projectId) throws IOException {
        commitService.revertCommitAbort(projectId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/continueRevert")
    public ResponseEntity<?> continueRevert(@PathVariable Long projectId, @RequestBody RevertCommitRequest request) throws IOException {
        commitService.revertCommitContinue(projectId, request);
        return ResponseEntity.ok().build();
    }


}
