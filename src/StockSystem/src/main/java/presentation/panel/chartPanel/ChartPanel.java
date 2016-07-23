package presentation.panel.chartPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

import com.qt.datapicker.DatePicker;

import enums.KLineType;
import logic.Analysisbl.MarketLogic;
import logic.benchmarkShowInfobl.BenchmarkLogic;
import logic.stockShowInfobl.MacdcalculateLogic;
import logic.stockShowInfobl.StockKLineLogic;
import logicservice.Analysisblservice.MarketLogicservice;
import logicservice.showInfoblservice.BenchmarkLogicservice;
import logicservice.showInfoblservice.MacdcalculateLogicservice;
import logicservice.showInfoblservice.StockKLineLogicService;
import presentation.common.DateTextField;
import presentation.common.ImgButton;
import presentation.common.MyDatePicker;
import presentation.common.Toast;
import presentation.graph.jfreechart.KLinePainter;
import presentation.main.MainController;
import presentation.ui.Images;
import utils.DateTool;
import utils.ValidateUtil;

public abstract class ChartPanel extends JPanel {

	private static final long serialVersionUID = 2908825964779650304L;

	StockKLineLogicService stockKLineLogicService = new StockKLineLogic();
	MacdcalculateLogicservice macdLogicService = new MacdcalculateLogic();
	BenchmarkLogicservice benchmarkLogicservice = new BenchmarkLogic();
	MarketLogicservice marketLogicservice = new MarketLogic();

	JPanel controlPanel;
	JPanel datePanel;

	JTabbedPane tabbedPane;
	JPanel BASEPanel;

	Date start;
	Date end;

	public static int ChartControlPanelH = 30;
	/**
	 * ChartTabPanelH = 500
	 */
	public static int ChartTabPanelH = 500;
	public static int ChartDatePanelH = 70;
	 DateTextField dateTextField_start;
	 DateTextField dateTextField_end;
	protected JButton rBtWeek;
	protected JButton rBtMonth;
	protected JButton rBt3Month;
	protected JButton rBtYear;
	protected ButtonGroup bGQuickDateRange;

	public ChartPanel() {

		setSize(897, 600);
		setLayout(null);

	}

	public void initDatePanel() {
		datePanel = new JPanel();
		datePanel.setBounds(0, 530, 800, 70);
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

	public void initKLineControlPanel() {
		controlPanel = new JPanel();
		controlPanel.setBounds(0, 0, 800, ChartControlPanelH);
		controlPanel.setLayout(null);
		add(controlPanel);

		JButton btnDayK = new JButton("日K");
		btnDayK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh(KLineType.day);
			}
		});
		btnDayK.setBounds(10, 0, 93, 23);
		controlPanel.add(btnDayK);

		JButton btnWeekK = new JButton("周K");
		btnWeekK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("click Week K");
				refresh(KLineType.week);
			}
		});
		btnWeekK.setBounds(123, 0, 93, 23);
		controlPanel.add(btnWeekK);

		JButton btnMonthK = new JButton("月K");
		btnMonthK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("click Month K");
				refresh(KLineType.month);
			}
		});
		btnMonthK.setBounds(236, 0, 93, 23);
		controlPanel.add(btnMonthK);

		ButtonGroup KbuttonGroup = new ButtonGroup();
		KbuttonGroup.add(btnDayK);
		KbuttonGroup.add(btnWeekK);
		KbuttonGroup.add(btnMonthK);

		JRadioButton rBtVolumn = new JRadioButton("成交量");
		rBtVolumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO radio
				KLinePainter.vd_flag = KLinePainter.VOL;
				refresh();
			}
		});
		rBtVolumn.setBounds(412, 0, 75, 23);
		controlPanel.add(rBtVolumn);

		JRadioButton rBtCount = new JRadioButton("成交金额");
		rBtCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KLinePainter.vd_flag = KLinePainter.DEAL;
				refresh();
			}
		});
		rBtCount.setBounds(489, 0, 82, 23);
		controlPanel.add(rBtCount);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rBtVolumn);
		buttonGroup.add(rBtCount);
		rBtVolumn.setSelected(true); // 默认选中 成交量

	}

	public void refreshDateTextField(Date start, Date end) {
		dateTextField_start.setText(DateTool.getStringByDate(start));
		dateTextField_end.setText(DateTool.getStringByDate(end));
	}

	public void sure() {
		if (ValidateUtil.isDatetime(dateTextField_start.getText())
				&& ValidateUtil.isDatetime(dateTextField_end.getText())) {
			Date startDate = DateTool.getDateByString(dateTextField_start.getText());
			Date endDate = DateTool.getDateByString(dateTextField_end.getText());
			if (ValidateUtil.isDateRangeValidated(startDate, endDate)) {
				refresh(startDate, endDate);
			} else {
				new Toast(MainController.frame, 2000, "起始日期晚于终止日期或者日期超出最新日期", Toast.WARING);
			}
		} else {
			new Toast(MainController.frame, 1000, "日期格式不正确", Toast.ERROR);
		}
	}

	
	
	/**
	 * 根据KlineType选择默认的日期范围
	 */
	protected void initDates(KLineType kLineType) {
		switch (kLineType) {
		case day:
			// BenchMark 日K线图时默认时间范围为1个月
			end = new Date();
			// 将end提前一月得到start
			Calendar c = Calendar.getInstance(Locale.CHINA);
			c.setTime(end);
			c.add(Calendar.MONTH, -1);
			start = c.getTime();
			start = DateTool.getDateByString(DateTool.getStringByDate(start));
			break;
		case week:
			// BenchMark 周K线图时默认时间范围为3个月
			end = new Date();
			// 将end提前一月得到start
			Calendar c1 = Calendar.getInstance(Locale.CHINA);
			c1.setTime(end);
			c1.add(Calendar.MONTH, -3);
			start = c1.getTime();
			start = DateTool.getDateByString(DateTool.getStringByDate(start));
			break;
		case month:
			// BenchMark 月K线图时默认时间范围为1年
			end = new Date();
			// 将end提前一月得到start
			Calendar c2 = Calendar.getInstance(Locale.CHINA);
			c2.setTime(end);
			c2.add(Calendar.YEAR, -1);
			start = c2.getTime();
			start = DateTool.getDateByString(DateTool.getStringByDate(start));
			break;
		default:
			break;
		}
	}

	public abstract void refresh();

	public abstract void refresh(KLineType kLineType);

	public abstract void refresh(Date start, Date end);
}
