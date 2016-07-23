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

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI.NormalColor;

import enums.Stockfield;
import presentation.common.DragToMove;
import presentation.common.ImagePanel;
import presentation.common.ImgButton;
import presentation.main.MainController;
import presentation.ui.Images;
import utils.Filter;
import utils.FilterItem;
import utils.ValidateUtil;

public class StockListFilterDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1639829325044237620L;
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

	public StockListFilterDialog(Frame parent) {
		super(parent, true);
		setSize(400, 600);
		Point point = parent.getLocation();// 获得主窗体在屏幕的坐标
		setLocation(point.x + parent.getWidth() / 2 - this.getWidth() / 2,
				point.y + parent.getHeight() / 2 - this.getHeight() / 2);

		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		initStockListFilterDialog();

		// drag to move
		JComponent content = (JComponent) getContentPane();
		DragToMove.apply(new Component[] { content });

		// setOpacity(0.9f);
		setVisible(true);
		
		
		System.out.println("finish init StockList Filter Dialog");
	}

	private void initStockListFilterDialog() {
		contentPanel = new ImagePanel(Images.filterBg);
		contentPanel.setLayout(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JButton btnCancel = new ImgButton(Images.cancelBt_normal, Images.cancelBt_on);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StockListFilterDialog.this.dispose();
			}
		});
		btnCancel.setBounds(96, 561, 100, 29);
		contentPanel.add(btnCancel);

		JButton btnDone = new ImgButton(Images.doneBt_normal, Images.doneBt_on);
		btnDone.setForeground(Color.white);
		btnDone.setUI(new BEButtonUI().setNormalColor(NormalColor.red));
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainController.filterDatas(getStockListFilter());
				StockListFilterDialog.this.dispose();
			}
		});
		btnDone.setBounds(221, 561, 100, 29);
		contentPanel.add(btnDone);

		textField_closestart = new JTextField();
		textField_closestart.setColumns(10);
		textField_closestart.setBounds(96, 147, 122, 30);
		contentPanel.add(textField_closestart);

		JLabel label_open = new JLabel("开盘价");
		label_open.setBounds(20, 101, 55, 18);
		contentPanel.add(label_open);

		textField_openstart = new JTextField();
		textField_openstart.setColumns(10);
		textField_openstart.setBounds(96, 96, 122, 30);
		contentPanel.add(textField_openstart);

		textField_openend = new JTextField();
		textField_openend.setColumns(10);
		textField_openend.setBounds(246, 96, 122, 30);
		contentPanel.add(textField_openend);

		textField_closeend = new JTextField();
		textField_closeend.setColumns(10);
		textField_closeend.setBounds(246, 147, 122, 30);
		contentPanel.add(textField_closeend);

		JLabel label_close = new JLabel("收盘价");
		label_close.setBounds(20, 152, 55, 18);
		contentPanel.add(label_close);

		JLabel label_high = new JLabel("最高价");
		label_high.setBounds(20, 206, 55, 18);
		contentPanel.add(label_high);

		textField_highstart = new JTextField();
		textField_highstart.setColumns(10);
		textField_highstart.setBounds(96, 201, 122, 30);
		contentPanel.add(textField_highstart);

		textField_highend = new JTextField();
		textField_highend.setColumns(10);
		textField_highend.setBounds(246, 201, 122, 30);
		contentPanel.add(textField_highend);

		textField_lowend = new JTextField();
		textField_lowend.setColumns(10);
		textField_lowend.setBounds(246, 252, 122, 30);
		contentPanel.add(textField_lowend);

		textField_lowstart = new JTextField();
		textField_lowstart.setColumns(10);
		textField_lowstart.setBounds(96, 252, 122, 30);
		contentPanel.add(textField_lowstart);

		JLabel label_low = new JLabel("最低价");
		label_low.setBounds(20, 257, 55, 18);
		contentPanel.add(label_low);

		textField_adj_pricestart = new JTextField();
		textField_adj_pricestart.setColumns(10);
		textField_adj_pricestart.setBounds(96, 303, 122, 30);
		contentPanel.add(textField_adj_pricestart);

		textField_adj_priceend = new JTextField();
		textField_adj_priceend.setColumns(10);
		textField_adj_priceend.setBounds(246, 303, 122, 30);
		contentPanel.add(textField_adj_priceend);

		textField_volumestart = new JTextField();
		textField_volumestart.setColumns(10);
		textField_volumestart.setBounds(96, 354, 122, 30);
		contentPanel.add(textField_volumestart);

		textField_volumeend = new JTextField();
		textField_volumeend.setColumns(10);
		textField_volumeend.setBounds(246, 354, 122, 30);
		contentPanel.add(textField_volumeend);

		textField_turnoverstart = new JTextField();
		textField_turnoverstart.setColumns(10);
		textField_turnoverstart.setBounds(96, 405, 122, 30);
		contentPanel.add(textField_turnoverstart);

		textField_turnoverend = new JTextField();
		textField_turnoverend.setColumns(10);
		textField_turnoverend.setBounds(246, 405, 122, 30);
		contentPanel.add(textField_turnoverend);

		textField_pestart = new JTextField();
		textField_pestart.setColumns(10);
		textField_pestart.setBounds(96, 457, 122, 30);
		contentPanel.add(textField_pestart);

		textField_peend = new JTextField();
		textField_peend.setColumns(10);
		textField_peend.setBounds(246, 457, 122, 30);
		contentPanel.add(textField_peend);

		label_adj_price = new JLabel("后复权价");
		label_adj_price.setBounds(20, 308, 55, 18);
		contentPanel.add(label_adj_price);

		label_volume = new JLabel("成交量");
		label_volume.setBounds(20, 359, 55, 18);
		contentPanel.add(label_volume);

		label_turnover = new JLabel("换手率");
		label_turnover.setBounds(20, 410, 55, 18);
		contentPanel.add(label_turnover);

		label_pe = new JLabel("市盈率");
		label_pe.setBounds(20, 462, 55, 18);
		contentPanel.add(label_pe);

		label_pb = new JLabel("市净率");
		label_pb.setBounds(20, 513, 55, 18);
		contentPanel.add(label_pb);

		textField_pbstart = new JTextField();
		textField_pbstart.setColumns(10);
		textField_pbstart.setBounds(96, 508, 122, 30);
		contentPanel.add(textField_pbstart);

		textField_pbend = new JTextField();
		textField_pbend.setColumns(10);
		textField_pbend.setBounds(246, 507, 122, 30);
		contentPanel.add(textField_pbend);

	}

	private Filter getStockListFilter() {
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
		return filter;
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
