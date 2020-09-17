package com.hong.entity;

/**
 * @Author LJH
 * @CreateTime 2020/07/14 10:09
 */
public class FileObject {
    private String fileName;
    private long fileSize;
    private String fileLastModTime;
    private String absolutePath;

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileLastModTime() {
        return fileLastModTime;
    }

    public void setFileLastModTime(String fileLastModTime) {
        this.fileLastModTime = fileLastModTime;
    }
}
