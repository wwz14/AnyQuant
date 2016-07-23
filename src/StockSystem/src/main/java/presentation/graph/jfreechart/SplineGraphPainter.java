package presentation.graph.jfreechart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleEdge;

import presentation.graph.util.ComputingModule;
import presentation.graph.util.GraphComponents;
import presentation.graph.util.StandardGraphDataVO;
import utils.DateTool;
import vo.DataItem;
import vo.GraphDataVO;
import vo.MResult;

/**  
* @ClassName: SplineGraphPainter    
* @Description: 折线图的画图器
* @author zhuding    
*        
*/
public class SplineGraphPainter {

	protected TimeSeriesCollection dataSet;
	
	protected DateAxis xAxis;
	
	protected NumberAxis yAxis;
	
	protected XYSplineRenderer splineRenderer;
	
	protected XYPlot xyPlot;
	
	protected List<Date> timeData;
	
	protected boolean ignoreWeekend = true;
	
	public SplineGraphPainter() {
		init();
	}
	
	private void init(){
		this.dataSet = new TimeSeriesCollection();
		this.xAxis = GraphComponents.createDateAxis();
		this.splineRenderer = GraphComponents.createXYSplineRenderer();
		this.xyPlot = new XYPlot();
		this.timeData = new ArrayList<>();
	}
	
	private void setAxisRange() {
		Date start = DateTool.beforeDate(DateTool.getMinDate(timeData), -1);
		Date end = DateTool.beforeDate(DateTool.getMaxDate(timeData), 1);
		xAxis.setRange(start,end);// 设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
		int t = (int) (DateTool.betweenDays(start, end) / 5);
		t = t < 1 ? 1 : t;
		xAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, t));// 设置时间刻度的间隔
		xAxis.setAutoRange(false);
		if(ignoreWeekend)
			xAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());// 设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期
		double maxValue = ComputingModule.computingMaxValueForTSC(dataSet);
		double minValue = ComputingModule.computingMinValueForTSC(dataSet);
		
		yAxis = GraphComponents.createNumberAxis(minValue, maxValue);
		yAxis.setNumberFormatOverride(new DecimalFormat("#0.0000"));
	}
	
	private List<?> dealMResults(List<?> dataCollection){
		List<StandardGraphDataVO<Date>> results = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		for (int i = 0; i < dataCollection.size(); i++) {
			MResult mResult = (MResult) dataCollection.get(i);
			calendar.add(Calendar.DATE, 1);
			StandardGraphDataVO<Date> tempGraphDataVO = new StandardGraphDataVO<Date>(calendar.getTime());
			tempGraphDataVO.add(new DataItem("最大期望", mResult.MaxException));
			tempGraphDataVO.add(new DataItem("平均期望", mResult.MidException));
			tempGraphDataVO.add(new DataItem("最小期望", mResult.MinException));
			results.add(tempGraphDataVO);
		}
		ignoreWeekend  = false;
		return results;
	}
	
	public void setIgnoreWeekend(boolean ignoreWeekend) {
		this.ignoreWeekend = ignoreWeekend;
		setAxisRange();
	}
	
	@SuppressWarnings("unchecked")
	public boolean addData(List<?> dataCollection) {
		if(dataCollection == null){
			System.err.println("数据集为空");
			return false;
		}
		if(dataCollection.size() == 0){
			System.out.println("数据为空");
			return false;
		}
		
		if(dataCollection.get(0) instanceof MResult){
			dataCollection = dealMResults(dataCollection);
		}
		
		List<GraphDataVO<Date>> dataList = new ArrayList<>();
		for (int i = 0; i < dataCollection.size(); i++) {
			dataList.add((GraphDataVO<Date>)dataCollection.get(i));
			System.out.println(dataList.get(i).getDataItemIterator().next().getData());
		}
		
		List<TimeSeries> timeSeriesList = new ArrayList<>();
		Iterator<DataItem> iterator = dataList.get(0).getDataItemIterator();
		while(iterator.hasNext()){
			timeSeriesList.add(new TimeSeries(iterator.next().getName()));
		}
		
		for (GraphDataVO<Date> dataIterable : dataList) {
			Iterator<DataItem> it = dataIterable.getDataItemIterator();
			Date date = dataIterable.getVar();
			timeData.add(date);
			Day day = new Day(date);
//			System.out.println("@@@@@"+date);
			for (int i = 0; i < timeSeriesList.size(); i++) {
				timeSeriesList.get(i).add(day, it.next().getData());
			}
		}
		
		for (TimeSeries timeSeries : timeSeriesList) {
			dataSet.addSeries(timeSeries);
		}
		
		setAxisRange();
		xyPlot.setDataset(0, dataSet);
		xyPlot.setDomainAxis(xAxis);
		xyPlot.setRangeAxis(0, yAxis);
		xyPlot.setRenderer(0, splineRenderer);
		
		return true;
	}
	
	public NumberAxis getYAxis() {
		return yAxis;
	}
	
	public XYPlot getPlot() {
		return xyPlot;
	}
	
	public XYSplineRenderer getSplineRenderer() {
		return splineRenderer;
	}
	
	public void setYPercent(boolean flag) {
		if(yAxis == null)
			return;
		if(flag )
			yAxis.setNumberFormatOverride(new DecimalFormat("##0.00%"));
		else
			yAxis.setNumberFormatOverride(new DecimalFormat("#0.0000"));
	}

	public ChartPanel getPanel(boolean lengendNeeded) {
		
		JFreeChart chart = new JFreeChart(" ", JFreeChart.DEFAULT_TITLE_FONT, xyPlot, lengendNeeded);
		if(lengendNeeded){
			chart.getLegend().setPosition(RectangleEdge.RIGHT);
		}
		ChartPanel panel = new ChartPanel(chart);
		panel.setMouseZoomable(false);

		return panel;
	}
	
	public ChartPanel getPanel() {
		return getPanel(true);
	}
	
	public void clear(){
		init();
	}
	
}
