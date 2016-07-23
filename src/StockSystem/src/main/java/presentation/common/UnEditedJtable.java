package presentation.common;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

public class UnEditedJtable extends JTable {

	private static final long serialVersionUID = -4927432357138370566L;

	public UnEditedJtable(TableModel tm) {
		super(tm);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/*
	 * 设置表格不可修改
	 * 
	 * @param row
	 * 
	 * @param column
	 * 
	 * @return false
	 */
	@Override
	public boolean isCellEditable(int row, int column) {

		return false;

	}

}
