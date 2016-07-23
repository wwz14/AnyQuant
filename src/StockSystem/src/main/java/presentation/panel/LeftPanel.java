package presentation.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import enums.ShowPanelType;
import presentation.common.ImgButton;
import presentation.main.MainController;
import presentation.main.MainFrame;
import presentation.ui.Images;
import utils.NetStatus;

public class LeftPanel extends JPanel {

	private static final long serialVersionUID = 5859839046445126430L;
	private Image bgImage;

	public static int PhotoW = 250;
	public static int PhotoH = 100;
	public static int ButtonW = 250;
	public static int ButtonH = 50;
	private ImgButton benchmarkbutton;
	private ImgButton stocklistbutton;
	private ImgButton marketbutton;
	private ImgButton stockanalysisbutton;
	private ImgButton settingbutton;

	public LeftPanel() {
		if (NetStatus.isConnected()) {
			bgImage = Images.LeftBg_Online;
		} else {
			bgImage = Images.LeftBg_Offline;
		}
		this.setSize(MainFrame.LeftW, MainFrame.LeftH);
		// this.setPreferredSize(new Dimension(bgImage.getWidth(null),
		// bgImage.getHeight(null)));
		setLayout(null);

		stocklistbutton = new ImgButton(Images.LeftBt1_normal, Images.LeftBt1_on);
		stocklistbutton.setBounds(0, PhotoH, ButtonW, ButtonH);
		add(stocklistbutton);

		benchmarkbutton = new ImgButton(Images.LeftBt2_normal, Images.LeftBt2_on);
		benchmarkbutton.setBounds(0, PhotoH + ButtonH, ButtonW, ButtonH);
		benchmarkbutton.setOn();  //设置benchmark默认选中
		add(benchmarkbutton);

		marketbutton = new ImgButton(Images.LeftBt3_normal, Images.LeftBt3_on);
		marketbutton.setBounds(0, PhotoH + ButtonH * 2, ButtonW, ButtonH);
		add(marketbutton);

		stockanalysisbutton = new ImgButton(Images.LeftBt4_normal, Images.LeftBt4_on);
		stockanalysisbutton.setBounds(0, PhotoH + ButtonH * 3, ButtonW, ButtonH);
		add(stockanalysisbutton);
		
		settingbutton = new ImgButton(Images.LeftBtsetting_normal, Images.LeftBtsetting_on);
		settingbutton.setBounds(0,PhotoH + ButtonH * 4, ButtonW, ButtonH);
		add(settingbutton);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(stocklistbutton);
		buttonGroup.add(benchmarkbutton);
		buttonGroup.add(marketbutton);
		buttonGroup.add(stockanalysisbutton);
		buttonGroup.add(settingbutton);

		addListener();
	}

	private void addListener() {
		stocklistbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtonsIcon(ShowPanelType.StockListPanel);
				MainController.changeToStockListPanel();
			}
		});

		benchmarkbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtonsIcon(ShowPanelType.BenchmarkPanel);
				MainController.changeToBenchmarkPanel();
			}
		});

		marketbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtonsIcon(ShowPanelType.MarketPanel);
				MainController.changeToMarketPanel();
			}
		});
		stockanalysisbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtonsIcon(ShowPanelType.StockAnalysisPanel);
				MainController.changeToStockAnalysisPanel();
			}
		});
		settingbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtonsIcon(ShowPanelType.SettingPanel);
				MainController.changeToSettingPanel();
			}
		});
	}

	public void setButtonsIcon(ShowPanelType showPanelType) {
		switch (showPanelType) {
		case StockListPanel:
			stocklistbutton.setOn();
			benchmarkbutton.setNormal();
			marketbutton.setNormal();
			stockanalysisbutton.setNormal();
			settingbutton.setNormal();
			break;
		case BenchmarkPanel:
			stocklistbutton.setNormal();
			benchmarkbutton.setOn();
			marketbutton.setNormal();
			stockanalysisbutton.setNormal();
			settingbutton.setNormal();
			break;
		case MarketPanel:
			stocklistbutton.setNormal();
			benchmarkbutton.setNormal();
			marketbutton.setOn();
			stockanalysisbutton.setNormal();
			settingbutton.setNormal();
			break;
		case StockAnalysisPanel:
			stocklistbutton.setNormal();
			benchmarkbutton.setNormal();
			marketbutton.setNormal();
			stockanalysisbutton.setOn();
			settingbutton.setNormal();
			break;
		case SettingPanel:
			stocklistbutton.setNormal();
			benchmarkbutton.setNormal();
			marketbutton.setNormal();
			stockanalysisbutton.setNormal();
			settingbutton.setOn();
			break;
		default:
			break;
		}
		
		MainController.changeTopPanelBg(showPanelType);
			
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImage, 0, 0, this);
	}

	public void changeBg(Image image) {
		this.bgImage = image;
		this.repaint();
	}
}
