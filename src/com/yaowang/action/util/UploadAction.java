package com.yaowang.action.util;

import java.io.IOException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.common.action.BaseDataAction;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.filesystem.util.FileSystemUtil;
import com.yaowang.util.filesystem.util.FileUtil;
import com.yaowang.util.filesystem.util.StorePathUtil;
import com.yaowang.util.upload.UploadFile;
import com.yaowang.util.upload.UploadUtils;
/**
 * 上传文件工具
 * @author shenl
 *
 */
public class UploadAction extends BaseDataAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户名
	 */
	private String uid;
	/**
	 * 上传到临时目录
	 * @throws Exception 
	 * @throws IOException
	 */
	public String upload() throws Exception{
		UploadFile[] files = UploadUtils.handleFileUpload();
		if (ArrayUtils.isNotEmpty(files)) {
			UploadFile file = files[0];
			//获取用户id
			String userId = UUIDUtils.newId();
			
			//编码文件名，为了防止中文问题
			String fileName = UUIDUtils.newId() + "." + FileUtil.getExtensionName(file.getFileName());
			String path = StorePathUtil.getStoreTempPath(fileName, userId);	
			FileSystemUtil.saveFile(file.getFile(), path);
			uid = path;
		}
		return SUCCESS;
	}
	/**
	 * kindeditor文件上传 
	 * @throws Exception 
	 */
	public void kindeditor() throws Exception{
		UploadFile[] files = UploadUtils.handleFileUpload();
		
		String msg = null;
		if (files == null || files.length == 0) {
			msg = "请先选择要上传的文件";
		}else {
			UploadFile file = files[0];
			if (!file.getContentType().matches("image/.+") 
					&& !file.getContentType().matches("application/.+")) {
				msg = "上传文件必须为图片";
			}
			
			if (StringUtils.isNotBlank(msg)) {
				//返回错误
				JSONObject jsonData = new JSONObject();
				jsonData.put("error", 1);
				jsonData.put("message", msg);
				write(jsonData.toJSONString());
//				write("{'error': 1, 'message': '" + msg + "' }");
			}else {
				//保存文件
				String ext = FileUtil.getExtensionName(file.getFileName());
				String fileName = UUIDUtils.newId() + "." + ext;
				StringBuffer path = StorePathUtil.buildPath(getCommonUserId(), "kindeditor", fileName);
				FileSystemUtil.saveFile(file.getFile(), path.toString());
				path.insert(0, getUploadPath());
				JSONObject jsonData = new JSONObject();
				jsonData.put("error", 0);
				jsonData.put("url", path.toString());
				jsonData.put("id", fileName);
				write(jsonData.toJSONString());
//				write("{'error': 0, 'url': '" + "http://127.0.0.1:8088/"+path.toString() + "', 'id': '" + fileName + "' }");
			}
		}
	}
	
	public void blank() throws IOException{
		write("");
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

}
