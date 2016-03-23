package com.yaowang.lansha.action.admin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.lansha.entity.YwBanner;
import com.yaowang.lansha.entity.YwUserRoom;
import com.yaowang.lansha.service.YwBannerService;
import com.yaowang.lansha.service.YwUserRoomService;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.filesystem.util.FileSystemUtil;
import com.yaowang.util.filesystem.util.FileUtil;
import com.yaowang.util.filesystem.util.StorePathUtil;

public class AdManageAction extends BasePageAction {

	/**
	 * [2015-12-4下午4:45:16]zlb
	 * @Fields serialVersionUID : TODO
	 */
	
	private static final long serialVersionUID = 1L;
	
	@Resource
	private YwBannerService ywBannerService;
	
	@Resource
	private YwUserRoomService ywUserRoomService;
	
	private YwBanner entity;
	
	private YwBanner ywBanner;
	
	//上传的小图
	private File bannerImg;
	//上传的大图
	private File bannerBigImg;
	//上传小图文件的文件名
	private String bannerImgFileName;
	//上传大图文件的文件名
	private String bannerBigImgFileName;
	//上传小图文件的类型
	private String bannerImgContentType;
	//上传大图文件的类型
	private String bannerBigImgContentType;
	
	/**
	 * 列表
	 * @return
	 * @creationDate. 2015-12-4 下午4:47:52
	 */
	public String list(){
		list = ywBannerService.getYwBannerPage(entity, getPageDto());
		ywBannerService.setRoom((List<YwBanner>) list, false);
		return SUCCESS;
	}
	
	/**
	 * 详情
	 * 
	 * @return
	 */
	public String detail() {
		if (StringUtils.isNotBlank(id)) {
			ywBanner = ywBannerService.getYwBannerById(id);
		}
		return SUCCESS;
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		if(StringUtils.isBlank(ywBanner.getId())){
			if(StringUtils.isNotBlank(ywBanner.getRoomId())){
				YwUserRoom ywUserRoom = null;
				try{
					ywUserRoom = ywUserRoomService.getYwUserRoomById(Integer.valueOf(ywBanner.getRoomId()));
				}catch(Exception e){
					addActionError("房间id填写错误，保存失败");
					return ERROR;
				}
				if(ywUserRoom == null){
					addActionError("该房间id找不到，保存失败");
					return ERROR;
				}
			}
			
			//保存图片并返回存储路径|
			ywBanner.setCreateTime(getNow());
			ywBanner.setId(UUIDUtils.newId());
			
			try {
				if(bannerImg != null){
					String msg = upload(ywBanner.getImg(), bannerImg, this.getBannerImgFileName(), ywBanner.getId(), 260, 89, "img");
					if(StringUtils.isNotBlank(msg)){
						addActionError(msg);
						ywBanner.setId(null);
						return ERROR;
					}
				}
			} catch (Exception e) {
				ywBanner.setId(null);
				e.printStackTrace();
			}
			try {
				if(bannerBigImg != null){
					String msg = upload(ywBanner.getBigImg(), bannerBigImg, this.getBannerBigImgFileName(), ywBanner.getId(), 980, 551, "bigImg");
					if(StringUtils.isNotBlank(msg)){
						addActionError(msg);
						ywBanner.setId(null);
						return ERROR;
					}
				}
			} catch (Exception e) {
				ywBanner.setId(null);
				e.printStackTrace();
			}
			ywBannerService.save(ywBanner);
		}else{
			if (bannerImg != null && bannerImg.exists()) {
				//保存图片并返回存储路径
				try {
					if(bannerImg != null){
						String msg = upload(ywBanner.getImg(), bannerImg, this.getBannerImgFileName(), ywBanner.getId(), 260, 89, "img");
						if(StringUtils.isNotBlank(msg)){
							addActionError(msg);
							return ERROR;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (bannerBigImg != null && bannerBigImg.exists()) {
				//保存图片并返回存储路径
				try {
					if(bannerBigImg != null){
						String msg = upload(ywBanner.getBigImg(), bannerBigImg, this.getBannerBigImgFileName(), ywBanner.getId(), 980, 551, "bigImg");
						if(StringUtils.isNotBlank(msg)){
							addActionError(msg);
							return ERROR;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(StringUtils.isNotBlank(ywBanner.getRoomId())){
				YwUserRoom ywUserRoom = null;
				try{
					ywUserRoom = ywUserRoomService.getYwUserRoomById(Integer.parseInt(ywBanner.getRoomId()));
				}catch(Exception e){
					addActionError("房间id填写错误，保存失败");
					return ERROR;
				}
				if(ywUserRoom == null){
					addActionError("该房间id找不到，保存失败");
					return ERROR;
				}
			}
			//如果是房间，清空大图地址
			if("0".equals(ywBanner.getType())){
				ywBanner.setBigImg("");
			}
			ywBannerService.update(ywBanner);
		}
		addActionMessage("保存成功");
		list();
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		ywBannerService.delete(ids);
		addActionMessage("删除成功");
		list();
		return SUCCESS;
	}
	
	/**
	 * 图片上传
	 * @param img 原图片路径
	 * @param imgFile 上传文件
	 * @param bannerImgFileName 上传文件文件名
	 * @param id 广告id
	 * @param width 限定宽(px)
	 * @param height 限定高(px)
	 * @param imgName 保存图片名
	 * @return
	 * @throws Exception
	 * @creationDate. 2015-12-18 下午6:04:22
	 */
	private String upload(String img, File imgFile, String bannerImgFileName, String id, int width, int height, String imgName) throws Exception {
		if(!StringUtils.isBlank(img)){
			//删除原有图片
			FileSystemUtil.deleteFile(img);
		}
		
		String ext = FileUtil.getExtensionName(bannerImgFileName);
		String fileName = imgName +"_"+ System.currentTimeMillis() +"."+ext;
		//${年}/${月}/banner/picture/${bannerId}/img.**(原图)
		String path = StorePathUtil.buildPath(id, "banner", fileName).toString();
		//更改为不限制图片宽高
//		try {
//			//获取图片宽高
//			BufferedImage bufferedImage = ImageIO.read(imgFile);   
//			int w = bufferedImage.getWidth();   
//			int h = bufferedImage.getHeight();
//			if(imgName.equals("bigImg")){
//				//判断宽高
//				if(w != width || h != height){
//					return "图片宽高不符合要求";
//				}
//			}
//		} catch (Exception e) {
//			return "图片上传错误";
//		}
		
		FileSystemUtil.saveFile(imgFile, path);
		
		if(imgName.equals("img")){
			//设置小图保存地址
			ywBanner.setImg(path);
		}
		if(imgName.equals("bigImg")){
			//设置大图保存地址
			ywBanner.setBigImg(path);
		}
		return null;
	}

	public YwBanner getEntity() {
		return entity;
	}

	public void setEntity(YwBanner entity) {
		this.entity = entity;
	}

	public YwBanner getYwBanner() {
		return ywBanner;
	}

	public void setYwBanner(YwBanner ywBanner) {
		this.ywBanner = ywBanner;
	}

	public File getBannerImg() {
		return bannerImg;
	}

	public void setBannerImg(File bannerImg) {
		this.bannerImg = bannerImg;
	}

	public String getBannerImgFileName() {
		return bannerImgFileName;
	}

	public void setBannerImgFileName(String bannerImgFileName) {
		this.bannerImgFileName = bannerImgFileName;
	}

	public String getBannerImgContentType() {
		return bannerImgContentType;
	}

	public void setBannerImgContentType(String bannerImgContentType) {
		this.bannerImgContentType = bannerImgContentType;
	}

	public File getBannerBigImg() {
		return bannerBigImg;
	}

	public void setBannerBigImg(File bannerBigImg) {
		this.bannerBigImg = bannerBigImg;
	}

	public String getBannerBigImgFileName() {
		return bannerBigImgFileName;
	}

	public void setBannerBigImgFileName(String bannerBigImgFileName) {
		this.bannerBigImgFileName = bannerBigImgFileName;
	}

	public String getBannerBigImgContentType() {
		return bannerBigImgContentType;
	}

	public void setBannerBigImgContentType(String bannerBigImgContentType) {
		this.bannerBigImgContentType = bannerBigImgContentType;
	}
}
