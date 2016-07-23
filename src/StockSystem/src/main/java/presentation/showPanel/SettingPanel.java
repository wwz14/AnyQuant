package presentation.showPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI.NormalColor;

import data.utils.SqlDataBase;
import enums.SortType;
import presentation.common.ImgButton;
import presentation.common.Toast;
import presentation.main.MainController;
import presentation.ui.Images;
import utils.Filter;

public class SettingPanel extends ShowPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1355747870618002893L;
	private JTextField textField;
	private JTextField textField_1;

	/** 数据库配置文件路径 */
	public static String configPath = "config/database.conf";
	/** MySQL配置时的用户名 */
	public static String user = "root";
	/** MySQL配置时的密码 */
	public static String password = "";
	/** 数据库名 */
	public static String databaseName = "stock";
	private JTextField textField_2;

	public SettingPanel() {
		super();

		getDataBaseConfig();

		JLabel label = new JLabel("用户名");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(91, 63, 90, 18);
		add(label);

		textField = new JTextField(user);
		textField.setBounds(191, 58, 131, 30);
		add(textField);
		textField.setColumns(10);

		JLabel label_1 = new JLabel("密码");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(91, 115, 90, 15);
		add(label_1);

		textField_1 = new JTextField(password);
		textField_1.setBounds(191, 108, 131, 30);
		add(textField_1);
		textField_1.setColumns(10);

		JButton btnDone = new ImgButton(Images.doneBt_normal, Images.doneBt_on);
		btnDone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setDataBaseConfig();
				SqlDataBase.ConnectToSql();
			}
		});
		btnDone.setForeground(Color.white);
		btnDone.setBounds(156, 163,100,29);
		btnDone.setUI(new BEButtonUI().setNormalColor(NormalColor.red));
		add(btnDone);
		
		textField_2 = new JTextField(databaseName);
		textField_2.setColumns(10);
		textField_2.setBounds(191, 10, 131, 30);
		add(textField_2);
		
		JLabel label_2 = new JLabel("数据库名称");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_2.setBounds(91, 17, 90, 18);
		add(label_2);
		
		
	}

	private void getDataBaseConfig() {
		try {
			// 从文件中读取数据库帐户名，密码和数据库名称
			FileReader fr = new FileReader(configPath);
			BufferedReader reader = new BufferedReader(fr);
			user = reader.readLine();
			password = reader.readLine();
			databaseName = reader.readLine();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setDataBaseConfig() {
		user = textField.getText().trim();
		password = textField_1.getText().trim();
		try {
			// 从文件中读取数据库帐户名，密码和数据库名称
			File file = new File(configPath);
			FileOutputStream out = new FileOutputStream(file, false); // 如果追加方式用true
			StringBuffer sb = new StringBuffer();
			sb.append(user + "\n");
			sb.append(password + "\n");
			sb.append("stock");
			out.write(sb.toString().getBytes("utf-8"));// 注意需要转换对应的字符集
			out.close();
			new Toast(MainController.frame, 1000, "更改成功", Toast.MESSEGE);
		} catch (IOException e) {
			new Toast(MainController.frame, 1000, "更改失败！", Toast.ERROR);
			e.printStackTrace();
		}
	}

	@Override
	public void refreshDatas() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshDatas(Filter filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshDatas(SortType sortType) {
		// TODO Auto-generated method stub

	}
}
