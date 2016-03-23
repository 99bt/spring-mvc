package com.yaowang.lansha.action.web;

import java.io.IOException;

import org.apache.commons.lang.ArrayUtils;

import com.alibaba.fastjson.JSONObject;
import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.filesystem.util.FileSystemUtil;
import com.yaowang.util.filesystem.util.FileUtil;
import com.yaowang.util.filesystem.util.StorePathUtil;
import com.yaowang.util.upload.UploadFile;
import com.yaowang.util.upload.UploadUtils;
/**
 * @ClassName: UploadAction
 * @Description: 上传文件到临时文件目录
 * @author wanglp
 * @date 2015-12-10 下午7:31:51
 *
 */
public class UploadAction extends LanshaBaseAction {
	private static final long serialVersionUID = 1L;
	
	public void uploadImg() throws IOException{
		JSONObject object = new JSONObject();
		try {
			//上传图片处理
			StringBuffer path = new StringBuffer();
//			List<String> exts = new ArrayList<String>();
			String[] exts = new String[]{"gif","jpg","jpeg","png"};
			
			UploadFile[] files = UploadUtils.handleFileUpload();
			for (UploadFile file : files) {
				//flash传文件，contentType=application/octet-stream(二进制流),判断文件后缀,忽略大小写
				String ext = FileUtil.getExtensionName(file.getFileName());
				if (ArrayUtils.contains(exts, ext.toLowerCase()) || file.getContentType().matches("image/.+")) {
					boolean b = getUserIsLogin();
					String uid = "";
					if (b) {
						uid = getUserLogin().getId();
					}else {
						uid = UUIDUtils.newId();
					}
					//文件地址 temp/年/月/文件名
					String filePath = StorePathUtil.getStoreTempPath(System.currentTimeMillis() + "." + ext, uid).toString();
					FileSystemUtil.saveFile(file.getFile(), filePath);
					path.append(filePath).append(",");
				}else{
					write(getFailed("图片类型错误"));
					return ;
				}
			}
			if(path.length() > 0){
				path.setLength(path.length() - 1);
			}
			
			object.put("url", getUploadPath() + path.toString());
			writeSuccess(object);
		} catch (Exception e) {
			write(getFailed("上传失败"));
		}
	}

}
