package presentation.graph.echarts;

import java.util.ArrayList;
import java.util.List;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;

import presentation.graph.util.GraphHtmlUtil;
import vo.GraphDataVO;
@Deprecated
public class BarGraphPainter implements EChartsPainter{

	private Option option;
	
	private List<GraphDataVO<String>> dataList;
	
	private String name = "";
	
	@SuppressWarnings("unchecked")
	public BarGraphPainter(List<?> dataCollection) {
		this.option = new GsonOption();
		this.dataList  = new ArrayList<>();
		for (int i = 0; i < dataCollection.size(); i++) {
			dataList.add((GraphDataVO<String>) dataCollection.get(i));
		}
		init();
	}
	
	public void setName(String name){
		this.name = name;
	} 
	
	private void init() {
		option.title().text(name);
        option.tooltip().trigger(Trigger.axis);
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView,
                new MagicType(Magic.line, Magic.bar).show(true));
        option.calculable(true);
        
        List<String> names = new ArrayList<>();
        List<Double> datas = new ArrayList<>();
        
        for (GraphDataVO<String> dataVO : dataList) {
			names.add(dataVO.getVar());
			datas.add(dataVO.getDataItemIterator().next().getData());
		}
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setData(names);
        option.xAxis(xAxis);
        option.yAxis(new ValueAxis());

        Bar bar = new Bar(dataList.get(0).getDataItemIterator().next().getName());
        bar.setData(datas);
        
        option.series(bar);
	}

	@Override
	public String getURL(int panelWidth, int panelHeight) {
		int width = (int) (panelWidth * 0.96);
		int height = (int) (panelHeight * 0.90);
		String path = GraphHtmlUtil.exportToHtml(option, "html/" + "Bar-" + System.currentTimeMillis() + ".html",
				width, height);
		return "file:///" + path;
	}

}
