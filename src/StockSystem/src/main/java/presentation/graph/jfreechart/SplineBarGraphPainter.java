package presentation.graph.jfreechart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import presentation.graph.util.ComputingModule;
import presentation.graph.util.GraphComponents;
import presentation.graph.util.StandardGraphDataVO;
import vo.DataItem;
import vo.GraphDataVO;
import vo.RegressionVO;

/**  
* @ClassName: SplineBarGraphPainter    
* @Description: 折线图和柱状图结合的画图器
* @author zhuding    
*        
*/
public class SplineBarGraphPainter extends SplineGraphPainter{

	protected TimeSeriesCollection barDataset;
	
	protected NumberAxis y2Axis; 
	
	protected XYBarRenderer xyBarRenderer;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SplineBarGraphPainter(List<RegressionVO> dataCollection) {
		this(dataCollection, "误差值");
		List<GraphDataVO<Date>> spList = new ArrayList<>();
		for (RegressionVO regressionVO : dataCollection) {
			StandardGraphDataVO graphDataVO = new StandardGraphDataVO(regressionVO.getVar());
			Iterator<DataItem> iterator = regressionVO.getDataItemIterator();
			while(iterator.hasNext()){
				DataItem dataItem = iterator.next();
				if(dataItem.getName().equals("误差值")){
					continue;
				}
				graphDataVO.add(dataItem);
			}
			spList.add(graphDataVO);
		}
		this.addData(spList);
 	}
	
	public SplineBarGraphPainter(List<?> dataCollection, String varName) {
		super();
		initBarData(dataCollection, varName);
	}
	
	@SuppressWarnings("unchecked")
	private void initBarData(List<?> dataCollection, String varName){
		this.barDataset = new TimeSeriesCollection();
		this.xyBarRenderer = new XYBarRenderer();
		
		List<GraphDataVO<Date>> dataVOs = new ArrayList<>();
		for (int i = 0; i < dataCollection.size(); i++) {
			dataVOs.add((GraphDataVO<Date>)dataCollection.get(i));
		}
		
		TimeSeries timeSeries = new TimeSeries(varName);
		for (GraphDataVO<Date> dataIterable : dataVOs) {
			Iterator<DataItem> it = dataIterable.getDataItemIterator();
			Date date = dataIterable.getVar();
			Day day = new Day(date);
			DataItem item = it.next();
			while(it.hasNext()){ 
				if(item.getName().equals(varName))
					break;
				item = it.next();
			}
			timeSeries.add(day, item.getData());
		}
		
		barDataset.addSeries(timeSeries);
		
		double maxValue = ComputingModule.computingMaxValueForTSC(barDataset);
		y2Axis = GraphComponents.createNumberAxis(0, maxValue);
		y2Axis.setNumberFormatOverride(new DecimalFormat("#0.0000"));
		
		xyBarRenderer.setMargin(0.3);// 设置柱形图之间的间隔
		xyBarRenderer.setShadowVisible(false);
		xyBarRenderer.setBarPainter(new StandardXYBarPainter());
		xyBarRenderer.setDrawBarOutline(false);
	}
	
	public ChartPanel getChartPanel(boolean lengendNeeded){
		xyPlot.setDataset(1,barDataset);
		xyPlot.setRenderer(1,xyBarRenderer);
		xyPlot.setRangeAxis(1,y2Axis);
		y2Axis = (NumberAxis) xyPlot.getRangeAxis(1);
		double max = y2Axis.getUpperBound() * 10;
		double min = y2Axis.getLowerBound();
		y2Axis.setRange(min, max);
		y2Axis.setTickUnit(new NumberTickUnit((max - min) / 10));;
		
		xyPlot.mapDatasetToRangeAxis(1, 1);
		
		return super.getPanel(lengendNeeded);
	}
	
}
