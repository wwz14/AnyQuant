package presentation.spider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import data.utils.DataBase;
import utils.NamesFactory;
import utils.NetStatus;

public class SpiderSave {

	public static String basePath = "data/";

	public static void Save() throws Exception {

		StrongStockJsonResult yzqs = Spider.getYZQS(1, "changes", 0);
		StrongStockJsonResult yyqs = Spider.getYYQS(1, "changes", 0);
		ShortChangesJsonResult shortChanges = Spider.getDQZD(1, "_3changes", 0);
		LongChangesJsonResult longChanges = Spider.getCQZD(1, "_10changes", 0);

		HashMap<String, StockOrgAnalysis> map = new HashMap<>();
		List<String> strings = NamesFactory.getAllNames();
		for (String s : strings) {
			map.put(s, Spider.getStockOrgAnalysis(s));
		}

		DataBase.saveTxtFile(basePath + "yzqs.dat", yzqs);
		DataBase.saveTxtFile(basePath + "yyqs.dat", yyqs);
		DataBase.saveTxtFile(basePath + "shortChanges.dat", shortChanges);
		DataBase.saveTxtFile(basePath + "longChanges.dat", longChanges);
		DataBase.saveTxtFile(basePath + "map.dat", map);

		Iterator<Entry<String, StockOrgAnalysis>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<java.lang.String, presentation.spider.StockOrgAnalysis> entry = (Map.Entry<java.lang.String, presentation.spider.StockOrgAnalysis>) iter
					.next();
			String stockName = entry.getKey();
			StockOrgAnalysis stockOrgAnalysis = entry.getValue();
			System.out.println(stockName + stockOrgAnalysis.toString());
		}

	}

	static {
		if (NetStatus.isConnected()) {
			try {
				Save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static StrongStockJsonResult getyzqs() {
		return (StrongStockJsonResult) DataBase.load(basePath + "yzqs.dat");
	}

	public static StrongStockJsonResult getyyqs() {
		return (StrongStockJsonResult) DataBase.load(basePath + "yyqs.dat");
	}

	public static ShortChangesJsonResult getshortChanges() {
		return (ShortChangesJsonResult) DataBase.load(basePath + "shortChanges.dat");
	}

	public static LongChangesJsonResult getlongChanges() {
		return (LongChangesJsonResult) DataBase.load(basePath + "longChanges.dat");
	}

	public static StockOrgAnalysis getStockOrgAnalysis(String stockCode) {
		HashMap<String, StockOrgAnalysis> map = (HashMap<String, StockOrgAnalysis>) DataBase.load(basePath + "map.dat");
		return map.get(stockCode);
	}

	// public static void main(String[] args) {
	// try {
	// Save();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
}
