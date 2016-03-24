package com.yaowang.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

public class DateUtils {
	
	private static transient int gregorianCutoverYear = 1582;
	/** 闰年中每月天数 */
	private static final int[] DAYS_P_MONTH_LY = { 31, 29, 31, 30, 31, 30, 31,
			31, 30, 31, 30, 31 };

	/** 非闰年中每月天数 */
	private static final int[] DAYS_P_MONTH_CY = { 31, 28, 31, 30, 31, 30, 31,
			31, 30, 31, 30, 31 };
	
	/** 一天中小时数 */
	public static final String[] DAYS_P_HOUR_CY = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10"
		, "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23","24"};

	/** 代表数组里的年、月、日 */
	private static final int Y = 0, M = 1, D = 2;
	
	/**
     * 获取某天的起始时间, e.g. 2005-10-01 00:00:00.000
     * 
     * @param date
     *            日期对象
     * @return 该天的起始时间
     */
    public static Date getStartDate(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }
	/**
     * 获取某天的结束时间, e.g. 2005-10-01 23:59:59.999
     * 
     * @param date
     *            日期对象
     * @return 该天的结束时间
     */
    public static Date getEndDate(Date date) {

        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }
    /**
     * 格式化时间
     * @param time
     * @return
     */
    public static String format(Date time){
    	if (time == null) {
			return "";
		}
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	return format.format(time);
    }
    
    public static String formatHms(Date time){
    	if (time == null) {
			return "";
		}
    	SimpleDateFormat formatHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return formatHms.format(time);
    }
    /**
     * 格式化
     * @param time
     * @return
     */
    public static Date pasetime(String time){
    	try {
    		SimpleDateFormat formatHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return formatHms.parse(time);
		} catch (ParseException e) {
			return null;
		}
    }
    public static Date toDate(String time) {
		if (StringUtils.isEmpty(time)) {
			return null;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.parse(time);
		} catch (ParseException e) {
			return null;
		}catch (NumberFormatException e) {
			return null;
		}
	}
    /**
     * 天转毫秒
     * @return
     */
    public static Integer getDayToTime(Integer day){
    	return day * 24 * 60 * 60 * 1000;
    }
    
    public static Long dateDiff(Date startTime, Date endTime,  String format, String str) {   
        // 按照传入的格式生成一个simpledateformate对象    
        SimpleDateFormat sd = new SimpleDateFormat(format);    
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数    
        long nh = 1000 * 60 * 60;// 一小时的毫秒数    
        long nm = 1000 * 60;// 一分钟的毫秒数    
        long ns = 1000;// 一秒钟的毫秒数    
        long diff;    
        long day = 0;    
        long hour = 0;    
        long min = 0;    
        long sec = 0;    
        // 获得两个时间的毫秒时间差异    
        try {    
            diff = sd.parse(sd.format(endTime)).getTime() - sd.parse(sd.format(startTime)).getTime();    
            day = diff / nd;// 计算差多少天    
            hour = diff / nh;// 计算差多少小时    
            min = diff / nm;// 计算差多少分钟    
            sec = diff / ns;// 计算差多少秒    
        } catch (ParseException e) {    
            e.printStackTrace();    
        }    
        if (str.equalsIgnoreCase("d")) {    
            return day;    
        } else if (str.equalsIgnoreCase("h")) {    
            return hour;    
        } else if(str.equalsIgnoreCase("m")){    
            return min;    
        } else{
        	return sec;
        }
    }
    
    /**
     * 格式化时间
     * @param time
     * @return
     */
    public static String format(Date time, String formatStr){
    	if (time == null) {
			return "";
		}
    	SimpleDateFormat format = new SimpleDateFormat(formatStr);
    	return format.format(time);
    }
    /**
     * 获取当前时间加上指定时间
     * @param appointTime
     * @return
     */
    public static Date getValidTime(Integer appointTime){
    	if(appointTime == null){
    		appointTime = 30;
    	}
    	long validTime = appointTime * 60 * 1000;
    	long currentTime = System.currentTimeMillis() + validTime;
    	return new Date(currentTime);
    }
    
    /**
     * 获取给定日期减一天
     * @creationDate. 2015-10-9 下午3:30:37 
     * return String
     * @throws ParseException 
     */
    public static Date getDate(Date d) throws ParseException{
    	SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		date.setTime(d);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
		return dft.parse(dft.format(date.getTime()));
    }
    
    /**
     * 获取给定日期加上指定天数
     * @creationDate. 2015-10-9 下午3:30:37 
     * return String
     * @throws ParseException 
     */
    public static Date getPlusDate(Date d, int day) throws ParseException{
    	SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		date.setTime(d);
		date.set(Calendar.DATE, date.get(Calendar.DATE) + day);
		return dft.parse(dft.format(date.getTime()));
    }
    
    /**
     * @Title: compare
     * @Description: 比较时间
     * @param date1
     * @param date2
     * @return
     */
    public static int compare(Date date1, Date date2){
    	long datetime1 = date1.getTime();
    	long datetime2 = date2.getTime();
    	
    	return (datetime1 > datetime2) ? 1 : (datetime1 == datetime2) ? 0 : -1;
    }
    
	public static String secondToTime(int second) {
		int h = 0;
		int d = 0;
		int s = 0;
		int temp = second % 3600;
		if (second > 3600) {
			h = second / 3600;
			if (temp != 0) {
				if (temp > 60) {
					d = temp / 60;
					if (temp % 60 != 0) {
						s = temp % 60;
					}
				} else {
					s = temp;
				}
			}
		} else {
			d = second / 60;
			if (second % 60 != 0) {
				s = second % 60;
			}
		}

		return h + "时" + d + "分" + s + "秒";
	}
	
	
	/**
	 * 计算两个日期之间相隔的天数
	 * 
	 * @param begin
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	public static long countDay(String begin, String end) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate, endDate;
		long day = 0;
		try {
			beginDate = format.parse(begin);
			endDate = format.parse(end);
			day = (endDate.getTime() - beginDate.getTime())
					/ (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}
	
	/**
	 * 将代表日期的字符串分割为代表年月日的整形数组
	 * 
	 * @param date
	 * @return
	 */
	public static int[] splitYMD(String date) {
		date = date.replace("-", "");
		int[] ymd = { 0, 0, 0 };
		ymd[Y] = Integer.parseInt(date.substring(0, 4));
		ymd[M] = Integer.parseInt(date.substring(4, 6));
		ymd[D] = Integer.parseInt(date.substring(6, 8));
		return ymd;
	}
	
	/**
	 * 检查传入的参数代表的年份是否为闰年
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return year >= gregorianCutoverYear ? ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0)))
				: (year % 4 == 0);
	}
	
	/**
	 * 日期加1天
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	private static int[] addOneDay(int year, int month, int day) {
		if (isLeapYear(year)) {
			day++;
			if (day > DAYS_P_MONTH_LY[month - 1]) {
				month++;
				if (month > 12) {
					year++;
					month = 1;
				}
				day = 1;
			}
		} else {
			day++;
			if (day > DAYS_P_MONTH_CY[month - 1]) {
				month++;
				if (month > 12) {
					year++;
					month = 1;
				}
				day = 1;
			}
		}
		int[] ymd = { year, month, day };
		return ymd;
	}
	
	/**
	 * 将不足四位的年份补足为四位
	 * 
	 * @param decimal
	 * @return
	 */
	public static String formatYear(int decimal) {
		DecimalFormat df = new DecimalFormat("0000");
		return df.format(decimal);
	}
	
	/**
	 * 将不足两位的月份或日期补足为两位
	 * 
	 * @param decimal
	 * @return
	 */
	public static String formatMonthDay(int decimal) {
		DecimalFormat df = new DecimalFormat("00");
		return df.format(decimal);
	}
	
	/**
	 * 以循环的方式计算日期
	 * 
	 * @param beginDate
	 *            endDate
	 * @param
	 * @return
	 * @throws ParseException
	 */
	public static List<Date> getEveryday(String beginDate, String endDate)
			throws ParseException {
		long days = countDay(beginDate, endDate);
		int[] ymd = splitYMD(beginDate);
		List<String> everyDays = new ArrayList<String>();
		everyDays.add(beginDate);
		for (int i = 0; i < days; i++) {
			ymd = addOneDay(ymd[Y], ymd[M], ymd[D]);
			everyDays.add(formatYear(ymd[Y]) + "-" + formatMonthDay(ymd[M])
					+ "-" + formatMonthDay(ymd[D]));
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<Date> dates = new ArrayList<Date>();
		for (String s : everyDays) {
			Date d = format.parse(s);
			dates.add(d);
		}
		return dates;
	}
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        return cal;
    }
    /**
     * 获取当前时间前后几天
     *
     * @param delta 前后天数，支持正负数
     * @return
     */
    public static Date appointed(int delta) {
        Calendar cal = getCalendar(new Date());
        if (delta != 0) {
            cal.add(Calendar.DATE, delta);
        }
        return cal.getTime();
    }
    /**
     * 判断两日期是否为同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return Boolean.FALSE;
        }
        DateTime d1 = new DateTime(date1);
        DateTime d2 = new DateTime(date2);
        if (d1.dayOfMonth().get() == d2.dayOfMonth().get()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static Date getDateEnd(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.withTime(23, 59, 59, 999).toDate();
    }
    public static Date getDateStart(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.withTime(00, 00, 00, 00).toDate();
    }


    public static Date getFirstDay(int year,int month) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar calendar = Calendar.getInstance();
//        Date theDate = calendar.getTime();
//        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
//        gcLast.setTime(theDate);
//        gcLast.set(Calendar.DAY_OF_MONTH, 1);
//        String day_first = df.format(gcLast.getTime());
        Calendar c = Calendar.getInstance();
        //设置年份
        c.set(Calendar.YEAR,year);
        //设置月份
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_MONTH,1);
        String first = df.format(c.getTime());
        return toDate(first);
    }

    public static Date setMinute(Date date, Integer time) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, time);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date getLastDay(int year,int month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR,year);
        //设置月份
        ca.set(Calendar.MONTH, month-1);
        ca.set(Calendar.DAY_OF_MONTH,ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        return toDate(last);
    }

    public static Integer getYear(Date date) {
        Calendar a=getCalendar(date);
        return  a.get(Calendar.YEAR);
    }
    public static Integer getMonth(Date date)
    {
        Calendar a=getCalendar(date);
        return  a.get(Calendar.MONTH)+1;
    }

    /**
     * 两个时间差
     * @param date1
     * @param date2
     * @return
     */
    public static long diff(Date date1, Date date2) {
        long result=0;
        result = date2.getTime() - date1.getTime();
        return result;

    }

    /**
     * 一天的秒数
     * @return
     */
    public static int diffSeconds() {
        int result=0;
        Long n = diff(new Date(), getDateEnd(new Date()))/1000;
        result=n.intValue();
        return result;
    }



    public static void main(String[] args) throws ParseException {
//        System.out.println(new Date());
//        System.out.println(getDateEnd(new Date()));
        System.out.println(diffSeconds());
        //System.out.println(setMinute(new Date(),24));
    }
}
