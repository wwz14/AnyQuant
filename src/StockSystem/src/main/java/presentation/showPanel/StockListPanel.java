package presentation.showPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

import enums.ShowPanelType;
import enums.SortType;
import enums.Stockfield;
import exception.StatusNotOKException;
import logic.stockShowInfobl.AddorRemoveStockLogic;
import logic.stockShowInfobl.StockListLogic;
import logicservice.showInfoblservice.AddorRemoveLogicservice;
import logicservice.showInfoblservice.StockListLogicservice;
import presentation.common.ImgButton;
import presentation.common.Toast;
import presentation.main.MainController;
import presentation.panel.TablePanel;
import presentation.panel.FilterAndSort.SortDialog;
import presentation.ui.Images;
import presentation.ui.UIConfig;
import utils.DateTool;
import utils.Filter;
import utils.Formater;
import vo.NStockVO;
import javax.swing.ImageIcon;

/**
 * @Description: (所有股票列表panel)
 * 
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public class StockListPanel extends ShowPanel {

	private static final long serialVersionUID = -6839034486238028169L;
	TablePanel tablePanel;
	JPanel controlPanel;
	JPanel currentPanel;

	StockListLogicservice stockListLogicservice = new StockListLogic();
	AddorRemoveLogicservice addorRemoveLogicservice =new AddorRemoveStockLogic();

	ArrayList<NStockVO> nStockVOs = new ArrayList<NStockVO>();
	Vector<Vector> datas;
	Vector<String> columns;
	private JButton tablebutton;

	/**
	 * Create the panel.
	 */
	public StockListPanel() {
		super();

		initColumns();

		Toast toast = new Toast(MainController.frame, -1, "正在更新股票列表...", Toast.MESSEGE);
//		initDatas();
		toast.disposeThread();

		tablePanel = new TablePanel(getDatas(), getColumns(), true, "股票列表", ShowPanelType.StockListPanel);
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
				tablebutton.setIcon(Images.control_tableBt_on);
				changeCurrentPanel(tablePanel);
			}
		});
		tablebutton.setLocation(0, 0);
		tablebutton.setIcon(Images.control_tableBt_on);
		controlPanel.add(tablebutton);

		ImgButton addButton = new ImgButton(Images.addBt_normal, Images.addBt_on);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddStockDialog(MainController.frame);
			}

		});
		addButton.setBounds(961, 323, 43, 42);
		add(addButton);
		
		ImgButton removeButton = new ImgButton(Images.removeBt_normal, Images.removeBt_on);
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stockCode =tablePanel.getSelectedStock();
				System.out.println("remove "+stockCode);
				try {
					addorRemoveLogicservice.removeStock(stockCode);
				} catch (SQLException e1) {
					 new Toast(MainController.frame, 2000, "删除股票失败...", Toast.ERROR);
					e1.printStackTrace();
				}
			}
		});
		removeButton.setBounds(961, 387, 43, 42);
		add(removeButton);

	}

	public void initColumns() {
		columns = new Vector<String>();
		columns.add(Stockfield.name.toString());
		columns.add(Stockfield.date.toString());
		columns.add(Stockfield.open.toString());
		columns.add(Stockfield.close.toString());
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
		System.out.println("init StockList Data");
		nStockVOs = stockListLogicservice.getResultListAll("2016", "sz+sh");
		datas = changeDatas(nStockVOs);
		setDatas(datas);
		setnStockVOs(nStockVOs);
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
	}

	@Override
	public void refreshDatas(SortType sortType) {
		nStockVOs = stockListLogicservice.sortStockList(getnStockVOs(), sortType);
		datas = changeDatas(nStockVOs);
		setDatas(datas);
		tablePanel.refreshDatas(getDatas());
	}

	public void refreshDatas(String searchstr) {
		// if (NetStatus.isConnected()) {
		try {
			// 搜索后筛选和排序要在搜索结果中进行，所以需要设置nStockVO
			nStockVOs = stockListLogicservice.search(searchstr);
			if (nStockVOs.size() == 0) {
				// JOptionPane.showMessageDialog(MainController.frame,
				// "╮(╯_╰╭没有数据...");
				new Toast(MainController.frame, 1000, "╮(╯_╰╭没有数据...", Toast.WARING);
			}
			datas = changeDatas(nStockVOs);
			setDatas(datas);
			tablePanel.refreshDatas(getDatas());

		} catch (StatusNotOKException e) {
			e.printStackTrace();
		}
		// }else {
		// new Toast(MainController.frame, 1000, "网络未连接...", Toast.ERROR);
		// }

	}

	public void refreshDatas() {
		initDatas();
		tablePanel.refreshDatas(getDatas());
	}

	public void changeCurrentPanel(JPanel panel) {
		this.remove(currentPanel);
		currentPanel = panel;
		add(currentPanel);
		this.repaint();
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
