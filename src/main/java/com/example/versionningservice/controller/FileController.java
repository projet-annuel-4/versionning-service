package com.example.versionningservice.controller;

import com.example.versionningservice.dto.request.*;
import com.example.versionningservice.service.FileService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/project/{projectId}/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/createFile")
    public ResponseEntity<?> createFile(@PathVariable Long projectId, @RequestBody CreateFileRequest request) throws IOException {
        this.fileService.createFile(request, projectId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getFileData")
    public ResponseEntity<byte[]> getFile(@PathVariable Long projectId, @RequestParam("fileNameUrl") String fileNameUrl) throws ChangeSetPersister.NotFoundException, IOException, URISyntaxException, InterruptedException {
        return ResponseEntity.ok(fileService.getFile(new GetFileRequest(fileNameUrl), projectId));
    }

    @GetMapping("/getTree")
    public ResponseEntity<List<String>> getTreeProject(@PathVariable Long projectId) throws IOException {
        return ResponseEntity.ok(fileService.getTreeProject(projectId));
    }

    @PostMapping("/saveFile")
    public ResponseEntity<?> saveFile(@PathVariable Long projectId, @RequestParam("fileNameUrl") String fileNameUrl, @RequestParam("file") MultipartFile file) throws IOException {
        this.fileService.saveFile(fileNameUrl, projectId, file);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/createDirectory")
    public ResponseEntity<?> createDirectory(@PathVariable Long projectId, @RequestBody CreateDirRequest dirNameUrl){
        this.fileService.createDirectory(dirNameUrl, projectId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<?> deleteFile(@PathVariable Long projectId, @RequestBody DeleteFileRequest fileNameUrl) throws IOException {
        this.fileService.deleteFile(fileNameUrl, projectId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteDirectory")
    public ResponseEntity<?> deleteDirectory(@PathVariable Long projectId, @RequestBody DeleteDirRequest dirNameUrl) throws IOException {
        this.fileService.deleteDirectory(dirNameUrl, projectId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/gitAddFile")
    public ResponseEntity<?> gitAddFile(@RequestBody AddFileRequest request, @PathVariable Long projectId) throws IOException {
        this.fileService.addFile(request, projectId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/gitRmFile")
    public ResponseEntity<?> gitRmFile(@RequestBody RmFileRequest request, @PathVariable Long projectId) throws IOException {
        this.fileService.rmFile(request, projectId);
        return ResponseEntity.ok().build();
    }

}
