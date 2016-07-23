package presentation.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import enums.ShowPanelType;
import presentation.common.ImgButton;
import presentation.graph.util.GraphHtmlUtil;
import presentation.main.MainController;
import presentation.main.MainFrame;
import presentation.ui.Images;

public class TopPanel extends JPanel {

	private static final long serialVersionUID = 3881013221229750113L;
	private Image[] bgImages={Images.TOPBAR1,Images.TOPBAR2,Images.TOPBAR3,Images.TOPBAR4,Images.TOPBARSetting};
	private Image bgImage;
	private JTextField textField;
	private JButton btnSearch;
	private JButton btnExit;

	public TopPanel() {
		bgImage =bgImages[0];
		this.setSize(MainFrame.TopW, MainFrame.TopH);
		// this.setPreferredSize(new Dimension(bgImage.getWidth(null),
		// bgImage.getHeight(null)));
		this.setLayout(null);

		textField = new JTextField();
		textField.setBounds(716, 21, 165, 32);
		add(textField);
		textField.setColumns(10);

		btnSearch = new ImgButton(Images.SearchBt, Images.SearchBt);
		btnSearch.setBounds(887, 12, 50, 50);
		add(btnSearch);

		btnExit = new ImgButton(Images.ExitBt, Images.ExitBt);
		btnExit.setBounds(963, 12, 50, 50);
		add(btnExit);

		addListener();
	}

	private void addListener() {
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainController.search(textField.getText());
			}
		});
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphHtmlUtil.deleteHtml();
				System.exit(0);
			}
		});
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					MainController.search(textField.getText());
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImage, 0, 0, this);
	}

	public void changeBg(ShowPanelType showPanelType) {
		switch (showPanelType) {
		case StockListPanel:
			setBgImage(bgImages[0]);
			break;
		case BenchmarkPanel:
			setBgImage(bgImages[1]);
			break;
		case MarketPanel:
			setBgImage(bgImages[2]);
			break;
		case StockAnalysisPanel:
			setBgImage(bgImages[3]);
			break;
		case SettingPanel:
			setBgImage(bgImages[4]);
			break;
		default:
			break;
		}
		
		this.repaint();
	}

	public Image getBgImage() {
		return bgImage;
	}

	public void setBgImage(Image bgImage) {
		this.bgImage = bgImage;
	}

}
