package presentation.graph.jfreechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.data.xy.XYBarDataset;
import org.jfree.data.xy.XYDataset;

import presentation.graph.util.ComputingModule;
import presentation.graph.util.GraphComponents;
import presentation.graph.util.StatisticalIndicators;
import presentation.ui.UIConfig;
import utils.DateTool;
import vo.BollVO;
import vo.KDJVO;
import vo.KLineVO;
import vo.MAVO;
import vo.MacdVO;
import vo.StockStatisticVO;
/**
 * 
* @ClassName: KLinePainter    
* @Description: 画K线图的画图器
* @author zhuding    
* @date 2016年4月13日 下午7:04:21    
*
 */
public abstract class KLinePainter implements PanelAvailable{
	
	//表示画成交量还是画成交额的标志
	public static final int DEAL = 0;

	public static final int VOL = 1;
	
	public static int vd_flag = VOL;
	// 原始数据
	protected List<KLineVO> klineDatas;

	protected List<MAVO> maDatas;

	protected List<StockStatisticVO> statisticVOs;

	protected List<KDJVO> kdjvos;

	protected List<MacdVO> macdVOs;

	protected List<BollVO> bollVOs;

	protected final OHLCSeriesCollection klineSeriesCollection;// 保留K线数据的数据集

	protected TimeSeriesCollection volSeriesCollection;// 保留成交量数据的集合

	protected TimeSeriesCollection dealSeriesCollection;// 保留成交额数据

	protected TimeSeriesCollection maKlineSeriesCollection;// 保留ma数据的集合

	protected TimeSeriesCollection maVolSeriesCollection;// 保留ma数据的集合

	protected final CandlestickRenderer candlestickRender;// 设置K线图的画图器

	protected DateAxis x1Axis;// 设置x轴，也就是时间轴

	protected NumberAxis y1Axis;// 设定y轴，就是数字轴

	protected NumberAxis y2Axis;

	protected NumberAxis y3Axis;

	protected XYPlot plot1;

	protected XYPlot plot2;

	protected XYPlot plot3;

	protected StatisticalIndicators currentSI = StatisticalIndicators.BASE;

	protected GraphData data;
	
	private int volOrDeal;

	public KLinePainter(List<KLineVO> datas, List<MAVO> maDatas) {
		super();

		this.klineDatas = datas;
		this.maDatas = maDatas;

		this.klineSeriesCollection = new OHLCSeriesCollection();
		this.dealSeriesCollection = new TimeSeriesCollection();
		this.maKlineSeriesCollection = new TimeSeriesCollection();
		this.maVolSeriesCollection = new TimeSeriesCollection();
		this.volSeriesCollection = new TimeSeriesCollection();

		this.candlestickRender = new CandlestickRenderer() {

			private static final long serialVersionUID = 1L;

			public Paint getItemOutlinePaint(int i, int j) {
				if (klineSeriesCollection.getCloseValue(i, j) > klineSeriesCollection.getOpenValue(i, j))
					return Color.RED;
				else
					return UIConfig.GREEN;
			}

		};
		this.x1Axis = new DateAxis();

		this.data = new GraphData();
		this.volOrDeal = KLinePainter.vd_flag;

		initDatas();
		paintMovingAverage();
		paintCandlestick();
		paintBarGraph();
	}

	/**
	 * 初始化K线和MA数据集
	 */
	protected void initDatas() {
		RegularTimePeriod time = null;
		
		OHLCSeries klineSeries = new OHLCSeries("");// 高开低收数据序列，依次是开，高，低，收
		TimeSeries volSeries = new TimeSeries("");// 对应时间成交量数据
		TimeSeries dealSeries = new TimeSeries("");// 对应时间成交额数据
	    TimeSeries ma5Series = new TimeSeries("");// MA5的数据
	    TimeSeries ma20Series = new TimeSeries("");// MA20的数据
	    TimeSeries ma30Series = new TimeSeries("");// MA30的数据
	    TimeSeries ma60Series = new TimeSeries("");// MA60的数据
	    TimeSeries maVol5Series = new TimeSeries("");// MAVOL5的数据
	    TimeSeries maVol10Series = new TimeSeries("");// MAVOL10的数据


		for (KLineVO kLineVO : klineDatas) {
			time = getRegularTimePeriod(kLineVO.getDate());
			klineSeries.add(time, kLineVO.getOpen().doubleValue(), kLineVO.getHigh().doubleValue(),
					kLineVO.getLow().doubleValue(), kLineVO.getClose().doubleValue());
			volSeries.add(time, kLineVO.getVolumn());
			dealSeries.add(time, kLineVO.getDealPrice());
		}
		klineSeriesCollection.addSeries(klineSeries);
		volSeriesCollection.addSeries(volSeries);
		dealSeriesCollection.addSeries(dealSeries);

		for (MAVO mavo : maDatas) {
			time = new Day(mavo.getDate());
			ma5Series.add(time, mavo.getMA5().doubleValue());
			ma20Series.add(time, mavo.getMA20().doubleValue());
			ma30Series.add(time, mavo.getMA30().doubleValue());
			ma60Series.add(time, mavo.getMA60().doubleValue());
			maVol5Series.add(time, mavo.getMAVOL5().doubleValue());
			maVol10Series.add(time, mavo.getMAVOL10().doubleValue());
		}
		maKlineSeriesCollection.addSeries(ma5Series);
		maKlineSeriesCollection.addSeries(ma20Series);
		maKlineSeriesCollection.addSeries(ma30Series);
		maKlineSeriesCollection.addSeries(ma60Series);
		maVolSeriesCollection.addSeries(maVol5Series);
		maVolSeriesCollection.addSeries(maVol10Series);
	}

	/**
	 * 获得date对应的时期（日/周/月）
	 * @param date
	 * @return
	 */
	protected abstract RegularTimePeriod getRegularTimePeriod(Date date);

	/**
	 * 对于不同的K线图，设置不同的细节
	 * @return
	 */
	protected abstract boolean setKLineDetail();

	public JFreeChart getChart(){
		return null;
	}
	
	@Override
	public NumberAxis getNumberAxis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XYPlot getPlot() {
		CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);
		combineddomainxyplot.add(plot1, 2);
		combineddomainxyplot.add(plot2, 1);
		combineddomainxyplot.setGap(0);
		return combineddomainxyplot;
	}
	
	@Override
	public DateAxis getDateAxis() {
		// TODO Auto-generated method stub
		return x1Axis;
	}
	
	/**
	 * 初始化不同数据集的方法
	 */
	
	protected TimeSeriesCollection getMACDDataset() {
		if (macdVOs == null) {
			System.err.println("没有macd数据");
			return null;
		}
		TimeSeries[] macdDatas = new TimeSeries[3];
		for (int i = 0; i < macdDatas.length; i++) {
			macdDatas[i] = new TimeSeries("");
		}

		for (MacdVO macdVO : macdVOs) {
			RegularTimePeriod time = new Day(macdVO.getDate());
			macdDatas[0].add(time, macdVO.getMacd());
			macdDatas[1].add(time, macdVO.getDea());
			macdDatas[2].add(time, macdVO.getDif());
		}

		TimeSeriesCollection macdDataSet = new TimeSeriesCollection();
		for (TimeSeries timeSeries : macdDatas) {
			macdDataSet.addSeries(timeSeries);
		}
		return macdDataSet;
	}

	/**
	 * 设置MA的画图器
	 */
	protected void paintMovingAverage() {
		XYSplineRenderer xyLineRender = GraphComponents.createXYSplineRenderer();
		//确定每条线的颜色
		xyLineRender.setSeriesPaint(0, GraphComponents.COLOR_SET.get("MA5"));
		xyLineRender.setSeriesPaint(1, GraphComponents.COLOR_SET.get("MA20"));
		xyLineRender.setSeriesPaint(2, GraphComponents.COLOR_SET.get("MA30"));
		xyLineRender.setSeriesPaint(3, GraphComponents.COLOR_SET.get("MA60"));

		double maxValue = ComputingModule.computingMaxValueForOHLCSC(klineSeriesCollection);// 设置K线数据当中的最大值
		double minValue = ComputingModule.computingMinValueForOHLCSC(klineSeriesCollection);// 设置K线数据当中的最小值
		
		y1Axis = GraphComponents.createNumberAxis(minValue, maxValue);
		plot1 = new XYPlot(maKlineSeriesCollection, x1Axis, y1Axis, xyLineRender);// 设置画图区域对象
	}

	/**
	 * 设置K线的画图器
	 */
	protected void paintCandlestick() {
		candlestickRender.setUseOutlinePaint(true); // 设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
		candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);// 设置如何对K线图的宽度进行设定
		candlestickRender.setAutoWidthGap(0);// 设置各个K线图之间的间隔
		candlestickRender.setUpPaint(Color.WHITE);// 设置股票上涨的K线图颜色
		candlestickRender.setDownPaint(UIConfig.GREEN);// 设置股票下跌的K线图颜色
		candlestickRender.setBaseToolTipGenerator(new XYToolTipGenerator() {
			@Override
			public String generateToolTip(XYDataset arg0, int arg1, int arg2) {
				OHLCSeriesCollection klineSeriesCollection = (OHLCSeriesCollection) arg0;
				Date date = new Date(klineSeriesCollection.getX(arg1, arg2).longValue());
				return "<html>" + DateTool.DATE_FORMAT.format(date) + "<br>开:"
						+ klineSeriesCollection.getOpenValue(arg1, arg2) + "<br>高:"
						+ klineSeriesCollection.getHighValue(arg1, arg2) + "<br>低:"
						+ klineSeriesCollection.getLowValue(arg1, arg2) + "<br>收:"
						+ klineSeriesCollection.getCloseValue(arg1, arg2) + "</html>";
			}
		});
		
		x1Axis.setAutoRange(false);// 设置不采用自动设置时间范围
		setKLineDetail();

		x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());// 设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期
		x1Axis.setAutoTickUnitSelection(false);// 设置不采用自动选择刻度值
		x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);// 设置标记的位置
		x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());// 设置标准的时间刻度单位
		x1Axis.setDateFormatOverride(new SimpleDateFormat("yy-MM-dd"));// 设置显示时间的格式
		
		plot1.setBackgroundPaint(Color.WHITE);
		plot1.setRenderer(1, candlestickRender);
		plot1.setDataset(1, klineSeriesCollection);

	}

	/**
	 * 设置成交量/成交额的柱状图画图器
	 */
	protected void paintBarGraph() {
		TimeSeriesCollection timeSeriesCollection = null;
		switch (vd_flag) {
		case VOL:
			timeSeriesCollection = volSeriesCollection;
			break;
		case DEAL:
			timeSeriesCollection = dealSeriesCollection;
			break;
		}
		XYBarRenderer xyBarRender = new XYBarRenderer() {
			private static final long serialVersionUID = 1L;// 为了避免出现警告消息，特设定此值

			public Paint getItemPaint(int i, int j) {// 匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
				if (klineSeriesCollection.getCloseValue(i, j) > klineSeriesCollection.getOpenValue(i, j)) {// 收盘价高于开盘价，股票上涨，选用股票上涨的颜色
					return Color.WHITE;
				} else {
					return UIConfig.GREEN;
				}
			}

			public Paint getItemOutlinePaint(int i, int j) {
				if (klineSeriesCollection.getCloseValue(i, j) > klineSeriesCollection.getOpenValue(i, j)) {// 收盘价高于开盘价，股票上涨，选用股票上涨的颜色
					return Color.RED;
				} else {
					return UIConfig.GREEN;
				}
			}
		};
		xyBarRender.setMargin(0.3);// 设置柱形图之间的间隔
		xyBarRender.setShadowVisible(false);
		xyBarRender.setBarPainter(new StandardXYBarPainter());
		xyBarRender.setDrawBarOutline(true);

		double maxValue = ComputingModule.computingMaxValueForTSC(timeSeriesCollection);
		
		y2Axis = GraphComponents.createNumberAxis(0, maxValue);
		y2Axis.setTickUnit(new NumberTickUnit((maxValue * 1.1) / 4));
		
		XYSplineRenderer xyLineRender = GraphComponents.createXYSplineRenderer();
		xyLineRender.setSeriesPaint(0, GraphComponents.COLOR_SET.get("MAVOL5"));
		xyLineRender.setSeriesPaint(1, GraphComponents.COLOR_SET.get("MAVOL10"));

		plot2 = new XYPlot(maVolSeriesCollection, null, y2Axis, xyLineRender);// 建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴
		plot2.setBackgroundPaint(Color.WHITE);
		plot2.setRenderer(1, xyBarRender);
		plot2.setDataset(1, timeSeriesCollection);
	}

	/**
	 * 获取有不同统计指标的panel
	 */
	
	public ChartPanel getPanelWithATR(List<StockStatisticVO> statisticVOs) {
		System.out.println("ATR数据数量：" + statisticVOs.size());
		this.statisticVOs = statisticVOs;
		this.currentSI = StatisticalIndicators.ATR;
		
		SplineGraphPainter splineGraphPainter = new SplineGraphPainter();
		splineGraphPainter.getSplineRenderer().setSeriesPaint(0, GraphComponents.COLOR_SET.get("ATR"));
		splineGraphPainter.addData(statisticVOs);
		y3Axis = (NumberAxis) splineGraphPainter.getPlot().getRangeAxis();
		plot3 = splineGraphPainter.getPlot();
		plot3.setBackgroundPaint(Color.WHITE);
		
		return combPlot(false);
	}

	public ChartPanel getPanelWithMACD(List<MacdVO> macdVOs) {
		System.out.println("macd数据数量：" + macdVOs.size());
		this.macdVOs = macdVOs;
		this.currentSI = StatisticalIndicators.MACD;
		final TimeSeriesCollection macdDataSet = getMACDDataset();

		double maxMacd = ComputingModule.computingMaxValueForTSC(macdDataSet);
		double minMacd = ComputingModule.computingMinValueForTSC(macdDataSet);
		double maxRange = maxMacd > -minMacd ? maxMacd : -minMacd;

		y3Axis = GraphComponents.createNumberAxis(-maxRange , maxRange);

		XYBarRenderer xyBarRender = new XYBarRenderer() {
			private static final long serialVersionUID = 1L;

			public Paint getItemPaint(int i, int j) {
				if (macdDataSet.getYValue(i, j) > 0) {
					return Color.RED;
				} else {
					return UIConfig.GREEN;
				}
			}
		};
		xyBarRender.setMargin(0.1);// 设置柱形图之间的间隔
		xyBarRender.setShadowVisible(false);

		TimeSeriesCollection seriesCollectionForMACD = new TimeSeriesCollection();
		seriesCollectionForMACD.addSeries(macdDataSet.getSeries(0));

		XYSplineRenderer xyLineRender = GraphComponents.createXYSplineRenderer();
		xyLineRender.setSeriesPaint(0, GraphComponents.COLOR_SET.get("DEA"));
		xyLineRender.setSeriesPaint(1, GraphComponents.COLOR_SET.get("DIF"));

		TimeSeriesCollection seriesCollectionForOther = new TimeSeriesCollection();
		seriesCollectionForOther.addSeries(macdDataSet.getSeries(1));
		seriesCollectionForOther.addSeries(macdDataSet.getSeries(2));

		XYBarDataset xyBarDataset = new XYBarDataset(seriesCollectionForMACD, 0.0);

		plot3 = new XYPlot(xyBarDataset, null, y3Axis, xyBarRender);
		plot3.setRenderer(1, xyLineRender);
		plot3.setDataset(1, seriesCollectionForOther);

		plot3.setBackgroundPaint(Color.WHITE);
		return combPlot(false);
	}

	public ChartPanel getPanelWithKDJ(List<KDJVO> kdjvos) {
		System.out.println("KDJ数据数量：" + kdjvos.size());
		this.kdjvos = kdjvos;
		this.currentSI = StatisticalIndicators.KDJ;
		
		SplineGraphPainter splineGraphPainter = new SplineGraphPainter();
		splineGraphPainter.getSplineRenderer().setSeriesPaint(0, GraphComponents.COLOR_SET.get("K"));
		splineGraphPainter.getSplineRenderer().setSeriesPaint(1, GraphComponents.COLOR_SET.get("D"));
		splineGraphPainter.getSplineRenderer().setSeriesPaint(2, GraphComponents.COLOR_SET.get("J"));
		splineGraphPainter.addData(kdjvos);
		
		y3Axis = (NumberAxis) splineGraphPainter.getPlot().getRangeAxis();
		plot3 = splineGraphPainter.getPlot();
		plot3.setBackgroundPaint(Color.WHITE);
		return combPlot(false);
	}

	public ChartPanel getPanelWithBoll(List<BollVO> bollVOs) {
		System.out.println("BOLL数据数量：" + bollVOs.size());
		this.bollVOs = bollVOs;
		this.currentSI = StatisticalIndicators.BOLL;
		
		SplineGraphPainter splineGraphPainter = new SplineGraphPainter();
		splineGraphPainter.getSplineRenderer().setSeriesPaint(0, GraphComponents.COLOR_SET.get("MB"));
		splineGraphPainter.getSplineRenderer().setSeriesPaint(1, GraphComponents.COLOR_SET.get("UP"));
		splineGraphPainter.getSplineRenderer().setSeriesPaint(2, GraphComponents.COLOR_SET.get("DN"));
		splineGraphPainter.addData(bollVOs);
		
		y3Axis = (NumberAxis) splineGraphPainter.getPlot().getRangeAxis();
		plot3 = splineGraphPainter.getPlot();
		plot3.setBackgroundPaint(Color.WHITE);
		return combPlot(false);
	}

	public ChartPanel getBasePanel() {
		this.currentSI = StatisticalIndicators.BASE;
		return combPlot(true);
	}
	
	/**
	 * 将已有的XYPlot组合并加入ChartPanel
	 * @param isBASE 是否是无统计指标的情况
	 * @return
	 */
	private ChartPanel combPlot(boolean isBASE){
		CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);
		combineddomainxyplot.add(plot1, 2);
		combineddomainxyplot.add(plot2, 1);
		if(!isBASE && plot3 != null){
			combineddomainxyplot.add(plot3, 1);
		}
		combineddomainxyplot.setGap(0);
		JFreeChart chart = new JFreeChart(" ", JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);
		NewChartPanel panel = new NewChartPanel(chart, false);
		panel.setMouseZoomable(false);
		panel.addChartMouseListener(new MouseListener(panel));
		return panel;
	}
	

	public boolean isVOL(){
		return volOrDeal == KLinePainter.VOL;
	}
	
	class MouseListener implements ChartMouseListener {

		NewChartPanel panel;

		Date x;

		int xPosUpBound;

		int yPosUpBound;

		int yPosLowBound;

		int xPosLowBound;

		MouseListener(NewChartPanel panel) {
			this.panel = panel;
		}

		void setRange() {
			ChartRenderingInfo chartRenderingInfo = this.panel.getChartRenderingInfo();
			Rectangle2D rectangle2D = chartRenderingInfo.getPlotInfo().getDataArea();
			
			xPosUpBound = (int) x1Axis.valueToJava2D(x1Axis.getUpperBound(), rectangle2D, plot1.getDomainAxisEdge());
			xPosLowBound = (int) x1Axis.valueToJava2D(x1Axis.getLowerBound(), rectangle2D, plot1.getDomainAxisEdge());
			yPosUpBound = (int) y1Axis.valueToJava2D(y1Axis.getLowerBound(), rectangle2D, plot1.getRangeAxisEdge());
			yPosLowBound = (int) y1Axis.valueToJava2D(y1Axis.getUpperBound(), rectangle2D, plot1.getRangeAxisEdge());
			
			Date date = new Date((long) x1Axis.getUpperBound());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
				int d = (int) (DateTool.betweenDays(x1Axis.getMinimumDate(), x1Axis.getMaximumDate()) - 1);
				xPosUpBound += 2 * (xPosUpBound - xPosLowBound) / d ;
			}
			
		}
		
		String getYValue(int yPos){
			int edgeY = yPosUpBound - yPosLowBound;
			NumberAxis yAxis = y1Axis;
			double q = 0;
			
			if(currentSI == StatisticalIndicators.BASE){
				if(yPosLowBound < yPos && yPos < yPosLowBound + edgeY * 2 / 3){
					q = 1.5 * (double) (yPos - yPosLowBound) / edgeY;
				}else if(yPosUpBound > yPos && yPos > yPosLowBound + edgeY * 2 / 3){
					q = 3.0 * (double) (yPos - yPosLowBound - edgeY * 2 / 3) / edgeY;
					yAxis = y2Axis;
				}
			}else{
				if (yPosLowBound < yPos && yPos < yPosLowBound + edgeY / 2) {
					q = 2.0 * (double) (yPos - yPosLowBound) / edgeY;
				} else if (yPosLowBound + edgeY / 2 < yPos && yPos < yPosLowBound + edgeY * 3 / 4) {
					q = 4.0 * (double) (yPos - yPosLowBound - edgeY / 2) / edgeY;
					yAxis = y2Axis;
				} else if (yPosUpBound > yPos && yPos > yPosLowBound + edgeY * 3 / 4) {
					q = 4.0 * (double) (yPos - yPosLowBound - edgeY * 3 / 4) / edgeY;
					yAxis = y3Axis;
				}
			}
			
			double yValueMax = yAxis.getUpperBound();
			double yValueMin = yAxis.getLowerBound();
			return GraphComponents.DEC_FORMAT.format(yValueMax - (yValueMax - yValueMin) * q );
		}

		@Override
		public void chartMouseClicked(ChartMouseEvent arg0) {
		}

		@Override
		public void chartMouseMoved(ChartMouseEvent paramChartMouseEvent) {
			setRange();
			int xPos = paramChartMouseEvent.getTrigger().getX();
			int yPos = paramChartMouseEvent.getTrigger().getY();
			
			
			
			Point2D point2D = this.panel.translateScreenToJava2D(new Point(xPos, yPos));
			ChartRenderingInfo chartRenderingInfo = this.panel.getChartRenderingInfo();
			Rectangle2D rectangle2D = chartRenderingInfo.getPlotInfo().getDataArea();
			
			double d1 = x1Axis.java2DToValue(point2D.getX(), rectangle2D, plot1.getDomainAxisEdge());
			x = new Date((long) d1);
			
			panel.setPara(x,getYValue(yPos) ,xPos, yPos, xPosLowBound, xPosUpBound, yPosLowBound, yPosUpBound);
			
			Date lowerBound = new Date((long) x1Axis.getLowerBound());
			Date upperBound = new Date((long) x1Axis.getUpperBound());
			boolean isInRange = lowerBound.before(x) && upperBound.after(x);
			if (isInRange)  saveDatas();;
		}

		void saveDatas() {
			if (data == null) {
				data = new GraphData();
			}
			data.setDateString(DateTool.DATE_FORMAT.format(x));
			RegularTimePeriod xTime = getRegularTimePeriod(x);
			for (KLineVO kLineVO : klineDatas) {
				if (xTime.equals(getRegularTimePeriod(kLineVO.getDate()))) {
					data.setClose(kLineVO.getClose().doubleValue());
					data.setHigh(kLineVO.getHigh().doubleValue());
					data.setLow(kLineVO.getLow().doubleValue());
					data.setOpen(kLineVO.getOpen().doubleValue());
					data.setVolumn(kLineVO.getVolumn().doubleValue());
					data.setDealPrice(kLineVO.getDealPrice().doubleValue());
					break;
				}
			}
			for (MAVO mavo : maDatas) {
				if (xTime.equals(getRegularTimePeriod(mavo.getDate()))) {
					data.setMA5(mavo.getMA5().doubleValue());
					data.setMA20(mavo.getMA20().doubleValue());
					data.setMA30(mavo.getMA30().doubleValue());
					data.setMA60(mavo.getMA60().doubleValue());
					data.setMAVOL5(mavo.getMAVOL5().doubleValue());
					data.setMAVOL10(mavo.getMAVOL10().doubleValue());
					break;
				}
			}
			if (statisticVOs != null)
				for (StockStatisticVO statisticVO : statisticVOs) {
					if (xTime.equals(getRegularTimePeriod(statisticVO.getDate()))) {
						data.setATR(statisticVO.getATR().doubleValue());
						break;
					}
				}
			if (bollVOs != null)
				for (BollVO bollVO : bollVOs) {
					if (xTime.equals(getRegularTimePeriod(bollVO.getDate()))) {
						data.setMb(bollVO.getMb());
						data.setUp(bollVO.getUp());
						data.setDn(bollVO.getDn());
						break;
					}
				}
			if (macdVOs != null)
				for (MacdVO macdVO : macdVOs) {
					if (xTime.equals(getRegularTimePeriod(macdVO.getDate()))) {
						data.setDif(macdVO.getDif());
						data.setDea(macdVO.getDea());
						data.setMacd(macdVO.getMacd());
						break;
					}
				}
			if (kdjvos != null)
				for (KDJVO kdjvo : kdjvos) {
					if (xTime.equals(getRegularTimePeriod(kdjvo.getDate()))) {
						data.setK(kdjvo.getK());
						data.setD(kdjvo.getD());
						data.setJ(kdjvo.getJ());
						break;
					}
				}
		}

	}

	@SuppressWarnings("serial")
	class NewChartPanel extends ChartPanel {

		int x;

		int y;

		int xLow;

		int xHigh;

		int yLow;

		int yHigh;
		
		Date date;
		
		String s = "";
		
		public NewChartPanel(JFreeChart chart, boolean useBuffer) {
			super(chart, useBuffer);
			this.setPopupMenu(null);
		}

		/**
		 * 接受画十字线所需参数并画十字线
		 * @param date
		 * @param x 当前x坐标
		 * @param y 当前y坐标
		 * @param xLow x最小坐标
		 * @param xHigh x最大坐标
		 * @param yLow y最小坐标
		 * @param yHigh y最大坐标
		 */
		void setPara(Date date, String s,int x, int y, int xLow, int xHigh, int yLow, int yHigh) {
			this.date = date;
			this.x = x;
			this.y = y;
			this.xLow = xLow;
			this.xHigh = xHigh;
			this.yLow = yLow;
			this.yHigh = yHigh;
			this.s = s;
			repaint();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(xHigh == 0) return;
			//设置虚线
			Graphics2D   g2d   =   (Graphics2D)g;
	        Stroke   bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 16, 4 }, 0);
	        g2d.setStroke(bs);
	        //画十字线
			if(x < xLow || x > xHigh || y < yLow || y > yHigh) return;
			g.setColor(new Color(143, 143, 143));
			g.drawLine(xLow, y, xHigh, y);// 横线
			g.drawLine(x, yLow, x, yHigh);// 竖线
	
			
			g.setColor(UIConfig.StockAnalysisBar);
			g.fillRect(Math.min(x - 40, this.getWidth() - 80),
					(int) (this.getChartRenderingInfo().getPlotInfo().getDataArea().getY()
							+ this.getChartRenderingInfo().getPlotInfo().getDataArea().getHeight()),
					80, 20);
			
			g.fillRect((int) this.getChartRenderingInfo().getPlotInfo().getDataArea().getX()-s.length()*8-4, y-10,s.length()*8+4, 20);
			
			
			
			g.setColor(Color.WHITE);
			if(date != null)
			g.drawString(DateTool.getStringByDate(date), Math.min(x - 40, this.getWidth() - 80) + 5,
					(int) (this.getChartRenderingInfo().getPlotInfo().getDataArea().getY()
							+ this.getChartRenderingInfo().getPlotInfo().getDataArea().getHeight()) + 14);
			g.drawString(s, (int) this.getChartRenderingInfo().getPlotInfo().getDataArea().getX()-s.length()*8, y+5);
			
			
			drawPart1(g);
			drawPart2(g);
			drawPart3(g);
		}
		
		private void drawPart3(Graphics g) {
			Font ft = new Font("微软雅黑", Font.BOLD, 13);
			Graphics2D g2 = (Graphics2D) g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setFont(ft);
	        FontMetrics fm = g2.getFontMetrics();
	        int x = xLow + 5, y = fm.getHeight() + yLow + (yHigh - yLow) * 3 / 4;
	        
	        String str = "";
	        switch(currentSI){
			case ATR:
				str = " ATR:" + GraphComponents.DEC_FORMAT.format(data.getATR());
				g.setColor(GraphComponents.COLOR_SET.get("ATR"));
		        g.drawString(str, x, y);
				break;
			case BOLL:
				str = " Mb:" + GraphComponents.DEC_FORMAT.format(data.getMb());
				Rectangle2D rc = fm.getStringBounds(str, g2);
				g.setColor(GraphComponents.COLOR_SET.get("MB"));
		        g.drawString(str, x, y);
				x += rc.getWidth() + 5;
				
				str = " Dn:" + GraphComponents.DEC_FORMAT.format(data.getDn());
				rc = fm.getStringBounds(str, g2);
				g.setColor(GraphComponents.COLOR_SET.get("DN"));
		        g.drawString(str, x, y);
				x += rc.getWidth() + 5;
				
				str = " Up:" + GraphComponents.DEC_FORMAT.format(data.getUp());
				rc = fm.getStringBounds(str, g2);
				g.setColor(GraphComponents.COLOR_SET.get("UP"));
		        g.drawString(str, x, y);
				break;
			case KDJ:
				str = " K:" + GraphComponents.DEC_FORMAT.format(data.getK());
				Rectangle2D rc1 = fm.getStringBounds(str, g2);
				g.setColor(GraphComponents.COLOR_SET.get("K"));
		        g.drawString(str, x, y);
				x += rc1.getWidth() + 5;
				
				str = " D:" + GraphComponents.DEC_FORMAT.format(data.getD());
				rc1 = fm.getStringBounds(str, g2);
				g.setColor(GraphComponents.COLOR_SET.get("D"));
		        g.drawString(str, x, y);
				x += rc1.getWidth() + 5;
				
				str = " J:" + GraphComponents.DEC_FORMAT.format(data.getJ());
				rc1 = fm.getStringBounds(str, g2);
				g.setColor(GraphComponents.COLOR_SET.get("J"));
		        g.drawString(str, x, y);
				break;
			case MACD:
				str = " DEA:" + GraphComponents.DEC_FORMAT.format(data.getDea());
				Rectangle2D rc11 = fm.getStringBounds(str, g2);
				g.setColor(GraphComponents.COLOR_SET.get("DEA"));
		        g.drawString(str, x, y);
				x += rc11.getWidth() + 5;
				
				str = " DIF:" + GraphComponents.DEC_FORMAT.format(data.getDif());
				rc11 = fm.getStringBounds(str, g2);
				g.setColor(GraphComponents.COLOR_SET.get("DIF"));
		        g.drawString(str, x, y);
				x += rc11.getWidth() + 5;
				
				str = " MACD:"+ GraphComponents.DEC_FORMAT.format(data.getMacd());
				rc11 = fm.getStringBounds(str, g2);
				if(data.getMacd() > 0)
					g.setColor(Color.RED);
				else
					g.setColor(UIConfig.GREEN);
		        g.drawString(str, x, y);
				break;
			default:
				break;
	        }
		}

		private void drawPart2(Graphics g) {
			Font ft = new Font("微软雅黑", Font.BOLD, 13);
			Graphics2D g2 = (Graphics2D) g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setFont(ft);
	        FontMetrics fm = g2.getFontMetrics();
	        int x = xLow + 5, y = fm.getHeight() + yLow;
	        if(currentSI == StatisticalIndicators.BASE){
	        	y += (yHigh - yLow) * 2 / 3;
	        }else{
	        	y += (yHigh - yLow) / 2;
	        }
	        g.setFont(ft);
	        
	        g.setColor(Color.GRAY);
	        String str = "";
	        if(isVOL()){
	        	str = " 成交量:" + GraphComponents.DEC_FORMAT.format(data.getVolumn());
	        }else{
	        	str = " 成交额:" + GraphComponents.DEC_FORMAT.format(data.getDealPrice());
	        }
	        Rectangle2D rc = fm.getStringBounds(str, g2);
	        g.drawString(str, x, y);
			x += rc.getWidth() + 5;
			
			if(isVOL()){
				str = " MAVOL5:" + GraphComponents.DEC_FORMAT.format(data.getMAVOL5());
				rc = fm.getStringBounds(str, g2);
				g.setColor(GraphComponents.COLOR_SET.get("MAVOL5"));
		        g.drawString(str, x, y);
				x += rc.getWidth() + 5;
				
				str = " MAVOL10:" + GraphComponents.DEC_FORMAT.format(data.getMAVOL10());
				rc = fm.getStringBounds(str, g2);
				g.setColor(GraphComponents.COLOR_SET.get("MAVOL10"));
		        g.drawString(str, x, y);
				x += rc.getWidth() + 5;
			}
		}

		private void drawPart1(Graphics g){
			Font ft = new Font("微软雅黑", Font.BOLD, 13);
			Graphics2D g2 = (Graphics2D) g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setFont(ft);
	        FontMetrics fm = g2.getFontMetrics();
	        int x = xLow + 5;
	        int y = yLow;
	        g.setFont(ft);
	        
	        g.setColor(Color.RED);
	        String str = data.getDateString();
	        Rectangle2D rc = fm.getStringBounds(str, g2);
			g.drawString(str, x, y);
			x += rc.getWidth() + 5;
			
			g.setColor(Color.GRAY);
	        str = "高:" + GraphComponents.DEC_FORMAT.format(data.getHigh()) + "开:" + GraphComponents.DEC_FORMAT.format(data.getOpen())
			+ "低:" + GraphComponents.DEC_FORMAT.format(data.getLow()) + "收:" + GraphComponents.DEC_FORMAT.format(data.getClose());
	        g.drawString(str, x, y);
	        
	        x = xLow + 5;
	        y += fm.getHeight();
	        g.setColor(GraphComponents.COLOR_SET.get("MA5"));
	        str = " MA5:" + GraphComponents.DEC_FORMAT.format(data.getMA5());
	        rc = fm.getStringBounds(str, g2);
	        g.drawString(str, x, y);
	        
	        x += rc.getWidth() + 5;
	        g.setColor(GraphComponents.COLOR_SET.get("MA20"));
	        str = " MA20:" + GraphComponents.DEC_FORMAT.format(data.getMA20());
	        rc = fm.getStringBounds(str, g2);
	        g.drawString(str, x, y);
	        
	        x += rc.getWidth() + 5;
	        g.setColor(GraphComponents.COLOR_SET.get("MA30"));
	        str = " MA30:" + GraphComponents.DEC_FORMAT.format(data.getMA30());
	        rc = fm.getStringBounds(str, g2);
	        g.drawString(str, x, y);
	        
	        x += rc.getWidth() + 5;
	        g.setColor(GraphComponents.COLOR_SET.get("MA60"));
	        str = " MA60:" + GraphComponents.DEC_FORMAT.format(data.getMA60());
	        rc = fm.getStringBounds(str, g2);
	        g.drawString(str, x, y);
		}
	}

}
