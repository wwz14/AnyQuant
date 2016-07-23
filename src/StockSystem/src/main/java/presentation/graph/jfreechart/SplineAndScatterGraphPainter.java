package presentation.graph.jfreechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import presentation.graph.util.ComputingModule;
import presentation.graph.util.StandardGraphDataVO;
import vo.DataItem;
import vo.GraphDataVO;
import vo.PolyFitVO;

/**  
* @ClassName: SplineAndScatterGraphPainter    
* @Description: 折线图和散点图结合的画图器
* @author zhuding    
*        
*/
public class SplineAndScatterGraphPainter {

	protected XYSeriesCollection scatterDataSet;

	protected XYSeriesCollection lineDataSet;

	protected XYDotRenderer xyDotRenderer;

	protected XYSplineRenderer splineRenderer;

	protected XYPlot xyPlot;

	protected NumberAxis xAxis;

	protected NumberAxis yAxis;

	public SplineAndScatterGraphPainter(PolyFitVO data) {
		scatterDataSet = new XYSeriesCollection();
		lineDataSet = new XYSeriesCollection();
		xyDotRenderer = new XYDotRenderer();
		splineRenderer = new XYSplineRenderer();
		xAxis = new NumberAxis();
		yAxis = new NumberAxis();

		List<GraphDataVO<Double>> splineData = new ArrayList<>();
		List<GraphDataVO<Double>> scatterData = new ArrayList<>();

		if (data.curveX.size() != data.pre.size())
			System.err.println("数据数目不匹配");

		for (int i = 0; i < data.act.size(); i++) {
			scatterData.add(new StandardGraphDataVO<Double>((double) i+1).add(new DataItem("真实值", data.act.get(i))));
		}
		for (int i = 0; i < data.pre.size(); i++) {
			splineData.add(new StandardGraphDataVO<Double>(data.curveX.get(i)).add(new DataItem("拟合曲线", data.pre.get(i))));
		}
		this.addLineData(splineData);
		this.addScatterData(scatterData);
		init();
	}

	private void init() {
		yAxis.setRange(
				Math.min(ComputingModule.computingMinValueForXYSC(scatterDataSet),
						ComputingModule.computingMinValueForXYSC(lineDataSet)) * 0.99,
				Math.max(ComputingModule.computingMaxValueForXYSC(scatterDataSet), ComputingModule.computingMaxValueForXYSC(lineDataSet))*1.01);
		xyDotRenderer.setDotWidth(8);
		xyDotRenderer.setDotHeight(8);
		xyDotRenderer.setSeriesPaint(0, new Color(242, 76, 38));
		splineRenderer.setPrecision(5);
		splineRenderer.setBaseShapesVisible(false);
		splineRenderer.setSeriesStroke(0, new BasicStroke(3.0F));
		splineRenderer.setSeriesPaint(0, new Color(243, 250, 30));

		xyPlot = new XYPlot(scatterDataSet, xAxis, yAxis, xyDotRenderer);
		xyPlot.setDataset(xyPlot.getDatasetCount(), lineDataSet);
		xyPlot.setRenderer(xyPlot.getRendererCount(), splineRenderer);

	}

	@SuppressWarnings("unchecked")
	protected void addLineData(List<?> dataCollection) {
		List<GraphDataVO<Double>> dataList = new ArrayList<>();
		for (int i = 0; i < dataCollection.size(); i++) {
			dataList.add((GraphDataVO<Double>) dataCollection.get(i));
		}

		List<XYSeries> xySeriesList = new ArrayList<>();
		Iterator<DataItem> iterator = dataList.get(0).getDataItemIterator();
		while (iterator.hasNext()) {
			xySeriesList.add(new XYSeries(iterator.next().getName()));
		}

		for (GraphDataVO<Double> dataIterable : dataList) {
			Iterator<DataItem> it = dataIterable.getDataItemIterator();
			double x = dataIterable.getVar();
			for (int i = 0; i < xySeriesList.size(); i++) {
				xySeriesList.get(i).add(x, it.next().getData());
			}
		}

		for (XYSeries xySeries : xySeriesList) {
			lineDataSet.addSeries(xySeries);
		}
	}

	@SuppressWarnings("unchecked")
	protected void addScatterData(List<?> dataCollection) {
		List<GraphDataVO<Double>> dataList = new ArrayList<>();
		for (int i = 0; i < dataCollection.size(); i++) {
			dataList.add((GraphDataVO<Double>) dataCollection.get(i));
		}

		List<XYSeries> xySeriesList = new ArrayList<>();
		Iterator<DataItem> iterator = dataList.get(0).getDataItemIterator();
		while (iterator.hasNext()) {
			xySeriesList.add(new XYSeries(iterator.next().getName()));
		}

		for (GraphDataVO<Double> dataIterable : dataList) {
			Iterator<DataItem> it = dataIterable.getDataItemIterator();
			double x = dataIterable.getVar();
			for (int i = 0; i < xySeriesList.size(); i++) {
				xySeriesList.get(i).add(x, it.next().getData());
			}
		}

		for (XYSeries xySeries : xySeriesList) {
			scatterDataSet.addSeries(xySeries);
		}
	}

	public JPanel getPanel() {
		return new ChartPanel(new JFreeChart(xyPlot));
	}
}
