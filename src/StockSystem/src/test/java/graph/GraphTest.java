package graph;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.junit.After;
import org.junit.Test;

import enums.KLineType;
import presentation.graph.GraphTester;
import presentation.graph.jfreechart.BarChartPainter;
import presentation.graph.jfreechart.DecoratorBoll;
import presentation.graph.jfreechart.DecoratorKDJ;
import presentation.graph.jfreechart.DecoratorMACD;
import presentation.graph.jfreechart.DialGraphPatinter;
import presentation.graph.jfreechart.KLineFactory;
import presentation.graph.jfreechart.KLinePainter;
import presentation.graph.jfreechart.SplineAndScatterGraphPainter;
import presentation.graph.jfreechart.SplineBarGraphPainter;
import presentation.graph.jfreechart.SplineGraphPainter;
import presentation.graph.jfreechart.ThermometerGraphPainter;
import vo.ThermometerDataVO;

public class GraphTest {

	JPanel panel = new JPanel();
	
	@After
	public void show(){
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setVisible(true);
	}
	
	@Test
	public void dialTest() {
		DialGraphPatinter dialGraphPatinter = new DialGraphPatinter(0.4,0,1);
		dialGraphPatinter.setName("上涨概率");
		panel = dialGraphPatinter.getPanel();
	}
	
	/**
	 * 独立的Boll线图
	 */
	@Test
	public void independentBollTest() {
		// 第二个参数是画图用的数据（以下均是）
		DecoratorBoll decoratorBoll = new DecoratorBoll(null, GraphTester.getBollVos(KLineType.day));
		panel = decoratorBoll.getBollPanel();
	}

	/**
	 * 独立的MACD图
	 */
	@Test
	public void independentMACDTest() {
		DecoratorMACD decoratorMACD = new DecoratorMACD(null, GraphTester.getMacdVos(KLineType.day));
		panel = decoratorMACD.getMacdPanel();
	}

	/**
	 * 独立的KDJ图
	 */
	@Test
	public void independentKDJTest() {
		DecoratorKDJ decoratorKDJ = new DecoratorKDJ(null, GraphTester.getKDJVOS(KLineType.day));
		panel = decoratorKDJ.getKDJPanel();
	}

	/**
	 * 散点图与折线图结合
	 */
	@Test
	public void scatterTest() {
		SplineAndScatterGraphPainter splineAndScatterGraphPainter = new SplineAndScatterGraphPainter(GraphTester.getPolyFitVOs());
		panel = splineAndScatterGraphPainter.getPanel();
	}
	
	/**
	 * 折线图
	 */
	@Test
	public void splTest() {
		SplineGraphPainter splineGraphPainter = new SplineGraphPainter();
		// splineGraphPainter.addData(getMResults());
		splineGraphPainter.addData(GraphTester.getAnaVO());
		splineGraphPainter.addData(GraphTester.getAnaVO());
		panel = splineGraphPainter.getPanel();
	}

	/**
	 * 折线图和柱状图结合
	 */
	@Test
	public void SplBarTest() {
		SplineBarGraphPainter splineGraphPainter = new SplineBarGraphPainter(GraphTester.getRegressionVO());
		panel = splineGraphPainter.getChartPanel(true);
	}

	/**
	 * 温度计图
	 */
	@Test
	public void ThemTest() {
		ThermometerGraphPainter thermometerGraphPainter = new ThermometerGraphPainter(
				new ThermometerDataVO(20.64, 21.68, 20.10, 20.16, "sz100000"));
		panel = thermometerGraphPainter.getPanel();
	}
	
	/**
	 * 各种K线图
	 */
	@Test
	public void klineTest() {
		JTabbedPane tab = new JTabbedPane();
		KLineType type1 = KLineType.day;
		KLinePainter kLinePainter1 = KLineFactory.createKLinePainter(GraphTester.getKLineVOs(type1), GraphTester.getMAVOs(type1), type1);
		tab.add("day", GraphTester.geTabbedPane(kLinePainter1, type1));
		KLineType type2 = KLineType.week;
		KLinePainter kLinePainter2 = KLineFactory.createKLinePainter(GraphTester.getKLineVOs(type2), GraphTester.getMAVOs(type2), type2);
		tab.add("week", GraphTester.geTabbedPane(kLinePainter2, type2));
		KLineType type3 = KLineType.month;
		KLinePainter kLinePainter3 = KLineFactory.createKLinePainter(GraphTester.getKLineVOs(type3), GraphTester.getMAVOs(type3), type3);
		tab.add("month", GraphTester.geTabbedPane(kLinePainter3, type3));
		panel.add(tab);

	}

	/**
	 * 柱状图
	 */
	@Test
	public void barchartTest() {
		BarChartPainter barGraphPainter = new BarChartPainter(GraphTester.getMResults());
		panel = barGraphPainter.getPanel();
	}
}
