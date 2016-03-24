package com.yaowang.util.filesystem.util;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class FileUtil {
	private static final Map<String, String> EXT_NAME_MAP = new HashMap<String, String>(); // 扩展名对应的图片名
	// 扩展名对应的文件名
	private static final String PICTURE = "picture.gif";// 图片
	private static final String WORD = "word.gif";// word文档
	private static final String PPT = "ppt.gif";// ppt文档
	private static final String EXCEL = "excel.gif";// excel文档
	private static final String TXT = "txt.gif";// txt文档
	private static final String PDF = "pdf.gif";// pdf文档
	private static final String MOVIE = "movie.gif";// 视频
	private static final String TWEENED = "twened.gif";// 动画
	private static final String VOICE = "voice.gif";// 音频
	private static final String ARCHIVE = "archive.gif";// 压缩文档
	private static final String DEFAULT = "default.gif";// 默认

	static {
		EXT_NAME_MAP.put("jpg", PICTURE);
		EXT_NAME_MAP.put("gif", PICTURE);
		EXT_NAME_MAP.put("bmp", PICTURE);
		EXT_NAME_MAP.put("doc", WORD);
		EXT_NAME_MAP.put("docx", WORD);
		EXT_NAME_MAP.put("wps", WORD);
		EXT_NAME_MAP.put("ppt", PPT);
		EXT_NAME_MAP.put("dps", PPT);
		EXT_NAME_MAP.put("xls", EXCEL);
		EXT_NAME_MAP.put("et", EXCEL);
		EXT_NAME_MAP.put("txt", TXT);
		EXT_NAME_MAP.put("pdf", PDF);
		EXT_NAME_MAP.put("rm", MOVIE);
		EXT_NAME_MAP.put("wmv", MOVIE);
		EXT_NAME_MAP.put("vm", MOVIE);
		EXT_NAME_MAP.put("mp4", MOVIE);
		EXT_NAME_MAP.put("flv", TWEENED);
		EXT_NAME_MAP.put("mp3", VOICE);
		EXT_NAME_MAP.put("zip", ARCHIVE);
		EXT_NAME_MAP.put("rar", ARCHIVE);
	}
	
	
	// 三分屏文件
	public static String THREE_SCREEN_FILE_EXTNAME = "zip";
	

	/**
	 * 获取扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1).toLowerCase();
			}
		}
		return filename;
	}

	/**
	 * 去除html标签
	 * 
	 * @param inputString
	 * @return
	 */
	public static String html2Text(String inputString) {
		if (StringUtils.isEmpty(inputString)) {
			return "";
		}
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script>]*?>[\s\S]*?<\/script>
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style>]*?>[\s\S]*?<\/style>
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签
			textStr = htmlStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;
	}

	/**
	 * 根据扩展名 返回 显示图片的名称
	 * 
	 * @param extName
	 *            扩展名
	 * @return icon2.gif
	 */
	public static String getImageNameByExtName(String extName) {
		String fileName = null;
		extName = StringUtils.lowerCase(extName);
		fileName = EXT_NAME_MAP.get(extName);
		if(fileName == null)
			fileName= DEFAULT;
		
		return fileName;
	}
	/**
	 * 判断后续是否为图片
	 */
	public static boolean isPicture(String extName){
		boolean result=false;
		String fileName = EXT_NAME_MAP.get(extName);
		if(FileUtil.PICTURE.equals(fileName)){
			result=true;
		}
		return result;
	}
	
	public static boolean checkIncludePic(String htmlStr){
		if(StringUtils.isEmpty(htmlStr)){
			return false;
		}
		
		//判断是否有图片
		if (htmlStr.indexOf("<img") > -1) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
     * 得到M为单位的文件大小 小于100kb的都以0.1M计
     * @param size
     * @return
     */
    public static String getSizeForM(long size){
        String Msize ="";
        //小于100KB
        if(size/1024/100<=1){
            Msize="0.1";
        }else{
            float m = (float)size/1024/1024;
            NumberFormat format = NumberFormat.getInstance();
            format.setMaximumFractionDigits(1);
            Msize = format.format(m); 
        }
        return Msize;
    }
}
