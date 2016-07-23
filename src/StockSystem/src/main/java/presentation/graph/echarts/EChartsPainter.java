package presentation.graph.echarts;

/**  
* @ClassName: EChartsPainter    
* @Description: 表明利用echarts
* @author zhuding    
*        
*/
public interface EChartsPainter {
	/**
	 * @param panelWidth
	 * @param panelHeight
	 * @return
	 */
	public String getURL(int panelWidth, int panelHeight);
}
