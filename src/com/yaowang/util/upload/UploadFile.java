package com.yaowang.util.upload;

import java.io.File;

/*
 * 上传文件adapter类
 * 
 * @author 
 * @since 1.0
 * @version $Id: 
 */
public class UploadFile {
    private String fileName;
    private File tempFile;
    private String contentType;
    private String fieldName;

    public UploadFile(File tempFile) {
        this.tempFile = tempFile;
        this.fileName = tempFile.getName();
    }

    public UploadFile(File tempFile, String contentType) {
        this.tempFile = tempFile;
        this.fileName = tempFile.getName();
        this.contentType = contentType;
    }

    public UploadFile(String fileName, File tempFile, String contentType, String fieldName) {
        this.fileName = fileName;
        this.tempFile = tempFile;
        this.contentType = (contentType == null) ? "" : contentType;
        this.fieldName = fieldName;
    }

    /**
     * @return Returns the fieldName.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * 文件大小
     * 
     * @return
     */
    public long getFileSize() {
        return tempFile.length();
    }

    /**
     * 文件类型
     * 
     * @return Returns the contentType.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * 实际保存到硬盘上的文件
     * 
     * @return Returns the file.
     */
    public File getFile() {
        return tempFile;
    }

    /**
     * 实际的文件名 文件名
     * 
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return getFileName();
    }
}
