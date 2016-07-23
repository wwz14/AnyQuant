package presentation.graph.jfreechart;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import presentation.graph.util.ComputingModule;
import presentation.graph.util.GraphComponents;
import utils.DateTool;
import vo.MarketVO;
/**
 * 
* @ClassName: MarketGraphPainter    
* @Description: 画行情折线图的画图器
* @author zhuding    
* @date 2016年4月13日 下午7:38:36    
*
 */
public class MarketGraphPainter {
	
	protected List<MarketVO> marketVOs;
	
	protected TimeSeries marketSeries;
	
	protected TimeSeries benchSeries;
	
	protected TimeSeriesCollection timeSeriesCollection;
	
	protected DateAxis xAxis;
	
	protected NumberAxis yAxis;
	
	protected XYPlot xyPlot;
	
	public MarketGraphPainter(List<MarketVO> marketVOs) {
//		System.out.println("VOs" + marketVOs == null);
		this.marketVOs = marketVOs;
		this.marketSeries = new TimeSeries("行情涨跌幅");
		this.benchSeries = new TimeSeries("大盘涨跌幅");
		this.timeSeriesCollection = new TimeSeriesCollection();
		this.xAxis = new DateAxis();
		this.yAxis = new NumberAxis();
		initdatas();
		paint();
	}
	
	private void initdatas() {
		
		for (MarketVO marketVO : marketVOs) {
//			System.out.print("VO");
//			System.out.println(marketVO == null);
//			System.out.print("VOdate"); 
//			System.out.println(marketVO.getDate() == null);
//			System.out.print("VOMRate");
//			System.out.println(marketVO.getMarketChangeRate() == null);
			marketSeries.add(new Day(marketVO.getDate()), marketVO.getMarketChangeRate().doubleValue());
			benchSeries.add(new Day(marketVO.getDate()), marketVO.getBenchmarkChangeRate().doubleValue());
		}
		timeSeriesCollection.addSeries(marketSeries);
		timeSeriesCollection.addSeries(benchSeries);
	}
	
	private void paint() {
		XYSplineRenderer xyLineRender = GraphComponents.createXYSplineRenderer();
		xyLineRender.setSeriesPaint(0, new Color(255, 128, 255));
		xyLineRender.setSeriesPaint(1, new Color(1, 173, 186));
		
		List<Date> dates = new ArrayList<>();
		for (MarketVO marketVO : marketVOs) {
			dates.add(marketVO.getDate());
		}
		
		xAxis.setRange(DateTool.getMinDate(dates), DateTool.beforeDate(DateTool.getMaxDate(dates), 1));// 设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
		int t = dates.size() / 5;
		t = t == 0 ? 1 : t;
		System.out.println(t);
		xAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, t));// 设置时间刻度的间隔
		xAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());// 设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期
		xAxis.setAutoTickUnitSelection(false);// 设置不采用自动选择刻度值
		xAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);// 设置标记的位置
		xAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());// 设置标准的时间刻度单位
		xAxis.setDateFormatOverride(new SimpleDateFormat("yy-MM-dd"));// 设置显示时间的格式

		double maxValue = ComputingModule.computingMaxValueForTSC(timeSeriesCollection);
		double minValue = ComputingModule.computingMinValueForTSC(timeSeriesCollection);
		yAxis = GraphComponents.createNumberAxis(minValue, maxValue);// 不使用自动设定范围
	
		xyPlot = new XYPlot(timeSeriesCollection, xAxis, yAxis, xyLineRender);
	}
	
	public JPanel getPanel() {
		JFreeChart chart = new JFreeChart(xyPlot);
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}
	
}
