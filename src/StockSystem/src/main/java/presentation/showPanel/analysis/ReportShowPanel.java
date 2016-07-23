package presentation.showPanel.analysis;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import enums.ShowPanelType;
import enums.SortType;
import presentation.common.ImgButton;
import presentation.main.MainController;
import presentation.main.MainFrame;
import presentation.showPanel.ShowPanel;
import presentation.spider.ReportShow;
import presentation.ui.Images;
import presentation.ui.UIConfig;
import utils.Filter;

public class ReportShowPanel extends ShowPanel {

	private static final long serialVersionUID = -3073582858747154218L;

	ReportShow reportShow;
	JPanel controlPanel;
	JPanel currentPanel;
	ShowPanelType showPanelType;

	public ReportShowPanel(ReportShow reportShow,ShowPanelType showPanelType) {

		this.reportShow = reportShow;
		this.showPanelType =showPanelType;
		// showPanel size
		setSize(MainFrame.ShowW, MainFrame.ShowH);
		setBackground(UIConfig.ShowBgColor);
		setLayout(null);

		controlPanel = new JPanel();
		controlPanel.setBackground(UIConfig.ShowBgColor);
		controlPanel.setBounds(0, ShowPanel.TablePanelY, 137, 655);
		add(controlPanel);
		controlPanel.setLayout(null);

		JButton returnbutton = new ImgButton(Images.control_returnBt_normal, Images.control_returnBt_on);
		returnbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainController.changeShowPanel(showPanelType);
			}
		});
		returnbutton.setBounds(0, 0, 137, 42);
		controlPanel.add(returnbutton);

		currentPanel = new JPanel();
		currentPanel.setLayout(null);
		currentPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, 857, 600);
		add(currentPanel);

		JLabel title = new JLabel(reportShow.getTitle());
		title.setFont(new Font("微软雅黑", Font.BOLD, 16));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(0, 0, 857, 33);
		currentPanel.add(title);

		JLabel detail = new JLabel(reportShow.getDetail());
		detail.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		detail.setHorizontalAlignment(SwingConstants.CENTER);
		detail.setBounds(0, 33, 857, 30);
		currentPanel.add(detail);

		JTextArea jTextArea = new JTextArea();
		jTextArea.setTabSize(4);
		jTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		jTextArea.setLineWrap(true);// 激活自动换行功能
		jTextArea.setWrapStyleWord(true);// 激活断行不断字功能
		jTextArea.append(checkContent(reportShow.getContent()));
		jTextArea.setCaretPosition(0); // 光标在开头

		JScrollPane scrollPane = new JScrollPane(jTextArea);
		scrollPane.setBounds(0, 63, 857, 537);
		currentPanel.add(scrollPane);

	}

	/**
	 * 检查内容是否为空 如果不为空则按原内容返回 如果为空则返回提示信息
	 */
	private String checkContent(String reportShowContent) {
		if (reportShowContent.isEmpty()) {
			return "(╥╯^╰╥)该文章消失了";
		}
		return reportShowContent;
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
