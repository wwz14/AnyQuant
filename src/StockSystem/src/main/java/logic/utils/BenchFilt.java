package logic.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import enums.Stockfield;
import exception.StatusNotOKException;
import po.NBenchMarkPO;
import utils.Filter;
import utils.FilterItem;
import utils.changeRateFormate;
import vo.NBenchMarkVO;

public class BenchFilt {
	/**
	 * 遍历过滤项过滤
	 * 不带时间的过滤
	 * polistlist 
	 * */
	public static ArrayList<NBenchMarkVO> filter(ArrayList<NBenchMarkVO> polistlist,Filter filter){
		ArrayList<NBenchMarkVO> result=new ArrayList<NBenchMarkVO>();
	
		//筛选
		for(int i=0;i<polistlist.size();i++){
			NBenchMarkVO temp=polistlist.get(i);
			if(isIn(temp, filter)){
				result.add(temp);
			}

		}
		return result;
	}

	// 带时间的筛选
	public static ArrayList<NBenchMarkVO> filterDetail(ArrayList<NBenchMarkVO> polistlist, Filter filter) {

		System.out.println("filter detail");
		ArrayList<NBenchMarkVO> result = filter(polistlist, filter);
		ArrayList<NBenchMarkVO> realres = new ArrayList<NBenchMarkVO>();

		Date startDate = filter.getStartDate();
		Date endDate = filter.getEndDate();

		System.out.println(startDate);
		if(startDate==null&&endDate==null){
			//都为空
			System.out.println("2 null");
			return result;
		}
		if(startDate==null){
			//开始为空，结束不为空
			for (int i = 0; i < result.size(); i++) {
				NBenchMarkVO tttt = result.get(i);
				if ( tttt.getDate().getTime() <= endDate.getTime()) {
					realres.add(tttt);
				}
			}
			System.out.println("s null");
			return realres;
		}
		if(endDate==null){
			//结束为空，开始不为空
			for (int i = 0; i < result.size(); i++) {
				NBenchMarkVO tttt = result.get(i);
				if ( tttt.getDate().getTime() >= startDate.getTime()) {
					realres.add(tttt);
				}
			}
			System.out.println("e null");
			return realres;
		}
		//开始结束都不为空
		for (int i = 0; i < result.size(); i++) {
			NBenchMarkVO tttt = result.get(i);
			if ((tttt.getDate().getTime() >= startDate.getTime()) && (tttt.getDate().getTime() <= endDate.getTime())) {
				realres.add(tttt);
			}
			//	System.out.println("s d");
		}
		return realres;

	}

	private static NBenchMarkVO toVO(NBenchMarkPO po) {
		return new NBenchMarkVO(po.getName(), po.getVolume(), po.getHigh(), po.getAdj_price(), po.getLow(),
				po.getDate(), po.getClose(), po.getOpen());
	}

	// 带时间的筛选
	public static ArrayList<NBenchMarkVO> filterDetail(String name,ArrayList<NBenchMarkVO> polistlist, Filter filter) {
		Date startDate = filter.getStartDate();
		Date endDate = filter.getEndDate();


		ArrayList<NBenchMarkPO> re=new ArrayList<NBenchMarkPO>();
		//为了算涨幅需要往前取一天
		try {
			re=GetPreStock.getPreB(name,startDate,  endDate,1);
		} catch (StatusNotOKException e) {
			e.printStackTrace();
		}
		ArrayList<NBenchMarkVO> RES=new ArrayList<NBenchMarkVO>();
		for(int i=0;i<re.size();i++){
			RES.add(toVO(re.get(i)));
		}
		//算一下涨跌幅
		//第一天算不了
		//目前算收盘价的涨跌幅
		for (int i = 1; i < RES.size(); i++) {
			NBenchMarkVO af=RES.get(i);
			NBenchMarkVO be=RES.get(i-1);
			BigDecimal afclose=af.getClose();
			BigDecimal beclose=be.getClose();
			//除法需要指名scale
			BigDecimal changeRate;
			if(beclose.compareTo(BigDecimal.ZERO)==0){
				//分母为0，即昨收盘为0，则停牌
				changeRate=new BigDecimal(0);
				RES.get(i).setRate("停牌");
			}else{
				changeRate=afclose.subtract(beclose).divide(beclose,5,BigDecimal.ROUND_HALF_UP);
				RES.get(i).setRate(changeRateFormate.formate(changeRate));

			}
			RES.get(i).setChangeRate(changeRate);

		}
		if(RES.size()>=1){
			RES.remove(0);
		}

		System.out.println("filter detail");
		ArrayList<NBenchMarkVO> result = filter(RES, filter);
		ArrayList<NBenchMarkVO> realres = new ArrayList<NBenchMarkVO>();



		System.out.println(startDate);
		if(startDate==null&&endDate==null){
			//都为空
			System.out.println("2 null");
			return result;
		}
		if(startDate==null){
			//开始为空，结束不为空
			for (int i = 0; i < result.size(); i++) {
				NBenchMarkVO tttt = result.get(i);
				if ( tttt.getDate().getTime() <= endDate.getTime()) {
					realres.add(tttt);
				}
			}
			System.out.println("s null");
			return realres;
		}
		if(endDate==null){
			//结束为空，开始不为空
			for (int i = 0; i < result.size(); i++) {
				NBenchMarkVO tttt = result.get(i);
				if ( tttt.getDate().getTime() >= startDate.getTime()) {
					realres.add(tttt);
				}
			}
			System.out.println("e null");
			return realres;
		}
		//开始结束都不为空
		for (int i = 0; i < result.size(); i++) {
			NBenchMarkVO tttt = result.get(i);
			if ((tttt.getDate().getTime() >= startDate.getTime()) && (tttt.getDate().getTime() <= endDate.getTime())) {
				realres.add(tttt);
			}
			//	System.out.println("s d");
		}
		return realres;

	}


	//判断是否在筛选条件中
	private static boolean isIn(NBenchMarkVO po,Filter filter){
		ArrayList<FilterItem> filterItems =filter.getFilterItems();

		for (int i=0;i<filterItems.size();i++){
			FilterItem temp= filterItems.get(i);
			BigDecimal t=getFieVal(temp,po);
			//判断filterItem中stazt,end是否为空
			if((temp.getStart()!=null)&&(temp.getEnd()!=null)){
				if((t.compareTo(temp.getStart())<0)||(t.compareTo(temp.getEnd())>0)){
					//不符合条件
					return false;
				}
			}else if((temp.getStart()==null)&&(temp.getEnd()==null)){
				continue;
			}else if (temp.getStart() == null && (temp.getEnd() != null)) {
				if (t.compareTo(temp.getEnd()) > 0) {
					return false;
				}
			} else if (temp.getStart() != null &&temp.getEnd() == null) {
				if (t.compareTo(temp.getStart()) < 0) {
					return false;
				}
			}
		}

		return true;

	}

	private static BigDecimal getFieVal(FilterItem temp,NBenchMarkVO Vo){
		if(temp.getField()==Stockfield.adj_price){
			return Vo.getAdj_price();
		}

		if(temp.getField()==Stockfield.close){
			return Vo.getClose();
		}
		if(temp.getField()==Stockfield.high){
			return Vo.getHigh();
		}
		if(temp.getField()==Stockfield.low){
			return Vo.getLow();
		}
		if(temp.getField()==Stockfield.open){
			return Vo.getOpen();
		}



		if(temp.getField()==Stockfield.volume){
			return Vo.getVolume();
		}
		return null;
	}
}
