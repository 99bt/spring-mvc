package com.yaowang.util.filesystem;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
/**
 * 文件操作接口
 * @author shenl
 * @version $Revision: 1.0 $, $Date: 2013-8-19 下午2:37:57 $
 */
public interface FileSystem {
    /**
     * 保存文件到文件服务器
     * @param src 支持3种类型(1:File 2:String 3:InputStream)
     * @param filePath
     */
    public Boolean saveFile(Object src, String filePath) throws Exception;
    /**
     * 文件服务器中从一个文件拷贝到另一个文件中
     * @param srcPath
     * @param destinationPath
     * @return
     */
    public Boolean copyFile(String srcPath, String destinationPath) throws Exception;
    /**
     * 将文件从文件服务器拷贝到本地
     * @return
     * @throws Exception 
     */
    public Boolean copyFileToLocal(String filePath, String localFilePath) throws Exception;
    /**
     * 删除文件服务器文件
     * @param filePath
     * @return
     */
    public Boolean deleteFile(String filePath) throws Exception;
    /**
     * 删除服务器端文件夹
     * @param path
     * @return
     * @throws Exception
     */
    public Boolean deleteDir(String filePath) throws Exception;
    /**
     * 获取文件服务器文件流
     * @param filePath
     * @return
     */
    public InputStream getFileAsStream(String filePath) throws Exception;
    /**
     * 读取文件服务器文本文件内容
     * @param filePath
     * @return
     * @throws Exception
     */
    public String getFileAsString(String filePath) throws Exception;
    /**
     * 获取此文件服务器文件夹下的所有文件
     * @param path
     */
    public String[] getFiles(String dirPath) throws Exception;
    /**
     * 获取此文件服务器文件夹下的所有文件名
     * @param dirPath
     * @return
     * @throws Exception
     */
    public String[] getFilesName(String dirPath) throws Exception;
    /**
     * 获取此文件服务器文件名
     * @param filePath
     * @return
     */
    public String getFileName(String filePath);
    
    /**
     * 判断文件服务器文件路径是否存在（仅能判断文件是否存在，不能判断文件夹是否存在）
     * @param filePath
     * @return
     */
    public Boolean fileExists(String filePath);
    /**
     * 判断文件服务器文件夹是否存在
     * @param dirPath
     * @return
     */
    public Boolean dirExists(String dirPath);
    /**
     * 获取文件大小
     * @param filePath
     * @return
     */
    public Long getFileLength(String filePath);
    /**
     * 获取最后修改时间
     * @param filePath
     * @return
     */
    public Date getLastModified(String filePath) throws Exception;
    /**
     * 创建文件夹
     * @param filePath
     * @return
     * @throws Exception 
     */
    public Boolean mkDir(String filePath) throws Exception;
    /**
     * 修改文件名称
     * @param srcPath
     * @param destinationPath
     * @return
     */
    public Boolean cutFile(String srcPath, String destinationPath)
    ;/**
     * 获取父文件夹
     * @param filePath
     * @return
     */
    public String getPatentFile(String filePath);
    /**
     * 获取文件
     * @param filePath
     * @return
     */
    public File getFile(String filePath);
}
