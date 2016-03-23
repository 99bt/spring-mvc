package com.yaowang.lansha.action.admin;

import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-2-29
 * Time: 上午11:21
 * To change this template use File | Settings | File Templates.
 */
public class CourseEntity implements Serializable {

    @Excel(name = "qq")
    private String qq;

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
