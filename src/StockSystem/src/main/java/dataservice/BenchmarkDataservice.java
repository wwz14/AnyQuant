package dataservice;

import java.util.ArrayList;

import exception.StatusNotOKException;
import po.NBenchMarkPO;

public interface BenchmarkDataservice {

	/**
	 * 返回一开始显示在大盘界面上
	 * @param name：大盘名称，现在只有hs300
	 * @return ArrayList<NBenchMarkPO> 一年的数据
	 * */
	ArrayList<NBenchMarkPO> getByName(String name) throws StatusNotOKException;
	

	/**
	 * 获得指定时间段某大盘的数据信息
	 * @param name 大盘名称，现在只有hs300
	 * @param startTime 起始时间
	 * @param endTime 最终时间
	 * @return 该大盘指定时间段数据
	 * @throws StatusNotOKException
	 */
	ArrayList<NBenchMarkPO> getByTime(String name, String startTime, String endTime) throws StatusNotOKException;
}
