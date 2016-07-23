package presentation.showPanel;

import javax.swing.JPanel;

import enums.SortType;
import presentation.main.MainFrame;
import presentation.ui.UIConfig;
import utils.Filter;

public abstract class ShowPanel extends JPanel {

	private static final long serialVersionUID = 3204969745204494298L;

	// 相对于showPanel(250,65,1030,655)

	public static int TablePanelX = 137;
	public static int TablePanelY = 20;
	public static int TablePanelW = 800;
	public static int TablePanelH = 420;
	
	public static int ChartPanelW = 897;
	public static int ChartPanelH = 600;
	
	

	public ShowPanel() {
		setSize(MainFrame.ShowW, MainFrame.ShowH);
		setBackground(UIConfig.ShowBgColor);
		setLayout(null);

	}

	public abstract void refreshDatas();

	public abstract void refreshDatas(Filter filter);

	public abstract void refreshDatas(SortType sortType);

}