package presentation.showPanel.analysis;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import enums.ShowPanelType;
import presentation.common.BaseDataLoader;
import presentation.common.NTScrollPane;
import presentation.common.Toast;
import presentation.common.UnEditedJtable;
import presentation.main.MainController;
import presentation.spider.ReportShow;
import presentation.spider.Spider;
import presentation.spider.StockPreformancePrediction;
import utils.NetStatus;

@SuppressWarnings("rawtypes")
public class StockPredictionPanel extends JPanel {

	private static final long serialVersionUID = -8713384916227684924L;
	private JTable table;
	Vector<Vector> datas;
	Vector<String> columns;

	ArrayList<StockPreformancePrediction> Allpredictions;
	ArrayList<StockPreformancePrediction> Onepredictions;
	private JTextField textField;

	static boolean isNextBatch = true; // 列表能否下拉刷新

	public StockPredictionPanel() {
		setSize(897, 600);
		setLayout(null);

		textField = new JTextField();
		textField.setBounds(56, 11, 107, 27);
		add(textField);
		textField.setColumns(10);

		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					search(textField.getText().trim());
				}
			}
		});

		JButton button = new JButton("确定");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search(textField.getText().trim());

			}
		});
		button.setBounds(173, 13, 93, 23);
		add(button);

		NTScrollPane scrollPane = new NTScrollPane();
		scrollPane.setDataLoader(new TableDataLoader());
		scrollPane.setBounds(0, 50, 897, 550);
		add(scrollPane);

		initTableDatasAndColumns();
		TableModel defaultTableModel = new DefaultTableModel(datas, columns);
		table = new UnEditedJtable(defaultTableModel);
		setTableFace();
		scrollPane.setViewportView(table);

		JLabel lblEps = new JLabel("EPS（每股收益）单位：元");
		lblEps.setBounds(437, 15, 157, 15);
		add(lblEps);

		JLabel label = new JLabel("每股收益预测");
		label.setFont(new Font("微软雅黑", Font.BOLD, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(313, 11, 131, 21);
		add(label);

		JLabel label_1 = new JLabel("搜索：");
		label_1.setBounds(10, 17, 54, 15);
		add(label_1);

		JButton btnFuyuan = new JButton("复原");
		btnFuyuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				datas.removeAllElements();
				for (int i = 0; i < Allpredictions.size(); i++) {
					datas.add(changeDatas(Allpredictions.get(i)));
				}
				refreshTable();
				isNextBatch = true;
			}
		});
		btnFuyuan.setBounds(807, 13, 80, 23);
		add(btnFuyuan);

		int linkcolumn = columns.indexOf("明细");
		LinkCellRenderer renderer = new LinkCellRenderer(linkcolumn) {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				int c = table.columnAtPoint(p);
				if (c == linkcolumn) {
					String reportShowURL = Allpredictions.get(table.getSelectedRow()).getReportShowURL();


					ReportShow reportShow = Spider.getReportShow(reportShowURL);
					ReportShowPanel reportShowPanel = new ReportShowPanel(reportShow,ShowPanelType.StockAnalysisPanel);

					MainController.frame.changeShowPanel(reportShowPanel);
				}
//				else if (c == 0) {
//					// 股票代号第0列，则调到该股票具体界面
//					String stockCode = table.getValueAt(table.rowAtPoint(p), 0).toString().trim();
////					textField.setText(stockCode);
////					search(stockCode);
//					MainController.changeToStockPanel(stockCode, ShowPanelType.StockAnalysisPanel);
//				}

			}
		};
		// 注入渲染器
		table.setDefaultRenderer(Object.class, renderer);
		// 注入监听器
		table.addMouseListener(renderer);
		table.addMouseMotionListener(renderer);

	}

	private void initTableDatasAndColumns() {

		datas = new Vector<Vector>();
		// 取得数据
		Allpredictions = Spider.getPreformancePrediction(0);
		for (int i = 0; i < Allpredictions.size(); i++) {
			datas.add(changeDatas(Allpredictions.get(i)));
		}

		columns = new Vector<String>();
		columns.add("股票代号");
		columns.add("股票名称");
		columns.add("15EPS");
		columns.add("16EPS");
		columns.add("17EPS");
		columns.add("18EPS");
		columns.add("报告日期");
		columns.add("研究机构");
		columns.add("明细");

	}

	@SuppressWarnings({ "unchecked" })
	private Vector changeDatas(StockPreformancePrediction stockPreformancePrediction) {
		Vector v = new Vector();
		v.add(stockPreformancePrediction.getStockCode());
		v.add(stockPreformancePrediction.getStockName());
		v.add(stockPreformancePrediction.getEPS15());
		v.add(stockPreformancePrediction.getEPS16());
		v.add(stockPreformancePrediction.getEPS17());
		v.add(stockPreformancePrediction.getEPS18());
		v.add(stockPreformancePrediction.getReportDate());
		v.add(stockPreformancePrediction.getOrg());
		v.add("<html><font color=blue><u>" + "明细");
		return v;
	}

	public void refreshTable() {
		if (this.table == null)
			return;
		DefaultTableModel tableModel = (DefaultTableModel) this.table.getModel();
		tableModel.setDataVector(datas, columns);
		setTableFace();
		table.repaint();
	}

	public void setTableFace() {
		table.setRowHeight(30);
		table.getColumnModel().getColumn(7).setPreferredWidth(175);
	}

	private void search(String stockCode) {
		System.out.println(new Date(System.currentTimeMillis()));
		if (!NetStatus.isConnected()) {
			new Toast(MainController.frame, 1000, "网络未连接...", Toast.ERROR);
			return;
		}
		System.out.println(new Date(System.currentTimeMillis()));
		if (!isStockCode(stockCode)) {
			new Toast(MainController.frame, 1000, "请输入存在的股票代号", Toast.ERROR);
			return;
		}
		System.out.println(new Date(System.currentTimeMillis()));
		new Toast(MainController.frame, 1000, "正在搜索...", Toast.MESSEGE);
		Onepredictions = Spider.getPreformancePrediction(stockCode);
		datas.removeAllElements();
		for (int i = 0; i < Onepredictions.size(); i++) {
			datas.add(changeDatas(Onepredictions.get(i)));
		}
		refreshTable();
		// 设置列表不能刷新
		isNextBatch = false;
		System.out.println(new Date(System.currentTimeMillis()));
	}

	/**
	 *检测股票代码是否存在，直接连接该链接，若返回是整体列表则不存在
	 */
	private boolean isStockCode(String stockCode) {
		ArrayList<StockPreformancePrediction> predictions=Spider.getPreformancePrediction(stockCode);
		if(predictions.get(0).getStockCode() .equals( predictions.get(1).getStockCode())){
			//此处必须用equals而不是==
			return true;
		}
		return false;
	}

	int UrlCount = 0;

	class TableDataLoader implements BaseDataLoader {

		@Override
		public void nextBatch() {
			// 如果是所有列表则下拉加载数据，如果是具体股票则不会加载
			if (isNextBatch) {
				// toast 通知
				new Toast(MainController.frame, 1000, "正在加载下一页...", Toast.MESSEGE);
				UrlCount++;
				Allpredictions.addAll(Spider.getPreformancePrediction(UrlCount));
				datas.removeAllElements();
				for (int i = 0; i < Allpredictions.size(); i++) {
					datas.add(changeDatas(Allpredictions.get(i)));
				}
				refreshTable();
			}
		}

	}
}
