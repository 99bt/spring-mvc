package com.yaowang.common.action;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yaowang.common.util.LogUtil;
import com.yaowang.service.SysMcodeDetailService;
import com.yaowang.service.SysOptionService;
import com.yaowang.service.impl.SysOptionServiceImpl;
import com.yaowang.util.DateUtils;
import com.yaowang.util.JudgeIsMoblie;
import com.yaowang.util.json.JsonUtil;

public class BaseDataAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    @Resource
    protected SysOptionService sysOptionService;
    @Resource
    protected SysMcodeDetailService sysMcodeDetailService;

    /**
     * 空数据
     */
    protected static final String EMPTY_ARRAY = "{\"status\": 1, \"data\": []}";
    protected static final String EMPTY_ENTITY = "{\"status\": 1, \"data\": {}}";
    protected static final String EMPTY_STRING = "{\"status\": 1, \"data\": \"\"}";

    /**
     * 错误消息
     *
     * @param failed
     * @return
     */
    public static String getFailed(String... failed) {
        StringBuilder builder = new StringBuilder("{\"status\": 0, \"failed\": \"");
        for (String string : failed) {
            builder.append(string);
        }
        builder.append("\"}");
        return builder.toString();
    }

    /**
     * 错误消息
     *
     * @param failed
     * @return
     */
    public static String getErrorMsg(String... msg) {
        StringBuilder builder = new StringBuilder("{\"status\": 0, \"msg\": \"");
        for (String string : msg) {
            builder.append(string);
        }
        builder.append("\"}");
        return builder.toString();
    }

    /**
     * 系统错误
     *
     * @param failed
     * @return
     */
    public static String getError(String... failed) {
        StringBuilder builder = new StringBuilder("{\"status\": -1, \"failed\": \"");
        for (String string : failed) {
            builder.append(string);
        }
        builder.append("\"}");
        return builder.toString();
    }

    /**
     * 错误消息，返回登录
     *
     * @param failed
     * @return
     */
    protected String getFailedLogin() {
        return getFailedLogin("登录超时");
    }

    protected String getFailedLogin(String... failed) {
        StringBuilder builder = new StringBuilder("{\"status\": -2, \"failed\": \"");
        for (String string : failed) {
            builder.append(string);
        }
        builder.append("\"}");
        return builder.toString();
    }

    /**
     * 主键id
     */
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected Object object;

    public Object getObject() {
        return object;
    }

    /**
     * 主键id数组
     */
    protected String[] ids;

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    /**
     * 列表
     */
    protected List<?> list;

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    /**
     * 开始时间
     */
    protected Date startTime;
    /**
     * 结束时间
     */
    protected Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = DateUtils.getStartDate(startTime);
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = DateUtils.getEndDate(endTime);
    }

    /**
     * 默认设置时间为7天,截止到昨天
     */
    protected void setDefaultTime() {
        if (null == startTime && null == endTime) {
            // 设置当前搜索日期
            Date now = getNow();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);

            calendar.add(Calendar.DAY_OF_MONTH, -1);
            endTime = DateUtils.getStartDate(calendar.getTime());

            calendar.add(Calendar.MONTH, -1);
            startTime = DateUtils.getStartDate(calendar.getTime());

        }
    }

    /**
     * 名称
     */
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取静态文件地址
     *
     * @return
     */
    public String getStaticFilePath(String path) {
        if (StringUtils.isBlank(path)) {
            return "";
        } else {
            if (isHttpPath(path)) {
                return path;
            } else {
                String staticPath = getStaticPath();
                if (isHttpPath(staticPath)) {
                    return staticPath + path + getStaticVersion();
                } else {
                    return getHostPath() + staticPath + path + getStaticVersion();
                }
            }
        }
    }

    /**
     * 获取静态文件域名
     *
     * @return
     */
    private String host;

    public String getStaticPath() {
        if (StringUtils.isNotBlank(host)) {
            return host;
        }
        host = SysOptionServiceImpl.getValue("SYS.CDN.HOST.PATH");
        if (StringUtils.isEmpty(host)) {
            return getContextPath();
        } else {
            return host;
        }
    }

    /**
     * js静态文件路径
     *
     * @return
     */
    public String getJsPath() {
        return getContextPath();
    }

    /**
     * 静态文件版本
     *
     * @return
     */
    private String staticVersion;

    public String getStaticVersion() {
        if (StringUtils.isNotBlank(staticVersion)) {
            return staticVersion;
        }
        staticVersion = SysOptionServiceImpl.getValue("SYS.STATIC.VERSION");
        return staticVersion;
    }

    /**
     * 获取上传文件路径
     *
     * @return
     */
    private String store;

    public String getUploadPath() {
        if (StringUtils.isNotBlank(store)) {
            return store;
        }
        store = SysOptionServiceImpl.getValue("SYS.UPLOAD.HOST.PATH");
        if (StringUtils.isEmpty(store)) {
            store = "";
        }
//		store += getStore();
        return store;
    }

    public String getStore() {
        return "/store";
    }

    /**
     * 获取上传文件的http路径
     *
     * @param path
     * @return
     */
    public String getUploadFilePath(String path) {
        if (StringUtils.isBlank(path)) {
            return "";
        } else {
            if (isHttpPath(path)) {
                return path;
            } else {
                String store = getUploadPath();
                //判断store中是否已经带有域名
                if (isHttpPath(store)) {
                    return store + path;
                } else {
                    return getHostPath() + store + path;
                }
            }
        }
    }

    /**
     * 上下文
     *
     * @return
     */
    public String getContextPath() {
        return getContextPathStatic();
    }

    public static String getContextPathStatic() {
        return getRequest().getContextPath();
    }

    /**
     * 当前时间
     */
    private Date now = null;

    public Date getNow() {
        if (now == null) {
            now = new Date();
        }
        return now;
    }

    /**
     * 往客户端写数据
     */
    public void write(Object... strs) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (Object str : strs) {
            builder.append(str);
        }
        write(builder.toString());
    }

    /**
     * 往客户端写数据
     */
    public static void write(String str) throws IOException {
        ServletResponse response = getResponse();
        response.setCharacterEncoding(getRequest().getCharacterEncoding());
        response.setContentType("text/html");
        Writer writer = response.getWriter();
        try {
            //unicode转码
            if (str != null) {
                writer.write(str);
                LogUtil.log("SERVER_RESPONSE", str);
            }
        } finally {
            writer.flush();
            writer.close();
        }
    }

    /**
     * 往客户端写数据(不记日志)
     */
    public void writeNoLog(Object... strs) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (Object str : strs) {
            builder.append(str);
        }
        writeNoLog(builder.toString());
    }

    /**
     * 往客户端写数据(不记日志)
     */
    public static void writeNoLog(String str) throws IOException {
        ServletResponse response = getResponse();
        response.setCharacterEncoding(getRequest().getCharacterEncoding());
        response.setContentType("text/html");
        Writer writer = response.getWriter();
        try {
            //unicode转码
            if (str != null) {
                writer.write(str);
            }
        } finally {
            writer.flush();
            writer.close();
        }
    }

    protected Writer writeNotClose(String str) throws IOException {
        ServletResponse response = getResponse();
        response.setCharacterEncoding(getRequest().getCharacterEncoding());
        Writer writer = response.getWriter();
        try {
            writer.write(str);
        } finally {
            writer.flush();
        }
        return writer;
    }

    /**
     * 按照jsone格式输出返回
     *
     * @param entity
     * @throws IOException
     */
    public void writeJson(Object... entity) throws IOException {
        if (entity == null) {
            write("[]");
        } else {
            String json = JsonUtil.toJSONString(entity);

            write(json);
        }
    }

    /**
     * 对象返回
     *
     * @param entity
     * @throws IOException
     */
    public void writerEntity(Map<String, Object> map) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (String key : map.keySet()) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append("\"").append(key).append("\": ");
            Object object = map.get(key) == null ? "" : map.get(key);
            boolean b = (object instanceof String);
            if (b) {
                builder.append(JSON.toJSONString(object));
            } else {
                if (object instanceof Integer || object instanceof Long || object instanceof Double || object instanceof Float) {
                    String objStr = String.valueOf(object);
                    if (StringUtils.isBlank(objStr)) {
                        builder.append("0");
                    } else {
                        builder.append(JSON.toJSONString(objStr));
                    }
                } else {
                    builder.append(JsonUtil.toJSONString(object));
                }
            }
        }
        builder.insert(0, "{\"status\": 1, \"data\": {");
        builder.append("}}");
        map.clear();
        map = null;
        write(builder.toString());
    }

    /**
     * 获取ip地址,防止集群、代理
     *
     * @return
     */
    public String getClientIP() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip.split(",")[0].trim();
        }
        ip = request.getHeader("Proxy-Client-IP");

        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取名称
     *
     * @return
     */
    private String platFormName;

    public String getPlatFormName() {
        if (StringUtils.isNotBlank(platFormName)) {
            return platFormName;
        }
        platFormName = getPlatFormNameStatic();
        return platFormName;
    }

    public static String getPlatFormNameStatic() {
        return SysOptionServiceImpl.getValue("SYS.NAME");
    }

    /**
     * 获取网站地址
     *
     * @return
     */
    private String hostPath;

    public String getHostPath() {
        if (StringUtils.isNotBlank(hostPath)) {
            return hostPath;
        }
        hostPath = getHostPathStatic();
        return hostPath;
    }

    public static String getHostPathStatic() {
        return SysOptionServiceImpl.getValue("SYS.HOST.PATH");
    }

    public String getHostContextPath(String url) {
        return getHostPath() + getContextPath() + url;
    }

    public static String getHostContextPathStatic() {
        return getHostPathStatic() + getContextPathStatic();
    }
    
    public String getCookie(String name){
    	Cookie[] cookies = getRequest().getCookies();
    	if(cookies!=null){
    		for (Cookie cookie : cookies) {
    			if (name.equals(cookie.getName())) {
    				return cookie.getValue();
    			}
    		}
    	}		
		return "";
    }

    /**
     * 获取本地临时文件夹
     *
     * @return
     */
    public String getLocalTempPath() {
        return System.getProperty("java.io.tmpdir") + "/lansha/";
    }

    /**
     * 是否为http地址
     *
     * @param path
     * @return
     */
    public static boolean isHttpPath(String path) {
        return path.matches(".+\\://.+");
    }

    /**
     * 判断是否是wap
     *
     * @return
     */
    public Boolean getIsWap() {
        return JudgeIsMoblie.judgeIsMoblie(getRequest());
    }
}
