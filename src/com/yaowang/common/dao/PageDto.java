package com.yaowang.common.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class PageDto implements Serializable {
    private static final long serialVersionUID = 5883636778638787974L;
    private int totalPage = 1; // 总页数
    private int totalNum = 0; // 总记录数
    private int rowNum = 15; // 每页行数
    private int currentPage = 1; // 当前页码
    private boolean isCount = true;//是否要查询总数
    private int pageType = 2;//分页方式

    private int selectRows = 0;// 当前查询获得的数据行数

    private String functionName; // 方法名字
    

    /**
     * 得到从数据库查询时记录的起始行号，注意从1开始计算
     * 
     * @return
     */
    public int getStartIndex() {
        if (getCurrentPage() <= 0) {
            return 1;
        }

        return getRowNum() * (getCurrentPage() - 1) + 1;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage == 0) {
            this.currentPage = 1;
        }
        else {
            this.currentPage = currentPage;
        }
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getTotalPage() {
        if (totalNum != 0 || rowNum != 0) {
            totalPage = totalNum / rowNum;
            if (totalNum % rowNum != 0) {
                totalPage++;
            }
        }
        if (totalPage == 0) {
        	totalPage = 1;
		}
        return totalPage;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getSelectRows() {
        return selectRows;
    }

    public void setSelectRows(int selectRows) {
        this.selectRows = selectRows;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

	public boolean isCount() {
		return isCount;
	}

	public void setCount(boolean isCount) {
		this.isCount = isCount;
	}

	public int getPageType() {
		return pageType;
	}

	public void setPageType(int pageType) {
		this.pageType = pageType;
	}

}
