/* 
 * @(#)UUIDUtils.java    Created on 2006-12-15
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/util/UUIDUtils.java,v 1.3 2007/01/11 01:17:24 liangxiao Exp $
 */
package com.yaowang.util;

import java.util.UUID;
/**
 * GUID生成工具
 * @author shenl
 *
 */
public final class UUIDUtils {
    
    public static final String newId(){
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
    
    public static void main(String[] args) {
        System.out.println(newId());
    }
}
