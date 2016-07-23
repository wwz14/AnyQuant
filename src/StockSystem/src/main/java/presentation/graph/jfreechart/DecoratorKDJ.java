package presentation.graph.jfreechart;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;

import presentation.graph.util.GraphComponents;
import vo.KDJVO;

/**  
* @ClassName: DecoratorKDJ    
* @Description: 向K线图添加KDJ图
* @author zhuding    
*        
*/
public class DecoratorKDJ extends KLineDecorator{
	
	protected SplineGraphPainter splineGraphPainter;

	public DecoratorKDJ(PanelAvailable panelAvailable, List<KDJVO> dataCollection) {
		super(panelAvailable);
		this.splineGraphPainter = new SplineGraphPainter();
		splineGraphPainter.addData(dataCollection);
		init();
	}

	private void init() {
		splineGraphPainter.getSplineRenderer().setSeriesPaint(0, GraphComponents.COLOR_SET.get("K"));
		splineGraphPainter.getSplineRenderer().setSeriesPaint(1, GraphComponents.COLOR_SET.get("D"));
		splineGraphPainter.getSplineRenderer().setSeriesPaint(2, GraphComponents.COLOR_SET.get("J"));
		splineGraphPainter.getPlot().setBackgroundPaint(Color.WHITE);
	}

	@Override
	public NumberAxis getNumberAxis() {
		return splineGraphPainter.getYAxis();
	}

	@Override
	public XYPlot getSubPlot() {
		return splineGraphPainter.getPlot();
	}

	@Override
	public DateAxis getDateAxis() {
		return panelAvailable.getDateAxis();
	}

	@Override
	public JFreeChart getChart() {
		return new JFreeChart(" ", JFreeChart.DEFAULT_TITLE_FONT, getPlot(), false);
	}
	
	/**
	 * @return 仅有KDJ图的panel
	 */
	public JPanel getKDJPanel() {
		return new ChartPanel(new JFreeChart(getSubPlot()));
	}

}
