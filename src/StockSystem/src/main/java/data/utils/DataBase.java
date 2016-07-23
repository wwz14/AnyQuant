package data.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * @author zhuding
 *
 * 负责文件读写
 */
public class DataBase {


	/**
	 * 
	 * @param path 文件路径
	 * @return 文件中包含的对象
	 */
	public static Object load(String path) {
		ObjectInputStream ois = null;
		try {
			File file = new File(path);
			if (file.length() == 0) {
				return null;
			}
			ois = new ObjectInputStream(new FileInputStream(file));
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 写文件
	 * @param path 文件路径
	 * @param object 放入的对象
	 */
	public static void save(String path,Object object) {

		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(path + " save error!");
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	public static void saveTxtFile(String path, Object object) throws IOException  {
		File file = new File(path);
		if (!file.exists()){
			file.createNewFile();
			System.out.println("create"+path);
		}
		//System.out.println(path);
		save(path,object);
	}



}
