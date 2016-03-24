package com.yaowang.common.util;

import java.io.File;

import com.yaowang.common.constant.BaseConstant;
import com.yaowang.util.filesystem.util.FileSystemUtil;
import com.yaowang.util.filesystem.util.FileUtil;
import com.yaowang.util.filesystem.util.StorePathUtil;
import com.yaowang.util.img.ImageUtils;
import com.yaowang.util.upload.UploadFile;

public class ImageFileUtil {
	/**
	 * 文件
	 * @param userId
	 * @param type
	 * @param file
	 * @param ext
	 * @return
	 * @throws Exception
	 */
	public static final String savePicture(String userId, String type, UploadFile file, String picture, String preview, Integer width, Integer height) throws Exception{
		if (width == null) {
			width = BaseConstant.PICTURE_WIDTH;
		}
		if (height == null) {
			height = BaseConstant.PICTURE_HEIGHT;
		}
		
		//获取扩展名
		String fileName = file.getFileName();
		String ext = FileUtil.getExtensionName(fileName);
		
		//组装文件路径
		StringBuffer picturePath = StorePathUtil.buildPath(userId, type, picture + "." + ext);
		//拷贝原始文件
		FileSystemUtil.saveFile(file.getFile(), picturePath.toString());
		
		//缩略图
		ImageUtils.changeSize(file.getFile().getAbsolutePath(), width, height);
		StringBuffer previewPath = StorePathUtil.buildPath(userId, type, preview + "." + ext);
		FileSystemUtil.saveFile(file.getFile(), previewPath.toString());
		//加入随机数，防止浏览器缓存
		String cachePath = StorePathUtil.formatUri(previewPath);
		return cachePath;
	}
	
	public static final String savePicture(String userId, String type, String filePath, String picture, String preview, Integer width, Integer height) throws Exception{
		if (width == null) {
			width = BaseConstant.PICTURE_WIDTH;
		}
		if (height == null) {
			height = BaseConstant.PICTURE_HEIGHT;
		}
		
		//获取扩展名
//		String fileName = file.getFileName();
		String ext = FileUtil.getExtensionName(filePath);
		
		//组装文件路径
		StringBuffer picturePath = StorePathUtil.buildPath(userId, type, picture + "." + ext);
		//拷贝原始文件
		FileSystemUtil.copyFile(filePath, picturePath.toString());
		
		//缩略图
		File file = FileSystemUtil.getFile(filePath);
		ImageUtils.changeSize(file.getAbsolutePath(), width, height);
		StringBuffer previewPath = StorePathUtil.buildPath(userId, type, preview + "." + ext);
		FileSystemUtil.saveFile(file, previewPath.toString());
		//加入随机数，防止浏览器缓存
		String cachePath = StorePathUtil.formatUri(previewPath);
		file.delete();
		return cachePath;
	}
}
