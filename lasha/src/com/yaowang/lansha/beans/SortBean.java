package com.yaowang.lansha.beans;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-3-4
 * Time: 下午6:56
 * To change this template use File | Settings | File Templates.
 */
public class SortBean {
    private String userId;
    private Integer number;
    private Integer ranking;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
}
