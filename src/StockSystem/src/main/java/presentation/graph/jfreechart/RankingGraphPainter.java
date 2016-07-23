package presentation.graph.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import presentation.graph.util.GraphComponents;
import vo.DataItem;
import vo.GraphDataVO;

/**  
* @ClassName: RankingGraphPainter    
* @Description: 排行图的画图器（基本同BarGraphPatinter）
* @author zhuding    
*        
*/
public class RankingGraphPainter {

	protected DefaultCategoryDataset dataset;

	protected String subTitle = "";

	public RankingGraphPainter(List<?> dataList) {
		dataset = new DefaultCategoryDataset();
		init(dataList);
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	@SuppressWarnings("unchecked")
	private void init(List<?> dataCollection) {
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
			dataset.addValue(it.next().getData(), "", dataIterable.getVar());
		}

	}
	
	protected JFreeChart setDetail() {
		JFreeChart chart = ChartFactory.createBarChart("", "股票名称", subTitle, dataset);
		chart.getLegend().setVisible(false);
		
		CategoryPlot  plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.GRAY);
		plot.setRangeGridlinesVisible(true);
		
		BarRenderer renderer = new BarRenderer(){

			private static final long serialVersionUID = 1L;
			
			public Paint getItemPaint(int i, int j){
				Random r = new Random();
				return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
			}
			
		};
		renderer.setBaseToolTipGenerator(
				new StandardCategoryToolTipGenerator("{1}: {2}", GraphComponents.DEC_FORMAT));
		renderer.setShadowVisible(false);
		renderer.setBarPainter(new StandardBarPainter());
		plot.setRenderer(renderer);
		
		CategoryAxis domainAxis = plot.getDomainAxis();
		/*------设置X轴坐标上的文字-----------*/
		domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 11));
		/*------设置X轴的标题文字------------*/
		domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 12));
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		/*------设置Y轴坐标上的文字-----------*/
		numberaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));
		/*------设置Y轴的标题文字------------*/
		numberaxis.setLabelFont(new Font("宋体", Font.BOLD, 12));
		double maxValue = Double.MIN_VALUE;
		double minValue = Double.MAX_VALUE;
		for (int i = 0; i < dataset.getColumnCount(); i++) {
			for (int j = 0; j < dataset.getRowCount(); j++) {
				if(dataset.getValue(j, i).doubleValue() > maxValue)
					maxValue = dataset.getValue(j, i).doubleValue();
				if(dataset.getValue(j, i).doubleValue() < minValue)
					minValue = dataset.getValue(j, i).doubleValue();
			}
		}
		double lowBound = minValue > 0 ? minValue * 0.9 :minValue * 1.1;
		double upBound = maxValue > 0 ? maxValue * 1.1 : maxValue * 0.9; 
		numberaxis.setRange(lowBound,upBound);
		numberaxis.setTickUnit(new NumberTickUnit((upBound-lowBound)/ 10));
		return chart;
	}

	public ChartPanel getPanel() {
		ChartPanel chartPanel = new ChartPanel(setDetail(), false);
		return chartPanel;
	}
}
