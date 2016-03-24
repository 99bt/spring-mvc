package com.yaowang.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yaowang.util.DateUtils;
/**
 * excel读取工具
 * @author shenl
 *
 */
public class ExcelUtil {
	/**
	 * 读取Excel表格头
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static List<String> loadTitle(File file, String ext) throws IOException{
		List<String> set = new ArrayList<String>();
		InputStream is = new FileInputStream(file);
		try {
			Workbook wb = null;
			if ("xls".equals(ext)) {
				wb = new HSSFWorkbook(is);
			}else if ("xlsx".equals(ext)) {
				wb = new XSSFWorkbook(is);
			}
			Sheet sheet = wb.getSheetAt(0);
			// 得到第一行
			int rowNumb = sheet.getFirstRowNum();
			Row row = sheet.getRow(rowNumb);
			if(row == null){
				//空文件
				throw new RuntimeException("上传文件为空，请重新选择上传文件.");
			}
			int cellNumb = row.getLastCellNum();
			for (int i = 0; i < cellNumb; i++) {
				if(row.getCell(i) == null){
					continue;
				}
				String str = row.getCell(i).toString().trim();
				set.add(str);
			}
		} finally{
			is.close();
		}
		
		return set;
	}
	/**
	 * 加载数据
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<List<String>> loadData(File file, String ext) throws IOException{
		List<List<String>> set = new ArrayList<List<String>>();
		InputStream is = new FileInputStream(file);
		try {
			Workbook wb = null;
			if ("xls".equals(ext)) {
				wb = new HSSFWorkbook(is);
			}else if ("xlsx".equals(ext)) {
				wb = new XSSFWorkbook(is);
			}
			Sheet sheet = wb.getSheetAt(0);
			// 得到第一行
			int rowNumb = sheet.getFirstRowNum();
			int lastRowNumb = sheet.getLastRowNum();
			if(rowNumb == lastRowNumb){
				//空文件
				throw new RuntimeException("上传文件为空，请重新选择上传文件.");
			}
			
			Row firstRow = sheet.getRow(rowNumb);
			int lastCellNumb = firstRow.getLastCellNum();
			
			for (int i = rowNumb + 1; i <= lastRowNumb; i++) {
				Row row = sheet.getRow(i);
				
				if(row == null){
					continue;
				}
				
				List<String> data = new ArrayList<String>();
				set.add(data);
				
				for (int j = 0; j < lastCellNumb; j++) {
					Cell cell = row.getCell(j);
					data.add(getStringCellValue(cell));
				}
			}
		} finally{
			is.close();
		}
		
		return set;
	}
	
	/**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private static String getStringCellValue(Cell cell) {
    	if (cell == null) {
            return "";
        }
    	
        String strCell = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
        	strCell = String.valueOf(cell.getNumericCellValue());
        	if(StringUtils.isNotBlank(strCell) && strCell.endsWith(".0")){
        		DecimalFormat df = new DecimalFormat("#");
        		strCell = df.format(cell.getNumericCellValue());
        	}
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        return strCell;
    }
	
	
	/**
	 * 写入excel
	 * @param fieldNames
	 * @param propertyNames
	 * @param list
	 * @return
	 */
	public static HSSFWorkbook setExcel(String titleName, String[] subTitle, Object[] subTitleData, String[] fieldNames, String[] propertyNames, List<?> list){
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet();  
        //设置单元格式  
        sheet.setDefaultColumnWidth(15);
        
        int n = 0;
        if(StringUtils.isNotBlank(titleName)){
        	//头格式
            HSSFCellStyle style = wb.createCellStyle();  
            HSSFFont font = wb.createFont();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);
            style.setFont(font);
            //头内容
	        HSSFRow rowTitle = sheet.createRow((int) n); 
	        HSSFCell cellTitle = rowTitle.createCell(0); 
	        cellTitle.setCellValue(titleName);
	        cellTitle.setCellStyle(style);
	        CellRangeAddress region = new CellRangeAddress(0, (short) 0, 0, (short) (fieldNames.length - 1));
	        sheet.addMergedRegion(region);
	        n++;
        }
        if(ArrayUtils.isNotEmpty(subTitle)){
        	//格式
        	HSSFCellStyle style = wb.createCellStyle();  
        	HSSFFont font = wb.createFont();
        	style.setAlignment(HSSFCellStyle.ALIGN_RIGHT); //居中
        	font.setFontHeightInPoints((short) 12);
        	font.setBold(true);
        	style.setFont(font);
        	//内容
        	HSSFRow rowSubTitle = sheet.createRow((int) n);
        	int j = 0;
        	for(int i = 0; i < subTitle.length; i++){
        		HSSFCell cell = rowSubTitle.createCell(j); 
        		cell.setCellValue(subTitle[i]);
        		cell.setCellStyle(style);
        		HSSFCell cell1 = rowSubTitle.createCell(++j); 
        		setCell(subTitleData[i], cell1);
        		j++;
        	}
	        n++;
        }
        
        //表头格式
        HSSFFont font = wb.createFont();
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        style.setFont(font);
        //表头
        HSSFRow row = sheet.createRow((int) n);
        for(int i = 0; i < fieldNames.length; i++){
        	HSSFCell cell = row.createCell(i);
        	cell.setCellValue(fieldNames[i]);
        	cell.setCellStyle(style);  
        }
        
        //表格内容
        if(CollectionUtils.isNotEmpty(list)){
        	for(int i = 0; i < list.size(); i++){
        		Object obj = list.get(i);
        		HSSFRow row1 = sheet.createRow(n+i+1);
        		//获取属性值
        		for(int j = 0; j < propertyNames.length; j++){
        			HSSFCell cell = row1.createCell(j); 
        			Method method = null;
        			Object value = null;
						try {
							if(propertyNames[j].indexOf(".") > -1){
								String[] properties = propertyNames[j].split("\\.");
								Object tmp = obj;
								for(String property:properties){
									method = tmp.getClass().getMethod("get" + toUpperCaseFirstOne(property));
									tmp = method.invoke(tmp);
								}
								value = tmp;
							}else{
								method = obj.getClass()  
								        .getMethod("get" + toUpperCaseFirstOne(propertyNames[j]));
								value = method.invoke(obj);
							}
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					setCell(value, cell);
        		}
        	}
        }
        return wb;
	}
	private static void setCell(Object value, HSSFCell cell){
		if (value == null) {
			cell.setCellType(
                    HSSFCell.CELL_TYPE_BLANK);
        }
        else if (value instanceof String) {
        	cell.setCellType(
                    HSSFCell.CELL_TYPE_STRING);
        	cell.setCellValue((String) value);
        }
        else if (value instanceof Boolean) {
        	cell.setCellType(
                    HSSFCell.CELL_TYPE_BOOLEAN);
        	cell.setCellValue(
                    ((Boolean) value).booleanValue());
        }
        else if (value instanceof Integer) {
        	cell.setCellValue(
                    ((Integer) value).intValue());
        }
        else if (value instanceof Double) {
        	cell.setCellValue(
                    ((Double) value).doubleValue());
        }
        else if (value instanceof Timestamp) {
        	cell.setCellType(
                    HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue((String) DateUtils.formatHms((Date) value));
        }
        else if (value instanceof Date) {
        	cell.setCellType(
        			HSSFCell.CELL_TYPE_STRING);
        	cell.setCellValue((String) DateUtils.format((Date) value));
        }
        else {
        	cell.setCellValue(value.toString());
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
	
	public static void main(String[] args) throws IOException {
		System.out.println(loadTitle(new File("C:\\Users\\Administrator\\Desktop\\out_stats_data20150319123939.xls"), "xls"));
		System.out.println(loadData(new File("C:\\Users\\Administrator\\Desktop\\out_stats_data20150319123939.xls"), "xls"));
	}
}
