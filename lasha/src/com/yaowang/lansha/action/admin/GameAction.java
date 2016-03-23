package com.yaowang.lansha.action.admin;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.common.constant.BaseConstant;
import com.yaowang.lansha.common.constant.LanshaConstant;
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.entity.YwGameHot;
import com.yaowang.lansha.entity.YwGameType;
import com.yaowang.lansha.service.YwGameHotService;
import com.yaowang.lansha.service.YwGameService;
import com.yaowang.lansha.service.YwGameTypeService;
import com.yaowang.util.QRCodeUtil;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.filesystem.util.FileSystemUtil;
import com.yaowang.util.filesystem.util.FileUtil;
import com.yaowang.util.filesystem.util.StorePathUtil;
import com.yaowang.util.upload.UploadFile;
import com.yaowang.util.upload.UploadUtils;

/**
 * @ClassName: GameAction
 * @Description: 游戏维护
 * @author wanglp
 * @date 2015-12-4 下午2:03:04
 *
 */
public class GameAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private YwGameService ywGameService;
	@Resource
	private YwGameTypeService ywGameTypeService;
	@Resource
	private YwGameHotService ywGameHotService;
	private YwGame entity;
	private Integer hot;
	private Integer orderId;

	/**
	 * @Title: list
	 * @Description: 列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String list(){
		YwGame searchGame = new YwGame();
		searchGame.setName(name);
		list = ywGameService.getYwGamePage(searchGame, null, getPageDto(), startTime, endTime, new Integer[]{LanshaConstant.STATUS_ONLINE, LanshaConstant.STATUS_OFFLINE});
		ywGameService.setGame((List<YwGame>)list, true);
		return SUCCESS;
	}
	/**
	 * @Title: detail
	 * @Description: 详情
	 * @return
	 */
	public String info(){
		if(StringUtils.isNotBlank(id)){
			entity = ywGameService.getYwGameById(id);
		}
		return SUCCESS;
	}
	/**
	 * @Title: save
	 * @Description: 保存
	 * @return
	 */
	public String save(){
		try {
			//上传图片处理
			UploadFile[] files = UploadUtils.handleFileUpload();
			if (files != null) {
				for (UploadFile file : files) {
					if (!file.getContentType().matches("image/.+")) {
						addActionError("请上传正确的图片文件");
						return SUCCESS;
					}
				}
			}
			String errormsg = null;
			if(StringUtils.isBlank(entity.getId())){
				
				entity.setId(UUIDUtils.newId());
				errormsg = upload(files, entity);
				ywGameService.save(entity);
			}else{

                errormsg = upload(files, entity);
                ywGameService.update(entity);
            }
			
			if(StringUtils.isNotBlank(errormsg)){
				addActionError(errormsg+"其他信息保存成功");
				id = entity.getId();
				info();
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("保存失败");
			id = entity.getId();
			info();
			return ERROR;
		}
		
		addActionMessage("保存成功");
		list();
		return SUCCESS;
	}
	/**
	 * @Title: remove
	 * @Description: 删除
	 * @return
	 */
	public String remove(){
		ywGameService.delete(ids);
		list();
		addActionMessage("删除成功");
		return SUCCESS;
	}
	/**
	 * @Title: updateHot
	 * @Description: 推荐/取消推荐游戏
	 */
	public void updateHot(){
		if(BaseConstant.YES == hot){
			//推荐
			YwGame game = ywGameService.getYwGameById(id);
			//游戏上线可设置热门
			if(LanshaConstant.STATUS_ONLINE.equals(game.getStatus())){
				//判断是否已经是热门
				YwGameHot gameHot = ywGameHotService.getYwGameHotByGameId(id);
				if(gameHot == null){
					gameHot = new YwGameHot();
					gameHot.setGameId(id);
					gameHot.setOrderId(0);
					gameHot.setCreateTime(getNow());
					//保存
					ywGameHotService.save(gameHot);
				}
			}
		}else{
			//取消推荐
			ywGameHotService.deleteByGameId(id);
		}
	}
	
	/**
	 * @Title: hotList
	 * @Description: 推荐游戏列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String hotList(){
		list = ywGameHotService.getYwGameHotPage(null, getPageDto());
		ywGameHotService.setGame((List<YwGameHot>)list);
		return SUCCESS;
	}
	/**
	 * @Title: hotList
	 * @Description: 取消推荐
	 * @return
	 */
	public String hotRemove(){
		ywGameHotService.delete(new String[]{ id });
		hotList();
		addActionMessage("删除成功");
		return SUCCESS;
	}
	/**
	 * @Title: hotList
	 * @Description: 推荐游戏列表
	 * @return
	 */
	public void hotUpdateOrder(){
		ywGameHotService.updateOrderId(id, orderId);
	}
	
	/**
	 * 保存图片
	 * 
	 * @param banner
	 * @throws Exception
	 */
	private String upload(UploadFile[] files, YwGame game) throws Exception {
		StringBuffer errormsg = new StringBuffer();
		//游戏图标
		UploadFile file1 = null;
		//游戏图
		UploadFile file2 = null;
		//背景图
		UploadFile file3 = null;
		//游戏图（小）
		UploadFile file4 = null;

        UploadFile file5 = null;
//		//ios二维码
//		UploadFile file4 = null;
		//游戏截图
		List<UploadFile> screenFiles = new ArrayList<UploadFile>();
		
    		for (UploadFile file : files) {
			//获取图片宽高
			BufferedImage bufferedImage = ImageIO.read(file.getFile());   
			int width = bufferedImage.getWidth();   
			int height = bufferedImage.getHeight(); 
			
			if ("iconImg".equals(file.getFieldName())) {
//				//判断宽高
//				if(width != 100 || height != 100){
//					errormsg.append("游戏图标宽高不符合要求,");
//					continue ;
//				}
				file1 = file;
			}else if("advertImg".equals(file.getFieldName())){
				//判断宽高
				if(width != 380 || height != 190){
					errormsg.append("游戏图宽高不符合要求,");
					continue ;
				}
				file2 = file;
			}else if("backgroundImg".equals(file.getFieldName())){
				//判断宽高
				if(width != 1920 || height != 500){
					errormsg.append("游戏背景图宽高不符合要求,");
					continue ;
				}
				file3 = file;
			}else if("advertSmallImg".equals(file.getFieldName())){
//				//判断宽高
//				if(width != 139 || height != 155){
//					errormsg.append("游戏图(小)宽高不符合要求,");
//					continue ;
//				}
				file4 = file;
			}else if(file.getFieldName().startsWith("screenImg")){
				//判断宽高
//				if(width != 270 || height != 360){
//					errormsg.append("游戏截图宽高不符合要求,");
//					continue ;
//				}
				screenFiles.add(file);
			}
            else if(file.getFieldName().startsWith("mobileImage")){
//                //判断宽高
//                if(width != 270 || height != 360){
//                    errormsg.append("banner宽高不符合要求,");
//                    continue ;
//                }
                file5 = file;

            }


        }
		
		long time = System.currentTimeMillis();
		String ext;
		String filePath;
		//游戏图标
		if(file1 != null){
			ext = FileUtil.getExtensionName(file1.getFileName());
			//文件地址 年/月/日/game/game_id/icon.
			filePath = StorePathUtil.buildPath(game.getId(), "game", "icon_"+ time +"." + ext).toString();
			FileSystemUtil.saveFile(file1.getFile(), filePath);
			game.setIcon(filePath);
		}
		//游戏图
		if(file2 != null){
			ext = FileUtil.getExtensionName(file2.getFileName());
			//文件地址 年/月/日/game/game_id/advert.
			filePath = StorePathUtil.buildPath(game.getId(), "game", "game_"+ time +"." + ext).toString();
			FileSystemUtil.saveFile(file2.getFile(), filePath);
			game.setAdvert(filePath);
		}
		//游戏图(小)
		if(file4 != null){
			ext = FileUtil.getExtensionName(file4.getFileName());
			//文件地址 年/月/日/game/game_id/advert_small.
			filePath = StorePathUtil.buildPath(game.getId(), "game", "game_small_"+ time +"." + ext).toString();
			FileSystemUtil.saveFile(file4.getFile(), filePath);
			game.setAdvertSmall(filePath);
		}
		//游戏背景图
		if(file3 != null){
			ext = FileUtil.getExtensionName(file3.getFileName());
			//文件地址 年/月/日/game/game_id/background.
			filePath = StorePathUtil.buildPath(game.getId(), "game", "background_"+ time +"." + ext).toString();
			FileSystemUtil.saveFile(file3.getFile(), filePath);
			game.setBackground(filePath);
		}

        if(file5 != null){
            ext = FileUtil.getExtensionName(file5.getFileName());
            //文件地址 年/月/日/game/game_id/background.
            filePath = StorePathUtil.buildPath(game.getId(), "game", "mobile_banner_"+ time +"." + ext).toString();
            FileSystemUtil.saveFile(file5.getFile(), filePath);
            game.setMobileBanner(filePath);
        }
		//二维码（如果已经生成过就不再修改）
		//if(StringUtils.isBlank(game.getQrcode())){
			String url = getHostContextPath("/download.html?id="+game.getId());
//			String url = "/download.html?id="+game.getId();
			filePath = StorePathUtil.buildPath(game.getId(), "game", "qrcode_"+ time +".jpg").toString();
			//生成二维码并保存到指定路径
			QRCodeUtil.encode(url, filePath);
			game.setQrcode(filePath);
		//}
		//游戏截图
		StringBuffer path = new StringBuffer();
		//原截图图片地址
		if(ArrayUtils.isNotEmpty(game.getScreens())){
			String[] screens = game.getScreens();
			for (int i = 0; i < screens.length; i++) {
				path.append(screens[i]).append(",");
			}
		}
		//新上传截图
        int i=0;
		if(CollectionUtils.isNotEmpty(screenFiles)){
			for(UploadFile file : screenFiles){
                time=time+i;
				ext = FileUtil.getExtensionName(file.getFileName());
				//文件地址 年/月/日/game/game_id/screen_时间戳.
				filePath = StorePathUtil.buildPath(game.getId(), "game", "screen_"+ time +"." + ext).toString();
				FileSystemUtil.saveFile(file.getFile(), filePath);
				path.append(filePath).append(",");
                i++;
			}
			
		}
		//重新组装图片截图路径
		if(path.length() > 0){
			path.setLength(path.length() - 1);
		}
		game.setScreen(path.toString());
		
		return errormsg.toString();
	}
	
	public List<YwGameType> getTypeList() {
		YwGameType searchType = new YwGameType();
		searchType.setStatus("1");
		return ywGameTypeService.getYwGameTypeList(searchType, null);
	}
	public YwGame getEntity() {
		return entity;
	}
	public void setEntity(YwGame entity) {
		this.entity = entity;
	}
	public void setHot(Integer hot) {
		this.hot = hot;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
}
