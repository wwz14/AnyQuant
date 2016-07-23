package presentation.graph.echarts;

import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Gauge;
import com.github.abel533.echarts.series.gauge.Detail;

import presentation.graph.util.GraphHtmlUtil;
@Deprecated
public class GaugeGraphPainter implements EChartsPainter{
	
	private int upperBound;
	
	private double value;
	
	private int lowerBound;
	
	private String name;
	
	private String formatterString;
	
	private GsonOption option;
	
	public GaugeGraphPainter(double value, String name) {
		this(value,name, 0, 100);
	}

	public GaugeGraphPainter(double value, String name, int lowerBound,int upperBound) {
		super();
		this.upperBound = upperBound;
		this.value = value;
		this.name = name;
		this.lowerBound = lowerBound;
		this.setIsPercent(false);;
		init();
	}
	
	private void init(){
		option = new GsonOption();
        option.tooltip().formatter("{a} <br/>{b} : {c}%");
        option.toolbox().show(false);
        option.series(
                new Gauge("").detail(new Detail().formatter(formatterString))
                        .data(new Data(name, value)).max(upperBound).min(lowerBound));
	}
	
	public void setIsPercent(boolean isPercent){
		if(isPercent)
			this.formatterString = "function(value){return value * 100 + '%';}";
		else
			this.formatterString = "{value}";
		init();
	}
	
	public String getURL(int panelWidth, int panelHeight) {
		int width = (int) (panelWidth * 0.88);
		int height = (int) (panelHeight * 0.88);
		String path = GraphHtmlUtil.exportToHtml(option, GraphHtmlUtil.HTML_PATH + "/" + 
				"Gauge-" + System.currentTimeMillis() + ".html", width, height);
		return "file:///" + path;
	}

	public int getUpperBound() {
		return upperBound;
	}

	public int getLowerBound() {
		return lowerBound;
	}
	
	
	
}
