package com.yaowang.util.filesystem.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.yaowang.util.UUIDUtils;

/**
 * 提供store目录工具类
 * @author shenl
 *
 */
public final class StorePathUtil {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("/yyyy/MM/");
	/**
	 * 组装路径
	 * @param id
	 * @param type
	 * @param fileName
	 * @return
	 */
	public static StringBuffer buildPath(String id, String type, String fileName){
		StringBuffer buffer = new StringBuffer(DATE_FORMAT.format(new Date()));
		buffer.append(id);
		buffer.append("/");
		buffer.append(type);
		buffer.append("/");
		buffer.append(fileName);
		return buffer;
	}
	
	/**
	 * 链接地址加入一个随机数
	 * @param buffer
	 */
	public static String formatUri(StringBuffer path){
		return formatUri(path, true);
	}
	/**
	 * 链接地址加入一个随机数
	 * @param buffer
	 */
	public static String formatUri(StringBuffer path, Boolean setCache){
		Calendar calendar = Calendar.getInstance();
		if (setCache) {
			path.append("?_").append(calendar.get(Calendar.MILLISECOND));
		}
		return path.toString();
	}
	/**
	 * 先检查有没有随机参数，如果有先去掉
	 * @param path
	 * @return
	 */
	public static String removeCache(String path){
		if (StringUtils.isEmpty(path)) {
			return path;
		}
		int pix = path.lastIndexOf("?");
		if (pix != -1) {
			path = path.substring(0, pix);
		}
		return path;
	}
	/**
	 * 获取store目录下的一个临时目录
	 * @param srcName
	 * @param unitId
	 * @param userId
	 * @return
	 */
	public static String getStoreTempPath(String srcName, String userId){
		Date date = new Date();
		StringBuffer buffer = getStoreTempPath(date);
		if (StringUtils.isNotBlank(userId)) {
			buffer.append(userId);
			buffer.append("/");
		}
		buffer.append(srcName);
		//拷贝到store临时目录下
		String path = buffer.toString();//不带store目录的临时目录
		return path;
	}
	/**
	 * 获取store目录下的一个临时目录
	 * @param date
	 * @return
	 */
    public static StringBuffer getStoreTempPath(Date date){
        StringBuffer buffer = new StringBuffer();
        buffer.append("/");
        buffer.append("temp");
//        buffer.append("/");
        buffer.append(getTimePath(date));
        return buffer;
    }
    /**
     * 是否临时目录
     * @param path
     * @return
     */
    public static Boolean isTempPath(String path) {
		return path != null && path.matches("/temp/.*");
	}
	
	/**
	 * 获取store目录中日期部分
	 * @return
	 */
	private static StringBuffer getTimePath(Date date){
		StringBuffer buffer = new StringBuffer();
		buffer.append(DATE_FORMAT.format(date));
		return buffer;
	}
	/**
	 * 获取本地临时路径
	 * @param path
	 * @return
	 */
	public static String getLocalTempPath(String path){
		StringBuffer buffer = new StringBuffer("temp/");
		buffer.append(path);
		return buffer.toString();
	}
	/**
	 * 获取本地临时文件
	 * @return
	 */
	public static File getLocalTempFile(){
		return new File(getLocalTempPath(UUIDUtils.newId()));
	}
}
