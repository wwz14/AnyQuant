package presentation.graph.util;

import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.data.xy.XYSeriesCollection;

/**  
* @ClassName: ComputingModule    
* @Description: 用于计算数据集的最值
* @author zhuding    
*/
public class ComputingModule {
	
	private ComputingModule(){}
	
	/**
	 * 计算TimeSeriesCollection中数据的最大值
	 * @return
	 */
	public static double computingMaxValueForTSC(TimeSeriesCollection timeSeriesCollection) {
		if(timeSeriesCollection == null)
			return Double.NaN;
		double result = Double.MIN_VALUE;
		int count1 = timeSeriesCollection.getSeriesCount();// 一共有多少个序列，目前为一个
		for (int i = 0; i < count1; i++) {
			int count2 = timeSeriesCollection.getItemCount(i);// 每一个序列有多少个数据项
			for (int j = 0; j < count2; j++) {
				if (result < timeSeriesCollection.getYValue(i, j)) {// 取第i个序列中的第j个数据项的值
					result = timeSeriesCollection.getYValue(i, j);
				}
			}
		}
		return result;
	}
	
	/**
	 * 计算TimeSeriesCollection中数据的最小值
	 * @return
	 */
	public static double computingMinValueForTSC(TimeSeriesCollection timeSeriesCollection) {
		if(timeSeriesCollection == null)
			return Double.NaN;
		double result = Double.MAX_VALUE;
		int count1 = timeSeriesCollection.getSeriesCount();// 一共有多少个序列，目前为一个
		for (int i = 0; i < count1; i++) {
			int count2 = timeSeriesCollection.getItemCount(i);// 每一个序列有多少个数据项
			for (int j = 0; j < count2; j++) {
				if (result > timeSeriesCollection.getYValue(i, j)) {// 取第i个序列中的第j个数据项的值
					result = timeSeriesCollection.getYValue(i, j);
				}
			}
		}
		return result;
	}
	
	/**
	 * 计算OHLCSeriesCollection中数据的最大值
	 * @return
	 */
	public static double computingMaxValueForOHLCSC(OHLCSeriesCollection ohlcSeriesCollection) {
		if(ohlcSeriesCollection == null)
			return Double.NaN;
		double result = Double.MIN_VALUE;
		int seriesCount = ohlcSeriesCollection.getSeriesCount();// 一共有多少个序列，目前为一个
		for (int i = 0; i < seriesCount; i++) {
			int itemCount = ohlcSeriesCollection.getItemCount(i);// 每一个序列有多少个数据项
			for (int j = 0; j < itemCount; j++) {
				if (result < ohlcSeriesCollection.getHighValue(i, j)) {
					result = ohlcSeriesCollection.getHighValue(i, j);
				}
			}
		}
		return result;
	}
	
	/**
	 * 计算OHLCSeriesCollection中数据的最小值
	 * @return
	 */
	public static double computingMinValueForOHLCSC(OHLCSeriesCollection ohlcSeriesCollection) {
		if(ohlcSeriesCollection == null)
			return Double.NaN;
		double result = Double.MAX_VALUE;
		int seriesCount = ohlcSeriesCollection.getSeriesCount();// 一共有多少个序列，目前为一个
		for (int i = 0; i < seriesCount; i++) {
			int itemCount = ohlcSeriesCollection.getItemCount(i);// 每一个序列有多少个数据项
			for (int j = 0; j < itemCount; j++) {
				if (result > ohlcSeriesCollection.getLowValue(i, j)) {
					result = ohlcSeriesCollection.getLowValue(i, j);
				}
			}
		}
		return result;
	}
	
	/**
	 * 计算XYSeriesCollection中数据的最小值
	 * @return
	 */
	public static double computingMinValueForXYSC(XYSeriesCollection xySeriesCollection) {
		if(xySeriesCollection == null)
			return Double.NaN;
		double result = Double.MAX_VALUE;
		int seriesCount = xySeriesCollection.getSeriesCount();// 一共有多少个序列，目前为一个
		for (int i = 0; i < seriesCount; i++) {
			int itemCount = xySeriesCollection.getItemCount(i);// 每一个序列有多少个数据项
			for (int j = 0; j < itemCount; j++) {
				if (result > xySeriesCollection.getYValue(i, j)) {
					result = xySeriesCollection.getYValue(i, j);
				}
			}
		}
		return result;
	}
	
	/**
	 * 计算XYSeriesCollection中数据的最大值
	 * @return
	 */
	public static double computingMaxValueForXYSC(XYSeriesCollection xySeriesCollection) {
		if(xySeriesCollection == null)
			return Double.NaN;
		double result = Double.MIN_VALUE;
		int seriesCount = xySeriesCollection.getSeriesCount();// 一共有多少个序列，目前为一个
		for (int i = 0; i < seriesCount; i++) {
			int itemCount = xySeriesCollection.getItemCount(i);// 每一个序列有多少个数据项
			for (int j = 0; j < itemCount; j++) {
				if (result < xySeriesCollection.getYValue(i, j)) {
					result = xySeriesCollection.getYValue(i, j);
				}
			}
		}
		return result;
	}
}
