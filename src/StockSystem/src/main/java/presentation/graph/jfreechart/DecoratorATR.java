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
import vo.StockStatisticVO;

/**  
* @ClassName: DecoratorATR    
* @Description: 向K线图添加ATR图
* @author zhuding    
*        
*/
public class DecoratorATR extends KLineDecorator{
	
	protected SplineGraphPainter splineGraphPainter;

	public DecoratorATR(PanelAvailable panelAvailable,List<StockStatisticVO> statisticVOs) {
		super(panelAvailable);
		this.splineGraphPainter = new SplineGraphPainter();
		splineGraphPainter.addData(statisticVOs);
		init();
	}

	private void init() {
		splineGraphPainter.getSplineRenderer().setSeriesPaint(0, GraphComponents.COLOR_SET.get("ATR"));
		splineGraphPainter.getPlot().setBackgroundPaint(Color.WHITE);
	}

	public XYPlot getSubPlot() {
		return splineGraphPainter.getPlot();
	}
	
	@Override
	public NumberAxis getNumberAxis() {
		return splineGraphPainter.getYAxis();
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
	 * @return 仅有ATR图的panel
	 */
	public JPanel getATRPanel() {
		return new ChartPanel(new JFreeChart(getSubPlot()));
	}
	
}
