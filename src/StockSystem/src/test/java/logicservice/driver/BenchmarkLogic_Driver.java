package logicservice.driver;


import java.util.ArrayList;
import java.util.Date;

import enums.KLineType;
import exception.StatusNotOKException;
import logic.benchmarkShowInfobl.BenchmarkLogic;
import logicservice.showInfoblservice.BenchmarkLogicservice;
import utils.DateTool;
import vo.KLineVO;
import vo.MAVO;

public class BenchmarkLogic_Driver {
	private static BenchmarkLogicservice benchmarkLogicService = new BenchmarkLogic();
	
	public static void main(String[] args) {
      testDay();
      testWeek();
      testMonth();	
      testGetMA() ;
	}
	
	public static void testDay() {
		ArrayList<KLineVO> klineVO = new ArrayList<KLineVO> ();
		Date start = DateTool.getDateByString("2016-03-01");
		Date end = DateTool.getDateByString("2016-03-14");
		
		try {
			klineVO = benchmarkLogicService.getKLineVOs(KLineType.day, start, end);
		} catch (StatusNotOKException e) {
			System.out.println("异常了");
			e.printStackTrace();
		}
		
		for(int i = 0;i<klineVO.size();i++) {
			//if(null == klineVO.get(i).getClose()){
			System.out.println(DateTool.getStringByDate(klineVO.get(i).getDate())+" "+
		klineVO.get(i).getHigh()+" "+klineVO.get(i).getLow()+" "+klineVO.get(i).getClose()+
		" "+klineVO.get(i).getOpen()+" "+klineVO.get(i).getVolumn()+" "+klineVO.get(i).getDealPrice());
	//	}else{
			//System.out.println(DateTool.getStringByDate(klineVO.get(i).getDate())+"非交易日");
	//	}
		}
		System.out.println("Day end");
	}
	
	public static void testWeek() {
		ArrayList<KLineVO> klineVO = new ArrayList<KLineVO> ();
		Date start = DateTool.getDateByString("2016-03-01");
		Date end = DateTool.getDateByString("2016-03-14");
		
		try {
			klineVO = benchmarkLogicService.getKLineVOs(KLineType.week, start, end);
		} catch (StatusNotOKException e) {
			System.out.println("异常了");
			e.printStackTrace();
		}
		
		for(int i = 0;i<klineVO.size();i++) {
			//if(null == klineVO.get(i).getClose()){
			System.out.println(DateTool.getStringByDate(klineVO.get(i).getDate())+" "+
		klineVO.get(i).getHigh()+" "+klineVO.get(i).getLow()+" "+klineVO.get(i).getClose()+
		" "+klineVO.get(i).getOpen()+" "+klineVO.get(i).getVolumn()+" "+klineVO.get(i).getDealPrice());
	//	}else{
			//System.out.println(DateTool.getStringByDate(klineVO.get(i).getDate())+"非交易日");
	//	}
		}
		System.out.println("Week end");
	}
	
	public static void testMonth() {
		ArrayList<KLineVO> klineVO = new ArrayList<KLineVO> ();
		Date start = DateTool.getDateByString("2016-03-01");
		Date end = DateTool.getDateByString("2016-03-14");
		
		try {
			klineVO = benchmarkLogicService.getKLineVOs(KLineType.month, start, end);
		} catch (StatusNotOKException e) {
			System.out.println("异常了");
			e.printStackTrace();
		}
		
		for(int i = 0;i<klineVO.size();i++) {
			//if(null == klineVO.get(i).getClose()){
			System.out.println(DateTool.getStringByDate(klineVO.get(i).getDate())+" "+
		klineVO.get(i).getHigh()+" "+klineVO.get(i).getLow()+" "+klineVO.get(i).getClose()+
		" "+klineVO.get(i).getOpen()+" "+klineVO.get(i).getVolumn()+" "+klineVO.get(i).getDealPrice());
	//	}else{
			//System.out.println(DateTool.getStringByDate(klineVO.get(i).getDate())+"非交易日");
	//	}
		}
		System.out.println("Month end");
	}
	
	public static void testGetMA() {
		ArrayList<MAVO> mavoList = new ArrayList<MAVO>();
		Date start = DateTool.getDateByString("2016-03-01");
		Date end = DateTool.getDateByString("2016-03-14");
		
//		public MAVO(BigDecimal mA5, BigDecimal mA20, BigDecimal mA30, BigDecimal mA60, BigDecimal mAVOL5,
//				BigDecimal mAVOL10, Date date) 
		
		try {
			mavoList = benchmarkLogicService.getMAVOs(start, end);
			for(int i = 0;i<mavoList.size();i++) {
				MAVO ma = mavoList.get(i);
				System.out.println("日期："+DateTool.getStringByDate(ma.getDate())+"  "+"ma5:"+ma.getMA5()+" "+"ma20"+ma.getMA20()+
						"ma30"+" "+ma.getMA30()+" "+"ma60:"+ma.getMA60()+" "+"maVOL5:"+ma.getMAVOL5()+
						" "+"maVOL10:"+ma.getMAVOL10());
			}
		} catch (StatusNotOKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
