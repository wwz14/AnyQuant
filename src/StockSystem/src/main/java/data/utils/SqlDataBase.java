package data.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.StatusNotOKException;
import logic.Initializebl.InitializeInDatabeseLogic;
import logicservice.showInfoblservice.initialLogicservice;
import presentation.common.Toast;
import presentation.main.MainController;
import utils.NetStatus;

/**
 *
 */
public class SqlDataBase {

	private static String driver = "com.mysql.jdbc.Driver";

	/** URL指向要访问的数据库名 */
	private static String url = "jdbc:mysql://127.0.0.1:3306/";

	/** MySQL配置时的用户名 */
	public static String user = "root";
	/** MySQL配置时的密码 */
	public static String password = "root";
	/** 数据库名 */
	public static String databaseName = "stock";
	/** 数据库配置文件路径 */
	public static String configPath = "config/database.conf";

	public static Connection conn;

	public static void ConnectToSql() {
		try {
			// 从文件中读取数据库帐户名，密码和数据库名称
			FileReader fr = new FileReader(configPath);
			BufferedReader reader = new BufferedReader(fr);
			user = reader.readLine();
			password = reader.readLine();
			databaseName = reader.readLine();
			url = "jdbc:mysql://127.0.0.1:3306/" + databaseName; // URL指向要访问的数据库名
			reader.close();
		} catch (FileNotFoundException e) {
			new Toast(MainController.frame, 1000, "配置文件未找到", Toast.ERROR);
		} catch (IOException e) {
			new Toast(MainController.frame, 1000, "读写配置文件出错", Toast.ERROR);
		}
		try {
			// 加载驱动程序
			Class.forName(driver);
			// 连续数据库
			conn = DriverManager.getConnection(url, user, password);
			if (!conn.isClosed())
			new Toast(MainController.frame, 1000, "数据库连接成功", Toast.MESSEGE);
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		if (NetStatus.isConnected()) {
			initialLogicservice initial = new InitializeInDatabeseLogic();
			try {
				initial.updateDatabese();
			} catch (StatusNotOKException e) {
				System.out.println("初始化异常");
				e.printStackTrace();
			}
			System.out.println("end");
		}
	}

	public static Connection getConnection() {
		return conn;
	}

	static {
		try {
			// 从文件中读取数据库帐户名，密码和数据库名称
			FileReader fr = new FileReader(configPath);
			BufferedReader reader = new BufferedReader(fr);
			user = reader.readLine();
			password = reader.readLine();
			databaseName = reader.readLine();
			url = "jdbc:mysql://127.0.0.1:3306/" + databaseName; // URL指向要访问的数据库名
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static {
		try {
			// 加载驱动程序
			Class.forName(driver);
			// 连续数据库
			conn = DriverManager.getConnection(url, user, password);
			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
