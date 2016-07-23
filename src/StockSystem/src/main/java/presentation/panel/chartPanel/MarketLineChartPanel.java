package presentation.panel.chartPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JPanel;

import enums.KLineType;
import logic.Analysisbl.AnalysisLogic;
import logicservice.Analysisblservice.AnalysisInMarketLogicservice;
import presentation.common.MyTextPanel;
import presentation.common.Toast;
import presentation.graph.jfreechart.SplineGraphPainter;
import presentation.main.MainController;
import presentation.ui.UIConfig;
import vo.AnalysisVO;

public class MarketLineChartPanel extends ChartPanel {

	private static final long serialVersionUID = 8698594132375823162L;
	AnalysisLogic analysisInMarketLogicservice = new AnalysisLogic();
	ArrayList<AnalysisVO<Date>> analysisVOs;
	MyTextPanel textPane;

	public MarketLineChartPanel() {
		super();
		initDatePanel();

		BASEPanel = new JPanel();
		BASEPanel.setBounds(0, 0, 800, 500);
		add(BASEPanel);

		textPane = new MyTextPanel();
		textPane.setBackground(UIConfig.MarketBar);
		textPane.setEditable(false);
		textPane.setBounds(0, 500, 897, 30);
		add(textPane);

	}

	public void initChartPanel(Date start, Date end) {
		System.out.println("init market line chart panel");
		this.start = start;
		this.end = end;

		refreshDateTextField(start, end);
		// start 不能早于去年
		Date now = new Date();
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(now);
		c.add(Calendar.YEAR, -1);
		c.add(Calendar.DAY_OF_YEAR, -1); // 再提前一天
		Date lastYear = c.getTime();
		if (lastYear.before(start)) {
			analysisVOs = analysisInMarketLogicservice.averageMarketPrice(start, end);

			SplineGraphPainter splineGraphPainter = new SplineGraphPainter();
			splineGraphPainter.addData(analysisVOs);
			JPanel panel = splineGraphPainter.getPanel();
			panel.setPreferredSize(new Dimension(800, 530));

			BASEPanel.removeAll();
			BASEPanel.add(panel, BorderLayout.CENTER);

			textPane.setText("");// clear
			String string;
			try {
				string = analysisInMarketLogicservice.averageMarket(start, end);
				textPane.setDocs(string, Color.WHITE, true, 16, false);
			} catch (Exception e) {
				e.printStackTrace();
			}

			this.revalidate();
			this.repaint();
		} else {
			new Toast(MainController.frame, 1000, "只能查看一年内的行情走势图", Toast.WARING);
		}

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(KLineType kLineType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Date start, Date end) {
		initChartPanel(start, end);

	}

}
