package presentation.panel.chartPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import enums.KLineType;
import enums.Stockfield;
import logic.Analysisbl.CompareLogic;
import logic.stockShowInfobl.StockListLogic;
import logicservice.Analysisblservice.CompareLogicservice;
import logicservice.showInfoblservice.StockListLogicservice;
import presentation.common.AutoTextFieldUtil;
import presentation.common.ImgButton;
import presentation.common.Toast;
import presentation.graph.echarts.GraphPanel;
import presentation.graph.echarts.RadarGraphPainter;
import presentation.graph.jfreechart.SpiderWebGraphPainter;
import presentation.graph.jfreechart.SplineGraphPainter;
import presentation.main.MainController;
import presentation.spider.SpiderSave;
import presentation.spider.StockOrgAnalysis;
import presentation.ui.Images;
import presentation.ui.UIConfig;
import utils.DateTool;
import utils.Formater;
import utils.NamesFactory;
import utils.NetStatus;
import utils.ValidateUtil;
import vo.AnalysisVO;
import vo.NStockVO;
import vo.RadarDataVO;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class StockAnalysisChartPanel extends ChartPanel {

	private static final long serialVersionUID = 9034279507143417672L;
	private JTextField textField;
	Vector<String> tips = new Vector<String>();

	CompareLogicservice compareLogicservice = new CompareLogic();
	StockListLogicservice stockListLogicservice = new StockListLogic();

	SplineGraphPainter splineGraphPainter;

	private JTable table;
	Vector<Vector> datas;
	Vector<String> columns;

	ArrayList<NStockVO> nStockVOs;
	JPanel twolinePanel;
	JPanel leidaPanel;
	private JComboBox comboBox;

	public StockAnalysisChartPanel() {

		super();

		initTips();
		initControlPanel();

		// twolinePanel = new JPanel();
		// twolinePanel.setBounds(0, 170, 897, 385);
		// add(twolinePanel);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				tabbedPane.getSelectedComponent().repaint();
			}
		});
		tabbedPane.setBounds(0, 170, 897, 390);
		add(tabbedPane);

		twolinePanel = new JPanel();
		tabbedPane.add("折线图", twolinePanel);

		leidaPanel = new JPanel();
		tabbedPane.add("雷达图", leidaPanel);
	}

	public void initChartPanel(ArrayList<String> strings) {
		if (strings.size() > 0) {

			twolinePanel.removeAll();
			leidaPanel.removeAll();

			splineGraphPainter = new SplineGraphPainter();
			splineGraphPainter.clear();

			ArrayList<RadarDataVO> radarDataVOs = getRadarVOs(strings);
			
//			RadarGraphPainter radarGraphPainter = new RadarGraphPainter(radarDataVOs.get(0));
//			for (int i = 1; i < radarDataVOs.size(); i++) {
//				radarGraphPainter.addData(radarDataVOs.get(i));
//			}
//			radarGraphPainter.setTitle("股票雷达图对比");
//			SwingUtilities.invokeLater(new Runnable() {
//				public void run() {
//					GraphPanel panel = new GraphPanel(radarGraphPainter);
//					panel.setPreferredSize(new Dimension(897, 330));
//					// panel.setSize(897, 300);
//					leidaPanel.add(panel);
//				}
//			});
//
//			leidaPanel.revalidate();
//			leidaPanel.repaint();

			SpiderWebGraphPainter spiderWebGraphPainter = new SpiderWebGraphPainter(radarDataVOs.get(0));
			for (int i = 1; i < radarDataVOs.size(); i++) {
				spiderWebGraphPainter.addData(radarDataVOs.get(i));
			}
			spiderWebGraphPainter.setTitle("股票雷达图对比");
			JPanel leipanel = spiderWebGraphPainter.getPanel();
			leidaPanel.add(leipanel);


			for (String stockCode : strings) {
				ArrayList<AnalysisVO<Date>> analysisVOs = compareLogicservice.changeRateLine(stockCode,
						(Stockfield) comboBox.getSelectedItem());
				splineGraphPainter.addData(analysisVOs);
			}

			// 如果显示涨跌幅则设置为百分比显示
			if ((Stockfield) comboBox.getSelectedItem() == Stockfield.changeString) {
				splineGraphPainter.setYPercent(true);
			}

			if (strings.size() == 2) {
				JPanel panel = splineGraphPainter.getPanel();
				panel.setPreferredSize(new Dimension(897, 270));
				twolinePanel.add(panel, BorderLayout.CENTER);

				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				textPane.setBackground(UIConfig.MarketBar);
				textPane.setPreferredSize(new Dimension(850, 60));
				twolinePanel.add(textPane, BorderLayout.SOUTH);

				// 当两只股票比较时，给出相关系数和置信区间Panel
				String confiText = "";
				String correlation = "";
				try {
					confiText = compareLogicservice.confiInterval(strings.get(0), strings.get(1),
							(Stockfield) comboBox.getSelectedItem());
					correlation = compareLogicservice.getcorrelationCoeffcient(strings.get(0), strings.get(1));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// JTextPanel
				Document doc = textPane.getDocument();
				SimpleAttributeSet attrSet = new SimpleAttributeSet();
				StyleConstants.setForeground(attrSet, Color.WHITE);
				StyleConstants.setBold(attrSet, true);
				StyleConstants.setFontSize(attrSet, 16);

				try {
					doc.insertString(doc.getLength(), confiText + "。", attrSet);
					doc.insertString(doc.getLength(), correlation, attrSet);
				} catch (BadLocationException e) {
					System.out.println("BadLocationException:   " + e);
				}

				// PopupMenu 显示
				JPopupMenu popup = new JPopupMenu();
				popup.setLayout(new BorderLayout());
				JPanel infoPanel = createtInfoPanel(confiText, correlation);
				popup.add(infoPanel, BorderLayout.CENTER);

				panel.addMouseMotionListener(new MouseMotionListener() {

					@Override
					public void mouseMoved(MouseEvent e) {
						Point point = e.getPoint();
						popup.show(panel, (int) point.getX(), (int) point.getY());
					}

					@Override
					public void mouseDragged(MouseEvent e) {
						// TODO Auto-generated method stub

					}
				});
			} else {
				// size != 2
				JPanel panel = splineGraphPainter.getPanel();
				panel.setPreferredSize(new Dimension(897, 330));
				twolinePanel.add(panel, BorderLayout.CENTER);
			}

			this.revalidate();
			this.repaint();
		} else {
			// string 为空，下面会报错，此时移除panel数据
			twolinePanel.removeAll();
			leidaPanel.removeAll();
			this.revalidate();
			this.repaint();
		}

	}

	private JPanel createtInfoPanel(String confiText, String correlation) {
		JPanel infoPanel = new JPanel();

		infoPanel.add(new JLabel(confiText));
		infoPanel.add(new JLabel("相关系数为：" + correlation));

		return infoPanel;
	}

	private ArrayList<RadarDataVO> getRadarVOs(ArrayList<String> strings) {

		ArrayList<RadarDataVO> radarDataVOs = new ArrayList<RadarDataVO>();

		for (String stockCode : strings) {
			StockOrgAnalysis stockOrgAnalysis = SpiderSave.getStockOrgAnalysis(stockCode);
			double gainProb = Formater.formate(Double.parseDouble(stockOrgAnalysis.getFrcGainProb()), "#0.000");
			double riseMeasure = Double.parseDouble(stockOrgAnalysis.getRiskMeasure());
			ArrayList<NStockVO> nStockVOs = stockListLogicservice.click(stockCode);
			double pb = nStockVOs.get(0).getPb().doubleValue();
			int vol = nStockVOs.get(0).getVolume().intValue();
			double end = nStockVOs.get(0).getClose().doubleValue();
			double start = nStockVOs.get(nStockVOs.size() - 1).getClose().doubleValue();
			
			double avgChangeRate = Formater.formate(Math.abs((end-start)/start), "0.00");
			RadarDataVO radarDataVO = new RadarDataVO(pb, vol, riseMeasure, gainProb, avgChangeRate, stockCode);
			radarDataVOs.add(radarDataVO);

		}

		return radarDataVOs;
	}

	private void initControlPanel() {
		JPanel controlPanel = new JPanel();
		controlPanel.setBounds(0, 0, 897, 170);
		add(controlPanel);
		controlPanel.setLayout(null);

		JLabel label = new JLabel("股票：");
		label.setBounds(6, 11, 55, 18);
		controlPanel.add(label);

		JButton button = new ImgButton(Images.addBt_normal, Images.addBt_on);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addStock();
			}

		});
		button.setBounds(222, 0, 43, 42);
		controlPanel.add(button);

		textField = new JTextField();
		AutoTextFieldUtil.setupAutoComplete(textField, tips);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { // 回车监听
					addStock();
				}
			}
		});
		textField.setBounds(56, 5, 156, 30);
		controlPanel.add(textField);
		textField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 40, 834, 130);
		controlPanel.add(scrollPane);

		initTableDatasAndColumns();
		DefaultTableModel defaultTableModel = new DefaultTableModel(datas, columns);
		table = new JTable(defaultTableModel) {
			private static final long serialVersionUID = -4739559426771494097L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;

			}
		};
		setTableFace();
		scrollPane.setViewportView(table);

		JButton button_1 = new ImgButton(Images.removeBt_normal, Images.removeBt_on);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeStocks();
			}

		});
		button_1.setBounds(844, 81, 43, 42);
		controlPanel.add(button_1);

		Stockfield[] types = new Stockfield[] { Stockfield.changeString, Stockfield.open, Stockfield.close,
				Stockfield.high, Stockfield.low, Stockfield.adj_price, Stockfield.volume, Stockfield.turnover,
				Stockfield.pe_ttm, Stockfield.pb };
		comboBox = new JComboBox(types);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				refresh();
			}
		});
		// 皮肤包下选中后的JComboBox 为蓝色，以下方法更改为白色
		Component component = comboBox.getEditor().getEditorComponent();
		if (component instanceof JTextField) {
			JTextField field = (JTextField) component;

			field.setEditable(false);

			field.setSelectionColor(
					field.getBackground()/* java.awt.Color.WHITE */);

			comboBox.setEditable(true);
		}
		comboBox.setBounds(298, 5, 103, 30);
		controlPanel.add(comboBox);

	};

	private void addStock() {

		if (!NetStatus.isConnected()) {
			new Toast(MainController.frame, 1000, "网络未连接...", Toast.ERROR);
			return;
		}
		if (!ValidateUtil.isStockCode(textField.getText())) {
			new Toast(MainController.frame, 1000, "请输入存在的股票代号", Toast.ERROR);
			return;
		}

		nStockVOs = stockListLogicservice.click(textField.getText());
		// 如果表格中已经存在该股票，则不能添加
		if (getStockCodes().contains(nStockVOs.get(0).getName())) {
			new Toast(MainController.frame, 1000, "请不要重复输入", Toast.WARING);
			return;
		}

		datas.add(changeDatas(nStockVOs.get(0)));
		refreshTableDatas();
		initChartPanel(getStockCodes());

	}

	private void removeStocks() {
		System.out.println("remove Stocks");
		getStockCodes().removeAll(getSelectedStockCodes());
		refreshTableDatas();
		initChartPanel(getStockCodes());
	}

	private ArrayList<String> getSelectedStockCodes() {
		ArrayList<String> stockCodes = new ArrayList<String>();
		Vector<Vector> selecetedDatas = new Vector<Vector>();
		int[] rows = table.getSelectedRows();
		if (rows.length > 0) {
			int column = table.getColumn(Stockfield.name.toString()).getModelIndex();

			for (int i = 0; i < rows.length; i++) {
				System.out.println("select row :" + rows[i]);
				stockCodes.add((String) table.getValueAt(rows[i], column));
				Vector v = datas.get(rows[i]);
				selecetedDatas.add(v);
			}
			datas.removeAll(selecetedDatas);
		} else {
			new Toast(MainController.frame, 1000, "没有选择需要移除的股票", Toast.WARING);
		}
		return stockCodes;

	}

	private void initTableDatasAndColumns() {

		datas = new Vector<Vector>();

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
	}

	private void initTips() {
		List<String> strings = NamesFactory.getAllNames();
		for (String s : strings) {
			tips.add(s);
		}
	}

	private Vector changeDatas(NStockVO nStockVO) {
		Vector v = new Vector();
		v.add(nStockVO.getName());
		v.add(DateTool.getStringByDate(nStockVO.getDate()));
		v.add(nStockVO.getOpen());
		v.add(nStockVO.getClose());
		v.add(nStockVO.getRate());
		v.add(nStockVO.getHigh());
		v.add(nStockVO.getLow());
		v.add(nStockVO.getAdj_price());
		v.add(nStockVO.getVolume());
		v.add(nStockVO.getTurnover());
		v.add(nStockVO.getPe_ttm());
		v.add(nStockVO.getPb());
		return v;
	}

	public void setTableFace() {
		table.setRowHeight(30);
		table.getColumn(Stockfield.date.toString()).setMinWidth(85);
		if (columns.contains(Stockfield.changeString.toString())) {
			TableColumn changeStringColumn = table.getColumn(Stockfield.changeString.toString());
			int changeColumnIndex = changeStringColumn.getModelIndex();
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
				private static final long serialVersionUID = 4148022539608976339L;

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					if (column == changeColumnIndex) {
						if (value instanceof String) {
							String changeString = (String) value;
							String s = changeString.replaceAll("%", "");
							Double d = Double.parseDouble(s);
							if (d > 0) {
								setForeground(UIConfig.RED);
							} else if (d < 0) {
								setForeground(UIConfig.GREEN);
							} else {
								// d=0
								setForeground(UIConfig.DefaultTableForeGround);
							}
						}
					}

					return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				}
			};
			changeStringColumn.setCellRenderer(tcr);
		}

	};

	public void refreshTableDatas() {
		if (this.table == null)
			return;
		DefaultTableModel tableModel = (DefaultTableModel) this.table.getModel();
		tableModel.setDataVector(datas, columns);
		setTableFace();
		table.repaint();
	}

	private ArrayList<String> getStockCodes() {
		System.out.println("get StockCodes");
		ArrayList<String> stockCodes = new ArrayList<String>();
		for (int i = 0; i < datas.size(); i++) {
			String stockCode = (String) datas.get(i).get(0);
			stockCodes.add(stockCode);
		}
		return stockCodes;
	}

	@Override
	public void refresh() {
		initChartPanel(getStockCodes());
	}

	@Override
	public void refresh(KLineType kLineType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Date start, Date end) {
		// TODO Auto-generated method stub

	}
}
