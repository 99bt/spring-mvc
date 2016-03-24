package com.yaowang.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.entity.SysOption;
import com.yaowang.entity.SysOptionType;
import com.yaowang.service.SysOptionService;
import com.yaowang.service.SysOptionTypeService;
import com.yaowang.util.DateUtils;
import com.yaowang.util.spring.ContainerManager;
/**
 * 系统管理
 * @author shenl
 *
 */
public class SystemAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	
	private SysOption entity;
	private String typeId;
	private List<SysOptionType> types;
	
	@Resource
	private SysOptionService sysOptionService;
	@Resource
	private SysOptionTypeService sysOptionTypeService;

	/**
	 * 系统管理
	 * @return
	 */
	public String system(){
		//分类
		types = sysOptionTypeService.getSysOptionTypeList();
		if (StringUtils.isEmpty(typeId) && !types.isEmpty()) {
			typeId = types.get(0).getId();
		}
		
		list = sysOptionService.getSysOptionPage(typeId, getPageDto(), null);
		return SUCCESS;
	}
	/**
	 * 显示
	 * @return
	 */
	public String view(){
		entity = sysOptionService.getSysOptionById(id);
		return SUCCESS;
	}
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		sysOptionService.update(entity);
		addActionMessage("保存成功！");
		return SUCCESS;
	}
	/**
	 * 分类
	 * @return
	 */
	public List<SysOptionType> getTypes(){
		return types;
	}
	
	/**
	 * 数据库连接池
	 * @return
	 */
	public String database(){
		object = (DataSource) ContainerManager.getComponent("dataSource");
		List<String> info = new ArrayList<String>();
		
		if (object == null) {
			info.add("dataSource不存在");
		}else if(object instanceof BasicDataSource){
			BasicDataSource dataSource = (BasicDataSource)object;
			info.add("DefaultAutoCommit:" + dataSource.getDefaultAutoCommit());
			info.add("DefaultTransactionIsolation:" + dataSource.getDefaultTransactionIsolation());
			
			info.add("<label style='color:red'>最大活动线程(MaxActive)</label>:" + dataSource.getMaxActive());
			info.add("InitialSize:" + dataSource.getInitialSize());
			info.add("MaxIdle:" + dataSource.getMaxIdle());
			info.add("MaxWait:" + dataSource.getMaxWait());
			info.add("MinIdle:" + dataSource.getMinIdle());
			info.add("当前空闲线程(NumIdle):" + dataSource.getNumIdle());
			info.add("<label style='color:red'>当前活动线程(NumActive)</label>:" + dataSource.getNumActive());
			info.add("TestOnBorrow:" + dataSource.getTestOnBorrow());
			info.add("Url:" + dataSource.getUrl());
			info.add("ValidationQuery:" + dataSource.getValidationQuery());
			info.add("RemoveAbandoned:" + dataSource.getRemoveAbandoned());
			info.add("RemoveAbandonedTimeout:" + dataSource.getRemoveAbandonedTimeout());
			info.add("LogAbandoned:" + dataSource.getLogAbandoned());
			info.add("Time:" + DateUtils.formatHms(getNow()));
		}
		list = info;
		
		return SUCCESS;
	}
	
	public SysOption getEntity() {
		return entity;
	}

	public void setEntity(SysOption entity) {
		this.entity = entity;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
}
