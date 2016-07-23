package presentation.graph.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import presentation.graph.util.GraphComponents;
import presentation.graph.util.StandardGraphDataVO;
import vo.DataItem;
import vo.GraphDataVO;
import vo.MResult;

/**  
* @ClassName: BarChartPainter    
* @Description: 用于画柱状图
* @author zhuding    
*        
*/
public class BarChartPainter {

	private DefaultCategoryDataset dataset;

	private String graphName = "";
	
	private JPanel panel;
	
	private boolean dataPerc = false;

	private List<?> dealMResult(List<?> dataCollection) {
		List<StandardGraphDataVO<String>> dataVOs = new ArrayList<>();

		for (int i = 0; i < dataCollection.size(); i++) {
			MResult mResult = (MResult) dataCollection.get(i);
			StandardGraphDataVO<String> tempGraphDataVO = new StandardGraphDataVO<String>("第" + (i+1) + "天");
			String[] names = mResult.interval.split(" ");
			tempGraphDataVO.add(new DataItem(names[0], mResult.FirstChance));
			tempGraphDataVO.add(new DataItem(names[1], mResult.SecondChance));
			tempGraphDataVO.add(new DataItem(names[2], mResult.ThirdChance));
			tempGraphDataVO.add(new DataItem(names[3], mResult.ForthChance));
			dataVOs.add(tempGraphDataVO);
		}
		this.setDataPerc(true);
		this.setGraphName("股票区间概率预测");
		return dataVOs;
	}

	public BarChartPainter(List<?> dataCollection) {
		dataset = new DefaultCategoryDataset();
		initDataSet(dataCollection);
	}

	/**
	 * @param graphName 图表标题
	 */
	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}
	
	/**
	 * @param dataPerc 数据是否需要用百分比显示
	 */
	public void setDataPerc(boolean dataPerc) {
		this.dataPerc = dataPerc;
	}
	
	@SuppressWarnings("unchecked")
	private void initDataSet(List<?> dataCollection) {
		if (dataCollection.get(0) instanceof MResult) {
			dataCollection = dealMResult(dataCollection);
		}
		
		List<GraphDataVO<String>> dataList = new ArrayList<>();
		for (int i = 0; i < dataCollection.size(); i++) {
			dataList.add((GraphDataVO<String>) dataCollection.get(i));
		}

		for (int i = 0; i < dataList.size(); i++) {
			GraphDataVO<String> dataIterable = dataList.get(i);
			Iterator<DataItem> it = dataIterable.getDataItemIterator();
			while (it.hasNext()) {
				DataItem dataItem = it.next();
				dataset.addValue(dataItem.getData(), dataItem.getName(), dataIterable.getVar());
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void initComponents() {
		JFreeChart mBarChart = ChartFactory.createBarChart(graphName, // 图表标题
				"", // 横轴（目录轴）标签
				"", // 纵轴（数值轴）标签
				dataset, // 数据集
				PlotOrientation.VERTICAL, // 图表方向
				true, // 是否显示图例
				true, // 是否生成提示工具
				false); // 是否生成url连接
		mBarChart.getPlot().setBackgroundPaint(Color.WHITE);
		mBarChart.getPlot().setOutlinePaint(new Color(143, 143, 143));
		mBarChart.setTitle(new TextTitle(graphName, new Font("宋体", Font.PLAIN, 15)));
		// 图表图例设置
		LegendTitle mLegend = mBarChart.getLegend();
		if (mLegend != null)
			mLegend.setItemFont(new Font("宋体", Font.CENTER_BASELINE, 15));
		// 设置柱状图轴
		CategoryPlot mPlot = (CategoryPlot) mBarChart.getPlot();
		mPlot.setRangeGridlinePaint(new Color(143, 143, 143));

		// x轴
		CategoryAxis mDomainAxis = mPlot.getDomainAxis();
		// 设置x轴标题的字体
		mDomainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 15));
		// 设置x轴坐标字体
		mDomainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 10));
		// y轴
		ValueAxis mValueAxis = mPlot.getRangeAxis();
		// 设置y轴标题字体
		mValueAxis.setLabelFont(new Font("宋体", Font.PLAIN, 15));
		// 设置y轴坐标字体
		mValueAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 15));
		// 柱体显 示数值
		BarRenderer mRenderer = new BarRenderer();
		if (dataPerc) {
			mRenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", GraphComponents.PERC_FORMAT));
		}else {
			mRenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", GraphComponents.DEC_FORMAT));
		}
		mRenderer.setItemLabelFont(new Font("宋体", Font.PLAIN, 10));
		mRenderer.setItemLabelsVisible(true);
		mRenderer.setBaseToolTipGenerator(
				new StandardCategoryToolTipGenerator("{1}: {2}", GraphComponents.DEC_FORMAT));
		mRenderer.setShadowVisible(false);
		mRenderer.setBarPainter(new StandardBarPainter());
		mRenderer.setSeriesPaint(0, GraphComponents.BLUE_TO_RED.get(0));
		mRenderer.setSeriesPaint(1, GraphComponents.BLUE_TO_RED.get(1));
		mRenderer.setSeriesPaint(2, GraphComponents.BLUE_TO_RED.get(2));
		mRenderer.setSeriesPaint(3, GraphComponents.BLUE_TO_RED.get(3));
		mPlot.setRenderer(mRenderer);
		panel = new ChartPanel(mBarChart);
	}
	
	/**
	 * @return 包含图表的panel
	 */
	public JPanel getPanel() {
		initComponents();
		return panel;
	}
	

}
