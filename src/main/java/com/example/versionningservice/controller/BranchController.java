package com.example.versionningservice.controller;


import com.example.versionningservice.domain.model.Conflict;
import com.example.versionningservice.dto.request.CheckoutBranchRequest;
import com.example.versionningservice.dto.request.CreateBranchRequest;
import com.example.versionningservice.dto.request.MergeBranchRequest;
import com.example.versionningservice.dto.response.BranchResponse;
import com.example.versionningservice.service.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project/{projectId}/branch")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping("/createBranch")
    public ResponseEntity<?> createBranch(@RequestBody CreateBranchRequest branchName, @PathVariable("projectId") Long projectId) throws IOException {
        this.branchService.createBranch(branchName, projectId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getActualBranch")
    public ResponseEntity<BranchResponse> getActualBranch(@PathVariable("projectId") Long projectId) throws IOException {
        String actualBranch = this.branchService.getActualBranch(projectId);
        return ResponseEntity.ok(new BranchResponse(actualBranch));
    }

    @PostMapping("/checkout")
    public ResponseEntity<List<String>> checkoutBranch(@RequestBody CheckoutBranchRequest branchName, @PathVariable("projectId") Long projectId) throws IOException {
        return ResponseEntity.ok(this.branchService.checkoutBranch(branchName, projectId));
    }

    @GetMapping("/getAllBranches")
    public ResponseEntity<List<String>> getAllBranches(@PathVariable("projectId") Long projectId) throws IOException {
        // if return size = 0 => only master
        return ResponseEntity.ok(this.branchService.getAllBranches(projectId));
    }

    @PostMapping("/merge")
    public ResponseEntity<List<Conflict>> mergeBranch(@RequestBody MergeBranchRequest request, @PathVariable("projectId") Long projectId) throws IOException {
        return ResponseEntity.ok(this.branchService.mergeBranch(request, projectId));
    }

    @PostMapping("/abortMerge")
    public ResponseEntity<?> abortRevert(@PathVariable Long projectId) throws IOException {
        this.branchService.mergeBranchAbort(projectId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/continueMerge")
    public ResponseEntity<?> continueRevert(@PathVariable Long projectId, @RequestBody MergeBranchRequest request) throws IOException {
        this.branchService.mergeBranchContinue(request, projectId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBranch(@RequestParam String branchName, @PathVariable("projectId") Long projectId) throws IOException {
        this.branchService.deleteBranch(branchName, projectId);
        return ResponseEntity.ok().build();
    }

}
