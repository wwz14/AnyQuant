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
import vo.BollVO;

/**  
* @ClassName: DecoratorBoll    
* @Description: 向K线图添加BOLL图
* @author zhuding    
*        
*/
public class DecoratorBoll extends KLineDecorator{
	
	protected SplineGraphPainter splineGraphPainter;

	public DecoratorBoll(PanelAvailable panelAvailable, List<BollVO> bollVOs) {
		super(panelAvailable);
		this.splineGraphPainter = new SplineGraphPainter();
		splineGraphPainter.addData(bollVOs);
		init();
	}
	
	private void init() {
		splineGraphPainter.getSplineRenderer().setSeriesPaint(0, GraphComponents.COLOR_SET.get("MB"));
		splineGraphPainter.getSplineRenderer().setSeriesPaint(1, GraphComponents.COLOR_SET.get("UP"));
		splineGraphPainter.getSplineRenderer().setSeriesPaint(2, GraphComponents.COLOR_SET.get("DN"));
		splineGraphPainter.getPlot().setBackgroundPaint(Color.WHITE);
	}

	/**
	 * @return 仅有BOLL图的panel
	 */
	public JPanel getBollPanel() {
		return new ChartPanel(new JFreeChart(getSubPlot()));
	}

	@Override
	public JFreeChart getChart() {
		return new JFreeChart(" ", JFreeChart.DEFAULT_TITLE_FONT, getPlot(), false);
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
	public XYPlot getSubPlot() {
		return  splineGraphPainter.getPlot();
	}

}
