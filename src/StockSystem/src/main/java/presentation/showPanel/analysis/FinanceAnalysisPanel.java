package presentation.showPanel.analysis;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import presentation.common.Toast;
import presentation.main.MainController;
import presentation.spider.Profit;
import presentation.spider.Spider;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FinanceAnalysisPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3144657059057302919L;

	public FinanceAnalysisPanel() {
		setSize(897, 600);
		setLayout(null);
		// tabbedPanel 区分四个模块
		JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);

		jTabbedPane.add("盈利能力", new ProfitPanel());

		jTabbedPane.setBounds(0, 0, 897, 600);
		add(jTabbedPane);
	}

	class ProfitPanel extends TongjiShowPanel {

		@Override
		protected void sortTable(int tableColumn, int isAsc) {
			String sort = columns.get(tableColumn).toString();
			String sortType = "";
			if (isAsc == 1) {
				sortType = sort + "升序";
			} else if (isAsc == 0) {
				sortType = sort + "降序";
			}
			// 初始化UrlCount,保存realsortcolumnName,isAsc
			UrlCount = 1;
			realsortcolumnName = realcolumns.get(tableColumn).toString();
			this.isAsc = isAsc;
			// toast 通知
			new Toast(MainController.frame, 1000, "正在排序：" + sortType, Toast.MESSEGE);
			// 取得数据
			ArrayList<Profit> profitList = Spider.getProfit(UrlCount, realsortcolumnName, isAsc);
			// 移除datas
			datas.removeAllElements();
			for (int i = 0; i < profitList.size(); i++) {
				datas.add(changeDatas(profitList.get(i)));
			}
			// 设置表格渲染的排序列的列号
			setRenderSortColumn(tableColumn);
			refreshTable();

		}

		@Override
		public void init() {
			datas = new Vector<Vector>();
			// 取得数据
			ArrayList<Profit> profitList = Spider.getProfit(1, "roe", 0);
			for (int i = 0; i < profitList.size(); i++) {
				datas.add(changeDatas(profitList.get(i)));
			}

			columns = new Vector<String>();

			columns.add("代号");
			columns.add("名称");
			columns.add("净资产收益率(%)");
			columns.add("净利率(%)");
			columns.add("毛利率(%)");
			columns.add("净利润(百万元)");
			columns.add("每股收益(元)");
			columns.add("营业收入(百万元)");
			columns.add("每股主营业务收入(元)");

			realcolumns = new Vector<String>();
			realcolumns.add("symbol");
			realcolumns.add("name");

			realcolumns.add("roe");
			realcolumns.add("netprofitmargin");
			realcolumns.add("profitmargin");
			realcolumns.add("netprofit");
			realcolumns.add("eps");
			realcolumns.add("income");
			realcolumns.add("mips");

			title = "盈利能力";
			detail = "说明：沪深A股盈利能力排名。  ";

			sortcolumn = columns.indexOf("净资产收益率(%)");

			realsortcolumnName = "roe";

		}

		private Vector changeDatas(Profit profit) {

			Vector v = new Vector();
			v.add(profit.getSymbol());
			v.add(profit.getName());

			v.add(profit.getRoe());
			v.add(profit.getNetprofitmargin());
			v.add(profit.getProfitmargin());
			v.add(profit.getNetprofit());
			v.add(profit.getEps());
			v.add(profit.getIncome());
			v.add(profit.getMips());

			return v;
		}

		@Override
		public void nextBatch() {
			// toast 通知
			new Toast(MainController.frame, 1000, "正在加载下一页...", Toast.MESSEGE);
			// 取得数据
			UrlCount++;
			ArrayList<Profit> profitList = Spider.getProfit(UrlCount, realsortcolumnName, isAsc);
			for (int i = 0; i < profitList.size(); i++) {
				datas.add(changeDatas(profitList.get(i)));
			}
			refreshTable();

		}

		@Override
		public void setTableFace() {
			super.setTableFace();
			getTable().getColumnModel().getColumn(0).setPreferredWidth(50);
			getTable().getColumnModel().getColumn(1).setPreferredWidth(50);
			getTable().getColumnModel().getColumn(2).setPreferredWidth(100);
			getTable().getColumnModel().getColumn(3).setPreferredWidth(50);
			getTable().getColumnModel().getColumn(4).setPreferredWidth(50);
			getTable().getColumnModel().getColumn(7).setPreferredWidth(80);
			getTable().getColumnModel().getColumn(8).setPreferredWidth(100);

		}

	}

}
