package com.yaowang.util.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.yaowang.util.DateUtils;
import com.yaowang.util.filesystem.util.FileSystemUtil;

/**
 * http 文件下载
 * @作者 shenl
 * @创建日期 2011-7-16
 * @版本 V 1.0
 */
public class DownloadUtil {
	/**
	 * 获取断点下载时的跳转长度
	 * @return
	 */
	public static long getRange(){
		// 断点续传
		String range = ServletActionContext.getRequest().getHeader("Range");
		long start;
		if (StringUtils.isEmpty(range)) {
			start = 0;
		} else {
			range = range.substring("bytes=".length(), range.indexOf("-"));
			start = Long.parseLong(range);
		}
		return start;
	}
	/**
	 * http 文件下载
	 * @param filePath
	 * @param fileName
	 * @param nPos
	 * @param response
	 * @throws Exception 
	 */
	public static void downloadLocalFile(String filePath, String fileName, long nPos) throws Exception{
		InputStream fis = new FileInputStream(filePath);
		download(fis, fileName, nPos);
	}
	
	public static void download(String filePath, String fileName, long nPos) throws Exception{
		InputStream fis = FileSystemUtil.getFileAsStream(filePath);
		download(fis, fileName, nPos);
	}
	
	public static void download(InputStream fis, String fileName, long nPos) throws Exception{
		long l = fis.available();  
		HttpServletResponse response = ServletActionContext.getResponse();
		// 清空response
		response.reset();
		// 设置response的Header
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes("gbk"),"iso8859-1") + "\"");
		
		String contentRange = null;
		if (nPos != 0) {
			fis.skip(nPos);
			contentRange = new StringBuffer("bytes ").append(nPos).append("-").append(l - 1).append("/").append(l).toString();
			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);  
		}else {
			contentRange = l + "";
		}
		response.addHeader("Content-Length", contentRange);
		ServletOutputStream out = response.getOutputStream();
		try {
			IOUtils.copy(fis, out);
			out.flush();
		}catch (ClientAbortException e) {
			System.err.println("文件下载：http连接被客户端关闭");
			throw e;
		} finally{
			IOUtils.closeQuietly(fis);
		}
	}
	
	public static void downloadExcel(String fileName, String tempPath, HSSFWorkbook wb){
		File file = new File(tempPath);
		Date nowDate = new Date();
		try  
        {  
            //写入文件
            if(!file.exists()){
            	file.mkdirs();
            }
            FileOutputStream fout = new FileOutputStream(tempPath+DateUtils.format(nowDate)+".xls"); 
            wb.write(fout);
            fout.flush();
            fout.close();
            //下载
			downloadLocalFile(tempPath+DateUtils.format(nowDate)+".xls", fileName, DownloadUtil.getRange()); 
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }
		finally{
			//删除临时文件
			file.delete();
			
		}
	}
	
	public static void downloadCSV(String fileName, File file) {
		try {
			InputStream fis = new FileInputStream(file);
			// 下载
			download(fis, fileName, DownloadUtil.getRange());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 删除临时文件
			file.delete();

		}
	}
}
