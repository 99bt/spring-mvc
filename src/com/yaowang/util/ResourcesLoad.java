package com.yaowang.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
/**
 * 自动加载配置文件类.能够分开发环境和部署环境两种
 * 开发环境能够做到定时刷新里面的值
 * @author shenl
 *
 */
public class ResourcesLoad {
	public static final ResourcePatternResolver resoler = new PathMatchingResourcePatternResolver();//spring资源文件加载

	private static Boolean devMode = false;//是否在开发模式(外网部署设置成false)
	private final Map<String, String> map = new ConcurrentHashMap<String, String>();//存放对应属性值
	private Resource[] resources;
	private String url;
	private ResourcesLoad(){}
	
	static{
		//加载struts配置文件中.是否为开发模式参数
		try {
			Resource[] resource = resoler.getResources("classpath*:struts.properties");
			if (resource!=null && resource.length>0) {
				Map<String, String> map = new HashMap<String, String>(); 
				readProperties(map, resource[0]);
				String mode = map.get("struts.devMode");
				if (StringUtils.isNotBlank(mode)) {
					devMode = Boolean.valueOf(mode);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化
	 * @param url
	 * @return
	 */
	public static Map<String, String> load(String url) {
		ResourcesLoad load = new ResourcesLoad();
		load.url = url;
		try {
			load.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//是否开发模式
		if (devMode) {
			ReadPropertyThread.init(load, load.resources);
		}
		return load.map;
	}
	/**
	 * 初始化
	 * @param url
	 * @return
	 */
	public static Properties loadProperties(String url){
		Map<String, String> map = load(url);
		Properties properties = new Properties();
		properties.putAll(map);
		return properties;
	}
	
	/**
	 * 初始化语法
	 * @throws IOException
	 */
	private void init() throws IOException {
		map.clear();
		this.resources = resoler.getResources(url);
		
		for (Resource resource : resources) {
			readProperties(map, resource);
		}
	}
	
	/**
	 * 读取配置文件
	 * @param in
	 * @return
	 * @throws IOException 
	 * @throws  
	 */
	public static Map<String, String> readProperties(Map<String, String> map, Resource resource) throws IOException {
		InputStream in = resource.getInputStream();
		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(in, "utf-8"));
		}finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Enumeration<Object> it = (Enumeration<Object>) properties.keys();
		while (it.hasMoreElements()) {
			String key = (String)it.nextElement();
			String t = (String) properties.get(key);
			if (map.containsKey(key)) {
				if(devMode)
					System.err.println("注意:key值\"" + key + "\"有重复! " + resource.getDescription());
			}else {
				map.put(key.trim(), t);
			}
		}
		
		return map;
	}
	

	/**
	 * 配置文件重新加载类(采用后台守护线程的方式)
	 * @author shenl
	 *
	 */
	static class ReadPropertyThread {
		private Map<String, Long> fileTime = new HashMap<String, Long>();
		private ResourcesLoad load;
		private Resource[] resources;
		
		private ReadPropertyThread(){}
		
		/**
		 * 启动线程
		 */
		private static void init(ResourcesLoad load, Resource[] resources){
			final ReadPropertyThread thread = new ReadPropertyThread();
			thread.load = load;
			thread.resources = resources;
			
			TimerUtil.schedule(new TimerTask(){
	            @Override
	            public void run() {
	                try {
	                    thread.isDevlep();
	                } catch (FileNotFoundException e) {
	                    //文件找不到直接退出
	                    cancel();
                    } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }, 1000, 5 * 1000, "资源文件监控线程：" + thread.load.url);
		}
		
		/**
		 * 是否开发模式
		 * @param resources
		 * @throws IOException
		 */
		private void isDevlep() throws IOException{
			Boolean isReadLoad = false;
			for (Resource resource : resources) {
				File file = resource.getFile();
				String key = file.getAbsolutePath();
				Long time = fileTime.get(key);
				if (time == null) {
					fileTime.put(key, file.lastModified());
				}else if (time < file.lastModified()){
					fileTime.put(key, file.lastModified());
					System.err.println("重新加载配置文件:"+file.getAbsolutePath());
					isReadLoad = true;
				}
			}
			if (isReadLoad) {
				load.init();
			}
		}
	}

    public static Boolean getDevMode() {
        return devMode;
    }
}