package com.yaowang.common.importData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.yaowang.util.filesystem.util.FileUtil;
import com.yaowang.util.spring.ContainerManager;
import com.yaowang.util.upload.UploadFile;
import com.yaowang.util.upload.UploadUtils;

public abstract class DataImportUtil {	
	/**
	 * 格式化时间
	 */
	private static final SimpleDateFormat[] FORMATS = new SimpleDateFormat[]{
		new SimpleDateFormat("yyyy-MM-dd"), new SimpleDateFormat("yyyy/MM/dd"),
		new SimpleDateFormat("dd-MM-yyyy")
	};
	
	protected static Date getDate(String date) throws ParseException{
		for (SimpleDateFormat F : FORMATS) {
			try {
				return F.parse(date);
			} catch (ParseException e) {
				
			}
		}
		return null;
	}
	/**
	 * 获取上传文件
	 * @throws Exception 
	 */
	protected Object getUploadFile(String id) throws Exception{
		UploadFile[] files = UploadUtils.handleFileUpload();
		if (files.length < 1) {
			return "文件未找到";
		}
		UploadFile excel = files[0];
		String ext = FileUtil.getExtensionName(excel.getFileName());
		if (!ext.equals("xls") && !ext.equals("xlsx")) {
			return "文件格式错误";
		}
		if (getLock().contains(id)) {
			return "正在导入";
		}else {
			open(id);
		}
		return excel;
	}
	
	protected static Object getComponent(String name) {
    	return ContainerManager.getComponent(name);
    }
	
	protected void open(String id){
		getLock().add(id);
	}
	
	protected void close(String id){
		getLock().remove(id);
	}
	/**
	 * 获取锁
	 * @return
	 */
	protected abstract Set<String> getLock();
}
