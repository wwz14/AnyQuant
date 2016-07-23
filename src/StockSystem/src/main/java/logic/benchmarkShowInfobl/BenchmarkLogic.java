package logic.benchmarkShowInfobl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import data.BenchmarkData;
import data.sqlImpl.BenchmarkDataSQL;
import dataservice.BenchmarkDataservice;
import enums.KLineType;
import enums.SortType;
import exception.StatusNotOKException;
import logic.utils.BenchFilt;
import logic.utils.CreatBenchMarkSort;
import logic.utils.CreatKline;
import logic.utils.HeapSort;
import logic.utils.SortByTime;
import logicservice.showInfoblservice.BenchmarkLogicservice;
import po.NBenchMarkPO;
import utils.DateTool;
import utils.Filter;
import utils.WHICHIMP;
import utils.changeRateFormate;
import vo.KLineVO;
import vo.MAVO;
import vo.NBenchMarkVO;
import vo.SortVO;

public class BenchmarkLogic implements BenchmarkLogicservice {
	private Date startTime;
	private Date endTime;
	private ArrayList<NBenchMarkVO> nBenchmarkList = new ArrayList<NBenchMarkVO>();
	BenchmarkDataservice benchmarkService ;


	MAVO mavo;
	KLineVO kLineVO;
	BigDecimal ma5 = new BigDecimal(0);
	BigDecimal ma20 = new BigDecimal(0);
	BigDecimal ma30 = new BigDecimal(0);
	BigDecimal ma60 = new BigDecimal(0);
	BigDecimal maVOL5 = new BigDecimal(0);
	BigDecimal maVOL10 = new BigDecimal(0);
	private int weishu = 5;
	//	BenchmarkDataservice stub = new BenchmarkData_Stub();
	// 为了用stub测试
	public void setService(BenchmarkDataservice ser) {
		this.benchmarkService = ser;
	}

	public BenchmarkLogic(){
		if(WHICHIMP.isSQL){
			benchmarkService = new BenchmarkDataSQL();
		}else{
			benchmarkService = new BenchmarkData();
		}
	}
	@Override
	public ArrayList<NBenchMarkVO> getByName(String name) {
		ArrayList<NBenchMarkVO> voList = new ArrayList<NBenchMarkVO>();

		try {
			//if(NetStatus.isConnected()){
			ArrayList<NBenchMarkPO> poList = benchmarkService.getByName(name);
			for (int i = 0; i < poList.size(); i++) {
				voList.add(toVO(poList.get(i)));
			}
			//			}else {
			//				voList = SaveDataToTXT.loadBenchmarkday(DateTool.getStringByDate(DateTool.beforeDate(new Date(), -200)), DateTool.getStringByDate(new Date()));
			//			}
			//TODO 涨跌幅,第一天算不了
			for(int i = 1;i<voList.size();i++) {
				BigDecimal thisClose = voList.get(i).getClose();
				BigDecimal preClose = voList.get(i-1).getClose();
				BigDecimal changeRate = new BigDecimal(0);
				if(preClose.compareTo(new BigDecimal(0)) != 0)
					changeRate = thisClose.subtract(preClose).divide(preClose,5,BigDecimal.ROUND_HALF_UP);
				else
					voList.get(i).setRate("null");
				voList.get(i).setChangeRate(changeRate);
				voList.get(i).setRate(changeRateFormate.formate(changeRate));
			}
		} catch (StatusNotOKException e) {
			System.out.println(e.message);
		}

		//大于1
		if(voList.size() >1){
			voList.remove(0);
		}
		//		else{
		//			voList.get(0).setChangeRate(new BigDecimal(0));
		//			voList.get(0).setRate("0");
		//		}

		return sort(voList, SortType.dateDown);
	}

	@Override
	public ArrayList<NBenchMarkVO> getResultList(ArrayList<NBenchMarkVO> vo, Filter filter) {
		if(vo==null){
			System.out.println("没有数据可供筛选");
			return null;
		}

		if(vo.size()>0){
			return BenchFilt.filterDetail(vo.get(0).getName(),vo, filter);	
		}

		else
			return BenchFilt.filterDetail(vo, filter);
	}

	@Override
	public ArrayList<NBenchMarkVO> sort(ArrayList<NBenchMarkVO> vo, SortType sortType) {
		ArrayList<SortVO> benchMarkSortList = new ArrayList<SortVO>();
		// 判断排序类型
		try {
			if (sortType.equals(SortType.openAscend)) {
				benchMarkSortList = HeapSort.AntiHeapSort(CreatBenchMarkSort.sortByOpen(vo));// 开盘价升序
			} else if (sortType.equals(SortType.openDown)) {
				benchMarkSortList = HeapSort.heapSort(CreatBenchMarkSort.sortByOpen(vo));// 开盘价降序
			} else if (sortType.equals(SortType.closeAscend)) {
				benchMarkSortList = HeapSort.AntiHeapSort(CreatBenchMarkSort.sortByClose(vo));
			} else if (sortType.equals(SortType.closeDown)) {
				benchMarkSortList = HeapSort.heapSort(CreatBenchMarkSort.sortByClose(vo));
			} else if (sortType.equals(SortType.adj_priceAscent)) {
				benchMarkSortList = HeapSort.AntiHeapSort(CreatBenchMarkSort.sortByAdjPrice(vo));
			} else if (sortType.equals(SortType.adj_priceDown)) {
				benchMarkSortList = HeapSort.heapSort(CreatBenchMarkSort.sortByAdjPrice(vo));
			} else if (sortType.equals(SortType.highAscend)) {
				benchMarkSortList = HeapSort.AntiHeapSort(CreatBenchMarkSort.sortByHigh(vo));
			} else if (sortType.equals(SortType.highDown)) {
				benchMarkSortList = HeapSort.heapSort(CreatBenchMarkSort.sortByHigh(vo));
			} else if (sortType.equals(SortType.lowAscend)) {
				benchMarkSortList = HeapSort.AntiHeapSort(CreatBenchMarkSort.sortByLow(vo));
			} else if (sortType.equals(SortType.lowDown)) {
				benchMarkSortList = HeapSort.heapSort(CreatBenchMarkSort.sortByLow(vo));
			} else if (sortType.equals(SortType.volumeAscent)) {
				benchMarkSortList = HeapSort.AntiHeapSort(CreatBenchMarkSort.sortByVolume(vo));
			} else if (sortType.equals(SortType.volumeDown)) {
				benchMarkSortList = HeapSort.heapSort(CreatBenchMarkSort.sortByVolume(vo));
			} else if (sortType.equals(SortType.dateAscent)) {
				benchMarkSortList = SortByTime.sortByTimeup(CreatBenchMarkSort.sortByDate(vo));
			} else if(sortType.equals(SortType.dateDown)){
				benchMarkSortList = SortByTime.sortByTime(CreatBenchMarkSort.sortByDate(vo));
			} else if(sortType.equals(SortType.changeRateAscent)){
				benchMarkSortList = HeapSort.AntiHeapSort(CreatBenchMarkSort.sortByChangeRate(vo));
			}else {
				benchMarkSortList = HeapSort.heapSort(CreatBenchMarkSort.sortByChangeRate(vo));
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		ArrayList<NBenchMarkVO> benchMarkList = new ArrayList<NBenchMarkVO>();
		for (int i = 0; i < benchMarkSortList.size(); i++) {
			benchMarkList.add(benchMarkSortList.get(i).getBenchMarkVO());
		}
		return benchMarkList;
	}

	private NBenchMarkVO toVO(NBenchMarkPO po) {
		return new NBenchMarkVO(po.getName(), po.getVolume(), po.getHigh(), po.getAdj_price(), po.getLow(),
				po.getDate(), po.getClose(), po.getOpen());
	}

	@Override
	public ArrayList<MAVO> getMAVOs(Date start, Date end) throws StatusNotOKException {
		Date datePointer = start;
		ArrayList<MAVO> result = new ArrayList<MAVO>();
		//向前取3个月日期，获得的数据按时间升序
		ArrayList<NBenchMarkVO> voList = getBenchMarkData("hs300", DateTool.beforeDate(start, -50), end);
		long betweenDay = betweenDays (start,end);	
		//计算某个日期的ma5，ma10，ma20，ma30，ma60
		for(int i = 0;i<betweenDay;i++){
			//计算ma5
			if(isWorkDay(voList,datePointer)){
				ArrayList<NBenchMarkVO> intercept_ma5 = intercept(voList,datePointer,5);
				for(int j = 0;j<intercept_ma5.size();j++) {
					ma5 = ma5.add(intercept_ma5.get(j).getClose());
				}
				ma5 = ma5.divide(new BigDecimal(5),weishu,BigDecimal.ROUND_HALF_UP);
				//计算ma20
				ArrayList<NBenchMarkVO> intercept_ma20 = intercept(voList,datePointer,20);
				for(int j = 0;j<intercept_ma20.size();j++) {
					ma20 = ma20.add(intercept_ma20.get(j).getClose());
				}
				ma20 = ma20.divide(new BigDecimal(20),weishu,BigDecimal.ROUND_HALF_UP);
				//计算ma30
				ArrayList<NBenchMarkVO> intercept_ma30 = intercept(voList ,datePointer,30);
				for(int j = 0;j<intercept_ma30.size();j++) {
					ma30 = ma30.add(intercept_ma30.get(j).getClose());
				}
				ma30 = ma30.divide(new BigDecimal(30),weishu,BigDecimal.ROUND_HALF_UP);
				//计算ma60
				ArrayList<NBenchMarkVO> intercept_ma60 = intercept(voList ,datePointer,60);
				for(int j = 0;j<intercept_ma60.size();j++) {
					ma60 = ma60.add(intercept_ma60.get(j).getClose());
				}
				ma60 = ma60.divide(new BigDecimal(60),weishu,BigDecimal.ROUND_HALF_UP);
				//计算maVOL5
				ArrayList<NBenchMarkVO> intercept_maVOL5 = intercept(voList ,datePointer,5);
				for(int j = 0;j<intercept_maVOL5.size();j++) {
					maVOL5  = maVOL5 .add(intercept_maVOL5.get(j).getVolume());
				}
				maVOL5 = maVOL5.divide(new BigDecimal(5),weishu,BigDecimal.ROUND_HALF_UP);
				//计算maVOL10
				ArrayList<NBenchMarkVO> intercept_maVOL10 = intercept(voList ,datePointer,10);
				for(int j = 0;j<intercept_maVOL10.size();j++) {
					maVOL10  = maVOL10.add(intercept_maVOL10.get(j).getVolume());
				}
				maVOL10 = maVOL10.divide(new BigDecimal(10),weishu,BigDecimal.ROUND_HALF_UP);
				//如果是工作日
				mavo = new MAVO(ma5,ma20,ma30,ma60,maVOL5,maVOL10,datePointer);
				result.add(mavo);
			}
			//			}else{
			//				//不是工作日，null
			//				mavo = new MAVO(null,null,null,null,null,null,datePointer);
			//			}
			//result.add(mavo);//将datePointer所指日期的数据放到返回数据列表中
			//清空ma5,ma20,ma30,ma60,,maVOL5,maVOL10
			setZero(ma5);
			setZero(ma20);
			setZero(ma30);
			setZero(ma60);
			setZero(maVOL5);
			setZero(maVOL10);
			//下一天日期
			datePointer = DateTool.beforeDate(datePointer, 1);		
		}		
		return result;
	}

	@Override
	public ArrayList<KLineVO> getKLineVOs(KLineType kLineType, Date start, Date end) throws StatusNotOKException {
		ArrayList<NBenchMarkVO> voList = new ArrayList<NBenchMarkVO> ();
		if(start.equals(startTime)&&end.equals(endTime)&&(!nBenchmarkList.isEmpty()))
			voList = nBenchmarkList;
		else{
			voList = getBenchMarkData("hs300", DateTool.beforeDate(start, -32), DateTool.beforeDate(end, 32));
			nBenchmarkList = voList;
		}
		ArrayList<KLineVO> kLineVoList = new ArrayList<KLineVO>();
		Date sameWeek = new Date();
		Date sameMonth = new Date();

		long betweendays = betweenDays(start,end);

		Date datePointer = start;
		if(kLineType.equals(KLineType.day)){
			ArrayList<NBenchMarkVO> trurList = removeMore(voList,start,end);
			for(int i = 0;i<betweendays;i++) {	
				if(isWorkDay(trurList,datePointer)){
					kLineVO = CreatKline.kLineForDay(todayBenchmark(trurList,datePointer),datePointer);
					kLineVoList.add(kLineVO);
				}
				datePointer = DateTool.beforeDate(datePointer, 1);

			}

		}else if(kLineType.equals(KLineType.week)){
			//System.out.println("week k line");
			//ArrayList<NBenchMarkVO>VOList = getBenchMarkData("hs300", DateTool.beforeDate(start, -10), DateTool.beforeDate(end, 10));
			for(int i = 0;i<betweendays;i++){				
				Date monday = DateTool.getTheFirstDay(datePointer);
				Date friday = DateTool.getTheLastDayOfWeek(datePointer);
				if(i == 0) {
					ArrayList<NBenchMarkVO> weekList = interceptByDate(voList,monday,friday);
					if(!weekList.isEmpty()) {
						kLineVO = CreatKline.kLineForWeek(weekList, weekList.get(weekList.size()-1).getDate());
						kLineVoList.add(kLineVO);
					}
					sameWeek = friday;
				}else {
					if(!sameWeek.equals(friday)) {
						ArrayList<NBenchMarkVO> weekList = interceptByDate(voList,monday,friday);
						if(!weekList.isEmpty()) {
							kLineVO = CreatKline.kLineForWeek(weekList, weekList.get(weekList.size()-1).getDate());
							kLineVoList.add(kLineVO);
						}
						sameWeek = friday;
					}
				}			
				datePointer = DateTool.beforeDate(datePointer, 1);				
			}

		}else if(kLineType.equals(KLineType.month)){
			for(int i = 0;i<betweendays;i++){
				Date firstDayOfMonth = DateTool.getTheFirstDay(datePointer);
				Date lastDayOfMonth = DateTool.getTheLastDay(datePointer);
				ArrayList<NBenchMarkVO> monthList = interceptByDate(voList,firstDayOfMonth,lastDayOfMonth);
				if(!monthList.isEmpty()){
					kLineVO = CreatKline.kLineForMonth(monthList, monthList.get(monthList.size()-1).getDate());
					if(i==0){
						kLineVoList.add(kLineVO);
						sameMonth = lastDayOfMonth;
					}else {
						if(!lastDayOfMonth.equals(sameMonth)){
							kLineVoList.add(kLineVO);
							sameMonth = lastDayOfMonth;
						}
					}
				}
				datePointer = DateTool.beforeDate(datePointer, 1);	

			}
		}
		return kLineVoList;
	}

	/**
	 * 用于截取5，20，30，60
	 * @param vo
	 * @param end
	 * @param days
	 * 前置条件；Date end一定是工作日
	 * @return
	 */
	private ArrayList<NBenchMarkVO> intercept(ArrayList<NBenchMarkVO> vo, Date end,int days) {
		int place = 0;
		ArrayList<NBenchMarkVO> reverse = sort(vo,SortType.dateDown);
		ArrayList<NBenchMarkVO> interceptList = new ArrayList<NBenchMarkVO>();
		//检索符合end日期的项,
		for(int j = 0;j<reverse.size();j++){
			if(reverse.get(j).getDate().getTime() == end.getTime())
				place = j;		
		}
		//截取j -j+days-1
		for(int p = place+days-1 ;p>= place;p--){
			if(p<reverse.size()){
				interceptList.add(reverse.get(p));
			}
		}

		return interceptList;
	}

	private long betweenDays(Date start,Date end) {
		long betweenDay = (long)((end.getTime()-start.getTime()) / (1000 * 60 * 60 *24) + 0.5);
		return betweenDay + 1;
	}

	private BigDecimal setZero(BigDecimal b) {
		return b.multiply(new BigDecimal(0));
	}

	private boolean isWorkDay(ArrayList<NBenchMarkVO> voList ,Date d) {
		boolean result = false;
		for(int i = 0;i<voList.size();i++) {
			if(voList.get(i).getDate().getTime() == d.getTime()){
				result = true;
				break;
			}
		}
		return result;
	}

	private NBenchMarkVO todayBenchmark(ArrayList<NBenchMarkVO> voList ,Date d) {
		NBenchMarkVO result = null;
		for(int i = 0;i<voList.size();i++) {
			if(voList.get(i).getDate().getTime() == d.getTime()){
				return voList.get(i);
			}
		}
		return result;
	}

	private ArrayList<NBenchMarkVO> getBenchMarkData(String name, Date start,Date end) throws StatusNotOKException {
		String startTime = DateTool.getStringByDate(DateTool.beforeDate(start, -60));
		String endTime = DateTool.getStringByDate(end);
		ArrayList<NBenchMarkVO> voList = new ArrayList<NBenchMarkVO> ();
		ArrayList<NBenchMarkPO> poList = benchmarkService.getByTime(name, startTime, endTime);
		//ArrayList<NBenchMarkPO> poList = stub.getByTime(name, startTime, endTime);
		for(int i = 0;i<poList.size();i++) {
			voList.add(toVO(poList.get(i)));
		}
		voList = sort(voList,SortType.dateAscent);
		return voList;
	}


	@Override
	public ArrayList<NBenchMarkVO> getByTime(String name, Date startTime,
			Date endTime) throws StatusNotOKException {
		ArrayList<NBenchMarkVO> voList = new ArrayList<NBenchMarkVO>();
		if(startTime==null||endTime==null){
			return voList;
		}
		try {
			ArrayList<NBenchMarkPO> poList = benchmarkService.getByTime(name, DateTool.getStringByDate(startTime),
					DateTool.getStringByDate(endTime));
			for (int i = 0; i < poList.size(); i++) {
				voList.add(toVO(poList.get(i)));
			}
			if(voList.size()<1){
				return voList;
			}
			//TODO 涨跌幅,第一天算不了
			for(int i = 1;i<voList.size();i++) {
				BigDecimal thisClose = voList.get(i).getClose();
				BigDecimal preClose = voList.get(i-1).getClose();
				BigDecimal changeRate = new BigDecimal(1);
				if(preClose.compareTo(new BigDecimal(0)) != 0)
					changeRate = thisClose.subtract(preClose).divide(preClose,5,BigDecimal.ROUND_HALF_UP);
				else
					voList.get(i).setRate("null");
				voList.get(i).setChangeRate(changeRate);
				voList.get(i).setRate(changeRateFormate.formate(changeRate));
			}
		} catch (StatusNotOKException e) {
			System.out.println(e.message);
		}
		if(voList.size() > 1)
			voList.remove(0);
		else{
			voList.get(0).setChangeRate(new BigDecimal(0));
			voList.get(0).setRate("0");
		}
		return sort(voList, SortType.dateAscent);
	}

	private ArrayList<NBenchMarkVO> interceptByDate(ArrayList<NBenchMarkVO> list,Date start,Date end) {
		ArrayList<NBenchMarkVO> result = new ArrayList<>();
		Date datePointer = start;
		while(datePointer.getTime() != DateTool.beforeDate(end, 1).getTime()){
			long time = datePointer.getTime();
			if(isWorkDay(list,datePointer)){
				for(int i = 0;i<list.size();i++) {
					if(list.get(i).getDate().getTime() == time ){
						result.add(list.get(i));
						list.remove(list.get(i));
						break;
					}

				}
			}
			datePointer = DateTool.beforeDate(datePointer, 1);

		}
		return result;
	}

	private ArrayList<NBenchMarkVO> removeMore(ArrayList<NBenchMarkVO> list , Date start,Date end) {
		ArrayList<NBenchMarkVO> trueList = list;
		for(int i = 0;i<list.size();i++) {
			boolean ispre = ( trueList.get(i).getDate().getTime() < start.getTime() );
			boolean ispost = ( trueList.get(trueList.size() - 1 - i).getDate().getTime() > end.getTime());
			if(ispre) 
				trueList.remove(i);
			if(ispost)
				trueList.remove(trueList.size() - 1 - i);			
			if((!ispre)&&(!ispost)) {
				break;
			}	
		}

		return trueList;
	}



}
