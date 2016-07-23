package data.sqlImpl.mysql;

/**
 * 数据库配置
 * @author wwz
 * @version 2016年5月05日 下午8:33:15
 */
public class MySQLConf {
	// 驱动程序名
	public static  String DRIVER = "com.mysql.jdbc.Driver";
	// URL指向要访问的数据库名nbalabala
	public static  String URL = "jdbc:mysql://127.0.0.1:3306/";
	public static  String databaseName = "stock";
	// 用户名
	public static  String USER = "root";
	// 密码
	public static  String PASSWORD = "861910";
	
	  /** 数据库配置文件路径 */
    public static String configPath = "config/database.conf";
    
//    public static Connection conn;
//    static {
//    	try {
//    		// 从文件中读取数据库帐户名，密码和数据库名称
//			FileReader fr = new FileReader(configPath);
//			BufferedReader reader = new BufferedReader(fr);
//			USER = reader.readLine();
//			PASSWORD = reader.readLine();
//			databaseName = "stock";
//			URL += databaseName; // URL指向要访问的数据库名
//			reader.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
}

