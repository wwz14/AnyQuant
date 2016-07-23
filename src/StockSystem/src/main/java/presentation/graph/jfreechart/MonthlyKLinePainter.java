package presentation.graph.jfreechart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;

import utils.DateTool;
import vo.KLineVO;
import vo.MAVO;

/**  
* @ClassName: MonthlyKLinePainter    
* @Description: 画月K线图的画图器
* @author zhuding    
* @date 2016年6月18日 下午8:35:09    
*        
*/
public class MonthlyKLinePainter extends KLinePainter{

	public MonthlyKLinePainter(List<KLineVO> datas,List<MAVO> maDatas) {
		super(datas, maDatas);
	}
	
	@Override
	protected boolean setKLineDetail() {
		candlestickRender.setCandleWidth(500 / klineDatas.size());
		List<Date> dates = new ArrayList<>();
		for (MAVO mavo : maDatas) {
			dates.add(mavo.getDate());
		}
		Date start = DateTool.getTheFirstDay(DateTool.getMinDate(dates));
		Date end = DateTool.beforeDate(DateTool.getTheLastDay(DateTool.getMaxDate(dates)), 1);
		x1Axis.setRange(start,end);// 设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
		int t = (int) (DateTool.betweenDays(start, end) / 5);
		t = t < 1 ? 1 : t;
		x1Axis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, t));// 设置时间刻度的间隔
		return true;
	}
	
	@Override
	protected RegularTimePeriod getRegularTimePeriod(Date date) {
		return new Month(date);
	}

}
