package presentation.graph.echarts;

import java.util.ArrayList;
import java.util.List;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Funnel;
import com.github.abel533.echarts.series.Pie;

import presentation.graph.util.GraphHtmlUtil;
import vo.DataItem;
import vo.GraphDataVO;

public class PieGraphPainter implements EChartsPainter {

	private Option option;
	
	private String name;

	@Deprecated
	public PieGraphPainter(String name,List<?> dataList){
		option = new Option();
		this.name = name;
		addData(dataList);
	}
	
	@SuppressWarnings("unchecked")
	private void addData(List<?> dataCollection){
		option.title().text(name);
		option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
		option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore,
				new MagicType(Magic.pie, Magic.funnel).option(new MagicType.Option()
						.funnel(new Funnel().x("25%").width("50%").funnelAlign(X.left).max(1548))));
	
		
		List<GraphDataVO<String>> dataList = new ArrayList<>();
		for (int i = 0; i < dataCollection.size(); i++) {
			dataList.add((GraphDataVO<String>) dataCollection.get(i));
		}

		List<String> dataNames = new ArrayList<>();
		List<PieData> pieDatas = new ArrayList<>();
		for (GraphDataVO<String> dataIterable : dataList) {
			DataItem dataItem =  dataIterable.getDataItemIterator().next();
			dataNames.add(dataItem.getName());
			pieDatas.add(new PieData(dataItem.getName(), dataItem.getData()));
		}
		option.legend().data(dataNames);
		Pie pie = new Pie();
		pie.setData(pieDatas);
		option.series(pie);
	}

	@Override
	public String getURL(int panelWidth, int panelHeight) {
		int width = (int) (panelWidth * 0.96);
		int height = (int) (panelHeight * 0.96);
		String path = GraphHtmlUtil.exportToHtml(option, "html/" + "Pie-" + System.currentTimeMillis() + ".html",
				width, height);
		return "file:///" + path;
	}

}
