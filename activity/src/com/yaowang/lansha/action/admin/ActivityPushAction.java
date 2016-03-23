package com.yaowang.lansha.action.admin;

import com.yaowang.lansha.common.action.LanshaBaseAction;
import com.yaowang.lansha.entity.LanshaActivityPush;
import com.yaowang.lansha.entity.YwGame;
import com.yaowang.lansha.service.LanshaActivityPushService;
import com.yaowang.util.QRCodeUtil;
import com.yaowang.util.UUIDUtils;
import com.yaowang.util.filesystem.util.FileSystemUtil;
import com.yaowang.util.filesystem.util.FileUtil;
import com.yaowang.util.filesystem.util.StorePathUtil;
import com.yaowang.util.upload.UploadFile;
import com.yaowang.util.upload.UploadUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-3-18
 * Time: 下午1:42
 * To change this template use File | Settings | File Templates.
 */
public class ActivityPushAction extends LanshaBaseAction {
    @Resource
    private LanshaActivityPushService lanshaActivityPushService;

    private LanshaActivityPush entity;

    private String title;

    public String list() {
        entity=new LanshaActivityPush();
        if(StringUtils.isNotEmpty(title))
        {
            entity.setTitle(title);
        }

       list =lanshaActivityPushService.getLanshaActivityPushPage(entity,getPageDto());
       return SUCCESS;
    }

    public String detail() {
        if (StringUtils.isNotBlank(id)) {
            entity = lanshaActivityPushService.getLanshaActivityPushById(id);
        }
        return SUCCESS;
    }
    public String delete() {
        if(lanshaActivityPushService.delete(ids)<0){
            addActionMessage("删除失败");
        }else{
            addActionMessage("删除成功");
        }
        list();
        return SUCCESS;
    }


    public String save() throws Exception {
        try {
            UploadFile[] files = UploadUtils.handleFileUpload();
            for (UploadFile file : files) {
                if (!file.getContentType().matches("image/.+")) {
                    addActionError("请上传正确的图片文件");
                    return SUCCESS;
                }
            }
            String errormsg = null;
            if (StringUtils.isBlank(entity.getId())) {
                entity.setId(UUIDUtils.newId());
                errormsg = upload(files, entity);
                entity.setCreateTime(getNow());
                lanshaActivityPushService.save(entity);
            } else {

                errormsg = upload(files, entity);
                lanshaActivityPushService.update(entity);
            }

            if (StringUtils.isNotBlank(errormsg)) {
                addActionError(errormsg + "其他信息保存成功");
                id = entity.getId();
                detail();
                return ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("保存失败");
            id = entity.getId();
            detail();
            return ERROR;
        }

        addActionMessage("保存成功");
        list();
        return SUCCESS;

    }

    /**
     * 保存图片
     *
     * @throws Exception
     */
    private String upload(UploadFile[] files, LanshaActivityPush lanshaActivityPush) throws Exception {
        StringBuffer errormsg = new StringBuffer();
        //图片
        UploadFile file1 = null;

        //游戏截图
        List<UploadFile> screenFiles = new ArrayList<UploadFile>();

        for (UploadFile file : files) {
            //获取图片宽高
            BufferedImage bufferedImage = ImageIO.read(file.getFile());
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();


            file1 = file;


        }

        long time = System.currentTimeMillis();
        String ext;
        String filePath;
        //游戏图标
        if (file1 != null) {
            ext = FileUtil.getExtensionName(file1.getFileName());
            //文件地址 年/月/日/game/game_id/icon.
            filePath = StorePathUtil.buildPath(lanshaActivityPush.getId(), "activity", "push_" + time + "." + ext).toString();
            FileSystemUtil.saveFile(file1.getFile(), filePath);
            lanshaActivityPush.setIndexImg(filePath);
        }


        return errormsg.toString();
    }


    public LanshaActivityPush getEntity() {
        return entity;
    }

    public void setEntity(LanshaActivityPush entity) {
        this.entity = entity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
