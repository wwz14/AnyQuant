package data.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.gson.Gson;

import data.GetJSON;
import po.LinkPO;
import po.ResultListPO;
import po.ResultPO;
import utils.DataStatus;
import utils.DateTool;
import utils.NetStatus;

public class DataManager {

	private static final String PATH = "data/data.dat";

	private static final int TASKSIZE = 8;

	private static String URL = "http://121.41.106.89:8010/api/stock";

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 返回指定股票的指定时段的历史信息
	 * 
	 * @param name
	 *            股票代码
	 * @param startDate
	 *            起始时间
	 * @param endDate
	 *            终止时间
	 * @return
	 */
	public static ResultPO getHistoryData(String name, String startDate, String endDate) {
		Gson gson = new Gson();
		String time = "?start=" + startDate + "&end=" + endDate;
		String urlNeeded = URL + "/" + name + "/" + time;
		ResultPO resultPO = gson.fromJson(GetJSON.getJSON(urlNeeded), ResultPO.class);
		return resultPO;
	}

	/**
	 * 获得最新一天的所有股票的信息
	 * 
	 * @return
	 */
	public static ResultListPO getNewestData() {
		Object object = DataBase.load(PATH);
		ResultListPO resultListPO = null;
		//无数据，取网络数据
		if(object == null){
			object = refreshData();
		}
		resultListPO = (ResultListPO)object;
		//只有link，取网络数据
		if(resultListPO.getResultPOs().size() == 0 || resultListPO.getResultPOs().get(0).getData().getTrading_info().size() == 0){
			return refreshData();
		}
		
		//检查是否是最新数据
		String dataTime = resultListPO.getResultPOs().get(0).getData().getTrading_info().get(0).getDate();
		Calendar date = DateTool.getNewestTime();

		if (dataTime.equals(df.format(date.getTime()))){
			return resultListPO;
		}
		
		resultListPO = refreshData();;
		return resultListPO;
	}
	
	private static ResultListPO refreshData(){
		if(!NetStatus.isConnected())
			return null;
		DataStatus.setNeedRefresh(true);
		ResultListPO resultListPO = getData();
		DataStatus.setNeedRefresh(false);
		DataBase.save(PATH, resultListPO);
		return resultListPO;
	}
	

	/**
	 * 
	 * @return 获取最新一天的数据，若返回值为null则说明无最新数据
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ResultListPO getData() {
		//用于获得resultListPO包含的股票代码
		Gson gson = new Gson();
		ResultListPO resultListPO = gson.fromJson(GetJSON.getJSON(URL + "s"), ResultListPO.class);
		Calendar date = DateTool.getNewestTime();
		String date1 = df.format(date.getTime());
		date.add(Calendar.DATE, 1);
		String date2 = df.format(date.getTime());

		//用于划分url以便交给不同线程
		ArrayList<ArrayList<String>> urlSplit = new ArrayList<>();
		ArrayList<LinkPO> linkPOs = resultListPO.getData();
		int numOfPerUrlList = linkPOs.size() / TASKSIZE;
		String time = "?start=" + date1 + "&end=" + date2;
		for (int i = 0; i < TASKSIZE; i++) {
			ArrayList<String> list = new ArrayList<>();
			for (int j = i * numOfPerUrlList; j < (i + 1) * numOfPerUrlList; j++) {
				list.add(URL + "/" + linkPOs.get(j).getName() + "/" + time);
			}
			urlSplit.add(list);
		}

		//执行读取任务
		ArrayList<ResultPO> resultPOs = new ArrayList<>();

		ExecutorService pool = Executors.newFixedThreadPool(TASKSIZE);
		// 创建多个有返回值的任务
		List<Future> list = new ArrayList<Future>();

		for (int i = 0; i < TASKSIZE; i++) {
			Callable c = new DataReader(urlSplit.get(i));
			Future f = pool.submit(c);
			list.add(f);
		}
		pool.shutdown();

		for (Future future : list) {
			try {
				resultPOs.addAll((ArrayList<ResultPO>) future.get());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		resultListPO.setResultPOs(resultPOs);
		return resultListPO;
	}

}
/**
 * 读取数据的线程
 * @author zhuding
 *
 */
class DataReader implements Callable<Object> {

	ArrayList<String> urls;

	public DataReader(ArrayList<String> urls) {
		this.urls = urls;
	}

	@Override
	public Object call() throws Exception {
		ArrayList<ResultPO> resultPOs = new ArrayList<>();
		Gson gson = new Gson();
		for (String url : urls) {
			ResultPO resultPO = gson.fromJson(GetJSON.getJSON(url), ResultPO.class);
			//System.out.println(resultPO.getData().getName());
			resultPOs.add(resultPO);
		}
		return resultPOs;
	}

}
