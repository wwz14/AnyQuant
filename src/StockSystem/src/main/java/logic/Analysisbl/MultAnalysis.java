package logic.Analysisbl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exception.StatusNotOKException;
import logic.Analysisbl.regression.Polyfit;
import logic.stockShowInfobl.MacdcalculateLogic;
import logic.stockShowInfobl.StockKLineLogic;
import logicservice.showInfoblservice.MacdcalculateLogicservice;
import utils.DateTool;
import utils.NamesFactory;
import vo.BollVO;
import vo.KDJVO;
import vo.MacdVO;
import vo.RSIVO;

public class MultAnalysis {
	StockKLineLogic service = new StockKLineLogic();
	MacdcalculateLogicservice macdService = new MacdcalculateLogic();
	public String analysisResult;
	private double bollwidth;

	public static void main(String[] args) {

		List<String> nameList = NamesFactory.getAllNames();
		for (int i = 0; i < nameList.size(); i++) {
			// MultAnalysis m = new MultAnalysis(nameList.get(i));
			// System.out.println(nameList.get(i)+m.analysisResult);
		}
		// double t = 0;
		// try {
		// t = m.analyseMACD("sz002142")
		// +m.analysisBoll("sz002142")+m.kdjText("sz002142")+m.getRSIText("sz002142");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println("平级： "+t);
	}

	public MultAnalysis(String stock_name) {
		double total = 0;
		try {
			total = analyseMACD(stock_name) + analysisBoll(stock_name) + getRSIText(stock_name) + kdjText(stock_name);
			total = total / 4;
			StringBuilder result = new StringBuilder();
			if (total >=0&& total <= 0.25)
				result.append("该股票走势平稳，建议观望。");
			else if (total > 0.25 && total <= 1.5)
				result.append("该股票行情将略有上涨，建议买入股票。");
			else if (total > 1.5)
				result.append("该股票很有可能进入高价区，建议买入！");
			else if (total < 0 && total >= -2)
				result.append("该股票行情将略有下降，建议卖出股票。");
			else if (total < -2)
				result.append("该股票很有可能进入低价区，建议卖出，趁高结利！");

			if (bollwidth > 0.5)
				result.append("另外，依据布林线开口情况,次股价将产生大的波动，投资风险较大");

			analysisResult = result.toString();
			// return analysisResult;

		} catch (Exception e) {
			e.printStackTrace();
		}
		// return "";
	}

	public double analyseMACD(String name) throws Exception {
		ArrayList<MacdVO> macdList = new ArrayList<>();
		// 计算最近150天的macd值
		macdList = macdService.calculateMacd(name, DateTool.beforeDate(new Date(), -15), new Date());
		double[] x = new double[macdList.size()];
		double[] y = new double[macdList.size()];
		for (int i = 0; i < macdList.size(); i++) {
			x[i] = i + 1;
			y[i] = macdList.get(i).getMacd();
		}
		// 计算macd的变化增、减性
		Polyfit polyfit = new Polyfit(x, y, Math.min(4, macdList.size() - 1));
		double gradient = polyfit.gradient();
		// 计算macd的平均值
		double macd_avg = 0;
		double dif_avg = 0;
		for (MacdVO macdVo : macdList) {
			macd_avg += macdVo.getMacd();
			dif_avg += macdVo.getDif();
		}
		macd_avg = macd_avg / macdList.size();
		dif_avg = dif_avg / macdList.size();
		// 特殊情况
		if (macd_avg * dif_avg <= 0) {
			System.out.println(0);
			return 0;
		}
		// 该股票行情行情处于多头行情中，可以买入开仓或多头持仓
		if (macd_avg > 0 && gradient > 0) {
			System.out.println(2);
			return 2;
		}
		// 该股票行情处于空头行情中，可以卖出开仓或观望
		else if (macd_avg < 0 && gradient < 0) {
			System.out.println(-2);
			return -2;
		}
		// 该股票行情处于下跌阶段，可以卖出开仓和观望
		else if (macd_avg > 0 && gradient < 0) {
			System.out.println(-3);
			return -3;
		}
		// 该股票行情即将上涨，可以买入开仓或多头持仓
		else if (macd_avg < 0 && gradient > 0) {
			System.out.println(3);
			return 3;
		}
		// 该股票行情走势稳定
		else if (gradient == 0)
			return 0;
		return 0;
	}

	public double analysisBoll(String name) throws Exception {
		ArrayList<BollVO> boll = new ArrayList<BollVO>();
		try {
			boll = service.getBoll(name, DateTool.beforeDate(new Date(), -6), new Date());
		} catch (StatusNotOKException e) {
			System.out.println("statusnotok");
			e.printStackTrace();
		}
		// 极限宽指标
		double width = (boll.get(boll.size() - 1).getUp() - boll.get(0).getDn()) / boll.get(0).getMb();
		bollwidth = width;
		double conclusion = 0;
		if (width < 0.1) {
			// "布林线极限宽指标小于10%,有突破可能性"
			conclusion += 1;
		}
		if (width > 0.5) {
			// "布林线开口较大,股价将产生大的波动，需慎重考虑买卖该股票"
			conclusion += -1;
		}
		double[] x = new double[boll.size()];
		double[] y = new double[boll.size()];
		for (int i = 0; i < boll.size(); i++) {
			x[i] = i + 1;
			y[i] = boll.get(i).getUp();
		}
		// 多项式拟合，斜率判断增减性
		Polyfit polyfit = new Polyfit(x, y, Math.min(4, boll.size() - 1));
		double Upgradient = polyfit.gradient();
		for (int i = 0; i < boll.size(); i++) {
			x[i] = i + 1;
			y[i] = boll.get(i).getDn();
		}
		// 多项式拟合，斜率判断增减性
		Polyfit polyfitd = new Polyfit(x, y, Math.min(4, boll.size() - 1));
		double Dngradient = polyfitd.gradient();
		for (int i = 0; i < boll.size(); i++) {
			x[i] = i + 1;
			y[i] = boll.get(i).getMb();
		}
		// 多项式拟合，斜率判断增减性
		Polyfit polyfit2 = new Polyfit(x, y, Math.min(4, boll.size() - 1));
		double Mbgradient = polyfit2.gradient();
		// 布林线的上、中、下轨线同时向上运行时，表明股价强势特征非常明显，股价短期内将继续上涨，投资者应坚决持股待涨或逢低买人。
		if (Dngradient > 0 && Mbgradient > 0 && Upgradient > 0) {
			conclusion += 2;
		}
		// "布林线的上、中、下轨线同时向下运行时，表明股价的弱势特征非常明显，股价短期内将继续下跌，投资者应坚决持币观望或逢高卖出。"
		if (Dngradient < 0 && Mbgradient < 0 && Upgradient < 0) {
			conclusion += -2;
		}
		if (Dngradient > 0 && Mbgradient > 0 && Upgradient < 0) {
			conclusion += 0;
		}
		System.out.println(conclusion);
		return conclusion;
	}

	public double kdjText(String name) {
		ArrayList<KDJVO> kdj = new ArrayList<KDJVO>();
		try {
			kdj = service.getKDJ(name, DateTool.beforeDate(new Date(), -6), new Date());
			System.out.println(kdj.size());
			System.out.println(kdj.get(0).getDate());
		} catch (StatusNotOKException e) {
			System.out.println("statusnotok");
			e.printStackTrace();
		}
		int size = kdj.size() - 1;
		double k = kdj.get(size).getK();
		double d = kdj.get(size).getD();
		double j = kdj.get(size).getJ();
		double conclusion = 0;
		if (k > 80 && d > 80) {
			// conclusion+="k、d均大于80，代表该股票已被超买,可考虑卖出该股票";
			System.out.println(-2);
			return -2;
		}
		if (k > 50 && d > 50 && j > 50) {
			// conclusion+="k、d、j均大于50，为多头市场，后市看涨,可考虑买进该股票";
			System.out.println(3);
			return 3;
		}
		if (k < 20 && d < 20) {
			// conclusion+="k、d均小于20，代表该股票已被超卖,可考虑买入该股票";
			System.out.println(2);
			return 2;
		}
		if (k < 50 && d < 50 && j < 50) {
			// conclusion+="k、d、j均小于50，为空头市场，后市看空,可考虑卖出该股票";
			System.out.println("kdj" + -3);
			return -3;
		}
		// conclusion="行情正在整理，需要继续观察";
		return conclusion;
	}

	public double getRSIText(String name) throws Exception {
		// 默认一个月的
		ArrayList<RSIVO> rsi = new ArrayList<RSIVO>();
		try {
			rsi = service.getRSI(name, DateTool.beforeDate(new Date(), -6), new Date());
		} catch (StatusNotOKException e) {
			e.printStackTrace();
		}

		// 计算近来5天RSI指数的均值
		int days = rsi.size();
		double ave6 = 0;
		double ave12 = 0;
		double ave24 = 0;

		for (int i = 0; i < days; i++) {
			ave6 += rsi.get(i).getRsi6();
			ave12 += rsi.get(i).getRsi12();
			System.out.println(rsi.get(i).getRsi12());
			ave24 += rsi.get(i).getRsi24();
		}
		ave12 = ave12 / days;
		ave6 = ave6 / days;
		ave24 = ave24 / days;
		double conclusion = 0;
		if (ave12 >= 70) {
			// "高居70以上,代表该证券已被超买,投资者应考虑出售该证券."
			conclusion += -1;
			// 当快速RSI由上而下跌破慢速RSI时，为卖出时机。
			if (ave6 < ave24) {
				// "并且短期RSI在长期之下,为卖出时机."
				conclusion += -2;
			}
		} else if (ave12 <= 30) {
			//
			conclusion += 1;
			// 当快速RSI由下往上突破慢速RSI时，为买进时机;
			if (ave6 > ave24) {
				// "并且短期RSI在长期之上,着实为极佳买进时机."
				conclusion += 2;
			}

		} else {
			conclusion += 0;
		}
		System.out.println("rsi:" + conclusion);
		return conclusion;
	}

}
