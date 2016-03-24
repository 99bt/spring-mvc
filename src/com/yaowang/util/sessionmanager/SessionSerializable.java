package com.yaowang.util.sessionmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SessionSerializable {
	/**
	 * 反序列化加载数据
	 * @return
	 */
	public static Object loadData(String path) {
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object data = ois.readObject();
			ois.close();
			return data;
		} catch(FileNotFoundException e){
			//文件找不到正常情况
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 序列化保存数据
	 * @param data
	 */
	public static void saveData(Object data, String path) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			try {
				oos.writeObject(data);
			} catch (NotSerializableException e) {
				
			}
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除序列化数据
	 * @return
	 */
	public static boolean deleteData(String path){
		File file = new File(path);
		return file.delete();
	}
}
