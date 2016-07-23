package presentation.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import enums.ShowPanelType;
import enums.SortType;
import enums.Stockfield;
import presentation.common.ImagePanel;
import presentation.common.ImgButton;
import presentation.common.Toast;
import presentation.common.UnEditedJtable;
import presentation.main.MainController;
import presentation.panel.FilterAndSort.BenchmarkFilterDialog;
import presentation.panel.FilterAndSort.SortDialog;
import presentation.panel.FilterAndSort.StockFilterDialog;
import presentation.panel.FilterAndSort.StockListFilterDialog;
import presentation.showPanel.ShowPanel;
import presentation.ui.Images;
import presentation.ui.UIConfig;

@SuppressWarnings("rawtypes")
public class TablePanel extends JPanel {

	private UnEditedJtable table;
	private boolean isTableClicked;

	Vector<Vector> datas;
	Vector<String> columns;
	JPanel controlPanel;
	JPanel topPanel;
	JPanel container;
	private int CurrentPage = 1;
	private static int PageCount = 10; // 每页显示10个数据
	Vector<Vector> pageDatas = new Vector<Vector>();// 每页显示的数据

	private static final long serialVersionUID = -7600191510182126777L;

	public static int TableW = 800;
	public static int TableH = 340;
	public static int TopH = 42;
	private JTextField textField_page;
	private JButton btnFirst;
	private JButton btnPre;
	private JButton btnNext;
	private JButton btnLast;
	private JButton btnRefresh;
	private JButton btnFilter;
	private JButton btnSort;
	private JLabel label;
	private JLabel label_title;
	Date start;
	Date end;

	ShowPanelType showPanelType;

	/**
	 * @param datas
	 *            数据
	 * @param columns
	 *            列名
	 * @param isTableClicked
	 *            boolean 表格是否可以编辑
	 * @param title
	 *            标题
	 * @param parent
	 *            tablePanel的父Panel
	 */
	public TablePanel(Vector<Vector> datas, Vector<String> columns, boolean isTableClicked, String title,
			ShowPanelType showPanelType) {
		setSize(ShowPanel.TablePanelW, ShowPanel.TablePanelH);
		setLayout(null);

		this.datas = datas;
		this.columns = columns;
		this.isTableClicked = isTableClicked;
		this.showPanelType = showPanelType;

		setPageDatasByPage(CurrentPage); // 1
		DefaultTableModel defaultTableModel = new DefaultTableModel(getPageDatas(), getColumns());
		table = new UnEditedJtable(defaultTableModel);
		// setDefaultRowFilter();
		// 设置表格颜色
		// table.setBackground(Color.black);
		// table.setForeground(Color.white);
		// table.setSelectionBackground(Color.gray);
		// table.setSelectionForeground(Color.blue);

		// table.setBounds(0, 0, TableW, TableH);

		// JScrollPane scrollPane = new JScrollPane();
		// scrollPane.setBounds(0, TopH, TableW, TableH);
		// scrollPane.setViewportView(table);
		// add(scrollPane);

		JTableHeader header = table.getTableHeader();
		header.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int tableColumn = header.columnAtPoint(e.getPoint());
				sortTable(tableColumn);
			}
		});

		container = new JPanel(new BorderLayout());
		container.setBounds(0, TopH, TableW, TableH);
		container.add(header, BorderLayout.NORTH);
		container.add(table, BorderLayout.CENTER);
		add(container);

		setTableFace();

		topPanel = new ImagePanel(Images.tableTopBg);
		topPanel.setBounds(0, 0, TableW, TopH);
		add(topPanel);
		topPanel.setLayout(null);

		// btnRefresh = new ImgButton(Images.RefreshBt, Images.RefreshBt);
		// btnRefresh.setLocation(704, 34);
		// topPanel.add(btnRefresh);
		btnRefresh = new ImgButton(Images.refreshBt, Images.refreshBt);
		btnRefresh.setLocation(758, 10);
		topPanel.add(btnRefresh);

		btnFilter = new ImgButton(Images.filterBt, Images.filterBt);
		btnFilter.setLocation(722, 10);
		topPanel.add(btnFilter);

		btnSort = new ImgButton(Images.sortBt, Images.sortBt);
		btnSort.setLocation(683, 10);
		topPanel.add(btnSort);
		
		
		

		label_title = new JLabel(title);
		label_title.setForeground(Color.WHITE);
		label_title.setFont(new Font("微软雅黑", Font.BOLD, 16));
		label_title.setBounds(10, 12, 286, 18);
		topPanel.add(label_title);

		controlPanel = new JPanel();
		controlPanel.setBounds(0, TableH + TopH, TableW, ShowPanel.TablePanelH - TableH - TopH);
		add(controlPanel);
		controlPanel.setLayout(null);
		initControlPanel();

	}

	private static final int ASCEND = 0;
	private static final int DOWN = 1;
	int flag = DOWN; // 默认降序

	private void sortTable(int tableColumn) {
		String columnName = table.getColumnName(tableColumn);
		System.out.println(columnName);
		SortType sortType = null;

		switch (flag) {
		case ASCEND:
			sortType = SortType.fromString(columnName + "升序");
			flag = DOWN;
			MainController.sortDatas(sortType);
			break;
		case DOWN:
			sortType = SortType.fromString(columnName + "降序");
			flag = ASCEND;
			MainController.sortDatas(sortType);
			break;
		default:
			break;
		}

	}

	private void initControlPanel() {
		btnPre = new JButton("上一页");
		btnPre.setBounds(257, 0, 90, 30);
		controlPanel.add(btnPre);

		btnFirst = new JButton("第一页");
		btnFirst.setBounds(155, 0, 90, 30);
		controlPanel.add(btnFirst);

		textField_page = new JTextField();
		textField_page.setHorizontalAlignment(SwingConstants.CENTER);
		textField_page.setText(String.valueOf(CurrentPage));
		textField_page.setBounds(352, 0, 50, 30);
		controlPanel.add(textField_page);
		textField_page.setColumns(10);

		btnNext = new JButton("下一页");
		btnNext.setBounds(440, 0, 90, 30);
		controlPanel.add(btnNext);

		btnLast = new JButton("最后页");
		btnLast.setBounds(542, 0, 90, 30);
		controlPanel.add(btnLast);

		label = new JLabel("/" + getLastPage());
		label.setBounds(403, 6, 38, 18);
		controlPanel.add(label);

		addButtonListener();
		addTableListener();

	}

	private void addTableListener() {
		if (isTableClicked) {
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						// 双击监听
						int row = table.rowAtPoint(e.getPoint());// 获得行位置
						int col = table.getColumn(Stockfield.name.toString()).getModelIndex();// 获得列名为"股票代号"的列
						String stockCode = (String) table.getValueAt(row, col);
						System.out.println(stockCode);
						// 调到stockPanel
						MainController.changeToStockPanel(stockCode, showPanelType);
					}
				}
			});
		}
	}
	
	public String getSelectedStock(){
		
		int row = table.getSelectedRow();// 获得行位置
		int col = table.getColumn(Stockfield.name.toString()).getModelIndex();// 获得列名为"股票代号"的列
		String stockCode = (String) table.getValueAt(row, col);
		return stockCode;
	}

	private void addButtonListener() {
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPageDatasByPage(1);
				CurrentPage = 1;
				refreshPageData();
			}
		});

		btnPre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (CurrentPage > 1) {
					setPageDatasByPage(--CurrentPage);
					refreshPageData();
				}

			}
		});

		textField_page.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (isInt(textField_page.getText())) {
					int page = Integer.parseInt(textField_page.getText());
					if (page > 0 && page <= getLastPage()) {
						setPageDatasByPage(page);
						CurrentPage = page;
						refreshPageData();
					} else {
						// System.out.println("请输入正确范围的数字");
						new Toast(MainController.frame, 1000, "请输入正确范围的数字", Toast.WARING);
					}

				} else {
					// System.out.println("请输入数字");
					new Toast(MainController.frame, 1000, "请输入数字", Toast.WARING);
				}

			}
		});

		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (CurrentPage < getLastPage()) {
					setPageDatasByPage(++CurrentPage);
					refreshPageData();
				}

			}
		});

		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPageDatasByPage(getLastPage());
				CurrentPage = getLastPage();
				refreshPageData();
			}
		});

		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SortDialog(MainController.frame, showPanelType);
			}
		});
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showPanelType == ShowPanelType.StockListPanel) {
					System.out.println("new StockList Filter Dialog");
					new StockListFilterDialog(MainController.frame);
				} else if (showPanelType == ShowPanelType.StockPanel) {
					System.out.println("new StocK Filter Dialog");
					new StockFilterDialog(MainController.frame, start, end);
				} else if (showPanelType == ShowPanelType.BenchmarkPanel) {
					System.out.println("new Benchmark Filter Dialog");
					new BenchmarkFilterDialog(MainController.frame, start, end);
				} else if (showPanelType == ShowPanelType.MarketPanel) {
					System.out.println("new MarketPanel Filter Dialog");
					// MarketPanel 的filterDialog和StockList 是一样的
					new StockListFilterDialog(MainController.frame);
				}
			}
		});
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainController.refresh();
			}
		});
	}

	// private void setAllDatas() {
	// if (table == null)
	// return;
	// DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
	// tableModel.setDataVector(getDatas(), getColumns());
	//// setDefaultRowFilter();
	// setTableFace();
	// getTable().repaint();
	// }

	/**
	 * @Description: 通过page获得数据
	 * 
	 */
	private void setPageDatasByPage(int page) {
		if (null != getDatas()) {
			pageDatas.removeAllElements();
			if (page > 0 && page <= getLastPage()) {

				if (page != getLastPage()) {
					// 如果不是最后一页
					for (int i = (page - 1) * PageCount; i < page * PageCount; i++) {

						pageDatas.add(getDatas().get(i));
					}
				} else {
					// 如果是最后一页
					for (int i = (page - 1) * PageCount; i < getDatas().size(); i++) {

						pageDatas.add(getDatas().get(i));
					}
				}
			} else {
				System.out.println("page 不对");

			}
		}
	}

	private int getLastPage() {
		if (null != getDatas()) {
			if (getDatas().size() % PageCount == 0) {
				return getDatas().size() / PageCount;
			} else {
				return getDatas().size() / PageCount + 1;
			}
		} else {
			return -1;
		}

	}

	public void refreshPageData() {
		if (this.table == null)
			return;
		// DefaultTableModel tableModel = (DefaultTableModel)
		// this.table.getModel();
		// tableModel.setDataVector(getPageDatas(), getColumns());
		// setTableFace();
		// getTable().repaint();
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		model.fireTableDataChanged();
		textField_page.setText(String.valueOf(CurrentPage));
		label.setText("/" + getLastPage());
	}

	public void refreshDatas(Vector<Vector> datas) {
		if (null != datas) {
			this.datas = datas;
			// // 清空筛选条件，这步很重要
			// if (null != getTable().getRowSorter()) {
			// ((TableRowSorter<TableModel>)
			// getTable().getRowSorter()).setRowFilter(null);
			// setDefaultRowFilter();
			// }
			CurrentPage = 1;
			setPageDatasByPage(CurrentPage);
			refreshPageData();
		} else {
			pageDatas.removeAllElements();
			refreshPageData();
		}

	}

	// public void setDefaultRowFilter() {
	// // 用于table排序
	// TableRowSorter<TableModel> sorter = new
	// TableRowSorter<TableModel>(table.getModel());
	// sorter.setSortable(0, false);
	// sorter.setSortable(1, false);
	// for (int col = 2; col < table.getColumnCount(); col++) {
	// sorter.setComparator(col, new OrderNumberComparator());
	// }
	// table.setRowSorter(sorter);
	// }

	public void setTableFace() {
		getTable().setRowHeight(30);
		getTable().getColumn(Stockfield.date.toString()).setMinWidth(85);


		if (getColumns().contains(Stockfield.changeString.toString())) {
			TableColumn changeStringColumn = getTable().getColumn(Stockfield.changeString.toString());
			int changeColumnIndex = changeStringColumn.getModelIndex();
			DefaultTableCellRenderer numtcr = new DefaultTableCellRenderer() {
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
			changeStringColumn.setCellRenderer(numtcr);
		}

	};

	public void setDates(Date start, Date end) {
		this.start = start;
		this.end = end;
	}

	private boolean isInt(String string) {

		try {
			Integer.parseInt(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public class OrderNumberComparator implements Comparator {

		public int compare(Object o1, Object o2) {
			int i = ((BigDecimal) o1).compareTo((BigDecimal) o2);
			return i;
		}

	}

	public UnEditedJtable getTable() {
		return table;
	}

	public void setTable(UnEditedJtable table) {
		this.table = table;
	}

	public int getCurrentPage() {
		return CurrentPage;
	}

	public void setCurrentPage(int currentPage) {
		CurrentPage = currentPage;
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

	public Vector<Vector> getPageDatas() {
		return pageDatas;
	}

	public void setPageDatas(Vector<Vector> pageDatas) {
		this.pageDatas = pageDatas;
	}

	public JLabel getLabel_title() {
		return label_title;
	}

	public void setLabel_title(JLabel label_title) {
		this.label_title = label_title;
	}
}
