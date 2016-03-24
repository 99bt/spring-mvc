package com.yaowang.util.freemark;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yaowang.entity.SysOption;
import com.yaowang.service.SysMcodeDetailService;
import com.yaowang.service.SysOptionService;
import com.yaowang.util.spring.ContainerManager;
/**
 * 
 * @author Administrator
 *
 */
public class AppSetting {
    static Logger logger = LoggerFactory.getLogger(AppSetting.class);
    private static AppSetting appSetting = new AppSetting();
    
    public static AppSetting getInstance(){
        return appSetting;
    }
    
    protected AppSetting() {
        logger.info(">>>>>>>>>初始化AppSetting...");
    }
    
    /**
     * 取得一个指定系统参数
     * @param mcodeid
     * @param mDetailId
     * @return
     */
    public String getOption(String iniid){
        SysOptionService optionService = (SysOptionService)ContainerManager.getComponent("sysOptionService");
        if(optionService==null) return null;
        SysOption option = optionService.getSysOptionByIniid(iniid);
        if (option == null) {
			return null;
		}
		return option.getNowvalue();
    }
    
    /**
     * 取得置顶系统参数备注
     * @param mcodeid
     * @param mDetailId
     * @return
     */
    public String getDescription(String iniid){
        SysOptionService optionService = (SysOptionService)ContainerManager.getComponent("sysOptionService");
        if(optionService==null) return null;
        return optionService.getSysOptionByIniid(iniid).getDescription();
    }
    
    /**
     * 获取数据字典的内容
     * @param mcodeType
     * @param thisId
     * @return
     */
    public String getMcodeContent(String mcodeType, String thisId){
    	if (StringUtils.isEmpty(thisId)) {
			return "";
		}
    	String result="";
    	SysMcodeDetailService mcodeDetailService = (SysMcodeDetailService)ContainerManager.getComponent("sysMcodeDetailService");
        if(mcodeDetailService==null) return result;
        Map<String, String> mCodeMap = mcodeDetailService.getMcodeThisIdToContent(mcodeType);
        if (mCodeMap == null || mCodeMap.isEmpty()) {
			return "";
		}
        result = mCodeMap.get(thisId.trim());
        return result==null?"":result;
    }
    
    /**
     * 获取直播地址
     * @param id
     * @return
     */
    public String getLivePath(Object id){
    	return getLivePathStatic(id);
    }
    
    public static String getLivePathStatic(Object id){
//    	if (ResourcesLoad.getDevMode()) {
//    		//开发环境
//    		return "/live/" + id + ".html";
//		}else {
			return "/" + id;
//		}
    }
}
