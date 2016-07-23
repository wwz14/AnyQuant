package presentation.showPanel.stock;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import enums.Stockfield;
import exception.StatusNotOKException;
import logic.Analysisbl.ContextLogic;
import logic.Analysisbl.MarkovLogic;
import logic.Analysisbl.Polyfit;
import logic.Analysisbl.RegressionAn;
import logic.stockShowInfobl.MacdcalculateLogic;
import logic.stockShowInfobl.StockKLineLogic;
import logicservice.Analysisblservice.ContextLogicService;
import logicservice.Analysisblservice.MarkovLogicservice;
import logicservice.Analysisblservice.PolyFitLogicservice;
import logicservice.Analysisblservice.RegressionLogicservice;
import logicservice.showInfoblservice.MacdcalculateLogicservice;
import logicservice.showInfoblservice.StockKLineLogicService;
import presentation.common.MyTextPanel;
import presentation.graph.echarts.GraphPanel;
import presentation.graph.echarts.RadarGraphPainter;
import presentation.graph.jfreechart.BarChartPainter;
import presentation.graph.jfreechart.DecoratorBoll;
import presentation.graph.jfreechart.DecoratorKDJ;
import presentation.graph.jfreechart.DecoratorMACD;
import presentation.graph.jfreechart.DialGraphPatinter;
import presentation.graph.jfreechart.SpiderWebGraphPainter;
import presentation.graph.jfreechart.SplineAndScatterGraphPainter;
import presentation.graph.jfreechart.SplineBarGraphPainter;
import presentation.graph.jfreechart.SplineGraphPainter;
import presentation.graph.jfreechart.ThermometerGraphPainter;
import presentation.spider.SpiderSave;
import presentation.spider.StockOrgAnalysis;
import presentation.ui.UIConfig;
import utils.Formater;
import utils.ResultMsg;
import vo.BollVO;
import vo.KDJVO;
import vo.MResult;
import vo.MacdVO;
import vo.PolyFitVO;
import vo.RSIVO;
import vo.RadarDataVO;
import vo.RegressionVO;
import vo.ThermometerDataVO;

@SuppressWarnings("unchecked")
public class StockShowPanel extends JPanel {

	private static final long serialVersionUID = -8017938173179488835L;

	public StockShowPanel() {
		setSize(897, 600);
		setLayout(null);
	}

	public void refresh(String stockCode) {

		this.removeAll();

		JTabbedPane tabbedPaneTop = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneTop.setBounds(0, 0, 400, 300);
		add(tabbedPaneTop);

		StockOrgAnalysis stockOrgAnalysis = SpiderSave.getStockOrgAnalysis(stockCode);
		ThermometerGraphPainter thermometerGraphPainter = new ThermometerGraphPainter(new ThermometerDataVO(
				Double.parseDouble(stockOrgAnalysis.getLowPrice()), Double.parseDouble(stockOrgAnalysis.getHighPrice()),
				Double.parseDouble(stockOrgAnalysis.getClosePrice()),
				Double.parseDouble(stockOrgAnalysis.getClosePrice()), stockCode));
		JPanel panel1 = thermometerGraphPainter.getPanel();
		tabbedPaneTop.add("预测价位", panel1);

		DialGraphPatinter patinter = new DialGraphPatinter(Double.parseDouble(stockOrgAnalysis.getFrcGainProb()), 0, 1);
		patinter.setFormatter("#0.0%");
		patinter.setName("上涨概率");
		JPanel panel2 = patinter.getPanel();
		tabbedPaneTop.add("上涨概率", panel2);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 300, 897, 300);
		add(tabbedPane);

		ContextLogicService contextLogic = new ContextLogic();
		String bollText = "";
		String kdjText = "";
		String macdText = "";
		String compText = "";
		ResultMsg rsi = null;
		try {
			bollText = contextLogic.bollText(stockCode);
			kdjText = contextLogic.kdjText(stockCode);
			macdText = contextLogic.macdText(stockCode);
			rsi = contextLogic.getRSIText(stockCode);
			compText = contextLogic.getComp(stockCode); // 总结的String
		} catch (Exception e) {
			e.printStackTrace();
		}

		StockKLineLogicService stockKLineLogicService = new StockKLineLogic();
		MacdcalculateLogicservice macdLogicService = new MacdcalculateLogic();

		Date end = new Date();
		// 将end 提前30天
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(end);
		c.add(Calendar.DAY_OF_YEAR, -30);
		Date start_30 = c.getTime();
		// 将end 提前90天
		c.setTime(end);
		c.add(Calendar.DAY_OF_YEAR, -90);
		Date start_90 = c.getTime();

		ArrayList<BollVO> bollVOs = null;
		ArrayList<KDJVO> kdjvos = null;
		ArrayList<MacdVO> macdVOs = null;
		try {

			bollVOs = stockKLineLogicService.getBoll(stockCode, start_30, end);
			kdjvos = stockKLineLogicService.getKDJ(stockCode, start_30, end);
			macdVOs = macdLogicService.calculateMacd(stockCode, start_90, end);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(StockShowPanel.class.getName()).log(Level.SEVERE, "获取数据失败");
		}

		DecoratorBoll decoratorBoll = new DecoratorBoll(null, bollVOs);
		JPanel picpanel1 = decoratorBoll.getBollPanel();
		TabShowPanel bollPanel = new TabShowPanel(picpanel1, TabShowPanel.SMALL);
		bollPanel.setDocs(bollText);
		tabbedPane.add("BOLL", bollPanel);

		DecoratorKDJ decoratorKDJ = new DecoratorKDJ(null, kdjvos);
		JPanel picpanel2 = decoratorKDJ.getKDJPanel();
		TabShowPanel kdjPanel = new TabShowPanel(picpanel2, TabShowPanel.SMALL);
		kdjPanel.setDocs(kdjText);
		tabbedPane.add("KDJ", kdjPanel);

		DecoratorMACD decoratorMACD = new DecoratorMACD(null, macdVOs);
		JPanel picpanel3 = decoratorMACD.getMacdPanel();
		TabShowPanel macdPanel = new TabShowPanel(picpanel3, TabShowPanel.SMALL);
		macdPanel.setDocs(macdText);
		tabbedPane.add("MACD", macdPanel);

		String rsiText = rsi.getMessage();
		ArrayList<RSIVO> rsivos = (ArrayList<RSIVO>) rsi.getResult();
		SplineGraphPainter splineGraphPainter = new SplineGraphPainter();
		splineGraphPainter.addData(rsivos);
		JPanel picPanel4 = splineGraphPainter.getPanel();
		TabShowPanel rsiPanel = new TabShowPanel(picPanel4, TabShowPanel.SMALL);
		rsiPanel.setDocs(rsiText);
		tabbedPane.add("RSI", rsiPanel);

		JTextPane textPane = new JTextPane();
		textPane.setBackground(UIConfig.MarketBar);
		textPane.setEditable(false);
		textPane.setBounds(470, 109, 400, 90);
		add(textPane);

		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attrSet, Color.WHITE);
		StyleConstants.setBold(attrSet, true);
		StyleConstants.setFontSize(attrSet, 16);
		Document doc = textPane.getDocument();
		try {
			doc.insertString(doc.getLength(), compText, attrSet);
		} catch (BadLocationException e) {
			System.out.println("BadLocationException:   " + e);
		}

	}

	public void radarrefresh(RadarDataVO radarDataVO) {
		this.removeAll();

		// RadarGraphPainter radarGraphPainter = new
		// RadarGraphPainter(radarDataVO);
		// radarGraphPainter.setTitle("股票"+radarDataVO.getName()+"雷达图分析");
		// SwingUtilities.invokeLater(new Runnable() {
		// public void run() {
		// GraphPanel panel = new GraphPanel(radarGraphPainter);
		// panel.setSize(897, 450);
		// StockShowPanel.this.add(panel);
		// }
		// });

		SpiderWebGraphPainter spiderWebGraphPainter = new SpiderWebGraphPainter(radarDataVO);
		spiderWebGraphPainter.setTitle("股票" + radarDataVO.getName() + "雷达图分析");
		JPanel leipanel = spiderWebGraphPainter.getPanel();
		leipanel.setBounds(0, 0, 897, 450);
		StockShowPanel.this.add(leipanel);

		MyTextPanel textPane = new MyTextPanel();
		textPane.setBackground(UIConfig.MarketBar);
		textPane.setEditable(false);
		textPane.setBounds(0, 450, 897, 150);
		add(textPane);

		textPane.setDocs("市净率", Color.WHITE, true, 16, false);
		textPane.setDocs(radarDataVO.getPb() + "", Color.YELLOW, true, 16, false);

		textPane.setDocs("成交量", Color.WHITE, true, 16, true);
		textPane.setDocs(radarDataVO.getVol() + "", Color.YELLOW, true, 16, false);

		textPane.setDocs("风险指数", Color.WHITE, true, 16, true);
		textPane.setDocs(radarDataVO.getRiskIndex() + "", Color.YELLOW, true, 16, false);

		textPane.setDocs("上涨概率", Color.WHITE, true, 16, true);
		textPane.setDocs(Formater.formate(radarDataVO.getRisingProbability() * 100, "0.00") + "%", Color.YELLOW, true,
				16, false);

		textPane.setDocs("平均涨跌幅", Color.WHITE, true, 16, true);
		textPane.setDocs(Formater.formate(radarDataVO.getAverageGains() * 100, "0.00") + "%", Color.YELLOW, true, 16,
				false);
	}

	JTabbedPane tabbedPane;
	@SuppressWarnings("rawtypes")
	JComboBox comboBox;
	String stockCode;
	Stockfield stockfield;

	public void predictrefresh(String stockCode, Stockfield stockfield) {
		this.stockCode = stockCode;
		this.stockfield = stockfield;
		this.removeAll();

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 897, 600);
		add(tabbedPane);

		MarkovLogicservice markovLogicservice = new MarkovLogic();
		ArrayList<MResult> mRresults = null;
		try {
			ResultMsg resultMsg = markovLogicservice.MaxProfit(stockCode);
			mRresults = (ArrayList<MResult>) resultMsg.getResult();
		} catch (StatusNotOKException e) {
			e.printStackTrace();
		}
		BarChartPainter barGraphPainter = new BarChartPainter(mRresults);
		barGraphPainter.setGraphName("收盘价区间概率预测");
		JPanel barchartpanel = barGraphPainter.getPanel();
		tabbedPane.add("收盘价区间概率预测", barchartpanel);

		RegressionLogicservice regressionLogicservice = new RegressionAn();
		ArrayList<RegressionVO> regressionVOs = null;
		String regressmessage = "";
		try {
			ResultMsg resultMsg = regressionLogicservice.regression(stockCode);
			regressionVOs = (ArrayList<RegressionVO>) resultMsg.getResult();
			regressmessage = resultMsg.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SplineBarGraphPainter splineGraphPainter = new SplineBarGraphPainter(regressionVOs);
		JPanel splinepanel = splineGraphPainter.getChartPanel(true);
		TabShowPanel tabShowPanel2 = new TabShowPanel(splinepanel, TabShowPanel.BIG);
		tabbedPane.add("线性回归预测", tabShowPanel2);

		// 显示message
		String[] strings = regressmessage.split(" ");
		for (int i = 0; i < strings.length; i++) {
			if (i == 2 || i == 5 || i == 7 || i == 9 || i == 11 || i == 13) {
				tabShowPanel2.setDocs(strings[i], Color.YELLOW, true, 16, false);
			} else if (i == 3 || i == 0 || i == 6 || i == 8 || i == 10 || i == 12) {
				tabShowPanel2.setDocs(strings[i], Color.WHITE, true, 16, false);
			} else {
				tabShowPanel2.setDocs(strings[i], Color.WHITE, true, 16, true);
			}
		}

		setCombox();

		PolyFitLogicservice polyFitLogicservice = new Polyfit();
		PolyFitVO polyFitVO = null;
		String polymessage = "";
		try {
			ResultMsg resultMsg = polyFitLogicservice.polyFit(stockCode, stockfield);
			polyFitVO = (PolyFitVO) resultMsg.getResult();
			polymessage = resultMsg.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}

		SplineAndScatterGraphPainter splineAndScatterGraphPainter = new SplineAndScatterGraphPainter(polyFitVO);
		JPanel huiguipanel = splineAndScatterGraphPainter.getPanel();
		huiguipanel.setBounds(0, 0, 897, 370);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.add(huiguipanel);
		comboBox.setBounds(0, 370, 897, 28);
		panel.add(comboBox);

		TabShowPanel tabShowPanel3 = new TabShowPanel(panel, TabShowPanel.BIG);
		// show message
		String[] strings2 = polymessage.split(" ");
		for (int i = 0; i < strings2.length; i++) {
			if (i == 0) {
				tabShowPanel3.setDocs(strings2[i], Color.WHITE, true, 16, false);
			} else if (i == 1) {
				tabShowPanel3.setDocs(strings2[i], Color.YELLOW, true, 16, false);
			}
		}
		tabbedPane.add("多项式回归预测", tabShowPanel3);

	}

	private void setCombox() {
		Stockfield[] types = new Stockfield[] { Stockfield.open, Stockfield.close, Stockfield.high, Stockfield.low,
				Stockfield.turnover, Stockfield.adj_price, Stockfield.pe_ttm, Stockfield.pb };
		comboBox = new JComboBox<Object>(types);
		comboBox.setSelectedItem(stockfield);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				huiguirefresh(stockCode, (Stockfield) comboBox.getSelectedItem());
			}
		});
		// 皮肤包下选中后的JComboBox 为蓝色，以下方法更改为白色
		Component component = comboBox.getEditor().getEditorComponent();
		if (component instanceof JTextField) {
			JTextField field = (JTextField) component;

			field.setEditable(false);

			field.setSelectionColor(
					field.getBackground()/* java.awt.Color.WHITE */);

			comboBox.setEditable(true);
		}
	}

	private void huiguirefresh(String stockCode, Stockfield stockfield) {
		predictrefresh(stockCode, stockfield);
		tabbedPane.setSelectedIndex(2);
	}

	class TabShowPanel extends JPanel {

		private static final long serialVersionUID = 9173763373551070425L;
		JTextPane textPane;
		JPanel picPanel;

		public static final int SMALL = 0;
		public static final int BIG = 1;

		public TabShowPanel(JPanel picPanel, int type) {
			setLayout(null);

			if (type == BIG) {
				this.picPanel = picPanel;
				picPanel.setBounds(0, 0, 897, 400);
				add(picPanel);
				textPane = new JTextPane();
				textPane.setBackground(UIConfig.MarketBar);
				textPane.setEditable(false);
				textPane.setBounds(0, 400, 890, 150);
				add(textPane);
			} else if (type == SMALL) {
				this.picPanel = picPanel;
				picPanel.setBounds(0, 0, 450, 210);
				add(picPanel);
				textPane = new JTextPane();
				textPane.setBackground(UIConfig.MarketBar);
				textPane.setEditable(false);
				textPane.setBounds(470, 60, 400, 90);
				add(textPane);
			}

		}

		public void insert(String str, AttributeSet attrSet, boolean isNewLine) {
			Document doc = textPane.getDocument();
			if (isNewLine == true) {
				str = "\n" + str;
			}
			try {
				doc.insertString(doc.getLength(), str, attrSet);
			} catch (BadLocationException e) {
				System.out.println("BadLocationException:   " + e);
			}
		}

		public void setDocs(String str, Color col, boolean bold, int fontSize, boolean isNewLine) {
			SimpleAttributeSet attrSet = new SimpleAttributeSet();
			StyleConstants.setForeground(attrSet, col);
			if (bold == true) {
				StyleConstants.setBold(attrSet, true);
			}
			StyleConstants.setFontSize(attrSet, fontSize);
			insert(str, attrSet, isNewLine);
		}

		/**
		 * default setDocs method
		 * 
		 * @param str
		 * 
		 */
		public void setDocs(String str) {
			setDocs(str, Color.WHITE, true, 16, false);
		}
	}

	public void huanhangLable(JLabel label, String text) {
		StringBuilder builder = new StringBuilder("<html>");
		char[] chars = text.toCharArray();
		FontMetrics fontMetrics = label.getFontMetrics(label.getFont());
		for (int beginIndex = 0, limit = 1;; limit++) {
			// System.out.println(beginIndex + " " + limit + " " +
			// (beginIndex + limit));
			if (fontMetrics.charsWidth(chars, beginIndex, limit) < label.getWidth()) {
				if (beginIndex + limit < chars.length) {
					continue;
				}
				builder.append(chars, beginIndex, limit);
				break;
			}
			builder.append(chars, beginIndex, limit - 1).append("<br/>");
			beginIndex += limit - 1;
			// beginIndex <span style="color: rgb(255, 0, 0);">+=</span>
			// limit - 1;
			limit = 0;
		}
		builder.append("</html>");
		label.setText(builder.toString());
	}
}
