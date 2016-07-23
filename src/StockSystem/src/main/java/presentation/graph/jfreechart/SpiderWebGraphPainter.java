package presentation.graph.jfreechart;

import java.awt.Font;
import java.util.Iterator;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

import presentation.graph.util.CalibrationSpiderWebPlot;
import vo.DataItem;
import vo.GraphDataVO;

public class SpiderWebGraphPainter {

	protected DefaultCategoryDataset defaultcategorydataset;
	
	protected String title = "";
	
	public SpiderWebGraphPainter(GraphDataVO<String> dataVO) {
		defaultcategorydataset = new DefaultCategoryDataset();
		addData(dataVO);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	private JFreeChart initSettings(){
		SpiderWebPlot spiderwebplot = new CalibrationSpiderWebPlot(defaultcategorydataset);
        spiderwebplot.setStartAngle(-54D);
        spiderwebplot.setInteriorGap(0.4D);
        spiderwebplot.setBackgroundPaint(null);
        spiderwebplot.setOutlinePaint(null);
        spiderwebplot.setLabelFont(new Font("宋体", Font.CENTER_BASELINE, 15));
        spiderwebplot
                .setToolTipGenerator(new StandardCategoryToolTipGenerator());
        JFreeChart jfreechart = new JFreeChart(title,
                new Font("宋体", Font.PLAIN, 20), spiderwebplot, true);
        
        LegendTitle mLegend = jfreechart.getLegend();
		if (mLegend != null){
			mLegend.setItemFont(new Font("宋体", Font.CENTER_BASELINE, 15));
			mLegend.setPosition(RectangleEdge.RIGHT);
		}
		
        return jfreechart;
	}
	
	public void addData(GraphDataVO<String> dataVO){
		String legendString = dataVO.getVar();
		Iterator<DataItem> iterator = dataVO.getDataItemIterator();
		while(iterator.hasNext()){
			DataItem dataItem = iterator.next();
			defaultcategorydataset.addValue(dataItem.getData(), legendString, dataItem.getName());
		}
	}
	
	public JPanel getPanel() {
		return new ChartPanel(initSettings());
	}
	
}
