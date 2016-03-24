package com.yaowang.action.admin;

import com.yaowang.common.action.BasePageAction;
import com.yaowang.util.DateUtils;
import com.yaowang.util.cache.DefaultCacheManager;
import com.yaowang.util.email.EmailUtil;
import com.yaowang.util.mt.MTLanchuangUtil;
import com.yaowang.util.mt.MTMaychooUtil;
import com.yaowang.util.mt.MTQixinUtil;
import com.yaowang.util.mt.MTUtil;
import com.yaowang.util.spring.ContainerManager;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

/**
 * 系统调用
 *
 * @author shenl
 */
public class SystemCallAction extends BasePageAction {
    private static final long serialVersionUID = 1L;

    private String dir;

    /**
     * 缓存刷新
     *
     * @return
     * @throws Exception
     */
    public String call() throws Exception {
        if (StringUtils.isEmpty(id)) {
            return SUCCESS;
        }
        if ("all-cache".equals(id)) {
            //刷新全部缓存
            DefaultCacheManager.getCacheService().clearAll();
            addActionMessage("刷新成功");
        } else if ("MT".equals(id)) {
            MTUtil.refresh();
            addActionMessage("已刷新");
        } else if ("EMAIL".equals(id)) {
            EmailUtil.refresh();
            addActionMessage("已刷新");
        } else if ("TICKET".equals(id)) {
            call("lanshaTicketService", "resort");
            addActionMessage("已刷新");
        } else if ("ROOM_RANKING".equals(id)) {
            if (startTime == null) {
                startTime = getNow();
            }
            if (endTime == null) {
                call("roomRankingService", "doReport", new Class<?>[]{Date.class},
                        new Object[]{DateUtils.getStartDate(startTime)});
            } else {
                //循环统计
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startTime);
                Date reportTime = calendar.getTime();

                endTime = DateUtils.getEndDate(endTime);
                while (reportTime.before(endTime)) {
                    call("roomRankingService", "doReport", new Class<?>[]{Date.class},
                            new Object[]{DateUtils.getStartDate(reportTime)});
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
                    reportTime = calendar.getTime();
                }
            }
            addActionMessage("已调用");
        }
        return SUCCESS;
    }

	/**
	 * 获取短信存量
	 * 
	 * @return
	 */
	public String getMtStock() {
		String str = null;
		try {
			str = MTQixinUtil.getStock().intValue() + "";
		} catch (Exception e) {
			str = "获取失败";
		}
		try {
			str += "," + MTLanchuangUtil.getStock().intValue();
		} catch (Exception e) {
			str += ",获取失败";
		}
		try {
			str += "," + MTMaychooUtil.getStock().intValue();
		} catch (Exception e) {
			str += ",获取失败";
		}
		return str;
	}

    /**
     * 反射调用
     *
     * @param type
     * @param method
     * @throws Exception
     */
    private void call(String type, String method) throws Exception {
        Object service = ContainerManager.getComponent(type);

        Class<?> clazz = service.getClass();
        // 反射调用方法
        Method m2 = clazz.getMethod(method);
        m2.invoke(service);
    }

    /**
     * 反射调用
     *
     * @param type
     * @param method
     * @throws Exception
     */
    private void call(String type, String method, Class<?>[] cls, Object[] os)
            throws Exception {
        Object service = ContainerManager.getComponent(type);

        Class<?> clazz = service.getClass();
        // 反射调用方法
        Method m2 = clazz.getMethod(method, cls);
        m2.invoke(service, os);
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
