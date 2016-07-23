package presentation.showPanel.stock;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import enums.CtrBtType;
import enums.KLineType;
import enums.ShowPanelType;
import enums.SortType;
import enums.Stockfield;
import logic.stockShowInfobl.StockKLineLogic;
import logic.stockShowInfobl.StockListLogic;
import logicservice.showInfoblservice.StockKLineLogicService;
import logicservice.showInfoblservice.StockListLogicservice;
import presentation.common.ImgButton;
import presentation.common.Toast;
import presentation.loadingUI.Gif;
import presentation.main.MainController;
import presentation.panel.TablePanel;
import presentation.panel.chartPanel.StockKChartPanel;
import presentation.showPanel.ShowPanel;
import presentation.spider.SpiderSave;
import presentation.spider.StockOrgAnalysis;
import presentation.ui.Images;
import presentation.ui.UIConfig;
import utils.DateTool;
import utils.Filter;
import utils.Formater;
import vo.NStockVO;
import vo.RadarDataVO;

/**
 * @Description: (具体股票详细信息panel)
 * 
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public class StockPanel extends ShowPanel {

	private static final long serialVersionUID = -5464497182224347560L;
	StockListLogicservice stockListLogicservice = new StockListLogic();
	StockKLineLogicService stockKLineLogicService = new StockKLineLogic();
	TablePanel tablePanel;
	StockKChartPanel KchartPanel;
	StockReportPanel reportPanel;
	StockShowPanel stockOrgAnalysisPanel;
	StockShowPanel radarPanel;
	StockShowPanel predictPanel;

	JPanel controlPanel;
	JPanel currentPanel;

	private ImgButton tablebutton;
	private ImgButton Kbutton;
	private ImgButton returnbutton;
	ImgButton reportbutton;
	ImgButton orgAnalysisbutton;
	ImgButton predictbutton;
	ImgButton radarbutton;

	// ImgButton[] buttons = new ImgButton[] { tablebutton, Kbutton,
	// returnbutton, reportbutton, orgAnalysisbutton,
	// predictbutton, radarbutton };

	ArrayList<NStockVO> nStockVOs;
	Vector<Vector> datas;
	Vector<String> columns;
	String stockCode;
	Date start;
	Date end;

	ShowPanelType showPanelType;

	/**
	 * Create the panel.
	 */
	public StockPanel() {
		super();

		datas = new Vector<Vector>();
		initColumns();
		tablePanel = new TablePanel(getDatas(), getColumns(), false, "股票信息", ShowPanelType.StockPanel);
		tablePanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.TablePanelW,
				ShowPanel.TablePanelH);
		add(tablePanel);
		currentPanel = tablePanel;

		controlPanel = new JPanel();
		controlPanel.setBackground(UIConfig.ShowBgColor);
		controlPanel.setBounds(0, ShowPanel.TablePanelY, 137, 655);
		add(controlPanel);
		controlPanel.setLayout(null);

		returnbutton = new ImgButton(Images.control_returnBt_normal, Images.control_returnBt_on);
		returnbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 先跳到tablePanel
				setStockControlPanelUI(CtrBtType.tableBt);
				changeCurrentPanel(tablePanel);
				MainController.changeShowPanel(showPanelType);

			}
		});
		returnbutton.setBounds(0, 0, 137, 42);
		controlPanel.add(returnbutton);

		tablebutton = new ImgButton(Images.control_tableBt_normal, Images.control_tableBt_on);
		tablebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setStockControlPanelUI(CtrBtType.tableBt);
				changeCurrentPanel(tablePanel);
			}
		});
		tablebutton.setLocation(0, 42);
		tablebutton.setIcon(Images.control_tableBt_on);
		controlPanel.add(tablebutton);

		Kbutton = new ImgButton(Images.control_KBt_normal, Images.control_KBt_on);
		Kbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initKChartPanel();
				setStockControlPanelUI(CtrBtType.KBt);
				changeCurrentPanel(KchartPanel);
			}
		});
		Kbutton.setLocation(0, 42 * 2);
		controlPanel.add(Kbutton);

		radarbutton = new ImgButton(Images.radarBt_normal, Images.radarBt_on);
		radarbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initRadarPanel();
				setStockControlPanelUI(CtrBtType.radarBt);
				changeCurrentPanel(radarPanel);
			}
		});
		radarbutton.setLocation(0, 42 * 3);
		controlPanel.add(radarbutton);

		reportbutton = new ImgButton(Images.stockreportBt_normal, Images.stockreportBt_on);
		reportbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initReportPanel();
				setStockControlPanelUI(CtrBtType.reportBt);
				changeCurrentPanel(reportPanel);
			}
		});
		reportbutton.setLocation(0, 42 * 4);
		controlPanel.add(reportbutton);

		orgAnalysisbutton = new ImgButton(Images.stockanalysisBt_normal, Images.stockanalysisBt_on);
		orgAnalysisbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initOrgAnalysisPanel();
				setStockControlPanelUI(CtrBtType.analysisBt);
				changeCurrentPanel(stockOrgAnalysisPanel);
			}
		});
		orgAnalysisbutton.setLocation(0, 42 * 5);
		controlPanel.add(orgAnalysisbutton);

		predictbutton = new ImgButton(Images.stockpredictBt_normal, Images.stockpredictBt_on);
		predictbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initPredictPanel();
				setStockControlPanelUI(CtrBtType.stockPredictionBt);
				changeCurrentPanel(predictPanel);
			}
		});
		predictbutton.setLocation(0, 42 * 6);
		controlPanel.add(predictbutton);

	}

	public void initColumns() {
		columns = new Vector<String>();
		columns.add(Stockfield.name.toString());
		columns.add(Stockfield.date.toString());
		columns.add(Stockfield.open.toString());
		columns.add(Stockfield.close.toString());
		columns.add(Stockfield.changeString.toString());
		columns.add(Stockfield.high.toString());
		columns.add(Stockfield.low.toString());
		columns.add(Stockfield.adj_price.toString());
		columns.add(Stockfield.volume.toString());
		columns.add(Stockfield.turnover.toString());
		columns.add(Stockfield.pe_ttm.toString());
		columns.add(Stockfield.pb.toString());
		setColumns(columns);
	}

	@SuppressWarnings("unchecked")
	private Vector<Vector> changeDatas(ArrayList<NStockVO> nStockVOs) {

		Vector<Vector> view = new Vector<Vector>();
		for (NStockVO nStockVO : nStockVOs) {
			Vector v = new Vector();

			v.add(nStockVO.getName());
			v.add(DateTool.getStringByDate(nStockVO.getDate()));
			v.add(Formater.formate(nStockVO.getOpen()));
			v.add(Formater.formate(nStockVO.getClose()));
			v.add(nStockVO.getRate());
			v.add(Formater.formate(nStockVO.getHigh()));
			v.add(Formater.formate(nStockVO.getLow()));
			v.add(Formater.formate(nStockVO.getAdj_price()));
			v.add(Formater.formate(nStockVO.getVolume(), "0"));
			v.add(Formater.formatePercent(nStockVO.getTurnover()));
			v.add(Formater.formate(nStockVO.getPe_ttm()));
			v.add(Formater.formate(nStockVO.getPb()));

			view.add(v);
		}
		return view;
	}

	public void changeCurrentPanel(JPanel panel) {
		this.remove(currentPanel);
		currentPanel = panel;
		add(currentPanel);
		this.repaint();
	}

	@Override
	public void refreshDatas() {
		System.out.println("stock panel refresh datas ");
		initTableDatas(stockCode, showPanelType);
	}

	@Override
	public void refreshDatas(Filter filter) {
		nStockVOs = stockListLogicservice.getResultDetail(getnStockVOs(), filter);
		datas = changeDatas(nStockVOs);
		setDatas(datas);
		tablePanel.refreshDatas(getDatas());

	}

	@Override
	public void refreshDatas(SortType sortType) {
		nStockVOs = stockListLogicservice.sortStockList(getnStockVOs(), sortType);
		datas = changeDatas(nStockVOs);
		setDatas(datas);
		tablePanel.refreshDatas(getDatas());
	}

	public void initTableDatas(String stockCode, ShowPanelType showPanelType) {
		this.stockCode = stockCode;
		this.showPanelType = showPanelType;

		nStockVOs = stockListLogicservice.click(stockCode);
		datas = changeDatas(nStockVOs);
		setDatas(datas);
		tablePanel.refreshDatas(getDatas());
		tablePanel.getLabel_title().setText("股票代号：" + stockCode);
		if (nStockVOs.size() > 0) {
			start = nStockVOs.get(nStockVOs.size() - 1).getDate();
			end = nStockVOs.get(0).getDate();
			tablePanel.setDates(start, end);
		}

	}

	boolean isKChartRefresh = true;

	public void setKChartRefreshTrue() {
		isKChartRefresh = true;
	}

	public void initKChartPanel() {

		if (null == KchartPanel) {
			new Toast(MainController.frame, 1000, "正在初始化K线图", Toast.MESSEGE);
			KchartPanel = new StockKChartPanel();
			KchartPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
					ShowPanel.ChartPanelH);

			KchartPanel.initChartPanel(stockCode, start, end, KLineType.day); // 默认为日K
			this.revalidate();
			this.repaint();
			isKChartRefresh = false;
			return;

		}
		if (isKChartRefresh) {

			KchartPanel.initChartPanel(stockCode, start, end, KLineType.day); // 默认为日K
			this.revalidate();
			this.repaint();
			isKChartRefresh = false;

		}
	}

	protected void initReportPanel() {
		if (null == reportPanel) {
			new Toast(MainController.frame, 1000, "正在初始化研究报告界面", Toast.MESSEGE);
			reportPanel = new StockReportPanel(stockCode);
			reportPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
					ShowPanel.ChartPanelH);
		}
		reportPanel.refresh(stockCode);
		this.revalidate();
		this.repaint();
	}

	protected void initOrgAnalysisPanel() {
		if (null == stockOrgAnalysisPanel) {
			new Toast(MainController.frame, 1000, "正在初始化股票分析界面", Toast.MESSEGE);
			stockOrgAnalysisPanel = new StockShowPanel();
			stockOrgAnalysisPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
					ShowPanel.ChartPanelH);
		}
		stockOrgAnalysisPanel.refresh(stockCode);
		this.revalidate();
		this.repaint();
	}

	protected void initPredictPanel() {
		if (null == predictPanel) {
			new Toast(MainController.frame, 1000, "正在初始化股票预测界面", Toast.MESSEGE);
			predictPanel = new StockShowPanel();
			predictPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
					ShowPanel.ChartPanelH);

		}
		predictPanel.predictrefresh(stockCode, Stockfield.close);
		this.revalidate();
		this.repaint();
	}

	protected void initRadarPanel() {
		if (null == radarPanel) {
			new Toast(MainController.frame, 1000, "正在初始化雷达图", Toast.MESSEGE);
			radarPanel = new StockShowPanel();
			radarPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
					ShowPanel.ChartPanelH);
		}
		// 获得radarVO
		StockOrgAnalysis stockOrgAnalysis = SpiderSave.getStockOrgAnalysis(stockCode);
		double gainProb = Formater.formate(Double.parseDouble(stockOrgAnalysis.getFrcGainProb()), "#0.000");
		double riseMeasure = Double.parseDouble(stockOrgAnalysis.getRiskMeasure());
		double pb = nStockVOs.get(0).getPb().doubleValue();
		int vol = nStockVOs.get(0).getVolume().intValue();
		double end = nStockVOs.get(0).getClose().doubleValue();
		double start = nStockVOs.get(nStockVOs.size() - 1).getClose().doubleValue();

		double avgChangeRate = Formater.formate(Math.abs((end-start)/start), "0.00");

		RadarDataVO radarDataVO = new RadarDataVO(pb, vol, riseMeasure, gainProb, avgChangeRate, stockCode);

		radarPanel.radarrefresh(radarDataVO);
		radarPanel.revalidate();
		radarPanel.repaint();
		this.revalidate();
		this.repaint();
	}

	private void setStockControlPanelUI(CtrBtType ctlBtType) {
		switch (ctlBtType) {
		case returnBt:
			returnbutton.setOn();
			tablebutton.setNormal();
			Kbutton.setNormal();
			reportbutton.setNormal();
			orgAnalysisbutton.setNormal();
			radarbutton.setNormal();
			predictbutton.setNormal();
			break;
		case tableBt:
			returnbutton.setNormal();
			tablebutton.setOn();
			Kbutton.setNormal();
			reportbutton.setNormal();
			orgAnalysisbutton.setNormal();
			radarbutton.setNormal();
			predictbutton.setNormal();
			break;
		case KBt:
			returnbutton.setNormal();
			tablebutton.setNormal();
			Kbutton.setOn();
			reportbutton.setNormal();
			orgAnalysisbutton.setNormal();
			radarbutton.setNormal();
			predictbutton.setNormal();
			break;
		case reportBt:
			returnbutton.setNormal();
			tablebutton.setNormal();
			Kbutton.setNormal();
			reportbutton.setOn();
			orgAnalysisbutton.setNormal();
			radarbutton.setNormal();
			predictbutton.setNormal();
			break;
		case analysisBt:
			returnbutton.setNormal();
			tablebutton.setNormal();
			Kbutton.setNormal();
			reportbutton.setNormal();
			orgAnalysisbutton.setOn();
			radarbutton.setNormal();
			predictbutton.setNormal();
			break;
		case radarBt:
			returnbutton.setNormal();
			tablebutton.setNormal();
			Kbutton.setNormal();
			reportbutton.setNormal();
			orgAnalysisbutton.setNormal();
			radarbutton.setOn();
			predictbutton.setNormal();
			break;
		case stockPredictionBt:
			returnbutton.setNormal();
			tablebutton.setNormal();
			Kbutton.setNormal();
			reportbutton.setNormal();
			orgAnalysisbutton.setNormal();
			radarbutton.setNormal();
			predictbutton.setOn();
			break;
		default:
			break;
		}

	}

	public ArrayList<NStockVO> getnStockVOs() {
		return nStockVOs;
	}

	public void setnStockVOs(ArrayList<NStockVO> nStockVOs) {
		this.nStockVOs = nStockVOs;
	}

	public Vector<Vector> getDatas() {
		return datas;
	}

	public void setDatas(Vector<Vector> datas) {
		this.datas = datas;
	}

	public Vector<String> getColumns() {
		return columns;
	}

	public void setColumns(Vector<String> columns) {
		this.columns = columns;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

}
