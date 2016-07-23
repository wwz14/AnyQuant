package presentation.main;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JRootPane;

import enums.ShowPanelType;
import enums.SortType;
import presentation.common.Toast;
import utils.Filter;
import utils.NetStatus;

public class MainController {

	public static MainFrame frame;

	public static void launch() {
		NetStatus.startNetTest();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BeautyEyeNFHelper.changeLook();
					BeautyEyeNFHelper.changeFont();
					
					frame = new MainFrame();

					// 在frame setVisible true之前调用，使窗体隐藏
					frame.setUndecorated(true);
					frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
					
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	public static void changeShowPanel(ShowPanelType showPanelType) {
		switch (showPanelType) {
		case StockListPanel:
			changeToStockListPanel();
			break;
		case BenchmarkPanel:
			changeToBenchmarkPanel();
			break;
		case MarketPanel:
			changeToMarketPanel();
			break;
		case StockAnalysisPanel:
			changeToStockAnalysisPanel();
			break;
		case StockPanel:
			frame.initStockPanel();
			frame.changeShowPanel(frame.stockPanel);
			break;
		default:
			break;
		}
	}

	public static void changeToStockPanel(String stockCode, ShowPanelType showPanelType) {
		frame.initStockPanel();
		frame.stockPanel.initTableDatas(stockCode, showPanelType);
		frame.stockPanel.setKChartRefreshTrue();
		frame.changeShowPanel(frame.stockPanel);
	}

	public static void changeToStockListPanel() {
		frame.initStockListPanel();
		frame.changeShowPanel(frame.stockListPanel);
	}

	public static void changeToBenchmarkPanel() {
		frame.initBenchmarkPanel();
		frame.changeShowPanel(frame.benchmarkPanel);
	}

	public static void changeToMarketPanel() {
		frame.initMarketPanel();
		frame.changeShowPanel(frame.marketPanel);
	}

	public static void changeToStockAnalysisPanel() {
		frame.initStockAnalysisPanel();
		frame.changeShowPanel(frame.stockAnalysisPanel);
	}
	
	public static void changeToSettingPanel() {
		frame.initSettingPanel();
		frame.changeShowPanel(frame.settingPanel);
	}

	public static void search(String searchstr) {
		changeToStockListPanel();
		frame.stockListPanel.refreshDatas(searchstr);
	}

	public static void filterDatas(Filter filter) {
		frame.currentPanel.refreshDatas(filter);
	}

	public static void sortDatas(SortType sortType) {
		new Toast(MainController.frame, 500, "排序方式：" + sortType.toString(), Toast.MESSEGE);
		frame.currentPanel.refreshDatas(sortType);
	}

	public static void refresh() {
		frame.currentPanel.refreshDatas();
	}

	public static void changeTopPanelBg(ShowPanelType showPanelType) {
		frame.toppanel.changeBg(showPanelType);
	}

	public static void changeLeftPanelBg(Image image) {
		frame.leftpanel.changeBg(image);
	}

}
