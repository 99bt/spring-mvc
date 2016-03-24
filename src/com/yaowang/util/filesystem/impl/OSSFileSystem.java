package com.yaowang.util.filesystem.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.IOUtils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;
import com.yaowang.util.filesystem.FileSystem;

/**
 * 
 * @Description: 阿里云oss服务器
 * @author wangjs
 * @date 2016-2-2
 * @version V1.0
 */
public class OSSFileSystem implements FileSystem{
    private static String bucketName = "wjstest";	
    private static OSSClient client = null;
    
    /**
     * 初始化
     * 
     * @param storePath
     */
    public static final void init(String accessKeyId,String accessKeySecret,String endpoint,String bucketName1) {
        client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        bucketName=bucketName1;
    }
    
	@Override
	public Boolean saveFile(Object src, String filePath) throws Exception {
		filePath = setPath(filePath);
		
        if (src instanceof File) {
            // 文件
        	client.putObject(new PutObjectRequest(bucketName, filePath, (File) src));	        	
            return true;
        } else if (src instanceof String) {
        	// 保存文本
        	InputStream is = new ByteArrayInputStream(((String) src).getBytes());
        	try {
        		client.putObject(bucketName, filePath, is);
			} finally{
	            IOUtils.closeQuietly(is);
			}
            return true;
        } else if (src instanceof InputStream) {
            // 保存流
            client.putObject(bucketName, filePath, (InputStream) src);
            return true;
        }else if (src instanceof BufferedImage) {
            // 保存图片
            InputStream is = getImageStream((BufferedImage)src);
        	try {
        		client.putObject(bucketName, filePath, is);
			} finally{
	            IOUtils.closeQuietly(is);
			}
            return true;
        }else {
            return false;
        }
	}

	@Override
	public Boolean copyFile(String srcPath, String destinationPath) throws Exception {
        OSSObject object = getOSSObject(srcPath);
        if(object == null){
        	return false;
        }
        destinationPath = setPath(destinationPath);
        InputStream in = object.getObjectContent();
        try {
        	client.putObject(bucketName, destinationPath, in);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return true;
	}

	@Override
	public Boolean copyFileToLocal(String filePath, String localFilePath) throws Exception {
        OSSObject object = getOSSObject(filePath);
        if(object == null){
        	return false;
        }
        OutputStream os = new FileOutputStream(localFilePath);
        InputStream in = object.getObjectContent();
        try {
            IOUtils.copy(in, os);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(in);
        }
        return true;        
	}

	@Override
	public Boolean deleteFile(String filePath) throws Exception {
		filePath = setPath(filePath);
		client.deleteObject(bucketName, filePath);
		return true;
	}


	@Override
	public InputStream getFileAsStream(String filePath) throws Exception {
        OSSObject object = getOSSObject(filePath);
        if(object == null){
        	return null;
        }
		return object.getObjectContent();
	}

	@Override
	public String getFileAsString(String filePath) throws Exception {
        OSSObject object = getOSSObject(filePath);
        if(object == null){
        	return null;
        }
        InputStream in = object.getObjectContent();
        try {
        	StringBuffer sb = displayTextInputStream(in);        
        	return sb.toString();
		} finally{
            IOUtils.closeQuietly(in);
		}
	}
	
	@Override
	public Boolean deleteDir(String filePath) throws Exception {
		filePath = setPath(filePath);
		String[] keys = getFiles(filePath);
		client.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(Arrays.asList(keys)));
		return null;
	}

	@Override
	public String[] getFiles(String dirPath) throws Exception {
		int maxKeys = 100;
		List<String> list = new ArrayList<String>();
		
		boolean isTruncated = true;
		String nextMarker = null;
		while (isTruncated) {
			ObjectListing objectListing = client.listObjects(new ListObjectsRequest(bucketName)
							.withMarker(nextMarker).withMaxKeys(maxKeys).withPrefix(dirPath));
			List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
			for (OSSObjectSummary s : sums) {
				list.add(s.getKey());
			}
			nextMarker = objectListing.getNextMarker();
			isTruncated = objectListing.isTruncated();
		}               
        return list.toArray(new String[list.size()]);
	}
	
	@Override
	public String[] getFilesName(String dirPath) throws Exception {
		dirPath = setPath(dirPath);
		String[] keys = getFiles(dirPath);
		String[] temp = new String[keys.length];
		for(int i=0; i<keys.length; i++){
			temp[i] = getFileName(keys[i]);
		}
		return temp;
	}
	
	@Override
	public Boolean dirExists(String dirPath) {
		return true;
	}
	
	@Override
	public Boolean mkDir(String filePath) throws Exception {
		//不存在目录的概念，直接都返回true
		return true;
	}
	
	@Override
	public String getPatentFile(String filePath) {
		//解析filePath
		String[] str = filePath.split("/");
        if(str.length <= 1){
        	return "";
        } else {
        	return str[str.length-2];
        }
	}
	
	@Override
	public String getFileName(String filePath) {
		String[] str = filePath.split("/");
		return str[str.length-1];
	}

	@Override
	public Boolean fileExists(String filePath) {
		filePath = setPath(filePath);
        boolean exists = client.doesObjectExist(bucketName, filePath);
		return exists;
	}

	@Override
	public Long getFileLength(String filePath) {
        OSSObject object = getOSSObject(filePath);
        if(object == null){
        	return 0l;
        }
        return object.getObjectMetadata().getContentLength();
	}

	@Override
	public Date getLastModified(String filePath) throws Exception {
        OSSObject object = getOSSObject(filePath);
        if(object == null){
        	return null;
        }
		return object.getObjectMetadata().getLastModified();
	}


	@Override
	public Boolean cutFile(String srcPath, String destinationPath) {
		//先复制再删除
		try {
			copyFile(srcPath, destinationPath);
			deleteFile(srcPath);
		} catch (Exception e) {
			return false;
		}
		return true;
	}


	@Override
	public File getFile(String filePath) {
        OSSObject object = getOSSObject(filePath);
        if(object==null){
        	return null;
        }
        //解析filePath路径
        String dir = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        File f = new File(dir);//存放到临时文件中
        if(!f.exists()){
        	f.mkdirs();
        }
        File file = new File(f, filePath.substring(filePath.lastIndexOf("/") + 1));
        if(file.exists()){
        	return file;
        }else{
        	try {
        		InputStream ins=object.getObjectContent();
        		OutputStream os = new FileOutputStream(file);
        		try {
        			int bytesRead = 0;
        			byte[] buffer = new byte[8192];
        			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
        				os.write(buffer, 0, bytesRead);
        			}
				} finally{
					IOUtils.closeQuietly(os);
					IOUtils.closeQuietly(ins);
				}
        	} catch(Exception e){
        		e.printStackTrace();
        	}
      		return file;
        }
	}

	private OSSObject getOSSObject(String filePath){
		filePath = setPath(filePath);
		
		OSSObject object = client.getObject(new GetObjectRequest(bucketName, filePath));
        return object;
	}
	
    private StringBuffer displayTextInputStream(InputStream input) throws IOException {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    	StringBuffer sb=new StringBuffer();
    	try {
    		while (true) {
    			String line = reader.readLine();
    			if (line == null){
    				break;
    			}
    			sb.append(line);
    		}
		} finally{
			IOUtils.closeQuietly(reader);
		}
        return sb;
    }
    
	private InputStream getImageStream(BufferedImage bi) throws IOException {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ImageOutputStream imOut;
		imOut = ImageIO.createImageOutputStream(bs);
		ImageIO.write(bi, "jpg", imOut);
		InputStream is = new ByteArrayInputStream(bs.toByteArray());
		return is;
	}
	
	private String setPath(String filePath){
		if(filePath.startsWith("/")){
			filePath = filePath.substring(1);
		}
		return filePath;
	}
    
	public static void main(String[] args) {
		OSSFileSystem aliOssFileSystem=new OSSFileSystem();
		try {
			String[] str=null;
			//File file=new File("D:\1.jpg");
			//aliOssFileSystem.saveFile(file, "1.jpg");
			//aliOssFileSystem.copyFile("w/j/s/2.txt", "w/8.txt");
			//aliOssFileSystem.copyFileToLocal("w/j/s/2.txt","D:/temp.txt");
			//aliOssFileSystem.deleteFile("b/3.txt");
			//aliOssFileSystem.deleteDir("w/j/s/");
			
			/*InputStream inputStream=aliOssFileSystem.getFileAsStream("b/2.txt");
			System.out.println(inputStream.toString());*/
			/*String str=aliOssFileSystem.getFileAsString("b/2.txt");
			System.out.println(str);*/
			//System.out.println(aliOssFileSystem.getPatentFile("w/j/s/2.txt"));
			//System.out.println(aliOssFileSystem.getFileName("w/j/5.txt"));			
			//System.out.println(aliOssFileSystem.fileExists("w/j/5.txt"));
			//System.out.println(aliOssFileSystem.getFileLength("w/j/5.txt"));
			//System.out.println(aliOssFileSystem.getLastModified("w/j/5.txt"));
			//aliOssFileSystem.cutFile("w/j/5.txt","w/j/51.txt");
			/*File file=aliOssFileSystem.getFile("w/j/51.txt");
			System.out.println(file.getPath());*/

			System.out.println("==========");
			str=aliOssFileSystem.getFiles("w/");
			//str=aliOssFileSystem.getFilesName("w/");
			print(str);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void print(String[] str){
		if(str==null){
			return;
		}
		for(String str1:str){
			System.out.println(str1);
		}
	}
}
