package presentation.graph.jfreechart;

import java.awt.Color;
import java.awt.Paint;
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
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYBarDataset;

import presentation.graph.util.ComputingModule;
import presentation.graph.util.GraphComponents;
import presentation.ui.UIConfig;
import utils.DateTool;
import vo.MacdVO;

/**  
* @ClassName: DecoratorMACD    
* @Description: 向K线图添加MACD图
* @author zhuding    
*        
*/
public class DecoratorMACD extends KLineDecorator{
	
	protected XYBarRenderer xyBarRender;
	
	protected XYSplineRenderer xyLineRender;
	
	protected NumberAxis yAxis;
	
	protected XYPlot subPlot;
	
	protected List<Date> dates;

	public DecoratorMACD(PanelAvailable panelAvailable, List<MacdVO> macdVOs) {
		super(panelAvailable);
		dates = new ArrayList<>();
		init(macdVOs);
	}

	private void init(List<MacdVO> macdVOs) {
		final TimeSeriesCollection macdDataSet = getMACDDataset(macdVOs);

		double maxMacd = ComputingModule.computingMaxValueForTSC(macdDataSet);
		double minMacd = ComputingModule.computingMinValueForTSC(macdDataSet);
		double maxRange = maxMacd > -minMacd ? maxMacd : -minMacd;

		yAxis = GraphComponents.createNumberAxis(-maxRange , maxRange);

		xyBarRender = new XYBarRenderer() {
			private static final long serialVersionUID = 1L;

			public Paint getItemPaint(int i, int j) {
				if (macdDataSet.getYValue(i, j) > 0) {
					return Color.RED;
				} else {
					return UIConfig.GREEN;
				}
			}
		};
		
		xyBarRender.setMargin(0.1);// 设置柱形图之间的间隔
		xyBarRender.setShadowVisible(false);

		TimeSeriesCollection seriesCollectionForMACD = new TimeSeriesCollection();
		seriesCollectionForMACD.addSeries(macdDataSet.getSeries(0));

		xyLineRender = GraphComponents.createXYSplineRenderer();
		xyLineRender.setSeriesPaint(0, GraphComponents.COLOR_SET.get("DEA"));
		xyLineRender.setSeriesPaint(1, GraphComponents.COLOR_SET.get("DIF"));

		TimeSeriesCollection seriesCollectionForOther = new TimeSeriesCollection();
		seriesCollectionForOther.addSeries(macdDataSet.getSeries(1));
		seriesCollectionForOther.addSeries(macdDataSet.getSeries(2));

		XYBarDataset xyBarDataset = new XYBarDataset(seriesCollectionForMACD, 0.0);
		subPlot = new XYPlot(xyBarDataset, null, yAxis, xyBarRender);
		subPlot.setRenderer(1, xyLineRender);
		subPlot.setDataset(1, seriesCollectionForOther);

		subPlot.setBackgroundPaint(Color.WHITE);
	}

	@Override
	public JFreeChart getChart() {
		return new JFreeChart(getPlot());
	}

	@Override
	public NumberAxis getNumberAxis() {
		return yAxis;
	}
	
	/**
	 * @return 仅有MACD图的panel
	 */
	public JPanel getMacdPanel() {
		subPlot.setDomainAxis(getDateAxis());
		return new ChartPanel(new JFreeChart(null, null, getSubPlot(), false));
	}

	@Override
	public XYPlot getPlot() {
		CombinedDomainXYPlot combineddomainxyplot = (CombinedDomainXYPlot) panelAvailable.getPlot();
		combineddomainxyplot.add((XYPlot) getSubPlot(), 1);
		combineddomainxyplot.setGap(0);
		return combineddomainxyplot;
	}

	@Override
	public DateAxis getDateAxis() {
		if (panelAvailable != null) {
			return panelAvailable.getDateAxis();
		}
		else{
			DateAxis xAxis = new DateAxis();
			Date start = DateTool.getMinDate(dates);
			Date end = DateTool.beforeDate(DateTool.getMaxDate(dates), 1);
			int t = (int) (DateTool.betweenDays(start, end) / 5);
			t = t < 1 ? 1 : t;
			xAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, t));// 设置时间刻度的间隔
			xAxis.setRange(start,end);// 设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
			xAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());// 设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期
			xAxis.setAutoTickUnitSelection(false);// 设置不采用自动选择刻度值
			xAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);// 设置标记的位置
			xAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());// 设置标准的时间刻度单位
			xAxis.setDateFormatOverride(new SimpleDateFormat("yy-MM-dd"));// 设置显示时间的格式
			return xAxis;
		}
	}

	@Override
	public XYPlot getSubPlot() {
		return subPlot;
	}

	/**
	 * @param macdVOs macd的原始数据
	 * @return macd的数据集
	 */
	protected TimeSeriesCollection getMACDDataset(List<MacdVO> macdVOs) {
		if (macdVOs == null) {
			System.err.println("没有macd数据");
			return null;
		}
		TimeSeries[] macdDatas = new TimeSeries[3];
		for (int i = 0; i < macdDatas.length; i++) {
			macdDatas[i] = new TimeSeries("");
		}

		for (MacdVO macdVO : macdVOs) {
			RegularTimePeriod time = new Day(macdVO.getDate());
			macdDatas[0].add(time, macdVO.getMacd());
			macdDatas[1].add(time, macdVO.getDea());
			macdDatas[2].add(time, macdVO.getDif());
			dates.add(macdVO.getDate());
		}

		TimeSeriesCollection macdDataSet = new TimeSeriesCollection();
		for (TimeSeries timeSeries : macdDatas) {
			macdDataSet.addSeries(timeSeries);
		}
		return macdDataSet;
	}
	
}
