package presentation.panel.chartPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import enums.KLineType;
import exception.StatusNotOKException;
import presentation.common.Toast;
import presentation.graph.jfreechart.KLineFactory;
import presentation.graph.jfreechart.KLinePainter;
import presentation.graph.util.StatisticalIndicators;
import presentation.main.MainController;
import utils.DateTool;
import vo.KLineVO;
import vo.MAVO;

public class BenchmarkKChartPanel extends ChartPanel {
	private static final long serialVersionUID = -4566864818394302746L;

	JPanel BASEPanel;
	JTabbedPane tabbedPane;

	KLineType kLineType;

	ArrayList<KLineVO> kLineVOs;
	ArrayList<MAVO> mavos;

	public BenchmarkKChartPanel() {
		super();
		initKLineControlPanel();
		initDatePanel();

		// tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		// tabbedPane.setBounds(0, ChartControlPanelH, 800, ChartTabPanelH);
		// add(tabbedPane);
		//
		// BASEPanel = new JPanel();
		// tabbedPane.addTab(" BASE ", BASEPanel);

		BASEPanel = new JPanel();
		BASEPanel.setBounds(0, ChartControlPanelH, 800, ChartTabPanelH);
		add(BASEPanel);

	}

	public void initChartPanel(Date start, Date end, KLineType kLineType) {
		// 判断联网
		//if (NetStatus.isConnected()) {
			switch (kLineType) {
			case day:
				new Toast(MainController.frame, 1000, "正在刷新日K线图...", Toast.MESSEGE);
				break;
			case week:
				new Toast(MainController.frame, 1000, "正在刷新周K线图...", Toast.MESSEGE);
				break;
			case month:
				new Toast(MainController.frame, 1000, "正在刷新月K线图...", Toast.MESSEGE);
				break;
			default:
				break;
			}
			
			try {
				this.start = start;
				this.end = end;
				this.kLineType = kLineType;

				refreshDateTextField(start, end);
				

				if(start.before(DateTool.getDateByString("2012-04-01"))){
					new Toast(MainController.frame, 1000, "起始时间不能早于2012-04-01", Toast.WARING);
					return;
				}
				
				kLineVOs = benchmarkLogicservice.getKLineVOs(kLineType, start, end);
				// System.out.println("start to get mavos" + new
				// Date(System.currentTimeMillis()));
				mavos = benchmarkLogicservice.getMAVOs(start, end);
				// System.out.println("start to paint" + new
				// Date(System.currentTimeMillis()));

				KLinePainter kLinePainter = KLineFactory.createKLinePainter(kLineVOs, mavos, kLineType);
				JPanel panel = KLineFactory.addStatisticalIndicators(kLinePainter, null, StatisticalIndicators.BASE);
				panel.setPreferredSize(new Dimension(800, 500));
				BASEPanel.removeAll();
				BASEPanel.add(panel, BorderLayout.CENTER);
				this.revalidate();
				this.repaint();

			} catch (StatusNotOKException e) {
				e.printStackTrace();
			}
		//} else {
//			if (NetStatus.isConnected()){
//			new Toast(MainController.frame, 1000, "网络未连接...", Toast.ERROR);
//			}
		//}

	}

	@Override
	public void refresh() {
		initChartPanel(start, end, kLineType);
	}

	public void refresh(KLineType kLineType) {
		System.out.println("Benchmark refresh ,KLineType:" + kLineType.toString());
		initDates(kLineType);
		initChartPanel(start, end, kLineType);
	}

	public void refresh(Date start, Date end) {
		System.out.println("Benchmark refresh ,date" + start + " " + end);
		initChartPanel(start, end, kLineType);
	}

}
