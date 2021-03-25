package com.nerdydev.mtawrapper.web.dto;

public class CheckIndexFileDto {

    private String fileName;


    public CheckIndexFileDto(String fileName) {
        this.fileName = fileName;
    }

    public CheckIndexFileDto() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "CheckIndexFileDto{" +
                "fileName='" + fileName + '\'' +
                '}';
    }
}
