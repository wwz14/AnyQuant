package logic.Analysisbl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import DistLib.t;
import enums.Stockfield;
import exception.StatusNotOKException;
import logic.benchmarkShowInfobl.BenchmarkLogic;
import logic.stockShowInfobl.StockListLogic;
import logic.utils.CorrelationCoefficient;
import logicservice.Analysisblservice.CompareLogicservice;
import logicservice.Analysisblservice.MarketLogicservice;
import logicservice.showInfoblservice.BenchmarkLogicservice;
import logicservice.showInfoblservice.StockListLogicservice;
import utils.DateTool;
import vo.AnalysisVO;
import vo.MarketVO;
import vo.NBenchMarkVO;
import vo.NStockVO;

public class CompareLogic implements CompareLogicservice {
	StockListLogicservice service = new StockListLogic();
	BenchmarkLogicservice bservice = new BenchmarkLogic();
	MarketLogicservice market=new MarketLogic();
	@Override
	public ArrayList<AnalysisVO<Date>> changeRateLine(String name,Stockfield field) {

		ArrayList<NStockVO> vo = new ArrayList<>();

		ArrayList<AnalysisVO<Date>> result = new ArrayList<>();
		if(!name.equals("hs300")){//股票
			vo = service.click(name);

			result=getField(vo,field);
		}else {//大盘
			ArrayList<NBenchMarkVO> benchMarkVo = new ArrayList<> ();
			try {
				benchMarkVo = bservice.getByTime("hs300", DateTool.beforeDate(new Date(), -31), new Date());

			} catch (StatusNotOKException e) {
				e.printStackTrace();
			}
			for(int i = 0;i<benchMarkVo.size();i++) {
				AnalysisVO<Date> avo = new AnalysisVO<Date>(name,benchMarkVo.get(i).getChangeRate().doubleValue(),benchMarkVo.get(i).getDate(),benchMarkVo.get(i).getDate());
				result.add(avo);
			}
		}


		return result;
	}
	//涨跌幅算的
	@Override
	public String getcorrelationCoeffcient(String name1, String name2) throws Exception {
		ArrayList<AnalysisVO<Date>> arg1=this.changeRateLine(name1,Stockfield.changeString);
		ArrayList<AnalysisVO<Date>> arg2=this.changeRateLine(name2,Stockfield.changeString);
		if(arg1.size()<1||arg2.size()<1){
			System.out.println("相关系数，数据太少,无法计算");
			throw new Exception("数据太少，无法进行分布计算");
		}
		double[] a1=new double[arg1.size()];
		for(int i=0;i<arg1.size();i++){
			a1[i]=arg1.get(i).getStatistic();
		}
		double[] a2=new double[arg2.size()];
		for(int i=0;i<arg2.size();i++){
			a1[i]=arg2.get(i).getStatistic();
		}
		double p=CorrelationCoefficient.correlationCoefficient(a1, a2);
		String conclusion="";
		if(p<0.4){
			conclusion+="这两支股票相关系数较小,组合购买风险较小";
		}else{
			conclusion+="这两支股票相关系数较大,组合购买风险较大，不推荐组合购买";
		}
		return conclusion;
	}
	private ArrayList<Double>  getField2(ArrayList<NStockVO> vo,Stockfield field){
		ArrayList<Double> result = new ArrayList<Double>();
		if(vo.size()<1){
			System.out.println("数据太少，无法计算");
			return null;
		}
		switch (field){
		case open:
			for(int i = 0;i<vo.size();i++) {
				result.add(vo.get(i).getOpen().doubleValue());
			}
			break;
		case close:
			for(int i = 0;i<vo.size();i++) {
				result.add(vo.get(i).getClose().doubleValue());
			}
			break;
		case high:
			for(int i = 0;i<vo.size();i++) {
				result.add(vo.get(i).getHigh().doubleValue());
			}
			break;
		case low:
			for(int i = 0;i<vo.size();i++) {
				result.add(vo.get(i).getLow().doubleValue());
			}
			break;
		case  changeString:
			for(int i = 0;i<vo.size();i++) {
				result.add(vo.get(i).getChangeRate().doubleValue());
			}
			break;
		case  adj_price:
			for(int i = 0;i<vo.size();i++) {
				result.add(vo.get(i).getAdj_price().doubleValue());
			}
			break;
		case  volume:
			for(int i = 0;i<vo.size();i++) {
				result.add(vo.get(i).getVolume().doubleValue());
			}
			break;
		case  turnover:
			for(int i = 0;i<vo.size();i++) {
				result.add(vo.get(i).getTurnover().doubleValue());
			}
			break;
		case  pe_ttm:
			for(int i = 0;i<vo.size();i++) {
				result.add(vo.get(i).getPe_ttm().doubleValue());
			}
			break;
		default:
			for(int i = 0;i<vo.size();i++) {
				result.add(vo.get(i).getPb().doubleValue());
			}

		}
		return result;

	}
	@Override
	public String confiInterval(String name1, String name2,Stockfield field) throws Exception{
		//得到数据,SIZE,分析的是涨幅
		ArrayList<NStockVO> vo1 = new ArrayList<>();
		vo1=service.click(name1);
		ArrayList<Double> n1=new ArrayList<Double> ();
		n1=getField2(vo1,field);
		ArrayList<NStockVO> vo2 = new ArrayList<>();
		vo2=service.click(name2);
		ArrayList<Double> n2=new ArrayList<Double> ();
		n2=getField2(vo2,field);
		//
		if(vo1.size()==0||vo2.size()==0||vo1.size()!=vo2.size()){

			System.out.println("数据太少，无法进行分布计算");
			throw new Exception("数据太少，无法进行分布计算");
		}
		int size=vo1.size();
		//
		ArrayList<Double> n3=new ArrayList<Double> ();
		for(int i=0;i<size;i++){
			n3.add(n1.get(i).doubleValue()-n2.get(i).doubleValue());
		}
		//
		//样本容量较小
		double a=avg(n3);
		//95%置信区间
		double b = Math.sqrt(var(n3) / (size - 1)) * Math.abs(t.quantile(0.05, size - 1));
		//
		double left = a - b;
		double right = a + b;
		System.out.println("left"+left);
		System.out.println("right"+right);
		BigDecimal   f1   =   new   BigDecimal(left);
		double   bleft   =   f1.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal   f2   =   new   BigDecimal(right);
		double   bright   =   f2.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue();
		String conclusion = "";
		System.out.println(field);
		if (left > 0 && right > 0) {
			conclusion = name1+"此项数据与"+name2+"之差的95%置信区间为("+bleft+","+bright
					+"),故可认为"+name1+field+"显著高于"+name2;
		}else if (left < 0 && right < 0) {
			conclusion = name1+"此项数据与"+name2+"之差的95%置信区间为("+bleft+","+bright
					+"),故可认为"+name1+field+"显著低于"+name2;
		}else {
			conclusion = name1+"此项数据与"+name2+"之差的95%置信区间为("+bleft+","+bright
					+"),故可认为"+name1+field+"与"+name2+"无显著差异";
		}
		return conclusion;
	}



	private ArrayList<AnalysisVO<Date>>  getField(ArrayList<NStockVO> vo,Stockfield field){
		ArrayList<AnalysisVO<Date>> result = new ArrayList<>();
		if(vo.size()<1){
			System.out.println("数据太少，无法计算");
			return null;
		}
		String name=vo.get(0).getName();
		switch (field){
		case open:
			for(int i = 0;i<vo.size();i++) {
				AnalysisVO<Date> avo = new AnalysisVO<Date>(name,vo.get(i).getOpen().doubleValue(),vo.get(i).getDate(),vo.get(i).getDate());
				result.add(avo);
			}
			break;
		case close:
			for(int i = 0;i<vo.size();i++) {
				AnalysisVO<Date> avo = new AnalysisVO<Date>(name,vo.get(i).getClose().doubleValue(),vo.get(i).getDate(),vo.get(i).getDate());
				result.add(avo);
			}
			break;
		case high:
			for(int i = 0;i<vo.size();i++) {
				AnalysisVO<Date> avo = new AnalysisVO<Date>(name,vo.get(i).getHigh().doubleValue(),vo.get(i).getDate(),vo.get(i).getDate());
				result.add(avo);
			}
			break;
		case low:
			for(int i = 0;i<vo.size();i++) {
				AnalysisVO<Date> avo = new AnalysisVO<Date>(name,vo.get(i).getLow().doubleValue(),vo.get(i).getDate(),vo.get(i).getDate());
				result.add(avo);
			}
			break;
		case  changeString:
			for(int i = 0;i<vo.size();i++) {
				AnalysisVO<Date> avo = new AnalysisVO<Date>(name,vo.get(i).getChangeRate().doubleValue(),vo.get(i).getDate(),vo.get(i).getDate());
				result.add(avo);
			}
			break;
		case  adj_price:
			for(int i = 0;i<vo.size();i++) {
				AnalysisVO<Date> avo = new AnalysisVO<Date>(name,vo.get(i).getAdj_price().doubleValue(),vo.get(i).getDate(),vo.get(i).getDate());
				result.add(avo);
			}
			break;
		case  volume:
			for(int i = 0;i<vo.size();i++) {
				AnalysisVO<Date> avo = new AnalysisVO<Date>(name,vo.get(i).getVolume().doubleValue(),vo.get(i).getDate(),vo.get(i).getDate());
				result.add(avo);
			}
			break;
		case  turnover:
			for(int i = 0;i<vo.size();i++) {
				AnalysisVO<Date> avo = new AnalysisVO<Date>(name,vo.get(i).getTurnover().doubleValue(),vo.get(i).getDate(),vo.get(i).getDate());
				result.add(avo);
			}
			break;
		case  pe_ttm:
			for(int i = 0;i<vo.size();i++) {
				AnalysisVO<Date> avo = new AnalysisVO<Date>(name,vo.get(i).getPe_ttm().doubleValue(),vo.get(i).getDate(),vo.get(i).getDate());
				result.add(avo);
			}
			break;
		default:
			for(int i = 0;i<vo.size();i++) {
				AnalysisVO<Date> avo = new AnalysisVO<Date>(name,vo.get(i).getPb().doubleValue(),vo.get(i).getDate(),vo.get(i).getDate());
				result.add(avo);
			}

		}
		return result;

	}

	private double avg(ArrayList<Double> data) {
		return sum(data, data.size())/ data.size();
	}

	private double sum(ArrayList<Double> data, int index) {
		double sum = 0;
		for (int i = 0; i< index; i++) {
			sum += data.get(i);
		}
		return sum;
	}

	private double var(ArrayList<Double> data) {
		if (data.size() < 2) return 0;
		double avg = avg(data);
		double tmp = 0;
		for (double d : data) {
			tmp += (d - avg) * (d - avg);
		}
		return tmp / (data.size() - 1);
	}
	@Override
	public String confiIntervalBench(String name1) throws Exception {
		//得到数据,SIZE,分析的是涨幅
		ArrayList<NStockVO> vo1 = new ArrayList<>();
		vo1=service.click(name1);
		ArrayList<Double> n1=new ArrayList<Double> ();
		n1=getField2(vo1,Stockfield.changeString);
		ArrayList<NBenchMarkVO> vo2 = new ArrayList<>();
		vo2=bservice.getByName("hs300");
		ArrayList<Double> n2=new ArrayList<Double> ();
		for(int i=0;i<vo2.size();i++){
			n2.add(vo2.get(i).getChangeRate().doubleValue());
		}
		//
		if(vo1.size()==0||vo2.size()==0||vo1.size()!=vo2.size()){

			System.out.println("数据太少，无法进行分布计算");
			throw new Exception("数据太少，无法进行分布计算");
		}
		int size=vo1.size();
		//
		ArrayList<Double> n3=new ArrayList<Double> ();
		for(int i=0;i<size;i++){
			n3.add(n1.get(i).doubleValue()-n2.get(i).doubleValue());
		}
		//
		//样本容量较小
		double a=avg(n3);
		//95%置信区间
		double b = Math.sqrt(var(n3) / (size - 1)) * Math.abs(t.quantile(0.05, size - 1));
		//
		double left = a - b;
		double right = a + b;
		System.out.println("left"+left);
		System.out.println("right"+right);
		BigDecimal   f1   =   new   BigDecimal(left);
		double   bleft   =   f1.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal   f2   =   new   BigDecimal(right);
		double   bright   =   f2.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue();
		String conclusion = "";
		if (left > 0 && right > 0) {
			conclusion = name1+"涨跌幅与大盘涨跌幅"+"之差的95%置信区间为("+bleft+","+bright
					+"),故可认为"+name1+"行情"+"好于大盘行情";
		}else if (left < 0 && right < 0) {
			conclusion = name1+"涨跌幅与大盘涨跌幅"+"之差的95%置信区间为("+bleft+","+bright
					+"),故可认为"+name1+"行情"+"差于大盘行情";
		}else {
			conclusion = name1+"涨跌幅与大盘涨跌幅"+"之差的95%置信区间为("+bleft+","+bright
					+"),故可认为"+name1+"行情"+"与大盘行情无显著差异";
		}
		return conclusion;
	}
	@Override
	public String confiIntervalHang(Date start,Date end) throws Exception {
		//得到数据,SIZE,分析的是涨幅
				ArrayList<MarketVO> vo1 = new ArrayList<>();
				vo1=market.getMarketVOs(start,end);
				ArrayList<Double> n1=new ArrayList<Double> ();
				for(int i=0;i<vo1.size();i++){
					n1.add(vo1.get(i).getMarketChangeRate().doubleValue());
				}
				ArrayList<Double> n2=new ArrayList<Double> ();
				for(int i=0;i<vo1.size();i++){
					n2.add(vo1.get(i).getBenchmarkChangeRate().doubleValue());
				}
				//
				if(vo1.size()==0){

					System.out.println("数据太少，无法进行分布计算");
					throw new Exception("数据太少，无法进行分布计算");
				}
				int size=vo1.size();
				//
				ArrayList<Double> n3=new ArrayList<Double> ();
				for(int i=0;i<size;i++){
					n3.add(n1.get(i).doubleValue()-n2.get(i).doubleValue());
				}
				//
				//样本容量较小
				double a=avg(n3);
				//95%置信区间
				double b = Math.sqrt(var(n3) / (size - 1)) * Math.abs(t.quantile(0.05, size - 1));
				//
				double left = a - b;
				double right = a + b;
				System.out.println("left"+left);
				System.out.println("right"+right);
				BigDecimal   f1   =   new   BigDecimal(left);
				double   bleft   =   f1.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue();
				BigDecimal   f2   =   new   BigDecimal(right);
				double   bright   =   f2.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue();
				String conclusion = "";
				if (left > 0 && right > 0) {
					conclusion ="整个银行行业的涨跌幅与大盘涨跌幅"+"之差的95%置信区间为("+bleft+","+bright
							+"),故可认为"+"银行行情"+"好于大盘行情";
				}else if (left < 0 && right < 0) {
					conclusion ="整个银行行业的涨跌幅与大盘涨跌幅"+"之差的95%置信区间为("+bleft+","+bright
							+"),故可认为"+"银行行情"+"差于大盘行情";
				}else {
					conclusion = "整个银行行业的涨跌幅与大盘涨跌幅"+"之差的95%置信区间为("+bleft+","+bright
							+"),故可认为"+"银行行情"+"与大盘行情无显著差异";
				}
				return conclusion;
	}

}