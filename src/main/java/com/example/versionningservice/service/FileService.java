package com.example.versionningservice.service;

import com.example.versionningservice.domain.model.ProcessResponse;
import com.example.versionningservice.dto.request.*;
import com.example.versionningservice.utils.GitCommand;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {

    private final CommandExecutorService commandExecutorService;
    @Value("${versioning.dir-path}")
    public String activeDir;
    public FileService(CommandExecutorService commandExecutorService) {
        this.commandExecutorService = commandExecutorService;
    }

    public byte[] getFile(GetFileRequest fileNameUrl, Long projectId) throws IOException {
        String filePath = activeDir + "/" + projectId + "/" + fileNameUrl.getFileNameUrl();
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));

        return bytes;
    }

    public void createFile(CreateFileRequest request, Long projectId) throws IOException {
        if ( isFileExist(request.getFileNameUrl(), projectId)){
            //todo custom exception
            throw new RuntimeException("");
                    }
        String fileNameUrl = request.getFileNameUrl();
        System.out.println("ici : " +  fileNameUrl);
        String filePath = activeDir + "/" + projectId + "/" + fileNameUrl;
        System.out.println(filePath);
        Path newFilePath = Paths.get(filePath);
        Files.createDirectories(newFilePath.toAbsolutePath());
        Files.createFile(newFilePath);
        commandExecutorService.execute(String.format(GitCommand.ADD, activeDir + "/" + projectId));
    }

    public void saveFile(String request, Long projectId, MultipartFile file) throws IOException {
        String filePath = activeDir + "/" + projectId + "/" + request;
        try {
            FileWriter writer = new FileWriter(filePath, false);
            writer.write(new String(file.getBytes(), StandardCharsets.UTF_8));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        commandExecutorService.execute(String.format(GitCommand.ADD, activeDir + "/" + projectId));
    }

    public void createDirectory(CreateDirRequest request, Long projectId){
        String directoryPath = activeDir + "/" + projectId + "/" + request.getDirNameUrl();
        java.io.File dir = new java.io.File(directoryPath);
        if (!dir.mkdir()) {
            throw new RuntimeException("unable to create directory");
        }
    }

    public void deleteFile(String fileNameUrl, Long projectId) throws IOException {
        String directoryPath = activeDir + "/" + projectId + "/" + fileNameUrl;
        File file = new File(directoryPath);
        if (!file.delete()) {
            System.out.println("File deleted successfully");
        }
        commandExecutorService.execute(String.format(GitCommand.ADD, activeDir + "/" + projectId));
    }

    public void deleteDirectory(DeleteDirRequest request, Long projectId) throws IOException {
        String directoryPath = activeDir + "/" + projectId + "/" + request.getDirNameUrl();
        File dir = new File(directoryPath);
        FileUtils.deleteDirectory(dir);
        if (!dir.delete()) {
            throw new RuntimeException("unable to create directory");
        }
    }

    private boolean isFileExist(String fileUrl, Long projectId) throws IOException {
        List<String> files = getAllFiles(projectId);
        for(String file : files){
            if( fileUrl.equals(file) ){
                return true;
            }
        }
        return false;
    }

    private boolean isDirectoryExist(){
        //TODO
        return false;
    }

    private List<String> getAllFiles(Long projectId) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.LIST_FILES, projectPath)
        );

        return processResponse.outputs;
    }

    public List<String> getTreeProject(Long projectId) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.LIST_FILES, projectPath)
        );
        System.out.println(processResponse);
        return processResponse.outputs;
    }

    public void addFile(AddFileRequest request, Long projectId) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.ADD_FILE, projectPath, request.getFileToAdd())
        );
        System.out.println("exit code : " + processResponse.exitCode);
        // if exit code = 1 => conflict
    }

    public void rmFile(RmFileRequest request, Long projectId) throws IOException {
        String projectPath = activeDir + "/" + projectId;
        ProcessResponse processResponse = commandExecutorService.execute(
                String.format(GitCommand.RM_FILE, projectPath, request.getFileToRm())
        );
        System.out.println("exit code : " + processResponse.exitCode);
        // if exit code = 1 => conflict
    }

}
