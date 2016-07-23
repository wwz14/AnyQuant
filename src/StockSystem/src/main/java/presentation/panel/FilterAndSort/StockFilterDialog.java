package presentation.panel.FilterAndSort;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI.NormalColor;

import com.qt.datapicker.DatePicker;

import enums.Stockfield;
import presentation.common.DateTextField;
import presentation.common.DragToMove;
import presentation.common.ImagePanel;
import presentation.common.ImgButton;
import presentation.common.MyDatePicker;
import presentation.common.Toast;
import presentation.main.MainController;
import presentation.ui.Images;
import utils.DateTool;
import utils.Filter;
import utils.FilterItem;
import utils.ValidateUtil;

public class StockFilterDialog extends JDialog {

	private static final long serialVersionUID = -2913643746015507485L;
	JPanel contentPanel;
	private JTextField textField_closestart;
	private JTextField textField_openstart;
	private JTextField textField_openend;
	private JTextField textField_closeend;
	private JTextField textField_highstart;
	private JTextField textField_highend;
	private JTextField textField_lowend;
	private JTextField textField_lowstart;
	private JTextField textField_adj_pricestart;
	private JTextField textField_adj_priceend;
	private JTextField textField_volumestart;
	private JTextField textField_volumeend;
	private JTextField textField_turnoverstart;
	private JTextField textField_turnoverend;
	private JTextField textField_pestart;
	private JTextField textField_peend;
	private JLabel label_adj_price;
	private JLabel label_volume;
	private JLabel label_turnover;
	private JLabel label_pe;
	private JLabel label_pb;
	private JTextField textField_pbstart;
	private JTextField textField_pbend;
	private DateTextField textField_datestart;
	private DateTextField textField_dateend;

	public StockFilterDialog(Frame parent, Date start, Date end) {
		super(parent, true);
		setSize(400, 600);
		Point point = parent.getLocation();// 获得主窗体在屏幕的坐标
		setLocation(point.x + parent.getWidth() / 2 - this.getWidth() / 2,
				point.y + parent.getHeight() / 2 - this.getHeight() / 2);

		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		initStockFilterDialog(start, end);

		// drag to move
		JComponent content = (JComponent) getContentPane();
		DragToMove.apply(new Component[] { content });

		// setOpacity(0.9f);
		setVisible(true);
	}

	private void initStockFilterDialog(Date start, Date end) {
		contentPanel = new ImagePanel(Images.filterBg);
		contentPanel.setLayout(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JButton btnCancel = new ImgButton(Images.cancelBt_normal, Images.cancelBt_on);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StockFilterDialog.this.dispose();
			}
		});
		btnCancel.setBounds(81, 554, 100, 29);
		contentPanel.add(btnCancel);

		JButton btnDone = new ImgButton(Images.doneBt_normal, Images.doneBt_on);
		btnDone.setForeground(Color.white);
		btnDone.setUI(new BEButtonUI().setNormalColor(NormalColor.red));
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainController.filterDatas(getStockFilter());
				StockFilterDialog.this.dispose();
			}
		});
		btnDone.setBounds(213, 554, 100, 29);
		contentPanel.add(btnDone);

		textField_closestart = new JTextField();
		textField_closestart.setColumns(10);
		textField_closestart.setBounds(96, 182, 122, 30);
		contentPanel.add(textField_closestart);

		JLabel label_open = new JLabel("开盘价");
		label_open.setBounds(20, 134, 55, 18);
		contentPanel.add(label_open);

		textField_openstart = new JTextField();
		textField_openstart.setColumns(10);
		textField_openstart.setBounds(96, 129, 122, 30);
		contentPanel.add(textField_openstart);

		textField_openend = new JTextField();
		textField_openend.setColumns(10);
		textField_openend.setBounds(246, 129, 122, 30);
		contentPanel.add(textField_openend);

		textField_closeend = new JTextField();
		textField_closeend.setColumns(10);
		textField_closeend.setBounds(246, 182, 122, 30);
		contentPanel.add(textField_closeend);

		JLabel label_close = new JLabel("收盘价");
		label_close.setBounds(20, 187, 55, 18);
		contentPanel.add(label_close);

		JLabel label_high = new JLabel("最高价");
		label_high.setBounds(20, 238, 55, 18);
		contentPanel.add(label_high);

		textField_highstart = new JTextField();
		textField_highstart.setColumns(10);
		textField_highstart.setBounds(96, 233, 122, 30);
		contentPanel.add(textField_highstart);

		textField_highend = new JTextField();
		textField_highend.setColumns(10);
		textField_highend.setBounds(246, 233, 122, 30);
		contentPanel.add(textField_highend);

		textField_lowend = new JTextField();
		textField_lowend.setColumns(10);
		textField_lowend.setBounds(246, 279, 122, 30);
		contentPanel.add(textField_lowend);

		textField_lowstart = new JTextField();
		textField_lowstart.setColumns(10);
		textField_lowstart.setBounds(96, 279, 122, 30);
		contentPanel.add(textField_lowstart);

		JLabel label_low = new JLabel("最低价");
		label_low.setBounds(20, 284, 55, 18);
		contentPanel.add(label_low);

		textField_adj_pricestart = new JTextField();
		textField_adj_pricestart.setColumns(10);
		textField_adj_pricestart.setBounds(96, 324, 122, 30);
		contentPanel.add(textField_adj_pricestart);

		textField_adj_priceend = new JTextField();
		textField_adj_priceend.setColumns(10);
		textField_adj_priceend.setBounds(246, 324, 122, 30);
		contentPanel.add(textField_adj_priceend);

		textField_volumestart = new JTextField();
		textField_volumestart.setColumns(10);
		textField_volumestart.setBounds(96, 374, 122, 30);
		contentPanel.add(textField_volumestart);

		textField_volumeend = new JTextField();
		textField_volumeend.setColumns(10);
		textField_volumeend.setBounds(246, 374, 122, 30);
		contentPanel.add(textField_volumeend);

		textField_turnoverstart = new JTextField();
		textField_turnoverstart.setColumns(10);
		textField_turnoverstart.setBounds(96, 422, 122, 30);
		contentPanel.add(textField_turnoverstart);

		textField_turnoverend = new JTextField();
		textField_turnoverend.setColumns(10);
		textField_turnoverend.setBounds(246, 422, 122, 30);
		contentPanel.add(textField_turnoverend);

		textField_pestart = new JTextField();
		textField_pestart.setColumns(10);
		textField_pestart.setBounds(96, 469, 122, 30);
		contentPanel.add(textField_pestart);

		textField_peend = new JTextField();
		textField_peend.setColumns(10);
		textField_peend.setBounds(246, 469, 122, 30);
		contentPanel.add(textField_peend);

		label_adj_price = new JLabel("后复权价");
		label_adj_price.setBounds(20, 329, 55, 18);
		contentPanel.add(label_adj_price);

		label_volume = new JLabel("成交量");
		label_volume.setBounds(20, 379, 55, 18);
		contentPanel.add(label_volume);

		label_turnover = new JLabel("换手率");
		label_turnover.setBounds(20, 427, 55, 18);
		contentPanel.add(label_turnover);

		label_pe = new JLabel("市盈率");
		label_pe.setBounds(20, 474, 55, 18);
		contentPanel.add(label_pe);

		label_pb = new JLabel("市净率");
		label_pb.setBounds(20, 519, 55, 18);
		contentPanel.add(label_pb);

		textField_pbstart = new JTextField();
		textField_pbstart.setColumns(10);
		textField_pbstart.setBounds(96, 514, 122, 30);
		contentPanel.add(textField_pbstart);

		textField_pbend = new JTextField();
		textField_pbend.setColumns(10);
		textField_pbend.setBounds(246, 514, 122, 30);
		contentPanel.add(textField_pbend);

		JLabel label_date = new JLabel("日期");
		label_date.setBounds(20, 85, 55, 18);
		contentPanel.add(label_date);

		textField_datestart = new DateTextField("yyyy-MM-dd");
		textField_datestart.setColumns(10);
		textField_datestart.setBounds(96, 80, 85, 30);
		contentPanel.add(textField_datestart);

		textField_dateend = new DateTextField("yyyy-MM-dd");
		textField_dateend.setColumns(10);
		textField_dateend.setBounds(246, 80, 85, 30);
		contentPanel.add(textField_dateend);

		refreshDateTextField(start, end);

		JButton button_chooseStartDate = new ImgButton(Images.calendar_normal, Images.calendar_on);
		button_chooseStartDate.setBounds(192, 79, 25, 25);
		button_chooseStartDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatePicker dp = new MyDatePicker(textField_datestart, Locale.CHINA);
				// previously selected date
				Date selectedDate = dp.parseDate(textField_datestart.getText());
				dp.setSelectedDate(selectedDate);
				dp.start(button_chooseStartDate);
			}
		});
		contentPanel.add(button_chooseStartDate);

		JButton button_chooseEndDate = new ImgButton(Images.calendar_normal, Images.calendar_on);
		button_chooseEndDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatePicker dp = new MyDatePicker(textField_dateend, Locale.CHINA);
				// previously selected date
				Date selectedDate = dp.parseDate(textField_dateend.getText());
				dp.setSelectedDate(selectedDate);
				dp.start(button_chooseEndDate);
			}
		});
		button_chooseEndDate.setBounds(342, 79, 25, 25);
		contentPanel.add(button_chooseEndDate);
	}

	private Filter getStockFilter() {
		ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();

		filterItems.add(checkField(textField_openstart, textField_openend, Stockfield.open));
		filterItems.add(checkField(textField_closestart, textField_closeend, Stockfield.close));
		filterItems.add(checkField(textField_highstart, textField_highend, Stockfield.high));
		filterItems.add(checkField(textField_lowstart, textField_lowend, Stockfield.low));
		filterItems.add(checkField(textField_adj_pricestart, textField_adj_priceend, Stockfield.adj_price));
		filterItems.add(checkField(textField_volumestart, textField_volumeend, Stockfield.volume));
		filterItems.add(checkField(textField_turnoverstart, textField_turnoverend, Stockfield.turnover));
		filterItems.add(checkField(textField_pestart, textField_peend, Stockfield.pe_ttm));
		filterItems.add(checkField(textField_pbstart, textField_pbend, Stockfield.pb));

		Filter filter = new Filter(filterItems, null, null);
		if (ValidateUtil.isDatetime(textField_datestart.getText())) {
			filter.setStartDate(DateTool.getDateByString(textField_datestart.getText()));
		} else {
			new Toast(MainController.frame, 1000, "起始日期格式不正确", Toast.ERROR);
		}
		if (ValidateUtil.isDatetime(textField_dateend.getText())) {
			filter.setEndDate(DateTool.getDateByString(textField_dateend.getText()));
		} else {
			new Toast(MainController.frame, 1000, "终止日期格式不正确", Toast.ERROR);
		}

		// 判断时间范围
		if (null != filter.getStartDate() && null != filter.getEndDate()) {

			if (ValidateUtil.isDateRangeValidated(filter.getStartDate(), filter.getEndDate())) {
				return filter;
			} else {
				new Toast(MainController.frame, 2000, "起始日期晚于终止日期或者日期超出最新日期", Toast.WARING);
			}
		}
		return filter;
	}

	public void refreshDateTextField(Date start, Date end) {
		textField_datestart.setText(DateTool.getStringByDate(start));
		textField_dateend.setText(DateTool.getStringByDate(end));
	}

	protected FilterItem checkField(JTextField textFieldstart, JTextField textFieldend, Stockfield stockfield) {
		FilterItem filterItem = new FilterItem(stockfield, null, null);
		if (isValidate(textFieldstart)) {
			filterItem.setStart(BigDecimal.valueOf(Double.parseDouble(textFieldstart.getText())));
		}
		if (isValidate(textFieldend)) {
			filterItem.setEnd(BigDecimal.valueOf(Double.parseDouble(textFieldend.getText())));
		}
		return filterItem;
	}

	private boolean isValidate(JTextField textField) {
		if (!textField.getText().equals("")) {
			if (ValidateUtil.isNumber(textField.getText())) {
				return true;
			}
		}
		return false;
	}

}
