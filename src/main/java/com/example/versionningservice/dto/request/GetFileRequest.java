package com.example.versionningservice.dto.request;

public class GetFileRequest {

        private String fileNameUrl;

    public GetFileRequest(String fileURL) {
        this.fileNameUrl =  fileURL;
    }

    public String getFileNameUrl() {
            return fileNameUrl;
        }

        public void setFileNameUrl(String fileNameUrl) {
            this.fileNameUrl = fileNameUrl;
        }
}
