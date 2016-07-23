package presentation.showPanel.market;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import enums.ShowPanelType;
import presentation.common.Toast;
import presentation.common.UnEditedJtable;
import presentation.main.MainController;
import presentation.showPanel.analysis.LinkCellRenderer;
import presentation.showPanel.analysis.ReportShowPanel;
import presentation.spider.Report;
import presentation.spider.ReportShow;
import presentation.spider.Spider;

public class MarketIndustryReportPanel extends JPanel {

	private static final long serialVersionUID = 6637489268779940397L;
	private JTable table;
	Vector<Vector> datas;
	Vector<String> columns;
	ArrayList<Report> industryReportList;

	int UrlCount = 1;
	private JLabel label_page;

	public MarketIndustryReportPanel() {
		// setSize(897, 600);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 28, 897, 537);
		add(scrollPane);

		initTableDatasAndColumns();
		TableModel defaultTableModel = new DefaultTableModel(datas, columns);
		table = new UnEditedJtable(defaultTableModel);
		setTableFace();
		scrollPane.setViewportView(table);

		JButton button = new JButton("上一页");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// toast 通知
				if (UrlCount <= 1) {
					new Toast(MainController.frame, 1000, "已经是第一页...", Toast.MESSEGE);
					return;
				}
				UrlCount--;
				industryReportList = Spider.getIndustryReport(UrlCount);
				datas.removeAllElements();
				for (int i = 0; i < industryReportList.size(); i++) {
					datas.add(changeDatas(industryReportList.get(i)));
				}
				refreshTable();
				label_page.setText(UrlCount + "");
			}
		});
		button.setBounds(315, 575, 93, 23);
		add(button);

		JButton button_1 = new JButton("下一页");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				UrlCount++;
				industryReportList = Spider.getIndustryReport(UrlCount);
				// toast 通知
				if (industryReportList.size() == 0) {
					new Toast(MainController.frame, 1000, "已经是最后一页...", Toast.MESSEGE);
					return;
				}
				datas.removeAllElements();
				for (int i = 0; i < industryReportList.size(); i++) {
					datas.add(changeDatas(industryReportList.get(i)));
				}
				refreshTable();
				label_page.setText(UrlCount + "");
			}
		});
		button_1.setBounds(494, 575, 93, 23);
		add(button_1);

		label_page = new JLabel("1");
		label_page.setHorizontalAlignment(SwingConstants.CENTER);
		label_page.setBounds(418, 579, 66, 15);
		add(label_page);

		JLabel title = new JLabel("最新行业研究报告");
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
					ReportShowPanel reportShowPanel = new ReportShowPanel(reportShow, ShowPanelType.MarketPanel);

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

	private void initTableDatasAndColumns() {

		datas = new Vector<Vector>();
		// 取得数据

		industryReportList = Spider.getIndustryReport(1);
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
