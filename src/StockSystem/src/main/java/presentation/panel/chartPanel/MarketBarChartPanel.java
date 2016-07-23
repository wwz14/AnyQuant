package presentation.panel.chartPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import enums.KLineType;
import enums.SortType;
import logic.Analysisbl.AnalysisLogic;
import logicservice.Analysisblservice.AnalysisInMarketLogicservice;
import presentation.graph.jfreechart.RankingGraphPainter;
import vo.AnalysisVO;

public class MarketBarChartPanel extends ChartPanel {

	private static final long serialVersionUID = -8852130997262736698L;

	AnalysisInMarketLogicservice analysisInMarketLogicservice = new AnalysisLogic();
	private JComboBox<?> comboBox;
	ArrayList<AnalysisVO<String>> changeRateanalysisVOs;
	ArrayList<AnalysisVO<String>> peanalysisVOs;
	ArrayList<AnalysisVO<String>> pbanalysisVOs;
	ArrayList<AnalysisVO<String>> dealPriceanalysisVOs;

	JPanel contentPanel;
	JTabbedPane tabbedPane;
	JPanel BASEPanel;
	SortType[] types;

	public MarketBarChartPanel() {
		super();
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 800, 40);
		panel.setLayout(null);
		add(panel);

		JLabel label = new JLabel("查看方式");
		label.setBounds(29, 10, 55, 18);
		panel.add(label);

		types = new SortType[] { SortType.changeRate, SortType.pe, SortType.pb, SortType.dealPrice };
		comboBox = new JComboBox<Object>(types);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				refresh();
			}
		});
		// 皮肤包下选中后的JComboBox 为蓝色，以下方法更改为白色
		Component component = comboBox.getEditor().getEditorComponent();
		if (component instanceof JTextField) {
			JTextField field = (JTextField) component;

			field.setEditable(false);

			field.setSelectionColor(
					field.getBackground()/* java.awt.Color.WHITE */);

			comboBox.setEditable(true);
		}
		comboBox.setBounds(94, 5, 131, 31);
		panel.add(comboBox);

		BASEPanel = new JPanel();
		BASEPanel.setBounds(0, 40, 800, 560);
		add(BASEPanel);

	}

	public void initChartPanel(SortType sortType) {
		System.out.println("init market bar chart panel " + sortType.toString());
		List<?> list = null;
		switch (sortType) {
		case changeRate:
			changeRateanalysisVOs = analysisInMarketLogicservice.changeRateBar();
			for (AnalysisVO<String>  s : changeRateanalysisVOs) {
				System.err.println(s.getName()+s.getStatistic());
			}
			list = changeRateanalysisVOs;
			break;
		case pe:
			peanalysisVOs = analysisInMarketLogicservice.shiYingLuBar();
			list = peanalysisVOs;
			break;
		case pb:
			pbanalysisVOs = analysisInMarketLogicservice.shiJingLuBar();
			list = pbanalysisVOs;
			break;
		case dealPrice:
			dealPriceanalysisVOs = analysisInMarketLogicservice.dealpriceBarandPie();
			list = dealPriceanalysisVOs;
			break;
		default:
			break;
		}

		RankingGraphPainter rankingGraphPainter = new RankingGraphPainter(list);
		BASEPanel.removeAll();
		rankingGraphPainter.setSubTitle(sortType.toString());
		JPanel panel = rankingGraphPainter.getPanel();
		panel.setPreferredSize(new Dimension(800, 560));
		BASEPanel.add(panel, BorderLayout.CENTER);

		this.revalidate();
		this.repaint();

	}

	// private void initControlPanel(){
	//
	//// controlPanel = new JPanel();
	//// controlPanel.setBounds(0, 0, 800, ChartControlPanelH);
	//// controlPanel.setLayout(null);
	//// add(controlPanel);
	// }

	@Override
	public void refresh() {
		System.out.println("market bar chart refresh");
		initChartPanel((SortType) comboBox.getSelectedItem());

	}

	@Override
	public void refresh(KLineType kLineType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Date start, Date end) {
		// TODO Auto-generated method stub

	}
}
