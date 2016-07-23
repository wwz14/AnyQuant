package presentation.showPanel.analysis;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import presentation.common.BaseDataLoader;
import presentation.common.NTScrollPane;
import presentation.common.UnEditedJtable;

public abstract class TongjiShowPanel extends JPanel implements BaseDataLoader {

	private static final long serialVersionUID = -5022836777754909814L;

	private JTable table;
	Vector<Vector> datas;
	/**
	 * 显示的列名
	 */
	Vector<String> columns;
	/**
	 * 真实的用于排序的列名
	 */
	Vector<String> realcolumns;
	String title = "标题";
	String detail = "说明";
	// 超链接的列号
	int linkcolumn = 0;
	// 排序的列号
	int sortcolumn = 0;

	private static final int ASCEND = 0;
	private static final int DOWN = 1;
	int flag = DOWN; // 默认降序
	LinkCellRenderer renderer;

	int UrlCount = 1; // page
	String realsortcolumnName = ""; // 排序用的列名
	int isAsc = 0; // 是否升序

	public TongjiShowPanel() {
		setLayout(null);

		init();

		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(0, 0, 897, 30);
		add(lblTitle);

		JLabel lblDetail = new JLabel(detail);
		lblDetail.setBounds(0, 30, 897, 24);
		add(lblDetail);

		NTScrollPane scrollPane = new NTScrollPane();
		scrollPane.setDataLoader(this);
		scrollPane.setBounds(0, 54, 897, 500);
		add(scrollPane);

		TableModel defaultTableModel = new DefaultTableModel(datas, columns);
		table = new UnEditedJtable(defaultTableModel);
		setTableFace();
		scrollPane.setViewportView(table);

		renderer = new LinkCellRenderer(linkcolumn) {
			@Override
			public void mouseClicked(MouseEvent e) {
//				Point p = e.getPoint();
//				int c = table.columnAtPoint(p);
//				if (c == 0) {
//					// 股票代号第0列，则调到该股票具体界面
//					String stockCode = table.getValueAt(table.rowAtPoint(p), 0).toString().trim();
//					MainController.changeToStockPanel(stockCode, ShowPanelType.StockAnalysisPanel);
//				}

			}

		};
		renderer.setSortcolumn(sortcolumn);
		// 注入渲染器
		table.setDefaultRenderer(Object.class, renderer);
		// 注入监听器
		table.addMouseListener(renderer);
		table.addMouseMotionListener(renderer);

		JTableHeader header = table.getTableHeader();
		header.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int tableColumn = header.columnAtPoint(e.getPoint());
				sortTable(tableColumn, flag);
				if (flag == ASCEND) {
					flag = DOWN;
				} else if (flag == DOWN) {
					flag = ASCEND;
				}
			}
		});

	}

	/**
	 * 排序表格
	 * 
	 * @param tableColumn
	 *            列号
	 * @param isAsc
	 *            是否升序
	 */
	protected abstract void sortTable(int tableColumn, int isAsc);

	public void setTableFace() {
		table.setRowHeight(30);
	}

	//
	// public void setTableHeaderColor(int columnIndex, final Color c) {
	// TableColumn column =
	// table.getTableHeader().getColumnModel().getColumn(columnIndex);
	//// DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
	//// cellRenderer.setForeground(c);
	//// column.setHeaderRenderer(cellRenderer);
	//
	// DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer(){
	// public Component getTableCellRendererComponent(JTable table, Object
	// value, boolean isSelected, boolean hasFocus, int row, int column)
	// {
	// JComponent comp = (JComponent) super.getTableCellRendererComponent(table,
	// value, isSelected, hasFocus, row, column);
	// comp.setForeground(c);
	// comp.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
	// return comp;
	// }
	// };
	// column.setHeaderRenderer(cellRenderer);
	//
	//
	//
	// }

	/**
	 * 初始化 datas数据、columns列名、 title标题、detail说明、 超链接的列号linkcolumn
	 * 、排序的列号sortcolumn 排序用的列名realsortcolumnName
	 */
	public abstract void init();

	@Override
	public abstract void nextBatch();

	public void refreshTable() {
		if (this.table == null)
			return;
		DefaultTableModel tableModel = (DefaultTableModel) this.table.getModel();
		tableModel.setDataVector(datas, columns);
		setTableFace();
		table.repaint();
	}

	public JTable getTable() {
		return table;
	}

	public void setRenderSortColumn(int sortcolumn) {
		renderer.setSortcolumn(sortcolumn);
	}

}
