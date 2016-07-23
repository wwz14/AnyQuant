package presentation.main;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import data.utils.SqlDataBase;
import presentation.panel.LeftPanel;
import presentation.panel.TopPanel;
import presentation.panel.FilterAndSort.SortDialog;
import presentation.showPanel.BenchmarkPanel;
import presentation.showPanel.SettingPanel;
import presentation.showPanel.ShowPanel;
import presentation.showPanel.StockListPanel;
import presentation.showPanel.analysis.ReportShowPanel;
import presentation.showPanel.analysis.StockAnalysisPanel;
import presentation.showPanel.market.MarketPanel;
import presentation.showPanel.stock.StockPanel;
import presentation.ui.Images;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 8651116202230259169L;

	public static int FrameW = 1280;
	public static int FrameH = 720;
	public static int ShowX = 250;
	public static int ShowY = 70;
	public static int ShowW = 1030;
	public static int ShowH = 655;
	public static int TopX = 250;
	public static int TopY = 0;
	public static int TopW = 1030;
	public static int TopH = 70;
	public static int LeftX = 0;
	public static int LeftY = 0;
	public static int LeftW = 250;
	public static int LeftH = 720;

	protected JPanel contentPane;
	protected TopPanel toppanel;
	protected LeftPanel leftpanel;

	protected ShowPanel currentPanel;
	protected StockListPanel stockListPanel;
	protected BenchmarkPanel benchmarkPanel;
	protected MarketPanel marketPanel;
	protected StockPanel stockPanel;
	protected StockAnalysisPanel stockAnalysisPanel;
	protected ReportShowPanel reportShowPanel;
	
	SettingPanel settingPanel;
	

	protected SortDialog sortPanel;

	/**
	 * Create the frame.
	 */
	public MainFrame() {

		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int width = FrameW;
		int height = FrameH;
		setSize(width, height);
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		this.setLocation(w, h);

		contentPane = new JPanel();
		contentPane.setLayout(null);

		initBenchmarkPanel();
		
		initTopPanel();
		initLeftPanel();
		addListener();

		setContentPane(contentPane);
		setIconImage(Images.Stock);
	}

	protected void initStockListPanel() {
		if (null == stockListPanel) {
			stockListPanel = new StockListPanel();
			stockListPanel.setBounds(ShowX, ShowY, ShowW, ShowH);
			contentPane.add(stockListPanel);
			stockListPanel.revalidate();
			stockListPanel.repaint();
		}
	}

	protected void initBenchmarkPanel() {
		if (null == benchmarkPanel) {
			benchmarkPanel = new BenchmarkPanel();
			if(SqlDataBase.getConnection()!=null){
				benchmarkPanel.refreshDatas();
			}
			benchmarkPanel.setBounds(ShowX, ShowY, ShowW, ShowH);
			contentPane.add(benchmarkPanel);
			currentPanel = benchmarkPanel;
		}
	}

	protected void initMarketPanel() {
		if (null == marketPanel) {
			marketPanel = new MarketPanel();
			marketPanel.setBounds(ShowX, ShowY, ShowW, ShowH);
			contentPane.add(marketPanel);
			marketPanel.revalidate();
			marketPanel.repaint();
		}
	}

	protected void initStockAnalysisPanel() {
		if (null == stockAnalysisPanel) {
			stockAnalysisPanel = new StockAnalysisPanel();
			stockAnalysisPanel.setBounds(ShowX, ShowY, ShowW, ShowH);
			contentPane.add(stockAnalysisPanel);
			stockAnalysisPanel.revalidate();
			stockAnalysisPanel.repaint();
		}
	}
	
	protected void initSettingPanel() {
		if (null == settingPanel) {
			settingPanel = new SettingPanel();
			settingPanel.setBounds(ShowX, ShowY, ShowW, ShowH);
			contentPane.add(settingPanel);
			settingPanel.revalidate();
			settingPanel.repaint();
		}
	}

	protected void initStockPanel() {
		if (null == stockPanel) {
			stockPanel = new StockPanel();
			stockPanel.setBounds(ShowX, ShowY, ShowW, ShowH);
			contentPane.add(stockPanel);
			stockPanel.revalidate();
			stockPanel.repaint();
		}
	}

//	protected void initReportShowPanel(ReportShow reportShow) {
//		if (null == reportShowPanel) {
//			reportShowPanel = new ReportShowPanel(reportShow);
//			reportShowPanel.setBounds(ShowX, ShowY, ShowW, ShowH);
//			contentPane.add(reportShowPanel);
//			reportShowPanel.revalidate();
//			reportShowPanel.repaint();
//		}
//	}

	private void initTopPanel() {
		toppanel = new TopPanel();
		toppanel.setBounds(TopX, TopY, TopW, TopH);
		toppanel.setBorder(null);
		contentPane.add(toppanel);
		toppanel.setLayout(null);

	}

	private void initLeftPanel() {
		leftpanel = new LeftPanel();
		leftpanel.setBounds(LeftX, LeftY, LeftW, LeftH);
		contentPane.add(leftpanel);
		leftpanel.setLayout(null);

	}

	/**
	 * @Description: 为所有组件添加监听
	 * @param 设定文件
	 * @return void 返回类型
	 */
	protected void addListener() {
		addContentPaneListener();
		// addShowPaneListener();

	}

	int mx, my, jfx, jfy;

	/**
	 * @Description: contentPane加监听，使主界面可以移动
	 * @param 设定文件
	 * @return void 返回类型
	 */
	protected void addContentPaneListener() {
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(jfx + e.getXOnScreen() - mx, jfy + e.getYOnScreen() - my);
			}
		});
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mx = e.getXOnScreen();
				my = e.getYOnScreen();
				jfx = getX();
				jfy = getY();
			}
		});
	}

	public void changeShowPanel(ShowPanel panel) {
		this.remove(currentPanel);
		currentPanel = panel;
		currentPanel.setBounds(ShowX, ShowY, ShowW, ShowH);
		currentPanel.refreshDatas();
		add(currentPanel);
		this.revalidate();
		this.repaint();
	}

}
