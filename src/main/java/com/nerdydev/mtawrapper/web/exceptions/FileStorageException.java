package com.nerdydev.mtawrapper.web.exceptions;



public class FileStorageException  extends RuntimeException{
    private String message;

    public FileStorageException(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return this.message;
    }
}
