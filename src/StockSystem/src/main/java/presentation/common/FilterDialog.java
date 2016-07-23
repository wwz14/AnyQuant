package presentation.common;

import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import presentation.panel.TablePanel;
import utils.ValidateUtil;

public class FilterDialog extends javax.swing.JDialog {

	private static final long serialVersionUID = -8531401935024698629L;
	private JTextField jTextField_search;
	Frame frame = null;
	TablePanel tablePanel;
	private static FilterDialog instance = null;
	private JTextField jtextField_start;
	private JTextField jtextField_end;

	public static FilterDialog getInstance(Frame parent, TablePanel tablePanel, boolean modal) {
		if (instance == null) {
			instance = new FilterDialog(parent, tablePanel, modal);
		}
		return instance;
	}

	public FilterDialog(Frame parent, TablePanel tablePanel, boolean modal) {
		super(parent, modal);
		this.frame = parent;
		this.tablePanel = tablePanel;
		this.setLocationRelativeTo(parent);
		initComponents();
	}

	private void initComponents() {

		jTextField_search = new JTextField();
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		jTextField_search.setBounds(21, 10, 191, 29);
		getContentPane().add(jTextField_search);

		jtextField_start = new JTextField();
		jtextField_start.setBounds(21, 75, 66, 29);
		getContentPane().add(jtextField_start);
		jtextField_start.setColumns(10);

		jtextField_end = new JTextField();
		jtextField_end.setBounds(146, 75, 66, 29);
		getContentPane().add(jtextField_end);
		jtextField_end.setColumns(10);
		
		int columnId=tablePanel.getTable().getSelectedColumn();
		JLabel label = new JLabel("筛选属性："+tablePanel.getTable().getColumnName(columnId));
		label.setBounds(21, 49, 191, 15);
		getContentPane().add(label);
		setTitle("过滤条件");
		setSize(248, 152);
		setVisible(true);

		addListener();
	}

	private void addListener() {
		
			jTextField_search.addKeyListener(new MyKeyAdapter());

			jtextField_start.addKeyListener(new MyKeyAdapter());

			jtextField_end.addKeyListener(new MyKeyAdapter());
		
	}
	
	class MyKeyAdapter extends KeyAdapter {
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent e) {
			JTable jtable = tablePanel.getTable();
			if (jtable != null) {
				String text = jTextField_search.getText();
				String starttext = jtextField_start.getText();
				String endtext = jtextField_end.getText();
				if (!text.equals("") ||!starttext.equals("") ||!endtext.equals("")) {
					
					if (jtable.getRowSorter() != null) {// 当Jtable中无数据时，jtable.getRowSorter()是null。
						List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
						if (!text.equals("")) {
							filters.add(RowFilter.regexFilter(Pattern.quote(text)));
						}
						
						
						if (!starttext.equals("")&&ValidateUtil.isNumber(starttext)) {
							
								filters.add(RowFilter.numberFilter(ComparisonType.AFTER,
										BigDecimal.valueOf(Double.parseDouble(starttext)),jtable.getSelectedColumn()));
						
						}

						if (!endtext.equals("")&&ValidateUtil.isNumber(endtext)) {
							
								filters.add(RowFilter.numberFilter(ComparisonType.BEFORE,
										BigDecimal.valueOf(Double.parseDouble(endtext)),jtable.getSelectedColumn()));
							
							
						}

						RowFilter<Object, Object> andfilter = RowFilter.andFilter(filters);

						((TableRowSorter<TableModel>) jtable.getRowSorter()).setRowFilter(andfilter);
					}
				}
			}
		}
	}
}
