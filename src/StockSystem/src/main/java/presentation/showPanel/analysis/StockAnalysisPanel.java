package presentation.showPanel.analysis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import enums.CtrBtType;
import enums.SortType;
import presentation.common.ImgButton;
import presentation.common.Toast;
import presentation.main.MainController;
import presentation.showPanel.ShowPanel;
import presentation.ui.Images;
import presentation.ui.UIConfig;
import utils.Filter;

public class StockAnalysisPanel extends ShowPanel {

	private static final long serialVersionUID = -1855812189527186639L;
	JPanel controlPanel;
	JPanel currentPanel;
//	StockAnalysisChartPanel stockAnalysisChartPanel;
	JPanel tongjiPanel; // 短期涨跌统计和长期涨跌统计,一周强势股和一月强势股  统计panel
	JPanel stockPredictionPanel;// 业绩预测
	JPanel financeAnalysisPanel; // 财务分析

//	private ImgButton twoLinebutton;
	private ImgButton tongjibutton;
	private ImgButton stockPredictionbutton;
	private ImgButton financeAnalysisbutton;

	public StockAnalysisPanel() {
		super();

		controlPanel = new JPanel();
		controlPanel.setBackground(UIConfig.ShowBgColor);
		controlPanel.setBounds(0, ShowPanel.TablePanelY, 137, 655);
		add(controlPanel);
		controlPanel.setLayout(null);

//		stockAnalysisChartPanel = new StockAnalysisChartPanel();
//		stockAnalysisChartPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
//				ShowPanel.ChartPanelH);
//		add(stockAnalysisChartPanel);
//		currentPanel = stockAnalysisChartPanel;
//
//		twoLinebutton = new ImgButton(Images.control_twoLineBt_normal, Images.control_twoLineBt_on);
//		twoLinebutton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				setBtUI(CtrBtType.twoLineBt);
//				changeCurrentPanel(stockAnalysisChartPanel);
//			}
//		});
//		twoLinebutton.setOn();
//		twoLinebutton.setLocation(0, 0);
//		controlPanel.add(twoLinebutton);
		
		tongjiPanel = new TongjiPanel();
		tongjiPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
				ShowPanel.ChartPanelH);
		add(tongjiPanel);
		currentPanel = tongjiPanel;
		
		tongjibutton = new ImgButton(Images.performanceBt_normal, Images.performanceBt_on);
		tongjibutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				inittongjiPanel();
				setBtUI(CtrBtType.barBt); 
				changeCurrentPanel(tongjiPanel);
			}
		});
		tongjibutton.setOn();
		tongjibutton.setSize(137, 42);
		tongjibutton.setLocation(0, 0);
		controlPanel.add(tongjibutton);

		

		stockPredictionbutton = new ImgButton(Images.predictBt_normal, Images.predictBt_on);
		stockPredictionbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 初始化业绩预测界面
				// 设置buttonUI
				// 切换界面
				initStockPredictionPanel();
				setBtUI(CtrBtType.stockPredictionBt); 
				changeCurrentPanel(stockPredictionPanel);
			}
		});
		// imgbutton 不要下面这句
		stockPredictionbutton.setSize(137, 42);
		stockPredictionbutton.setLocation(0,  42 * 1);
		controlPanel.add(stockPredictionbutton);
		
		
		financeAnalysisbutton =new ImgButton(Images.financeanalysisBt_normal, Images.financeanalysisBt_on);
		financeAnalysisbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 初始化业绩预测界面
				// 设置buttonUI
				// 切换界面
				initFinanceAnalysisPanel();
				setBtUI(CtrBtType.analysisBt); // 未完成
				changeCurrentPanel(financeAnalysisPanel);
			}
		});
		// imgbutton 不要下面这句
		financeAnalysisbutton.setSize(137, 42);
		financeAnalysisbutton.setLocation(0,  42 * 2);
		controlPanel.add(financeAnalysisbutton);

	}

	/**
	 * 初始化统计界面
	 */
	protected void inittongjiPanel() {
		if (null == tongjiPanel) {
			new Toast(MainController.frame, 1000, "正在初始化统计界面...", Toast.MESSEGE);
			tongjiPanel = new TongjiPanel();
			tongjiPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
					ShowPanel.ChartPanelH);

			this.revalidate();
			this.repaint();
		}
	}

	/**
	 * 初始化业绩预测界面
	 */
	protected void initStockPredictionPanel() {
		if (null == stockPredictionPanel) {
			new Toast(MainController.frame, 1000, "正在初始化业绩预测界面...", Toast.MESSEGE);
			stockPredictionPanel = new StockPredictionPanel();
			stockPredictionPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
					ShowPanel.ChartPanelH);

			this.revalidate();
			this.repaint();
		}

	}
	
	/**
	 * 初始化财务分析界面
	 */
	protected void initFinanceAnalysisPanel() {
		if (null == financeAnalysisPanel) {
			new Toast(MainController.frame, 1000, "正在初始化财务分析界面...", Toast.MESSEGE);
			financeAnalysisPanel = new FinanceAnalysisPanel();
			financeAnalysisPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
					ShowPanel.ChartPanelH);

			this.revalidate();
			this.repaint();
		}

	}

	public void changeCurrentPanel(JPanel panel) {
		this.remove(currentPanel);
		currentPanel = panel;
		add(currentPanel);
		this.repaint();
	}
	

	private void setBtUI(CtrBtType ctlBtType) {
		switch (ctlBtType) {
//		case twoLineBt:
////			twoLinebutton.setOn();
//			tongjibutton.setNormal();
//			stockPredictionbutton.setNormal();
//			financeAnalysisbutton.setNormal();
//			break;
		case barBt:
//			twoLinebutton.setNormal();
			tongjibutton.setOn();
			stockPredictionbutton.setNormal();
			financeAnalysisbutton.setNormal();
			break;
		case stockPredictionBt:
//			twoLinebutton.setNormal();
			tongjibutton.setNormal();
			stockPredictionbutton.setOn();
			financeAnalysisbutton.setNormal();
			break;
		case analysisBt:
//			twoLinebutton.setNormal();
			tongjibutton.setNormal();
			stockPredictionbutton.setNormal();
			financeAnalysisbutton.setOn();
			break;

		default:
			break;
		}
	}

	@Override
	public void refreshDatas() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshDatas(Filter filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshDatas(SortType sortType) {
		// TODO Auto-generated method stub

	}

}
