package com.nerdydev.mtawrapper.web.dto;

public class WtcFolderNameDto {
    String folderName;
    public WtcFolderNameDto() {

    }

    public WtcFolderNameDto(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
