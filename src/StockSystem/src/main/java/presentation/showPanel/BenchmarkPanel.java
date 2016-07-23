package presentation.showPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JPanel;

import enums.CtrBtType;
import enums.KLineType;
import enums.ShowPanelType;
import enums.SortType;
import enums.Stockfield;
import logic.benchmarkShowInfobl.BenchmarkLogic;
import logicservice.showInfoblservice.BenchmarkLogicservice;
import presentation.common.ImgButton;
import presentation.common.Toast;
import presentation.main.MainController;
import presentation.panel.TablePanel;
import presentation.panel.chartPanel.BenchmarkKChartPanel;
import presentation.ui.Images;
import presentation.ui.UIConfig;
import utils.DateTool;
import utils.Filter;
import utils.Formater;
import vo.NBenchMarkVO;

/**
 * @Description: (具体大盘指数详细信息panel)
 * 
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public class BenchmarkPanel extends ShowPanel {

	private static final long serialVersionUID = 6371930429045653802L;

	BenchmarkLogicservice benchmarkLogicservice = new BenchmarkLogic();
	TablePanel tablePanel;
	BenchmarkKChartPanel KchartPanel;

	JPanel controlPanel;
	JPanel currentPanel;

	private ImgButton tablebutton;
	private ImgButton Kbutton;
	ArrayList<NBenchMarkVO> nBenchMarkVOs;
	Vector<Vector> datas;
	Vector<String> columns;
	Date start;
	Date end;
	Date Allstart;
	Date Allend;

	/**
	 * Create the panel.
	 */
	public BenchmarkPanel() {
		super();

		initColumns();
//		initDatas();

		tablePanel = new TablePanel(getDatas(), getColumns(), false, "大盘指数", ShowPanelType.BenchmarkPanel);
		tablePanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.TablePanelW,
				ShowPanel.TablePanelH);
		tablePanel.setDates(Allstart, Allend);
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
				setBenchmarkControlPanelUI(CtrBtType.tableBt);
				changeCurrentPanel(tablePanel);
			}
		});
		tablebutton.setLocation(0, 0);
		tablebutton.setIcon(Images.control_tableBt_on);
		controlPanel.add(tablebutton);

		Kbutton = new ImgButton(Images.control_KBt_normal, Images.control_KBt_on);
		Kbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initkChartPanl();
				setBenchmarkControlPanelUI(CtrBtType.KBt);
				changeCurrentPanel(KchartPanel);
			}
		});
		Kbutton.setLocation(0, 42);
		controlPanel.add(Kbutton);

	}

	private void initkChartPanl() {
		if (null == KchartPanel) {
			KchartPanel = new BenchmarkKChartPanel();
			KchartPanel.setBounds(ShowPanel.TablePanelX, ShowPanel.TablePanelY, ShowPanel.ChartPanelW,
					ShowPanel.ChartPanelH);
		//	if (NetStatus.isConnected()) {
				new Toast(MainController.frame, 1000, "正在初始化K线图", Toast.MESSEGE);
				KchartPanel.initChartPanel(start, end, KLineType.day); // 默认为日K
				this.revalidate();
				this.repaint();
			//}
				
		}

	}

	public void changeCurrentPanel(JPanel panel) {
		this.remove(currentPanel);
		currentPanel = panel;
		System.out.println("add currentPanel");
		add(currentPanel);
		this.repaint();
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
		// benchmark没有下面三个属性
		// columns.add(Stockfield.turnover.toString());
		// columns.add(Stockfield.pe_ttm.toString());
		// columns.add(Stockfield.pb.toString());
		setColumns(columns);
	}

	public void initDatas() {
			nBenchMarkVOs = benchmarkLogicservice.getByName("hs300");
			datas = changeDatas(nBenchMarkVOs);
			setDatas(datas);
			setnBenchMarkVOs(nBenchMarkVOs);

			Allstart = nBenchMarkVOs.get(nBenchMarkVOs.size() - 1).getDate();
			Allend = nBenchMarkVOs.get(0).getDate();
			// BenchMark 日K线图时默认时间范围为1个月
			end = new Date();
			// 将end提前一月得到start
			Calendar c = Calendar.getInstance(Locale.CHINA);
			c.setTime(end);
			c.add(Calendar.MONTH, -1);
			start = c.getTime();
			start = DateTool.getDateByString(DateTool.getStringByDate(start));
	}

	@SuppressWarnings("unchecked")
	private Vector<Vector> changeDatas(ArrayList<NBenchMarkVO> nBenchMarkVOs) {

		Vector<Vector> view = new Vector<Vector>();
		for (NBenchMarkVO nBenchMarkVO : nBenchMarkVOs) {
			
			Vector v = new Vector();
			v.add(nBenchMarkVO.getName());
			v.add(DateTool.getStringByDate(nBenchMarkVO.getDate()));
			v.add(Formater.formate(nBenchMarkVO.getOpen()));
			v.add(Formater.formate(nBenchMarkVO.getClose()));
			v.add(nBenchMarkVO.getRate());
			v.add(Formater.formate(nBenchMarkVO.getHigh()));
			v.add(Formater.formate(nBenchMarkVO.getLow()));
			v.add(Formater.formate(nBenchMarkVO.getAdj_price()));
			v.add(Formater.formate(nBenchMarkVO.getVolume(),"0"));
			view.add(v);
		}
		return view;
	}

	@Override
	public void refreshDatas(Filter filter) {
		System.out.println("benchMark refresh filter");
		//保存nBenchMarkVOs
		nBenchMarkVOs =benchmarkLogicservice.getResultList(getnBenchMarkVOs(), filter);
		datas = changeDatas(nBenchMarkVOs);
		setDatas(datas);
		tablePanel.refreshDatas(getDatas());
	}

	@Override
	public void refreshDatas(SortType sortType) {
		nBenchMarkVOs =benchmarkLogicservice.sort(getnBenchMarkVOs(), sortType);
		datas = changeDatas(nBenchMarkVOs);
		setDatas(datas);
		tablePanel.refreshDatas(getDatas());
	}

	public void refreshDatas() {
		initDatas();
		tablePanel.refreshDatas(getDatas());
		tablePanel.getTable().repaint();
	}

	private void setBenchmarkControlPanelUI(CtrBtType ctlBtType) {
		switch (ctlBtType) {
		case tableBt:
			tablebutton.setOn();
			Kbutton.setNormal();
			break;
		case KBt:
			tablebutton.setNormal();
			Kbutton.setOn();
			break;
		default:
			break;
		}
	}

	public ArrayList<NBenchMarkVO> getnBenchMarkVOs() {
		return nBenchMarkVOs;
	}

	public void setnBenchMarkVOs(ArrayList<NBenchMarkVO> nBenchMarkVOs) {
		this.nBenchMarkVOs = nBenchMarkVOs;
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
