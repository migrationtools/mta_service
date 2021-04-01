package com.nerdydev.mtawrapper.web.dto;

import java.util.Date;

public class WtcFileNamesDto {
    private String folderName;
    private Date lastModified;

    public WtcFileNamesDto() {

    }


    public WtcFileNamesDto(String folderName, Date lastModified) {
        this.folderName = folderName;
        this.lastModified = lastModified;
    }


    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "WtcFileNamesDto{" +
                "folderName='" + folderName + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }
}
