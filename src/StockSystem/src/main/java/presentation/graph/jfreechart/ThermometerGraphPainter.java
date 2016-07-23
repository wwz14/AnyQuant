package presentation.graph.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.data.general.DefaultValueDataset;

import presentation.ui.UIConfig;
import vo.DataItem;
import vo.ThermometerDataVO;

/**  
* @ClassName: ThermometerGraphPainter    
* @Description: 温度计图的画图器
* @author zhuding    
*        
*/
public class ThermometerGraphPainter {
	
	public static final int DEFAULT_LOWER_BOUND = 0;
	
	private DefaultValueDataset dataset;
	
	private ThermometerDataVO data;
	
	private int upperBound = DEFAULT_LOWER_BOUND;
	
	private int lowerBound ;
	
	public ThermometerGraphPainter(ThermometerDataVO value) {
		this(value, calMaxRange(value), DEFAULT_LOWER_BOUND);
	}
	
	public ThermometerGraphPainter(ThermometerDataVO value, int upperBound, int lowerBound) {
		this.upperBound = upperBound;
		this.lowerBound = lowerBound;
		this.data = value;
		this.dataset = new DefaultValueDataset();
		dataset.setValue(value.getCurrentPrice());
	}
	
	private static int calMaxRange(ThermometerDataVO value) {
		double max = Double.MIN_VALUE;
		Iterator<DataItem> iterator = value.getDataItemIterator();
		while(iterator.hasNext()){
			DataItem dataItem = iterator.next();
			max = max > dataItem.getData() ? max : dataItem.getData();
		}
		return (int) (max / 0.618);
	}
	
	public JPanel getPanel() {
		ThermometerPlot thermometerplot = new ThermometerPlot(dataset);
        JFreeChart jfreechart = new JFreeChart(null,
                JFreeChart.DEFAULT_TITLE_FONT, thermometerplot, false);
        thermometerplot.setBackgroundPaint(null);
        thermometerplot.setOutlineVisible(false);
        thermometerplot.setLowerBound(lowerBound);
        thermometerplot.setUpperBound(upperBound);
        
        Color color = null;
        if(data.getCurrentPrice() > data.getHQZRSP())
        	color = UIConfig.RED;
        else
        	color = UIConfig.GREEN;
        thermometerplot.setSubrangePaint(lowerBound, color);
        
        thermometerplot.setUnits(ThermometerPlot.UNITS_NONE);
        thermometerplot.setAxisLocation(ThermometerPlot.NONE);
        
		return new MyChartPanel(jfreechart);
	}

	public int getUpperBound() {
		return upperBound;
	}

	public int getLowerBound() {
		return lowerBound;
	}
	
	@SuppressWarnings("serial")
	class MyChartPanel extends ChartPanel{

		public MyChartPanel(JFreeChart chart) {
			super(chart);
		}
		
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			int xStart = this.getWidth() / 8;
			int xEnd = this.getWidth() * 7 / 8;
			int xCenter = this.getWidth() / 2;
			int yCenter = (int) (this.getHeight() * (1 - 0.618));
			
			Font ft = new Font("微软雅黑", Font.BOLD, 13);
			Graphics2D g2 = (Graphics2D) g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setFont(ft);
	        FontMetrics fm = g2.getFontMetrics();
	        
	        int stringHeight = fm.getHeight();
	        
	        String string = null;
	        string = "预计买入价:" + data.getExpectedBuyPrice();
	        g.setColor(UIConfig.GREEN);
	        g.drawString(string, xStart, yCenter - 2 * stringHeight);
	        
	        string = "预计卖出价:" + data.getExpectedSellPrice();
	        g.setColor(UIConfig.RED);
	        g.drawString(string, xStart, yCenter + stringHeight);
	        
	        string = "昨日收盘价:" + data.getHQZRSP();
	        g.setColor(UIConfig.RED);
	        Rectangle2D rc = fm.getStringBounds(string, g2);
	        g.drawString(string, (int) (xEnd - rc.getWidth()), yCenter);
		
	        string = "现价：" + data.getCurrentPrice();
	        Color color = null;
	        if(data.getCurrentPrice() > data.getHQZRSP())
	        	color = UIConfig.RED;
	        else
	        	color = UIConfig.GREEN;
	        g.setColor(color);
	        rc = fm.getStringBounds(string, g2);
	        g.drawString(string, (int) (xCenter - rc.getWidth() / 2), this.getHeight() - stringHeight / 2);
		
		}
		
	}
}
