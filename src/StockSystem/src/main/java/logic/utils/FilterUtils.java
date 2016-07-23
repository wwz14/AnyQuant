package logic.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import enums.Stockfield;
import exception.StatusNotOKException;
import po.NStockPO;
import utils.Filter;
import utils.FilterItem;
import utils.changeRateFormate;
import vo.NStockVO;

public class FilterUtils {
	/**
	 * 遍历过滤项过滤 不带时间的过滤 polistlist
	 */
	public static ArrayList<NStockVO> filter(ArrayList<NStockVO> polistlist, Filter filter) {
		ArrayList<NStockVO> result = new ArrayList<NStockVO>();
		// 筛选
		for (int i = 0; i < polistlist.size(); i++) {
			NStockVO temp = polistlist.get(i);
			if (isIn(temp, filter)) {
				result.add(temp);
			}

		}
		return result;
	}

	// 带时间的筛选
	public static ArrayList<NStockVO> filterDetail(ArrayList<NStockVO> polistlist, Filter filter) {
		//带时间的筛选,日期可能会超过一个月
		System.out.println("filter detail");
		

		Date startDate = filter.getStartDate();
		Date endDate = filter.getEndDate();
		
		
		
		ArrayList<NStockVO> result = filter(polistlist, filter);
		ArrayList<NStockVO> realres = new ArrayList<NStockVO>();
		
		//System.out.println(startDate);
		if(startDate==null&&endDate==null){
			//都为空
			System.out.println("2 null");
			return result;
		}
		if(startDate==null){
			//开始为空，结束不为空
			for (int i = 0; i < result.size(); i++) {
				NStockVO tttt = result.get(i);
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
				NStockVO tttt = result.get(i);
				if ( tttt.getDate().getTime() >= startDate.getTime()) {
					realres.add(tttt);
				}
			}
			System.out.println("e null");
			return realres;
		}
		//开始结束都不为空
		for (int i = 0; i < result.size(); i++) {
			NStockVO tttt = result.get(i);
			if ((tttt.getDate().getTime() >= startDate.getTime()) && (tttt.getDate().getTime() <= endDate.getTime())) {
				realres.add(tttt);
			}
			//System.out.println("s d");
		}
		return realres;

	}
private static NStockVO poTOvo(NStockPO po) {
		
		return new NStockVO(po.getName(), po.getDate(), po.getOpen(), po.getHigh(), po.getLow(), po.getClose(),
				po.getAdj_price(), po.getVolume(), po.getTurnover(), po.getPe_ttm(), po.getPb());

	}
	// 带时间的筛选
		public static ArrayList<NStockVO> filterDetail(String name,ArrayList<NStockVO> polistlist, Filter filter) {
			//带时间的筛选,日期可能会超过一个月
			

			Date startDate = filter.getStartDate();
			Date endDate = filter.getEndDate();
			ArrayList<NStockPO> re=new ArrayList<NStockPO>();
			//为了算第一天的涨幅要夺取一天；
			try {
				re=GetPreStock.getPreS(name, startDate,  endDate,1);
			} catch (StatusNotOKException e) {
				e.printStackTrace();
			}
			ArrayList<NStockVO> RES=new ArrayList<NStockVO>();
			for(int i=0;i<re.size();i++){
				RES.add(poTOvo(re.get(i)));
			}
			//算一下涨跌幅
			//第一天算不了
			//目前算收盘价的涨跌幅
			for (int i = 1; i < RES.size(); i++) {
				NStockVO af=RES.get(i);
				NStockVO be=RES.get(i-1);
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
			ArrayList<NStockVO> result = filter(RES, filter);
			System.out.println(result.size());
			ArrayList<NStockVO> realres = new ArrayList<NStockVO>();
			
			//System.out.println(startDate);
			if(startDate==null&&endDate==null){
				//都为空
				System.out.println("2 null");
				return result;
			}
			if(startDate==null){
				//开始为空，结束不为空
				for (int i = 0; i < result.size(); i++) {
					NStockVO tttt = result.get(i);
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
					NStockVO tttt = result.get(i);
					if ( tttt.getDate().getTime() >= startDate.getTime()) {
						realres.add(tttt);
					}
				}
				System.out.println("e null");
				return realres;
			}
			//开始结束都不为空
			for (int i = 0; i < result.size(); i++) {
				NStockVO tttt = result.get(i);
				if ((tttt.getDate().getTime() >= startDate.getTime()) && (tttt.getDate().getTime() <= endDate.getTime())) {
					realres.add(tttt);
				}
				//System.out.println("s d");
			}
			
			
//			for(int i=0;i<realres.size();i++){
//				System.out.println(realres.get(i).getDate());
//				System.out.println(realres.get(i).getRate());
//			}
			
			return realres;

		}

	// 判断是否在筛选条件中
	private static boolean isIn(NStockVO po, Filter filter) {
		ArrayList<FilterItem> filterItems = filter.getFilterItems();

		for (int i = 0; i < filterItems.size(); i++) {
			FilterItem temp = filterItems.get(i);
			BigDecimal t = getFieVal(temp, po);
			// 判断filterItem中stazt,end是否为空
			if ((temp.getStart() != null) && (temp.getEnd() != null)) {
				if ((t.compareTo(temp.getStart()) < 0) || (t.compareTo(temp.getEnd()) > 0)) {
					// 不符合条件
					return false;
				}
			} 
			else if ((temp.getStart() == null) && (temp.getEnd() == null)) {
				continue;
			} 
			else if (temp.getStart() == null && (temp.getEnd() != null)) {
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

	private static BigDecimal getFieVal(FilterItem temp, NStockVO Vo) {
		if (temp.getField() == Stockfield.adj_price) {
			return Vo.getAdj_price();
		}

		if (temp.getField() == Stockfield.close) {
			return Vo.getClose();
		}
		if (temp.getField() == Stockfield.high) {
			return Vo.getHigh();
		}
		if (temp.getField() == Stockfield.low) {
			return Vo.getLow();
		}
		if (temp.getField() == Stockfield.open) {
			return Vo.getOpen();
		}
		if (temp.getField() == Stockfield.pb) {
			return Vo.getPb();
		}
		if (temp.getField() == Stockfield.pe_ttm) {
			return Vo.getPe_ttm();
		}
		if (temp.getField() == Stockfield.turnover) {
			return Vo.getTurnover();
		}
		if (temp.getField() == Stockfield.volume) {
			return Vo.getVolume();
		}
		return null;
	}
}
