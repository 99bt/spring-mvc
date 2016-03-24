package com.yaowang.util.upload;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
/**
 * 获取上传文件工具
 * @author shenl
 *
 */
public class UploadUtils {
	/**
	 * 获取上传文件
	 * @return
	 * @throws Exception
	 */
	public static final UploadFile[] handleFileUpload() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request instanceof MultiPartRequestWrapper) {
			MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
			if (!requestWrapper.hasErrors()) {
				Enumeration<String> e = requestWrapper.getFileParameterNames();
				List<UploadFile> filelist = new ArrayList<UploadFile>();
				while (e.hasMoreElements()) {
					String fieldName = e.nextElement();
					// uploaded file properties
					File[] uploadedFiles = requestWrapper.getFiles(fieldName);
					if ((uploadedFiles == null) || (uploadedFiles.length == 0)) {
						Exception fileUploadException = new Exception(
								"文件上传表单域读取错误: " + fieldName);
						throw fileUploadException;
					}
					String[] uploadNames = requestWrapper.getFileNames(fieldName);
					String[] contentTypes = requestWrapper.getContentTypes(fieldName);

					if ((uploadNames.length == contentTypes.length)
							&& (uploadNames.length == uploadedFiles.length)) {
						String fileName;
						for (int i = 0; i < uploadedFiles.length; i++) {
							fileName = uploadNames[i];
							int slash = Math.max(fileName.lastIndexOf('/'),	fileName.lastIndexOf('\\'));
							if (slash > -1) {
								fileName = fileName.substring(slash + 1);
							}
							UploadFile uploadfile = new UploadFile(fileName, uploadedFiles[i], contentTypes[i], fieldName);
							filelist.add(uploadfile);
						}
					}
				}
				return (UploadFile[]) filelist.toArray(new UploadFile[0]);
			}
		}
		return null;
	}
}
