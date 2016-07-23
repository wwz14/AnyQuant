package presentation.graph.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.renderer.xy.XYSplineRenderer;

/**  
* @ClassName: GraphComponents    
* @Description: 绘图常用组件
* @author zhuding    
*        
*/
public class GraphComponents {
	
	/**
	 * 用于存放各指标对应的颜色
	 */
	public static final HashMap<String, Color> COLOR_SET;
	
	static{
		COLOR_SET = new HashMap<>();
		COLOR_SET.put("MA5", new Color(252, 156, 184));
		COLOR_SET.put("MA20", new Color(31, 192, 219));
		COLOR_SET.put("MA30", new Color(241, 86, 184));
		COLOR_SET.put("MA60", new Color(161, 199, 58));
		COLOR_SET.put("MAVOL5", new Color(252, 156, 184));
		COLOR_SET.put("MAVOL10", new Color(31, 192, 219));
		COLOR_SET.put("DEA", new Color(210, 61, 194));
		COLOR_SET.put("DIF", new Color(0, 193, 235));
		COLOR_SET.put("K", new Color(143, 143, 143));
		COLOR_SET.put("D", new Color(255, 176, 17));
		COLOR_SET.put("J", new Color(210, 61, 194));
		COLOR_SET.put("ATR", new Color(255, 0, 255));
		COLOR_SET.put("MB", new Color(143, 143, 143));
		COLOR_SET.put("UP", new Color(255, 176, 17));
		COLOR_SET.put("DN", new Color(210, 61, 194));
	}
	
	public static final List<Color> YELLOW_TO_RED;
	
	static{
		YELLOW_TO_RED = new ArrayList<>();
		YELLOW_TO_RED.add(new Color(235, 245, 82));
		YELLOW_TO_RED.add(new Color(255, 222, 62));
		YELLOW_TO_RED.add(new Color(255, 132, 63));
		YELLOW_TO_RED.add(new Color(245, 80, 67));
	}
	
	public static final List<Color> BLUE_TO_RED;
	
	static{
		BLUE_TO_RED = new ArrayList<>();
		BLUE_TO_RED.add(new Color(49, 134, 245));
		BLUE_TO_RED.add(new Color(43, 255, 145));
		BLUE_TO_RED.add(new Color(255, 180, 38));
		BLUE_TO_RED.add(new Color(245, 58, 36));
	}
	
	public static final int COLOR_SERIES_SIZE = 4;
	
	public static final DecimalFormat DEC_FORMAT = new DecimalFormat("#0.000");
	
	public static final DecimalFormat PERC_FORMAT = new DecimalFormat("#0.00%");
	
	private GraphComponents() {}
	
	public static NumberAxis createNumberAxis(double minDataValue, double maxDataValue) {
		NumberAxis numberAxis = new NumberAxis();
		numberAxis.setAutoRange(false);
		double lowBound = minDataValue > 0 ? minDataValue * 0.9 :minDataValue * 1.1;
		double upBound = maxDataValue > 0 ? maxDataValue * 1.1 : maxDataValue * 0.9; 
		numberAxis.setRange(lowBound,upBound);
		numberAxis.setTickUnit(new NumberTickUnit((upBound-lowBound)/ 10));
		numberAxis.setNumberFormatOverride(DEC_FORMAT);
		return numberAxis;
	}
	
	
	@SuppressWarnings("deprecation")
	public static XYSplineRenderer createXYSplineRenderer() {
		XYSplineRenderer xyLineRender = new XYSplineRenderer();
		xyLineRender.setBaseToolTipGenerator(new StandardXYToolTipGenerator("{0}: ({1}, {2})",
				new SimpleDateFormat("yyyy-MM-dd"), new DecimalFormat("0.00")));
		xyLineRender.setPrecision(5);
		xyLineRender.setBaseShapesVisible(false);
		xyLineRender.setStroke(new BasicStroke(2.0F));
		return xyLineRender;
	}
	
	public static DateAxis createDateAxis() {
		DateAxis dateAxis = new DateAxis();
		dateAxis.setAutoTickUnitSelection(false);// 设置不采用自动选择刻度值
		dateAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);// 设置标记的位置
		dateAxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());// 设置标准的时间刻度单位
		dateAxis.setDateFormatOverride(new SimpleDateFormat("yy-MM-dd"));// 设置显示时间的格式
		return dateAxis;
	}
}
