/**
 * Created by zhuding
 */
package presentation.graph.jfreechart;

import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;

/**  
* @ClassName: KLineDecorator    
* @Description: 装饰K线图的装饰器
* @author zhuding    
* @date 2016年4月20日 下午11:23:32    
*        
*/
public abstract class KLineDecorator implements PanelAvailable{
	
	protected PanelAvailable panelAvailable;

	public KLineDecorator(PanelAvailable panelAvailable) {
		super();
		this.panelAvailable = panelAvailable;
	}
	
	/**
	 * @return 获得仅有添加到K线图上的图表的panel
	 */
	public abstract XYPlot getSubPlot();
	
	@Override
	public XYPlot getPlot(){
		CombinedDomainXYPlot combineddomainxyplot = (CombinedDomainXYPlot) panelAvailable.getPlot();
		combineddomainxyplot.add((XYPlot) getSubPlot(), 1);
		combineddomainxyplot.setGap(0);
		return combineddomainxyplot;
	}
	
}
