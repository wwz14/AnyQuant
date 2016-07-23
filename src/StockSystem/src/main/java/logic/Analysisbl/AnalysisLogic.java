package logic.Analysisbl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import data.StockListData;
import data.sqlImpl.StockDataSQL;
import dataservice.StockListDataservice;
import exception.StatusNotOKException;
import logic.Analysisbl.regression.Polyfit;
import logic.utils.DateAndAverage;
import logicservice.Analysisblservice.AnalysisInMarketLogicservice;
import logicservice.Analysisblservice.MarketLogicservice;
import po.NStockPO;
import utils.DateTool;
import utils.WHICHIMP;
import vo.AnalysisVO;
import vo.MarketVO;
import vo.NStockVO;

public class AnalysisLogic implements AnalysisInMarketLogicservice {
	private StockListDataservice stockdataService;
	private MarketLogicservice marketService;
	// private StockCacheDataservice catchService = new StockCacheTxt();

	private ArrayList<NStockPO> todayStockList = new ArrayList<NStockPO>();

	public AnalysisLogic() {
		if (WHICHIMP.isSQL) {
			// sql实现
			stockdataService = new StockDataSQL();
		} else {
			stockdataService = new StockListData();
		}
		marketService = new MarketLogic();
		read();
	}

	@Override
	public ArrayList<AnalysisVO<Date>> averageMarketPrice(Date start, Date end) {
		long betweenDays = betweenDays(start, end);
		ArrayList<AnalysisVO<Date>> result = new ArrayList<>();

		@SuppressWarnings("unused")
		ArrayList<MarketVO> market = new ArrayList<MarketVO>();
		Date datePointer = start;
		ArrayList<DateAndAverage> dateAndAverageList = new ArrayList<DateAndAverage>();
		for (int i = 0; i < betweenDays; i++) {
			// 计算某一日期，16支股票的加权平均数，
			ArrayList<NStockPO> poList = new ArrayList<>();
			try {
				poList = stockdataService.getAllByTime(DateTool.getStringByDate(datePointer));
			} catch (StatusNotOKException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (!poList.isEmpty()) {
				DateAndAverage thisdayAverage = average(poList, datePointer);
				dateAndAverageList.add(thisdayAverage);
			}
			datePointer = DateTool.beforeDate(datePointer, 1);
		}

		for (int i = 0; i < dateAndAverageList.size(); i++) {
			AnalysisVO<Date> vo = new AnalysisVO<Date>("银行", dateAndAverageList.get(i).getAveraage().doubleValue(),
					dateAndAverageList.get(i).getDate(), dateAndAverageList.get(i).getDate());
			vo.setInTime(true);
			result.add(vo);
		}

		return result;
	}

	public String averageMarket(Date start, Date end) throws Exception {
		ArrayList<AnalysisVO<Date>> result = averageMarketPrice(start, end);

		double[] x = new double[result.size()];
		double[] y = new double[result.size()];
		for (int i = 0; i < result.size(); i++) {
			x[i] = i + 1;
			y[i] = result.get(i).getStatistic();
		}
		// 计算macd的变化增、减性
		Polyfit polyfit = new Polyfit(x, y, Math.min(4, result.size() - 1));
		double gradient = polyfit.gradient();
		if (gradient > 0)
			return "银行行业均价呈上升趋势";
		else if (gradient < 0)
			return "银行行业均价出现下降趋势";
		else
			return "银行行业均价走势稳定";
	}

	/**
	 * 行业内市盈率柱状图
	 * 
	 * @param marketname
	 *            行业名
	 * @return TODO
	 */
	@Override
	public ArrayList<AnalysisVO<String>> shiYingLuBar() {
		ArrayList<AnalysisVO<String>> result = new ArrayList<>();
		for (int i = 0; i < todayStockList.size(); i++) {
			AnalysisVO<String> vo = new AnalysisVO<String>(todayStockList.get(i).getName(),
					todayStockList.get(i).getPe_ttm().doubleValue(), todayStockList.get(i).getDate(),
					todayStockList.get(i).getName());
			result.add(vo);
		}
		return sort(result);
	}

	@Override
	public ArrayList<AnalysisVO<String>> shiJingLuBar() {
		ArrayList<AnalysisVO<String>> result = new ArrayList<>();
		for (int i = 0; i < todayStockList.size(); i++) {
			AnalysisVO<String> vo = new AnalysisVO<String>(todayStockList.get(i).getName(),
					todayStockList.get(i).getPb().doubleValue(), todayStockList.get(i).getDate(),
					todayStockList.get(i).getName());
			result.add(vo);
		}
		return sort(result);
	}

	public static void main(String[] args) {
		AnalysisLogic a = new AnalysisLogic();
		a.changeRateBar();
	}

	@Override
	public ArrayList<AnalysisVO<String>> changeRateBar() {
		ArrayList<NStockVO> stocks = new ArrayList<>();
		ArrayList<AnalysisVO<String>> result = new ArrayList<>();
		try {
			stocks = marketService.getResultListAll(new Date());
		} catch (StatusNotOKException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < stocks.size(); i++) {
			AnalysisVO<String> vo = new AnalysisVO<String>(stocks.get(i).getName(),
					stocks.get(i).getChangeRate().doubleValue(), stocks.get(i).getDate(), stocks.get(i).getName());
			result.add(vo);
			// System.out.println(result.get(i).getStatistic());
		}

		return sort(result);
	}

	/**
	 * 行业内成交量状图和饼图
	 * 
	 * @param marketname
	 *            行业名
	 * @return
	 */
	@Override
	public ArrayList<AnalysisVO<String>> dealpriceBarandPie() {

		ArrayList<AnalysisVO<String>> result = new ArrayList<>();

		for (int i = 0; i < todayStockList.size(); i++) {
			AnalysisVO<String> vo = new AnalysisVO<String>(todayStockList.get(i).getName(),
					todayStockList.get(i).getAdj_price().doubleValue()
							* todayStockList.get(i).getVolume().doubleValue(),
					todayStockList.get(i).getDate(), todayStockList.get(i).getName());
			result.add(vo);
		}
		return sort(result);
	}

	private long betweenDays(Date start, Date end) {
		long betweenDay = (long) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24) + 0.5);
		return betweenDay + 1;
	}

	private DateAndAverage average(ArrayList<NStockPO> po, Date date) {
		ArrayList<NStockVO> voList = new ArrayList<NStockVO>();
		BigDecimal totalDeal = new BigDecimal(0);
		BigDecimal totalWeight = new BigDecimal(0);
		BigDecimal average = new BigDecimal(0);
		for (int i = 0; i < po.size(); i++) {
			voList.add(poTOvo(po.get(i)));
		}

		for (int j = 0; j < voList.size(); j++) {
			totalDeal = totalDeal.add(voList.get(j).getDealprice());
			BigDecimal weight = voList.get(j).getDealprice().multiply(voList.get(j).getClose());
			totalWeight = totalWeight.add(weight);
		}

		if (totalDeal.compareTo(BigDecimal.ZERO) != 0)
			average = totalWeight.divide(totalDeal, 5, BigDecimal.ROUND_HALF_UP);
		else
			average = totalDeal;
		return new DateAndAverage(date, average);
	}

	private NStockVO poTOvo(NStockPO po) {
		return new NStockVO(po.getName(), po.getDate(), po.getOpen(), po.getHigh(), po.getLow(), po.getClose(),
				po.getAdj_price(), po.getVolume(), po.getTurnover(), po.getPe_ttm(), po.getPb());
	}

	private void read() {
		Date datePointer = new Date();
		while (todayStockList.isEmpty()) {
			// if(NetStatus.isConnected()){
			try {
				todayStockList = stockdataService.getAllByTime(DateTool.getStringByDate(datePointer));
			} catch (StatusNotOKException e) {
				e.printStackTrace();
			}

			// }else {
			// todayStockList = SaveDataToTXT.load16Stocks("today");
			// }
			datePointer = DateTool.beforeDate(datePointer, -1);
		}
		// try {
		// SaveDataToTXT.saveToday(todayStockList,FilePath.getThepath());
		// } catch (StatusNotOKException e) {
		// e.printStackTrace();
		// };
	}

	private static ArrayList<AnalysisVO<String>> sort(ArrayList<AnalysisVO<String>> sortList) {
		int j;
		for (int p = 1; p < sortList.size(); p++) {
			AnalysisVO<String> temp = sortList.get(p);
			for (j = p; j > 0 && temp.getStatistic() < sortList.get(j - 1).getStatistic(); j--) {
				sortList.set(j, sortList.get(j - 1));
			}

			sortList.set(j, temp);
		}
		return sortList;
	}

}
