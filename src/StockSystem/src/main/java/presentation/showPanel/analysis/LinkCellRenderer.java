package presentation.showPanel.analysis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import presentation.ui.UIConfig;

/**
 * 显示超链接的JTable单元格渲染器
 */
public class LinkCellRenderer extends DefaultTableCellRenderer implements MouseInputListener {

	private static final long serialVersionUID = -8090902632868119610L;
	// 鼠标事件所在的行
	private int row = -1;
	// 鼠标事件所在的列
	private int col = -1;
	// 当前监听的Table
	private JTable table = null;
	// 超链接的列号
	private int linkcolumn = -1;
	// 排序的列号
	private int sortcolumn = -1;

	public LinkCellRenderer(int linkcolumn) {
		super();
		if (linkcolumn >= 0) {
			this.linkcolumn = linkcolumn;
		} else {
			Logger.getLogger(LinkCellRenderer.class.getName()).log(Level.SEVERE, "列号必须大于等于0");
		}
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// 恢复默认状态
		this.table = table;
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		this.setForeground(Color.black);
		this.setText(value.toString());
		if (column == sortcolumn) {

			if (value instanceof String) {
				String string = (String) value;
				// 如果是String且有%
				if (string.indexOf("%") != -1) {
					// 改变前景色(文字颜色)
					String s = string.replaceAll("%", "");
					Double d = Double.parseDouble(s);
					if (d > 0) {
						setForeground(UIConfig.RED);
					} else if (d < 0) {
						setForeground(UIConfig.GREEN);
					} else {
						// d=0
						setForeground(UIConfig.DefaultTableForeGround);
					}
				}else{
					//其他的设置为红色
					setForeground(UIConfig.RED);
				}
			}
		}
		// 如果当前需要渲染器的单元格就是鼠标事件所在的单元格
		if (row == this.row && column == this.col) {
			// 如果是显示超链接的列
			if (column == linkcolumn ) {
				// 改变前景色(文字颜色)
				this.setForeground(Color.white);
				// 改变鼠标形状
				table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				// 显示超链接样式
				this.setText("<html><u>" + value.toString() + "</u></html>");
			}
			setBackground(UIConfig.BLUE);
		} else if (isSelected) {
			// 如果单元格被选中,则改变前景色和背景色
			table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			setForeground(Color.white);
			setBackground(UIConfig.BLUE);
		} else {
			// 其他情况下恢复默认背景色
			table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			setBackground(Color.white);
		}
		return this;
	}

	/**
	 * 鼠标移出事件
	 * 
	 * @param e
	 */
	public void mouseExited(MouseEvent e) {
		if (table != null) {
			int oldRow = row;
			int oldCol = col;
			// 鼠标移出目标表格后,恢复行列数据到默认值
			row = -1;
			col = -1;
			// 当之前的行列数据有效时重画相关区域
			if (oldRow != -1 && oldCol != -1) {
				Rectangle rect = table.getCellRect(oldRow, oldCol, false);
				table.repaint(rect);
			}
		}
	}

	/**
	 * 鼠标拖动事件
	 * 
	 * @param e
	 */
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	/**
	 * 鼠标移动事件
	 * 
	 * @param e
	 */
	public void mouseMoved(MouseEvent e) {
		if (table != null) {
			Point p = e.getPoint();
			int oldRow = row;
			int oldCol = col;
			row = table.rowAtPoint(p);
			col = table.columnAtPoint(p);
			// 重画原来的区域
			if (oldRow != -1 && oldCol != -1) {
				Rectangle rect = table.getCellRect(oldRow, oldCol, false);
				table.repaint(rect);
			}
			// 重画新的区域
			if (row != -1 && col != -1) {
				Rectangle rect = table.getCellRect(row, col, false);
				table.repaint(rect);
			}
		}
	}

	/**
	 * 鼠标单击事件
	 * 
	 * @param e
	 */
	public void mouseClicked(MouseEvent e) {
		// 获取事件所在的行列坐标信息
		Point p = e.getPoint();
		int c = table.columnAtPoint(p);
		if (c != linkcolumn) {
			return;
		}
		int r = table.rowAtPoint(p);
		try {
			// 取得目标单元格的值,即链接信息
			URL url = new URL(table.getValueAt(r, c).toString());
			// 在系统默认浏览器中打开链接
			Desktop.getDesktop().browse(url.toURI());
		} catch (Exception ex) {
			Logger.getLogger(LinkCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * 鼠标按下事件
	 * 
	 * @param e
	 */
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * 鼠标释放事件
	 * 
	 * @param e
	 */
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * 鼠标进入事件
	 * 
	 * @param e
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 将在表格中呈现的数据
		Object[] header = new String[] { "标题", "链接" };
		Object[][] data = new String[10][2];
		for (int i = 0; i < 10; i++) {
			data[i][0] = "网页标题";
			data[i][1] = "http://www.yshjava.cn/post/529.html";
		}
		// 构建表格数据模型
		TableModel model = new DefaultTableModel(data, header);
		// 创建表格对象
		JTable table = new JTable(model);
		// 创建单元格渲染器暨鼠标事件监听器
		LinkCellRenderer renderer = new LinkCellRenderer(-1);
		// 注入渲染器
		table.setDefaultRenderer(Object.class, renderer);

		// 注入监听器
		table.addMouseListener(renderer);
		table.addMouseMotionListener(renderer);

		// 为表格增加爱滚动窗格
		JScrollPane sp = new JScrollPane(table);
		// 创建窗口程序
		JFrame f = new JFrame("JTable 单元格超链接测试");
		f.getContentPane().add(sp, BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 600);
		f.setLocationRelativeTo(null);
		// 显示窗口
		f.setVisible(true);
	}

	public void setSortcolumn(int sortcolumn) {
		this.sortcolumn = sortcolumn;
	}

}