package presentation.showPanel.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import presentation.common.Toast;
import presentation.main.MainController;
import presentation.spider.LongChangesItem;
import presentation.spider.LongChangesJsonResult;
import presentation.spider.MarketValueItem;
import presentation.spider.MarketValueJsonResult;
import presentation.spider.PeriodItem;
import presentation.spider.PeriodJsonResult;
import presentation.spider.ShortChangesItem;
import presentation.spider.ShortChangesJsonResult;
import presentation.spider.Spider;
import presentation.spider.StockReduceItem;
import presentation.spider.StockReduceJsonResult;
import presentation.spider.StockRiseItem;
import presentation.spider.StockRiseJsonResult;
import presentation.spider.StrongStockItem;
import presentation.spider.StrongStockJsonResult;
import utils.DateTool;
import utils.Formater;

/**
 * 统计panel 有4个模块，短期涨跌统计和长期涨跌统计,一周强势股和一月强势股
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TongjiPanel extends JPanel {

	private static final long serialVersionUID = 6271043095102986710L;

	StrongStockJsonResult yzqs = Spider.getYZQS(1, "changes", 0);
	StrongStockJsonResult yyqs = Spider.getYYQS(1, "changes", 0);

	public TongjiPanel() {
		setSize(897, 600);
		setLayout(null);
		// tabbedPanel 区分四个模块
		JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);

		jTabbedPane.add("阶段最高最低", new PeriodListPanel());
		jTabbedPane.add("短期涨跌统计", new DQZDPanel());
		jTabbedPane.add("长期涨跌统计", new CQZDPanel());

		if (yzqs.getArraylist() != null) {
			jTabbedPane.add("一周强势股", new YZQSPanel());
		}
		if (yyqs.getArraylist() != null) {
			jTabbedPane.add("一月强势股", new YYQSPanel());
		}
		jTabbedPane.add("流通市值排行", new MarketValuePanel());
		jTabbedPane.add("连续上涨个股", new StockRisePanel());
		jTabbedPane.add("连续下跌个股", new StockReducePanel());

		jTabbedPane.setBounds(0, 0, 897, 600);
		add(jTabbedPane);

	}

	class PeriodListPanel extends TongjiShowPanel {

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
			PeriodJsonResult periodList = Spider.getPeriodList(UrlCount, realsortcolumnName, isAsc);
			ArrayList<PeriodItem> items = periodList.getArraylist();
			// 移除datas
			datas.removeAllElements();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			// 设置表格渲染的排序列的列号
			setRenderSortColumn(tableColumn);
			refreshTable();

		}

		@Override
		public void init() {
			datas = new Vector<Vector>();
			// 取得数据
			// getPeriodList?page=1&num=50&sort=_5high&asc=0&node=adr_hk
			PeriodJsonResult periodList = Spider.getPeriodList(1, "_5high", 0);
			ArrayList<PeriodItem> items = periodList.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}

			columns = new Vector<String>();
			columns.add("代号");
			columns.add("名称");
			columns.add("近5日最高价");
			columns.add("近5日最低价");
			columns.add("近5日涨跌幅");
			columns.add("近10日最高价");
			columns.add("近10日最低价");
			columns.add("近10日涨跌幅");
			columns.add("近20日最高价");
			columns.add("近20日最低价");
			columns.add("近20日涨跌幅");

			realcolumns = new Vector<String>();
			realcolumns.add("symbol");
			realcolumns.add("name");
			realcolumns.add("_5high");
			realcolumns.add("_5low");
			realcolumns.add("_5changes");
			realcolumns.add("_10high");
			realcolumns.add("_10low");
			realcolumns.add("_10changes");
			realcolumns.add("_20high");
			realcolumns.add("_20low");
			realcolumns.add("_20changes");

			title = "阶段最高最低";
			String today = items.get(0).getDay(); // 今天日期
			detail = "说明：沪深A股最近5、10、20日的阶段内最高价、最低价及涨跌幅。带括号数据表示统计区间有除权除息。      截止日期：" + today + "   注:数据每日18点更新";

			linkcolumn = 0;
			sortcolumn = columns.indexOf("近5日最高价");

			realsortcolumnName = "_5high";

		}

		@Override
		public void nextBatch() {
			// toast 通知
			new Toast(MainController.frame, 1000, "正在加载下一页...", Toast.MESSEGE);
			// 取得数据
			UrlCount++;
			PeriodJsonResult periodList = Spider.getPeriodList(UrlCount, realsortcolumnName, isAsc);
			ArrayList<PeriodItem> items = periodList.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			refreshTable();
		}

		private Vector changeDatas(PeriodItem periodItem) {
			Vector v = new Vector();
			v.add(periodItem.getSymbol());
			v.add(periodItem.getName());
			v.add(Formater.formate(periodItem.get_5high()));
			v.add(Formater.formate(periodItem.get_5low()));
			v.add(Formater.formatePercent(periodItem.get_5changes()));
			v.add(Formater.formate(periodItem.get_10high()));
			v.add(Formater.formate(periodItem.get_10low()));
			v.add(Formater.formatePercent(periodItem.get_10changes()));
			v.add(Formater.formate(periodItem.get_20high()));
			v.add(Formater.formate(periodItem.get_20low()));
			v.add(Formater.formatePercent(periodItem.get_20changes()));
			return v;
		}

		@Override
		public void setTableFace() {
			super.setTableFace();
			getTable().getColumnModel().getColumn(0).setPreferredWidth(50);
			getTable().getColumnModel().getColumn(1).setPreferredWidth(50);
			for (int i = 2; i < 11; i++) {
				getTable().getColumnModel().getColumn(i).setPreferredWidth(75);
			}

		}

	}

	/**
	 * 短期涨跌panel
	 */
	class DQZDPanel extends TongjiShowPanel {

		@Override
		public void init() {
			datas = new Vector<Vector>();
			// 取得数据
			// getShortList?page=1&num=50&sort=_3changes&asc=0&node=adr_hk";
			ShortChangesJsonResult shortChangesJsonResult = Spider.getDQZD(1, "_3changes", 0);
			ArrayList<ShortChangesItem> items = shortChangesJsonResult.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}

			columns = new Vector<String>();
			columns.add("代号");
			columns.add("名称");
			columns.add("近2日涨跌幅");
			columns.add("近2日换手率");
			columns.add("近2日振幅");
			columns.add("近3日涨跌幅");
			columns.add("近3日换手率");
			columns.add("近3日振幅");
			columns.add("近5日涨跌幅");
			columns.add("近5日换手率");
			columns.add("近5日振幅");

			realcolumns = new Vector<String>();
			realcolumns.add("symbol");
			realcolumns.add("name");
			realcolumns.add("_2changes");
			realcolumns.add("_2turnover");
			realcolumns.add("_2aov");
			realcolumns.add("_3changes");
			realcolumns.add("_3turnover");
			realcolumns.add("_3aov");
			realcolumns.add("_5changes");
			realcolumns.add("_5turnover");
			realcolumns.add("_5aov");

			title = "短期涨跌统计";
			String today = items.get(0).getDay(); // 今天日期
			detail = "说明：沪深A股最近2、3、5日的阶段涨跌幅、换手率、振幅，其中涨跌幅以复权价进行计算。      截止日期：" + today + "   注:数据每日18点更新";

			linkcolumn = 0;
			sortcolumn = columns.indexOf("近3日涨跌幅");

			realsortcolumnName = "_3changes";

		}

		private Vector changeDatas(ShortChangesItem shortChangesItem) {
			Vector v = new Vector();
			v.add(shortChangesItem.getSymbol());
			v.add(shortChangesItem.getName());
			v.add(Formater.formatePercent(shortChangesItem.get_2changes()));
			v.add(Formater.formatePercent(shortChangesItem.get_2turnover()));
			v.add(Formater.formatePercent(shortChangesItem.get_2aov()));
			v.add(Formater.formatePercent(shortChangesItem.get_3changes()));
			v.add(Formater.formatePercent(shortChangesItem.get_3turnover()));
			v.add(Formater.formatePercent(shortChangesItem.get_3aov()));
			v.add(Formater.formatePercent(shortChangesItem.get_5changes()));
			v.add(Formater.formatePercent(shortChangesItem.get_5turnover()));
			v.add(Formater.formatePercent(shortChangesItem.get_5aov()));
			return v;
		}

		@Override
		public void nextBatch() {
			// toast 通知
			new Toast(MainController.frame, 1000, "正在加载下一页...", Toast.MESSEGE);
			// 取得数据
			UrlCount++;
			ShortChangesJsonResult dqzd = Spider.getDQZD(UrlCount, realsortcolumnName, isAsc);
			ArrayList<ShortChangesItem> items = dqzd.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			refreshTable();

		}

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
			ShortChangesJsonResult dqzd = Spider.getDQZD(UrlCount, realsortcolumnName, isAsc);
			ArrayList<ShortChangesItem> items = dqzd.getArraylist();
			// 移除datas
			datas.removeAllElements();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			// 设置表格渲染的排序列的列号
			setRenderSortColumn(tableColumn);
			refreshTable();

		}

	}

	/**
	 * 长期涨跌panel
	 */
	class CQZDPanel extends TongjiShowPanel {

		@Override
		public void init() {
			datas = new Vector<Vector>();
			// 取得数据
			// getLongList?page=1&num=50&sort=_10changes&asc=0&node=adr_hk
			LongChangesJsonResult longChangesJsonResult = Spider.getCQZD(1, "_10changes", 0);
			ArrayList<LongChangesItem> items = longChangesJsonResult.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}

			columns = new Vector<String>();
			columns.add("代号");
			columns.add("名称");
			columns.add("近10日涨跌幅");
			columns.add("近10日换手率");
			columns.add("近10日振幅");
			columns.add("近30日涨跌幅");
			columns.add("近30日换手率");
			columns.add("近30日振幅");
			columns.add("近60日涨跌幅");
			columns.add("近60日换手率");
			columns.add("近60日振幅");

			realcolumns = new Vector<String>();
			realcolumns.add("symbol");
			realcolumns.add("name");
			realcolumns.add("_10changes");
			realcolumns.add("_10turnover");
			realcolumns.add("_10aov");
			realcolumns.add("_30changes");
			realcolumns.add("_30turnover");
			realcolumns.add("_30aov");
			realcolumns.add("_60changes");
			realcolumns.add("_60turnover");
			realcolumns.add("_60aov");

			title = "长期涨跌统计";
			String today = items.get(0).getDay(); // 今天日期
			detail = "说明：沪深A股最近10、30、60日的阶段涨跌幅、换手率、振幅，其中涨跌幅以复权价进行计算。      截止日期：" + today + "   注:数据每日18点更新";

			linkcolumn = 0;
			sortcolumn = columns.indexOf("近10日涨跌幅");

			realsortcolumnName = "_10changes";

		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private Vector changeDatas(LongChangesItem longChangesItem) {
			Vector v = new Vector();
			v.add(longChangesItem.getSymbol());
			v.add(longChangesItem.getName());
			v.add(Formater.formatePercent(longChangesItem.get_10changes()));
			v.add(Formater.formatePercent(longChangesItem.get_10turnover()));
			v.add(Formater.formatePercent(longChangesItem.get_10aov()));
			v.add(Formater.formatePercent(longChangesItem.get_30changes()));
			v.add(Formater.formatePercent(longChangesItem.get_30turnover()));
			v.add(Formater.formatePercent(longChangesItem.get_30aov()));
			v.add(Formater.formatePercent(longChangesItem.get_60changes()));
			v.add(Formater.formatePercent(longChangesItem.get_60turnover()));
			v.add(Formater.formatePercent(longChangesItem.get_60aov()));
			return v;
		}

		@Override
		public void nextBatch() {
			// toast 通知
			new Toast(MainController.frame, 1000, "正在加载下一页...", Toast.MESSEGE);
			// 取得数据
			UrlCount++;
			LongChangesJsonResult cqzd = Spider.getCQZD(UrlCount, realsortcolumnName, isAsc);
			ArrayList<LongChangesItem> items = cqzd.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			refreshTable();

		}

		@Override
		public void setTableFace() {
			super.setTableFace();
			getTable().getColumnModel().getColumn(0).setPreferredWidth(50);
			getTable().getColumnModel().getColumn(1).setPreferredWidth(50);
			getTable().getColumnModel().getColumn(4).setPreferredWidth(60);
			getTable().getColumnModel().getColumn(7).setPreferredWidth(60);
			getTable().getColumnModel().getColumn(10).setPreferredWidth(60);

		}

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
			LongChangesJsonResult cqzd = Spider.getCQZD(UrlCount, realsortcolumnName, isAsc);
			ArrayList<LongChangesItem> items = cqzd.getArraylist();
			// 移除datas
			datas.removeAllElements();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			// 设置表格渲染的排序列的列号
			setRenderSortColumn(tableColumn);
			refreshTable();

		}

	}

	/**
	 * 一周强势股panel
	 */
	class YZQSPanel extends TongjiShowPanel {

		@Override
		public void init() {
			datas = new Vector<Vector>();
			// 取得数据
			StrongStockJsonResult yzqs = Spider.getYZQS(1, "changes", 0);
			ArrayList<StrongStockItem> items = yzqs.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}

			columns = new Vector<String>();
			columns.add("代号");
			columns.add("名称");
			columns.add("周涨跌幅");
			columns.add("周开盘价 ");
			columns.add("周收盘价");
			columns.add("周最高价");
			columns.add("周最低价");
			columns.add("周成交量/股");
			columns.add("周成交金额");
			columns.add("换手率");
			columns.add("沪深300涨幅");
			columns.add("时间");

			realcolumns = new Vector<String>();
			realcolumns.add("symbol");
			realcolumns.add("name");
			realcolumns.add("changes");
			realcolumns.add("open");
			realcolumns.add("close");
			realcolumns.add("high");
			realcolumns.add("low");
			realcolumns.add("volume");
			realcolumns.add("amount");
			realcolumns.add("turnover");
			realcolumns.add("index_changes");
			realcolumns.add("day");

			title = "一周强势股";
			String today = items.get(0).getDay(); // 今天日期
			detail = "说明：沪深A股本周涨跌幅大于沪深300指数的股票，涨跌幅以复权价进行计算。      截止日期：" + today + "   注:数据每日18点更新";

			linkcolumn = 0;
			sortcolumn = columns.indexOf("周涨跌幅");

			realsortcolumnName = "changes";
		}

		@Override
		public void nextBatch() {
			// toast 通知
			new Toast(MainController.frame, 1000, "正在加载下一页...", Toast.MESSEGE);
			// 取得数据
			UrlCount++;
			StrongStockJsonResult yzqs = Spider.getYZQS(UrlCount, realsortcolumnName, isAsc);
			ArrayList<StrongStockItem> items = yzqs.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			refreshTable();

		}

		private Vector changeDatas(StrongStockItem yzqsItem) {
			Vector v = new Vector();
			v.add(yzqsItem.getSymbol());
			v.add(yzqsItem.getName());
			v.add(Formater.formatePercent(yzqsItem.getChanges()));
			v.add(Formater.formate(yzqsItem.getOpen()));
			v.add(Formater.formate(yzqsItem.getClose()));
			v.add(Formater.formate(yzqsItem.getHigh()));
			v.add(Formater.formate(yzqsItem.getLow()));
			v.add(yzqsItem.getVolume());
			v.add(yzqsItem.getAmount());
			v.add(Formater.formatePercent(yzqsItem.getTurnover()));
			v.add(Formater.formatePercent(yzqsItem.getIndex_changes()));
			v.add(yzqsItem.getDay());

			return v;
		}

		@Override
		public void setTableFace() {
			super.setTableFace();
			getTable().getColumnModel().getColumn(7).setPreferredWidth(100);
			getTable().getColumnModel().getColumn(8).setPreferredWidth(100);
			getTable().getColumnModel().getColumn(10).setPreferredWidth(100);
			getTable().getColumnModel().getColumn(11).setPreferredWidth(100);
		}

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
			StrongStockJsonResult yzqs = Spider.getYZQS(UrlCount, realsortcolumnName, isAsc);
			ArrayList<StrongStockItem> items = yzqs.getArraylist();
			// 移除datas
			datas.removeAllElements();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			// 设置表格渲染的排序列的列号
			setRenderSortColumn(tableColumn);
			refreshTable();

		}

	}

	/**
	 * 一月强势股panel
	 */
	class YYQSPanel extends TongjiShowPanel {

		@Override
		public void init() {

			datas = new Vector<Vector>();
			// 取得数据
			StrongStockJsonResult yyqs = Spider.getYYQS(1, "changes", 0);
			ArrayList<StrongStockItem> items = yyqs.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}

			columns = new Vector<String>();
			columns.add("代号");
			columns.add("名称");
			columns.add("月涨跌幅");
			columns.add("月开盘价 ");
			columns.add("月收盘价");
			columns.add("月最高价");
			columns.add("月最低价");
			columns.add("月成交量/股");
			columns.add("月成交金额");
			columns.add("换手率");
			columns.add("沪深300涨幅");
			columns.add("时间");

			realcolumns = new Vector<String>();
			realcolumns.add("symbol");
			realcolumns.add("name");
			realcolumns.add("changes");
			realcolumns.add("open");
			realcolumns.add("close");
			realcolumns.add("high");
			realcolumns.add("low");
			realcolumns.add("volume");
			realcolumns.add("amount");
			realcolumns.add("turnover");
			realcolumns.add("index_changes");
			realcolumns.add("day");

			title = "一月强势股";
			String today = items.get(0).getDay(); // 今天日期
			detail = "说明：沪深A股本月涨跌幅大于沪深300指数的股票，涨跌幅以复权价进行计算。      截止日期：" + today + "   注:数据每日18点更新";

			linkcolumn = 0;
			sortcolumn = columns.indexOf("月涨跌幅");

			realsortcolumnName = "changes";
		}

		private Vector changeDatas(StrongStockItem yyqsItem) {
			Vector v = new Vector();
			v.add(yyqsItem.getSymbol());
			v.add(yyqsItem.getName());
			v.add(Formater.formatePercent(yyqsItem.getChanges()));
			v.add(Formater.formate(yyqsItem.getOpen()));
			v.add(Formater.formate(yyqsItem.getClose()));
			v.add(Formater.formate(yyqsItem.getHigh()));
			v.add(Formater.formate(yyqsItem.getLow()));
			v.add(yyqsItem.getVolume());
			v.add(yyqsItem.getAmount());
			v.add(Formater.formatePercent(yyqsItem.getTurnover()));
			v.add(Formater.formatePercent(yyqsItem.getIndex_changes()));
			v.add(yyqsItem.getDay());

			return v;
		}

		@Override
		public void nextBatch() {
			// toast 通知
			new Toast(MainController.frame, 1000, "正在加载下一页...", Toast.MESSEGE);
			// 取得数据
			UrlCount++;
			StrongStockJsonResult yyqs = Spider.getYYQS(UrlCount, realsortcolumnName, isAsc);
			ArrayList<StrongStockItem> items = yyqs.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			refreshTable();
		}

		@Override
		public void setTableFace() {
			super.setTableFace();
			getTable().getColumnModel().getColumn(7).setPreferredWidth(100);
			getTable().getColumnModel().getColumn(8).setPreferredWidth(100);
			getTable().getColumnModel().getColumn(10).setPreferredWidth(100);
			getTable().getColumnModel().getColumn(11).setPreferredWidth(100);
		}

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
			StrongStockJsonResult yyqs = Spider.getYYQS(UrlCount, realsortcolumnName, isAsc);
			ArrayList<StrongStockItem> items = yyqs.getArraylist();
			// 移除datas
			datas.removeAllElements();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			// 设置表格渲染的排序列的列号
			setRenderSortColumn(tableColumn);
			refreshTable();

		}

	}

	class MarketValuePanel extends TongjiShowPanel {

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
			MarketValueJsonResult marketValueList = Spider.getMarketValueList(UrlCount, realsortcolumnName, isAsc);
			ArrayList<MarketValueItem> items = marketValueList.getArraylist();
			// 移除datas
			datas.removeAllElements();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			// 设置表格渲染的排序列的列号
			setRenderSortColumn(tableColumn);
			refreshTable();

		}

		@Override
		public void init() {
			datas = new Vector<Vector>();
			// 取得数据
			// Market_Center.getHQNodeDataNew?page=1&num=50&sort=nmc&asc=0&node=hs_a
			MarketValueJsonResult marketValueList = Spider.getMarketValueList(1, "nmc", 0);
			ArrayList<MarketValueItem> items = marketValueList.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}

			columns = new Vector<String>();
			columns.add("代号");
			columns.add("名称");
			columns.add("收盘价");
			columns.add("涨跌幅");
			columns.add("成交量/股");
			columns.add("换手率");
			columns.add("流通市值/万元");
			columns.add("总市值/万元");

			realcolumns = new Vector<String>();
			realcolumns.add("symbol");
			realcolumns.add("name");
			realcolumns.add("trade");
			realcolumns.add("changepercent");
			realcolumns.add("volume");
			realcolumns.add("turnoverratio");
			realcolumns.add("nmc");
			realcolumns.add("mktcap");

			title = "流通市值排名";
			String today = DateTool.getStringByDate(new Date()); // 今天日期
			detail = "说明：沪深A股流通市值排名。      截止日期：" + today + "   注:数据每日18点更新";

			linkcolumn = 0;
			sortcolumn = columns.indexOf("流通市值/万元");

			realsortcolumnName = "nmc";

		}

		private Vector changeDatas(MarketValueItem marketValueItem) {
			Vector v = new Vector();
			v.add(marketValueItem.getSymbol());
			v.add(marketValueItem.getName());

			v.add(marketValueItem.getTrade());
			v.add(marketValueItem.getChangepercent() + "%");
			v.add(marketValueItem.getVolume());
			v.add(Formater.formatePercent(marketValueItem.getTurnoverratio()));
			v.add(Formater.formate(marketValueItem.getNmc()));
			v.add(Formater.formate(marketValueItem.getMktcap()));

			return v;
		}

		@Override
		public void nextBatch() {
			// toast 通知
			new Toast(MainController.frame, 1000, "正在加载下一页...", Toast.MESSEGE);
			// 取得数据
			UrlCount++;
			MarketValueJsonResult marketValueList = Spider.getMarketValueList(UrlCount, realsortcolumnName, isAsc);
			ArrayList<MarketValueItem> items = marketValueList.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			refreshTable();
		}

	}

	class StockRisePanel extends TongjiShowPanel {

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
			StockRiseJsonResult stockRiseList = Spider.getStockRiseList(UrlCount, realsortcolumnName, isAsc);
			ArrayList<StockRiseItem> items = stockRiseList.getArraylist();
			// 移除datas
			datas.removeAllElements();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			// 设置表格渲染的排序列的列号
			setRenderSortColumn(tableColumn);
			refreshTable();
		}

		@Override
		public void init() {
			datas = new Vector<Vector>();
			// 取得数据
			// StatisticsService.getStockRiseConList?page=1&num=50&sort=day_con&asc=0&node=adr_hk
			StockRiseJsonResult stockRiseList = Spider.getStockRiseList(1, "day_con", 0);
			ArrayList<StockRiseItem> items = stockRiseList.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}

			/*
			 * String symbol; String name;
			 * 
			 * String day_con; //上涨天数 String changes_con; //阶段涨幅 String close;
			 * //收盘价 String changes; //涨跌幅 String volume; //成交量/股 String
			 * turnover; //换手率
			 * 
			 */

			columns = new Vector<String>();
			columns.add("代号");
			columns.add("名称");
			columns.add("上涨天数");
			columns.add("阶段涨幅");
			columns.add("收盘价");
			columns.add("涨跌幅");
			columns.add("成交量/股");
			columns.add("换手率");

			realcolumns = new Vector<String>();
			realcolumns.add("symbol");
			realcolumns.add("name");
			realcolumns.add("day_con");
			realcolumns.add("changes_con");
			realcolumns.add("close");
			realcolumns.add("changes");
			realcolumns.add("volume");
			realcolumns.add("turnover");

			title = "连续上涨个股";
			String today = items.get(0).getDay(); // 今天日期
			detail = "说明：沪深A股中连续上涨的股票。带括号数据表示统计区间有除权除息。      截止日期：" + today + "   注:数据每日18点更新";

			linkcolumn = 0;
			sortcolumn = columns.indexOf("上涨天数");

			realsortcolumnName = "day_con";
		}

		private Vector changeDatas(StockRiseItem stockRiseItem) {
			Vector v = new Vector();
			v.add(stockRiseItem.getSymbol());
			v.add(stockRiseItem.getName());

			v.add(stockRiseItem.getDay_con());
			v.add(Formater.formatePercent(stockRiseItem.getChanges_con()));
			v.add(Formater.formate(stockRiseItem.getClose()));
			v.add(Formater.formatePercent(stockRiseItem.getChanges()));
			v.add(stockRiseItem.getVolume());
			v.add(Formater.formatePercent(stockRiseItem.getTurnover()));

			return v;
		}

		@Override
		public void nextBatch() {
			// 没有下一页
			return;
		}

	}

	class StockReducePanel extends TongjiShowPanel {

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
			StockReduceJsonResult stockReduceList = Spider.getStockReduceList(UrlCount, realsortcolumnName, isAsc);
			ArrayList<StockReduceItem> items = stockReduceList.getArraylist();
			// 移除datas
			datas.removeAllElements();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			// 设置表格渲染的排序列的列号
			setRenderSortColumn(tableColumn);
			refreshTable();
		}

		@Override
		public void init() {
			datas = new Vector<Vector>();
			// 取得数据
			// StatisticsService.getStockReduceConList?page=1&num=50&sort=day_con&asc=0&node=adr_hk
			StockReduceJsonResult stockReduceList = Spider.getStockReduceList(1, "day_con", 0);
			ArrayList<StockReduceItem> items = stockReduceList.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}

			/*
			 * String symbol; String name;
			 * 
			 * String day_con; //上涨天数 String changes_con; //阶段涨幅 String close;
			 * //收盘价 String changes; //涨跌幅 String volume; //成交量/股 String
			 * turnover; //换手率
			 * 
			 */

			columns = new Vector<String>();
			columns.add("代号");
			columns.add("名称");
			columns.add("下跌天数");
			columns.add("阶段跌幅");
			columns.add("收盘价");
			columns.add("涨跌幅");
			columns.add("成交量/股");
			columns.add("换手率");

			realcolumns = new Vector<String>();
			realcolumns.add("symbol");
			realcolumns.add("name");
			realcolumns.add("day_con");
			realcolumns.add("changes_con");
			realcolumns.add("close");
			realcolumns.add("changes");
			realcolumns.add("volume");
			realcolumns.add("turnover");

			title = "连续上涨个股";
			String today = items.get(0).getDay(); // 今天日期
			detail = "说明：沪深A股中连续下跌的股票。带括号数据表示统计区间有除权除息。      截止日期：" + today + "   注:数据每日18点更新";

			linkcolumn = 0;
			sortcolumn = columns.indexOf("下跌天数");

			realsortcolumnName = "day_con";
		}

		private Vector changeDatas(StockReduceItem stockReduceItem) {
			Vector v = new Vector();
			v.add(stockReduceItem.getSymbol());
			v.add(stockReduceItem.getName());

			v.add(stockReduceItem.getDay_con());
			v.add(Formater.formatePercent(stockReduceItem.getChanges_con()));
			v.add(Formater.formate(stockReduceItem.getClose()));
			v.add(Formater.formatePercent(stockReduceItem.getChanges()));
			v.add(stockReduceItem.getVolume());
			v.add(Formater.formatePercent(stockReduceItem.getTurnover()));

			return v;
		}

		@Override
		public void nextBatch() {
			// toast 通知
			new Toast(MainController.frame, 1000, "正在加载下一页...", Toast.MESSEGE);
			// 取得数据
			UrlCount++;
			StockReduceJsonResult stockReduceList = Spider.getStockReduceList(UrlCount, realsortcolumnName, isAsc);
			ArrayList<StockReduceItem> items = stockReduceList.getArraylist();
			for (int i = 0; i < items.size(); i++) {
				datas.add(changeDatas(items.get(i)));
			}
			refreshTable();
		}

	}
}
