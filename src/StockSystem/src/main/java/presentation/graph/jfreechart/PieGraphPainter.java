package presentation.graph.jfreechart;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import vo.DataItem;
import vo.GraphDataVO;

/**  
* @ClassName: PieGraphPainter    
* @Description: 饼图的画图器
* @author zhuding    
* @date 2016年6月18日 下午8:37:48    
*        
*/
public class PieGraphPainter {

	protected DefaultPieDataset  dataset;

	public PieGraphPainter(List<?> dataList) {
		dataset = new DefaultPieDataset();
		init(dataList);
	}
	
	@SuppressWarnings("unchecked")
	private void init(List<?> dataCollection){
		if (dataCollection == null) {
			System.err.println("数据集为空");
			return;
		}
		if (dataCollection.size() == 0) {
			System.out.println("数据为空");
			return;
		}
		List<GraphDataVO<String>> dataList = new ArrayList<>();
		for (int i = 0; i < dataCollection.size(); i++) {
			dataList.add((GraphDataVO<String>) dataCollection.get(i));
		}

		for (GraphDataVO<String> dataIterable : dataList) {
			Iterator<DataItem> it = dataIterable.getDataItemIterator();
			dataset.setValue(dataIterable.getVar(),it.next().getData());
		}
	}
	
	public ChartPanel getPanel() {
		JFreeChart chart = ChartFactory.createPieChart("", dataset);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setShadowPaint(null); // 设置绘图面板阴影的填充颜色
		ChartPanel panel = new ChartPanel(chart);
		return panel;
	}
	
}
