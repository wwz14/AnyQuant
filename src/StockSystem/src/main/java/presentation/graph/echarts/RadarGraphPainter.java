package presentation.graph.echarts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.abel533.echarts.Polar;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Radar;
import com.github.abel533.echarts.series.Series;

import presentation.graph.util.GraphHtmlUtil;
import vo.DataItem;
import vo.GraphDataVO;

/**  
* @ClassName: RadarGraphPainter    
* @Description: 绘制雷达图
* @author zhuding    
*        
*/
/**  
* @ClassName: RadarGraphPainter    
* @Description: 
* @author zhuding    
* @date 2016年6月18日 下午8:20:50    
*        
*/
public class RadarGraphPainter implements EChartsPainter{

	protected GsonOption option;

	@SuppressWarnings("rawtypes")
	protected List<Series> dataVOs;
	
	protected String title;
	
	public RadarGraphPainter(GraphDataVO<String> dataVO) {
		this.title = dataVO.getVar();
		this.dataVOs = new ArrayList<>();
		initSetting(dataVO);
		addData(dataVO);
	}
	
	private void initSetting(GraphDataVO<String> dataVO){
		option = new GsonOption();
		Iterator<DataItem> iterator = dataVO.getDataItemIterator();
		Polar polar = new Polar();
		List<Object> datas = new ArrayList<>();
		while(iterator.hasNext()){
			DataItem dataItem = iterator.next();
			datas.add(new Data().text(dataItem.getName()).max(dataItem.getUpperBound()));
		}
		polar.setIndicator(datas);
		option.polar(polar);
//		option.legend(new Legend(dataVO.getVar()));
		option.grid().borderWidth(0).y(60).y2(0).x(0).x2(0);
		option.tooltip(Trigger.item);
		option.toolbox().show(false);
		option.calculable(true);
		option.title(title);
	}
	
	/**
	 * @param title 图表标题
	 */
	public void setTitle(String title){
		this.title = title;
		option.title(title);
	}

	
	/**
	 * 向雷达图中添加数据
	 * @param dataVO 数据
	 */
	public void addData(GraphDataVO<String> dataVO) {
		option.legend(dataVO.getVar());
		System.out.println(option.getLegend().data());;
		Radar radar = new Radar();
		Iterator<DataItem> iterator = dataVO.getDataItemIterator();
		radar.data(new Data().name(dataVO.getVar()).value(iterator.next().getData(), iterator.next().getData(), iterator.next().getData(),
				 iterator.next().getData(), iterator.next().getData()));
		this.dataVOs.add(radar);
		option.setSeries(dataVOs);;
		
	}

	@Override
	public String getURL(int panelWidth, int panelHeight) {
		int width = (int) (panelWidth * 0.96);
		int height = (int) (panelHeight * 0.90);
		String path = GraphHtmlUtil.exportToHtml(option, "html/" + "Radar-" + System.currentTimeMillis() + ".html",
				width, height);
		return "file:///" + path;
	}

}
