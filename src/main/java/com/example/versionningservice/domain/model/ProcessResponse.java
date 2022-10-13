package com.example.versionningservice.domain.model;

import java.util.List;

public class ProcessResponse {

    public List<String> outputs;
    public List<String> errors;
    public int exitCode;

    public ProcessResponse(List<String> outputs, List<String> errors, int exitCode) {
        this.outputs = outputs;
        this.errors = errors;
        this.exitCode = exitCode;
    }

    public ProcessResponse() {
    }

    public List<String> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<String> outputs) {
        this.outputs = outputs;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    @Override
    public String toString() {
        return "ProcessResponse{" +
                "outputs=" + outputs +
                ", errors=" + errors +
                ", exitCode=" + exitCode +
                '}';
    }
}
