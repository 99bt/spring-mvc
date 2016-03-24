package com.yaowang.common.action;

import com.yaowang.common.dao.PageDto;

/**
 * 分页
 * @author shenl
 *
 */
public class BasePageAction extends BaseDataAction {
	private static final long serialVersionUID = 1L;

	protected Integer pageIndex;
	/**
	 * 分页对象
	 */
	private PageDto page = null;
	/**
	 * 获取分页对象
	 * @return
	 */
	public PageDto getPageDto(){
		if (page == null) {
			page = new PageDto();
		}
		return page;
	}
	/**
	 * 设置当前页数
	 * @param pageIndex
	 */
	public void setPageIndex(Integer pageIndex){
		if(pageIndex == null){
			pageIndex = 1;
		}
		getPageDto().setCurrentPage(pageIndex);
	}
	/**
	 * 获取当前页数
	 * @return
	 */
	public Integer getPageIndex(){
		return getPageDto().getCurrentPage();
	}
}
