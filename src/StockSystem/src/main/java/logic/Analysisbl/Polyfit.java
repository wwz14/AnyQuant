package logic.Analysisbl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import data.sqlImpl.StockDataSQL;
import dataservice.StockListDataservice;
import enums.Stockfield;
import logicservice.Analysisblservice.PolyFitLogicservice;
import po.NStockPO;
import utils.DateTool;
import utils.ResultMsg;
import vo.PolyFitVO;

public class Polyfit implements PolyFitLogicservice {
	private StockListDataservice getData = new StockDataSQL();

	@Override
	public  ResultMsg polyFit(String name) throws Exception {
		ArrayList<NStockPO>  stock = new ArrayList<>();
		stock = getData.getByName(name, DateTool.getStringByDate(DateTool.beforeDate(new Date(), -90)), 
				DateTool.getStringByDate(new Date()));
		//TODO 按时间升序
		ArrayList<Double> yList = new ArrayList<>();
		ArrayList<Date> dateList = new ArrayList<>();
		for(NStockPO stockPO:stock) {
			yList.add(stockPO.getClose().doubleValue());//真实的y值；
			dateList.add(stockPO.getDate());
		}
		RegressionHandle  regression = new RegressionHandle(yList);
		double[] y = regression.getCurveY();
		double[] x = regression.getCurveX();
		ArrayList<Double> curvey = new ArrayList<>();//曲线的y值
		for(int i = 0;i<y.length;i++) {
			curvey.add(y[i]);
		}
		ArrayList<Double> curvex = new ArrayList<>();
		for(int m = 0;m<x.length;m++) {
			curvex.add(x[m]);
		}
		
		ResultMsg result = new ResultMsg();
		DecimalFormat format = new DecimalFormat();
        String valueString = format.format(regression.getNextValueByRegression());
        String message = "通过多项式回归分析,预测该股票下一个工作日收盘价为 " + valueString;
		result.setMessage(message) ;
		PolyFitVO vo = new PolyFitVO(dateList,yList,curvex,curvey);
		result.setResult(vo);
		result.setIsok(true);    
		return result;
	}
	
	
	public static void main(String[] args) {
		
		Polyfit p = new Polyfit();
		
		try {
			ResultMsg result = p.polyFit("sh600000",Stockfield.close);
			System.out.println(result.getMessage());
			PolyFitVO vo = (PolyFitVO) result.getResult();
			ArrayList<Date> a = vo.date;
			ArrayList<Double> t = vo.act;
			ArrayList<Double> pl = vo.pre;
			
			for(int i = 0;i<a.size();i++) {
				System.out.println(DateTool.getStringByDate(a.get(i)));
				System.out.println(t.get(i));
				System.out.println(pl.get(i));
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

















	@Override
	public ResultMsg polyFit(String name, Stockfield field) throws Exception {
		ArrayList<NStockPO>  stock = new ArrayList<>();
		stock = getData.getByName(name, DateTool.getStringByDate(DateTool.beforeDate(new Date(), -90)), 
				DateTool.getStringByDate(new Date()));
		//TODO 按时间升序
		ArrayList<Double> yList = new ArrayList<>();
		ArrayList<Date> dateList = new ArrayList<>();
		ArrayList<Double> xzhou = new ArrayList<>();
		
		for(NStockPO stockPO:stock) {
			yList.add(fieldvalue(stockPO,field));//真实的y值；
			dateList.add(stockPO.getDate());
		}
//		double[] x = new double[stock.size()];
//		for(int n = 0;n<dateList.size();n++) {
//			x[n] = n+1;
//		}
		System.out.println("Date: "+dateList.size());
		System.out.println("act: "+yList.size());
		RegressionHandle  regression = new RegressionHandle(yList);
		double[] y = regression.getCurveY();
		//double[] y = regression.getnewy();
		double[] x = regression.getCurveX();
		
		ArrayList<Double> curve = new ArrayList<>();//曲线的y值
		ArrayList<Double> curvex = new ArrayList<>();
		for(int i = 0;i<y.length;i++) {
			curve.add(y[i]);
			curvex.add(x[i]);
//			System.out.println("y:"+y[i]);
//			System.out.println("x: "+x[i]);
		}
		
//		for(int m = 0;m<x.length;m++) {
//			curvex.add(x[m]);
//	   	//System.out.println("x: "+x[m]);
//		}
		//System.out.println();
		System.out.println("y:"+ curve.size());
		ResultMsg result = new ResultMsg();
		DecimalFormat format = new DecimalFormat();
        String valueString = format.format(regression.getNextValueByRegression());
        String hanshu = new String();
//        double[] xishu = regression.getCoes();
//        int i = xishu.length;
//        for(int j = 0;j<i;j++) {
//        	if(xishu[j] != 0){
//        		String zhishu = Integer.toString(i-1-j);
//        		hanshu += xishu[j]+"x^"+zhishu; 
//        	}
//        }
        
        String message = "通过多项式回归分析,预测该股票下一个工作日收盘价为 " + valueString;
        result.setMessage(message);
		PolyFitVO vo = new PolyFitVO(dateList,yList,curvex,curve);
		result.setResult(vo);
		result.setIsok(true);    
		return result;
	
	}
	
	private double fieldvalue(NStockPO po,Stockfield s) throws Exception {
		if(s.equals(Stockfield.open))
			return po.getOpen().doubleValue();
		else if(s.equals(Stockfield.close))
			return po.getClose().doubleValue();
		else if(s.equals(Stockfield.high))
			return po.getHigh().doubleValue();
		else if(s.equals(Stockfield.low))
			return po.getLow().doubleValue();
		else if(s.equals(Stockfield.turnover))
			return po.getTurnover().doubleValue();
		else if(s.equals(Stockfield.adj_price))
			return po.getAdj_price().doubleValue();
		else if(s.equals(Stockfield.pb))
			return po.getPb().doubleValue();
		else if(s.equals(Stockfield.pe_ttm))
			return po.getPe_ttm().doubleValue();
		else 
			throw new Exception(" error！本项数据不适宜提供拟合预测服务");
	}
	
	private String fieldName(Stockfield s) throws Exception {
		if(s.equals(Stockfield.open))
			return "开盘价";
		else if(s.equals(Stockfield.close))
			return "收盘价";
		else if(s.equals(Stockfield.high))
			return "最高价";
		else if(s.equals(Stockfield.low))
			return "最低价";
		else if(s.equals(Stockfield.turnover))
			return "换手率";
		else if(s.equals(Stockfield.adj_price))
			return "后复权价";
		else if(s.equals(Stockfield.pb))
			return "市净率";
		else if(s.equals(Stockfield.pe_ttm))
			return "市盈率";
		else 
			return "";
	}
	

}
