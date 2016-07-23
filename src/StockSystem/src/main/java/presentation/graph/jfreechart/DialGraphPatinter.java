package presentation.graph.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;

/**  
* @ClassName: DialGraphPatinter    
* @Description: 画温度计图使用
* @author zhuding    
*        
*/
/**  
* @ClassName: DialGraphPatinter    
* @Description: 
* @author zhuding    
* @date 2016年6月18日 下午8:31:56    
*        
*/
public class DialGraphPatinter {

	private double value;

	private double lowerBound;

	private double upperBound;

	private DefaultValueDataset dataset;

	private JPanel panel;
	
	private String name = "";
	
	private String formatterString = "##0.0";
	
	public DialGraphPatinter(double value) {
		this(value, 0, 100);
	}

	public DialGraphPatinter(double value, double lowerBound, double upperBound) {
		super();
		this.value = value;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.dataset = new DefaultValueDataset(value);
	}
	
	/**
	 * @param name 图表标题
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param formatter 数字显示的格式
	 */
	public void setFormatter(String formatter) {
		this.formatterString = formatter;
	}

	private void init() {
		DialPlot dialplot = new DialPlot();
		dialplot.setDataset(dataset);
		dialplot.setBackgroundPaint(Color.WHITE);
		
		StandardDialFrame standarddialframe = new StandardDialFrame();
		standarddialframe.setVisible(false);
		dialplot.setDialFrame(standarddialframe);
		
		// 设置显示在表盘中央位置的信息
		DialTextAnnotation dialtextannotation = new DialTextAnnotation(name);
		dialtextannotation.setFont(new Font("微软雅黑", 17, 17));
		dialtextannotation.setRadius(0.6D);// 字体距离圆心的距离
		dialplot.addLayer(dialtextannotation);
		
		DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
		dialvalueindicator.setBackgroundPaint(dialplot.getBackgroundPaint());
		dialvalueindicator.setOutlinePaint(dialplot.getBackgroundPaint());
		dialvalueindicator.setNumberFormat(new DecimalFormat(formatterString));
		dialplot.addLayer(dialvalueindicator);
		
		// 根据表盘的直径大小（0.88），设置总刻度范围
		StandardDialScale standarddialscale = new StandardDialScale(lowerBound, upperBound, -135.0D, -270.0D, (upperBound - lowerBound) / 10, 9);
		standarddialscale.setTickRadius(0.9D);
		standarddialscale.setTickLabelOffset(0.1D);// 显示数字 距离圆边的距离
		standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
		// 主意是 dialplot.addScale（）不是dialplot.addLayer（）
		dialplot.addScale(0, standarddialscale);

		double d1 = 0.5;
		double d2 = 0.3;
		double point1 = lowerBound + (upperBound - lowerBound) * d1;
		double point2 = lowerBound + (upperBound - lowerBound) * (d2 + d1);
		// 设置刻度范围（红色）
		StandardDialRange standarddialrange = new StandardDialRange(lowerBound, point1, Color.green);
		standarddialrange.setInnerRadius(0.6D);
		standarddialrange.setOuterRadius(0.62D);
		dialplot.addLayer(standarddialrange);
		// 设置刻度范围（橘黄色）
		StandardDialRange standarddialrange1 = new StandardDialRange(point1, point2 , Color.orange);
		standarddialrange1.setInnerRadius(0.6D);// 半径返回 两条线
		standarddialrange1.setOuterRadius(0.62D);
		dialplot.addLayer(standarddialrange1);
		// 设置刻度范围（绿色）
		StandardDialRange standarddialrange2 = new StandardDialRange(point2, upperBound, Color.red);
		standarddialrange2.setInnerRadius(0.6);
		standarddialrange2.setOuterRadius(0.62);
		dialplot.addLayer(standarddialrange2);
		
		
		DialPointer.Pin dialPointer = new DialPointer.Pin();
		Paint color = null;
		if(value < point1)
			color = standarddialrange.getPaint();
		else if(value > point2)
			color = standarddialrange2.getPaint();
		else
			color = standarddialrange1.getPaint();
		dialPointer.setPaint(color);
		dialplot.addPointer(dialPointer);

		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, dialplot, false);
		panel = new ChartPanel(chart);
		
	}

	/**
	 * @return 获得仪表盘图的panel
	 */
	public JPanel getPanel() {
		init();
		panel.setBackground(Color.WHITE);
		return panel;
	}
}
