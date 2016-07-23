package presentation.showPanel.stock;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import enums.ShowPanelType;
import presentation.common.UnEditedJtable;
import presentation.main.MainController;
import presentation.showPanel.analysis.LinkCellRenderer;
import presentation.showPanel.analysis.ReportShowPanel;
import presentation.spider.Report;
import presentation.spider.ReportShow;
import presentation.spider.Spider;

public class StockReportPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5950220420234154583L;
	private JTable table;
	Vector<Vector> datas;
	Vector<String> columns;
	ArrayList<Report> industryReportList;

	int UrlCount = 1;
	private JLabel label_page;

	public StockReportPanel(String stockCode) {
		// setSize(897, 600);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 30, 897, 570);
		add(scrollPane);

		initTableDatasAndColumns(stockCode);
		TableModel defaultTableModel = new DefaultTableModel(datas, columns);
		table = new UnEditedJtable(defaultTableModel);
		setTableFace();
		scrollPane.setViewportView(table);

		JLabel title = new JLabel("最新公司研究报告");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("微软雅黑", Font.BOLD, 16));
		title.setBounds(0, 0, 897, 30);
		add(title);

		int linkcolumn = columns.indexOf("标题");
		LinkCellRenderer renderer = new LinkCellRenderer(linkcolumn) {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				int c = table.columnAtPoint(p);
				if (c == linkcolumn) {
					String reportShowURL = industryReportList.get(table.getSelectedRow()).getLink();

					ReportShow reportShow = Spider.getReportShow(reportShowURL);
					ReportShowPanel reportShowPanel = new ReportShowPanel(reportShow, ShowPanelType.StockPanel);

					MainController.frame.changeShowPanel(reportShowPanel);
				}

			}
		};
		// 注入渲染器
		table.setDefaultRenderer(Object.class, renderer);
		// 注入监听器
		table.addMouseListener(renderer);
		table.addMouseMotionListener(renderer);
	}

	public void refresh(String stockCode) {
		// 取得数据

		industryReportList = Spider.getStockReport(stockCode);
		datas.removeAllElements();
		for (Report industryReport : industryReportList) {
			datas.add(changeDatas(industryReport));
		}
		refreshTable();
	}

	private void initTableDatasAndColumns(String stockCode) {

		datas = new Vector<Vector>();
		// 取得数据

		industryReportList = Spider.getStockReport(stockCode);
		for (Report industryReport : industryReportList) {
			datas.add(changeDatas(industryReport));
		}

		columns = new Vector<String>();
		columns.add("序号");
		columns.add("标题");
		columns.add("报告类型");
		columns.add("发布日期");
		columns.add("机构");
		columns.add("研究员");

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Vector changeDatas(Report industryReport) {
		Vector v = new Vector();
		v.add(industryReport.getId());
		v.add(industryReport.getTitle());
		v.add(industryReport.getType());
		v.add(industryReport.getDate());
		v.add(industryReport.getOrg());
		v.add(industryReport.getReporter());
		return v;
	}

	public void setTableFace() {
		table.setRowHeight(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(400);
	}

	public void refreshTable() {
		if (this.table == null)
			return;
		DefaultTableModel tableModel = (DefaultTableModel) this.table.getModel();
		tableModel.setDataVector(datas, columns);
		setTableFace();
		table.repaint();
	}

}
