package com.example.versionningservice.service;

import com.example.versionningservice.domain.model.ProcessResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommandExecutorService {
    @Value("${versioning.dir-path}")
    public  String activeDir;
    public CommandExecutorService() {
    }

    public ProcessResponse execute(String command) throws IOException {
        System.out.println("J'execute : " + command);
        Process process;
        process = Runtime.getRuntime().exec( command );
        return getProcessResult(process);
    }

    private ProcessResponse getProcessResult(Process process) throws IOException {
        ProcessResponse response = new ProcessResponse();
        Map<String, ArrayList<String>> result = new HashMap<>();
        response.outputs = getOutputs(process);
        response.errors = getErrors(process);
        response.exitCode = process.exitValue();

        return response;
    }

    public String createDir(String dirName, String path){
        String directoryPath = activeDir + "/" + dirName;
        System.out.println(directoryPath);
        java.io.File dir = new java.io.File(directoryPath);
        if (!dir.mkdir()) {
            throw new RuntimeException("unable to create directory");
        }
        return dir.getAbsolutePath();
    }

    private ArrayList<String> getOutputs(Process process) throws IOException {
        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        ArrayList<String> outputs = new ArrayList<>();
        String outputLine;
        while ( (outputLine = output.readLine()) != null){
            outputs.add(outputLine);
        }
        return outputs;
    }

    private ArrayList<String> getErrors(Process process) throws IOException {
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        ArrayList<String> errors = new ArrayList<>();
        String errorLine ;
        while ( (errorLine = error.readLine()) != null){
            System.out.println( "123" );
            System.out.println(errorLine + " ///// " );
            errors.add(errorLine);
        }

        return errors;
    }


}
