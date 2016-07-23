package presentation.panel.chartPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.qt.datapicker.DatePicker;

import enums.KLineType;
import logic.Analysisbl.CompareLogic;
import presentation.common.DateTextField;
import presentation.common.ImgButton;
import presentation.common.MyDatePicker;
import presentation.common.MyTextPanel;
import presentation.common.Toast;
import presentation.graph.jfreechart.MarketGraphPainter;
import presentation.main.MainController;
import presentation.ui.Images;
import presentation.ui.UIConfig;
import utils.DateTool;
import vo.MarketVO;

public class MarketTwoLineChartPanel extends ChartPanel {

	private static final long serialVersionUID = -9004692335676824549L;

	ArrayList<MarketVO> marketVOs;

	Date start;
	Date end;
	MarketBenchmarkStockDuiBiPanel duibipanel;
	
	public MarketTwoLineChartPanel() {
		super();

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 897, 650);
		add(tabbedPane);
		
		duibipanel =new MarketBenchmarkStockDuiBiPanel();
		tabbedPane.addTab("大盘对比", duibipanel);

		StockAnalysisChartPanel stockAnalysisChartPanel= new StockAnalysisChartPanel();
		tabbedPane.addTab("股票对比", stockAnalysisChartPanel);
		
		

	}
	
	class MarketBenchmarkStockDuiBiPanel extends ChartPanel{
		JPanel BASEPanel;
		MyTextPanel textPane;
		public MarketBenchmarkStockDuiBiPanel() {
			setSize(897,570);
			BASEPanel = new JPanel();
			BASEPanel.setBounds(0, 0, 897, 470);
			add(BASEPanel);
			
			
			textPane = new MyTextPanel();
			textPane.setBackground(UIConfig.MarketBar);
			textPane.setEditable(false);
			textPane.setBounds(0, 490, 897, 30);
			add(textPane);
			
			datePanel = new JPanel();
			datePanel.setBounds(0, 490, 800, 70);
			datePanel.setLayout(null);
			add(datePanel);

			dateTextField_start = new DateTextField("yyyy-MM-dd");
			dateTextField_start.setColumns(10);
			dateTextField_start.setBounds(10, 37, 85, 30);
			datePanel.add(dateTextField_start);

			dateTextField_end = new DateTextField("yyyy-MM-dd");
			dateTextField_end.setColumns(10);
			dateTextField_end.setBounds(161, 37, 85, 30);
			datePanel.add(dateTextField_end);

			ImgButton btnStartDate = new ImgButton(Images.calendar_normal, Images.calendar_on);
			btnStartDate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DatePicker dp = new MyDatePicker(dateTextField_start, Locale.CHINA);
					// previously selected date
					Date selectedDate = dp.parseDate(dateTextField_start.getText());
					dp.setSelectedDate(selectedDate);
					dp.start(btnStartDate);
				}
			});
			btnStartDate.setBounds(106, 39, 25, 25);
			datePanel.add(btnStartDate);

			ImgButton btnEndDate = new ImgButton(Images.calendar_normal, Images.calendar_on);
			btnEndDate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DatePicker dp = new MyDatePicker(dateTextField_end, Locale.CHINA);
					// previously selected date
					Date selectedDate = dp.parseDate(dateTextField_end.getText());
					dp.setSelectedDate(selectedDate);
					dp.start(btnEndDate);
				}
			});
			btnEndDate.setBounds(256, 39, 25, 25);
			datePanel.add(btnEndDate);

			JButton btnSure = new JButton("确定");
			btnSure.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sure();
				}

			});
			btnSure.setBounds(313, 43, 93, 23);
			datePanel.add(btnSure);

			rBtWeek = new JButton("最近一周");
			rBtWeek.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Date end = new Date();

					// 将end提前一周得到start
					Calendar c = Calendar.getInstance(Locale.CHINA);
					c.setTime(end);
					c.add(Calendar.WEEK_OF_YEAR, -1);
					Date start = c.getTime();
					start = DateTool.getDateByString(DateTool.getStringByDate(start));
					refreshDateTextField(start, end);
					sure();
				}
			});
			rBtWeek.setBounds(10, 8, 94, 23);
			datePanel.add(rBtWeek);

			rBtMonth = new JButton("最近一个月");
			rBtMonth.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Date end = new Date();

					// 将end提前一月得到start
					Calendar c = Calendar.getInstance(Locale.CHINA);
					c.setTime(end);
					c.add(Calendar.MONTH, -1);
					Date start = c.getTime();
					start = DateTool.getDateByString(DateTool.getStringByDate(start));
					refreshDateTextField(start, end);
					sure();
				}
			});
			rBtMonth.setBounds(106, 8, 99, 23);
			datePanel.add(rBtMonth);

			rBt3Month = new JButton("最近三个月");
			rBt3Month.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Date end = new Date();

					// 将end提前三个月得到start
					Calendar c = Calendar.getInstance(Locale.CHINA);
					c.setTime(end);
					c.add(Calendar.MONTH, -3);
					Date start = c.getTime();
					start = DateTool.getDateByString(DateTool.getStringByDate(start));
					refreshDateTextField(start, end);
					sure();
				}
			});
			rBt3Month.setBounds(207, 8, 99, 23);
			datePanel.add(rBt3Month);

			rBtYear = new JButton("最近一年");
			rBtYear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Date end = new Date();

					// 将end提前一年得到start
					Calendar c = Calendar.getInstance(Locale.CHINA);
					c.setTime(end);
					c.add(Calendar.YEAR, -1);
					Date start = c.getTime();
					start = DateTool.getDateByString(DateTool.getStringByDate(start));
					refreshDateTextField(start, end);
					sure();

				}
			});
			rBtYear.setBounds(308, 8, 93, 23);
			datePanel.add(rBtYear);

			bGQuickDateRange = new ButtonGroup();
			bGQuickDateRange.add(rBtWeek);
			bGQuickDateRange.add(rBtMonth);
			bGQuickDateRange.add(rBt3Month);
			bGQuickDateRange.add(rBtYear);
		}

		@Override
		public void refresh() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void refresh(KLineType kLineType) {
			initChartPanel(start, end);
			
		}

		@Override
		public void refresh(Date start, Date end) {
			initChartPanel(start, end);
			
		}
	}

	public void initChartPanel(Date start, Date end) {
		try {

			this.start = start;
			this.end = end;

			duibipanel.refreshDateTextField(start, end);

			marketVOs = marketLogicservice.getMarketVOs(start, end);
			// start 不能早于去年
			Date now = new Date();
			Calendar c = Calendar.getInstance(Locale.CHINA);
			c.setTime(now);
			c.add(Calendar.YEAR, -1);
			c.add(Calendar.DAY_OF_YEAR, -1); // 再提前一天
			Date lastYear = c.getTime();
			if (lastYear.before(start)) {
				if (marketVOs.size() > 0) {
					MarketGraphPainter painter = new MarketGraphPainter(marketVOs);
					JPanel panel = painter.getPanel();
					panel.setPreferredSize(new Dimension(897, 470));
					duibipanel.BASEPanel.removeAll();
					duibipanel.BASEPanel.add(panel);
					
					duibipanel.textPane.setText("");// clear
					String string;
					try {
						string =new CompareLogic().confiIntervalHang(start, end);
						duibipanel.textPane.setDocs(string, Color.WHITE, true, 16, false);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				} else {
					new Toast(MainController.frame, 1000, "日期范围太近", Toast.WARING);
					duibipanel.BASEPanel.removeAll();
				}

				this.revalidate();
				this.repaint();
			} else {
				new Toast(MainController.frame, 1000, "行情对比只能对比一年内的数据", Toast.WARING);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * MarketChartPanel 不需要refresh KLineType
	 */
	@Override
	public void refresh(KLineType kLineType) {

	}

	@Override
	public void refresh(Date start, Date end) {
		System.out.println("Market refresh ,date" + start + " " + end);
		initChartPanel(start, end);

	}

	@Override
	public void refresh() {
		initChartPanel(start, end);
	}

}
