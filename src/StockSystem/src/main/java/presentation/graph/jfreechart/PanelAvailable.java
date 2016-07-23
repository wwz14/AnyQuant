/**
 * Created by zhuding
 */
package presentation.graph.jfreechart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;

/**  
* @ClassName: PanelAvailable    
* @Description: 
* @author zhuding    
* @date 2016年4月20日 下午7:26:24    
*        
*/
public interface PanelAvailable {
	/**
	 * @return 获得所画图的JFreeChart
	 */
	public JFreeChart getChart();
	
	/**
	 * @return 获得使用的数据轴（y轴）
	 */
	public NumberAxis getNumberAxis();
	
	/**
	 * @return 获得使用的plot对象
	 */
	public XYPlot getPlot();
	
	/**
	 * @return 获得使用的时间轴（x轴）
	 */
	public DateAxis getDateAxis();
}
