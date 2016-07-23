package presentation.showPanel.market;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JPanel;

import enums.CtrBtType;
import enums.ShowPanelType;
import enums.SortType;
import enums.Stockfield;
import exception.StatusNotOKException;
import logic.Analysisbl.MarketLogic;
import logic.stockShowInfobl.StockListLogic;
import logicservice.Analysisblservice.MarketLogicservice;
import logicservice.showInfoblservice.StockListLogicservice;
import presentation.common.ImgButton;
import presentation.common.Toast;
import presentation.main.MainController;
import presentation.panel.TablePanel;
import presentation.panel.chartPanel.MarketBarChartPanel;
import presentation.panel.chartPanel.MarketLineChartPanel;
import presentation.panel.chartPanel.MarketTwoLineChartPanel;
import presentation.showPanel.ShowPanel;
import presentation.ui.Images;
import presentation.ui.UIConfig;
import utils.DateTool;
import utils.Filter;
import utils.Formater;
import vo.NStockVO;

@SuppressWarnings("rawtypes")
public class MarketPanel extends ShowPanel {

	private static final long serialVersionUID = -6839034486238028169L;
	TablePanel tablePanel;
	JPanel controlPanel;
	MarketTwoLineChartPanel twolinechartPanel;

	MarketLineChartPanel linechartPanel;
	MarketBarChartPanel barchartPanel;

	JPanel reportPanel;

	JPanel currentPanel;

	StockListLogicservice stockListLogicservice = new StockListLogic();
	MarketLogicservice marketLogicservice = new MarketLogic();

	ArrayList<NStockVO> nStockVOs = new ArrayList<NStockVO>();
	Vector<Vector> datas;
	Vector<String> columns;
	private ImgButton tablebutton;
	private ImgButton linebutton;
	private ImgButton barbutton;
	private ImgButton twoLinebutton;
	ImgButton reportbutton;

	Date start;
	Date end;

	/**
	 * Create the panel.
	 */
	public MarketPanel() {
		super();

		initColumns();
		initDates(); // 初始化日期Date start;Date end;

		Toast toast = new Toast(MainController.frame, 1000, "正在更新行业模块...", Toast.MESSEGE);
//		initDatas();
//		toast.disposeThread();

		tablePanel = new TablePanel(getDatas(), getColumns(), true, "银行股票列表", ShowPanelType.MarketPanel);
		tablePanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.TablePanelW,
				ShowPanel.TablePanelH);
		add(tablePanel);
		currentPanel = tablePanel;

		controlPanel = new JPanel();
		controlPanel.setBackground(UIConfig.ShowBgColor);
		controlPanel.setBounds(0, ShowPanel.TablePanelY, 137, 655);
		add(controlPanel);
		controlPanel.setLayout(null);

		tablebutton = new ImgButton(Images.control_tableBt_normal, Images.control_tableBt_on);
		tablebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMarketControlPanelUI(CtrBtType.tableBt);
				changeCurrentPanel(tablePanel);
			}
		});
		tablebutton.setLocation(0, 0);
		tablebutton.setIcon(Images.control_tableBt_on);
		controlPanel.add(tablebutton);

		linebutton = new ImgButton(Images.control_lineBt_normal, Images.control_lineBt_on);
		linebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initLineChartPanel();
				setMarketControlPanelUI(CtrBtType.lineBt);
				changeCurrentPanel(linechartPanel);
			}
		});
		linebutton.setLocation(0, 42);
		controlPanel.add(linebutton);

		barbutton = new ImgButton(Images.control_barBt_normal, Images.control_barBt_on);
		barbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initBarChartPanel();
				setMarketControlPanelUI(CtrBtType.barBt);
				changeCurrentPanel(barchartPanel);
			}
		});
		barbutton.setLocation(0, 42 * 2);
		controlPanel.add(barbutton);

		twoLinebutton = new ImgButton(Images.control_twoLineBt_normal, Images.control_twoLineBt_on);
		twoLinebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initTwoLineChartPanel();
				setMarketControlPanelUI(CtrBtType.twoLineBt);
				changeCurrentPanel(twolinechartPanel);
			}
		});
		twoLinebutton.setLocation(0, 42 * 3);
		controlPanel.add(twoLinebutton);

		reportbutton = new ImgButton(Images.marketreportBt_normal, Images.marketreportBt_on);
		reportbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				initReportPanel();
				setMarketControlPanelUI(CtrBtType.reportBt);
				changeCurrentPanel(reportPanel);
			}
		});
		reportbutton.setBounds(0, 42 * 4, 137, 42);
		controlPanel.add(reportbutton);

	}

	/**
	 * MarketPanel 默认显示银行行业近一个月的走势图
	 */
	private void initDates() {
		end = new Date();

		// 将end提前一个月得到start
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(end);
		c.add(Calendar.MONTH, -1);
		start = c.getTime();
		start = DateTool.getDateByString(DateTool.getStringByDate(start));

	}

	private void initBarChartPanel() {
		if (null == barchartPanel) {
			new Toast(MainController.frame, 1000, "正在初始化行业排行...", Toast.MESSEGE);
			barchartPanel = new MarketBarChartPanel();
			barchartPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
					ShowPanel.ChartPanelH);
			barchartPanel.initChartPanel(SortType.changeRate);
			this.revalidate();
			this.repaint();
		}
	}

	private void initLineChartPanel() {
		if (null == linechartPanel) {
			new Toast(MainController.frame, 1000, "正在初始化行业均价走势图...", Toast.MESSEGE);
			linechartPanel = new MarketLineChartPanel();
			linechartPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
					ShowPanel.ChartPanelH);
			linechartPanel.initChartPanel(start, end);
			this.revalidate();
			this.repaint();
		}
	}

	private void initTwoLineChartPanel() {
		if (null == twolinechartPanel) {
			new Toast(MainController.frame, 1000, "正在初始化行业对比...", Toast.MESSEGE);
			twolinechartPanel = new MarketTwoLineChartPanel();
			// if (NetStatus.isConnected()) {
			twolinechartPanel.initChartPanel(start, end); // 默认为日K
			// }
			twolinechartPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
					ShowPanel.ChartPanelH);
			this.revalidate();
			this.repaint();
		}
	}

	protected void initReportPanel() {
		if (null == reportPanel) {
			new Toast(MainController.frame, 1000, "正在初始化行业报告...", Toast.MESSEGE);
			reportPanel = new MarketIndustryReportPanel();
			reportPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
					ShowPanel.ChartPanelH);
			this.revalidate();
			this.repaint();
		}
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

	public void initDatas() {
		try {
			nStockVOs = marketLogicservice.getResultListAll(new Date());
			System.out.println(nStockVOs.size());
			datas = changeDatas(nStockVOs);
			setDatas(datas);
			setnStockVOs(nStockVOs);

		} catch (StatusNotOKException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	protected Vector<Vector> changeDatas(ArrayList<NStockVO> nStockVOs) {

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

	@Override
	public void refreshDatas(Filter filter) {
		nStockVOs = stockListLogicservice.getResultList(getnStockVOs(), filter);
		datas = changeDatas(nStockVOs);
		setDatas(datas);
		tablePanel.refreshDatas(getDatas());
		tablePanel.getTable().repaint();
	}

	@Override
	public void refreshDatas(SortType sortType) {
		nStockVOs = stockListLogicservice.sortStockList(getnStockVOs(), sortType);
		datas = changeDatas(nStockVOs);
		setDatas(datas);
		tablePanel.refreshDatas(getDatas());
	}

	public void refreshDatas() {
		initDatas();
		tablePanel.refreshDatas(getDatas());
		tablePanel.getTable().repaint();
	}

	public void changeCurrentPanel(JPanel panel) {
		this.remove(currentPanel);
		currentPanel = panel;
		add(currentPanel);
		this.repaint();
	}

	private void setMarketControlPanelUI(CtrBtType ctlBtType) {
		switch (ctlBtType) {
		case tableBt:
			tablebutton.setOn();
			linebutton.setNormal();
			barbutton.setNormal();
			twoLinebutton.setNormal();
			reportbutton.setNormal();
			break;
		case lineBt:
			tablebutton.setNormal();
			linebutton.setOn();
			barbutton.setNormal();
			twoLinebutton.setNormal();
			reportbutton.setNormal();
			break;
		case barBt:
			tablebutton.setNormal();
			linebutton.setNormal();
			barbutton.setOn();
			twoLinebutton.setNormal();
			reportbutton.setNormal();
			break;
		case twoLineBt:
			tablebutton.setNormal();
			linebutton.setNormal();
			barbutton.setNormal();
			twoLinebutton.setOn();
			reportbutton.setNormal();
			break;
		case reportBt:
			tablebutton.setNormal();
			linebutton.setNormal();
			barbutton.setNormal();
			twoLinebutton.setNormal();
			reportbutton.setOn();
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

}