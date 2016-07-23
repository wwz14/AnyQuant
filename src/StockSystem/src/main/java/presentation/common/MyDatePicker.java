package presentation.common;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Locale;
import java.util.Observer;

import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import com.qt.datapicker.DatePicker;

import presentation.ui.Images;

public class MyDatePicker extends DatePicker {

	public MyDatePicker(Observer observer, Locale locale) {
		super(observer, locale);
		getScreen().setPreferredSize(new Dimension(250, 200));
		getScreen().getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		getScreen().setTitle("选择日期");
		getScreen().setIconImage(Images.calendar_normal.getImage());

	}

	@Override
	public void start(Component c) {
		if (c != null) {
			Point p = c.getLocationOnScreen();
			int x = p.x + c.getWidth() - getScreen().getWidth() / 2, y = p.y - c.getHeight() - getScreen().getHeight();

			getScreen().setLocation(x, y);
		} else {
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			getScreen().setLocation((int) (dim.getWidth() - getScreen().getWidth()) / 2,
					(int) (dim.getHeight() - getScreen().getHeight()) / 2);
		}
		SwingUtilities.invokeLater(this);

	}

	/*
	 * 原来的架包月份是英文，这里重写变成数字
	 */
	@Override
	public String getString(String key, String dv) {

		return monthInt.get(key);
	}

	static HashMap<String, String> monthInt = new HashMap<String, String>();

	static {
		for (int i = 0; i < 12; i++) {
			monthInt.put("month." + i, i + 1 + "");
		}
	}

}
