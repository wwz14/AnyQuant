package presentation.panel.chartPanel;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import enums.KLineType;
import exception.StatusNotOKException;
import presentation.common.Toast;
import presentation.graph.jfreechart.KLineFactory;
import presentation.graph.jfreechart.KLinePainter;
import presentation.graph.util.StatisticalIndicators;
import presentation.main.MainController;
import utils.DateTool;
import vo.BollVO;
import vo.KDJVO;
import vo.KLineVO;
import vo.MAVO;
import vo.MacdVO;
import vo.StockStatisticVO;

public class StockKChartPanel extends ChartPanel {

	private static final long serialVersionUID = -290982808807692135L;

	JTabbedPane tabbedPane;
	// BOLL,KDJ,MACD,ATR
	JPanel BOLLPanel;
	JPanel kDJPanel;
	JPanel MACDPanel;
	JPanel ATRPanel;

	JPanel BASEPanel;

	String stockcode;
	KLineType kLineType;

	ArrayList<KLineVO> kLineVOs;
	ArrayList<MAVO> mavos;

	ArrayList<BollVO> bollVOs;
	ArrayList<KDJVO> kdjvos;
	ArrayList<MacdVO> macdVOs;
	ArrayList<StockStatisticVO> atrs;

	KLinePainter kLinePainter;

	public StockKChartPanel() {
		super();

		initKLineControlPanel();
		initDatePanel();

		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setBounds(0, ChartControlPanelH, 800, ChartTabPanelH);
		add(tabbedPane);
		BASEPanel = new JPanel();
		// tabbedPane.addTab(" BASE ", BASEPanel);
		BOLLPanel = new JPanel();
		// tabbedPane.addTab(" BOLL ", BOLLPanel);
		kDJPanel = new JPanel();
		// tabbedPane.addTab(" KDJ ", kDJPanel);
		MACDPanel = new JPanel();
		// tabbedPane.addTab(" MACD ", MACDPanel);
		ATRPanel = new JPanel();
		// tabbedPane.addTab(" ATR ", ATRPanel);

	}

	public void initChartPanel(String stockcode, Date start, Date end, KLineType kLineType) {
		//需要联网
	//	if(NetStatus.isConnected()){
			switch (kLineType) {
			case day:
				new Toast(MainController.frame, 1000, "正在刷新日K线图...", Toast.MESSEGE);
				break;
			case week:
				new Toast(MainController.frame, 1000, "正在刷新周K线图...", Toast.MESSEGE);
				break;
			case month:
				new Toast(MainController.frame, 1000, "正在刷新月K线图...", Toast.MESSEGE);
				break;
			default:
				break;
			}
			try {
				this.stockcode = stockcode;
				this.start = start;
				this.end = end;
				this.kLineType = kLineType;

				if(null==start&&null==end){
					return;
				}
				
				refreshDateTextField(start, end);
				
				if(start.before(DateTool.getDateByString("2015-01-01"))){
					new Toast(MainController.frame, 1000, "起始时间不能早于2015-01-01", Toast.WARING);
					return;
				}
				// 重置时间快速选择radioButton
				// bGQuickDateRange.clearSelection();

				// System.out.println(new Date(System.currentTimeMillis()));
				kLineVOs = stockKLineLogicService.getKLineVOs(stockcode, kLineType, start, end);
				// System.out.println(new Date(System.currentTimeMillis()));
				mavos = stockKLineLogicService.getMAVOs(stockcode, start, end);
				// System.out.println(new Date(System.currentTimeMillis()));

				try {
					kLinePainter = KLineFactory.createKLinePainter(kLineVOs, mavos, kLineType);

					// BASEPanel.removeAll();
					// BASEPanel.add(KLineFactory.addStatisticalIndicators(kLinePainter,
					// null, StatisticalIndicators.BASE));

					this.bollVOs = stockKLineLogicService.getBoll(stockcode, start, end);
					// System.out.println(new Date(System.currentTimeMillis()));
					// BOLLPanel.removeAll();
					// BOLLPanel.add(KLineFactory.addStatisticalIndicators(kLinePainter,
					// bollVOs, StatisticalIndicators.BOLL));

					this.kdjvos = stockKLineLogicService.getKDJ(stockcode, start, end);
					// kDJPanel.removeAll();
					// kDJPanel.add(KLineFactory.addStatisticalIndicators(kLinePainter,
					// kdjvos, StatisticalIndicators.KDJ));

					// System.out.println("getMacd" + start + " " + end + " " +
					// kLineType);
					this.macdVOs = macdLogicService.calculateMacd(stockcode, start, end);
					// MACDPanel.removeAll();
					// MACDPanel.add(KLineFactory.addStatisticalIndicators(kLinePainter,
					// macdVOs, StatisticalIndicators.MACD));

					this.atrs = stockKLineLogicService.getStockATR(stockcode, start, end);
					// ATRPanel.removeAll();
					// ATRPanel.add(KLineFactory.addStatisticalIndicators(kLinePainter,
					// atrs, StatisticalIndicators.ATR));

					tabbedPane.removeAll();
					tabbedPane.addTab(" BASE ", BASEPanel);
					tabbedPane.setComponentAt(0,
							KLineFactory.addStatisticalIndicators(kLinePainter, null, StatisticalIndicators.BASE));
					tabbedPane.addTab(" BOLL ", null);
					tabbedPane.addTab(" KDJ ", null);
					tabbedPane.addTab(" MACD ", null);
					tabbedPane.addTab(" ATR ", null);
					tabbedPane.addChangeListener(new ChangeListener() {

						@Override
						public void stateChanged(ChangeEvent e) {
							int index = tabbedPane.getSelectedIndex();
							if (index == -1)
								index = 0;
							String title = tabbedPane.getTitleAt(index);

							switch (title) {
							case " BASE ":
								tabbedPane.setComponentAt(index, KLineFactory.addStatisticalIndicators(kLinePainter, null,
										StatisticalIndicators.BASE));
								break;

							case " MACD ":
								System.out.println("tab reset");
								tabbedPane.setComponentAt(index, KLineFactory.addStatisticalIndicators(kLinePainter,
										macdVOs, StatisticalIndicators.MACD));
								break;

							case " KDJ ":
								tabbedPane.setComponentAt(index, KLineFactory.addStatisticalIndicators(kLinePainter, kdjvos,
										StatisticalIndicators.KDJ));
								break;

							case " ATR ":
								tabbedPane.setComponentAt(index, KLineFactory.addStatisticalIndicators(kLinePainter, atrs,
										StatisticalIndicators.ATR));
								break;

							case " BOLL ":
								tabbedPane.setComponentAt(index, KLineFactory.addStatisticalIndicators(kLinePainter,
										bollVOs, StatisticalIndicators.BOLL));
								break;
							}
						}
					});
				} catch (Exception e) {
					new Toast(MainController.frame, 1000, "日期范围太近", Toast.ERROR);
					tabbedPane.removeAll();
				}

				this.updateUI();
				this.revalidate();
				this.repaint();

			} catch (StatusNotOKException e) {
				e.printStackTrace();
			}
		//}else {
			//new Toast(MainController.frame, 1000, "网络未连接...", Toast.ERROR);
		//}
		

	}

	@Override
	public void refresh() {
		initChartPanel(stockcode, start, end, kLineType);
	}

	@Override
	public void refresh(KLineType kLineType) {
		// TODO 效率可以提升
		initDates(kLineType);
		initChartPanel(stockcode, start, end, kLineType);
	}

	@Override
	public void refresh(Date start, Date end) {
		initChartPanel(stockcode, start, end, kLineType);
	}

}
