package com.yaowang.util.json;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
    /**
     * 转换成json格式
     * @param entity
     * @return
     */
    public static String toJSONString(Object...entity){
        String json = JSON.toJSONString(entity);
        if (json.startsWith("[[")){
            json = json.substring(1);
        }
        
        if (json.endsWith("]]")){
            json = json.substring(0, json.length()-1);
        }
        return json;
    }
}
