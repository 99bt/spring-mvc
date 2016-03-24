package com.yaowang.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yaowang.util.DateUtils;

public class CSVUtils {
	/**
     * CSV文件生成方法
     * @param head
     * @param dataList
     * @param outPutPath
     * @param filename
     * @return
     */
    public static File createCSVFile(List<Object> head, List<String> propertyNames, List<?> dataList,
            String outPutPath, String filename){
//    	outPutPath = getLocalTempPath();
//    	filename = DateUtils.format(nowDate);
    	List<List<Object>> csvDataList = new ArrayList<List<Object>>();
    	if(CollectionUtils.isNotEmpty(dataList)){
    		for(Object obj : dataList){
    			List<Object> rowList = new ArrayList<Object>();
    			Method method = null;
    			Object value = null;
    			for(String fieldname : propertyNames){
    				try {
						method = obj.getClass()  
								.getMethod("get" + toUpperCaseFirstOne(fieldname));
						value = method.invoke(obj);
						
						rowList.add(value);
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
    			}
    			csvDataList.add(rowList);
    		}
    	}
    	
    	return createCSVFile(head, csvDataList, outPutPath, filename);
    }
	
	/**
     * CSV文件生成方法
     * @param head
     * @param dataList
     * @param outPutPath
     * @param filename
     * @return
     */
    public static File createCSVFile(List<Object> head, List<List<Object>> dataList,
            String outPutPath, String filename) {

        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
           // csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "GBK"), 1024);
            // 写入文件头部
            writeRow(head, csvWtriter);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    /**
     * 写一行数据方法
     * @param row
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(setCell(data)).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }
    
    private static Object setCell(Object value){
		if (value == null) {
			return "";
        }
        else if (value instanceof String) {
        	//防止科学计数法
        	return value+"\t";
        }
        else if (value instanceof Boolean) {
        	return ((Boolean)value).booleanValue();
        }
        else if (value instanceof Integer) {
        	return ((Integer) value).intValue();
        }
        else if (value instanceof Double) {
        	return ((Double) value).doubleValue();
        }
        else if (value instanceof Float) {
        	DecimalFormat df = new DecimalFormat("0.00");
        	return df.format(value);
        }
        else if (value instanceof Timestamp) {
        	return DateUtils.formatHms((Date) value);
        }
        else if (value instanceof Date) {
        	return DateUtils.format((Date) value);
        }
        else {
        	return value;
        }
	}

	private static String toUpperCaseFirstOne(String name) {
   //   name = name.substring(0, 1).toUpperCase() + name.substring(1);
//		return  name;
		if(Character.isUpperCase(name.charAt(0))){
			return name;
		}else{
			char[] cs=name.toCharArray();
			cs[0]-=32;
			return String.valueOf(cs);
		}
        
    }
}
