package com.yaowang.util.filesystem.util;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import com.yaowang.util.filesystem.FileSystem;

/**
 * 文件读写工具类
 * 
 * @author
 * @date 2013-8-17
 * @version V 1.0
 */
public class FileSystemUtil {
    private static FileSystem FILE_SYSTEM = null;
    public static final String TEMP_DIR = "temp/";

    /**
     * 初始化文件服务器
     * 
     * @param system
     */
    public static void setSystem(FileSystem system) {
        if (system == null) {
            throw new RuntimeException("文件服务器为空！");
        }
        FileSystemUtil.FILE_SYSTEM = system;
    }

    /**
     * 删除文件服务器的文件
     * 
     * @param filePath
     * @return
     */
    public static Boolean deleteFile(String path) throws Exception {
        return FILE_SYSTEM.deleteFile(path);
    }

    /**
     * 删除服务器端文件夹
     * 
     * @param path
     * @return
     * @throws Exception
     */
    public static Boolean deleteDir(String path) throws Exception {
        return FILE_SYSTEM.deleteDir(path);
    }

    /**
     * 获取文件服务器的文件流
     * 
     * @param filePath
     * @return
     */
    public static InputStream getFileAsStream(String path) throws Exception {
        return FILE_SYSTEM.getFileAsStream(path);
    }

    /**
     * 读取文件服务器中的文件
     * 
     * @param path
     * @return
     * @throws Exception
     */
    public static String getFileAsString(String path) throws Exception {
        return FILE_SYSTEM.getFileAsString(path);
    }

    /**
     * 保存文件到文件服务器
     * 
     * @param src
     *            支持3种类型(1:File 2:String 3:InputStream)
     * @param filePath
     */
    public static Boolean saveFile(Object src, String path) throws Exception {
        return FILE_SYSTEM.saveFile(src, path);
    }

    /**
     * 将文件从文件服务器拷贝到本地
     * 
     * @return
     * @throws Exception
     */
    public static Boolean copyFileToLocal(String filePath, String localFilePath) throws Exception {
        return FILE_SYSTEM.copyFileToLocal(filePath, localFilePath);
    }

    /**
     * 文件服务器中从一个文件拷贝到另一个文件中
     * 
     * @param srcPath
     * @param destinationPath
     * @return
     * @throws Exception
     */
    public static Boolean copyFile(String srcPath, String destinationPath) throws Exception {
        return FILE_SYSTEM.copyFile(srcPath, destinationPath);
    }

    /**
     * 获取此文件服务器文件夹下的所有文件
     * 
     * @param dirPath
     */
    public static String[] getFiles(String dirPath) throws Exception {
        return FILE_SYSTEM.getFiles(dirPath);
    }
    /**
     * 获取此文件服务器文件夹下的所有文件名
     * @param dirPath
     * @return
     * @throws Exception
     */
    public static String[] getFilesName(String dirPath) throws Exception {
        return FILE_SYSTEM.getFilesName(dirPath);
    }
    /**
     * 获取此文件服务器文件名
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        return FILE_SYSTEM.getFileName(filePath);
    }

    /**
     * 文件服务器文件路径是否存在
     * 
     * @param filePath
     * @return
     */
    public static Boolean fileExists(String filePath) {
        return FILE_SYSTEM.fileExists(filePath);
    }
    /**
     * 判断文件服务器文件夹是否存在
     * @param dirPath
     * @return
     */
    public static Boolean dirExists(String dirPath){
        return FILE_SYSTEM.dirExists(dirPath);
    }
    /**
     * 获取文件大小
     * 
     * @param filePath
     * @return
     */
    public static Long getFileLength(String filePath) {
        return FILE_SYSTEM.getFileLength(filePath);
    }

    /**
     * 获取最后修改时间
     * 
     * @param filePath
     * @return
     */
    public static Date getLastModified(String filePath) throws Exception {
        return FILE_SYSTEM.getLastModified(filePath);
    }

    /**
     * 创建文件夹
     * 
     * @param filePath
     * @return
     * @throws Exception
     */
    public static Boolean mkDir(String filePath) throws Exception {
        return FILE_SYSTEM.mkDir(filePath);
    }

    /**
     * 剪切文件到另一个文件
     * 
     * @param srcPath
     * @param destinationPath
     * @return
     */
    public static Boolean cutFile(String srcPath, String destinationPath) {
        return FILE_SYSTEM.cutFile(srcPath, destinationPath);
    }
    /**
     * 获取父文件夹
     * @param filePath
     * @return
     */
    public static String getPatentFile(String filePath){
    	return FILE_SYSTEM.getPatentFile(filePath);
    }
    
    /**
     * 获取文件
     * @param filePath
     * @return
     */
	public static File getFile(String filePath) {
		return FILE_SYSTEM.getFile(filePath);
	}
	
	public static void copyTempFile(String srcPath, String destPath) throws Exception{
		if(StorePathUtil.isTempPath(srcPath)){
			copyFile(srcPath, destPath);
		}
	}
}
