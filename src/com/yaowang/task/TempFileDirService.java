package com.yaowang.task;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.yaowang.util.filesystem.util.FileSystemUtil;
import com.yaowang.util.filesystem.util.StorePathUtil;
/**
 * 定时删除临时目录
 * @author 
 * @date 2014-9-15 
 * @version V 1.0
 */
@Service("tempFileDirService")
public class TempFileDirService {
    /**
     * 删除文件夹
     */
    public void deleteDir(){
        //获取上一个月的临时目录的上级目录，为了跨年份出现问题
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        //删一个月的文件夹
        String befStorePath = StorePathUtil.getStoreTempPath(new Date()).toString();
        String fileName=  FileSystemUtil.getFileName(befStorePath);
        String dirPath = FileSystemUtil.getPatentFile(befStorePath);
		try {
			//文件夹名称
			String[] dirs = FileSystemUtil.getFilesName(dirPath);
			//删除除当前月份临时目录外的目录
			for (String temp : dirs) {
				if (temp.compareTo(fileName) <= 0) {
					Boolean b =FileSystemUtil.deleteDir(temp);
					if (b) {
						System.out.println("=====>定时删除临时目录：" + temp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
}
